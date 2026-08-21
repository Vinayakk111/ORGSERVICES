package com.app.vpk.controller;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app.vpk.docservice.PDFBoxTableExample;
import com.app.vpk.dto.UserInfo;
import com.app.vpk.entity.CountryLanguage;
import com.app.vpk.entity.FeatureFlag;
import com.app.vpk.entity.User;
import com.app.vpk.repository.CountryLanguageRepository;
import com.app.vpk.repository.FeatureFlagRepository;
import com.app.vpk.service.ConfigService;
import com.app.vpk.service.UserService;
import com.app.vpk.utils.ApiResponse;
import com.app.vpk.utils.ResourceNotFoundException;
import com.app.vpk.utils.Utilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Test API", description = "Test API with Swagger")
@CrossOrigin(origins = "http://localhost:4200") // Allow requests only from Angular
public class TestController {
	private static final Logger logger = LogManager.getLogger(TestController.class);

	@Value("${app.download.path}")
	private String downloadPath;

	@Autowired
	private ConfigService configService;

	@Autowired
	EntityManager manager;

	@Autowired
	PDFBoxTableExample pDFBoxTableExample;

	@Autowired
	UserService userService;

	@Autowired
	CountryLanguageRepository countryLanguageRepository;

	@Autowired
	FeatureFlagRepository featureFlagrepo;

	@Operation(summary = "Getting Countries Data", deprecated = false, method = "getcnlang", requestBody = @RequestBody(description = "nothong to pass to get counttry data", required = true, content = @Content(schema = @Schema(implementation = CountryLanguage.class))), description = "Returns a Countries Data")
	@GetMapping("/getcountrylanguage")
	public List<CountryLanguage> getcnlang() {
		return countryLanguageRepository.findAll();
	}

	
	@GetMapping("/test")
	public ResponseEntity<?> getpropname() {
		logger.info(downloadPath);
		
		return ResponseEntity
		.status(HttpStatus.OK)
		.body("Success");
	}

	@GetMapping("/getsamplepdfdownload")
	public ResponseEntity<?> getSampleMultiLanguagePDFDownload() throws IOException, PrinterException {
		try {
			String Response = "";
			Optional<FeatureFlag> fFlag = featureFlagrepo.findById("generatePDF");
			if (fFlag.isPresent() && fFlag.get().isEnable()) {
				pDFBoxTableExample.getpdf(pDFBoxTableExample.createSamplePDF());
				Response = "success";
			} else {
				Response = "failed due to Feature is Disabled";
			}
			logger.info("Respose {}" , Response);
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(Response);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e);
		}
	}

	@GetMapping("/getZIPDownload")
	public ResponseEntity<?> getSampleMultiLanguageZipDownolad() throws IOException {
		
		try {
			File pdfFile = new File(pDFBoxTableExample.createSamplePDF());
			File zipFile = new Utilities().zipPdf(pdfFile);
			
			InputStreamResource resource = new InputStreamResource(new FileInputStream(zipFile));
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipFile.getName())
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);	
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e);
		}
	}

	@GetMapping("/find")
	public String findResource(@RequestParam String id) {
		if ("123".equals(id)) {
			return "Resource found!";
		}
		throw new ResourceNotFoundException("Resource with ID " + id + " not found");
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<User>> getUser(@PathVariable Long id) {
		int maxAttempts = configService.getIntConfig("MAX_LOGIN_ATTEMPTS", 3);
		boolean isNewFeatureEnabled = configService.getBooleanConfig("ENABLE_NEW_FEATURE", false);
		String supportEmail = configService.getStringConfig("SUPPORT_EMAIL", "default@x.com");

		Map<String, Runnable> commands = new HashMap<>();

		commands.put("start", () -> System.out.println("Starting..."));
		commands.put("stop", () -> System.out.println("Stopping..."));
		commands.put("restart", () -> {
			System.out.println("Stopping...");
			System.out.println("Starting...");
		});

		User user = userService.findById(id);
		if (user == null) {
			return ApiResponse.error("User not found", HttpStatus.NOT_FOUND, Optional.empty());
		}
		return ApiResponse.success(user, "User fetched successfully");
	}

	@PostMapping("/jsontest")
	public String jsonTest(@org.springframework.web.bind.annotation.RequestBody(required = true) UserInfo userInfo) throws JsonProcessingException {
	    Utilities.infoLogger.accept(logger, new ObjectMapper().writeValueAsString(userInfo));
		return new ObjectMapper().writeValueAsString(userInfo);
	}

}
