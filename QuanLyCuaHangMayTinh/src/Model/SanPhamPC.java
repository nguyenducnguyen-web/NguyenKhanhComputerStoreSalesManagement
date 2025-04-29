/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.util.Objects;
/**
 *
 * @author nguyenducnguyen
 */
public class SanPhamPC extends SanPhamMayTinh{
    
    private String mainBoard;
    private int congSuatNguon;

    public SanPhamPC(String mainBoard, int congSuatNguon) {
        this.mainBoard = mainBoard;
        this.congSuatNguon = congSuatNguon;
    }

    public SanPhamPC(String mainBoard, int congSuatNguon, String maMay, String tenMay, int soLuong, double gia, String tenCpu, String ram, String xuatXu, String cardManHinh, String Rom, int trangThai) {
        super(maMay, tenMay, soLuong, gia, tenCpu, ram, xuatXu, cardManHinh, Rom,trangThai);
        this.mainBoard = mainBoard;
        this.congSuatNguon = congSuatNguon;
    }

    public SanPhamPC() {
        
    }

    public String getMainBoard() {
        return mainBoard;
    }

    public void setMainBoard(String mainBoard) {
        this.mainBoard = mainBoard;
    }

    public int getCongSuatNguon() {
        return congSuatNguon;
    }

    public void setCongSuatNguon(int congSuatNguon) {
        this.congSuatNguon = congSuatNguon;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.mainBoard);
        hash = 43 * hash + Objects.hashCode(this.congSuatNguon);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SanPhamPC other = (SanPhamPC) obj;
        if (!Objects.equals(this.mainBoard, other.mainBoard)) {
            return false;
        }
        return Objects.equals(this.congSuatNguon, other.congSuatNguon);
    }

    @Override
    public String toString() {
        return "PC{" + "mainBoard=" + mainBoard + ", congSuatNguon=" + congSuatNguon + '}';
    }
    
    
}
