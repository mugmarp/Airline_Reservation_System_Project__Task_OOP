/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PDFGenerator;

/**
 *
 * @author Administrator
 */
import DB.DBConnection;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.io.File;
import java.sql.*;

public class PDFReportGenerator {

    File reportDir = new File("src/generated_PDFs");

    // REPORT 1: Passenger Ticket Receipt
    public void generateTicketReceipt(int passengerId, int scheduleId) {
        try {
            Document document = new Document();

            File saveFile = new File(reportDir, "Ticket_Receipt_Passenger_" + passengerId + ".pdf");
            PdfWriter.getInstance(document, new FileOutputStream(saveFile));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            document.add(new Paragraph("ENTEBBE AIRLINES - TICKET RECEIPT", titleFont));
            document.add(new Paragraph(" "));

            Paragraph p = new Paragraph("Passenger ID: " + passengerId + " | Schedule ID: " + scheduleId);
            document.add(p);
            document.add(new Paragraph("Status: CONFIRMED"));
            document.add(new Paragraph("Thank you for flying with us!"));

            document.close();
            System.out.println("Ticket PDF generated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // REPORT 2: All Passengers List
    public void generateAllPassengersReport() {
        generateTableReport("All_Passengers_Report.pdf", "PASSENGER MANIFEST", "SELECT passenger_id, first_name, last_name, nationality, passport_number FROM passengers");
    }

    // REPORT 3: All Flights Schedule
    public void generateAllFlightsReport() {
        String sql = "SELECT f.flight_number, a1.city as origin, a2.city as dest, fs.departure_time "
                + "FROM flight_schedules fs JOIN flights f ON fs.flight_id = f.flight_id "
                + "JOIN airports a1 ON f.origin_airport = a1.airport_code "
                + "JOIN airports a2 ON f.dest_airport = a2.airport_code";
        generateTableReport("All_Flights_Report.pdf", "FLIGHT SCHEDULES", sql);
    }

    // REPORT 4: Airports & Routes Network
    public void generateAirportsReport() {
        generateTableReport("Airports_Network.pdf", "AIRPORTS & ROUTES", "SELECT airport_code, city, country FROM airports");
    }

    // REPORT 5: Aircraft Fleet Details
    public void generateAircraftFleetReport() {
        String sql = "SELECT a.model, a.total_capacity, al.name as airline_name " + "FROM aircrafts a JOIN airlines al ON a.airline_id = al.airline_id";
        generateTableReport("Aircraft_Fleet.pdf", "AIRCRAFT FLEET DETAILS", sql);
    }

    // REPORT 6: Airlines Directory
    public void generateAirlinesReport() {
        generateTableReport("Airlines_Directory.pdf", "REGISTERED AIRLINES", "SELECT airline_id, name, iata_code FROM airlines");
    }

    // --- HELPER METHOD TO AVOID REPEATING CODE ---
    private void generateTableReport(String fileName, String reportTitle, String sql) {

        // crete the folder if it does not exist
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
        // now combining the file name with directory
        File targetFILE = new File(reportDir, fileName);
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(targetFILE));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            document.add(new Paragraph(reportTitle, titleFont));
            document.add(new Paragraph(" "));

            try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

                ResultSetMetaData metaData = rs.getMetaData();
                int columns = metaData.getColumnCount();

                // Create PDF Table
                PdfPTable table = new PdfPTable(columns);
                table.setWidthPercentage(100);

                // Adding Headers
                for (int i = 1; i <= columns; i++) {
                    PdfPCell cell = new PdfPCell(new Phrase(metaData.getColumnName(i)));
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(cell);
                }

                // ADDING Data Rows
                while (rs.next()) {
                    for (int i = 1; i <= columns; i++) {
                        table.addCell(rs.getString(i));
                    }
                }
                document.add(table);
            }
            document.close();
            String message = (fileName + " generated successfully. Saved at :" + targetFILE);

            System.out.println(fileName + " generated successfully. Saved at :" + targetFILE);
        } catch (Exception e) {
            System.err.println("Error generating " + fileName + ": " + e.getMessage());
        }
    }
}
