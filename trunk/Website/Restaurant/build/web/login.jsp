<%-- 
    Document   : login
    Created on : Mar 12, 2014, 12:04:23 AM
    Author     : nhphuoc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng Nhập</title>
    </head>
    <body>
    <center><h1>ĐĂNG NHẬP HỆ THỐNG</h1></center>
    <form action="Controller" method="post" name="F">
        <table style="margin: auto">
            <tr>
                <td>Tên đăng nhập</td>
                <td><input type="text" name="user" value=""/></td>
            </tr>
            <tr>
                <td>Mật Khẩu</td>
                <td><input type="text" name="pass" value=""/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" name="sb" value="Login" onclick="document.F.submit();"/></td>
            </tr>
        </table>
    </form>   
    </body>
</html>
