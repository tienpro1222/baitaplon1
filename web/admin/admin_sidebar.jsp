<%-- 
    Document   : admin_sidebar
    Created on : Nov 12, 2025, 10:42:28 PM
    Author     : toite
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <link rel="stylesheet" href="css/adminstyle.css">

<c:set var="currentPage" value="${pageContext.request.requestURI.substring(pageContext.request.contextPath.length() + 1)}" />

<div class="admin-sidebar">
    <h3 class="sidebar-title">Admin Menu</h3>
    <ul class="nav flex-column">
        <li class="nav-item">
            <a class="nav-link ${currentPage == 'admin.jsp' ? 'active' : ''}" 
               href="admin.jsp">
                <i class="fa fa-tachometer"></i> Tổng quan
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${currentPage == 'QuanLySanPhamServlet' ? 'active' : ''}" 
               href="QuanLySanPhamServlet">
                <i class="fa fa-shopping-bag"></i> Quản lý Sản phẩm
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${currentPage == 'admin_orders.jsp' ? 'active' : ''}" 
               href="#"> <%-- Sẽ làm ở bước sau --%>
                <i class="fa fa-file-text"></i> Quản lý Đơn hàng
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${currentPage == 'admin_users.jsp' ? 'active' : ''}" 
               href="#"> <%-- Sẽ làm ở bước sau --%>
                <i class="fa fa-users"></i> Quản lý Khách hàng
            </a>
        </li>
<a class="nav-link ${currentPage == 'admin/admin_orders.jsp' || currentPage == 'QuanLyDonHangServlet' ? 'active' : ''}" 
   href="QuanLyDonHangServlet">
    <i class="fa fa-file-text"></i> Quản lý Đơn hàng
</a>
        
        <li class="nav-item mt-3">
             <a class="nav-link" href="<c:url value='/TrangChu'/>" style="color: #adb5bd;">
                <i class="fa fa-home"></i> Quay về Trang chủ
            </a>
        </li>
    </ul>
</div>
