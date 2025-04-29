/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package GiaoDien;

import Controller.TimKiemKhachHang;
import Controller.DinhDangPDF;
import Controller.TimKiemSanPham;
import DAO.ChiTietHoaDonDAO;
import DAO.HoaDonDAO;
import DAO.KhachHangDAO;
import DAO.SanPhamMayTinhDAO;
import Model.ChiTietHoaDon;
import Model.HoaDon;
import Model.KhachHang;
import Model.SanPhamMayTinh;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author nguyenducnguyen
 */
public class ThemHoaDonForm extends javax.swing.JInternalFrame {

    /**
     * Creates new form ThemHoaDonForm
     */
        private DefaultTableModel tblModelSanPham;
    private DefaultTableModel tblModelKhachHang;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    private ArrayList<SanPhamMayTinh> allProduct;
    private ArrayList<KhachHang> allKhachHang;
    private String maHoaDon;
    private ArrayList<ChiTietHoaDon> chiTietHoaDon;

    public ThemHoaDonForm() {
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        initComponents();
        allProduct = SanPhamMayTinhDAO.getInstance().selectAllExist();
        allKhachHang = KhachHangDAO.getInstance().selectAll();
        initTable();
        loadDataToTableProduct(allProduct);
        loadDataToTableKhachHang(allKhachHang);
        tblSanPham.setDefaultEditor(Object.class, null);
        tblNhapHang.setDefaultEditor(Object.class, null);
        tblKhachHang.setDefaultEditor(Object.class, null);
        maHoaDon = createId(HoaDonDAO.getInstance().selectAll());
        txtMaHoaDon.setText(maHoaDon);
        chiTietHoaDon = new ArrayList<>();
        txtNguoiTao.setFocusable(false);
        txtThueVAT.setText("10");
        txtKhuyenMai.setText("0");
    }

    public final void initTable() {
        tblModelSanPham = new DefaultTableModel();
        String[] headerTblSanPham = new String[]{"Mã máy", "Tên máy", "Số lượng", "Đơn giá"};
        tblModelSanPham.setColumnIdentifiers(headerTblSanPham);
        tblSanPham.setModel(tblModelSanPham);
        tblSanPham.getColumnModel().getColumn(0).setPreferredWidth(5);
        tblSanPham.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblSanPham.getColumnModel().getColumn(2).setPreferredWidth(5);

        tblModelKhachHang = new DefaultTableModel();
        String[] headerTblKhachHang = new String[]{"Mã KH", "Họ tên", "SĐT", "Địa chỉ"};
        tblModelKhachHang.setColumnIdentifiers(headerTblKhachHang);
        tblKhachHang.setModel(tblModelKhachHang);
        tblKhachHang.getColumnModel().getColumn(0).setPreferredWidth(5);
        tblKhachHang.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblKhachHang.getColumnModel().getColumn(2).setPreferredWidth(50);

        DefaultTableModel tblModelNhapHang = new DefaultTableModel();
        String[] headerTblNhapHang = new String[]{"STT", "Mã SP", "Tên SP", "Số lượng", "Đơn giá"};
        tblModelNhapHang.setColumnIdentifiers(headerTblNhapHang);
        tblNhapHang.setModel(tblModelNhapHang);
        tblNhapHang.getColumnModel().getColumn(0).setPreferredWidth(5);
        tblNhapHang.getColumnModel().getColumn(1).setPreferredWidth(10);
        tblNhapHang.getColumnModel().getColumn(2).setPreferredWidth(250);
    }

