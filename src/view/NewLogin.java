/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DaoDatabase;
import dao.DaoLogin;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author nghia
 */
public class NewLogin extends javax.swing.JFrame {

    /**
     * Creates new form NewLogin
     */
    public NewLogin() {
        initComponents();
        init();
    }

    private void init() {
        rbSQL.setSelected(true);
        loadComboBoxDefDB(DaoDatabase.getList());
    }

    private void loadComboBoxDefDB(List<String> list) {
        jComboBoxDefDB.removeAllItems();
        for (String string : list) {
            jComboBoxDefDB.addItem(string);
        }
        jComboBoxDefDB.setSelectedItem("QLTV");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        rbWindows = new javax.swing.JRadioButton();
        rbSQL = new javax.swing.JRadioButton();
        jLabelPwd = new javax.swing.JLabel();
        jPasswordFieldPwd = new javax.swing.JPasswordField();
        jLabelConfirm = new javax.swing.JLabel();
        jPasswordFieldConfirm = new javax.swing.JPasswordField();
        jCheckBoxPolicy = new javax.swing.JCheckBox();
        jCheckBoxExpiration = new javax.swing.JCheckBox();
        jCheckBoxMustChange = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxDefDB = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login - New");
        setMaximumSize(new java.awt.Dimension(2000, 1000));
        setResizable(false);

        jPanel1.setPreferredSize(new java.awt.Dimension(600, 300));

        jLabel1.setText("Login name:");

        buttonGroup1.add(rbWindows);
        rbWindows.setText("Windows Authentication");
        rbWindows.setEnabled(false);

        buttonGroup1.add(rbSQL);
        rbSQL.setSelected(true);
        rbSQL.setText("SQL Server Authentication");
        rbSQL.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbSQLItemStateChanged(evt);
            }
        });

        jLabelPwd.setText("Password:");

        jLabelConfirm.setText("Confirm Password:");

        jCheckBoxPolicy.setSelected(true);
        jCheckBoxPolicy.setText("Enforce password policy");
        jCheckBoxPolicy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxPolicyItemStateChanged(evt);
            }
        });

        jCheckBoxExpiration.setSelected(true);
        jCheckBoxExpiration.setText("Enforce password expiration");
        jCheckBoxExpiration.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxExpirationItemStateChanged(evt);
            }
        });

        jCheckBoxMustChange.setSelected(true);
        jCheckBoxMustChange.setText("User must change password at next login");

        jLabel4.setText("Default database");

        jComboBoxDefDB.setMaximumRowCount(15);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelPwd)
                                    .addComponent(jLabelConfirm))
                                .addGap(145, 145, 145)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPasswordFieldPwd)
                                    .addComponent(jPasswordFieldConfirm)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBoxPolicy)
                                    .addComponent(jCheckBoxMustChange)
                                    .addComponent(jCheckBoxExpiration))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(177, 177, 177)
                        .addComponent(jComboBoxDefDB, 0, 205, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbWindows)
                            .addComponent(rbSQL))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(203, 203, 203)
                        .addComponent(jTextField1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbWindows)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbSQL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPwd)
                    .addComponent(jPasswordFieldPwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelConfirm)
                    .addComponent(jPasswordFieldConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxPolicy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxExpiration)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxMustChange)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxDefDB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setPreferredSize(new java.awt.Dimension(556, 40));

        jButton1.setText("OK");
        jButton1.setPreferredSize(new java.awt.Dimension(65, 28));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jPanel2.add(jButton1);

        jButton2.setText("Cancel");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jPanel2.add(jButton2);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbSQLItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rbSQLItemStateChanged
        // TODO add your handling code here:
        System.out.println("rbSQL select");
        if (rbSQL.isSelected()) {
            jLabelPwd.setEnabled(true);
            jLabelConfirm.setEnabled(true);
            jPasswordFieldConfirm.setEnabled(true);
            jPasswordFieldPwd.setEnabled(true);
            jCheckBoxExpiration.setEnabled(true);
            jCheckBoxMustChange.setEnabled(true);
            jCheckBoxPolicy.setEnabled(true);
        } else {
            jLabelPwd.setEnabled(false);
            jLabelConfirm.setEnabled(false);
            jPasswordFieldConfirm.setEnabled(false);
            jPasswordFieldPwd.setEnabled(false);
            jCheckBoxExpiration.setEnabled(false);
            jCheckBoxMustChange.setEnabled(false);
            jCheckBoxPolicy.setEnabled(false);
        }
    }//GEN-LAST:event_rbSQLItemStateChanged

    private void jCheckBoxPolicyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxPolicyItemStateChanged
        // TODO add your handling code here:
        System.out.println("chọn hoặc bỏ chọn");
        if (jCheckBoxPolicy.isSelected()) {
            jCheckBoxExpiration.setEnabled(true);
            jCheckBoxMustChange.setEnabled(true);

            jCheckBoxExpiration.setSelected(true);
            jCheckBoxMustChange.setSelected(true);
        } else {
            jCheckBoxExpiration.setEnabled(false);
            jCheckBoxMustChange.setEnabled(false);

            jCheckBoxExpiration.setSelected(false);
            jCheckBoxMustChange.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxPolicyItemStateChanged

    private void jCheckBoxExpirationItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxExpirationItemStateChanged
        // TODO add your handling code here:
        if (jCheckBoxExpiration.isSelected()) {
            jCheckBoxMustChange.setEnabled(true);

            jCheckBoxMustChange.setSelected(true);
        } else {
            jCheckBoxMustChange.setEnabled(false);

            jCheckBoxMustChange.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxExpirationItemStateChanged

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            this.dispose();
        }
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            String pw = new String(jPasswordFieldPwd.getPassword());
            String confirm = new String(jPasswordFieldConfirm.getPassword());
            String name=jTextField1.getText().trim();
            if (name.equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Login name is missing or empty!");
                return;
            }
            if (!pw.equals("") || !confirm.equals("")) {
                if (!pw.equals(confirm)) {
                    JOptionPane.showMessageDialog(rootPane, "Password and Confirm password do not match");
                    return;
                }
            }
            boolean policy = jCheckBoxPolicy.isSelected();
            boolean exp = jCheckBoxExpiration.isSelected();
            boolean mustChange=jCheckBoxMustChange.isSelected();
            String defDB=jComboBoxDefDB.getSelectedItem().toString();
            DaoLogin.insert(name, pw, defDB, policy, exp, mustChange);
        }
    }//GEN-LAST:event_jButton1MouseClicked

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBoxExpiration;
    private javax.swing.JCheckBox jCheckBoxMustChange;
    private javax.swing.JCheckBox jCheckBoxPolicy;
    private javax.swing.JComboBox<String> jComboBoxDefDB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelConfirm;
    private javax.swing.JLabel jLabelPwd;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordFieldConfirm;
    private javax.swing.JPasswordField jPasswordFieldPwd;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JRadioButton rbSQL;
    private javax.swing.JRadioButton rbWindows;
    // End of variables declaration//GEN-END:variables
}