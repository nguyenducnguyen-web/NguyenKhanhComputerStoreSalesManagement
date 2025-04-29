/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GiaoDien;

import com.formdev.flatlaf.FlatLightLaf;
import Controller.TimKiemKhachHang;
import Controller.TimKiemSanPham;
import DAO.ChiTietHoaDonDAO;
import DAO.HoaDonDAO;
import DAO.KhachHangDAO;
import DAO.SanPhamMayTinhDAO;
import DAO.TaiKhoanDAO;
import Model.ChiTietHoaDon;
import Model.HoaDon;
import Model.KhachHang;
import Model.SanPhamMayTinh;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author nguyenducnguyen
 */
public class CapNhatHoaDon extends javax.swing.JDialog {

    /**
     * Creates new form CapNhatHoaDon
     */
        private DefaultTableModel tblModelSanPham;
    private DefaultTableModel tblModelKhachHang;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    private ArrayList<SanPhamMayTinh> allProduct;
    private ArrayList<KhachHang> allKhachHang;
    private HoaDon hoaDon;
    private ArrayList<ChiTietHoaDon> chiTietHoaDon;
    private ArrayList<ChiTietHoaDon> chiTietHoaDonOld;
    private QuanLyHoaDonForm parent;

    public CapNhatHoaDon(QuanLyHoaDonForm parent, javax.swing.JFrame owner, boolean modal) throws UnsupportedLookAndFeelException {
        super(owner, modal);
        UIManager.setLookAndFeel(new FlatLightLaf());
        this.parent = parent;
        initComponents();
        setLocationRelativeTo(null);
        
        // Lấy thông tin
        allProduct = SanPhamMayTinhDAO.getInstance().selectAllExist();
        allKhachHang = KhachHangDAO.getInstance().selectAll();
        this.hoaDon = parent.getHoaDonSelect(); // Sử dụng getHoaDonSelect từ QuanLyHoaDonForm
        chiTietHoaDon = ChiTietHoaDonDAO.getInstance().selectAll(hoaDon.getMaHoaDon());
        chiTietHoaDonOld = new ArrayList<>(chiTietHoaDon);
        
        // Hiển thị thông tin
        initTable();
        loadDataToTableProduct(allProduct);
        loadDataToTableKhachHang(allKhachHang);
        loadDataToTableNhapHang();
        displayInfo();
        
        tblSanPham.setDefaultEditor(Object.class, null);
        tblNhapHang.setDefaultEditor(Object.class, null);
        tblKhachHang.setDefaultEditor(Object.class, null);
    }

