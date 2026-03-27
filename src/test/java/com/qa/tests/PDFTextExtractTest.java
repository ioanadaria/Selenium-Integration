package com.qa.tests;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;

/**
 * Downloads a sample PDF from the web and extracts its text using Apache PDFBox.
 * No browser required — pure Java test.
 */
public class PDFTextExtractTest {

    @Test
    public void extractTextFromSamplePDF() {
        String pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
        Path localPath = Path.of(System.getProperty("java.io.tmpdir"), "dummy.pdf");

        try {
            System.out.println("📥 Downloading PDF from: " + pdfUrl);

            URI uri = URI.create(pdfUrl);
            try (InputStream in = uri.toURL().openStream();
                 FileOutputStream out = new FileOutputStream(localPath.toFile())) {
                in.transferTo(out);
            }

            PDDocument document = PDDocument.load(localPath.toFile());

            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            System.out.println("===== Extracted PDF Text =====");
            System.out.println(text);
            System.out.println("==============================");

            document.close();

        } catch (IOException e) {
            System.out.println("⚠️ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
