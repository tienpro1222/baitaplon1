<%@page import="model.NhanVien"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.ThongKeDoanhThu"%>
<%@page import="DAO.HoaDonDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    // --- (Phần Java kiểm tra Admin giữ nguyên) ---
    Object userObj = session.getAttribute("user");
    Integer role = (Integer) session.getAttribute("role");
    NhanVien admin = null;
    
    if (userObj == null || role == null || role != 2 || !(userObj instanceof NhanVien)) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return; 
    } else {
        admin = (NhanVien) userObj;
    }
 
    // --- (Chỉ load dữ liệu cho trang này) ---
    HoaDonDAO hdDAO = new HoaDonDAO();
    List<ThongKeDoanhThu> dataDoanhThu = hdDAO.getThongKeDoanhThu7NgayQua();
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Quản Trị - Tổng quan</title>
    
    <link rel="stylesheet" href="<c:url value='/css/bootstrap.min.css'/>" type="text/css">
    <link rel="stylesheet" href="<c:url value='/css/font-awesome.min.css'/>" type="text/css">
    <link rel="stylesheet" href="<c:url value='/css/adminstyle.css'/>" type="text/css">
    
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

    <div class="admin-layout">
        
        <jsp:include page="admin_sidebar.jsp" />
        
        <div class="admin-content-wrapper">
            
            <div class="admin-header">
                <h1>Tổng quan</h1>
                <div>
                    <span>Chào, <strong>${sessionScope.user.hoTen}</strong>!</span>
                    <a href="DangXuatServlet" class="btn btn-outline-danger" style="margin-left: 15px;">Đăng xuất</a>
                </div>
            </div>

            <div class="admin-container">
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                Thống Kê Doanh Thu (7 Ngày Gần Nhất)
                            </div>
                            <div class="card-body">
                                <canvas id="myRevenueChart"></canvas>
                            </div>
                        </div>
                    </div>
                </div>
                
                </div>
            
        </div>
    </div>

    
    <script src="<c:url value='/js/jquery-3.3.1.min.js'/>"></script>
    <script src="<c:url value='/js/bootstrap.min.js'/>"></script>

    <script>
        // Định nghĩa biến toàn cục để tệp js/admin.js có thể đọc được
        var chartLabels = [];
        var chartData = [];
        
        <c:forEach items="<%= dataDoanhThu %>" var="tk">
            chartLabels.push("${tk.ngay}");
            chartData.push(${tk.tongDoanhThu});
        </c:forEach>
    </script>
    
    <script src="<c:url value='/js/admin.js'/>"></script>
    
</body>
</html>