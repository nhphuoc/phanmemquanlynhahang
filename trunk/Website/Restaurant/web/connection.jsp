<%@page import="org.apache.jasper.tagplugins.jstl.core.Out"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.CallableStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
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
        <title></title>
    </head>
    <body>
        <%!
            public Connection conn() {
                Connection cn = null;
                try {
                    String db = "vttu_restaurant?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true";
                    String usr = "root";
                    String pass = "";
                    String ipdb = "localhost";
                    int port = 3306;
                    Class.forName("com.mysql.jdbc.Driver");
                    String database = "jdbc:mysql://" + ipdb + ":" + port + "/" + db;
                    // String database = "jdbc:mysql://localhost:3306/vttu_restaurant";
                    cn = DriverManager.getConnection(database, usr, pass);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
                return cn;
            }
        %>
    </body>
</html>



