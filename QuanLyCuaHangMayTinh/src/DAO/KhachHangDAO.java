/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import KetNoiCoSoDuLieu.CSDLQuanLyCuaHangMayTinh;
import Model.KhachHang;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author nguyenducnguyen
 */
public class KhachHangDAO implements GiaoDienDAO<KhachHang> {

    public static KhachHangDAO getInstance() {
        return new KhachHangDAO();
    }

    @Override
    public int insert(KhachHang t) {
        int ketQua = 0;
        try {
            java.sql.Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "INSERT INTO KhachHang (maKhachHang, hoTen, soDienThoai, diaChi) VALUES (?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaKhachHang());
            pst.setString(2, t.getHoTen());
            pst.setString(3, t.getSoDienThoai());
            pst.setString(4, t.getDiaChi());
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không thêm được khách hàng " + t.getMaKhachHang(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(KhachHang t) {
        int ketQua = 0;
        try {
            java.sql.Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "UPDATE KhachHang SET hoTen=?, soDienThoai=?, diaChi=? WHERE maKhachHang=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getHoTen());
            pst.setString(2, t.getSoDienThoai());
            pst.setString(3, t.getDiaChi());
            pst.setString(4, t.getMaKhachHang());
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(KhachHang t) {
        int ketQua = 0;
        try {
            java.sql.Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "DELETE FROM KhachHang WHERE maKhachHang=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaKhachHang());
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<KhachHang> selectAll() {
        ArrayList<KhachHang> ketQua = new ArrayList<>();
        try {
            java.sql.Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "SELECT * FROM KhachHang";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maKhachHang = rs.getString("maKhachHang");
                String hoTen = rs.getString("hoTen");
                String soDienThoai = rs.getString("soDienThoai");
                String diaChi = rs.getString("diaChi");
                KhachHang kh = new KhachHang(maKhachHang, hoTen, soDienThoai, diaChi);
                ketQua.add(kh);
            }
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
    
    public KhachHang selectBySoDienThoai(String soDienThoai) {
    KhachHang ketQua = null;
    try {
        java.sql.Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
        String sql = "SELECT * FROM KhachHang WHERE soDienThoai=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, soDienThoai);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            String maKhachHang = rs.getString("maKhachHang");
            String hoTen = rs.getString("hoTen");
            String diaChi = rs.getString("diaChi");
            ketQua = new KhachHang(maKhachHang, hoTen, soDienThoai, diaChi);
        }
        CSDLQuanLyCuaHangMayTinh.closeConnection(con);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return ketQua;
}

    @Override
    public KhachHang selectById(String t) {
        KhachHang ketQua = null;
        try {
            java.sql.Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE maKhachHang=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maKhachHang = rs.getString("maKhachHang");
                String hoTen = rs.getString("hoTen");
                String soDienThoai = rs.getString("soDienThoai");
                String diaChi = rs.getString("diaChi");
                ketQua = new KhachHang(maKhachHang, hoTen, soDienThoai, diaChi);
            }
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
}