package codevengersapplication;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author oweth
 */
public class AcademicsPageForm extends javax.swing.JFrame {
    private User currentUser;
    private void loadAnnouncements(javax.swing.JTextArea txtArea, String departmentFilter) {
        txtArea.setText(""); // Clear previous content
        // Assuming you have a JTextArea called txtAnnouncements
        txtArea.setLineWrap(true);          // Enable line wrap
        txtArea.setWrapStyleWord(true);     // Wrap at word boundaries, not mid-word
        txtArea.setEditable(false);         // Optional: make it read-only
        String url = "jdbc:mysql://localhost:3306/dsdatabase";
        String user = "root";
        String password = "Parklane01$";

        String sql = "SELECT a.department,a.Content, a.title, u.Full_name, u.Email, a.date_posted " +
                     "FROM announcements a " +
                     "JOIN users u ON a.posted_by = u.User_ID ";

        if(departmentFilter != null && !departmentFilter.isEmpty()) {
            sql += "WHERE a.department = ? ";
        }

        sql += "ORDER BY a.date_posted DESC";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if(departmentFilter != null && !departmentFilter.isEmpty()) {
                pstmt.setString(1, departmentFilter);
            }

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                String department = rs.getString("department");
                String announcement = rs.getString("title");
                String content = rs.getString("Content");
                String fullName = rs.getString("Full_name");
                String email = rs.getString("Email");
                String datePosted = rs.getString("date_posted");

                txtArea.append(
                    "Department: " + department + "\n" +
                    "Announcement: " + announcement + "\n" +
                    "Content: " + content + "\n" +
                    "Name: " + fullName + "\n" +
                    "Email: " + email + "\n" +
                    "Date: " + datePosted + "\n"+
                    "------------------------------------------------------------------------------------------------\n\n"
                );
            }

            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void filterAnnouncements() {
        String searchText = edtSearch.getText().trim().toLowerCase();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dsdatabase", "root", "Parklane01$")) {

            String sql = "SELECT a.title, a.content, a.date_posted, u.Full_name, u.Email, a.department " +
                         "FROM announcements a " +
                         "JOIN users u ON a.posted_by = u.User_ID " +
                         "WHERE a.title LIKE ?";

            PreparedStatement pst;

            if (currentUser != null && !"Administrative".equalsIgnoreCase(currentUser.getDepartment())) {
                // Non-admins: filter by their department
                sql += " AND a.department = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, "%" + searchText + "%");
                pst.setString(2, currentUser.getDepartment());
            } else{
                // Admins: no department filter
                pst = conn.prepareStatement(sql);
                pst.setString(1, "%" + searchText + "%");
            }

            ResultSet rs = pst.executeQuery();

            // Clear your JTextArea first
            jtxInfo.setText("");

            while (rs.next()) {
                jtxInfo.append("----------\n");
                jtxInfo.append("Department: " + rs.getString("department") + "\n");
                jtxInfo.append("Title: " + rs.getString("title") + "\n");
                jtxInfo.append("Content: " + rs.getString("content") + "\n");
                jtxInfo.append("Name: " + rs.getString("Full_name") + "\n");
                jtxInfo.append("Email: " + rs.getString("Email") + "\n");
                jtxInfo.append("Date: " + rs.getString("date_posted") + "\n");
                jtxInfo.append("----------\n\n");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }


    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AcademicsPageForm.class.getName());

    /**
     * Creates new form CategoriesPageForm
     */
    public AcademicsPageForm() {
        initComponents();    
        jList1.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {  // Avoid double firing
                String selectedDept = jList1.getSelectedValue(); // Get selected department
                loadAnnouncements(jtxInfo, selectedDept);
            }
        });       
        // Add the DocumentListener for the search field
        edtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterAnnouncements();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterAnnouncements();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterAnnouncements();
            }
        });
    }
    public AcademicsPageForm(User user){
        this();
        // Load announcements automatically based on user type        
        this.currentUser=user;
        if (currentUser != null) {
            if (!"Administrative".equalsIgnoreCase(currentUser.getDepartment())) {
                loadAnnouncements(jtxInfo, currentUser.getDepartment()); // Filter by course
                jLabel3.setVisible(false);
                jScrollPane1.setVisible(false);
                jToggleButton1.setVisible(false);
            } else{
                loadAnnouncements(jtxInfo,""); // Filter by department
            }
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
        jLabel2 = new javax.swing.JLabel();
        edtSearch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtxInfo = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("ACADEMICS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        edtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtSearchActionPerformed(evt);
            }
        });

        jLabel1.setText("Search Title");

        jList1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "General", "Information Technology", "Civil Engineering", "Chemical Engineering", "Mechanical Engineering", "Healthcare and Nursing" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel3.setText("Select Course");

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel5.setText("Announcements");

        jtxInfo.setColumns(20);
        jtxInfo.setRows(5);
        jScrollPane3.setViewportView(jtxInfo);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(172, 172, 172)
                .addComponent(jLabel5)
                .addContainerGap(178, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane2.setViewportView(jPanel3);

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setText("Back To Home Page");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jToggleButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jToggleButton1.setText("Remove filters");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(edtSearch)
                            .addComponent(jScrollPane1)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToggleButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton1)
                    .addComponent(jButton2))
                .addGap(9, 9, 9))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void edtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtSearchActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new HomeScreenStudentForm(currentUser).setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        edtSearch.setText("");
        loadAnnouncements(jtxInfo, "");        
    }//GEN-LAST:event_jToggleButton1ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new AcademicsPageForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField edtSearch;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JTextArea jtxInfo;
    // End of variables declaration//GEN-END:variables
}
