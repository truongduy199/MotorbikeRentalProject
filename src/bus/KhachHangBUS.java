package bus;

import dao.KhachHangDAO;
import dto.KhachHangDTO;
import java.util.List;
import java.util.ArrayList;

public class KhachHangBUS {
    private KhachHangDAO khachHangDAO;

    public KhachHangBUS() {
        // Rất nhiều bạn quên dòng này dẫn đến lỗi NullPointerException
        khachHangDAO = new KhachHangDAO();
    }
    public String themKhachHang(KhachHangDTO kh) {
        if (kh.getFullName().isEmpty() || kh.getPhone().isEmpty() || kh.getCccd().isEmpty()) {
            return "Vui lòng nhập các trường bắt buộc (Họ tên, SĐT, CCCD)!";
        }
        if (khachHangDAO.themKhachHang(kh)) {
            return "Thêm thông tin thành công!";
        }
        return "Lỗi khi lưu thông tin khách hàng!";
    }

    public List<KhachHangDTO> getAllCustomers() {
        return khachHangDAO.getAllCustomers();
    }
}