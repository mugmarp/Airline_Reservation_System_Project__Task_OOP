/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PDFGenerator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.io.File;
import java.util.List;

/**
 * PDF Report Generator - Client-side PDF generation from server-provided data.
 * No direct database access - receives data from server via ClientStub.
 */
public class PDFReportGenerator {

    private File reportDir = new File("src/generated_PDFs");

    /**
     * Generate a ticket receipt PDF from server-provided data.
     * @param reportTitle Title of the report (e.g., "TICKET RECEIPT")
     * @param headers Column headers (List of String arrays, typically 1 row with field names)
     * @param rows Data rows (List of String arrays)
     * @param fileName Output file name
     */
    public void generateReport(String reportTitle, List<String[]> headers, List<String[]> rows, String fileName) {
        try {
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }
            File targetFile = new File(reportDir, fileName);
            
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(targetFile));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            document.add(new Paragraph(reportTitle, titleFont));
            document.add(new Paragraph(" "));

            // Determine column count from headers
            int columns = headers.isEmpty() ? 0 : headers.get(0).length;
            if (columns == 0 && !rows.isEmpty()) {
                columns = rows.get(0).length;
            }

            if (columns > 0) {
                PdfPTable table = new PdfPTable(columns);
                table.setWidthPercentage(100);

                // Add Headers
                for (String[] headerRow : headers) {
                    for (String header : headerRow) {
                        PdfPCell cell = new PdfPCell(new Phrase(header));
                        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        table.addCell(cell);
                    }
                }

                // Add Data Rows
                for (String[] row : rows) {
                    for (String cellValue : row) {
                        table.addCell(cellValue != null ? cellValue : "");
                    }
                }
                document.add(table);
            }

            document.close();
            System.out.println(fileName + " generated successfully. Saved at: " + targetFile);
        } catch (Exception e) {
            System.err.println("Error generating " + fileName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Generate a simple key-value ticket receipt (field-value pairs).
     * Used for ticket receipts where data is field-value pairs.
     */
    public void generateTicketReceipt(String reportTitle, List<String[]> fields, String fileName) {
        try {
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }
            File targetFile = new File(reportDir, fileName);
            
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(targetFile));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            document.add(new Paragraph("ENTEBBE AIRLINES - " + reportTitle, titleFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            
            // Add field-value pairs
            for (String[] field : fields) {
                if (field.length >= 2) {
                    PdfPCell labelCell = new PdfPCell(new Phrase(field[0]));
                    labelCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(labelCell);
                    table.addCell(field[1]);
                }
            }
            
            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Status: CONFIRMED"));
            document.add(new Paragraph("Thank you for flying with us!"));

            document.close();
            System.out.println(fileName + " generated successfully. Saved at: " + targetFile);
        } catch (Exception e) {
            System.err.println("Error generating " + fileName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===== Legacy methods (not used in client-server mode, kept for reference) ====
    
    // @Deprecated - uses direct DB access, not for client-server mode
    public void generateTicketReceipt(int passengerId, int scheduleId) {
        System.err.println("WARNING: Legacy generateTicketReceipt uses direct DB access. Use generateReport() with server data instead.");
    }

    @Deprecated
    public void generateAllPassengersReport() {
        System.err.println("WARNING: Legacy method uses direct DB access.");
    }

    @Deprecated
    public void generateAllFlightsReport() {
        System.err.println("WARNING: Legacy method uses direct DB access.");
    }

    @Deprecated
    public void generateAirportsReport() {
        System.err.println("WARNING: Legacy method uses direct DB access.");
    }

    @Deprecated
    public void generateAircraftFleetReport() {
        System.err.println("WARNING: Legacy method uses direct DB access.");
    }

    @Deprecated
    public void generateAirlinesReport() {
        System.err.println("WARNING: Legacy method uses direct DB access.");
    }
}