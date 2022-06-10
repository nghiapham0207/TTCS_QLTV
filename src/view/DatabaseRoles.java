/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DaoDatabase;
import dao.DaoDatabaseRoles;
import dao.DaoMembers;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nghia
 */
public class DatabaseRoles extends javax.swing.JInternalFrame {

    /**
     * Creates new form DatabaseRoles
     */
    private static DefaultTableModel dtm;
    public static String currentDB;

    public DatabaseRoles() {
        initComponents();
        init();
    }
    
    private void init(){
        loadComboBoxDefDB(DaoDatabase.getList());
        String dbName = (String) jComboBoxDB.getSelectedItem();
        loadDatabaseRoles(DaoDatabaseRoles.getList(dbName));
        currentDB = dbName;
    }

    public static void loadDatabaseRoles(List<String> list) {
        dtm = (DefaultTableModel) jTableDatabaseRoles.getModel();
        dtm.setRowCount(0);
        for (String string : list) {
            dtm.addRow(new Object[]{
                string
            });
        }
        jTableDatabaseRoles.setModel(dtm);
    }
    
    private void loadComboBoxDefDB(List<String> list) {
        jComboBoxDB.removeAllItems();
        for (String string : list) {
            jComboBoxDB.addItem(string);
        }
        jComboBoxDB.setSelectedItem("QLTV");
    }

    private void loadListMembersOfRole(List<String> list) {
        dtm = (DefaultTableModel) jTableListMembers.getModel();
        dtm.setRowCount(0);
        for (String string : list) {
            dtm.addRow(new Object[]{
                string
            });
        }
        jTableListMembers.setModel(dtm);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDatabaseRoles = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldRoleName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableListMembers = new javax.swing.JTable();
        jButtonRemoveMember = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButtonRefresh = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButtonNewRole = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxDB = new javax.swing.JComboBox<>();

        setClosable(true);
        setTitle("Database Roles");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jTableDatabaseRoles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Database Roles"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableDatabaseRoles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableDatabaseRoles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDatabaseRolesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableDatabaseRoles);

        jLabel2.setText("Role name:");

        jTextFieldRoleName.setEditable(false);

        jLabel3.setText("Members of this role:");

        jTableListMembers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Role Members"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableListMembers);

        jButtonRemoveMember.setText("Remove");
        jButtonRemoveMember.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonRemoveMemberMouseClicked(evt);
            }
        });

        jButton2.setText("Add");
        jButton2.setPreferredSize(new java.awt.Dimension(74, 28));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jButtonRefresh.setText("Refresh");
        jButtonRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonRefreshMouseClicked(evt);
            }
        });

        jButton1.setText("Grant");
        jButton1.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldRoleName))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                        .addComponent(jButtonRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRemoveMember)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldRoleName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRemoveMember)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRefresh)
                    .addComponent(jButton1)))
        );

        jButtonNewRole.setText("New Database Role");
        jButtonNewRole.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonNewRoleMouseClicked(evt);
            }
        });

        jLabel4.setText("Database");

        jComboBoxDB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxDBItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxDB, 0, 196, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonNewRole, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jButtonNewRole))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxDB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTableDatabaseRolesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDatabaseRolesMouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            int row = jTableDatabaseRoles.getSelectedRow();
            if (row != -1) {
                String roleName = (String) jTableDatabaseRoles.getValueAt(row, 0);
                jTextFieldRoleName.setText(roleName);
                loadListMembersOfRole(DaoMembers.getList(roleName, currentDB));
            }
        }
    }//GEN-LAST:event_jTableDatabaseRolesMouseClicked

    private void jButtonRemoveMemberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonRemoveMemberMouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            int row = jTableListMembers.getSelectedRow();
            if (row != -1) {
                int option = JOptionPane.showConfirmDialog(null, "Do you want to delete this member?");
                if (option == JOptionPane.YES_OPTION) {
                    String roleName = jTextFieldRoleName.getText().trim();
                    String memberName = (String) jTableListMembers.getValueAt(row, 0);
                    DaoDatabaseRoles.removeMember(roleName, memberName, currentDB);
                    loadListMembersOfRole(DaoMembers.getList(roleName, currentDB));
                }
            } else {
                JOptionPane.showMessageDialog(null, "No member selected!");
            }
        }
    }//GEN-LAST:event_jButtonRemoveMemberMouseClicked

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        System.out.println("Database Roles: " + evt.getID());
        Main.removeTab((JTabbedPane) getParent(), getClass().toString());
    }//GEN-LAST:event_formInternalFrameClosing

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            int row = jTableDatabaseRoles.getSelectedRow();
            if (row != -1) {
                String roleName = jTextFieldRoleName.getText().trim();
                AddMemberToRole addMemberToRole = new AddMemberToRole(roleName);
                addMemberToRole.setLocationRelativeTo(null);
                addMemberToRole.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "No Database Role selected!");
            }
        }
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButtonNewRoleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonNewRoleMouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            NewRole newRole = new NewRole();
            newRole.setLocationRelativeTo(null);
            newRole.setVisible(true);
        }
    }//GEN-LAST:event_jButtonNewRoleMouseClicked

    private void jButtonRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonRefreshMouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            String roleName = jTextFieldRoleName.getText().trim();
            loadListMembersOfRole(DaoMembers.getList(roleName, currentDB));
        }
    }//GEN-LAST:event_jButtonRefreshMouseClicked

    private void jComboBoxDBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxDBItemStateChanged
        // TODO add your handling code here:
        String dbName = (String) jComboBoxDB.getSelectedItem();
        loadDatabaseRoles(DaoDatabaseRoles.getList(dbName));
        currentDB = dbName;
    }//GEN-LAST:event_jComboBoxDBItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonNewRole;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JButton jButtonRemoveMember;
    private javax.swing.JComboBox<String> jComboBoxDB;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private static javax.swing.JTable jTableDatabaseRoles;
    private javax.swing.JTable jTableListMembers;
    private javax.swing.JTextField jTextFieldRoleName;
    // End of variables declaration//GEN-END:variables
}
