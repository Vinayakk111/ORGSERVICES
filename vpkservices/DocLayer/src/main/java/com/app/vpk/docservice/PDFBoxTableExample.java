package com.app.vpk.docservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.function.BiFunction;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.vpk.entity.CountryLanguage;
import com.app.vpk.repository.CountryLanguageRepository;

@Service
public class PDFBoxTableExample {

	@Value("${app.download.path}")
	private String downloadPath;
	
	@Autowired
	CountryLanguageRepository countryLanguageRepository; 

	public void getpdf(String lang) {
		String langName = StringUtils.isNotEmpty(lang) ? "lang/lang_" + lang + ".properties"
				: "lang/lang_en" + ".properties";
		Properties prop = new Properties();
		InputStream ipStream = getClass().getClassLoader().getResourceAsStream(langName);
		if (null != ipStream) {
			try {
				prop.load(ipStream);
			} catch (IOException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}

		try (PDDocument document = new PDDocument()) {

			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(document, page);
//			
//			PDType0Font unicodeFont = PDType0Font.load(document, getFontStream("mr"));
//            contentStream.setFont(unicodeFont, 12);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			float margin = 50;
			float yStart = page.getMediaBox().getHeight() - margin;
			float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
			float yPosition = yStart;
			float rowHeight = 20;
			int numRows = 5;
			int numCols = 3;
			float colWidth = tableWidth / numCols;

			// Draw table grid
			for (int i = 0; i <= numRows; i++) {
				contentStream.moveTo(margin, yPosition - (i * rowHeight));
				contentStream.lineTo(margin + tableWidth, yPosition - (i * rowHeight));
			}
			for (int i = 0; i <= numCols; i++) {
				contentStream.moveTo(margin + (i * colWidth), yStart);
				contentStream.lineTo(margin + (i * colWidth), yStart - (numRows * rowHeight));
			}
			contentStream.stroke();

			// Add table headers
			contentStream.beginText();
			contentStream.newLineAtOffset(margin + 5, yPosition - 15);
			contentStream.showText("Column 1");
			contentStream.newLineAtOffset(colWidth, 0);
			contentStream.showText("Column 2");
			contentStream.newLineAtOffset(colWidth, 0);
			contentStream.showText("Column 3");
			contentStream.endText();

			BiFunction<String, String, String> sendlangProp = (input, constant) -> {
				return StringUtils.isEmpty(input) ? constant : prop.getProperty(input);
			};
			// Add table rows
//			String[][] data = { { "Hiii", "Row 1 Col 2", "Row 1 Col 3" },
//					{ "Row 2 Col 1", "Row 2 Col 2", "Row 2 Col 3" }, { "Row 3 Col 1", "Row 3 Col 2", "Row 3 Col 3" },
//					{ "Row 4 Col 1", "Row 4 Col 2", "Row 4 Col 3" }, { "Row 5 Col 1", "Row 5 Col 2", "Row 5 Col 3" } };
			
	List<CountryLanguage> countryList=countryLanguageRepository.findAll();

			yPosition -= rowHeight;
//			contentStream.setFont(PDType1Font.HELVETICA, 12);
			PDType0Font unicodeFont1 = PDType0Font.load(document, getFontStream("mr"));
            contentStream.setFont(unicodeFont1, 12);

			for (CountryLanguage country : countryList) {
				contentStream.beginText();
//				contentStream.newLineAtOffset(margin + 5, yPosition - 15);
				contentStream.showText(country.getCountryCode());
				contentStream.newLineAtOffset(colWidth, 0);
				contentStream.showText(country.getLanguage());
				contentStream.newLineAtOffset(colWidth, 0);
				contentStream.showText(country.getIsOfficial());
				contentStream.endText();
				yPosition -= rowHeight;
			}

			contentStream.close();

			// Save and close document
			document.save(downloadPath + "/table.pdf");
			System.out.println();
			System.out.println("PDF Created: table.pdf");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new PDFBoxTableExample().getpdf("mr");
	}

	private InputStream getFontStream(String lang) throws IOException {
		InputStream fontStream = null;
		
			fontStream = getClass().getClassLoader().getResourceAsStream("fonts/NotoSans-Regular.ttf");
			if (fontStream == null) {
				throw new IOException("Font file not found in resources!");
			}
		
		return fontStream;
	}

}
