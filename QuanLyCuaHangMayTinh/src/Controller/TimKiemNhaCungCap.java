/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.NhaCungCapDAO;
import java.util.ArrayList;
import Model.NhaCungCap;


/**
 *
 * @author nguyenducnguyen
 */
public class TimKiemNhaCungCap {

    public static TimKiemNhaCungCap getInstance() {
        return new TimKiemNhaCungCap();
    }

    public ArrayList<NhaCungCap> TimKiemTatCa(String text) {
        ArrayList<NhaCungCap> result = new ArrayList<>();
        ArrayList<NhaCungCap> armt = NhaCungCapDAO.getInstance().selectAll();
        for (var ncc : armt) {
            if (ncc.getMaNhaCungCap().toLowerCase().contains(text.toLowerCase())
                    || ncc.getTenNhaCungCap().toLowerCase().contains(text.toLowerCase())
                    || ncc.getSdt().toLowerCase().contains(text.toLowerCase())
                    || ncc.getDiaChi().toLowerCase().contains(text.toLowerCase())) {
                result.add(ncc);
            }
        }
        return result;
    }

    public ArrayList<NhaCungCap> TimKiemTenNCC(String text) {
        ArrayList<NhaCungCap> result = new ArrayList<>();
        ArrayList<NhaCungCap> armt = NhaCungCapDAO.getInstance().selectAll();
        for (var ncc : armt) {
            if (ncc.getTenNhaCungCap().toLowerCase().contains(text.toLowerCase())) {
                result.add(ncc);
            }
        }
        return result;
    }

    public ArrayList<NhaCungCap> TimKiemMaNCC(String text) {
        ArrayList<NhaCungCap> result = new ArrayList<>();
        ArrayList<NhaCungCap> armt = NhaCungCapDAO.getInstance().selectAll();
        for (var ncc : armt) {
            if (ncc.getMaNhaCungCap().toLowerCase().contains(text.toLowerCase())) {
                result.add(ncc);
            }
        }
        return result;
    }

    public ArrayList<NhaCungCap> TimKiemDiaChi(String text) {
        ArrayList<NhaCungCap> result = new ArrayList<>();
        ArrayList<NhaCungCap> armt = NhaCungCapDAO.getInstance().selectAll();
        for (var ncc : armt) {
            if (ncc.getDiaChi().toLowerCase().contains(text.toLowerCase())) {
                result.add(ncc);
            }
        }
        return result;
    }

    public ArrayList<NhaCungCap> TimKiemSdt(String text) {
        ArrayList<NhaCungCap> result = new ArrayList<>();
        ArrayList<NhaCungCap> armt = NhaCungCapDAO.getInstance().selectAll();
        for (var ncc : armt) {
            if (ncc.getSdt().toLowerCase().contains(text.toLowerCase())) {
                result.add(ncc);
            }
        }
        return result;
    }
}


