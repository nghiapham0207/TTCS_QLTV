/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DaoRestore;
import java.awt.CardLayout;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import model.BackupSet;
import model.DatabaseFile;

/**
 *
 * @author nghia
 */
public class Restore extends javax.swing.JInternalFrame {

    /**
     * Creates new form Restore
     */
    private final CardLayout cl;
    private DefaultTableModel dtm;
    private final String defReLocateFile = "D:\\Program Files\\";

    public Restore() {
        initComponents();
        cl = (CardLayout) jPanel2.getLayout();
    }

    private void loadBackupSets(List<BackupSet> list) {
        dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);
        list.forEach(backupSet -> {
            dtm.addRow(new Object[]{
                backupSet.isRestore(),
                backupSet.getType(),
                backupSet.getPhysicalDeviceName(),
                backupSet.getDatabaseName(),
                backupSet.getPosition(),
                backupSet.getStartDate(),
                backupSet.getFinishDate(),});
        });
        jTable1.setModel(dtm);
    }

    public void loadComboBoxDBName(List<String> list) {
        if (!list.isEmpty()) {
            jComboBoxDBName.removeAllItems();
            jComboBoxDBName.removeAllItems();

            list.forEach((String q) -> {
                jComboBoxDBName.addItem(q);
            });
            jComboBoxDBName.setSelectedIndex(0);
        }
    }

    private void loadDBFiles(List<DatabaseFile> list) {
        if (list.isEmpty()) {
            return;
        }

        //restore into .mdf .ldf not .bak .trn
        //tomorrow i am going to fix
        dtm = (DefaultTableModel) jTableDBFiles.getModel();
        dtm.setRowCount(0);
        File restoreAs;
        String fileName = "";
        for (DatabaseFile databaseFile : list) {
            //get origin file to get path
            restoreAs = new File(databaseFile.getOriginalFileName());
            if (databaseFile.getFileType().equalsIgnoreCase("rows data")) {
                fileName = (String) jComboBoxDBDestination.getSelectedItem();
                fileName = fileName.concat(".mdf");
            }
            if (databaseFile.getFileType().equalsIgnoreCase("log")) {
                fileName = (String) jComboBoxDBDestination.getSelectedItem();
                fileName = fileName.concat("_log.ldf");
            }
            //set restore as use parent path of origin file
            databaseFile.setRestoreAs(restoreAs.getParent().concat("\\" + fileName));
            dtm.addRow(new Object[]{
                databaseFile.getLogicalName(),
                databaseFile.getFileType(),
                databaseFile.getOriginalFileName(),
                databaseFile.getRestoreAs()
            });
        }
//        list.forEach(databaseFile -> {
//            dtm.addRow(new Object[]{
//                databaseFile.getLogicalName(),
//                databaseFile.getFileType(),
//                databaseFile.getOriginalFileName(),
//                databaseFile.getRestoreAs()
//            });
//        });
        jTableDBFiles.setModel(dtm);
    }

    private void setTabFiles() {
        System.out.println("starting set tab Files");
        String pathDisk = jTextFieldDevice.getText().trim();
        System.out.println("load table database files");
        System.out.println("load file is only: " + pathDisk);
        if (!pathDisk.isEmpty()) {
            loadDBFiles(DaoRestore.getDBFiles(pathDisk));

            //set tab files
            //reset
            jTextFieldDataFile.setText("");
            jTextFieldLogFile.setText("");
            //set
            String dbDesName = (String) jComboBoxDBDestination.getSelectedItem();
            System.out.println("des db empty: " + dbDesName.isEmpty() + ". can not set tab Files!");
            if (!dbDesName.isEmpty()) {
                jTextFieldDataFile.setText(defReLocateFile + dbDesName + ".mdf");
                jTextFieldLogFile.setText(defReLocateFile + dbDesName + "_log.trn");
            } else {
                jTextFieldDataFile.setText(defReLocateFile);
                jTextFieldLogFile.setText(defReLocateFile);
            }
        }
    }

    public void restore() {
        String execStmt = "Use [master] ";
        String dbName = (String) jComboBoxDBDestination.getSelectedItem();
        String originDBName = (String) jComboBoxDBName.getSelectedItem();
        if (dbName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Destination database can NOT empty!");
            return;
        }
        int checkedCount = 0;
        boolean restore;
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            restore = (boolean) jTable1.getValueAt(i, 0);
            if (restore) {
                checkedCount++;
            }
        }
        String path;
        int position;
        String full;
        String diff;
        String log;

        for (int i = 0; i < jTable1.getRowCount(); i++) {
            restore = (boolean) jTable1.getValueAt(i, 0);
            if (restore) {
                path = (String) jTable1.getValueAt(i, 2);
                position = (int) jTable1.getValueAt(i, 4);
                String backupType = (String) jTable1.getValueAt(i, 1);
                switch (backupType) {
                    case "Full" -> {
                        full = " restore database [" + dbName
                                + "] from disk = N'" + path
                                + "' with file = " + position;

                        //write new destination db name
                        if (!dbName.equalsIgnoreCase(originDBName)) {
//                        if (!DaoRestore.checkExistDB(dbName)) {
                            setTabFiles();
                            String dataFile = (String) jTableDBFiles.getValueAt(0, 3);
                            String logFile = (String) jTableDBFiles.getValueAt(1, 3);
                            System.out.println("save new db: " + dataFile);
                            //
                            jTextFieldDataFile.setText(dataFile);
                            jTextFieldLogFile.setText(logFile);
                            jCheckBoxRelocate.setSelected(true);
                        }
                        //get event select
                        if (jCheckBoxRelocate.isSelected()) {
                            String dataFile = jTextFieldDataFile.getText().trim();
                            String logFile = jTextFieldLogFile.getText().trim();
                            String db = (String) jTableDBFiles.getValueAt(0, 0);
                            String dbLog = (String) jTableDBFiles.getValueAt(1, 0);

                            String move = ", move N'" + db + "' to N'" + dataFile + "' , "
                                    + " move N'" + dbLog + "' to N'" + logFile + "'";
                            full = full.concat(move);
                        }
                        if (i != checkedCount - 1) {
                            full = full.concat(", norecovery, nounload");
                        } else {
                            full = full.concat(", nounload");
                        }
                        if (jCheckBoxReplace.isSelected()) {
                            full = full.concat(", replace");
                        }
                        execStmt = execStmt.concat(full);
                    }
                    case "Differential" -> {
                        diff = " restore database [" + dbName
                                + "] from disk = N'" + path
                                + "' with file = " + position;
                        if (i != checkedCount - 1) {
                            diff = diff.concat(", norecovery, nounload");
                        } else {
                            diff = diff.concat(", nounload");
                        }
                        execStmt = execStmt.concat(diff);
                    }
                    case "Transaction Log" -> {
                        log = " restore log [" + dbName
                                + "] from disk = N'" + path
                                + "' with file = " + position;
                        if (i != checkedCount - 1) {
                            log = log.concat(", norecovery, nounload");
                        } else {
                            log = log.concat(", nounload");
                        }
                        execStmt = execStmt.concat(log);
                    }
                }
            }
        }
