/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import client.ClientStub;
import protocol.SearchFlightsRequest;
import protocol.SearchFlightsResponse;
import model.flight;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingUtilities;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class MainFrame extends javax.swing.JFrame {

    private final ClientStub clientStub = new ClientStub();
    private DefaultTableModel tableModel;
    private List<flight> currentFlights;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainFrame.class.getName());

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        setupTable();
        setLocationRelativeTo(null);
        
        // Connect to server
        if (!clientStub.connect()) {
            JOptionPane.showMessageDialog(this,
                "Could not connect to server. Please start the server first.",
                "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupTable() {
        tableModel = new DefaultTableModel(new String[]{"Flight #", "From", "To", "Departure", "Arrival"}, 0);
        resultsTable.setModel(tableModel);
    }

    // this is the Search Logic
    private void searchFlights() {
        // Read directly from the dropdowns
        String origin = cmbOrigin.getSelectedItem().toString();
        String destination = cmbDestination.getSelectedItem().toString();

        tableModel.setRowCount(0); // Clear old results
        btnSearch.setEnabled(false);
        btnSearch.setText("Searching...");

        // Query the server asynchronously
        clientStub.searchFlights(origin, destination, response -> {
            SwingUtilities.invokeLater(() -> {
                btnSearch.setEnabled(true);
                btnSearch.setText("Search Flights");

                if (response.isSuccess()) {
                    currentFlights = response.getFlights();

                    if (currentFlights == null || currentFlights.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "No flights found for " + origin + " to " + destination);
                    } else {
                        for (flight f : currentFlights) {
                            tableModel.addRow(new Object[]{
                                f.getFlightNumber(),
                                f.getOrigin(),
                                f.getDestination(),
                                f.getDepartureTime(),
                                f.getArrivalTime()
                            });
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Search failed: " + response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        });
    }

    // this is the Booking Dialog Logic
    private void openBookingDialog() {
        int selectedRow = resultsTable.getSelectedRow();

        // to Safety check and make sure a row is actually selected
        if (selectedRow < 0 || currentFlights == null || currentFlights.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a valid flight first.");
            return;
        }

        // fpr getting the EXACT schedule ID from the saved flight object
        int actualScheduleId = currentFlights.get(selectedRow).getScheduleId();

        // Open the dialog and pass the real ID
        BookingDialog dialog = new BookingDialog(this, true, actualScheduleId);
        dialog.setVisible(true);
    }

    /*private void openBookingDialog() {
        int selectedRow = resultsTable.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        String flightNumber = (String) tableModel.getValueAt(selectedRow, 0);
        int scheduleId = Integer.parseInt(flightNumber.replaceAll("[^0-9]", "")); // to extract ID from flight number

        // For simplicity, we'll use schedule_id = selectedRow + 1
        // In production, you'd store the actual schedule_id in the table model
        BookingDialog dialog = new BookingDialog(this, true, selectedRow + 1);
        dialog.setVisible(true);
    }*/
//    private void openBookingDialog() {
//        int selectedRow = resultsTable.getSelectedRow();
//
//        // to safety check and make sure a row is actually selected
//        if (selectedRow < 0 || currentFlights == null || currentFlights.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Please select a valid flight first.");
//            return;
//        }
//
//        // to get the EXACT schedule ID from the saved flight object
//        int actualScheduleId = currentFlights.get(selectedRow).getScheduleId();
//
//        // to open the dialog and pass the real ID
//        BookingDialog dialog = new BookingDialog(this, true, actualScheduleId);
//        dialog.setVisible(true);
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblOrigin = new javax.swing.JLabel();
        lblDestination = new javax.swing.JLabel();
        btnBook = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        resultsTablejScrollPane = new javax.swing.JScrollPane();
        resultsTable = new javax.swing.JTable();
        cmbOrigin = new javax.swing.JComboBox<>();
        cmbDestination = new javax.swing.JComboBox<>();
        btnExit = new javax.swing.JButton();
        btnLogOut = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("🛫 Airline Reservation System - Entebbe 🛫 🛫 ");

        lblOrigin.setText("From :");

        lblDestination.setText("To :");

        btnBook.setText("Book Selected Flight");
        btnBook.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnBook.setEnabled(false);
        btnBook.setMargin(new java.awt.Insets(2, 8, 2, 8));
        btnBook.addActionListener(this::btnBookActionPerformed);

        btnSearch.setText("Search Flights");
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        btnCancel.setText("Cancel");
        btnCancel.setMargin(new java.awt.Insets(2, 6, 2, 6));
        btnCancel.addActionListener(this::btnCancelActionPerformed);

        resultsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Flight", "From", "To", "Departure", "Arrival"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        resultsTable.setRowHeight(25);
        resultsTable.setShowGrid(true);
        resultsTable.getSelectionModel().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting()) {
                btnBook.setEnabled(resultsTable.getSelectedRow() >= 0);
            }
        });
        resultsTablejScrollPane.setViewportView(resultsTable);

        cmbOrigin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entebbe", "Nairobi", "Kigali", "Dubai", "London" }));

        cmbDestination.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nairobi", "Kigali", "Dubai", "London", "New York", "Tokyo" }));

        btnExit.setText("Exit");
        btnExit.addActionListener(this::btnExitActionPerformed);

        btnLogOut.setText("LogOut");
        btnLogOut.addActionListener(this::btnLogOutActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(resultsTablejScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(17, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnLogOut)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnBook, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCancel)
                                .addGap(34, 34, 34)
                                .addComponent(btnExit)
                                .addGap(34, 34, 34))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(lblOrigin, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cmbOrigin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblDestination)
                .addGap(16, 16, 16)
                .addComponent(cmbDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSearch)
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblOrigin, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbOrigin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblDestination)
                        .addComponent(btnSearch)
                        .addComponent(cmbDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(resultsTablejScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBook)
                    .addComponent(btnExit)
                    .addComponent(btnCancel))
                .addGap(18, 18, 18)
                .addComponent(btnLogOut)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchFlights();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookActionPerformed
        openBookingDialog();
    }//GEN-LAST:event_btnBookActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        tableModel.setRowCount(0); // this clears the table
        cmbOrigin.setSelectedIndex(0); // for Reseting  to first item (Entebbe)
        cmbDestination.setSelectedIndex(0); // this is for Reseting to first item
        btnBook.setEnabled(false); // disables book button until a new selection of flight is made
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        this.dispose();
        new AuthLoginFrame().setVisible(true);
    }//GEN-LAST:event_btnLogOutActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void Main() {
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
        java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBook;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnExit;
    private javax.swing.JToggleButton btnLogOut;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbDestination;
    private javax.swing.JComboBox<String> cmbOrigin;
    private javax.swing.JLabel lblDestination;
    private javax.swing.JLabel lblOrigin;
    private javax.swing.JTable resultsTable;
    private javax.swing.JScrollPane resultsTablejScrollPane;
    // End of variables declaration//GEN-END:variables
}
