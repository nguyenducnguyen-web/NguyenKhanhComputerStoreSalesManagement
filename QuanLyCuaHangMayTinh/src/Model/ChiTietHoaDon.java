/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Model;

/**
 *
 * @author nguyenducnguyen
 */

public class ChiTietHoaDon {
    private String maHoaDon;
    private String maMay;
    private int soLuong;
    private double donGia;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(String maHoaDon, String maMay, int soLuong, double donGia) {
        this.maHoaDon = maHoaDon;
        this.maMay = maMay;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    // Getters and Setters
    public String getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(String maHoaDon) { this.maHoaDon = maHoaDon; }
    public String getMaMay() { return maMay; }
    public void setMaMay(String maMay) { this.maMay = maMay; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }
}