    private void loadDataToTableProduct(ArrayList<SanPhamMayTinh> arrProd) {
        try {
            tblModelSanPham.setRowCount(0);
            for (var i : arrProd) {
                tblModelSanPham.addRow(new Object[]{
                    i.getMaMay(), i.getTenMay(), i.getSoLuong(), formatter.format(i.getGia()) + "đ"
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataToTableKhachHang(ArrayList<KhachHang> arrKH) {
        try {
            tblModelKhachHang.setRowCount(0);
            for (var i : arrKH) {
                tblModelKhachHang.addRow(new Object[]{
                    i.getMaKhachHang(), i.getHoTen(), i.getSoDienThoai(), i.getDiaChi()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double tinhTongTien() {
        double tt = 0;
        for (var i : chiTietHoaDon) {
            tt += i.getDonGia() * i.getSoLuong();
        }
        float thueGTGT = Float.parseFloat(txtThueVAT.getText().replace("%", "")) / 100;
        float khuyenMai;
        try {
            khuyenMai = Float.parseFloat(txtKhuyenMai.getText()) / 100; // Khuyến mãi tính theo %
        } catch (NumberFormatException e) {
            khuyenMai = 0; // Nếu không nhập số hợp lệ, khuyến mãi = 0%
        }
        return tt * (1 + thueGTGT) * (1 - khuyenMai);
    }

    public SanPhamMayTinh findMayTinh(String maMay) {
        for (var i : allProduct) {
            if (maMay.equals(i.getMaMay())) {
                return i;
            }
        }
        return null;
    }

    public ChiTietHoaDon findCTHoaDon(String maMay) {
        for (var i : chiTietHoaDon) {
            if (maMay.equals(i.getMaMay())) {
                return i;
            }
        }
        return null;
    }

    public void loadDataToTableNhapHang() {
        double sum = 0;
        try {
            DefaultTableModel tblNhapHangmd = (DefaultTableModel) tblNhapHang.getModel();
            tblNhapHangmd.setRowCount(0);
            for (int i = 0; i < chiTietHoaDon.size(); i++) {
                tblNhapHangmd.addRow(new Object[]{
                    i + 1, chiTietHoaDon.get(i).getMaMay(), findMayTinh(chiTietHoaDon.get(i).getMaMay()).getTenMay(), 
                    chiTietHoaDon.get(i).getSoLuong(), formatter.format(chiTietHoaDon.get(i).getDonGia()) + "đ"
                });
                sum += chiTietHoaDon.get(i).getDonGia() * chiTietHoaDon.get(i).getSoLuong();
            }
            textTongTien.setText(formatter.format(tinhTongTien()) + "đ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNguoiTao(String name) {
        txtNguoiTao.setText(name);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNguoiTao = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhapHang = new javax.swing.JTable();
        btnTaoHoaDon = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        textTongTien = new javax.swing.JLabel();
        deleteProduct = new javax.swing.JButton();
        deleteProduct1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtKhuyenMai = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cboPTTT = new javax.swing.JComboBox<>();
        txtHoTen = new javax.swing.JTextField();
        txtThueVAT = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButtonCapNhat = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        addProduct = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        btnReset = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        txtSearchKhachHang = new javax.swing.JTextField();
        btnResetKhachHang = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        btnChonKhachHang = new javax.swing.JButton();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 51, 51));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Số điện thoại");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, -1, -1));

        txtMaHoaDon.setEditable(false);
        txtMaHoaDon.setEnabled(false);
        txtMaHoaDon.setFocusable(false);
        jPanel2.add(txtMaHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 340, 110, 36));

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Người tạo phiếu");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 580, -1, -1));

        txtNguoiTao.setEditable(false);
        txtNguoiTao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNguoiTaoActionPerformed(evt);
            }
        });
        jPanel2.add(txtNguoiTao, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 570, 160, 36));

        tblNhapHang.setFont(tblNhapHang.getFont().deriveFont((float)15));
        tblNhapHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SP", "Tên SP", "Số lượng", "Đơn giá"
            }
        ));
        jScrollPane1.setViewportView(tblNhapHang);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 580, 300));

        btnTaoHoaDon.setBackground(new java.awt.Color(0, 204, 51));
        btnTaoHoaDon.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        btnTaoHoaDon.setText("Tạo hoá đơn");
        btnTaoHoaDon.setBorder(null);
        btnTaoHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });
        jPanel2.add(btnTaoHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 680, 123, 40));

        jLabel5.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Tổng tiền:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 630, 130, 30));

        textTongTien.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        textTongTien.setForeground(new java.awt.Color(255, 255, 255));
        textTongTien.setText("0đ");
        jPanel2.add(textTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 630, -1, 30));

        deleteProduct.setBackground(new java.awt.Color(0, 255, 255));
        deleteProduct.setText("Xoá sản phẩm");
        deleteProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteProductActionPerformed(evt);
            }
        });
        jPanel2.add(deleteProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 680, 130, 40));

        deleteProduct1.setBackground(new java.awt.Color(255, 51, 51));
        deleteProduct1.setForeground(new java.awt.Color(255, 51, 51));
        deleteProduct1.setText("Nhập excel");
        deleteProduct1.setBorder(null);
        deleteProduct1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteProduct1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteProduct1ActionPerformed(evt);
            }
        });
        jPanel2.add(deleteProduct1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 680, -1, 40));

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Mã hoá đơn");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, -1));

        txtSoDienThoai.setEnabled(false);
        txtSoDienThoai.setFocusable(false);
        jPanel2.add(txtSoDienThoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 490, 280, 36));

        jLabel6.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("PTTT");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 350, -1, -1));

        txtKhuyenMai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKhuyenMaiKeyReleased(evt);
            }
        });
        jPanel2.add(txtKhuyenMai, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 440, 80, 36));

        txtDiaChi.setEnabled(false);
        txtDiaChi.setFocusable(false);
        jPanel2.add(txtDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 440, 280, 36));

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Địa chỉ");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, -1, -1));

        cboPTTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thanh toán bằng thẻ tín dụng", "Thanh toán bằng tiền mặt", "Thanh toán bằng ví điện tử", "Thanh toán bằng ngân hàng" }));
        jPanel2.add(cboPTTT, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 340, 200, 40));

        txtHoTen.setEnabled(false);
        txtHoTen.setFocusable(false);
        jPanel2.add(txtHoTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 390, 280, 36));

        txtThueVAT.setEditable(false);
        txtThueVAT.setText("10%");
        txtThueVAT.setEnabled(false);
        txtThueVAT.setFocusable(false);
        jPanel2.add(txtThueVAT, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 390, 80, 36));

        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Khuyến mãi");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 450, -1, -1));

        jLabel9.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Họ tên");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, -1, -1));

        jLabel10.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Thuế VAT");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 400, -1, -1));

        jButtonCapNhat.setBackground(new java.awt.Color(0, 255, 204));
        jButtonCapNhat.setText("Cập nhật số lượng");
        jButtonCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCapNhatActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 680, -1, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 0, 620, 750));

        jPanel3.setBackground(new java.awt.Color(255, 51, 51));

        tblSanPham.setFont(tblSanPham.getFont().deriveFont((float)15));
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã máy", "Tên máy", "Số lượng", "Đơn giá"
            }
        ));
        jScrollPane2.setViewportView(tblSanPham);

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Số lượng");

        txtSoLuong.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSoLuong.setText("1");
        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });

        addProduct.setBackground(new java.awt.Color(0, 255, 255));
        addProduct.setText("Thêm");
        addProduct.setBorder(null);
        addProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 51, 51));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(255, 255, 0));
        btnReset.setText("Làm mới");
        btnReset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11))
        );

        jPanel5.setBackground(new java.awt.Color(255, 51, 51));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        txtSearchKhachHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKhachHangKeyReleased(evt);
            }
        });

        btnResetKhachHang.setBackground(new java.awt.Color(255, 255, 0));
        btnResetKhachHang.setText("Làm mới");
        btnResetKhachHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnResetKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetKhachHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtSearchKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnResetKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnResetKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11))
        );

        tblKhachHang.setFont(tblKhachHang.getFont().deriveFont((float)15));
        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã máy", "Tên máy", "Số lượng", "Đơn giá"
            }
        ));
        jScrollPane3.setViewportView(tblKhachHang);

        btnChonKhachHang.setBackground(new java.awt.Color(0, 255, 255));
        btnChonKhachHang.setText("Chọn");
        btnChonKhachHang.setBorder(null);
        btnChonKhachHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChonKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonKhachHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(142, 142, 142)
                                .addComponent(jLabel4)
                                .addGap(40, 40, 40)
                                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(addProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(220, 220, 220)
                                .addComponent(btnChonKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChonKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 750));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
           if (chiTietHoaDon.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Bạn chưa chọn sản phẩm để tạo hóa đơn!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
    } else if (txtHoTen.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
    } else {
        int check = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn tạo hóa đơn?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (check == JOptionPane.YES_OPTION) {
            long now = System.currentTimeMillis();
            Timestamp sqlTimestamp = new Timestamp(now);
            String maKH = (String) tblKhachHang.getValueAt(tblKhachHang.getSelectedRow(), 0);
            float khuyenMai;
            try {
                khuyenMai = Float.parseFloat(txtKhuyenMai.getText());
            } catch (NumberFormatException e) {
                khuyenMai = 0;
            }
            HoaDon hd = new HoaDon(maHoaDon, cboPTTT.getSelectedItem().toString(), Float.parseFloat(txtThueVAT.getText().replace("%", "")), 
                                   khuyenMai, (float) tinhTongTien(), sqlTimestamp, 
                                   txtNguoiTao.getText(), maKH, chiTietHoaDon);
            try {
                HoaDonDAO.getInstance().insert(hd);
                SanPhamMayTinhDAO mtdao = SanPhamMayTinhDAO.getInstance();
                for (var i : chiTietHoaDon) {
                    ChiTietHoaDonDAO.getInstance().insert(i);
                    mtdao.updateSoLuong(i.getMaMay(), mtdao.selectById(i.getMaMay()).getSoLuong() - i.getSoLuong());
                }
                JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công!");
                
                // Thêm xác nhận xuất file PDF
                int res = JOptionPane.showConfirmDialog(this, "Bạn có muốn xuất file PDF?", "", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    DinhDangPDF writepdf = new DinhDangPDF();
                    writepdf.writeHoaDon(maHoaDon); // Gọi hàm xuất PDF hóa đơn
                }
                
                allProduct = SanPhamMayTinhDAO.getInstance().selectAllExist();
                loadDataToTableProduct(allProduct);
                DefaultTableModel l = (DefaultTableModel) tblNhapHang.getModel();
                l.setRowCount(0);
                chiTietHoaDon = new ArrayList<>();
                txtSoLuong.setText("1");
                textTongTien.setText("0đ");
                txtHoTen.setText("");
                txtDiaChi.setText("");
                txtSoDienThoai.setText("");
                txtKhuyenMai.setText("0");
                maHoaDon = createId(HoaDonDAO.getInstance().selectAll());
                txtMaHoaDon.setText(maHoaDon);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void addProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductActionPerformed
        // TODO add your handling code here:
         int i_row = tblSanPham.getSelectedRow();
        if (i_row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để thêm vào hóa đơn!");
        } else {
            int soluongselect = allProduct.get(i_row).getSoLuong();
            if (soluongselect == 0) {
                JOptionPane.showMessageDialog(this, "Sản phẩm đã hết hàng!");
            } else {
                int soluong;
                try {
                    soluong = Integer.parseInt(txtSoLuong.getText().trim());
                    if (soluong > 0) {
                        if (soluongselect < soluong) {
                            JOptionPane.showMessageDialog(this, "Số lượng không đủ!");
                        } else {
                            ChiTietHoaDon cthd = findCTHoaDon((String) tblSanPham.getValueAt(i_row, 0));
                            if (cthd != null) {
                                if (findMayTinh((String) tblSanPham.getValueAt(i_row, 0)).getSoLuong() < cthd.getSoLuong() + soluong) {
                                    JOptionPane.showMessageDialog(this, "Số lượng máy không đủ!");
                                } else {
                                    cthd.setSoLuong(cthd.getSoLuong() + soluong);
                                }
                            } else {
                                SanPhamMayTinh mt = TimKiemSanPham.getInstance().TimKiemId((String) tblSanPham.getValueAt(i_row, 0));
                                ChiTietHoaDon ctp = new ChiTietHoaDon(maHoaDon, mt.getMaMay(), soluong, mt.getGia());
                                chiTietHoaDon.add(ctp);
                            }
                            loadDataToTableNhapHang();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng lớn hơn 0");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng ở dạng số nguyên!");
                }
            }
        }
    }//GEN-LAST:event_addProductActionPerformed

    private void deleteProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteProductActionPerformed
        // TODO add your handling code here:
          int i_row = tblNhapHang.getSelectedRow();
        if (i_row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa khỏi hóa đơn!");
        } else {
            chiTietHoaDon.remove(i_row);
            loadDataToTableNhapHang();
        }
    }//GEN-LAST:event_deleteProductActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        String textSearch = txtSearch.getText().toLowerCase();
        ArrayList<SanPhamMayTinh> Mtkq = new ArrayList<>();
        for (SanPhamMayTinh i : allProduct) {
            if (i.getMaMay().concat(i.getTenMay()).toLowerCase().contains(textSearch)) {
                Mtkq.add(i);
            }
        }
        loadDataToTableProduct(Mtkq);
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
         txtSearch.setText("");
        loadDataToTableProduct(allProduct);
    }//GEN-LAST:event_btnResetActionPerformed

    private void txtNguoiTaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNguoiTaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNguoiTaoActionPerformed

    private void deleteProduct1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteProduct1ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_deleteProduct1ActionPerformed

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private void txtSearchKhachHangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKhachHangKeyReleased
        // TODO add your handling code here:
         String searchContent = txtSearchKhachHang.getText().toLowerCase();
        ArrayList<KhachHang> khachHangKQ = TimKiemKhachHang.getInstance().TimKiemTatCa(searchContent);
        loadDataToTableKhachHang(khachHangKQ);

    }//GEN-LAST:event_txtSearchKhachHangKeyReleased

    private void btnResetKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetKhachHangActionPerformed
        // TODO add your handling code here:
         txtSearchKhachHang.setText("");
        loadDataToTableKhachHang(allKhachHang);

    }//GEN-LAST:event_btnResetKhachHangActionPerformed

    private void btnChonKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonKhachHangActionPerformed
        // TODO add your handling code here:
        int i_row = tblKhachHang.getSelectedRow();
        if (i_row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng!");
        } else {
            txtHoTen.setText((String) tblKhachHang.getValueAt(i_row, 1));
            txtSoDienThoai.setText((String) tblKhachHang.getValueAt(i_row, 2));
            txtDiaChi.setText((String) tblKhachHang.getValueAt(i_row, 3));
        }

    }//GEN-LAST:event_btnChonKhachHangActionPerformed

    private void jButtonCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCapNhatActionPerformed
        // TODO add your handling code here:
          int i_row = tblNhapHang.getSelectedRow();
        if (i_row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để sửa số lượng!");
        } else {
            String newSL = JOptionPane.showInputDialog(this, "Nhập số lượng cần thay đổi", "Thay đổi số lượng", QUESTION_MESSAGE);
            if (newSL != null) {
                int soLuong;
                try {
                    soLuong = Integer.parseInt(newSL);
                    if (soLuong > 0) {
                        if (soLuong > findMayTinh(chiTietHoaDon.get(i_row).getMaMay()).getSoLuong()) {
                            JOptionPane.showMessageDialog(this, "Số lượng không đủ!");
                        } else {
                            chiTietHoaDon.get(i_row).setSoLuong(soLuong);
                            loadDataToTableNhapHang();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng lớn hơn 0");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng ở dạng số nguyên!");
                }
            }
        }
    }//GEN-LAST:event_jButtonCapNhatActionPerformed

    private void txtKhuyenMaiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKhuyenMaiKeyReleased
        // TODO add your handling code here:
        try {
            float khuyenMai = Float.parseFloat(txtKhuyenMai.getText());
            if (khuyenMai < 0 || khuyenMai > 100) {
                JOptionPane.showMessageDialog(this, "Khuyến mãi phải từ 0 đến 100!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                txtKhuyenMai.setText("0");
            }
            textTongTien.setText(formatter.format(tinhTongTien()) + "đ");
        } catch (NumberFormatException e) {
            if (!txtKhuyenMai.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                txtKhuyenMai.setText("0");
            }
            textTongTien.setText(formatter.format(tinhTongTien()) + "đ");
        }
    }//GEN-LAST:event_txtKhuyenMaiKeyReleased

    public String createId(ArrayList<HoaDon> arr) {
        int id = arr.size() + 1; 
        String check = "";
        for (HoaDon hd : arr) {
            if (hd.getMaHoaDon().equals("HD" + String.format("%03d", id))) {
                check = hd.getMaHoaDon();
            }
        }
        while (check.length() != 0) {
            id++;
            check = "";
            for (HoaDon hd : arr) {
                if (hd.getMaHoaDon().equals("HD" + String.format("%03d", id))) {
                    check = hd.getMaHoaDon();
                }
            }
        }
        return "HD" + String.format("%03d", id);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addProduct;
    private javax.swing.JButton btnChonKhachHang;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnResetKhachHang;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JComboBox<String> cboPTTT;
    private javax.swing.JButton deleteProduct;
    private javax.swing.JButton deleteProduct1;
    private javax.swing.JButton jButtonCapNhat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblNhapHang;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JLabel textTongTien;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtKhuyenMai;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtNguoiTao;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSearchKhachHang;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtThueVAT;
    // End of variables declaration//GEN-END:variables
}
