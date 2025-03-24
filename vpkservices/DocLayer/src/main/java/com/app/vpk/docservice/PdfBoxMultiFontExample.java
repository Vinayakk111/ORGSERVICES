package com.app.vpk.docservice;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.*;
import java.io.*;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.*;
import java.io.*;

public class PdfBoxMultiFontExample {
    public static void main(String[] args) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // ✅ Load English Font
            InputStream englishFontStream = PdfBoxMultiFontExample.class
                    .getClassLoader()
                    .getResourceAsStream("fonts/NotoSans-Regular.ttf");

            // ✅ Load Devanagari Font
            InputStream devanagariFontStream = PdfBoxMultiFontExample.class
                    .getClassLoader()
                    .getResourceAsStream("fonts/NotoSansDevanagari-Regular.ttf");

            if (englishFontStream == null || devanagariFontStream == null) {
                throw new IOException("Font files not found in resources!");
            }

            PDType0Font englishFont = PDType0Font.load(document, englishFontStream);
            PDType0Font devanagariFont = PDType0Font.load(document, devanagariFontStream);

            // ✅ Write English + Hindi/Marathi text using correct fonts
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            
            // Write English Text
            contentStream.setFont(englishFont, 14);
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Hello, this is English Text!");

            // Write Hindi/Marathi Text
            contentStream.setFont(devanagariFont, 14);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("नमस्ते, ही मराठी भाषा आहे!");

            contentStream.endText();
            contentStream.close();

            // ✅ Save & Close Document
            document.save("MultiLanguagePDF.pdf");
            document.close();
            System.out.println("✅ PDF created successfully with English + Hindi/Marathi!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
