<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý bệnh nhân</title>
</head>
<body>
    <center>
        <h1>Hệ Thống Quản Lý Bệnh Nhân</h1>
        <h2><a href="new">Thêm Mới Bệnh Nhân</a></h2>
    </center>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>Danh sách bệnh nhân</h2></caption>
            <tr>
                <th>ID</th><th>Username</th><th>Họ Tên</th><th>Giới tính</th><th>Số ĐT</th><th>Email</th><th>Ngày sinh</th><th>Hành động</th>
            </tr>
            <c:forEach var="p" items="${listPatient}">
                <tr>
                    <td><c:out value="${p.patientId}" /></td>
                    <td><c:out value="${p.username}" /></td>
                    <td><c:out value="${p.fullname}" /></td>
                    <td><c:out value="${p.gender}" /></td>
                    <td><c:out value="${p.phone}" /></td>
                    <td><c:out value="${p.email}" /></td>
                    <td><c:out value="${p.dob}" /></td>
                    <td>
                        <a href="edit?id=<c:out value='${p.patientId}' />">Sửa</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="delete?id=<c:out value='${p.patientId}' />" onclick="return confirm('Bạn chắc chắn muốn xóa?')">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>