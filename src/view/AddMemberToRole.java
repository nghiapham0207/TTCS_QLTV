/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DaoDatabaseRoles;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Member;

/**
 *
 * @author nghia
 */
public class AddMemberToRole extends javax.swing.JFrame {

    /**
     * Creates new form AddMemberToRole
     */
    private DefaultTableModel dtm;
    private List<Member> addMembers;
    private String roleName;

    public AddMemberToRole(String roleName) {
        initComponents();
        loadMembers(dao.DaoMembers.getListMemberToAdd());
        this.roleName = roleName;
        addMembers = new ArrayList<>();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    private void loadMembers(List<Member> list) {
        dtm = (DefaultTableModel) jTableMembers.getModel();
        dtm.setRowCount(0);
        for (Member member : list) {
            dtm.addRow(new Object[]{
                member.isSelect(),
                member.getName(),
                member.getType()
            });
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMembers = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButtonOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add members to database role");

        jLabel1.setText("Users, Database Roles");

        jTableMembers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "#", "Name", "Type"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableMembers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMembersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableMembers);
        if (jTableMembers.getColumnModel().getColumnCount() > 0) {
            jTableMembers.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTableMembers.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jButton1.setText("Cancel");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButtonOK.setText("OK");
        jButtonOK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonOKMouseClicked(evt);
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
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonOK, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButtonOK))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            this.dispose();
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButtonOKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonOKMouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            Member newMember;
            dtm = (DefaultTableModel) jTableMembers.getModel();
            for (int i = 0; i < dtm.getRowCount(); i++) {
                boolean select = (boolean) jTableMembers.getValueAt(i, 0);
                if (select) {
                    String name = (String) jTableMembers.getValueAt(i, 1);
                    String type = (String) jTableMembers.getValueAt(i, 2);
                    newMember = new Member(name, type, true);
                    System.out.println("chọn member " + newMember.getName());
                    addMembers.add(newMember);
                }
            }

            if (addMembers.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No member selected!");
                return;
            }
            System.out.println("danh sách member đã chọn:");
            for (Member addMember : addMembers) {
                System.out.println(addMember.getName());
            }
            DaoDatabaseRoles.addMember(roleName, addMembers);
            this.dispose();
        }
    }//GEN-LAST:event_jButtonOKMouseClicked

    private void jTableMembersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMembersMouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
//            int row = jTableMembers.getSelectedRow();
//            Member newMember;
//            if (row != -1) {
//                String name = (String) jTableMembers.getValueAt(row, 1);
//                String type = (String) jTableMembers.getValueAt(row, 2);
//                newMember = new Member(name, type, true);
//                boolean select = (boolean) jTableMembers.getValueAt(row, 0);
//                if (select) {
//                    System.out.println("chọn member " + newMember.getName());
//                    addMembers.add(newMember);
//                } else {
//                    for (int i = 0; i < addMembers.size(); i++) {
//                        if (addMembers.get(i).getName().equals(newMember.getName())) {
//                            addMembers.remove(i);
//                        }
//                    }
////                    if (addMembers.remove(newMember)) {
////                        System.out.println("bỏ chọn item " + newMember.getName());
////                    } else {
////                        System.out.println("không thể bỏ item " + newMember.getName());
////                    }
//                }
//            }
        }
    }//GEN-LAST:event_jTableMembersMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableMembers;
    // End of variables declaration//GEN-END:variables
}