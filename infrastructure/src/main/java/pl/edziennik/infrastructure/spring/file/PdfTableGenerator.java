package pl.edziennik.infrastructure.spring.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for creating PDF document with a table
 */
@Slf4j
public class PdfTableGenerator {

    private final List<String> columns = new ArrayList<>();
    private final List<String> rows = new ArrayList<>();
    private String docTitle;

    private final List<String> leftParamNames = new ArrayList<>();
    private final List<String> leftParamValues = new ArrayList<>();
    private final List<String> rightFooterParamNames = new ArrayList<>();
    private final List<String> rightFooterParamValues = new ArrayList<>();

    public void setColumns(String... columns) {
        this.columns.addAll(Arrays.asList(columns));
    }

    public void setDocumentTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public void setRows(String... rows) {
        this.rows.addAll(Arrays.asList(rows));
    }

    public void addHeader(String paramName, String paramValue) {
        leftParamNames.add(paramName);
        leftParamValues.add(paramValue);
    }

    public void addFooter(String paramName, String paramValue) {
        rightFooterParamNames.add(paramName);
        rightFooterParamValues.add(paramValue);
    }

    public byte[] generate() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);

        try {
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            int marginDown = 15;
            int marginRight = 120;

            int pageHeight = (int) page.getTrimBox().getHeight();
            int width = (int) page.getTrimBox().getWidth();

            int initX = 10;
            int initY = pageHeight - 50;
            int cellHeight = 30;
            int cellWidth = 190;

            int offset = docTitle.length() * 3;

            // set document title
            contentStream.beginText();
            contentStream.newLineAtOffset(((float) width / 2 - offset), initY);
            contentStream.setFont(PDType1Font.COURIER_BOLD, 12);
            contentStream.showText(docTitle);
            contentStream.endText();

            initY -= 50;


            // set header values
            for (int i = 0; i < leftParamNames.size(); i++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(initX, initY);
                contentStream.setFont(PDType1Font.COURIER_BOLD, 8);
                contentStream.showText(leftParamNames.get(i));
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(initX + marginRight, initY);
                contentStream.setFont(PDType1Font.COURIER, 8);
                contentStream.showText(leftParamValues.get(i));
                contentStream.endText();

                initY -= marginDown;
            }

            // set columns
            for (String column : columns) {
                contentStream.addRect(initX, initY, cellWidth, -cellHeight);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_BOLD, 10);
                contentStream.newLineAtOffset(initX + 5, initY - cellHeight + 10);
                contentStream.showText(column.toUpperCase());
                contentStream.endText();

                initX += cellWidth;
            }

            initY -= cellHeight;
            initX = 10;

            // set rows
            int columnCounter = 0;
            for (String row : rows) {
                if (columnCounter == columns.size()) {
                    initY -= cellHeight;
                    initX = 10;
                    columnCounter = 0;
                }
                contentStream.addRect(initX, initY, cellWidth, -cellHeight);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 8);
                contentStream.newLineAtOffset(initX + 5, initY - cellHeight + 10);
                contentStream.showText(row.toUpperCase());
                contentStream.endText();
                initX += cellWidth;
                columnCounter++;
            }

            initY -= cellHeight;
            initX = width / 2 + 80;
            initY -= cellHeight;

            // set footer
            for (int i = 0; i < rightFooterParamNames.size(); i++) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
                contentStream.newLineAtOffset(initX + 5, initY - cellHeight + 10);
                contentStream.showText(rightFooterParamNames.get(i));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 10);
                contentStream.newLineAtOffset(initX + marginRight, initY - cellHeight + 10);
                contentStream.showText(rightFooterParamValues.get(i));
                contentStream.endText();
                initY -= marginDown;
            }

            contentStream.stroke();
            contentStream.close();

            document.save(outputStream);
            document.close();

            log.info("Successfully created PDF document");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
        }

        return outputStream.toByteArray();
    }

}