    private CapNhatHoaDon(JFrame jFrame, boolean b) {
        super(jFrame, b);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void displayInfo() {
        txtMaHoaDon.setText(hoaDon.getMaHoaDon());
        txtNguoiTao.setText(TaiKhoanDAO.getInstance().selectById(hoaDon.getNguoiTao()).getUser());
        cboPTTT.setSelectedItem(hoaDon.getPhuongThucThanhToan());
        txtThueVAT.setText(String.valueOf(hoaDon.getThueGTGT()));
        txtKhuyenMai.setText(String.valueOf(hoaDon.getKhuyenMai()));
        textTongTien.setText(formatter.format(hoaDon.getThanhTien()) + "đ");
        
        // Hiển thị thông tin khách hàng
        KhachHang kh = KhachHangDAO.getInstance().selectById(hoaDon.getMaKhachHang());
        txtHoTen.setText(kh.getHoTen());
        txtSoDienThoai.setText(kh.getSoDienThoai());
        txtDiaChi.setText(kh.getDiaChi());
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

    private void loadDataToTableNhapHang() {
        try {
            DefaultTableModel tblNhapHangmd = (DefaultTableModel) tblNhapHang.getModel();
            tblNhapHangmd.setRowCount(0);
            for (int i = 0; i < chiTietHoaDon.size(); i++) {
                tblNhapHangmd.addRow(new Object[]{
                    i + 1, chiTietHoaDon.get(i).getMaMay(), findMayTinh(chiTietHoaDon.get(i).getMaMay()).getTenMay(),
                    chiTietHoaDon.get(i).getSoLuong(), formatter.format(chiTietHoaDon.get(i).getDonGia()) + "đ"
                });
            }
            textTongTien.setText(formatter.format(tinhTongTien()) + "đ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double tinhTongTien() {
        double tt = 0;
        for (var i : chiTietHoaDon) {
            tt += i.getDonGia() * i.getSoLuong();
        }
        float thueGTGT;
        try {
            thueGTGT = Float.parseFloat(txtThueVAT.getText().replace("%", ""));
        } catch (NumberFormatException e) {
            thueGTGT = 0;
        }
        float khuyenMai;
        try {
            khuyenMai = Float.parseFloat(txtKhuyenMai.getText());
        } catch (NumberFormatException e) {
            khuyenMai = 0;
        }
        return tt * (1 + thueGTGT / 100) * (1 - khuyenMai / 100);
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
        txtSoDienThoai = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNguoiTao = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhapHang = new javax.swing.JTable();
        btnLuuThongTin = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        textTongTien = new javax.swing.JLabel();
        deleteProduct = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtKhuyenMai = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        cboPTTT = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JTextField();
        txtThueVAT = new javax.swing.JTextField();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sửa phiếu xuất");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 51, 51));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("SF Pro Display", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Thuế VAT");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 320, -1, -1));

        txtSoDienThoai.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        txtSoDienThoai.setEnabled(false);
        txtSoDienThoai.setFocusable(false);
        jPanel2.add(txtSoDienThoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 270, 36));

        jLabel3.setFont(new java.awt.Font("SF Pro Display", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Người tạo phiếu");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 480, -1, -1));

        txtNguoiTao.setEditable(false);
        txtNguoiTao.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        jPanel2.add(txtNguoiTao, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 470, 170, 36));

        tblNhapHang.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        tblNhapHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SP", "Tên SP", "Số lượng", "Đơn giá"
            }
        ));
        jScrollPane1.setViewportView(tblNhapHang);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 580, 230));

        btnLuuThongTin.setBackground(new java.awt.Color(255, 255, 0));
        btnLuuThongTin.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        btnLuuThongTin.setText("Lưu thông tin");
        btnLuuThongTin.setBorder(null);
        btnLuuThongTin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLuuThongTin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuThongTinActionPerformed(evt);
            }
        });
        jPanel2.add(btnLuuThongTin, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 570, 130, 37));

        jLabel5.setFont(new java.awt.Font("SF Pro Display", 1, 19)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Tổng tiền:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 520, 120, 30));

        textTongTien.setFont(new java.awt.Font("SF Pro Display", 1, 19)); // NOI18N
        textTongTien.setForeground(new java.awt.Color(255, 255, 255));
        textTongTien.setText("0đ");
        jPanel2.add(textTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 520, -1, 30));

        deleteProduct.setBackground(new java.awt.Color(0, 204, 51));
        deleteProduct.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        deleteProduct.setText("Xoá sản phẩm");
        deleteProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteProductActionPerformed(evt);
            }
        });
        jPanel2.add(deleteProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, 150, 40));

        jButton1.setBackground(new java.awt.Color(0, 255, 204));
        jButton1.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        jButton1.setText("Sửa số lượng");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 570, 140, 40));

        jLabel2.setFont(new java.awt.Font("SF Pro Display", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Mã hoá đơn");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        jLabel6.setFont(new java.awt.Font("SF Pro Display", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Họ tên");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, -1));

        jLabel7.setFont(new java.awt.Font("SF Pro Display", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Địa chỉ");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, -1, -1));

        jLabel8.setFont(new java.awt.Font("SF Pro Display", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Số điện thoại");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, -1, -1));

        txtKhuyenMai.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        txtKhuyenMai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKhuyenMaiKeyReleased(evt);
            }
        });
        jPanel2.add(txtKhuyenMai, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 360, 80, 36));

        txtHoTen.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        txtHoTen.setEnabled(false);
        txtHoTen.setFocusable(false);
        jPanel2.add(txtHoTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 270, 36));

        txtDiaChi.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        txtDiaChi.setEnabled(false);
        txtDiaChi.setFocusable(false);
        jPanel2.add(txtDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, 270, 36));

        cboPTTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thanh toán bằng thẻ tín dụng", "Thanh toán bằng tiền mặt", "Thanh toán bằng ví điện tử", "Thanh toán bằng ngân hàng" }));
        jPanel2.add(cboPTTT, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 260, 250, 40));

        jLabel9.setFont(new java.awt.Font("SF Pro Display", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Khuyến mãi");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 370, -1, -1));

        jLabel10.setFont(new java.awt.Font("SF Pro Display", 1, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("PTTT");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 270, -1, -1));

        txtMaHoaDon.setEditable(false);
        txtMaHoaDon.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        txtMaHoaDon.setEnabled(false);
        txtMaHoaDon.setFocusable(false);
        jPanel2.add(txtMaHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 120, 36));

        txtThueVAT.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        jPanel2.add(txtThueVAT, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 310, 80, 36));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 0, 630, 750));

        jPanel3.setBackground(new java.awt.Color(255, 51, 51));
        jPanel3.setForeground(new java.awt.Color(255, 51, 51));

        tblSanPham.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
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

        jLabel4.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Số lượng");

        txtSoLuong.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        txtSoLuong.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSoLuong.setText("1");

        addProduct.setBackground(new java.awt.Color(0, 255, 255));
        addProduct.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
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

        txtSearch.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(255, 255, 0));
        btnReset.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
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
                .addContainerGap(20, Short.MAX_VALUE)
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

        txtSearchKhachHang.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        txtSearchKhachHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKhachHangKeyReleased(evt);
            }
        });

        btnResetKhachHang.setBackground(new java.awt.Color(255, 255, 0));
        btnResetKhachHang.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
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

        tblKhachHang.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
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
        btnChonKhachHang.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        btnChonKhachHang.setText("Thêm");
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
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jLabel4)
                        .addGap(49, 49, 49)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(addProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(219, 219, 219)
                .addComponent(btnChonKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnChonKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(124, Short.MAX_VALUE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLuuThongTinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuThongTinActionPerformed
        // TODO add your handling code here:
           if (chiTietHoaDon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn sản phẩm để cập nhật hóa đơn!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtHoTen.getText().isEmpty() || txtSoDienThoai.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Xác nhận trước khi lưu
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn cập nhật hóa đơn này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            // Tìm khách hàng dựa trên số điện thoại
            KhachHang kh = KhachHangDAO.getInstance().selectBySoDienThoai(txtSoDienThoai.getText().trim());
            if (kh == null || !kh.getHoTen().equalsIgnoreCase(txtHoTen.getText().trim())) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng với thông tin này!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String maKH = kh.getMaKhachHang();

            // Kiểm tra số lượng tồn kho
            for (var ct : chiTietHoaDon) {
                SanPhamMayTinh sp = SanPhamMayTinhDAO.getInstance().selectById(ct.getMaMay());
                if (sp == null || sp.getSoLuong() < ct.getSoLuong()) {
                    JOptionPane.showMessageDialog(this, "Số lượng sản phẩm " + (sp != null ? sp.getTenMay() : ct.getMaMay()) + " không đủ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            // Khôi phục số lượng sản phẩm cũ
            for (var ct : chiTietHoaDonOld) {
                SanPhamMayTinhDAO.getInstance().updateSoLuong(ct.getMaMay(), 
                    SanPhamMayTinhDAO.getInstance().selectById(ct.getMaMay()).getSoLuong() + ct.getSoLuong());
            }

            // Cập nhật số lượng sản phẩm mới
            for (var ct : chiTietHoaDon) {
                SanPhamMayTinhDAO.getInstance().updateSoLuong(ct.getMaMay(), 
                    SanPhamMayTinhDAO.getInstance().selectById(ct.getMaMay()).getSoLuong() - ct.getSoLuong());
            }

            // Lấy thời gian hiện tại
            long now = System.currentTimeMillis();
            Timestamp sqlTimestamp = new Timestamp(now);

            // Xử lý thuế VAT
            float thueVAT;
            try {
                thueVAT = Float.parseFloat(txtThueVAT.getText().replace("%", ""));
                if (thueVAT < 0) {
                    JOptionPane.showMessageDialog(this, "Thuế VAT không được âm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Thuế VAT không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Xử lý khuyến mãi
            float khuyenMai;
            try {
                khuyenMai = Float.parseFloat(txtKhuyenMai.getText());
                if (khuyenMai < 0 || khuyenMai > 100) {
                    JOptionPane.showMessageDialog(this, "Khuyến mãi phải từ 0 đến 100!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    khuyenMai = 0;
                }
            } catch (NumberFormatException e) {
                khuyenMai = 0;
            }

            // Tạo đối tượng hóa đơn
            HoaDon hd = new HoaDon(
                hoaDon.getMaHoaDon(),
                cboPTTT.getSelectedItem().toString(),
                thueVAT,
                khuyenMai,
                (float) tinhTongTien(),
                sqlTimestamp,
                txtNguoiTao.getText(),
                maKH,
                chiTietHoaDon
            );

            // Cập nhật hóa đơn và chi tiết hóa đơn
            try {
                // Cập nhật hóa đơn
                HoaDonDAO.getInstance().update(hd);

                // Xóa toàn bộ chi tiết hóa đơn cũ
                ChiTietHoaDonDAO.getInstance().deleteAll(hoaDon.getMaHoaDon());

                // Thêm chi tiết hóa đơn mới
                for (var ct : chiTietHoaDon) {
                    ChiTietHoaDonDAO.getInstance().insert(ct);
                }

                JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thành công!");
                parent.loadDataToTable();
                this.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi cập nhật hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi không xác định!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnLuuThongTinActionPerformed

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
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
                    if (soLuong > findMayTinh(chiTietHoaDon.get(i_row).getMaMay()).getSoLuong()) {
                        JOptionPane.showMessageDialog(this, "Số lượng không đủ!");
                    } else {
                        chiTietHoaDon.get(i_row).setSoLuong(soLuong);
                        loadDataToTableNhapHang();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng ở dạng số nguyên!");
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
                int soluong = Integer.parseInt(txtSoLuong.getText().trim());
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
                        ChiTietHoaDon ctp = new ChiTietHoaDon(hoaDon.getMaHoaDon(), mt.getMaMay(), soluong, mt.getGia());
                        chiTietHoaDon.add(ctp);
                    }
                    loadDataToTableNhapHang();
                }
            }
        }
    }//GEN-LAST:event_addProductActionPerformed

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

    /**
     * @param args the command line arguments
     */
        public void setNguoiTao(String name) {
        txtNguoiTao.setText(name);
    }
    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatLightLaf());
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CapNhatHoaDon dialog = new CapNhatHoaDon(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addProduct;
    private javax.swing.JButton btnChonKhachHang;
    private javax.swing.JButton btnLuuThongTin;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnResetKhachHang;
    private javax.swing.JComboBox<String> cboPTTT;
    private javax.swing.JButton deleteProduct;
    private javax.swing.JButton jButton1;
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
