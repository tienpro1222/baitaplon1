package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.NhanVien;
import model.TaiKhoan;
import util.DBConnect;

public class TaiKhoanDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    public TaiKhoan checkKhachHangLogin(String userOrEmail, String pass) {
        String sql = "SELECT * FROM khachhang WHERE email = ? AND MatKhau = ?"; 
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, userOrEmail);
            ps.setString(2, pass); 
            
            rs = ps.executeQuery();
            if (rs.next()) {
                return new TaiKhoan(
                        rs.getString("MaKH"), 
                        rs.getString("HoTen"),
                        rs.getString("DiaChi"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("Email"),      
                        rs.getString("MatKhau"),    
                        rs.getInt("VaiTro")       
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }
    
    public NhanVien checkNhanVienLogin(String userOrEmail, String pass) {
        String sql = "SELECT * FROM nhanvien WHERE (MaNV = ? OR email = ?) AND MatKhau = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, userOrEmail);
            ps.setString(2, userOrEmail);
            ps.setString(3, pass); 
            
            rs = ps.executeQuery();
            if (rs.next()) {
                return new NhanVien(
                        rs.getString("MaNV"), 
                        rs.getString("HoTen"),
                        rs.getString("Email"),
                        null, 
                        null, 
                        rs.getString("MaNV"),     
                        rs.getString("MatKhau"),  
                        rs.getInt("VaiTro")     
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }

   
    public TaiKhoan checkTaiKhoanTonTai(String email) {
        String sql = "SELECT * FROM khachhang WHERE email = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new TaiKhoan(
                        rs.getString("MaKH"), 
                        rs.getString("HoTen"),
                        rs.getString("DiaChi"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("Email"),      
                        rs.getString("MatKhau"),    
                        rs.getInt("VaiTro")       
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }

    public void signup(String user, String pass, String email, String ten, String sdt, String diaChi) {
        String sql = "INSERT INTO khachhang (HoTen, DiaChi, DienThoai, Email, MatKhau, VaiTro) "
                   + "VALUES (?, ?, ?, ?, ?, 0)"; 
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, ten);
            ps.setString(2, diaChi);
            ps.setString(3, sdt);
            ps.setString(4, email);
            ps.setString(5, pass); 
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    // SỬA: Hàm lấy tài khoản bằng String maKH
    public TaiKhoan getTaiKhoanByMaKH(String maKH) { // SỬA: Nhận String
        String sql = "SELECT * FROM khachhang WHERE MaKH = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, maKH); // SỬA: setString
            rs = ps.executeQuery();
            if (rs.next()) {
                return new TaiKhoan(
                        rs.getString("MaKH"), // SỬA
                        rs.getString("HoTen"),
                        rs.getString("DiaChi"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("Email"),     
                        rs.getString("MatKhau"),   
                        rs.getInt("VaiTro")      
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }

    // Hàm đóng kết nối
    private void closeConnections() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<TaiKhoan> getAllKhachHang() {
        List<TaiKhoan> list = new ArrayList<>();
        // Lấy tất cả khách hàng (VaiTro = 0)
        String sql = "SELECT * FROM khachhang WHERE VaiTro = 0";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                // Sử dụng constructor 8 tham số của model TaiKhoan
                // (Truyền Email 2 lần, 1 cho email, 1 cho username)
                list.add(new TaiKhoan(
                        rs.getString("MaKH"),
                        rs.getString("HoTen"),
                        rs.getString("DiaChi"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("Email"), // Dùng Email cho trường username
                        rs.getString("MatKhau"),
                        rs.getInt("VaiTro"),
                        rs.getBoolean("HieuLuc") // Thêm HieuLuc
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return list;
    }

    /**
     * MỚI: Cập nhật trạng thái HieuLuc (Active/Inactive) của tài khoản
     */
    public boolean updateHieuLuc(String maKH, boolean hieuLucMoi) {
        String sql = "UPDATE khachhang SET HieuLuc = ? WHERE MaKH = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, hieuLucMoi);
            ps.setString(2, maKH);
            
            return ps.executeUpdate() > 0; // Trả về true nếu thành công
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnections();
        }}
    // Dán 5 hàm này vào cuối file DAO/TaiKhoanDAO.java

    /**
     * MỚI: Lấy tất cả tài khoản Nhân viên
     */
    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM nhanvien";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new NhanVien(
                        rs.getString("MaNV"),
                        rs.getString("HoTen"),
                        rs.getString("Email"),
                        rs.getString("MatKhau"),
                        rs.getInt("VaiTro")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return list;
    }

    /**
     * MỚI: Lấy một Nhân viên theo MaNV
     */
    public NhanVien getNhanVienByMaNV(String maNV) {
        String sql = "SELECT * FROM nhanvien WHERE MaNV = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, maNV);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new NhanVien(
                        rs.getString("MaNV"),
                        rs.getString("HoTen"),
                        rs.getString("Email"),
                        rs.getString("MatKhau"),
                        rs.getInt("VaiTro")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }

    /**
     * MỚI: Thêm một Nhân viên mới
     */
    public void addNhanVien(NhanVien nv) {
        String sql = "INSERT INTO nhanvien (MaNV, HoTen, Email, MatKhau, VaiTro) VALUES (?, ?, ?, ?, ?)";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, nv.getMaNV());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getEmail());
            ps.setString(4, nv.getPassword());
            ps.setInt(5, nv.getRole());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    /**
     * MỚI: Cập nhật một Nhân viên
     */
    public void updateNhanVien(NhanVien nv) {
        // Cập nhật cả mật khẩu nếu nó được cung cấp
        String sql = "UPDATE nhanvien SET HoTen = ?, Email = ?, MatKhau = ?, VaiTro = ? WHERE MaNV = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getEmail());
            ps.setString(3, nv.getPassword());
            ps.setInt(4, nv.getRole());
            ps.setString(5, nv.getMaNV());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    /**
     * MỚI: Xóa một Nhân viên
     */
    public void deleteNhanVien(String maNV) {
        String sql = "DELETE FROM nhanvien WHERE MaNV = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, maNV);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }
}