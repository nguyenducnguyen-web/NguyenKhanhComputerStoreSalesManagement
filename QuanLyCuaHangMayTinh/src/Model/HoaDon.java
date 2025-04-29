/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Model;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author nguyenducnguyen
 */

public class HoaDon {
    private String maHoaDon;
    private String phuongThucThanhToan;
    private float thueGTGT;
    private float khuyenMai;
    private float thanhTien;
    private Timestamp ngayTao;
    private String nguoiTao;
    private String maKhachHang;
    private ArrayList<ChiTietHoaDon> chiTietHoaDon;

    public HoaDon() {
    }

    public HoaDon(String maHoaDon, String phuongThucThanhToan, float thueGTGT, float khuyenMai, float thanhTien, 
                  Timestamp ngayTao, String nguoiTao, String maKhachHang, ArrayList<ChiTietHoaDon> chiTietHoaDon) {
        this.maHoaDon = maHoaDon;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.thueGTGT = thueGTGT;
        this.khuyenMai = khuyenMai;
        this.thanhTien = thanhTien;
        this.ngayTao = ngayTao;
        this.nguoiTao = nguoiTao;
        this.maKhachHang = maKhachHang;
        this.chiTietHoaDon = chiTietHoaDon;
    }

    // Getters and Setters
    public String getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(String maHoaDon) { this.maHoaDon = maHoaDon; }
    public String getPhuongThucThanhToan() { return phuongThucThanhToan; }
    public void setPhuongThucThanhToan(String phuongThucThanhToan) { this.phuongThucThanhToan = phuongThucThanhToan; }
    public float getThueGTGT() { return thueGTGT; }
    public void setThueGTGT(float thueGTGT) { this.thueGTGT = thueGTGT; }
    public float getKhuyenMai() { return khuyenMai; }
    public void setKhuyenMai(float khuyenMai) { this.khuyenMai = khuyenMai; }
    public float getThanhTien() { return thanhTien; }
    public void setThanhTien(float thanhTien) { this.thanhTien = thanhTien; }
    public Timestamp getNgayTao() { return ngayTao; }
    public void setNgayTao(Timestamp ngayTao) { this.ngayTao = ngayTao; }
    public String getNguoiTao() { return nguoiTao; }
    public void setNguoiTao(String nguoiTao) { this.nguoiTao = nguoiTao; }
    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }
    public ArrayList<ChiTietHoaDon> getChiTietHoaDon() { return chiTietHoaDon; }
    public void setChiTietHoaDon(ArrayList<ChiTietHoaDon> chiTietHoaDon) { this.chiTietHoaDon = chiTietHoaDon; }
}