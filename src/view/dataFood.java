package view;

import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;
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
public class dataFood extends javax.swing.JFrame {  
    
    public final Connection conn = new koneksi().konek();
    
    String filepath;
    
    ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("logo.png"));
    
    private DefaultTableModel tabmode;
    
    private void title(){
        String sql = "select * from app where id='1'";
        try {
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String name = hasil.getString("name");
                String version = hasil.getString("version");
                this.setTitle(name + " " + version + " - " + "DATA FOOD");
            }
        } catch (Exception e) {
            this.setTitle("Warning : No Connection");
        }
    }
    
    private void aktif(){
        txtFoodId.setEnabled(false);
        txtCategories.setEnabled(true);
        txtFoodName.setEnabled(true);
        txtPrice.setEnabled(true);
        txtQty.setEnabled(true);
        txtDescription.setEnabled(true);
        btnSave.setEnabled(false);
        itemSave.setEnabled(false);
        btnUpdate.setEnabled(rootPaneCheckingEnabled);
        itemUpdate.setEnabled(rootPaneCheckingEnabled);
        btnErase.setEnabled(rootPaneCheckingEnabled);
        itemErase.setEnabled(rootPaneCheckingEnabled);
    }
    
    protected void kosong(){
        txtFoodId.setEnabled(false);
        txtCategories.setSelectedItem(null);
        txtFoodName.setText(null);
        txtPrice.setText(null);
        txtQty.setText(null);
        txtDescription.setText(null);
        btnSave.setEnabled(rootPaneCheckingEnabled);
        itemSave.setEnabled(rootPaneCheckingEnabled);
        btnUpdate.setEnabled(false);
        itemUpdate.setEnabled(false);
        btnErase.setEnabled(false);
        itemErase.setEnabled(false);
        txtCategories.requestFocus();
        id_auto();
    }
    
    public void noTable(){
        int Baris = tabmode.getRowCount();
        String s = Integer.toString(Baris);
        total.setText(s);
    }
    
    public void id_auto(){
        try {
            Statement stat = conn.createStatement();
            String sql = "select max(right(food_id, 4)) as no from food";
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                if (rs.first() == false) {
                    txtFoodId.setText("KFF0001");
                } else {
                    rs.last();
                    int set_id = rs.getInt(1)+1;
                    String no = String.valueOf(set_id);
                    int id_next = no.length();
                    for (int a = 0; a < 4 - id_next; a++) {
                        no = "0" + no;
                    }
                    txtFoodId.setText("KFF" + no);
                }
            }
        } catch (Exception e) {
        }
    }
    
    public void dataTable(){
        Object[] Baris = {"Food Id", "Categories", "Name", "Price", "Qty", "Description"};
        tabmode = new DefaultTableModel(null, Baris);
        tableFood.setModel(tabmode);
        String sql = "select * from food order by food_id";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {                
                String food_id = hasil.getString("food_id");
                String categories = hasil.getString("categories");
                String food_name = hasil.getString("food_name");
                String price = hasil.getString("price");
                String qty = hasil.getString("qty");
                String description = hasil.getString("description");
                String[] data = {food_id, categories, food_name, price, qty, description};
                tabmode.addRow(data);
                noTable();
            }
        } catch (Exception e) {
        }
    }
    
    public void lebarKolom(){
        TableColumn column;
        tableFood.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tableFood.getColumnModel().getColumn(0);
        column.setPreferredWidth(80);
        column = tableFood.getColumnModel().getColumn(1);
        column.setPreferredWidth(80);
        column = tableFood.getColumnModel().getColumn(2);
        column.setPreferredWidth(150);
        column = tableFood.getColumnModel().getColumn(3);
        column.setPreferredWidth(60);
        column = tableFood.getColumnModel().getColumn(4);
        column.setPreferredWidth(40);
        column = tableFood.getColumnModel().getColumn(5);
        column.setPreferredWidth(300);
    }
    
    public void cari (){
        String sqlPencarian = "select * from food where food_id like '%"+txtFind.getText()+"%' or name like '%"+txtFind.getText()+"%'";
        pencarian(sqlPencarian);
        lebarKolom();
    }
    
    public void pencarian (String sql) {
        Object[] Baris = {"Food Id", "Categories", "Name", "Price","Qty", "Description"};
        tabmode = new DefaultTableModel(null, Baris);
        tableFood.setModel(tabmode);
        int brs = tableFood.getRowCount();
        for (int i = 0; 1 < brs; i++){
            tabmode.removeRow(1);
        }
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                String food_id = hasil.getString("food_id");
                String categories = hasil.getString("categories");
                String food_name = hasil.getString("food_name");
                String price = hasil.getString("price");
                String qty = hasil.getString("qty");
                String description = hasil.getString("description");
                String[] data = {food_id, categories, food_name, price, qty, description};
                tabmode.addRow(data);
                noTable();
            }
        } catch (Exception e) {
        }
    }
    
    public dataFood(java.awt.Frame parent, boolean modal) {
        initComponents();
        title();
        kosong();
        dataTable();
        lebarKolom();
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

        jSeparator3 = new javax.swing.JSeparator();
        panelInventory = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        btnErase = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        foodId = new javax.swing.JLabel();
        txtFoodId = new javax.swing.JTextField();
        categories = new javax.swing.JLabel();
        txtCategories = new javax.swing.JComboBox<>();
        foodName = new javax.swing.JLabel();
        txtFoodName = new javax.swing.JTextField();
        price = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        description = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        qty = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableFood = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnFind = new javax.swing.JButton();
        txtFind = new javax.swing.JTextField();
        btnRefresh = new javax.swing.JButton();
        dataTotal = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuSystem = new javax.swing.JMenu();
        itemSettings = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
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
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelInventory.setPreferredSize(new java.awt.Dimension(580, 600));

        title.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        title.setText("FOOD DATA");

        javax.swing.GroupLayout panelInventoryLayout = new javax.swing.GroupLayout(panelInventory);
        panelInventory.setLayout(panelInventoryLayout);
        panelInventoryLayout.setHorizontalGroup(
            panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(panelInventoryLayout.createSequentialGroup()
                        .addGap(0, 278, Short.MAX_VALUE)
                        .addComponent(title)
                        .addGap(0, 279, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelInventoryLayout.setVerticalGroup(
            panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(panelInventory, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 60));

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnErase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/delete_24x24.png"))); // NOI18N
        btnErase.setText("ERASE");
        btnErase.setToolTipText("Deletes selected data");
        btnErase.setPreferredSize(new java.awt.Dimension(110, 60));
        btnErase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEraseActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/diskette_24x24.png"))); // NOI18N
        btnSave.setText("SAVE");
        btnSave.setToolTipText("Saves your data");
        btnSave.setMinimumSize(new java.awt.Dimension(75, 30));
        btnSave.setPreferredSize(new java.awt.Dimension(110, 60));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/edit_24x24.png"))); // NOI18N
        btnUpdate.setText("UPDATE");
        btnUpdate.setToolTipText("Changes selected data");
        btnUpdate.setPreferredSize(new java.awt.Dimension(110, 60));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/eraser_24x24.png"))); // NOI18N
        btnClear.setText("CLEAR");
        btnClear.setToolTipText("Clears the form");
        btnClear.setPreferredSize(new java.awt.Dimension(110, 60));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/printer_24x24.png"))); // NOI18N
        btnPrint.setText("PRINT");
        btnPrint.setToolTipText("Clears the form");
        btnPrint.setPreferredSize(new java.awt.Dimension(110, 60));
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
                .addGap(12, 12, 12)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnErase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnErase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnClear, btnErase, btnSave, btnUpdate});

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 610, 670, -1));

        jPanel1.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel1.setPreferredSize(new java.awt.Dimension(375, 325));

        foodId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        foodId.setText("Food Id");

        txtFoodId.setEnabled(false);
        txtFoodId.setPreferredSize(new java.awt.Dimension(200, 30));
        txtFoodId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFoodIdKeyPressed(evt);
            }
        });

        categories.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        categories.setText("Categories");
        categories.setPreferredSize(new java.awt.Dimension(64, 24));

        txtCategories.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Food", "Drink" }));
        txtCategories.setToolTipText("");
        txtCategories.setPreferredSize(new java.awt.Dimension(170, 30));

        foodName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        foodName.setText("Name");

        txtFoodName.setPreferredSize(new java.awt.Dimension(170, 30));
        txtFoodName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFoodNameKeyPressed(evt);
            }
        });

        price.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        price.setText("Price");

        txtPrice.setPreferredSize(new java.awt.Dimension(170, 30));
        txtPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPriceKeyPressed(evt);
            }
        });

        description.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        description.setText("Description");

        txtDescription.setColumns(5);
        txtDescription.setLineWrap(true);
        txtDescription.setRows(4);
        txtDescription.setToolTipText("");
        txtDescription.setWrapStyleWord(true);
        txtDescription.setMinimumSize(new java.awt.Dimension(170, 30));
        jScrollPane2.setViewportView(txtDescription);

        qty.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        qty.setText("Qty");

        txtQty.setPreferredSize(new java.awt.Dimension(170, 30));
        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQtyKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(foodId)
                    .addComponent(categories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(foodName)
                    .addComponent(price))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFoodId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCategories, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFoodName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(description)
                            .addComponent(qty))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtQty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtCategories, txtFoodId, txtFoodName, txtPrice});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFoodId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(foodId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCategories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(categories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFoodName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(foodName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(price)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(qty))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(description))))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtCategories, txtFoodId, txtFoodName, txtPrice});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {categories, description, foodId, foodName, price});

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 609, 180));

        tableFood.setModel(new javax.swing.table.DefaultTableModel(
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
        tableFood.setRowHeight(25);
        tableFood.setRowMargin(2);
        tableFood.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableFoodMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableFood);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 680, 280));

        btnFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/search_24x24.png"))); // NOI18N
        btnFind.setToolTipText("Finds data");
        btnFind.setFocusable(false);
        btnFind.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFind.setPreferredSize(new java.awt.Dimension(50, 30));
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

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/reload_24x24.png"))); // NOI18N
        btnRefresh.setPreferredSize(new java.awt.Dimension(100, 30));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        dataTotal.setText("Data Total :");

        total.setText("00");
        total.setToolTipText("Shows how many data existed");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(dataTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(total)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 276, Short.MAX_VALUE)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFind, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dataTotal)
                        .addComponent(total))
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 53, -1, 50));

        menuSystem.setText("System");

        itemSettings.setText("Settings");
        itemSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSettingsActionPerformed(evt);
            }
        });
        menuSystem.add(itemSettings);
        menuSystem.add(jSeparator4);

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

        jMenuBar1.add(menuSystem);

        menuInventory.setText("Inventory");
        menuInventory.setToolTipText("");

        itemFood.setText("Food");
        itemFood.setToolTipText("Food Page");
        itemFood.setEnabled(false);
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

        jMenuBar1.add(menuInventory);

        menuHelp.setText("Help");
        menuHelp.setToolTipText("");

        itemAbout.setText("About");
        itemAbout.setToolTipText("About App");
        itemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAboutActionPerformed(evt);
            }
        });
        menuHelp.add(itemAbout);

        jMenuBar1.add(menuHelp);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        kosong();
        dataTable();
        lebarKolom();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnEraseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEraseActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Do you want to " + "erase data", "Confirmation Dialog", JOptionPane.YES_NO_OPTION);
        if (ok==0) {
            String sql = "delete from food where food_id ='" + txtFoodId.getText()+"'";
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
        String sql = "update food set categories=?, food_name=?, price=?, qty=?, description=? where food_id = '" + txtFoodId.getText() + "'";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            stat.setObject(1, txtCategories.getSelectedItem());
            stat.setString(2, txtFoodName.getText());
            stat.setString(3, txtPrice.getText());
            stat.setString(4, txtQty.getText());
            stat.setString(5, txtDescription.getText());
            stat.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data Changed Succesfully");

            kosong();
            dataTable();
            lebarKolom();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data Invalid" + e);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (txtCategories.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(null, "Food Categories cannot be empty");
        } else if (txtFoodName.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Food Name cannot be empty");
        } else if (txtPrice.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Price cannot be empty");
        } else {
            String sql = "insert into food values (?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                
                stat.setString(1, txtFoodId.getText());
                stat.setObject(2, txtCategories.getSelectedItem());
                stat.setString(3, txtFoodName.getText());
                stat.setString(4, txtPrice.getText());
                stat.setString(5, txtQty.getText());
                stat.setString(6, txtDescription.getText());
                stat.executeUpdate();

                JOptionPane.showMessageDialog(null, "Data Saved Succesfully");

                kosong();
                dataTable();
                lebarKolom();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Data Invalid" + e);
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

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

    private void itemAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAdminActionPerformed
        dataAdmin da = new dataAdmin(this, rootPaneCheckingEnabled);
        da.setVisible(rootPaneCheckingEnabled);
        this.dispose();
    }//GEN-LAST:event_itemAdminActionPerformed

    private void itemSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSettingsActionPerformed
        settings s = new settings();
        s.setVisible(true);
    }//GEN-LAST:event_itemSettingsActionPerformed

    private void txtFoodIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoodIdKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtCategories.requestFocus();
        }
    }//GEN-LAST:event_txtFoodIdKeyPressed

    private void txtFoodNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoodNameKeyPressed
       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtPrice.requestFocus();
        }
    }//GEN-LAST:event_txtFoodNameKeyPressed

    private void txtPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriceKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtQty.requestFocus();
        }
    }//GEN-LAST:event_txtPriceKeyPressed

    private void txtQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtDescription.requestFocus();
        }
    }//GEN-LAST:event_txtQtyKeyPressed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        cari();
    }//GEN-LAST:event_btnFindActionPerformed

    private void txtFindKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFindKeyTyped
        cari();
    }//GEN-LAST:event_txtFindKeyTyped

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        dataTable();
        lebarKolom();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        try {
            HashMap parameter = new HashMap();
            Class.forName("com.mysql.jdbc.Driver");
            Connection cn = DriverManager.getConnection("jdbc:mysql:" + "///kff", "root", "");
            File file = new File("src/food.jasper");
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

    private void tableFoodMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableFoodMouseClicked
        try {
            int bar = tableFood.getSelectedRow();
            String a = tabmode.getValueAt(bar, 0).toString();
            String b = tabmode.getValueAt(bar, 1).toString();
            String c = tabmode.getValueAt(bar, 2).toString();
            String d = tabmode.getValueAt(bar, 3).toString();
            String e = tabmode.getValueAt(bar, 4).toString();
            String f = tabmode.getValueAt(bar, 5).toString();

            txtFoodId.setText(a);
            txtCategories.setSelectedItem(b);
            txtFoodName.setText(c);
            txtPrice.setText(d);
            txtQty.setText(e);
            txtDescription.setText(f);
            aktif();
        } catch (Exception e) {
        }
        
    }//GEN-LAST:event_tableFoodMouseClicked

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
            java.util.logging.Logger.getLogger(dataFood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dataFood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dataFood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dataFood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                dataFood dialog = new dataFood(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnErase;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel categories;
    private javax.swing.JLabel dataTotal;
    private javax.swing.JLabel description;
    private javax.swing.JLabel foodId;
    private javax.swing.JLabel foodName;
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
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenu menuInventory;
    private javax.swing.JMenu menuSystem;
    private javax.swing.JPanel panelInventory;
    private javax.swing.JLabel price;
    private javax.swing.JLabel qty;
    private javax.swing.JTable tableFood;
    private javax.swing.JLabel title;
    private javax.swing.JLabel total;
    private javax.swing.JComboBox<String> txtCategories;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtFind;
    private javax.swing.JTextField txtFoodId;
    private javax.swing.JTextField txtFoodName;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtQty;
    // End of variables declaration//GEN-END:variables
}