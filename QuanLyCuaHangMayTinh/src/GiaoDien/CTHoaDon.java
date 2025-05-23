/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GiaoDien;

import Controller.DinhDangPDF;
import DAO.TaiKhoanDAO;
import DAO.ChiTietHoaDonDAO;
import DAO.SanPhamMayTinhDAO;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import Model.ChiTietHoaDon;
import Model.HoaDon;


/**
 * 
 * @author nguyenducnguyen
 */
public class CTHoaDon extends javax.swing.JDialog {

    /**
     * Creates new form CTHoaDonDialog
     */
     private QuanLyHoaDonForm parent;
    private DecimalFormat formatter = new DecimalFormat("###,###,###");
    private SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public CTHoaDon(QuanLyHoaDonForm parent, javax.swing.JFrame owner, boolean modal) {
        super(owner, modal);
        this.parent = parent;
        initComponents();
        setLocationRelativeTo(null);
        
        // Lấy thông tin hóa đơn từ parent
        HoaDon hd = this.parent.getHoaDonSelect();
        labelMaPhieu.setText(hd.getMaHoaDon());
        labelNguoiTao.setText(TaiKhoanDAO.getInstance().selectById(hd.getNguoiTao()).getUser());
        labelTongTien.setText(formatter.format(hd.getThanhTien()) + "đ");
        labelThoiGianTao.setText(formatDate.format(hd.getNgayTao()));
        
        // Tải dữ liệu chi tiết hóa đơn
        loadDataToTableProduct();
        setWidthTable();
        
        // Vô hiệu hóa chỉnh sửa bảng
        tblChiTietPhieu.setDefaultEditor(Object.class, null);
    }

    public void setWidthTable() {
        tblChiTietPhieu.getColumnModel().getColumn(0).setPreferredWidth(5);
        tblChiTietPhieu.getColumnModel().getColumn(1).setPreferredWidth(10);
        tblChiTietPhieu.getColumnModel().getColumn(2).setPreferredWidth(250);
        tblChiTietPhieu.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblChiTietPhieu.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblChiTietPhieu.getColumnModel().getColumn(5).setPreferredWidth(100);
    }

    public void loadDataToTableProduct() {
        try {
            ArrayList<ChiTietHoaDon> ctHoaDon = ChiTietHoaDonDAO.getInstance().selectAll(this.parent.getHoaDonSelect().getMaHoaDon());
            DefaultTableModel tblCTHoaDonmd = (DefaultTableModel) tblChiTietPhieu.getModel();
            tblCTHoaDonmd.setRowCount(0);
            for (int i = 0; i < ctHoaDon.size(); i++) {
                tblCTHoaDonmd.addRow(new Object[]{
                    i + 1,
                    ctHoaDon.get(i).getMaMay(),
                    SanPhamMayTinhDAO.getInstance().selectById(ctHoaDon.get(i).getMaMay()).getTenMay(),
                    ctHoaDon.get(i).getSoLuong(),
                    formatter.format(ctHoaDon.get(i).getDonGia()) + "đ",
                    formatter.format(ctHoaDon.get(i).getDonGia() * ctHoaDon.get(i).getSoLuong()) + "đ"
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblChiTietPhieu = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        labelTongTien = new javax.swing.JLabel();
        labelMaPhieu = new javax.swing.JLabel();
        labelNguoiTao = new javax.swing.JLabel();
        labelThoiGianTao = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Chi tiết phiếu");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 51, 51));

        jLabel1.setFont(new java.awt.Font("SF Pro Display", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("THÔNG TIN CHI TIẾT HOÁ ĐƠN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(214, 214, 214)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel2.setText("Mã hoá đơn : ");

        tblChiTietPhieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã máy", "Tên máy", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ));
        jScrollPane1.setViewportView(tblChiTietPhieu);

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel4.setText("Người tạo :");

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel5.setText("Thời gian tạo:");

        labelTongTien.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        labelTongTien.setForeground(new java.awt.Color(255, 51, 51));
        labelTongTien.setText("tongtien");

        labelMaPhieu.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        labelMaPhieu.setText("maphieu");

        labelNguoiTao.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        labelNguoiTao.setText("nguoitao");

        labelThoiGianTao.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        labelThoiGianTao.setText("tgtao");

        jLabel7.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 51, 51));
        jLabel7.setText("TỔNG TIỀN:");

        jButton1.setBackground(new java.awt.Color(255, 51, 51));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Xuất PDF");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelMaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(labelThoiGianTao, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(48, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(220, 220, 220)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(labelTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(labelMaPhieu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(labelThoiGianTao)
                    .addComponent(jLabel4)
                    .addComponent(labelNguoiTao))
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(labelTongTien)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addContainerGap())
        );

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         DinhDangPDF writepdf = new DinhDangPDF();
        writepdf.writeHoaDon(this.parent.getHoaDonSelect().getMaHoaDon());
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the FlatLightLaf look and feel */
        try {
            javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CTHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CTHoaDon dialog = new CTHoaDon(null, new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelMaPhieu;
    private javax.swing.JLabel labelNguoiTao;
    private javax.swing.JLabel labelThoiGianTao;
    private javax.swing.JLabel labelTongTien;
    private javax.swing.JTable tblChiTietPhieu;
    // End of variables declaration//GEN-END:variables
}
