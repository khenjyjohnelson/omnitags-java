package view;

import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
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
public class dataSales extends javax.swing.JFrame {  
    
    public final Connection conn = new koneksi().konek();
    
    ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("logo.png"));
    
    String filepath;
    
    private DefaultTableModel tabmode;
    private DefaultTableModel tabmode2;
    
    private void title(){
        String sql = "select * from app where id='1'";
        try {
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String name = hasil.getString("name");
                String version = hasil.getString("version");
                this.setTitle(name + " " + version + " - " + "DATA SALES");
            }
        } catch (Exception e) {
            this.setTitle("Warning : No Connection");
        }
    }
    
    private void aktif(){
        tableFood.setEnabled(false);
        txtSalesId.setEnabled(false);
        txtFoodName.setEnabled(false);
        txtFoodId.setEnabled(false);
        txtPrice.setEnabled(true);
        txtQty.setEnabled(false);
        txtTotal.setEnabled(true);
        txtDescription.setEnabled(true);
        btnSave.setEnabled(false);
        itemSave.setEnabled(false);
        btnUpdate.setEnabled(rootPaneCheckingEnabled);
        itemUpdate.setEnabled(rootPaneCheckingEnabled);
        btnErase.setEnabled(rootPaneCheckingEnabled);
        itemErase.setEnabled(rootPaneCheckingEnabled);
    }
    
    protected void kosong(){
        txtSalesId.setEnabled(false);
        txtQty.setEnabled(true);
        txtFoodId.setText(null);
        txtFoodName.setText(null);
        txtPrice.setText(null);
        txtQty.setText(null);
        txtTotal.setText(null);
        txtDescription.setText(null);
        btnSave.setEnabled(rootPaneCheckingEnabled);
        itemSave.setEnabled(rootPaneCheckingEnabled);
        btnUpdate.setEnabled(false);
        itemUpdate.setEnabled(false);
        btnErase.setEnabled(false);
        itemErase.setEnabled(false);
        id_auto();
    }
    
    public final void kali() throws NumberFormatException{
        if(txtPrice.getText()=="" && txtQty.getText()==""){
            txtTotal.setText(null);
        } else {
            try {
                int prc = Integer.parseInt(txtPrice.getText());
                int qt = Integer.parseInt(txtQty.getText());
                Integer ttl = prc * qt;
                String result = String.valueOf(ttl);

                txtTotal.setText(result);
            } catch (Exception e) {
            }
        }
    }
    
    public void date(){
        Date tgl = new Date();
        txtSalesDate.setDate(tgl);
    }
    
    public void noTable(){
        int Baris = tabmode.getRowCount();
        String s = Integer.toString(Baris);
        total.setText(s);
    }
    
    public void id_auto(){
        try {
            Statement stat = conn.createStatement();
            String sql = "select max(right(sales_id, 4)) as no from sales";
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                if (rs.first() == false) {
                    txtSalesId.setText("S0001");
                } else {
                    rs.last();
                    int set_id = rs.getInt(1)+1;
                    String no = String.valueOf(set_id);
                    int id_next = no.length();
                    for (int a = 0; a < 4 - id_next; a++) {
                        no = "0" + no;
                    }
                    txtSalesId.setText("S" + no);
                }
            }
        } catch (Exception e) {
        }
    }
    
    public void dataTable(){
        Object[] Baris = {"Sales Id", "Sales Date", "Food Id", "Food Name", "Price", "Qty", "Total", "Description"};
        tabmode = new DefaultTableModel(null, Baris);
        tableSales.setModel(tabmode);
        String sql = "select * from sales order by sales_id asc";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String sales_id = hasil.getString("sales_id");
                String sales_date = hasil.getString("sales_date");
                String food_id = hasil.getString("food_id");
                String food_name = hasil.getString("food_name");
                String price = hasil.getString("price");
                String qty = hasil.getString("qty");
                String total = hasil.getString("total");
                String description = hasil.getString("description");
                String[] data = {sales_id, sales_date, food_id, food_name, price, qty, total, description};
                tabmode.addRow(data);
                noTable();
            }
        } catch (Exception e) {
        }
    }
    
    public void dataTable2(){
        Object[] Baris2 = {"Food ID","Food Name", "Price"};
        tabmode2 = new DefaultTableModel(null, Baris2);
        tableFood.setModel(tabmode2);
        String sql = "select * from food order by food_id asc";
        try{
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                String food_id = hasil.getString("food_id");
                String food_name = hasil.getString("food_name");
                String price = hasil.getString("price");
                String[] data = {food_id, food_name, price};
                tabmode2.addRow(data);
            }
        } catch (Exception e){
        }
    }
    
    public void lebarKolom(){
        TableColumn column;
        tableSales.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tableSales.getColumnModel().getColumn(0);
        column.setPreferredWidth(80);
        column = tableSales.getColumnModel().getColumn(1);
        column.setPreferredWidth(100);
        column = tableSales.getColumnModel().getColumn(2);
        column.setPreferredWidth(80);
        column = tableSales.getColumnModel().getColumn(3);
        column.setPreferredWidth(150);
        column = tableSales.getColumnModel().getColumn(4);
        column.setPreferredWidth(60);
        column = tableSales.getColumnModel().getColumn(5);
        column.setPreferredWidth(40);
        column = tableSales.getColumnModel().getColumn(6);
        column.setPreferredWidth(60);
        column = tableSales.getColumnModel().getColumn(7);
        column.setPreferredWidth(300);
    }
    
    public void lebarKolom2(){
        TableColumn column;
        tableFood.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tableFood.getColumnModel().getColumn(0);
        column.setPreferredWidth(80);
        column = tableFood.getColumnModel().getColumn(1);
        column.setPreferredWidth(200);
        column = tableFood.getColumnModel().getColumn(2);
        column.setPreferredWidth(100);
    }
    
    public void cari (){
        String sqlPencarian = "select * from sales where sales_id like '%"+txtFind.getText()+"%' or food_name like '%"+txtFind.getText()+"%'";
        pencarian(sqlPencarian);
        lebarKolom();
        lebarKolom2();
    }
    
    public void pencarian (String sql) {
        Object[] Baris = {"Sales Id", "Sales Date", "Food Id", "Food Name", "Price","Qty", "Total", "Description"};
        tabmode = new DefaultTableModel(null, Baris);
        tableSales.setModel(tabmode);
        int brs = tableSales.getRowCount();
        for (int i = 0; 1 < brs; i++){
            tabmode.removeRow(1);
        }
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                String sales_id = hasil.getString("sales_id");
                String sales_date = hasil.getString("sales_date");
                String food_id = hasil.getString("food_id");
                String food_name = hasil.getString("food_name");
                String price = hasil.getString("price");
                String qty = hasil.getString("qty");
                String total = hasil.getString("total");
                String description = hasil.getString("description");
                String[] data = {sales_id, sales_date, food_id, food_name, price, qty, total, description};
                tabmode.addRow(data);
                noTable();
            }
        } catch (Exception e) {
        }
    }
    
    public dataSales(java.awt.Frame parent, boolean modal) {
        initComponents();
        title();
        kosong();
        dataTable();
        dataTable2();
        date();
        lebarKolom();
        lebarKolom2();
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
        dataTotal = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        foodId = new javax.swing.JLabel();
        txtSalesId = new javax.swing.JTextField();
        categories = new javax.swing.JLabel();
        FoodId = new javax.swing.JLabel();
        txtFoodId = new javax.swing.JTextField();
        price = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        description = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        qty = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        chooseFood = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableFood = new javax.swing.JTable();
        foodName = new javax.swing.JLabel();
        txtFoodName = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        btnTotal = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnErase = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        txtFind = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableSales = new javax.swing.JTable();
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
        title.setText("SALES DATA");

        dataTotal.setText("Data Total :");

        total.setText("00");
        total.setToolTipText("Shows how many data existed");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setPreferredSize(new java.awt.Dimension(375, 325));

        foodId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        foodId.setText("Sales Id");

        txtSalesId.setEnabled(false);
        txtSalesId.setPreferredSize(new java.awt.Dimension(200, 30));
        txtSalesId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSalesIdKeyPressed(evt);
            }
        });

        categories.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        categories.setText("Date");
        categories.setPreferredSize(new java.awt.Dimension(64, 24));

        FoodId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        FoodId.setText("Food Id");

        txtFoodId.setEnabled(false);
        txtFoodId.setPreferredSize(new java.awt.Dimension(170, 30));
        txtFoodId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFoodIdKeyPressed(evt);
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

        chooseFood.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        chooseFood.setText("Choose Food");

        tableFood.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tableFood.setRowMargin(2);
        tableFood.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableFoodMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableFood);

        foodName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        foodName.setText("Name");

        txtFoodName.setEnabled(false);
        txtFoodName.setPreferredSize(new java.awt.Dimension(170, 30));
        txtFoodName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFoodNameKeyPressed(evt);
            }
        });

        txtTotal.setPreferredSize(new java.awt.Dimension(170, 30));
        txtTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTotalKeyPressed(evt);
            }
        });

        btnTotal.setText("Total");
        btnTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTotalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chooseFood)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 7, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(foodId)
                            .addComponent(FoodId)
                            .addComponent(categories, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(foodName))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSalesId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFoodId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFoodName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(price)
                                    .addComponent(qty)
                                    .addComponent(btnTotal))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtQty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtPrice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(description)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtFoodId, txtPrice, txtSalesId});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chooseFood)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSalesId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(foodId))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(categories, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FoodId)
                            .addComponent(txtFoodId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(price))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(qty))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTotal))))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(description)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtFoodName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(foodName))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtFoodId, txtPrice, txtSalesId});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {FoodId, categories, description, foodId, price});

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnErase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/delete_24x24.png"))); // NOI18N
        btnErase.setText("ERASE");
        btnErase.setToolTipText("Deletes selected data");
        btnErase.setPreferredSize(new java.awt.Dimension(150, 30));
        btnErase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEraseActionPerformed(evt);
            }
        });

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnErase, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnClear, btnErase, btnSave, btnUpdate});

        txtFind.setPreferredSize(new java.awt.Dimension(170, 30));
        txtFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFindKeyTyped(evt);
            }
        });

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

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/reload_24x24.png"))); // NOI18N
        btnRefresh.setPreferredSize(new java.awt.Dimension(100, 30));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        tableSales.setModel(new javax.swing.table.DefaultTableModel(
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
        tableSales.setRowHeight(25);
        tableSales.setRowMargin(2);
        tableSales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSalesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableSales);

        javax.swing.GroupLayout panelInventoryLayout = new javax.swing.GroupLayout(panelInventory);
        panelInventory.setLayout(panelInventoryLayout);
        panelInventoryLayout.setHorizontalGroup(
            panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventoryLayout.createSequentialGroup()
                .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInventoryLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 791, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(panelInventoryLayout.createSequentialGroup()
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelInventoryLayout.createSequentialGroup()
                                        .addComponent(dataTotal)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(total)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtFind, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(panelInventoryLayout.createSequentialGroup()
                        .addGap(338, 338, 338)
                        .addComponent(title)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        panelInventoryLayout.setVerticalGroup(
            panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dataTotal)
                        .addComponent(total)
                        .addComponent(txtFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        getContentPane().add(panelInventory, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 670));

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
        itemFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemFoodActionPerformed(evt);
            }
        });
        menuInventory.add(itemFood);

        itemSales.setText("Sales");
        itemSales.setEnabled(false);
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
        lebarKolom2();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnEraseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEraseActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Do you want to " + "erase data", "Confirmation Dialog", JOptionPane.YES_NO_OPTION);
        if (ok==0) {
            String sql = "delete from sales where sales_id ='" + txtSalesId.getText()+"'";
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null,"Data Deleted Succesfully");
                kosong();
                dataTable();
                lebarKolom();
                lebarKolom2();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Data Invalid"+ e);
            }
        }
    }//GEN-LAST:event_btnEraseActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        String sql = "update sales set sales_date=?, food_id=?,food_name=?, price=?, qty=?, total=?, description=? where sales_id = '" + txtSalesId.getText() + "'";
        String tampilan = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tampilan);
        String date = String.valueOf(fm.format(txtSalesDate.getDate()));
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, date);
            stat.setString(2, txtFoodId.getText());
            stat.setString(3, txtFoodName.getText());
            stat.setString(4, txtPrice.getText());
            stat.setString(5, txtQty.getText());
            stat.setString(6, txtTotal.getText());
            stat.setString(7, txtDescription.getText());
            stat.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data Changed Succesfully");

            kosong();
            dataTable();
            lebarKolom();
            lebarKolom2();
            txtFoodId.requestFocus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data Invalid" + e);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (txtFoodId.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Sales Name cannot be empty");
        } else if (txtPrice.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Price cannot be empty");
        } else {
            String sql = "insert into sales values (?, ?, ?, ?, ?, ?, ?, ?)";
            String tampilan = "yyyy-MM-dd";
            SimpleDateFormat fm = new SimpleDateFormat(tampilan);
            String date = String.valueOf(fm.format(txtSalesDate.getDate()));
            
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString(1, txtSalesId.getText());
                stat.setString(2, date);
                stat.setString(3, txtFoodId.getText());
                stat.setString(4, txtFoodName.getText());
                stat.setString(5, txtPrice.getText());
                stat.setString(6, txtQty.getText());
                stat.setString(7, txtTotal.getText());
                stat.setString(8, txtDescription.getText());
                stat.executeUpdate();

                JOptionPane.showMessageDialog(null, "Data Saved Succesfully");

                kosong();
                dataTable();
                lebarKolom();
                lebarKolom2();
                txtFoodId.requestFocus();
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

    private void txtSalesIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSalesIdKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtFoodId.requestFocus();
        }
    }//GEN-LAST:event_txtSalesIdKeyPressed

    private void txtFoodIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoodIdKeyPressed
       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtFoodName.requestFocus();
        }
    }//GEN-LAST:event_txtFoodIdKeyPressed

    private void txtPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriceKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtQty.requestFocus();
        }
    }//GEN-LAST:event_txtPriceKeyPressed

    private void txtQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtTotal.requestFocus();
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
        lebarKolom2();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        reportSales rs = new reportSales(this, rootPaneCheckingEnabled);
        rs.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_btnPrintActionPerformed

    private void txtFoodNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoodNameKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtPrice.requestFocus();
        }
    }//GEN-LAST:event_txtFoodNameKeyPressed

    private void tableFoodMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableFoodMouseClicked
        try {
            int bar = tableFood.getSelectedRow();
            String a = tabmode2.getValueAt(bar, 0).toString();
            String b = tabmode2.getValueAt(bar, 1).toString();
            String c = tabmode2.getValueAt(bar, 2).toString();
            txtFoodId.setText(a);
            txtFoodName.setText(b);
            txtPrice.setText(c);
            txtQty.requestFocus();
        } catch (Exception e) {
        }
        
    }//GEN-LAST:event_tableFoodMouseClicked

    private void itemSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSalesActionPerformed
        dataSales sa = new dataSales(this, rootPaneCheckingEnabled);
        sa.setVisible(rootPaneCheckingEnabled);
        this.dispose();
    }//GEN-LAST:event_itemSalesActionPerformed

    private void itemPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPrintActionPerformed
        btnPrintActionPerformed(evt);
    }//GEN-LAST:event_itemPrintActionPerformed

    private void itemFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemFoodActionPerformed
        dataFood df = new dataFood(this, rootPaneCheckingEnabled);
        df.setVisible(rootPaneCheckingEnabled);
        this.dispose();
    }//GEN-LAST:event_itemFoodActionPerformed

    private void tableSalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSalesMouseClicked
        try {
            int bar = tableSales.getSelectedRow();
            String a = tabmode.getValueAt(bar, 0).toString();
            String b = tabmode.getValueAt(bar, 1).toString();
            String c = tabmode.getValueAt(bar, 2).toString();
            String d = tabmode.getValueAt(bar, 3).toString();
            String e = tabmode.getValueAt(bar, 4).toString();
            String f = tabmode.getValueAt(bar, 5).toString();
            String g = tabmode.getValueAt(bar, 6).toString();
            String h = tabmode.getValueAt(bar, 7).toString();

            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            Date dateValue = null;
            try{
                dateValue = date.parse((String)tableSales.getValueAt(bar, 1));
            } catch (ParseException ex){
                Logger.getLogger(dataSales.class.getName()).log(Level.SEVERE, null, ex);
            }

            txtSalesId.setText(a);
            txtSalesDate.setDate(dateValue);
            txtFoodId.setText(c);
            txtFoodName.setText(d);
            txtPrice.setText(e);
            txtQty.setText(f);
            txtTotal.setText(g);
            txtDescription.setText(h);
            aktif();
        } catch (Exception e) {
        }
        
    }//GEN-LAST:event_tableSalesMouseClicked

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        title();
    }//GEN-LAST:event_formWindowGainedFocus

    private void txtTotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtDescription.requestFocus();
        }
    }//GEN-LAST:event_txtTotalKeyPressed

    private void btnTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTotalActionPerformed
        kali();
    }//GEN-LAST:event_btnTotalActionPerformed

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
            java.util.logging.Logger.getLogger(dataSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dataSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dataSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dataSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                dataSales dialog = new dataSales(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel FoodId;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnErase;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnTotal;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel categories;
    private javax.swing.JLabel chooseFood;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
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
    private javax.swing.JTable tableSales;
    private javax.swing.JLabel title;
    private javax.swing.JLabel total;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtFind;
    private javax.swing.JTextField txtFoodId;
    private javax.swing.JTextField txtFoodName;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtSalesId;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}