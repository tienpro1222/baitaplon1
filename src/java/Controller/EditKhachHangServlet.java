package Controller;

import DAO.TaiKhoanDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.TaiKhoan;

@WebServlet(name = "EditKhachHangServlet", urlPatterns = {"/EditKhachHangServlet"})
public class EditKhachHangServlet extends HttpServlet {

    // HIỂN THỊ FORM SỬA (LẤY DỮ LIỆU CŨ)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String maKH = request.getParameter("maKH");
        TaiKhoanDAO tkDAO = new TaiKhoanDAO();
        // Dùng hàm getKhachHangByMaKH mới (hoặc hàm getTaiKhoanByMaKH của bạn)
        TaiKhoan kh = tkDAO.getTaiKhoanByMaKH(maKH); 
        
        if (kh != null) {
            request.setAttribute("kh", kh);
            request.getRequestDispatcher("admin/edit_user.jsp").forward(request, response);
        } else {
            response.sendRedirect("QuanLyKhachHangServlet");
        }
    }

    // XỬ LÝ NÚT "LƯU THAY ĐỔI" (CẬP NHẬT)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Lấy dữ liệu từ form
        String maKH = request.getParameter("maKH");
        String hoTen = request.getParameter("hoTen");
        String email = request.getParameter("email");
        String dienThoai = request.getParameter("dienThoai"); // <-- Sửa tên
        String diaChi = request.getParameter("diaChi");

        // Tạo đối tượng TaiKhoan
        TaiKhoan kh = new TaiKhoan();
        kh.setMaKH(maKH);
        kh.setHoTen(hoTen);
        kh.setEmail(email);
        kh.setDienThoai(dienThoai); // <-- Sửa tên hàm
        kh.setDiaChi(diaChi);

        // Gọi DAO để cập nhật
        TaiKhoanDAO tkDAO = new TaiKhoanDAO();
        tkDAO.updateTaiKhoan(kh); // <-- Sửa tên hàm

        // Quay về trang danh sách
        response.sendRedirect("QuanLyKhachHangServlet");
    }
}