//        execStmt =execStmt.concat(" go");
        System.out.println(execStmt);
        DaoRestore.execNonQuery(execStmt);
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
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jRadioButton1 = new javax.swing.JRadioButton();
        jTextFieldDevice = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxDBName = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxDBDestination = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldTimeLine = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jCheckBoxRelocate = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldDataFile = new javax.swing.JTextField();
        jButtonBrowserData = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldLogFile = new javax.swing.JTextField();
        jButtonBrowserLog = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDBFiles = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jCheckBoxReplace = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButtonOK = new javax.swing.JButton();

        setClosable(true);
        setTitle("Restore Database");
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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setText("General");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton4.setText("Files");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });

        jButton8.setText("Options");
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setLayout(new java.awt.CardLayout());

        jLabel1.setText("Source");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Device:");

        jTextFieldDevice.setEditable(false);

        jButton2.setText("Browser");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jLabel2.setText("Database");

        jComboBoxDBName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxDBNameItemStateChanged(evt);
            }
        });

        jLabel3.setText("Destination");

        jLabel4.setText("Database:");

        jComboBoxDBDestination.setEditable(true);

        jButton3.setText("Single user");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jLabel5.setText("Restore to:");

        jTextFieldTimeLine.setEditable(false);

        jButton5.setText("Timeline...");
        jButton5.setEnabled(false);

        jLabel6.setText("Backup sets to restore:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Restore", "Backup type", "Physical device name", "Database name", "Position", "Backup start date", "Backup finish date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(60);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(70);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(70);
        }

        jLabel7.setText("Restore plan");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(jLabel2))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jRadioButton1)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxDBName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldDevice))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(7, 7, 7)
                                .addComponent(jSeparator2))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxDBDestination, 0, 495, Short.MAX_VALUE)
                                    .addComponent(jTextFieldTimeLine))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator3)))
                        .addContainerGap())))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton2, jButton3});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jTextFieldDevice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxDBName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxDBDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldTimeLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel3, "card2");

        jLabel9.setText("Restore database files as");

        jCheckBoxRelocate.setText("Relocate all files to folder");
        jCheckBoxRelocate.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxRelocateItemStateChanged(evt);
            }
        });

        jLabel10.setText("Data file folder:");

        jTextFieldDataFile.setText("D:\\Program Files\\");
            jTextFieldDataFile.setEnabled(false);

            jButtonBrowserData.setText("Browser");
            jButtonBrowserData.setEnabled(false);

            jLabel11.setText("Log file folder:");

            jTextFieldLogFile.setText("D:\\Program Files\\");
                jTextFieldLogFile.setEnabled(false);

                jButtonBrowserLog.setText("Browser");
                jButtonBrowserLog.setEnabled(false);

                jTableDBFiles.setModel(new javax.swing.table.DefaultTableModel(
                    new Object [][] {

                    },
                    new String [] {
                        "Logical File Name", "File Type", "Origin File Name", "Restore As"
                    }
                ) {
                    boolean[] canEdit = new boolean [] {
                        false, true, true, false
                    };

                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit [columnIndex];
                    }
                });
                jScrollPane2.setViewportView(jTableDBFiles);
                if (jTableDBFiles.getColumnModel().getColumnCount() > 0) {
                    jTableDBFiles.getColumnModel().getColumn(0).setPreferredWidth(120);
                    jTableDBFiles.getColumnModel().getColumn(0).setMaxWidth(120);
                    jTableDBFiles.getColumnModel().getColumn(1).setPreferredWidth(80);
                    jTableDBFiles.getColumnModel().getColumn(1).setMaxWidth(80);
                }

                javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                jPanel4.setLayout(jPanel4Layout);
                jPanel4Layout.setHorizontalGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator5))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBoxRelocate)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jTextFieldLogFile)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButtonBrowserLog))
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextFieldDataFile)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButtonBrowserData))))))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE))
                        .addContainerGap())
                );
                jPanel4Layout.setVerticalGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxRelocate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTextFieldDataFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonBrowserData))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jButtonBrowserLog)
                            .addComponent(jTextFieldLogFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                        .addContainerGap())
                );

                jPanel2.add(jPanel4, "card3");

                jLabel8.setText("Restore options:");

                jCheckBoxReplace.setText("Overwrite the existing database (WITH REPLACE)");

                jCheckBox2.setText("Preserve the replication settings (WITH KEEP_REPLICATION)");
                jCheckBox2.setEnabled(false);

                jCheckBox3.setText("Restrict access to the restored database (WITH RESTRICTED_USER)");
                jCheckBox3.setEnabled(false);

                javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
                jPanel6.setLayout(jPanel6Layout);
                jPanel6Layout.setHorizontalGroup(
                    jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator4)
                                .addContainerGap())
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox2)
                                    .addComponent(jCheckBoxReplace)
                                    .addComponent(jCheckBox3))
                                .addContainerGap(297, Short.MAX_VALUE))))
                );
                jPanel6Layout.setVerticalGroup(
                    jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxReplace)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox3)
                        .addContainerGap(248, Short.MAX_VALUE))
                );

                jPanel2.add(jPanel6, "card4");

                jButton6.setText("Cancel");
                jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        jButton6MouseClicked(evt);
                    }
                });

                jButtonOK.setText("OK");
                jButtonOK.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        jButtonOKMouseClicked(evt);
                    }
                });

                javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
                jPanel5.setLayout(jPanel5Layout);
                jPanel5Layout.setHorizontalGroup(
                    jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonOK)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6)
                        .addContainerGap())
                );

                jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton6, jButtonOK});

                jPanel5Layout.setVerticalGroup(
                    jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton6)
                            .addComponent(jButtonOK))
                        .addContainerGap())
                );

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                );
                layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                );

                pack();
            }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        System.out.println("Restore: " + evt.getID());
        Main.removeTab((JTabbedPane) getParent(), getClass().toString());
    }//GEN-LAST:event_formInternalFrameClosing

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            cl.show(jPanel2, "card2");
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            cl.show(jPanel2, "card3");
            setTabFiles();
        }
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            JFileChooser jfc = new JFileChooser("C:\\Program Files\\Microsoft SQL Server\\MSSQL12.MSSQLSERVER\\MSSQL\\Backup\\");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Backup Files(*.bak;*.trn)", "bak", "trn");
            jfc.setFileFilter(filter);
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jfc.setMultiSelectionEnabled(true);
            jfc.setToolTipText("Choose path or file to back up");
            jfc.setDialogTitle("Choose path or file to back up");

            int option = jfc.showOpenDialog(null);

            if (option == JFileChooser.APPROVE_OPTION) {
                File path = jfc.getSelectedFile();
                if (path.isFile()) {
                    jTextFieldDevice.setText(path.getPath());
//                    System.out.println(DaoRestore.getDBNameFromBak(path.getPath()));

                    loadComboBoxDBName(DaoRestore.getDBNameFromBak(path.getPath()));

                    String dbNameFromBak;
                    dbNameFromBak = (String) jComboBoxDBName.getSelectedItem();
                    if (dbNameFromBak != null) {
                        jComboBoxDBDestination.removeAllItems();
                        jComboBoxDBDestination.addItem(dbNameFromBak);
                        jComboBoxDBDestination.setSelectedItem(dbNameFromBak);
                        String dbName = (String) jComboBoxDBName.getSelectedItem();
                        System.out.println(path.getPath());
                        loadBackupSets(
                                DaoRestore.getBackupSets(dbName, path.getPath()));
                        int rowCount = jTable1.getRowCount();
                        System.out.println("row count: " + rowCount);
                        if (rowCount != 0) {
                            String startDate = (String) jTable1.getValueAt(rowCount - 1, 5);
                            jTextFieldTimeLine.setText(startDate);
                        }

                        //set tab files
//                        setTabFiles();
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            Main.removeTab((JTabbedPane) getParent(), getClass().toString());
            this.dispose();
        }
    }//GEN-LAST:event_jButton6MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            String dbName = (String) jComboBoxDBDestination.getSelectedItem();
            if (dbName != null) {
                DaoRestore.takeOffline(dbName);
            }
        }
    }//GEN-LAST:event_jButton3MouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            int row = jTable1.getSelectedRow();
            if (row != -1) {
                String startDate = (String) jTable1.getValueAt(row, 5);
                jTextFieldTimeLine.setText(startDate);
                boolean restore = (boolean) jTable1.getValueAt(row, 0);
                if (restore) {
                    for (int i = 0; i < row; i++) {
                        jTable1.setValueAt(true, i, 0);
                    }
                } else {
                    for (int i = row; i < jTable1.getRowCount(); i++) {
                        jTable1.setValueAt(false, i, 0);
                    }
                }
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            cl.show(jPanel2, "card4");
        }
    }//GEN-LAST:event_jButton8MouseClicked

    private void jComboBoxDBNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxDBNameItemStateChanged
        // TODO add your handling code here:
        evt.getItem();
        String dbNameFromBak = (String) jComboBoxDBName.getSelectedItem();
        if (dbNameFromBak != null) {
            jComboBoxDBDestination.removeAllItems();
            jComboBoxDBDestination.addItem(dbNameFromBak);
            String path = jTextFieldDevice.getText().trim();
            loadBackupSets(
                    DaoRestore.getBackupSets(dbNameFromBak, path));
            int rowCount = jTable1.getRowCount();
            System.out.println("row count: " + rowCount);
            if (rowCount != 0) {
                String startDate = (String) jTable1.getValueAt(rowCount - 1, 5);
                jTextFieldTimeLine.setText(startDate);
            }

            //set up tab files
//            dbNameFromBak = (String) jComboBoxDBName.getSelectedItem();
//            System.out.println("load table database file");
//            System.out.println("load file is only: "+dbNameFromBak);
//            if (dbNameFromBak != null) {
//                loadDBFiles(DaoRestore.getDBFiles(dbNameFromBak));
//            }
        }
    }//GEN-LAST:event_jComboBoxDBNameItemStateChanged

    private void jButtonOKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonOKMouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            if (jTable1.getRowCount() != 0) {
                boolean restore = (boolean) jTable1.getValueAt(0, 0);
                if (restore) {
                    restore();
                } else {
                    JOptionPane.showMessageDialog(null, "Choose position to restore!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No files to restore!");
            }
        }
    }//GEN-LAST:event_jButtonOKMouseClicked

    private void jCheckBoxRelocateItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxRelocateItemStateChanged
        // TODO add your handling code here:
        evt.getItem();
        if (jCheckBoxRelocate.isSelected()) {
            jTextFieldDataFile.setEnabled(true);
            jTextFieldLogFile.setEnabled(true);

            jButtonBrowserData.setEnabled(true);
            jButtonBrowserLog.setEnabled(true);
        } else {
            jTextFieldDataFile.setEnabled(false);
            jTextFieldLogFile.setEnabled(false);

            jButtonBrowserData.setEnabled(false);
            jButtonBrowserLog.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBoxRelocateItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButtonBrowserData;
    private javax.swing.JButton jButtonBrowserLog;
    private javax.swing.JButton jButtonOK;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBoxRelocate;
    private javax.swing.JCheckBox jCheckBoxReplace;
    private javax.swing.JComboBox<String> jComboBoxDBDestination;
    private javax.swing.JComboBox<String> jComboBoxDBName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTableDBFiles;
    private javax.swing.JTextField jTextFieldDataFile;
    private javax.swing.JTextField jTextFieldDevice;
    private javax.swing.JTextField jTextFieldLogFile;
    private javax.swing.JTextField jTextFieldTimeLine;
    // End of variables declaration//GEN-END:variables
}
