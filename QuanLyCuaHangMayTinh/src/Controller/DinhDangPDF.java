/*
 * Click nbfs://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import java.io.*;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.Point;
import java.awt.Rectangle; // Dùng cho FileDialog
import javax.swing.JFrame;
import DAO.TaiKhoanDAO;
import DAO.ChiTietPhieuNhapDAO;
import DAO.ChiTietPhieuXuatDAO;
import DAO.SanPhamMayTinhDAO;
import DAO.NhaCungCapDAO;
import DAO.PhieuNhapDAO;
import DAO.HoaDonDAO;
import DAO.ChiTietHoaDonDAO;
import DAO.KhachHangDAO;
import DAO.PhieuXuatDAO;
import Model.ChiTietPhieuNhapXuat;
import Model.SanPhamMayTinh;
import Model.PhieuNhap;
import Model.PhieuXuat;
import Model.HoaDon;
import Model.ChiTietHoaDon;

/**
 * 
 * @author nguyenducnguyen
 */
public class DinhDangPDF {
    private final DecimalFormat formatter = new DecimalFormat("###,###,###");
    private final SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private Document document;
    private FileOutputStream file;
    private final JFrame jf = new JFrame();
    private final FileDialog fd = new FileDialog(jf, "Xuất PDF", FileDialog.SAVE);
    
    // Fonts
    private Font fontTitle;
    private Font fontHeader;
    private Font fontData;
    private Font fontFooter;
    
    // Màu sắc
    private static final BaseColor RED_COLOR = new BaseColor(200, 16, 46); // Màu đỏ đậm #C8102E
    private static final BaseColor GRAY_COLOR = new BaseColor(245, 245, 245); // Màu xám nhạt #F5F5F5
    private static final BaseColor WHITE_COLOR = BaseColor.WHITE;

