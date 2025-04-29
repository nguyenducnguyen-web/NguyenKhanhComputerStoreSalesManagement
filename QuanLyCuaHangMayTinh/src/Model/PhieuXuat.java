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
public class PhieuXuat extends PhieuTao{

    public PhieuXuat() {
    }  

    public PhieuXuat(String maPhieu, Timestamp thoiGianTao, String nguoiTao, ArrayList<ChiTietPhieuNhapXuat> CTPhieu, double tongTien) {
        super(maPhieu, thoiGianTao, nguoiTao, CTPhieu, tongTien);
    }
    
}
