package com.app.vpk.docservice;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PDFBoxTableExample {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
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
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(margin + 5, yPosition - 15);
            contentStream.showText("Column 1");
            contentStream.newLineAtOffset(colWidth, 0);
            contentStream.showText("Column 2");
            contentStream.newLineAtOffset(colWidth, 0);
            contentStream.showText("Column 3");
            contentStream.endText();

            // Add table rows
            String[][] data = {
                    {"Row 1 Col 1", "Row 1 Col 2", "Row 1 Col 3"},
                    {"Row 2 Col 1", "Row 2 Col 2", "Row 2 Col 3"},
                    {"Row 3 Col 1", "Row 3 Col 2", "Row 3 Col 3"},
                    {"Row 4 Col 1", "Row 4 Col 2", "Row 4 Col 3"},
                    {"Row 5 Col 1", "Row 5 Col 2", "Row 5 Col 3"}
            };

            yPosition -= rowHeight;
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            for (String[] row : data) {
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + 5, yPosition - 15);
                contentStream.showText(row[0]);
                contentStream.newLineAtOffset(colWidth, 0);
                contentStream.showText(row[1]);
                contentStream.newLineAtOffset(colWidth, 0);
                contentStream.showText(row[2]);
                contentStream.endText();
                yPosition -= rowHeight;
            }

            contentStream.close();

            // Save and close document
            document.save("table.pdf");
            System.out.println("PDF Created: table.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
