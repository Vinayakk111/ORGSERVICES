package com.app.vpk.controller;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.vpk.docservice.PDFBoxTableExample;
import com.app.vpk.entity.CountryLanguage;
import com.app.vpk.entity.FeatureFlag;
import com.app.vpk.repository.CountryLanguageRepository;
import com.app.vpk.repository.FeatureFlagRepository;
import com.app.vpk.service.UserService;
import com.app.vpk.utils.ResourceNotFoundException;

@RestController
public class TestController {
	private static final Logger logger = LogManager.getLogger(TestController.class);
	
	@Value("${app.download.path}")
    private String appName;

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
	
	@GetMapping("/getcnlang1")
	public List<CountryLanguage> getcnlang() {
		return countryLanguageRepository.findAll();
	}
	
	@GetMapping("/getpropname1")
	public String getpropname() {
		logger.info(appName);
		return appName;
	}
	
	@GetMapping("/getlangPDF")
	public String getlangPDF() throws IOException, PrinterException {
		String Response="";
		Optional<FeatureFlag> fFlag=featureFlagrepo.findById("generatePDF");
		if(fFlag.isPresent() && fFlag.get().isEnable()) {
			pDFBoxTableExample.getpdf("mr");
			Response="success";
		}else {
			Response="failed due to DDisabled Features";
		}
		logger.info("Resp"+Response);
		return Response;
	}
	@GetMapping("/getlangPDFDownload")
	public ResponseEntity<InputStreamResource>  getlangPDFDownload() throws IOException {
		 File pdfFile = new File("E:/data/dev/table.pdf");
		 File zipFile = zipPdf(pdfFile);

		 InputStreamResource resource = new InputStreamResource(new FileInputStream(zipFile));
	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipFile.getName())
	                .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                .body(resource);
	}
	
	 public File zipPdf(File pdfFile) throws IOException {
	        File zipFile = new File("report.zip");
	        try (FileOutputStream fos = new FileOutputStream(zipFile);
	             ZipOutputStream zipOut = new ZipOutputStream(fos);
	             FileInputStream fis = new FileInputStream(pdfFile)) {
	            
	            ZipEntry zipEntry = new ZipEntry(pdfFile.getName());
	            zipOut.putNextEntry(zipEntry);
	            byte[] bytes = new byte[1024];
	            int length;
	            while ((length = fis.read(bytes)) >= 0) {
	                zipOut.write(bytes, 0, length);
	            }
	        }
	        return zipFile;
	    }
	 
	   @GetMapping("/find")
	    public String findResource(@RequestParam String id) {
	        if ("123".equals(id)) {
	            return "Resource found!";
	        }
	     throw new ResourceNotFoundException("Resource with ID " + id + " not found");
	    }
	
}
