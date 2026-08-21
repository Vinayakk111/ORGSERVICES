package com.app.vpk.docservice;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.printing.PDFPageable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PDFBoxTableExample {
	
	@Value("${app.download.path}")
	private String downloadPath;
	

	public String createSamplePDF() {
		
		String pdfPath = downloadPath+"/lang_document"+UUID.randomUUID()+".pdf";
		try {
			PDDocument document = new PDDocument();
			PDPage page = new PDPage();
			document.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(document, page);
			PDType0Font thyFont = PDType0Font.load(document,
					new File(downloadPath+"\\fonts\\NotoSansThai-Regular.ttf"));
			
			PDType0Font arielFont = PDType0Font.load(document, new File(downloadPath+"\\fonts\\arial.ttf"));
			
			PDType0Font korianFont = PDType0Font.load(document,
					new File(downloadPath+"\\fonts\\NotoSerifKR-Regular.ttf"));
			
			PDType0Font ChinesFont = PDType0Font.load(document,
					new File(downloadPath+"\\fonts\\NotoSansSC-Regular.ttf"));
			
			PDType0Font hindiFont = PDType0Font.load(document,
					new File(downloadPath+"\\fonts\\TiroDevanagariHindi-Regular.ttf"));

			contentStream.beginText();
			contentStream.setFont(thyFont, 14);
			contentStream.newLineAtOffset(100, 700);
			contentStream.showText("Hello");
			contentStream.newLineAtOffset(0, -20); // Move down for next line
			
			contentStream.setFont(arielFont, 14);
			contentStream.showText("Привіт! Як справи? Дуже добре, дякую!");
			contentStream.newLineAtOffset(0, -20); // Move down for next line
			
			contentStream.showText("Russian: Привет! Как дела? У меня всё хорошо, спасибо!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("German: Hallo! Wie geht es dir? Mir geht es gut, danke!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("French: Bonjour! Comment ça va? Je vais bien, merci!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("Spanish: ¡Hola! ¿Cómo estás? Estoy bien, gracias!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("Italian: Ciao! Come stai? Sto bene, grazie!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("Portuguese: Olá! Como você está? Estou bem, obrigado!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("Dutch: Hallo! Hoe gaat het? Het gaat goed, dank je!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("Turkish: Merhaba! Nasılsın? Ben iyiyim, teşekkür ederim!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("Greek: Γεια σου! Πώς είσαι; Είμαι καλά, ευχαριστώ!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("Hebrew: שלום! מה שלומך? אני בסדר, תודה!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("Vietnamese: Xin chào! Bạn khỏe không? Tôi khỏe, cảm ơn!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("Swahili: Habari! Hujambo? Niko sawa, asante!");
			contentStream.newLineAtOffset(0, -20);

			contentStream.showText("Arabic: مرحبا! كيف حالك؟ أنا بخير، شكرا لك!");
			contentStream.newLineAtOffset(0, -20);

			contentStream.showText("turkish  Gelecek sefer görüşürüz: See you next time");
			contentStream.newLineAtOffset(0, -20);

			contentStream.setFont(thyFont, 16);
			contentStream.showText("Thai: สวัสดี! คุณเป็นอย่างไรบ้าง? ฉันสบายดี ขอบคุณ!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.setFont(korianFont, 16);
			contentStream.showText("Korean: 안녕하세요! 잘 지내세요? 저는 잘 지내요, 감사합니다!");
			contentStream.newLineAtOffset(0, -20);

			contentStream.setFont(ChinesFont, 16);
			contentStream.showText("Chinese: 你好！你好吗？我很好，谢谢 asd！");
			contentStream.newLineAtOffset(0, -20);

			contentStream.setFont(hindiFont, 16);
			contentStream.showText(
					"Hindi: चूंकि मानव अधिकारों के प्रति उपेक्षा और घृणा के फलस्वरूप ही ऐसे बर्बर कार्य हुए जिनसे मनुष्य Happy");
			contentStream.newLineAtOffset(0, -20);

			contentStream.endText();
			contentStream.close();

			document.save(pdfPath);
			document.close();
			System.out.println("PDF Created: " + pdfPath);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdfPath;
	}
	
	public void getpdf(String pdfPath) throws IOException, PrinterException {
		java.io.File file = new File(pdfPath);
		PDDocument document = PDDocument.load(file);

		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPageable(new PDFPageable(document));

		job.print();
		document.close();

	}
	
	public static void main(String[] args) {

		try {
			PDDocument document = new PDDocument();
			PDPage page = new PDPage();
			document.addPage(page);

			
			PDPageContentStream contentStream = new PDPageContentStream(document, page);
			PDType0Font thyFont = PDType0Font.load(document,
					new File("E:\\data\\dev\\fonts\\NotoSansThai-Regular.ttf"));
			
			PDType0Font arielFont = PDType0Font.load(document, new File("C:\\Windows\\Fonts\\arial.ttf"));
			
			PDType0Font korianFont = PDType0Font.load(document,
					new File("E:\\data\\dev\\fonts\\NotoSerifKR-Regular.ttf"));
			
			PDType0Font ChinesFont = PDType0Font.load(document,
					new File("E:\\data\\dev\\fonts\\NotoSansSC-Regular.ttf"));
			
			PDType0Font hindiFont = PDType0Font.load(document,
					new File("E:\\data\\dev\\fonts\\TiroDevanagariHindi-Regular.ttf"));

			// 3. Write Polish Text
			contentStream.beginText();
			contentStream.setFont(thyFont, 14);
			contentStream.newLineAtOffset(100, 700);
			contentStream.showText("Hello");
			contentStream.newLineAtOffset(0, -20); // Move down for next line
			contentStream.setFont(arielFont, 14);
			contentStream.showText("Привіт! Як справи? Дуже добре, дякую!");
			contentStream.newLineAtOffset(0, -20); // Move down for next line
			
			contentStream.showText("Russian: Привет! Как дела? У меня всё хорошо, спасибо!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("German: Hallo! Wie geht es dir? Mir geht es gut, danke!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("French: Bonjour! Comment ça va? Je vais bien, merci!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("Spanish: ¡Hola! ¿Cómo estás? Estoy bien, gracias!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("Italian: Ciao! Come stai? Sto bene, grazie!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("Portuguese: Olá! Como você está? Estou bem, obrigado!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("Dutch: Hallo! Hoe gaat het? Het gaat goed, dank je!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("Turkish: Merhaba! Nasılsın? Ben iyiyim, teşekkür ederim!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("Greek: Γεια σου! Πώς είσαι; Είμαι καλά, ευχαριστώ!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("Hebrew: שלום! מה שלומך? אני בסדר, תודה!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("Vietnamese: Xin chào! Bạn khỏe không? Tôi khỏe, cảm ơn!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.showText("Swahili: Habari! Hujambo? Niko sawa, asante!");
			contentStream.newLineAtOffset(0, -20);

			contentStream.showText("Arabic: مرحبا! كيف حالك؟ أنا بخير، شكرا لك!");
			contentStream.newLineAtOffset(0, -20);

			contentStream.showText("turkish  Gelecek sefer görüşürüz: See you next time");
			contentStream.newLineAtOffset(0, -20);

			contentStream.setFont(thyFont, 16);
			contentStream.showText("Thai: สวัสดี! คุณเป็นอย่างไรบ้าง? ฉันสบายดี ขอบคุณ!");
			contentStream.newLineAtOffset(0, -20);
			
			contentStream.setFont(korianFont, 16);
			contentStream.showText("Korean: 안녕하세요! 잘 지내세요? 저는 잘 지내요, 감사합니다!");
			contentStream.newLineAtOffset(0, -20);

			contentStream.setFont(ChinesFont, 16);
			contentStream.showText("Chinese: 你好！你好吗？我很好，谢谢 asd！");
			contentStream.newLineAtOffset(0, -20);

			contentStream.setFont(hindiFont, 16);
			contentStream.showText(
					"Hindi: चूंकि मानव अधिकारों के प्रति उपेक्षा और घृणा के फलस्वरूप ही ऐसे बर्बर कार्य हुए जिनसे मनुष्य Happy");
			contentStream.newLineAtOffset(0, -20);

			contentStream.endText();
			contentStream.close();

			String pdfPath = "E:/data/dev/lang_document.pdf";
			document.save(pdfPath);
			document.close();
			System.out.println("PDF Created: " + pdfPath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
