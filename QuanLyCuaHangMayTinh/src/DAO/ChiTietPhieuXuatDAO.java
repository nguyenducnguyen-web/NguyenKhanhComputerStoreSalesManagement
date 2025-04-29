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
import Model.ChiTietPhieuNhapXuat;
import Model.ChiTietPhieuNhapXuat;
import Model.PhieuTao;
/**
 *
 * @author nguyenducnguyen
 */
public class ChiTietPhieuXuatDAO implements GiaoDienDAO<ChiTietPhieuNhapXuat> {

    public static ChiTietPhieuXuatDAO getInstance() {
        return new ChiTietPhieuXuatDAO();
    }

    @Override
    public int insert(ChiTietPhieuNhapXuat t) {
        int ketQua = 0;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "INSERT INTO ChiTietPhieuXuat (maPhieu, maMay, soLuong, donGia) VALUES (?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            pst.setString(2, t.getMaMay());
            pst.setInt(3, t.getSoLuong());
            pst.setDouble(4, t.getDonGia());
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(ChiTietPhieuNhapXuat t) {
        int ketQua = 0;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "UPDATE ChiTietPhieuXuat SET maPhieu=?, maMay=?, soLuong=?, donGia = ?  WHERE maPhieu=? AND maMay=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            pst.setString(2, t.getMaMay());
            pst.setInt(3, t.getSoLuong());
            pst.setDouble(4, t.getDonGia());
            pst.setString(5, t.getMaPhieu());
            pst.setString(6, t.getMaMay());
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(ChiTietPhieuNhapXuat t) {
        int ketQua = 0;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "DELETE FROM ChiTietPhieuXuat WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }

    public ArrayList<ChiTietPhieuNhapXuat> selectAll(String t) {
        ArrayList<ChiTietPhieuNhapXuat> ketQua = new ArrayList<ChiTietPhieuNhapXuat>();
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "SELECT * FROM ChiTietPhieuXuat WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                String maMay = rs.getString("maMay");
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("donGia");
                ChiTietPhieuNhapXuat ctp = new ChiTietPhieuNhapXuat(maPhieu, maMay, soLuong, donGia);
                ketQua.add(ctp);
            }
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<ChiTietPhieuNhapXuat> selectAll() {
        ArrayList<ChiTietPhieuNhapXuat> ketQua = new ArrayList<ChiTietPhieuNhapXuat>();
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "SELECT * FROM ChiTietPhieuXuat";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                String maMay = rs.getString("maMay");
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("donGia");
                ChiTietPhieuNhapXuat ctp = new ChiTietPhieuNhapXuat(maPhieu, maMay, soLuong, donGia);
                ketQua.add(ctp);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ChiTietPhieuNhapXuat selectById(String t) {
        ChiTietPhieuNhapXuat ketQua = null;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "SELECT * FROM ChiTietPhieuXuat WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                String maMay = rs.getString("maMay");
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("donGia");
                ketQua = new ChiTietPhieuNhapXuat(maPhieu, maMay, soLuong, donGia);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
}
