/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.KhachHangDAO;
import Model.KhachHang;
import java.util.ArrayList;

/**
 *
 * @author nguyenducnguyen
 */
public class TimKiemKhachHang {
    private static TimKiemKhachHang instance;

    public static TimKiemKhachHang getInstance() {
        if (instance == null) {
            instance = new TimKiemKhachHang();
        }
        return instance;
    }

    private TimKiemKhachHang() {
    }

    public ArrayList<KhachHang> TimKiemTatCa(String searchContent) {
        ArrayList<KhachHang> result = new ArrayList<>();
        ArrayList<KhachHang> listKhachHang = KhachHangDAO.getInstance().selectAll();
        for (KhachHang kh : listKhachHang) {
            if (kh.getMaKhachHang().toLowerCase().contains(searchContent.toLowerCase()) ||
                kh.getHoTen().toLowerCase().contains(searchContent.toLowerCase()) ||
                kh.getSoDienThoai().toLowerCase().contains(searchContent.toLowerCase()) ||
                kh.getDiaChi().toLowerCase().contains(searchContent.toLowerCase())) {
                result.add(kh);
            }
        }
        return result;
    }

    public ArrayList<KhachHang> TimKiemMaKH(String maKH) {
        ArrayList<KhachHang> result = new ArrayList<>();
        ArrayList<KhachHang> listKhachHang = KhachHangDAO.getInstance().selectAll();
        for (KhachHang kh : listKhachHang) {
            if (kh.getMaKhachHang().toLowerCase().contains(maKH.toLowerCase())) {
                result.add(kh);
            }
        }
        return result;
    }

    public ArrayList<KhachHang> TimKiemHoTen(String hoTen) {
        ArrayList<KhachHang> result = new ArrayList<>();
        ArrayList<KhachHang> listKhachHang = KhachHangDAO.getInstance().selectAll();
        for (KhachHang kh : listKhachHang) {
            if (kh.getHoTen().toLowerCase().contains(hoTen.toLowerCase())) {
                result.add(kh);
            }
        }
        return result;
    }

    public ArrayList<KhachHang> TimKiemSdt(String sdt) {
        ArrayList<KhachHang> result = new ArrayList<>();
        ArrayList<KhachHang> listKhachHang = KhachHangDAO.getInstance().selectAll();
        for (KhachHang kh : listKhachHang) {
            if (kh.getSoDienThoai().toLowerCase().contains(sdt.toLowerCase())) {
                result.add(kh);
            }
        }
        return result;
    }

    public ArrayList<KhachHang> TimKiemDiaChi(String diaChi) {
        ArrayList<KhachHang> result = new ArrayList<>();
        ArrayList<KhachHang> listKhachHang = KhachHangDAO.getInstance().selectAll();
        for (KhachHang kh : listKhachHang) {
            if (kh.getDiaChi().toLowerCase().contains(diaChi.toLowerCase())) {
                result.add(kh);
            }
        }
        return result;
    }
}