package view;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author khenj
 */
public class dataAdmin extends javax.swing.JFrame {  
    
    public final Connection conn = new koneksi().konek();
    
    ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("logo.png"));
    
    private DefaultTableModel tabmode;
    private DefaultTableModel tabmode2;
    
    private void title(){
        String sql = "select * from app where id='1'";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String name = hasil.getString("name");
                String version = hasil.getString("version");
                this.setTitle(name + " " + version + " - " + "DATA ADMIN");
            }
        } catch (Exception e) {
            this.setTitle("Warning : No Connection");
        }
    }
    
    private void aktif(){
        txtAdminId.setEnabled(false);;
        txtUsername.setEnabled(true);
        txtEmail.setEnabled(true);
        txtPassword.setEnabled(true);
        btnSave.setEnabled(false);
        itemSave.setEnabled(false);
        btnUpdate.setEnabled(rootPaneCheckingEnabled);
        itemUpdate.setEnabled(rootPaneCheckingEnabled);
        btnErase.setEnabled(rootPaneCheckingEnabled);
        itemErase.setEnabled(rootPaneCheckingEnabled);
    }
    
    protected void kosong(){
        txtAdminId.setEnabled(false);
        txtPassword.setEnabled(true);
        txtAdminId.setText(null);
        txtUsername.setText(null);
        txtEmail.setText(null);
        txtPassword.setText(null);
        btnSave.setEnabled(rootPaneCheckingEnabled);
        itemSave.setEnabled(rootPaneCheckingEnabled);
        btnUpdate.setEnabled(false);
        itemUpdate.setEnabled(false);
        btnErase.setEnabled(false);
        itemErase.setEnabled(false);
        txtUsername.requestFocus();
        id_auto();
    }
    
    public void id_auto(){
        try {
            Statement stat = conn.createStatement();
            String sql = "select max(right(admin_id, 3)) as no from admin";
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                if (rs.first() == false) {
                    txtAdminId.setText("KFFA001");
                } else {
                    rs.last();
                    int set_id = rs.getInt(1)+1;
                    String no = String.valueOf(set_id);
                    int id_next = no.length();
                    for (int a = 0; a < 4 - id_next; a++) {
                        no = "0" + no;
                    }
                    txtAdminId.setText("KFFA" + no);
                }
            }
        } catch (Exception e) {
        }
    }
    
    public void noTable(){
        int Baris = tabmode.getRowCount();
        String s = Integer.toString(Baris);
        total.setText(s);
    }
    
    public void dataTable(){
        Object[] Baris = {"Admin Id", "Username", "Email", "Password"};
        tabmode = new DefaultTableModel(null, Baris);
        tableAdmin.setModel(tabmode);
        String sql = "select * from admin order by admin_id asc";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {                
                String admin_id = hasil.getString("admin_id");
                String username = hasil.getString("username");
                String email = hasil.getString("email");
                String password = hasil.getString("password");
                String[] data = {admin_id, username, email, password};
                tabmode.addRow(data);
                noTable();
            }
        } catch (Exception e) {
        }
    }
    
    public void lebarKolom(){
        TableColumn column;
        tableAdmin.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tableAdmin.getColumnModel().getColumn(0);
        column.setPreferredWidth(80);
        column = tableAdmin.getColumnModel().getColumn(1);
        column.setPreferredWidth(175);
        column = tableAdmin.getColumnModel().getColumn(2);
        column.setPreferredWidth(175);
        column = tableAdmin.getColumnModel().getColumn(3);
        column.setPreferredWidth(120);
    }
    
    public void cari(){
        String sqlPencarian = "select * from admin where admin_id like '%"+txtFind.getText()+"%' or username like '%"+txtFind.getText()+"%'";
        pencarian(sqlPencarian);
        lebarKolom();
    }
    
    public void pencarian (String sql) {
        Object[] Baris = {"Admin Id", "Username", "Email", "Password"};
        tabmode = new DefaultTableModel(null, Baris);
        tableAdmin.setModel(tabmode);
        int brs = tableAdmin.getRowCount();
        for (int i = 0; 1 < brs; i++){
            tabmode.removeRow(1);
        }
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                String admin_id = hasil.getString("admin_id");
                String username = hasil.getString("username");
                String email = hasil.getString("email");
                String password = hasil.getString("password");
                String[] data = {admin_id, username, email, password};
                tabmode.addRow(data);
                noTable();
            }
        } catch (Exception e) {
        }
    }
    
    public dataAdmin(java.awt.Frame parent, boolean modal) {
        initComponents();
        kosong();
        dataTable();
        lebarKolom();
        title();
        this.setIconImage(logo.getImage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelInventory = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        dataTotal = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        btnFind = new javax.swing.JButton();
        txtFind = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        adminId = new javax.swing.JLabel();
        txtAdminId = new javax.swing.JTextField();
        userName = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        email = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        newPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnErase = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnRefresh = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAdmin = new javax.swing.JTable();
        jMenuBar = new javax.swing.JMenuBar();
        menuSystem = new javax.swing.JMenu();
        itemSettings = new javax.swing.JMenuItem();
        itemLogout = new javax.swing.JMenuItem();
        itemExit = new javax.swing.JMenuItem();
        menuInventory = new javax.swing.JMenu();
        itemFood = new javax.swing.JMenuItem();
        itemSales = new javax.swing.JMenuItem();
        itemAdmin = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        itemSave = new javax.swing.JMenuItem();
        itemUpdate = new javax.swing.JMenuItem();
        itemErase = new javax.swing.JMenuItem();
        itemClear = new javax.swing.JMenuItem();
        itemPrint = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        itemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("KFF V0.2");
        setResizable(false);
        setSize(new java.awt.Dimension(610, 590));
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelInventory.setMinimumSize(new java.awt.Dimension(580, 600));
        panelInventory.setPreferredSize(new java.awt.Dimension(580, 600));

        title.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        title.setText("ADMIN DATA");

        dataTotal.setText("Data Total :");

        total.setText("00");
        total.setToolTipText("Shows how many data existed");

        btnFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/search_24x24.png"))); // NOI18N
        btnFind.setToolTipText("Finds data");
        btnFind.setFocusable(false);
        btnFind.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFind.setPreferredSize(new java.awt.Dimension(75, 30));
        btnFind.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        txtFind.setPreferredSize(new java.awt.Dimension(170, 30));
        txtFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFindKeyTyped(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setPreferredSize(new java.awt.Dimension(375, 325));

        adminId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        adminId.setText("Admin Id");

        txtAdminId.setToolTipText("");
        txtAdminId.setEnabled(false);
        txtAdminId.setPreferredSize(new java.awt.Dimension(170, 30));
        txtAdminId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAdminIdKeyPressed(evt);
            }
        });

        userName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        userName.setText("Username");

        txtUsername.setToolTipText("");
        txtUsername.setPreferredSize(new java.awt.Dimension(170, 30));
        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsernameKeyPressed(evt);
            }
        });

        email.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        email.setText("Email");

        txtEmail.setPreferredSize(new java.awt.Dimension(170, 30));
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmailKeyPressed(evt);
            }
        });

        newPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        newPassword.setText("Password");

        txtPassword.setMinimumSize(new java.awt.Dimension(6, 30));
        txtPassword.setPreferredSize(new java.awt.Dimension(170, 30));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(email)
                    .addComponent(newPassword)
                    .addComponent(userName)
                    .addComponent(adminId))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtUsername, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtAdminId, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAdminId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adminId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(email))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtAdminId, txtEmail, txtPassword, txtUsername});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {adminId, email, newPassword, userName});

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/diskette_24x24.png"))); // NOI18N
        btnSave.setText("SAVE");
        btnSave.setToolTipText("Saves your data");
        btnSave.setMinimumSize(new java.awt.Dimension(75, 30));
        btnSave.setPreferredSize(new java.awt.Dimension(150, 30));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/edit_24x24.png"))); // NOI18N
        btnUpdate.setText("UPDATE");
        btnUpdate.setToolTipText("Changes selected data");
        btnUpdate.setPreferredSize(new java.awt.Dimension(150, 30));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnErase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/delete_24x24.png"))); // NOI18N
        btnErase.setText("ERASE");
        btnErase.setToolTipText("Deletes selected data");
        btnErase.setPreferredSize(new java.awt.Dimension(150, 30));
        btnErase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEraseActionPerformed(evt);
            }
        });

        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/eraser_24x24.png"))); // NOI18N
        btnClear.setText("CLEAR");
        btnClear.setToolTipText("Clears the form");
        btnClear.setPreferredSize(new java.awt.Dimension(150, 30));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/printer_24x24.png"))); // NOI18N
        btnPrint.setText("PRINT");
        btnPrint.setToolTipText("Clears the form");
        btnPrint.setPreferredSize(new java.awt.Dimension(150, 30));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnErase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnErase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnClear, btnErase, btnSave, btnUpdate});

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/reload_24x24.png"))); // NOI18N
        btnRefresh.setPreferredSize(new java.awt.Dimension(100, 30));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        tableAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableAdmin.setRowHeight(25);
        tableAdmin.setRowMargin(2);
        tableAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableAdminMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableAdmin);

        javax.swing.GroupLayout panelInventoryLayout = new javax.swing.GroupLayout(panelInventory);
        panelInventory.setLayout(panelInventoryLayout);
        panelInventoryLayout.setHorizontalGroup(
            panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventoryLayout.createSequentialGroup()
                .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelInventoryLayout.createSequentialGroup()
                        .addGap(235, 235, 235)
                        .addComponent(title))
                    .addGroup(panelInventoryLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelInventoryLayout.createSequentialGroup()
                                    .addComponent(dataTotal)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(total)
                                    .addGap(116, 116, 116)
                                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtFind, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelInventoryLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)
                        .addGap(7, 7, 7)))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        panelInventoryLayout.setVerticalGroup(
            panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInventoryLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dataTotal)
                            .addComponent(total))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(panelInventory, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 580, 540));

        menuSystem.setText("System");
        menuSystem.setToolTipText("");

        itemSettings.setText("Settings");
        itemSettings.setToolTipText("");
        itemSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSettingsActionPerformed(evt);
            }
        });
        menuSystem.add(itemSettings);

        itemLogout.setText("Logout");
        itemLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemLogoutActionPerformed(evt);
            }
        });
        menuSystem.add(itemLogout);

        itemExit.setText("Exit");
        itemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemExitActionPerformed(evt);
            }
        });
        menuSystem.add(itemExit);

        jMenuBar.add(menuSystem);

        menuInventory.setText("Inventory");
        menuInventory.setToolTipText("");

        itemFood.setText("Food");
        itemFood.setToolTipText("Food Page");
        itemFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemFoodActionPerformed(evt);
            }
        });
        menuInventory.add(itemFood);

        itemSales.setText("Sales");
        itemSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSalesActionPerformed(evt);
            }
        });
        menuInventory.add(itemSales);

        itemAdmin.setText("Admin");
        itemAdmin.setToolTipText("Admin Page");
        itemAdmin.setEnabled(false);
        itemAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAdminActionPerformed(evt);
            }
        });
        menuInventory.add(itemAdmin);
        menuInventory.add(jSeparator2);

        itemSave.setText("Save Data");
        itemSave.setToolTipText("Saves your data");
        itemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSaveActionPerformed(evt);
            }
        });
        menuInventory.add(itemSave);

        itemUpdate.setText("Update Data");
        itemUpdate.setToolTipText("Changes selected data");
        itemUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemUpdateActionPerformed(evt);
            }
        });
        menuInventory.add(itemUpdate);

        itemErase.setText("Erase Data");
        itemErase.setToolTipText("Deletes selected data");
        itemErase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemEraseActionPerformed(evt);
            }
        });
        menuInventory.add(itemErase);

        itemClear.setText("Clear Form");
        itemClear.setToolTipText("Clears the form");
        itemClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemClearActionPerformed(evt);
            }
        });
        menuInventory.add(itemClear);

        itemPrint.setText("Print Data");
        itemPrint.setToolTipText("Clears the form");
        itemPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPrintActionPerformed(evt);
            }
        });
        menuInventory.add(itemPrint);

        jMenuBar.add(menuInventory);

        menuHelp.setText("Help");

        itemAbout.setText("About");
        itemAbout.setToolTipText("About App");
        itemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAboutActionPerformed(evt);
            }
        });
        menuHelp.add(itemAbout);

        jMenuBar.add(menuHelp);

        setJMenuBar(jMenuBar);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void itemLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemLogoutActionPerformed
        login lg = new login();
        lg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_itemLogoutActionPerformed

    private void itemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAboutActionPerformed
        about ab = new about();
        ab.setVisible(true);
    }//GEN-LAST:event_itemAboutActionPerformed

    private void itemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_itemExitActionPerformed

    private void itemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSaveActionPerformed
        this.btnSaveActionPerformed(evt);
    }//GEN-LAST:event_itemSaveActionPerformed

    private void itemUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemUpdateActionPerformed
        this.btnUpdateActionPerformed(evt);
    }//GEN-LAST:event_itemUpdateActionPerformed

    private void itemEraseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemEraseActionPerformed
        this.btnEraseActionPerformed(evt);
    }//GEN-LAST:event_itemEraseActionPerformed

    private void itemClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemClearActionPerformed
        this.btnClearActionPerformed(evt);
    }//GEN-LAST:event_itemClearActionPerformed

    private void itemFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemFoodActionPerformed
        dataFood df = new dataFood(this, rootPaneCheckingEnabled);
        df.setVisible(rootPaneCheckingEnabled);
        this.dispose();
    }//GEN-LAST:event_itemFoodActionPerformed

    private void itemAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAdminActionPerformed

    }//GEN-LAST:event_itemAdminActionPerformed

    private void itemSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSettingsActionPerformed
        settings s = new settings();
        s.setVisible(true);
    }//GEN-LAST:event_itemSettingsActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        kosong();
        dataTable();
        lebarKolom();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnEraseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEraseActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Do you want to " + "erase data", "Confirmation Dialog", JOptionPane.YES_NO_OPTION);
        if (ok==0) {
            String sql = "delete from admin where admin_id ='" + txtAdminId.getText()+"'";
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null,"Data Deleted Succesfully");
                kosong();
                dataTable();
                lebarKolom();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Data Invalid"+ e);
            }
        }
    }//GEN-LAST:event_btnEraseActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        //cek data admin
        if (txtUsername.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Username cannot be empty!");
        } else if (txtEmail.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Email cannot be empty!");
        } else if (txtPassword.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Password cannot be empty!");
        } else {
                String sql = "update admin set username=?, email=?, password=? where admin_id = '" + txtAdminId.getText()+"'";
                try {
                    PreparedStatement stat = conn.prepareStatement(sql);

                    stat.setString(1, txtUsername.getText());
                    stat.setString(2, txtEmail.getText());
                    stat.setString(3, txtPassword.getText());
                    stat.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Data Changed Succesfully");

                    kosong();
                    dataTable();
                    lebarKolom();
                    txtUsername.requestFocus();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Data Invalid" + e);
                }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
            if (txtUsername.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty!");
            } else if (txtEmail.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Email cannot be empty!");
            } else if (txtPassword.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Password cannot be empty!");
            } else {
                String sql = "insert into admin values (?, ?, ?, ?)";
                try {
                    PreparedStatement stat = conn.prepareStatement(sql);

                    stat.setString(1, txtAdminId.getText());
                    stat.setString(2, txtUsername.getText());
                    stat.setString(3, txtEmail.getText());
                    stat.setString(4, txtPassword.getText());
                    stat.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Data Saved Succesfully");

                    kosong();
                    dataTable();
                    lebarKolom();
                    txtUsername.requestFocus();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Data Invalid" + e);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data Invalid, Check your ID!");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtFindKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFindKeyTyped
        cari();
    }//GEN-LAST:event_txtFindKeyTyped

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        cari();
    }//GEN-LAST:event_btnFindActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        dataTable();
        lebarKolom();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtPassword.requestFocus();
        }
    }//GEN-LAST:event_txtEmailKeyPressed

    private void txtUsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtEmail.requestFocus();
        }
    }//GEN-LAST:event_txtUsernameKeyPressed

    private void txtAdminIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdminIdKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtUsername.requestFocus();
        }
    }//GEN-LAST:event_txtAdminIdKeyPressed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        try {
            HashMap parameter = new HashMap();
            Class.forName("com.mysql.jdbc.Driver");
            Connection cn = DriverManager.getConnection("jdbc:mysql:" + "///kff", "root", "");
            File file = new File("src/admin.jasper");
            JasperReport jr = (JasperReport) JRLoader.loadObject(file);
            JasperPrint jp = JasperFillManager.fillReport(jr, parameter, cn);
            JasperViewer.viewReport(jp, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, 
                    "Data Tidak Dapat dicetak!!!" + "\n" + e.getMessage(), "Cetak Data", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void itemPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPrintActionPerformed
        btnPrintActionPerformed(evt);
    }//GEN-LAST:event_itemPrintActionPerformed

    private void itemSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSalesActionPerformed
        dataSales sa = new dataSales(this, rootPaneCheckingEnabled);
        sa.setVisible(rootPaneCheckingEnabled);
        this.dispose();
    }//GEN-LAST:event_itemSalesActionPerformed

    private void tableAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableAdminMouseClicked
        try {
            int bar = tableAdmin.getSelectedRow();
            String a = tabmode.getValueAt(bar, 0).toString();
            String b = tabmode.getValueAt(bar, 1).toString();
            String c = tabmode.getValueAt(bar, 2).toString();
            String d = tabmode.getValueAt(bar, 3).toString();

            txtAdminId.setText(a);
            txtUsername.setText(b);
            txtEmail.setText(c);
            txtPassword.setText(d);
            aktif();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tableAdminMouseClicked

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        title();
    }//GEN-LAST:event_formWindowGainedFocus

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
            java.util.logging.Logger.getLogger(dataAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dataAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dataAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dataAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                dataAdmin dialog = new dataAdmin(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e){
                    System.exit(0);
                }
            });
            dialog.setVisible(true); 
            }
        });
    }
        
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel adminId;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnErase;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel dataTotal;
    private javax.swing.JLabel email;
    private javax.swing.JMenuItem itemAbout;
    private javax.swing.JMenuItem itemAdmin;
    private javax.swing.JMenuItem itemClear;
    private javax.swing.JMenuItem itemErase;
    private javax.swing.JMenuItem itemExit;
    private javax.swing.JMenuItem itemFood;
    private javax.swing.JMenuItem itemLogout;
    private javax.swing.JMenuItem itemPrint;
    private javax.swing.JMenuItem itemSales;
    private javax.swing.JMenuItem itemSave;
    private javax.swing.JMenuItem itemSettings;
    private javax.swing.JMenuItem itemUpdate;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenu menuInventory;
    private javax.swing.JMenu menuSystem;
    private javax.swing.JLabel newPassword;
    private javax.swing.JPanel panelInventory;
    private javax.swing.JTable tableAdmin;
    private javax.swing.JLabel title;
    private javax.swing.JLabel total;
    private javax.swing.JTextField txtAdminId;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFind;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JLabel userName;
    // End of variables declaration//GEN-END:variables
}