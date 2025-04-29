/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.SanPhamMayTinhDAO;
import java.util.ArrayList;
import Model.SanPhamMayTinh;

/**
 *
 * @author nguyenducnguyen
 */
public class TimKiemSanPham {

    public static TimKiemSanPham getInstance() {
        return new TimKiemSanPham();
    }

    public ArrayList<SanPhamMayTinh> TimKiemTatCa(String text) {
        ArrayList<SanPhamMayTinh> result = new ArrayList<>();
        ArrayList<SanPhamMayTinh> armt = SanPhamMayTinhDAO.getInstance().selectAllExist();
        for (var mt : armt) {
            if (mt.getTrangThai() == 1) {
                if (mt.getMaMay().toLowerCase().contains(text.toLowerCase()) || mt.getTenMay().toLowerCase().contains(text.toLowerCase())
                        || mt.getTenCpu().toLowerCase().contains(text.toLowerCase())
                        || mt.getCardManHinh().toLowerCase().contains(text.toLowerCase())
                        || mt.getXuatXu().toLowerCase().contains(text.toLowerCase())) {
                    result.add(mt);
                }
            }
        }
        return result;
    }

    public ArrayList<SanPhamMayTinh> TimKiemMaMay(String text) {
        ArrayList<SanPhamMayTinh> result = new ArrayList<>();
        ArrayList<SanPhamMayTinh> armt = SanPhamMayTinhDAO.getInstance().selectAllExist();
        for (var mt : armt) {
            if (mt.getTrangThai() == 1) {
                if (mt.getMaMay().toLowerCase().contains(text.toLowerCase())) {
                    result.add(mt);
                }
            }
        }
        return result;
    }

    public ArrayList<SanPhamMayTinh> TimKiemTenMay(String text) {
        ArrayList<SanPhamMayTinh> result = new ArrayList<>();
        ArrayList<SanPhamMayTinh> armt = SanPhamMayTinhDAO.getInstance().selectAllExist();
        for (var mt : armt) {
            if (mt.getTrangThai() == 1) {
                if (mt.getTenMay().toLowerCase().contains(text.toLowerCase())) {
                    result.add(mt);
                }
            }
        }
        return result;
    }

    public ArrayList<SanPhamMayTinh> TimKiemSoLuong(String text) {
        ArrayList<SanPhamMayTinh> result = new ArrayList<>();
        ArrayList<SanPhamMayTinh> armt = SanPhamMayTinhDAO.getInstance().selectAllExist();
        for (var mt : armt) {
            if (mt.getTrangThai() == 1) {
                if (text.length() != 0) {
                    if (mt.getSoLuong() > Integer.parseInt(text)) {
                        result.add(mt);
                    }
                } else {
                    result.add(mt);
                }
            }
        }
        return result;
    }

    public ArrayList<SanPhamMayTinh> TimKiemDonGia(String text) {
        ArrayList<SanPhamMayTinh> result = new ArrayList<>();
        ArrayList<SanPhamMayTinh> armt = SanPhamMayTinhDAO.getInstance().selectAllExist();
        for (var mt : armt) {
            if (mt.getTrangThai() == 1) {

                if (text.length() != 0) {
                    if (mt.getGia() > Integer.parseInt(text)) {
                        result.add(mt);
                    }
                }
                else {
                    result.add(mt);
                }
            } 
        }
        return result;
    }

    public ArrayList<SanPhamMayTinh> TimKiemRam(String text) {
        ArrayList<SanPhamMayTinh> result = new ArrayList<>();
        ArrayList<SanPhamMayTinh> armt = SanPhamMayTinhDAO.getInstance().selectAllExist();
        for (var mt : armt) {
            if (mt.getRam().toLowerCase().contains(text.toLowerCase())) {
                result.add(mt);
            }
        }
        return result;
    }

    public ArrayList<SanPhamMayTinh> TimKiemCpu(String text) {
        ArrayList<SanPhamMayTinh> result = new ArrayList<>();
        ArrayList<SanPhamMayTinh> armt = SanPhamMayTinhDAO.getInstance().selectAllExist();
        for (var mt : armt) {
            if (mt.getTenCpu().toLowerCase().contains(text.toLowerCase())) {
                result.add(mt);
            }
        }
        return result;
    }

    public ArrayList<SanPhamMayTinh> TimKiemDungLuong(String text) {
        ArrayList<SanPhamMayTinh> result = new ArrayList<>();
        ArrayList<SanPhamMayTinh> armt = SanPhamMayTinhDAO.getInstance().selectAllExist();
        for (var mt : armt) {
            if (mt.getRom().toLowerCase().contains(text.toLowerCase())) {
                result.add(mt);
            }
        }
        return result;
    }

    public ArrayList<SanPhamMayTinh> TimKiemCard(String text) {
        ArrayList<SanPhamMayTinh> result = new ArrayList<>();
        ArrayList<SanPhamMayTinh> armt = SanPhamMayTinhDAO.getInstance().selectAllExist();
        for (var mt : armt) {
            if (mt.getCardManHinh().toLowerCase().contains(text.toLowerCase())) {
                result.add(mt);
            }
        }
        return result;
    }


    public ArrayList<SanPhamMayTinh> TimKiemXuatXu(String text) {
        ArrayList<SanPhamMayTinh> result = new ArrayList<>();
        ArrayList<SanPhamMayTinh> armt = SanPhamMayTinhDAO.getInstance().selectAllExist();
        for (var mt : armt) {
            if (mt.getXuatXu().toLowerCase().contains(text.toLowerCase())) {
                result.add(mt);
            }
        }
        return result;
    }

    public ArrayList<SanPhamMayTinh> TimKiemDaXoa(String text) {
        ArrayList<SanPhamMayTinh> result = new ArrayList<>();
        ArrayList<SanPhamMayTinh> armt = SanPhamMayTinhDAO.getInstance().selectAll();
        for (var mt : armt) {
            if (mt.getTrangThai() == 0) {
                if (mt.getMaMay().toLowerCase().contains(text.toLowerCase())) {
                    result.add(mt);
                }
            }
        }
        return result;
    }

    public SanPhamMayTinh TimKiemId(String text) {
        SanPhamMayTinh result = new SanPhamMayTinh();
        ArrayList<SanPhamMayTinh> armt = SanPhamMayTinhDAO.getInstance().selectAllExist();
        for (var mt : armt) {
            if (mt.getMaMay().toLowerCase().contains(text.toLowerCase())) {
                return mt;
            }
        }
        return null;
    }
}
