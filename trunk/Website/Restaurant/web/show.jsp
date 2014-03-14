<%-- 
    Document   : show
    Created on : Mar 11, 2014, 11:49:42 PM
    Author     : nhphuoc
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="connection.jsp" %>
        <%
            String sql = "select * from usr";
            Statement st = conn().createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                out.print(rs.getString(2));
            }
        %>
    </body>
</html>
