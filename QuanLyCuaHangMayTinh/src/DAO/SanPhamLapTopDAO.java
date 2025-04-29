/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


import KetNoiCoSoDuLieu.CSDLQuanLyCuaHangMayTinh;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Model.SanPhamLapTop;
import Model.SanPhamMayTinh;

/**
 *
 * @author nguyenducnguyen
 */
public class SanPhamLapTopDAO implements GiaoDienDAO<SanPhamLapTop> {

    public static SanPhamLapTopDAO getInstance() {
        return new SanPhamLapTopDAO();
    }

    @Override
    public int insert(SanPhamLapTop t) {
        int ketQua = 0;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "INSERT INTO MayTinh (maMay, tenMay, soLuong, tenCpu, ram, cardManHinh, gia, dungLuongPin, kichThuocMan, xuatXu, loaiMay, rom, trangThai) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaMay());
            pst.setString(2, t.getTenMay());
            pst.setInt(3, t.getSoLuong());
            pst.setString(4, t.getTenCpu());
            pst.setString(5, t.getRam());
            pst.setString(6, t.getCardManHinh());
            pst.setDouble(7, t.getGia());
            pst.setString(8, t.getDungLuongPin());
            pst.setDouble(9, t.getkichThuocMan());
            pst.setString(10, t.getXuatXu());
            pst.setString(11, "Laptop");
            pst.setString(12, t.getRom());
            pst.setInt(13, t.getTrangThai());
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không thêm được " + t.getMaMay(),"Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return ketQua;
    }

    @Override
    public int update(SanPhamLapTop t) {
        int ketQua = 0;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            //String sql = "INSERT INTO MayTinh (maMay, tenMay, soLuong, tenCpu, ram, cardManHinh, gia, dungLuongPin, dungLuongPin, dungLuongPin, loaiMay, rom) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            String sql = "UPDATE MayTinh SET maMay=?, tenMay=?, soLuong=?, tenCpu=?, ram=?, cardManHinh=?, gia=?, dungLuongPin=?, kichThuocMan=?, xuatXu=?, loaiMay = ?, rom = ?, trangThai = ? WHERE maMay=? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaMay());
            pst.setString(2, t.getTenMay());
            pst.setInt(3, t.getSoLuong());
            pst.setString(4, t.getTenCpu());
            pst.setString(5, t.getRam());
            pst.setString(6, t.getCardManHinh());
            pst.setDouble(7, t.getGia());
            pst.setString(8, t.getDungLuongPin());
            pst.setDouble(9, t.getkichThuocMan());
            pst.setString(10, t.getXuatXu());
            pst.setString(11, "Laptop");
            pst.setString(12, t.getRom());
            pst.setInt(13, t.getTrangThai());
            pst.setString(14, t.getMaMay());
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(SanPhamLapTop t) {
        int ketQua = 0;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "DELETE FROM MayTinh WHERE maMay=? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaMay());
            ketQua = pst.executeUpdate();
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<SanPhamLapTop> selectAll() {
        ArrayList<SanPhamLapTop> ketQua = new ArrayList<SanPhamLapTop>();
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "SELECT * FROM MayTinh";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maMay = rs.getString("maMay");
                String tenMay = rs.getString("tenMay");
                int soLuong = rs.getInt("soLuong");
                String tenCpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String cardManHinh = rs.getString("cardManHinh");
                double gia = rs.getDouble("gia");
                double kichThuocMan = rs.getDouble("kichThuocMan");
                String dungLuongPin = rs.getString("dungLuongPin");
                String rom = rs.getString("rom");
                String xuatXu = rs.getString("xuatXu");
                int trangThai = rs.getInt("trangThai");
                SanPhamLapTop mt = new SanPhamLapTop(kichThuocMan, dungLuongPin, maMay, tenMay, soLuong, gia, tenCpu, ram, xuatXu, cardManHinh, rom, trangThai);
                ketQua.add(mt);
            }
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public SanPhamLapTop selectById(String t) {
        SanPhamLapTop ketQua = null;
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "SELECT * FROM MayTinh WHERE maMay=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maMay = rs.getString("maMay");
                String tenMay = rs.getString("tenMay");
                int soLuong = rs.getInt("soLuong");
                String tenCpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String cardManHinh = rs.getString("cardManHinh");
                double gia = rs.getDouble("gia");
                double kichThuocMan = rs.getDouble("kichThuocMan");
                String dungLuongPin = rs.getString("dungLuongPin");
                String rom = rs.getString("rom");
                String xuatXu = rs.getString("xuatXu");
                int trangThai = rs.getInt("trangThai");
                //Laptop(String kichThuocMan, String dungLuongPin, String maMay, String tenMay, int soLuong, double gia, String tenCpu, String ram, String xuatXu, String cardManHinh, String Rom)
                ketQua = new SanPhamLapTop(kichThuocMan, dungLuongPin, maMay, tenMay, soLuong, gia, tenCpu, ram, xuatXu, cardManHinh, rom, trangThai);
            }
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }

    public boolean isLaptop(String id) {
        try {
            Connection con = CSDLQuanLyCuaHangMayTinh.getConnection();
            String sql = "SELECT * FROM MayTinh WHERE maMay= ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            String tl = null;
            while (rs.next()) {
                tl = rs.getString("loaiMay");
            }
            if (tl.equals("Laptop")) {
                return true;
            }
            CSDLQuanLyCuaHangMayTinh.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }
}