    public DinhDangPDF() {
        try {
            // Khởi tạo các font chữ
            BaseFont baseFont;
            BaseFont baseFontBold;
            try {
                baseFont = BaseFont.createFont("font/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                baseFontBold = BaseFont.createFont("font/Roboto-Bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            } catch (IOException e) {
                // Nếu không tìm thấy font, sử dụng font mặc định
                System.err.println("Không tìm thấy font Roboto. Sử dụng font mặc định.");
                baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                baseFontBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            }
            fontTitle = new Font(baseFontBold, 25, Font.NORMAL, RED_COLOR);
            fontHeader = new Font(baseFontBold, 11, Font.NORMAL, BaseColor.WHITE);
            fontData = new Font(baseFont, 11, Font.NORMAL, BaseColor.BLACK);
            fontFooter = new Font(baseFont, 9, Font.NORMAL, BaseColor.GRAY);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chooseURL(String url) {
        try {
            if (document != null) {
                document.close();
            }
            document = new Document();
            file = new FileOutputStream(url);
            PdfWriter writer = PdfWriter.getInstance(document, file);
            // Thêm sự kiện để thêm số trang
            writer.setPageEvent(new PdfPageEventHelper() {
                @Override
                public void onEndPage(PdfWriter writer, Document document) {
                    PdfContentByte cb = writer.getDirectContent();
                    Phrase footer = new Phrase(String.format("Trang %d", writer.getPageNumber()), fontFooter);
                    ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, footer,
                            document.right(), document.bottom() - 10, 0);
                }
            });
            document.open();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy đường dẫn file " + url);
            ex.printStackTrace();
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, "Không thể mở document!");
            ex.printStackTrace();
        }
    }

    private void setHeader(String title) throws DocumentException {
        // Thêm logo
        try {
            java.net.URL logoUrl = getClass().getResource("/icon/logoNguyenKhanh.png");
            if (logoUrl != null) {
                Image logoImage = Image.getInstance(logoUrl);
                logoImage.scaleToFit(100, 100); // Điều chỉnh kích thước logo
                logoImage.setAlignment(Element.ALIGN_CENTER);
                logoImage.setSpacingAfter(10);
                document.add(logoImage);
            } else {
                System.err.println("Không tìm thấy logo tại /icon/logoNguyenKhanh.png");
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi tải logo: " + e.getMessage());
        }

        // Luôn hiển thị dòng chữ "CỬA HÀNG MÁY TÍNH NGUYÊN KHANH"
        Paragraph logoFallback = new Paragraph("CỬA HÀNG MÁY TÍNH NGUYÊN KHANH", new Font(fontTitle.getBaseFont(), 30, Font.BOLD, RED_COLOR));
        logoFallback.setAlignment(Element.ALIGN_CENTER);
        logoFallback.setSpacingAfter(10);
        document.add(logoFallback);

        // Tiêu đề phiếu
        Paragraph pdfTitle = new Paragraph(title, fontTitle);
        pdfTitle.setAlignment(Element.ALIGN_CENTER);
        pdfTitle.setSpacingAfter(10);
        document.add(pdfTitle);

        // Đường kẻ ngang màu đỏ
        LineSeparator line = new LineSeparator();
        line.setLineColor(RED_COLOR);
        line.setLineWidth(1);
        document.add(new Chunk(line));
        document.add(Chunk.NEWLINE);
    }

    private String getFile(String name) {
        fd.pack();
        fd.setSize(800, 600);
        fd.validate();
        Rectangle rect = jf.getContentPane().getBounds();
        double width = fd.getBounds().getWidth();
        double height = fd.getBounds().getHeight();
        double x = rect.getCenterX() - (width / 2);
        double y = rect.getCenterY() - (height / 2);
        Point leftCorner = new Point();
        leftCorner.setLocation(x, y);
        fd.setLocation(leftCorner);
        fd.setFile(name + ".pdf");
        fd.setVisible(true);
        String url = fd.getDirectory() + fd.getFile();
        if (url.equals("nullnull")) {
            return null;
        }
        return url;
    }

    public void writePhieuNhap(String mapn) {
        String url = "";
        try {
            fd.setTitle("In Phiếu Nhập");
            fd.setLocationRelativeTo(null);
            url = getFile("PhieuNhap_" + mapn);
            if (url == null) {
                return;
            }
            chooseURL(url);

            // Thiết lập tiêu đề
            setHeader("PHIẾU NHẬP HÀNG");

            // Lấy thông tin phiếu nhập
            PhieuNhap pn = PhieuNhapDAO.getInstance().selectById(mapn);

            // Phần thông tin phiếu (căn chỉnh 2 cột)
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setWidths(new float[]{50f, 50f});
            
            PdfPCell cellLeft = new PdfPCell();
            cellLeft.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
            Paragraph paraLeft = new Paragraph();
            paraLeft.setFont(fontData);
            paraLeft.add("Mã phiếu: " + pn.getMaPhieu() + "\n");
            paraLeft.add("Thời gian tạo: " + formatDate.format(pn.getThoiGianTao()));
            cellLeft.addElement(paraLeft);

            PdfPCell cellRight = new PdfPCell();
            cellRight.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
            Paragraph paraRight = new Paragraph();
            paraRight.setFont(fontData);
            paraRight.setAlignment(Element.ALIGN_RIGHT);
            paraRight.add("Người tạo: " + TaiKhoanDAO.getInstance().selectById(pn.getNguoiTao()).getFullName() + "\n");
            paraRight.add("Nhà cung cấp: " + NhaCungCapDAO.getInstance().selectById(pn.getNhaCungCap()).getTenNhaCungCap());
            cellRight.addElement(paraRight);

            infoTable.addCell(cellLeft);
            infoTable.addCell(cellRight);
            document.add(infoTable);
            document.add(Chunk.NEWLINE);

            // Bảng chi tiết sản phẩm
            PdfPTable pdfTable = new PdfPTable(5);
            pdfTable.setWidthPercentage(100);
            pdfTable.setWidths(new float[]{10f, 30f, 15f, 10f, 15f});
            
            // Tiêu đề bảng
            String[] headers = {"Mã máy", "Tên máy", "Đơn giá", "Số lượng", "Tổng tiền"};
            for (String header : headers) {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, fontHeader));
                headerCell.setBackgroundColor(RED_COLOR);
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                headerCell.setPadding(8);
                headerCell.setBorderColor(RED_COLOR);
                pdfTable.addCell(headerCell);
            }

            // Dữ liệu chi tiết
            for (ChiTietPhieuNhapXuat ctpn : ChiTietPhieuNhapDAO.getInstance().selectAll(mapn)) {
                SanPhamMayTinh mt = SanPhamMayTinhDAO.getInstance().selectById(ctpn.getMaMay());
                PdfPCell cell;
                
                cell = new PdfPCell(new Phrase(ctpn.getMaMay(), fontData));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                cell.setBackgroundColor(GRAY_COLOR);
                pdfTable.addCell(cell);

                cell = new PdfPCell(new Phrase(mt.getTenMay(), fontData));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setPadding(5);
                cell.setBackgroundColor(GRAY_COLOR);
                pdfTable.addCell(cell);

                cell = new PdfPCell(new Phrase(formatter.format(mt.getGia()) + " VNĐ", fontData));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPadding(5);
                cell.setBackgroundColor(GRAY_COLOR);
                pdfTable.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(ctpn.getSoLuong()), fontData));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                cell.setBackgroundColor(GRAY_COLOR);
                pdfTable.addCell(cell);

                cell = new PdfPCell(new Phrase(formatter.format(ctpn.getSoLuong() * mt.getGia()) + " VNĐ", fontData));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPadding(5);
                cell.setBackgroundColor(GRAY_COLOR);
                pdfTable.addCell(cell);
            }

