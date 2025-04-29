/*
 * Click nbfs://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import KetNoiCoSoDuLieu.CSDLQuanLyCuaHangMayTinh;
import javax.swing.JOptionPane;
import Model.TaiKhoan;

/**
 *
 * @author nguyenducnguyen
 */
public class TaiKhoanDAO implements GiaoDienDAO<TaiKhoan> {
    
    public static TaiKhoanDAO getInstance() {
        return new TaiKhoanDAO();
    }

    @Override
    public int insert(TaiKhoan t) {
        int ketQua = 0;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "INSERT INTO TaiKhoan (hoVaTen, userName, matKhau, quyen, trangThai, email) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getFullName()); 
            pst.setString(2, t.getUser());     
            pst.setString(3, t.getPassword()); 
            pst.setString(4, t.getRole());     
            pst.setInt(5, t.getStatus());      // trangThai
            pst.setString(6, t.getEmail());    
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(TaiKhoan t) {
        int ketQua = 0;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "UPDATE TaiKhoan SET hoVaTen=?, matKhau=?, quyen=?, trangThai=?, email=? WHERE userName=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getFullName()); 
            pst.setString(2, t.getPassword()); 
            pst.setString(3, t.getRole());     
            pst.setInt(4, t.getStatus());      
            pst.setString(5, t.getEmail());   
            pst.setString(6, t.getUser());     
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(TaiKhoan t) {
        int ketQua = 0;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "DELETE FROM TaiKhoan WHERE userName=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getUser()); 
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<TaiKhoan> selectAll() {
        ArrayList<TaiKhoan> ketQua = new ArrayList<TaiKhoan>();
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "SELECT * FROM TaiKhoan";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String fullName = rs.getString("hoVaTen");   
                String user = rs.getString("userName");      
                String password = rs.getString("matKhau");   
                String role = rs.getString("quyen");         
                int status = rs.getInt("trangThai");        
                String email = rs.getString("email");        
                TaiKhoan acc = new TaiKhoan(fullName, user, password, role, status, email);
                ketQua.add(acc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public TaiKhoan selectById(String t) {
        TaiKhoan acc = null;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "SELECT * FROM TaiKhoan WHERE userName=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String fullName = rs.getString("hoVaTen");   
                String user = rs.getString("userName");      
                String password = rs.getString("matKhau");   
                String role = rs.getString("quyen");         
                int status = rs.getInt("trangThai");         
                String email = rs.getString("email");        
                acc = new TaiKhoan(fullName, user, password, role, status, email);
            }
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acc;
    }
    
    public int updatePassword(String email, String password) {
        int ketQua = 0;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "UPDATE TaiKhoan SET matKhau=? WHERE email=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, password); 
            pst.setString(2, email);    
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
}