package com.app.vpk.docservice;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.printing.PDFPageable;
import org.springframework.stereotype.Service;

@Service
public class PDFBoxTableExample {public static void main(String[] args) {

    try {
        // 1. Create PDF Document
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        // 2. Set Font with Polish Characters Support
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        PDType0Font font = PDType0Font.load(document, new File("C:/Windows/Fonts/arial.ttf")); // Ensure the font supports Polish

        // 3. Write Polish Text
        contentStream.beginText();
        contentStream.setFont(font, 14);
        contentStream.newLineAtOffset(100, 700);
        contentStream.showText("Witaj świecie! Żółć gęślą jaźń.");
        contentStream.newLineAtOffset(0, -20); // Move down for next line
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
        
        

//      contentStream.showText("Thai: สวัสดี! คุณเป็นอย่างไรบ้าง? ฉันสบายดี ขอบคุณ!");
//      contentStream.newLineAtOffset(0, -20);
//      contentStream.showText("Korean: 안녕하세요! 잘 지내세요? 저는 잘 지내요, 감사합니다!");
//      contentStream.newLineAtOffset(0, -20);
//      contentStream.showText("Chinese: 你好！你好吗？我很好，谢谢！");
//      contentStream.newLineAtOffset(0, -20);
        contentStream.endText();
        contentStream.close();

        // 4. Save PDF
        String pdfPath = "E:/data/dev/lang_document.pdf";
        document.save(pdfPath);
        document.close();
        System.out.println("PDF Created: " + pdfPath);

        // 5. Print the PDF
//        printPDF(pdfPath);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void getpdf(String pdfPath) throws IOException, PrinterException {
    java.io.File file = new File(pdfPath);
    PDDocument document = PDDocument.load(file);

    PrinterJob job = PrinterJob.getPrinterJob();
    job.setPageable(new PDFPageable(document));

    if (job.printDialog()) { // Show print dialog
        job.print();
    }
    document.close();

}}
