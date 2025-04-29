/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import KetNoiCoSoDuLieu.CSDLQuanLyCuaHangMayTinh;
import Model.ChiTietHoaDon;

/**
 *
 * @author nguyenducnguyen
 */
public class ChiTietHoaDonDAO implements GiaoDienDAO<ChiTietHoaDon> {

    public static ChiTietHoaDonDAO getInstance() {
        return new ChiTietHoaDonDAO();
    }

    @Override
    public int insert(ChiTietHoaDon t) {
        int ketQua = 0;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "INSERT INTO ChiTietHoaDon (maHoaDon, maMay, soLuong, donGia) VALUES (?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaHoaDon());
            pst.setString(2, t.getMaMay());
            pst.setInt(3, t.getSoLuong());
            pst.setDouble(4, t.getDonGia());
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(ChiTietHoaDon t) {
        int ketQua = 0;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "UPDATE ChiTietHoaDon SET maHoaDon=?, maMay=?, soLuong=?, donGia=? WHERE maHoaDon=? AND maMay=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaHoaDon());
            pst.setString(2, t.getMaMay());
            pst.setInt(3, t.getSoLuong());
            pst.setDouble(4, t.getDonGia());
            pst.setString(5, t.getMaHoaDon());
            pst.setString(6, t.getMaMay());
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(ChiTietHoaDon t) {
        int ketQua = 0;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "DELETE FROM ChiTietHoaDon WHERE maHoaDon=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaHoaDon());
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public ArrayList<ChiTietHoaDon> selectAll(String t) {
        ArrayList<ChiTietHoaDon> ketQua = new ArrayList<ChiTietHoaDon>();
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "SELECT * FROM ChiTietHoaDon WHERE maHoaDon=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maHoaDon = rs.getString("maHoaDon");
                String maMay = rs.getString("maMay");
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("donGia");
                ChiTietHoaDon cthd = new ChiTietHoaDon(maHoaDon, maMay, soLuong, donGia);
                ketQua.add(cthd);
            }
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<ChiTietHoaDon> selectAll() {
        ArrayList<ChiTietHoaDon> ketQua = new ArrayList<ChiTietHoaDon>();
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "SELECT * FROM ChiTietHoaDon";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maHoaDon = rs.getString("maHoaDon");
                String maMay = rs.getString("maMay");
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("donGia");
                ChiTietHoaDon cthd = new ChiTietHoaDon(maHoaDon, maMay, soLuong, donGia);
                ketQua.add(cthd);
            }
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
    
    public int deleteAll(String maHoaDon) {
    int ketQua = 0;
    try {
        Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
        String sql = "DELETE FROM ChiTietHoaDon WHERE maHoaDon=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, maHoaDon);
        ketQua = pst.executeUpdate();
        CSDLQuanLyCuaHangMayTinh.closeConnection(con);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return ketQua;
}

    @Override
    public ChiTietHoaDon selectById(String t) {
        ChiTietHoaDon ketQua = null;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "SELECT * FROM ChiTietHoaDon WHERE maHoaDon=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maHoaDon = rs.getString("maHoaDon");
                String maMay = rs.getString("maMay");
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("donGia");
                ketQua = new ChiTietHoaDon(maHoaDon, maMay, soLuong, donGia);
            }
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
}