            document.add(pdfTable);
            document.add(Chunk.NEWLINE);

            // Tổng thanh toán
            Paragraph paraTongThanhToan = new Paragraph("Tổng thanh toán: " + formatter.format(pn.getTongTien()) + " VNĐ", 
                    new Font(fontHeader.getBaseFont(), 12, Font.BOLD, RED_COLOR));
            paraTongThanhToan.setAlignment(Element.ALIGN_RIGHT);
            paraTongThanhToan.setSpacingBefore(10);
            document.add(paraTongThanhToan);

            // Chân trang
            document.add(Chunk.NEWLINE);
            LineSeparator footerLine = new LineSeparator();
            footerLine.setLineColor(RED_COLOR);
            footerLine.setLineWidth(1);
            document.add(new Chunk(footerLine));
            
            Paragraph footer = new Paragraph();
            footer.setFont(fontFooter);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.add("Chương trình quản lý bán hàng - cửa hàng máy tính Nguyễn Khanh\n");
            footer.add("Địa chỉ: 123C Nguyễn Ái Quốc, Trảng Dài, Biên Hòa, Đồng Nai\n");
            footer.add("Email: cuahangmaytinhnguyenkhanh@gmail.com | Hotline: 0971 234 500\n");
            footer.add("Ngày in: " + formatDate.format(new Date()));
            document.add(footer);

