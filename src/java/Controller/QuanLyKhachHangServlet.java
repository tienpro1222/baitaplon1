package Controller;

import DAO.TaiKhoanDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import model.NhanVien;
import model.TaiKhoan;

@WebServlet(name = "QuanLyKhachHangServlet", urlPatterns = {"/QuanLyKhachHangServlet"})
public class QuanLyKhachHangServlet extends HttpServlet {

    // Hiển thị danh sách khách hàng
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Kiểm tra quyền Admin
        HttpSession session = request.getSession();
        Object userObj = session.getAttribute("user");
        Integer role = (Integer) session.getAttribute("role");
        
        if (userObj == null || role == null || role != 2 || !(userObj instanceof NhanVien)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return; 
        }
        
        // Lấy DAO
        TaiKhoanDAO dao = new TaiKhoanDAO();
        List<TaiKhoan> listKhachHang = dao.getAllKhachHang();
        
        // Gửi danh sách sang JSP
        request.setAttribute("listKhachHang", listKhachHang);
        
        // Chuyển tiếp đến trang JSP
        request.getRequestDispatcher("admin/admin_users.jsp").forward(request, response);
    }

    // Xử lý Vô hiệu hóa / Kích hoạt
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Kiểm tra quyền Admin
        HttpSession session = request.getSession();
        Object userObj = session.getAttribute("user");
        Integer role = (Integer) session.getAttribute("role");
        
        if (userObj == null || role == null || role != 2 || !(userObj instanceof NhanVien)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return; 
        }
        
        try {
            String action = request.getParameter("action");
            String maKH = request.getParameter("maKH");
            
            TaiKhoanDAO dao = new TaiKhoanDAO();
            
            if ("disable".equals(action)) {
                // Vô hiệu hóa
                dao.updateHieuLuc(maKH, false);
            } else if ("enable".equals(action)) {
                // Kích hoạt
                dao.updateHieuLuc(maKH, true);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Tải lại trang
        response.sendRedirect("QuanLyKhachHangServlet");
    }
}