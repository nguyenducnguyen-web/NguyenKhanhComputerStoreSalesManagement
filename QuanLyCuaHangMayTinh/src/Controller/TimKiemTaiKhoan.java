/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.TaiKhoanDAO;
import java.util.ArrayList;
import Model.TaiKhoan;


/**
 *
 * @author nguyenducnguyen
 */
public class TimKiemTaiKhoan {

    public static TimKiemTaiKhoan getInstance() {
        return new TimKiemTaiKhoan();
    }

    public ArrayList<TaiKhoan> searchTatCaAcc(String text) {
        ArrayList<TaiKhoan> result = new ArrayList<>();
        ArrayList<TaiKhoan> armt = TaiKhoanDAO.getInstance().selectAll();
        for (var ncc : armt) {
            if (ncc.getFullName().toLowerCase().contains(text.toLowerCase())
                    || ncc.getUser().toLowerCase().contains(text.toLowerCase())
                    || ncc.getRole().toLowerCase().contains(text.toLowerCase())) {
                result.add(ncc);
            }
        }
        return result;
    }

    public ArrayList<TaiKhoan> searchFullName(String text) {
        ArrayList<TaiKhoan> result = new ArrayList<>();
        ArrayList<TaiKhoan> armt = TaiKhoanDAO.getInstance().selectAll();
        for (var ncc : armt) {
            if (ncc.getFullName().toLowerCase().contains(text.toLowerCase())) {
                result.add(ncc);
            }
        }
        return result;
    }

    public ArrayList<TaiKhoan> searchUserName(String text) {
        ArrayList<TaiKhoan> result = new ArrayList<>();
        ArrayList<TaiKhoan> armt = TaiKhoanDAO.getInstance().selectAll();
        for (var ncc : armt) {
            if (ncc.getUser().toLowerCase().contains(text.toLowerCase())) {
                result.add(ncc);
            }
        }
        return result;
    }

    public ArrayList<TaiKhoan> searchRole(String text) {
        ArrayList<TaiKhoan> result = new ArrayList<>();
        ArrayList<TaiKhoan> armt = TaiKhoanDAO.getInstance().selectAll();
        for (var ncc : armt) {
            if (ncc.getRole().toLowerCase().contains(text.toLowerCase())) {
                result.add(ncc);
            }
        }
        return result;
    }
}