            document.close();
            JOptionPane.showMessageDialog(null, "Ghi file thành công: " + url);
            openFile(url);

        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi ghi file (DocumentException): " + url);
            ex.printStackTrace();
        }
    }
    
    public void writeHoaDon(String maHD) {
    String url = "";
    try {
        fd.setTitle("In Hóa Đơn");
        fd.setLocationRelativeTo(null);
        url = getFile("HoaDon_" + maHD);
        if (url == null) {
            return;
        }
        chooseURL(url);

        // Thiết lập tiêu đề
        setHeader("HÓA ĐƠN BÁN HÀNG");

        // Lấy thông tin hóa đơn
        HoaDon hd = HoaDonDAO.getInstance().selectById(maHD);

        // Phần thông tin hóa đơn (căn chỉnh 2 cột)
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setWidths(new float[]{50f, 50f});
        
        PdfPCell cellLeft = new PdfPCell();
        cellLeft.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
        Paragraph paraLeft = new Paragraph();
        paraLeft.setFont(fontData);
        paraLeft.add("Mã hóa đơn: " + hd.getMaHoaDon() + "\n");
        paraLeft.add("Thời gian tạo: " + formatDate.format(hd.getNgayTao()) + "\n");
        paraLeft.add("Phương thức thanh toán: " + hd.getPhuongThucThanhToan());
        cellLeft.addElement(paraLeft);

        PdfPCell cellRight = new PdfPCell();
        cellRight.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
        Paragraph paraRight = new Paragraph();
        paraRight.setFont(fontData);
        paraRight.setAlignment(Element.ALIGN_RIGHT);
        paraRight.add("Người tạo: " + TaiKhoanDAO.getInstance().selectById(hd.getNguoiTao()).getFullName() + "\n");
        paraRight.add("Khách hàng: " + KhachHangDAO.getInstance().selectById(hd.getMaKhachHang()).getHoTen());
        cellRight.addElement(paraRight);

        infoTable.addCell(cellLeft);
        infoTable.addCell(cellRight);
        document.add(infoTable);
        document.add(Chunk.NEWLINE);

        // Bảng chi tiết sản phẩm
        PdfPTable pdfTable = new PdfPTable(5);
        pdfTable.setWidthPercentage(100);
        pdfTable.setWidths(new float[]{10f, 30f, 15f, 10f, 15f});
        
        // Tiêu đề bảng
        String[] headers = {"Mã máy", "Tên máy", "Đơn giá", "Số lượng", "Tổng tiền"};
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, fontHeader));
            headerCell.setBackgroundColor(RED_COLOR);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerCell.setPadding(8);
            headerCell.setBorderColor(RED_COLOR);
            pdfTable.addCell(headerCell);
        }

        // Dữ liệu chi tiết
        for (ChiTietHoaDon cthd : ChiTietHoaDonDAO.getInstance().selectAll(maHD)) {
            SanPhamMayTinh mt = SanPhamMayTinhDAO.getInstance().selectById(cthd.getMaMay());
            PdfPCell cell;
            
            cell = new PdfPCell(new Phrase(cthd.getMaMay(), fontData));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            cell.setBackgroundColor(GRAY_COLOR);
            pdfTable.addCell(cell);

            cell = new PdfPCell(new Phrase(mt.getTenMay(), fontData));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPadding(5);
            cell.setBackgroundColor(GRAY_COLOR);
            pdfTable.addCell(cell);

            cell = new PdfPCell(new Phrase(formatter.format(mt.getGia()) + " VNĐ", fontData));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setPadding(5);
            cell.setBackgroundColor(GRAY_COLOR);
            pdfTable.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(cthd.getSoLuong()), fontData));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            cell.setBackgroundColor(GRAY_COLOR);
            pdfTable.addCell(cell);

            cell = new PdfPCell(new Phrase(formatter.format(cthd.getSoLuong() * mt.getGia()) + " VNĐ", fontData));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setPadding(5);
            cell.setBackgroundColor(GRAY_COLOR);
            pdfTable.addCell(cell);
        }

        document.add(pdfTable);
        document.add(Chunk.NEWLINE);

        // Thông tin thuế và khuyến mãi
        Paragraph paraThueKM = new Paragraph();
        paraThueKM.setFont(fontData);
        paraThueKM.setAlignment(Element.ALIGN_RIGHT);
        paraThueKM.add("Thuế VAT: " + formatter.format(hd.getThueGTGT()) + "%\n");
        paraThueKM.add("Khuyến mãi: " + formatter.format(hd.getKhuyenMai()) + "%");
        document.add(paraThueKM);

        // Tổng thanh toán
        Paragraph paraTongThanhToan = new Paragraph("Tổng thanh toán: " + formatter.format(hd.getThanhTien()) + " VNĐ", 
                new Font(fontHeader.getBaseFont(), 12, Font.BOLD, RED_COLOR));
        paraTongThanhToan.setAlignment(Element.ALIGN_RIGHT);
        paraTongThanhToan.setSpacingBefore(10);
        document.add(paraTongThanhToan);

        // Chân trang
        document.add(Chunk.NEWLINE);
        LineSeparator footerLine = new LineSeparator();
        footerLine.setLineColor(RED_COLOR);
        footerLine.setLineWidth(1);
        document.add(new Chunk(footerLine));
        
        Paragraph footer = new Paragraph();
        footer.setFont(fontFooter);
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.add("Chương trình quản lý bán hàng - cửa hàng máy tính Nguyễn Khanh\n");
        footer.add("Địa chỉ: 123C Nguyễn Ái Quốc, Trảng Dài, Biên Hòa, Đồng Nai\n");
        footer.add("Email: cuahangmaytinhnguyenkhanh@gmail.com | Hotline: 0971 234 567\n");
        footer.add("Ngày in: " + formatDate.format(new Date()));
        document.add(footer);

        document.close();
        JOptionPane.showMessageDialog(null, "Ghi file thành công: " + url);
        openFile(url);

    } catch (DocumentException ex) {
        JOptionPane.showMessageDialog(null, "Lỗi khi ghi file (DocumentException): " + url);
        ex.printStackTrace();
    }
}

    public void writePhieuXuat(String mapx) {
        String url = "";
        try {
            fd.setTitle("In Phiếu Xuất");
            fd.setLocationRelativeTo(null);
            url = getFile("PhieuXuat_" + mapx);
            if (url == null) {
                return;
            }
            chooseURL(url);

            // Thiết lập tiêu đề
            setHeader("PHIẾU XUẤT HÀNG");

            // Lấy thông tin phiếu xuất
            PhieuXuat px = PhieuXuatDAO.getInstance().selectById(mapx);

            // Phần thông tin phiếu (căn chỉnh 2 cột)
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setWidths(new float[]{50f, 50f});
            
            PdfPCell cellLeft = new PdfPCell();
            cellLeft.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
            Paragraph paraLeft = new Paragraph();
            paraLeft.setFont(fontData);
            paraLeft.add("Mã phiếu: " + mapx + "\n");
            paraLeft.add("Thời gian tạo: " + formatDate.format(px.getThoiGianTao()));
            cellLeft.addElement(paraLeft);

            PdfPCell cellRight = new PdfPCell();
            cellRight.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
            Paragraph paraRight = new Paragraph();
            paraRight.setFont(fontData);
            paraRight.setAlignment(Element.ALIGN_RIGHT);
            paraRight.add("Người tạo: " + TaiKhoanDAO.getInstance().selectById(px.getNguoiTao()).getFullName());
            cellRight.addElement(paraRight);

            infoTable.addCell(cellLeft);
            infoTable.addCell(cellRight);
            document.add(infoTable);
            document.add(Chunk.NEWLINE);

            // Bảng chi tiết sản phẩm
            PdfPTable pdfTable = new PdfPTable(5);
            pdfTable.setWidthPercentage(100);
            pdfTable.setWidths(new float[]{10f, 30f, 15f, 10f, 15f});
            
            // Tiêu đề bảng
            String[] headers = {"Mã máy", "Tên máy", "Đơn giá", "Số lượng", "Tổng tiền"};
            for (String header : headers) {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, fontHeader));
                headerCell.setBackgroundColor(RED_COLOR);
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                headerCell.setPadding(8);
                headerCell.setBorderColor(RED_COLOR);
                pdfTable.addCell(headerCell);
            }

            // Dữ liệu chi tiết
            for (ChiTietPhieuNhapXuat ctpx : ChiTietPhieuXuatDAO.getInstance().selectAll(mapx)) {
                SanPhamMayTinh mt = SanPhamMayTinhDAO.getInstance().selectById(ctpx.getMaMay());
                PdfPCell cell;
                
                cell = new PdfPCell(new Phrase(ctpx.getMaMay(), fontData));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                cell.setBackgroundColor(GRAY_COLOR);
                pdfTable.addCell(cell);

                cell = new PdfPCell(new Phrase(mt.getTenMay(), fontData));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setPadding(5);
                cell.setBackgroundColor(GRAY_COLOR);
                pdfTable.addCell(cell);

                cell = new PdfPCell(new Phrase(formatter.format(mt.getGia()) + " VNĐ", fontData));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPadding(5);
                cell.setBackgroundColor(GRAY_COLOR);
                pdfTable.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(ctpx.getSoLuong()), fontData));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                cell.setBackgroundColor(GRAY_COLOR);
                pdfTable.addCell(cell);

                cell = new PdfPCell(new Phrase(formatter.format(ctpx.getSoLuong() * mt.getGia()) + " VNĐ", fontData));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPadding(5);
                cell.setBackgroundColor(GRAY_COLOR);
                pdfTable.addCell(cell);
            }

            document.add(pdfTable);
            document.add(Chunk.NEWLINE);

            // Tổng thanh toán
            Paragraph paraTongThanhToan = new Paragraph("Tổng thanh toán: " + formatter.format(px.getTongTien()) + " VNĐ", 
                    new Font(fontHeader.getBaseFont(), 12, Font.BOLD, RED_COLOR));
            paraTongThanhToan.setAlignment(Element.ALIGN_RIGHT);
            paraTongThanhToan.setSpacingBefore(10);
            document.add(paraTongThanhToan);

            // Chân trang
            document.add(Chunk.NEWLINE);
            LineSeparator footerLine = new LineSeparator();
            footerLine.setLineColor(RED_COLOR);
            footerLine.setLineWidth(1);
            document.add(new Chunk(footerLine));
            
            Paragraph footer = new Paragraph();
            footer.setFont(fontFooter);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.add("Chương trình quản lý bán hàng - cửa hàng máy tính Nguyễn Khanh\n");
            footer.add("Địa chỉ: 123C Nguyễn Ái Quốc, Trảng Dài, Biên Hòa, Đồng Nai\n");
            footer.add("Email: cuahangmaytinhnguyenkhanh@gmail.com | Hotline: 0971 234 500\n");
            footer.add("Ngày in: " + formatDate.format(new Date()));
            document.add(footer);

            document.close();
            JOptionPane.showMessageDialog(null, "Ghi file thành công: " + url);
            openFile(url);

        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi ghi file (DocumentException): " + url);
            ex.printStackTrace();
        }
    }

    private void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}