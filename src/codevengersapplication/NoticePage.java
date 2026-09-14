/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package codevengersapplication;
import javax.swing.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Senzwa4
 */
public class NoticePage extends javax.swing.JFrame {
    private User currentUser; 
    private String currentCategory;
    private DefaultTableModel tableModel;
    private boolean isEditable = false;
    private java.util.List<Object> deletedRowIds = new java.util.ArrayList<>();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(NoticePage.class.getName());

    /**
     * Creates new form NoticePage
     */
    public NoticePage() {
        initComponents();
    }
    public NoticePage(User user) {
        initComponents();
        this.currentUser=user;
    }    
    private void loadAnnouncements(String category) {
        String tableName;
        switch(category.toLowerCase()) {
            case "academics": tableName = "announcements"; break;
            case "urgent": tableName = "urgent_announcements"; break;
            case "bursary": tableName = "bursaries"; break;
            case "event": tableName = "events"; break;
            default: return;
        }

        String sql = "SELECT * FROM " + tableName + " WHERE posted_by = ?";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dsdatabase",
                "root",
                "Parklane01$");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, currentUser.getUserId());
            ResultSet rs = ps.executeQuery();

            // Load data into JTable
            jTable1.setModel(buildTableModel(rs));

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }
    private void hideColumn(int colIndex) {
        if (jTable1.getColumnCount() > colIndex) {
            jTable1.getColumnModel().getColumn(colIndex).setMinWidth(0);
            jTable1.getColumnModel().getColumn(colIndex).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(colIndex).setWidth(0);
            jTable1.getColumnModel().getColumn(colIndex).setPreferredWidth(0);
        }
    }
    public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Column names
        Vector<String> columnNames = new Vector<>();
        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }

        // Data rows
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                vector.add(rs.getObject(i));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return isEditable;  // now correctly refers to instance variable
            }
        };
    }
    private String getTableFromCategory(String category) {
        return switch (category.toLowerCase()) {
            case "academics" -> "announcement";
            case "urgent" -> "urgent_announcements";
            case "bursary" -> "bursaries";
            case "event" -> "events";
            default -> null; // or throw an exception
        };
    }

    private void editSelectedRow() {
        int row = jTable1.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a row first!"); return; }
        // Cells are editable in table model, user can edit directly
        JOptionPane.showMessageDialog(this, "Edit directly in the table and click Save.");
    }
        private void saveTableChanges() {
        if (currentCategory == null) return;

        String tableName = getTableFromCategory(currentCategory);
        if (tableName == null) return;

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dsdatabase", "root", "Parklane01$")) {
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                // Assuming first column is ID
                Object id = tableModel.getValueAt(row, 0);
                String title = tableModel.getValueAt(row, 1).toString();
                String content = tableModel.getValueAt(row, 2).toString();

                String sql = "UPDATE " + tableName + " SET title = ?, content = ? WHERE id = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, title);
                    ps.setString(2, content);
                    ps.setObject(3, id);
                    ps.executeUpdate();
                }
            }
            JOptionPane.showMessageDialog(this, "Changes saved successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        btnBursary = new javax.swing.JButton();
        btnAcademic = new javax.swing.JButton();
        btnUrgent = new javax.swing.JButton();
        btnEvent = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnRestore = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnBursary.setText("Bursary");
        btnBursary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBursaryActionPerformed(evt);
            }
        });

        btnAcademic.setText("Announcements");
        btnAcademic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcademicActionPerformed(evt);
            }
        });

        btnUrgent.setText("Urgent");
        btnUrgent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUrgentActionPerformed(evt);
            }
        });

        btnEvent.setText("Event");
        btnEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEventActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAcademic)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUrgent, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBursary, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(230, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAcademic, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                        .addComponent(btnUrgent, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
                    .addComponent(btnBursary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEvent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnRestore.setText("Restore");
        btnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestoreActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jButton5.setText("Back To Home Page");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRestore)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete)
                    .addComponent(btnRestore)
                    .addComponent(btnSave))
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new HomeScreenStudentForm(currentUser).setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        // Assume the first column is always the ID column
        Object idValue = model.getValueAt(selectedRow, 0);

        if (idValue != null) {
            deletedRowIds.add(idValue); // keep track for DB deletion later
        }

        model.removeRow(selectedRow); // remove from table UI
        JOptionPane.showMessageDialog(this, "Row marked for deletion. Click Save to apply changes to the database.");  
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAcademicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcademicActionPerformed
        // TODO add your handling code here:
        currentCategory = "academics";
        loadAnnouncements("academics");
        hideColumn(0);
        hideColumn(4);
        hideColumn(3);
        hideColumn(5);
    }//GEN-LAST:event_btnAcademicActionPerformed

    private void btnUrgentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUrgentActionPerformed
        // TODO add your handling code here:
        currentCategory = "urgent";
        loadAnnouncements("urgent");
        hideColumn(0);
        hideColumn(4);
        hideColumn(3);
        hideColumn(5);
    }//GEN-LAST:event_btnUrgentActionPerformed

    private void btnBursaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBursaryActionPerformed
        // TODO add your handling code here:
        currentCategory = "bursary";
        loadAnnouncements("bursary");
        hideColumn(0);
        hideColumn(7);
        hideColumn(3);
        hideColumn(4);
        hideColumn(5);
        hideColumn(8);
    }//GEN-LAST:event_btnBursaryActionPerformed

    private void btnEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEventActionPerformed
        // TODO add your handling code here:
        currentCategory = "event";
        loadAnnouncements("event");
        hideColumn(0);
        hideColumn(6);
        hideColumn(3);
        hideColumn(5);
    }//GEN-LAST:event_btnEventActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        // Make all cells in the JTable editable
        isEditable = true;
        jTable1.repaint(); // refresh table to apply editable state
        JOptionPane.showMessageDialog(this, "Table is now editable!");     
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if (currentCategory == null) {
            JOptionPane.showMessageDialog(this, "Select a category first!");
            return;
        }

        // Ask user for confirmation
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to save all changes?\nThis will update the database.",
                "Confirm Save",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return; // cancel save if user says No
        }

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dsdatabase", "root", "Parklane01$")) {

            switch (currentCategory.toLowerCase()) {
                case "academics": { // Announcements
                    for (int row = 0; row < model.getRowCount(); row++) {
                        Object id = model.getValueAt(row, 0); // Announcement_ID
                        String title = model.getValueAt(row, 1).toString();
                        String content = model.getValueAt(row, 2).toString();

                        String sql = "UPDATE announcements SET Title = ?, Content = ? WHERE Announcement_ID = ?";
                        try (PreparedStatement ps = conn.prepareStatement(sql)) {
                            ps.setString(1, title);
                            ps.setString(2, content);
                            ps.setObject(3, id);
                            ps.executeUpdate();
                        }
                    }
                    break;
                }
                case "urgent": { // Urgent Announcements
                    for (int row = 0; row < model.getRowCount(); row++) {
                        Object id = model.getValueAt(row, 0); // urgent_id
                        String title = model.getValueAt(row, 1).toString();
                        String content = model.getValueAt(row, 2).toString();

                        String sql = "UPDATE urgent_announcements SET title = ?, content = ? WHERE urgent_id = ?";
                        try (PreparedStatement ps = conn.prepareStatement(sql)) {
                            ps.setString(1, title);
                            ps.setString(2, content);
                            ps.setObject(3, id);
                            ps.executeUpdate();
                        }
                    }
                    break;
                }
                case "bursary": { // Bursaries
                    for (int row = 0; row < model.getRowCount(); row++) {
                        Object id = model.getValueAt(row, 0); // bursary_id
                        String title = model.getValueAt(row, 1).toString();
                        String description = model.getValueAt(row, 2).toString();
                        String contactEmail = model.getValueAt(row, 6).toString();

                        String sql = "UPDATE bursaries SET title = ?, description = ?, contact_email = ? WHERE bursary_id = ?";
                        try (PreparedStatement ps = conn.prepareStatement(sql)) {
                            ps.setString(1, title);
                            ps.setString(2, description);
                            ps.setString(3, contactEmail);
                            ps.setObject(4, id);
                            ps.executeUpdate();
                        }
                    }
                    break;
                }
                case "event": { // Events
                    for (int row = 0; row < model.getRowCount(); row++) {
                        Object id = model.getValueAt(row, 0); // event_id
                        String title = model.getValueAt(row, 1).toString();
                        String description = model.getValueAt(row, 2).toString();
                        String location = model.getValueAt(row, 4).toString();

                        String sql = "UPDATE events SET title = ?, description = ?, location = ? WHERE event_id = ?";
                        try (PreparedStatement ps = conn.prepareStatement(sql)) {
                            ps.setString(1, title);
                            ps.setString(2, description);
                            ps.setString(3, location);
                            ps.setObject(4, id);
                            ps.executeUpdate();
                        }
                    }
                    break;
                }
                default:
                    JOptionPane.showMessageDialog(this, "Unknown category: " + currentCategory);
                    return;
            }

            // Handle deletions after updates
            for (Object id : deletedRowIds) {
                String tableName = switch (currentCategory.toLowerCase()) {
                    case "academics" -> "announcements";
                    case "urgent" -> "urgent_announcements";
                    case "bursary" -> "bursaries";
                    case "event" -> "events";
                    default -> null;
                };

                String idColumn = switch (currentCategory.toLowerCase()) {
                    case "academics" -> "Announcement_ID";
                    case "urgent" -> "urgent_id";
                    case "bursary" -> "bursary_id";
                    case "event" -> "event_id";
                    default -> null;
                };

                if (tableName != null && idColumn != null) {
                    String sql = "DELETE FROM " + tableName + " WHERE " + idColumn + " = ?";
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setObject(1, id);
                        ps.executeUpdate();
                    }
                }
            }
            deletedRowIds.clear();

            JOptionPane.showMessageDialog(this, "Changes saved successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestoreActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(this, 
        "Are you sure you want to restore? All unsaved changes will be lost.", 
        "Confirm Restore", 
        JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Reload data based on current category
            switch (currentCategory.toLowerCase()) {
                case "academics":
                    loadAnnouncements("academics");
                    hideColumn(0);
                    hideColumn(4);
                    hideColumn(3);
                    hideColumn(5);
                    break;
                case "urgent":
                    loadAnnouncements("urgent");
                    hideColumn(0);
                    hideColumn(4);
                    hideColumn(3);
                    hideColumn(5);
                    break;
                case "bursary":
                    loadAnnouncements("bursary");
                    hideColumn(0);
                    hideColumn(7);
                    hideColumn(3);
                    hideColumn(4);
                    hideColumn(5);
                    hideColumn(8);                    
                    break;
                case "event":
                    loadAnnouncements("event");
                    hideColumn(0);
                    hideColumn(6);
                    hideColumn(3);
                    hideColumn(5);                    
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "No category selected.");
                    return;
            }

            // Clear deleted IDs so they don't get applied on Save
            deletedRowIds.clear();

            JOptionPane.showMessageDialog(this, "Table restored successfully.");
        }
    }//GEN-LAST:event_btnRestoreActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new NoticePage().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcademic;
    private javax.swing.JButton btnBursary;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnEvent;
    private javax.swing.JButton btnRestore;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUrgent;
    private javax.swing.JButton jButton5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
