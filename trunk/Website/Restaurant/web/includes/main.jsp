
<%@page import="java.sql.ResultSet"%>
<%-- 
    Document   : main
    Created on : Mar 14, 2014, 8:30:03 PM
    Author     : nhphuoc
--%>

<%@page import="java.sql.Statement"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
    </head>
    <body>
        <div class="contentright">    

            <%@include file="../connection.jsp"%>
            <table width="100%" cellpadding="5" cellspacing="5">

                <%
                    String sql = "call service_get_all";
                    Statement st = conn().createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    int n = 0;
                    //rs.last();
                    n = 9;
                    //rs.beforeFirst();                   
                    for (int i = 0; i <3; i++) {
                        out.print("<tr style='padding:10px'>");
                        for (int j = 0; j < 3; j++) {
                            if(rs.next()){                                
                            out.print("<td>"
                                    + "<div class='product'><center>"
                                    + "<img src='"+rs.getString("image") +"' width='200' height='150'/></center>"
                                    + "<b><font color='blue'>"+rs.getString(2)+"</font></b><br>"                                            
                                    +"<b>Đơn Giá: "+rs.getString(5) +"</b><br>"
                                    +"<b>Đơn Vị Tính: "+rs.getString(4) +"</b>"
                                    + "</div>"
                                    +"</td>");
                            }
                        }
                        out.print("</tr>");
                    }

                %>
            </table>            

        </div>
    </body>
</html>
