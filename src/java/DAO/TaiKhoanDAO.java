package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
}