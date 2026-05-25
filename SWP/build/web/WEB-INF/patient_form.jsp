<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thông tin bệnh nhân</title>
</head>
<body>
    <center>
        <h1>Quản lý bệnh nhân</h1>
        <h2>
            <c:if test="${patient != null}">Sửa thông tin bệnh nhân</c:if>
            <c:if test="${patient == null}">Thêm bệnh nhân mới</c:if>
        </h2>
    </center>
    <div align="center">
        <c:if test="${patient != null}"><form action="update" method="post"></c:if>
        <c:if test="${patient == null}"><form action="insert" method="post"></c:if>
        <table border="1" cellpadding="5">
            <c:if test="${patient != null}">
                <input type="hidden" name="id" value="<c:out value='${patient.patientId}' />" />
            </c:if>
            <tr>
                <th>Username: </th>
                <td><input type="text" name="username" size="45" value="<c:out value='${patient.username}' />" required /></td>
            </tr>
            <tr>
                <th>Mật khẩu: </th>
                <td><input type="password" name="password" size="45" value="<c:out value='${patient.password}' />" required /></td>
            </tr>
            <tr>
                <th>Họ và Tên: </th>
                <td><input type="text" name="fullname" size="45" value="<c:out value='${patient.fullname}' />" required /></td>
            </tr>
            <tr>
                <th>Giới tính: </th>
                <td><input type="text" name="gender" size="45" value="<c:out value='${patient.gender}' />" /></td>
            </tr>
            <tr>
                <th>Số điện thoại: </th>
                <td><input type="text" name="phone" size="45" value="<c:out value='${patient.phone}' />" /></td>
            </tr>
            <tr>
                <th>Email: </th>
                <td><input type="text" name="email" size="45" value="<c:out value='${patient.email}' />" /></td>
            </tr>
            <tr>
                <th>Ngày sinh (YYYY-MM-DD): </th>
                <td><input type="text" name="dob" size="45" value="<c:out value='${patient.dob}' />" placeholder="1999-12-31" required /></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="Lưu dữ liệu" /></td>
            </tr>
        </table>
        </form>
    </div>
</body>
</html>