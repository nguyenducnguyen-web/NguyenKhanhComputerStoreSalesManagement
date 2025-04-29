package DAO;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import KetNoiCoSoDuLieu.CSDLQuanLyCuaHangMayTinh;
import Model.HoaDon;
import Model.ChiTietHoaDon;

/**
 * 
 * @author nguyenducnguyen
 */

public class HoaDonDAO implements GiaoDienDAO<HoaDon> {
    public static HoaDonDAO getInstance() {
        return new HoaDonDAO();
    }

    @Override
    public int insert(HoaDon t) {
        int ketQua = 0;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "INSERT INTO HoaDon (maHoaDon, phuongThucThanhToan, thueGTGT, khuyenMai, thanhTien, ngayTao, nguoiTao, maKhachHang) " +
                         "VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaHoaDon());
            pst.setString(2, t.getPhuongThucThanhToan());
            pst.setFloat(3, t.getThueGTGT());
            pst.setFloat(4, t.getKhuyenMai());
            pst.setFloat(5, t.getThanhTien());
            pst.setTimestamp(6, t.getNgayTao());
            pst.setString(7, t.getNguoiTao());
            pst.setString(8, t.getMaKhachHang());
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(HoaDon t) {
        int ketQua = 0;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "UPDATE HoaDon SET phuongThucThanhToan=?, thueGTGT=?, khuyenMai=?, thanhTien=?, ngayTao=?, nguoiTao=?, maKhachHang=? " +
                         "WHERE maHoaDon=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getPhuongThucThanhToan());
            pst.setFloat(2, t.getThueGTGT());
            pst.setFloat(3, t.getKhuyenMai());
            pst.setFloat(4, t.getThanhTien());
            pst.setTimestamp(5, t.getNgayTao());
            pst.setString(6, t.getNguoiTao());
            pst.setString(7, t.getMaKhachHang());
            pst.setString(8, t.getMaHoaDon());
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(HoaDon t) {
        int ketQua = 0;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "DELETE FROM HoaDon WHERE maHoaDon=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaHoaDon());
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<HoaDon> selectAll() {
        ArrayList<HoaDon> ketQua = new ArrayList<>();
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "SELECT * FROM HoaDon ORDER BY ngayTao DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maHoaDon = rs.getString("maHoaDon");
                String phuongThucThanhToan = rs.getString("phuongThucThanhToan");
                float thueGTGT = rs.getFloat("thueGTGT");
                float khuyenMai = rs.getFloat("khuyenMai");
                float thanhTien = rs.getFloat("thanhTien");
                Timestamp ngayTao = rs.getTimestamp("ngayTao");
                String nguoiTao = rs.getString("nguoiTao");
                String maKhachHang = rs.getString("maKhachHang");
                ArrayList<ChiTietHoaDon> chiTiet = ChiTietHoaDonDAO.getInstance().selectAll(maHoaDon);
                HoaDon hd = new HoaDon(maHoaDon, phuongThucThanhToan, thueGTGT, khuyenMai, thanhTien, ngayTao, nguoiTao, maKhachHang, chiTiet);
                ketQua.add(hd);
            }
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public HoaDon selectById(String t) {
        HoaDon ketQua = null;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "SELECT * FROM HoaDon WHERE maHoaDon=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maHoaDon = rs.getString("maHoaDon");
                String phuongThucThanhToan = rs.getString("phuongThucThanhToan");
                float thueGTGT = rs.getFloat("thueGTGT");
                float khuyenMai = rs.getFloat("khuyenMai");
                float thanhTien = rs.getFloat("thanhTien");
                Timestamp ngayTao = rs.getTimestamp("ngayTao");
                String nguoiTao = rs.getString("nguoiTao");
                String maKhachHang = rs.getString("maKhachHang");
                ArrayList<ChiTietHoaDon> chiTiet = ChiTietHoaDonDAO.getInstance().selectAll(maHoaDon);
                ketQua = new HoaDon(maHoaDon, phuongThucThanhToan, thueGTGT, khuyenMai, thanhTien, ngayTao, nguoiTao, maKhachHang, chiTiet);
            }
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
}