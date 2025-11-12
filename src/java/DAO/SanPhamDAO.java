package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.HinhAnhSanPham;
import model.SanPham;
import model.SanPhamSize;
import util.DBConnect;
import model.DanhMuc;
import model.KichCo;

public class SanPhamDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // --- HÀM ĐÓNG KẾT NỐI (DÙNG CHUNG) ---
    private void closeConnections() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- CÁC HÀM CỦA BẠN (ĐÃ SỬA LỖI) ---

    public void addSanPham(SanPham sp) {
        // SỬA SQL: Thêm MoTaDonVi, NgaySanXuat, LuotXem
        String sql = "INSERT INTO sanpham (TenSP, MaDM, MoTaDonVi, GiaCoBan, NgaySanXuat, LuotXem, MoTa, MaNCC) "
                   + "VALUES (?, ?, ?, ?, NOW(), 0, ?, ?)";
        try {
            con = new DBConnect().getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, sp.getTenSP());
            ps.setInt(2, sp.getMaDM());
            ps.setString(3, sp.getMoTaDonVi()); // SỬA: Thêm
            ps.setDouble(4, sp.getGiaCoBan());
            ps.setString(5, sp.getMoTa());
            ps.setString(6, sp.getMaNCC()); 
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    // HÀM getNewSanPham (ĐÃ SỬA LỖI SQL VÀ CONSTRUCTOR)
    public List<SanPham> getNewSanPham(int topN) {
        List<SanPham> list = new ArrayList<>();
        // SỬA SQL: Lấy đủ các cột và JOIN ảnh default
        String sql = "SELECT sp.*, hinhsp.URLHinhAnh " +
                     "FROM sanpham sp " +
                     "LEFT JOIN hinhanhsanpham hinhsp ON sp.MaSP = hinhsp.MaSP AND hinhsp.IsDefault = 1 " +
                     "ORDER BY sp.NgaySanXuat DESC LIMIT ?";
        try {
            con = new DBConnect().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, topN); 
            rs = ps.executeQuery();
            while (rs.next()) {
                // SỬA CONSTRUCTOR: Gọi hàm 9 tham số
                SanPham sp = new SanPham(
                        rs.getInt("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("MaDM"),
                        rs.getString("MoTaDonVi"),
                        rs.getDouble("GiaCoBan"),
                        rs.getDate("NgaySanXuat"),
                        rs.getInt("LuotXem"),
                        rs.getString("MoTa"),
                        rs.getString("MaNCC")
                );
                sp.setHinhAnh(rs.getString("URLHinhAnh")); // Set ảnh
                list.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return list;
    }

    public void deleteSanPham(int maSP) {
        String sql = "DELETE FROM sanpham WHERE MaSP = ?";
        try {
            con = new DBConnect().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, maSP);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    public void updateSanPham(SanPham sp) {
        // SỬA SQL: Thêm MoTaDonVi
        String sql = "UPDATE sanpham SET TenSP = ?, MaDM = ?, MoTaDonVi = ?, GiaCoBan = ?, MoTa = ?, MaNCC = ? "
                   + "WHERE MaSP = ?";
        try {
            con = new DBConnect().getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, sp.getTenSP());
            ps.setInt(2, sp.getMaDM());
            ps.setString(3, sp.getMoTaDonVi()); // SỬA: Thêm
            ps.setDouble(4, sp.getGiaCoBan());
            ps.setString(5, sp.getMoTa());
            ps.setString(6, sp.getMaNCC());
            ps.setInt(7, sp.getMaSP()); // Tham số cho WHERE
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }
    
    public List<KichCo> getAllKichCo() {
        List<KichCo> listKC = new ArrayList<>();
        String sql = "SELECT * FROM kichco ORDER BY MaKC";
        try {
            con = new DBConnect().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                listKC.add(new KichCo(
                        rs.getInt("MaKC"),
                        rs.getString("TenKichCo")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return listKC;
    }
    
    // HÀM getAllSanPhamSortedByDate (ĐÃ SỬA LỖI SQL VÀ CONSTRUCTOR)
    public List<SanPham> getAllSanPhamSortedByDate() {
        List<SanPham> listSP = new ArrayList<>();
        // SỬA SQL: Lấy đủ cột và JOIN ảnh
        String sql = "SELECT sp.*, hinhsp.URLHinhAnh " +
                     "FROM sanpham sp " +
                     "LEFT JOIN hinhanhsanpham hinhsp ON sp.MaSP = hinhsp.MaSP AND hinhsp.IsDefault = 1 " +
                     "ORDER BY sp.NgaySanXuat DESC";
        try {
            con = new DBConnect().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                // SỬA CONSTRUCTOR: Gọi hàm 9 tham số
                SanPham sp = new SanPham(
                        rs.getInt("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("MaDM"),
                        rs.getString("MoTaDonVi"),
                        rs.getDouble("GiaCoBan"),
                        rs.getDate("NgaySanXuat"),
                        rs.getInt("LuotXem"),
                        rs.getString("MoTa"),
                        rs.getString("MaNCC")
                );
                sp.setHinhAnh(rs.getString("URLHinhAnh")); // Set ảnh
                listSP.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return listSP;
    }
    
    // --- CÁC HÀM CỦA TÔI (GIỮ NGUYÊN VÌ NÓ ĐÚNG) ---

    public int countSanPhamByFilter(String maDM, String keyword) {
        StringBuilder sql = new StringBuilder("SELECT count(*) FROM sanpham sp");
        List<Object> params = new ArrayList<>(); 
        List<String> conditions = new ArrayList<>(); 

        if (maDM != null && !maDM.isEmpty()) {
            conditions.add("sp.MaDM = ?");
            params.add(maDM);
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            conditions.add("sp.TenSP LIKE ?");
            params.add("%" + keyword.trim() + "%"); 
        }

        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", conditions));
        }

        try {
            con = new DBConnect().getConnection();
            ps = con.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return 0;
    }


    public List<SanPham> getSanPhamByFilter(String maDM, String keyword, String sort, int page, int pageSize) {
        List<SanPham> list = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder(
            "SELECT sp.*, hinhsp.URLHinhAnh " +
            "FROM sanpham sp " +
            "LEFT JOIN hinhanhsanpham hinhsp ON sp.MaSP = hinhsp.MaSP AND hinhsp.IsDefault = 1"
        );
        
        List<Object> params = new ArrayList<>();
        List<String> conditions = new ArrayList<>();

        if (maDM != null && !maDM.isEmpty()) {
            conditions.add("sp.MaDM = ?");
            params.add(maDM);
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            conditions.add("sp.TenSP LIKE ?");
            params.add("%" + keyword.trim() + "%");
        }

        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", conditions));
        }

        if (sort != null && !sort.isEmpty()) {
            if ("price_asc".equals(sort)) {
                sql.append(" ORDER BY sp.GiaCoBan ASC");
            } else if ("price_desc".equals(sort)) {
                sql.append(" ORDER BY sp.GiaCoBan DESC");
            } else if ("name_asc".equals(sort)) {
                sql.append(" ORDER BY sp.TenSP ASC");
            }
        } else {
            sql.append(" ORDER BY sp.MaSP DESC"); 
        }

        sql.append(" LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add((page - 1) * pageSize);

        try {
            con = new DBConnect().getConnection();
            ps = con.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            
            rs = ps.executeQuery();
            while (rs.next()) {
                // SỬA: Hàm này đã dùng setter, nên nó khớp với model mới
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getInt("MaSP"));
                sp.setTenSP(rs.getString("TenSP"));
                sp.setMaDM(rs.getInt("MaDM"));
                sp.setMoTaDonVi(rs.getString("MoTaDonVi")); // SỬA: Thêm
                sp.setGiaCoBan(rs.getDouble("GiaCoBan"));
                sp.setNgaySanXuat(rs.getDate("NgaySanXuat"));
                sp.setLuotXem(rs.getInt("LuotXem"));
                sp.setMoTa(rs.getString("MoTa"));
                sp.setMaNCC(rs.getString("MaNCC"));
                sp.setHinhAnh(rs.getString("URLHinhAnh")); 
                
                list.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return list;
    }    
    
    // HÀM getTopViewed (ĐÃ SỬA LỖI SQL VÀ CONSTRUCTOR)
    public List<SanPham> getTopViewed(int topN) {
        List<SanPham> list = new ArrayList<>();
        // SỬA SQL: Lấy đủ cột và JOIN ảnh
        String sql = "SELECT sp.*, hinhsp.URLHinhAnh " +
                     "FROM sanpham sp " +
                     "LEFT JOIN hinhanhsanpham hinhsp ON sp.MaSP = hinhsp.MaSP AND hinhsp.IsDefault = 1 " +
                     "ORDER BY sp.LuotXem DESC LIMIT ?";
        try {
            con = new DBConnect().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, topN); 
            rs = ps.executeQuery();
            while (rs.next()) {
                // SỬA CONSTRUCTOR: Gọi hàm 9 tham số
                SanPham sp = new SanPham(
                        rs.getInt("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("MaDM"),
                        rs.getString("MoTaDonVi"),
                        rs.getDouble("GiaCoBan"),
                        rs.getDate("NgaySanXuat"),
                        rs.getInt("LuotXem"),
                        rs.getString("MoTa"),
                        rs.getString("MaNCC")
                );
                sp.setHinhAnh(rs.getString("URLHinhAnh")); // Set ảnh
                list.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return list;
    }

public List<DanhMuc> getAllDanhMuc() {
        List<DanhMuc> listDM = new ArrayList<>();
        String sql = "SELECT MaDM, TenDM, Hinh FROM danhmuc"; 
        try {
            con = new DBConnect().getConnection(); 
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                listDM.add(new DanhMuc(
                        rs.getInt("MaDM"),
                        rs.getString("TenDM"),
                        rs.getString("Hinh") 
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return listDM;
    }    
    public SanPham getSanPhamByMaSP(int maSP) {
        String sql = "SELECT sp.*, dm.TenDM, ncc.TenCty " +
                     "FROM sanpham sp " +
                     "JOIN danhmuc dm ON sp.MaDM = dm.MaDM " +
                     "JOIN nhacc ncc ON sp.MaNCC = ncc.MaNCC " +
                     "WHERE sp.MaSP = ?";
        try {
            con = new DBConnect().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, maSP);
            rs = ps.executeQuery();
            if (rs.next()) {
                SanPham sp = new SanPham(
                        rs.getInt("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("MaDM"),
                        rs.getString("MoTaDonVi"),
                        rs.getDouble("GiaCoBan"),
                        rs.getDate("NgaySanXuat"),
                        rs.getInt("LuotXem"),
                        rs.getString("MoTa"),
                        rs.getString("MaNCC")
                );
                
                // Set các trường JOIN
                sp.setTenDanhMuc(rs.getString("TenDM"));
                sp.setTenNhaCungCap(rs.getString("TenCty"));
                return sp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }

    // Hàm lấy các size của 1 sản phẩm (Không đổi)
    public List<SanPhamSize> getChiTietSPByMaSP(int maSP) {
        List<SanPhamSize> listSize = new ArrayList<>();
        String sql = "SELECT ct.MaCTSP, ct.MaSP, kc.TenKichCo, ct.SoLuongTon, ct.GiaBan "
                   + "FROM chitietsp ct "
                   + "JOIN kichco kc ON ct.MaKC = kc.MaKC "
                   + "WHERE ct.MaSP = ?";
        try {
            con = new DBConnect().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, maSP);
            rs = ps.executeQuery();
            while (rs.next()) {
                listSize.add(new SanPhamSize(
                        rs.getInt("MaCTSP"),
                        rs.getInt("MaSP"),
                        rs.getString("TenKichCo"),
                        rs.getInt("SoLuongTon"),
                        rs.getDouble("GiaBan")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return listSize;
    }
    
    public List<HinhAnhSanPham> getListHinhAnhByMaSP(int maSP) {
        List<HinhAnhSanPham> listAnh = new ArrayList<>();
        String sql = "SELECT * FROM hinhanhsanpham WHERE MaSP = ?";
        try {
            con = new DBConnect().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, maSP);
            rs = ps.executeQuery();
            while (rs.next()) {
                listAnh.add(new HinhAnhSanPham(
                        rs.getInt("MaHinhAnh"),
                        rs.getInt("MaSP"),
                        rs.getString("URLHinhAnh"),
                        rs.getBoolean("IsDefault")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return listAnh;
    }
    
    public List<SanPham> getSanPhamLienQuan(int maDM, int maSP_hienTai) {
        List<SanPham> listSP = new ArrayList<>();
        // SỬA SQL: Lấy đủ cột và JOIN ảnh
        String sql = "SELECT sp.*, hinhsp.URLHinhAnh " +
                     "FROM sanpham sp " +
                     "LEFT JOIN hinhanhsanpham hinhsp ON sp.MaSP = hinhsp.MaSP AND hinhsp.IsDefault = 1 "
                   + "WHERE sp.MaDM = ? AND sp.MaSP != ? "
                   + "ORDER BY NgaySanXuat DESC " 
                   + "LIMIT 4";
        try {
            con = new DBConnect().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, maDM);
            ps.setInt(2, maSP_hienTai);
            rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham(
                        rs.getInt("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("MaDM"),
                        rs.getString("MoTaDonVi"),
                        rs.getDouble("GiaCoBan"),
                        rs.getDate("NgaySanXuat"),
                        rs.getInt("LuotXem"),
                        rs.getString("MoTa"),
                        rs.getString("MaNCC")
                );
                sp.setHinhAnh(rs.getString("URLHinhAnh")); 
                listSP.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return listSP;
    }
}