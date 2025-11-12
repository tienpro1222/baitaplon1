<%-- 
    Document   : admin_users
    Created on : Nov 12, 2025, 11:34:14 PM
    Author     : toite
--%>

<%@page import="model.NhanVien"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    // --- Kiểm tra Admin (Bắt buộc) ---
    Object userObj = session.getAttribute("user");
    Integer role = (Integer) session.getAttribute("role");
    
    if (userObj == null || role == null || role != 2 || !(userObj instanceof NhanVien)) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return; 
    }
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý Khách hàng</title>
    
    <link rel="stylesheet" href="<c:url value='/css/bootstrap.min.css'/>" type="text/css">
    <link rel="stylesheet" href="<c:url value='/css/font-awesome.min.css'/>" type="text/css">
    <link rel="stylesheet" href="<c:url value='/css/adminstyle.css'/>" type="text/css">
</head>
<body>

    <div class="admin-layout">
        
        <jsp:include page="admin_sidebar.jsp" />
        
        <div class="admin-content-wrapper">
            
            <div class="admin-header">
                <h1>Quản lý Khách hàng</h1>
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
                                Danh Sách Khách Hàng
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-hover table-striped">
                                        <thead class="thead-dark">
                                            <tr>
                                                <th>Mã KH</th>
                                                <th>Họ Tên</th>
                                                <th>Email</th>
                                                <th>Điện thoại</th>
                                                <th>Địa chỉ</th>
                                                <th>Trạng thái</th>
                                                <th>Hành động</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${listKhachHang}" var="kh">
                                            <tr>
                                                <td>${kh.maKH}</td>
                                                <td>${kh.hoTen}</td>
                                                <td>${kh.email}</td>
                                                <td>${kh.dienThoai}</td>
                                                <td>${kh.diaChi}</td>
                                                <td>
                                                    <c:if test="${kh.hieuLuc}">
                                                        <span class="badge bg-success">Đang hoạt động</span>
                                                    </c:if>
                                                    <c:if test="${!kh.hieuLuc}">
                                                        <span class="badge bg-danger">Đã vô hiệu hóa</span>
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <form action="QuanLyKhachHangServlet" method="POST" style="display: inline;">
                                                        <input type="hidden" name="maKH" value="${kh.maKH}">
                                                        <c:if test="${kh.hieuLuc}">
                                                            <input type="hidden" name="action" value="disable">
                                                            <button type="submit" class="btn btn-danger btn-sm">Vô hiệu hóa</button>
                                                        </c:if>
                                                        <c:if test="${!kh.hieuLuc}">
                                                            <input type="hidden" name="action" value="enable">
                                                            <button type="submit" class="btn btn-success btn-sm">Kích hoạt</button>
                                                        </c:if>
                                                    </form>
                                                </td>
                                            </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
    </div>
    
    <script src="<c:url value='/js/jquery-3.3.1.min.js'/>"></script>
    <script src="<c:url value='/js/bootstrap.min.js'/>"></script>
    
</body>
</html>
