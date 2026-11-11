/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import client.ClientStub;
import protocol.GenerateReportRequest;
import protocol.GenerateReportResponse;
import PDFGenerator.PDFReportGenerator;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.util.List;

/**
 * Report Dialog - generates reports by fetching data from server via ClientStub,
 * then generates PDF locally using PDFReportGenerator.
 */
public class ReportDialog extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReportDialog.class.getName());

    // Client-side stub for async server communication
    private final ClientStub clientStub = new ClientStub();
    private final PDFReportGenerator pdfGen = new PDFReportGenerator();

    /**
     * Creates new form ReportDialog
     */
    public ReportDialog() {
        initComponents();
        setLocationRelativeTo(null);
        
        // Connect to server
        if (!clientStub.connect()) {
            JOptionPane.showMessageDialog(this,
                "Could not connect to server. Please start the server first.",
                "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Generate a report by fetching data from server and generating PDF locally.
     */
    private void generateReport(GenerateReportRequest.ReportType reportType, String buttonLabel) {
        // Disable the button that was clicked
        disableButton(buttonLabel);
        
        clientStub.generateReport(reportType, response -> {
            SwingUtilities.invokeLater(() -> {
                enableButton(buttonLabel);
                
                if (response.isSuccess()) {
                    // Generate PDF locally from server-provided data
                    pdfGen.generateReport(
                        response.getReportTitle(),
                        response.getHeaders(),
                        response.getRows(),
                        response.getFileName()
                    );
                    JOptionPane.showMessageDialog(this, 
                        "Report saved as: " + response.getFileName(), 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Failed to generate report: " + response.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        });
    }

    /**
     * Generate ticket receipt (requires passengerId and scheduleId).
     */
    private void generateTicketReport() {
        // Prompt for passenger ID and schedule ID
        String passengerIdStr = JOptionPane.showInputDialog(this, "Enter Passenger ID:", "Ticket Receipt", JOptionPane.QUESTION_MESSAGE);
        if (passengerIdStr == null || passengerIdStr.trim().isEmpty()) return;
        
        String scheduleIdStr = JOptionPane.showInputDialog(this, "Enter Schedule ID:", "Ticket Receipt", JOptionPane.QUESTION_MESSAGE);
        if (scheduleIdStr == null || scheduleIdStr.trim().isEmpty()) return;
        
        try {
            int passengerId = Integer.parseInt(passengerIdStr.trim());
            int scheduleId = Integer.parseInt(scheduleIdStr.trim());
            
            btnReportTickets.setEnabled(false);
            btnReportTickets.setText("Generating...");
            
            clientStub.generateReport(GenerateReportRequest.ReportType.TICKETS, passengerId, scheduleId, response -> {
                SwingUtilities.invokeLater(() -> {
                    btnReportTickets.setEnabled(true);
                    btnReportTickets.setText("6. Ticket Receipts");
                    
                    if (response.isSuccess()) {
                        // For ticket receipt, use the special generateTicketReceipt method
                        pdfGen.generateTicketReceipt(
                            response.getReportTitle(),
                            response.getRows(), // rows contain field-value pairs
                            response.getFileName()
                        );
                        JOptionPane.showMessageDialog(this, 
                            "Ticket receipt saved as: " + response.getFileName(), 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            "Failed to generate ticket: " + response.getMessage(), 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
            });
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblReportTitle = new javax.swing.JLabel();
        btnReportPassengers = new javax.swing.JButton();
        btnReportFlights = new javax.swing.JButton();
        btnReportAirports = new javax.swing.JButton();
        btnReportAircraft = new javax.swing.JButton();
        btnReportAirlines = new javax.swing.JButton();
        btnCloseReports = new javax.swing.JButton();
        btnReportTickets = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("📊 Entebbe Airlines - Administrative Reports");
        setResizable(false);

        lblReportTitle.setText("📊 Entebbe Airlines - Administrative Reports");

        btnReportPassengers.setText("1. Generate All Passengers Manifest");
        btnReportPassengers.addActionListener(this::btnReportPassengersActionPerformed);

        btnReportFlights.setText("2. Generate All Flight Schedules");
        btnReportFlights.addActionListener(this::btnReportFlightsActionPerformed);

        btnReportAirports.setText("3. Generate Airports Network");
        btnReportAirports.addActionListener(this::btnReportAirportsActionPerformed);

        btnReportAircraft.setText("4. Generate Aircraft Fleet Details");
        btnReportAircraft.addActionListener(this::btnReportAircraftActionPerformed);

        btnReportAirlines.setText("5. Generate Airlines Directory");
        btnReportAirlines.addActionListener(this::btnReportAirlinesActionPerformed);

        btnCloseReports.setText("Close Report Center");
        btnCloseReports.addActionListener(this::btnCloseReportsActionPerformed);

        btnReportTickets.setText("6. Ticket Receipts");
        btnReportTickets.addActionListener(this::btnReportTicketsActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReportPassengers)
                    .addComponent(btnReportAirports)
                    .addComponent(btnReportAirlines))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReportAircraft)
                    .addComponent(btnReportFlights, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReportTickets))
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblReportTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCloseReports, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(176, 176, 176))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnReportAircraft, btnReportAirlines, btnReportAirports, btnReportFlights, btnReportPassengers, btnReportTickets});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(lblReportTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(52, 52, 52)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnReportPassengers)
                        .addComponent(btnReportFlights))
                    .addGap(32, 32, 32)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnReportAirports)
                        .addComponent(btnReportAircraft))
                    .addGap(45, 45, 45)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnReportAirlines)
                        .addComponent(btnReportTickets))
                    .addGap(69, 69, 69)
                    .addComponent(btnCloseReports)
                    .addContainerGap(79, Short.MAX_VALUE)))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnReportAircraft, btnReportAirlines, btnReportAirports, btnReportFlights, btnReportPassengers, btnReportTickets});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseReportsActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseReportsActionPerformed

    private void btnReportPassengersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportPassengersActionPerformed
        generateReport(GenerateReportRequest.ReportType.PASSENGERS, "1. Generate All Passengers Manifest");
    }//GEN-LAST:event_btnReportPassengersActionPerformed

    private void btnReportFlightsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportFlightsActionPerformed
        generateReport(GenerateReportRequest.ReportType.FLIGHTS, "2. Generate All Flight Schedules");
    }//GEN-LAST:event_btnReportFlightsActionPerformed

    private void btnReportAirportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportAirportsActionPerformed
        generateReport(GenerateReportRequest.ReportType.AIRPORTS, "3. Generate Airports Network");
    }//GEN-LAST:event_btnReportAirportsActionPerformed

    private void btnReportAircraftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportAircraftActionPerformed
        generateReport(GenerateReportRequest.ReportType.AIRCRAFT, "4. Generate Aircraft Fleet Details");
    }//GEN-LAST:event_btnReportAircraftActionPerformed

    private void btnReportAirlinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportAirlinesActionPerformed
        generateReport(GenerateReportRequest.ReportType.AIRLINES, "5. Generate Airlines Directory");
    }//GEN-LAST:event_btnReportAirlinesActionPerformed

    private void btnReportTicketsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportTicketsActionPerformed
        generateTicketReport();
    }//GEN-LAST:event_btnReportTicketsActionPerformed

    private void btnCloseReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseReportsActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseReportsActionPerformed

    // Helper methods to disable/enable buttons during async operations
    private void disableButton(String buttonLabel) {
        switch (buttonLabel) {
            case "1. Generate All Passengers Manifest":
                btnReportPassengers.setEnabled(false);
                btnReportPassengers.setText("Generating...");
                break;
            case "2. Generate All Flight Schedules":
                btnReportFlights.setEnabled(false);
                btnReportFlights.setText("Generating...");
                break;
            case "3. Generate Airports Network":
                btnReportAirports.setEnabled(false);
                btnReportAirports.setText("Generating...");
                break;
            case "4. Generate Aircraft Fleet Details":
                btnReportAircraft.setEnabled(false);
                btnReportAircraft.setText("Generating...");
                break;
            case "5. Generate Airlines Directory":
                btnReportAirlines.setEnabled(false);
                btnReportAirlines.setText("Generating...");
                break;
            case "6. Ticket Receipts":
                btnReportTickets.setEnabled(false);
                btnReportTickets.setText("Generating...");
                break;
        }
    }

    private void enableButton(String buttonLabel) {
        switch (buttonLabel) {
            case "1. Generate All Passengers Manifest":
                btnReportPassengers.setEnabled(true);
                btnReportPassengers.setText("1. Generate All Passengers Manifest");
                break;
            case "2. Generate All Flight Schedules":
                btnReportFlights.setEnabled(true);
                btnReportFlights.setText("2. Generate All Flight Schedules");
                break;
            case "3. Generate Airports Network":
                btnReportAirports.setEnabled(true);
                btnReportAirports.setText("3. Generate Airports Network");
                break;
            case "4. Generate Aircraft Fleet Details":
                btnReportAircraft.setEnabled(true);
                btnReportAircraft.setText("4. Generate Aircraft Fleet Details");
                break;
            case "5. Generate Airlines Directory":
                btnReportAirlines.setEnabled(true);
                btnReportAirlines.setText("5. Generate Airlines Directory");
                break;
            case "6. Ticket Receipts":
                btnReportTickets.setEnabled(true);
                btnReportTickets.setText("6. Ticket Receipts");
                break;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new ReportDialog().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCloseReports;
    private javax.swing.JButton btnReportAircraft;
    private javax.swing.JButton btnReportAirlines;
    private javax.swing.JButton btnReportAirports;
    private javax.swing.JButton btnReportFlights;
    private javax.swing.JButton btnReportPassengers;
    private javax.swing.JButton btnReportTickets;
    private javax.swing.JLabel lblReportTitle;
    // End of variables declaration//GEN-END:variables
}