/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import org.xml.sax.XMLReader;
import vn.edu.vttu.ui.loadConnection;

/**
 *
 * @author nhphuoc
 */
public class readxml {

    public static void readXMLinfo() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(XMLReader.class.getResourceAsStream("/config/config.xml"));
            doc.getDocumentElement().normalize();
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            /**
             * Database and server infomation
             */
            NodeList nList = doc.getElementsByTagName("serverconfig");
            Node nNode = nList.item(0);            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                // Địa chỉ máy chứa database
                InfoRestaurant.setIPdatabase(eElement.getElementsByTagName("IPdatabase").item(0).getTextContent());
                // cổng database
                InfoRestaurant.setPortdatabase(Integer.parseInt(eElement.getElementsByTagName("portdatabase").item(0).getTextContent()));
                // Tên database
                InfoRestaurant.setNamedatabase(eElement.getElementsByTagName("namedatabase").item(0).getTextContent());
                // tài khoản đăng nhập database
                InfoRestaurant.setUsernamedatabase(eElement.getElementsByTagName("usernamedatabase").item(0).getTextContent());
                // mật khẩu truy cập database
                InfoRestaurant.setPassdatabase(eElement.getElementsByTagName("passdatabase").item(0).getTextContent());
                // địa chỉ máy server
                InfoRestaurant.setIPserver(eElement.getElementsByTagName("IPserver").item(0).getTextContent());
                // tên đăng nhập server
                InfoRestaurant.setUsernameserver(eElement.getElementsByTagName("usernameServer").item(0).getTextContent());
                // mật khẩu đăng nhập server
                InfoRestaurant.setPassserver(eElement.getElementsByTagName("passSserver").item(0).getTextContent());
                
                

                //System.out.println("Staff id : " + eElement.getAttribute("id"));               
            }
            
            
            // Thông Tin Nhà Hàng
            NodeList nListInfo = doc.getElementsByTagName("info");
            Node nNodeInfo = nListInfo.item(0);            
            if (nNodeInfo.getNodeType() == Node.ELEMENT_NODE) {
                Element eElementInfo = (Element) nNodeInfo;
                // Tên Nhà Hàng
                InfoRestaurant.setName(eElementInfo.getElementsByTagName("name").item(0).getTextContent());                
                // Điện thoại
                InfoRestaurant.setPhone(eElementInfo.getElementsByTagName("phone").item(0).getTextContent());
                // Địa chỉ email
                InfoRestaurant.setEmail(eElementInfo.getElementsByTagName("email").item(0).getTextContent());
                // Địa chỉ
                InfoRestaurant.setAddress(eElementInfo.getElementsByTagName("address").item(0).getTextContent());
                // Link hình ảnh
                InfoRestaurant.setLogo(eElementInfo.getElementsByTagName("logo").item(0).getTextContent());
                // Thời gian cho phép đặt bàn
                InfoRestaurant.setHourAcceptReservationNomal(Integer.parseInt(eElementInfo.getElementsByTagName("hourAcceptReservationNomal").item(0).getTextContent()));
                // Thời gian cho phép đặt tiệc
                InfoRestaurant.setHourAcceptReservationParty(Integer.parseInt(eElementInfo.getElementsByTagName("hourAcceptReservationParty").item(0).getTextContent()));
                // Thời gian cảnh báo người dùng
                InfoRestaurant.setMinuteWarningCustomer(Integer.parseInt(eElementInfo.getElementsByTagName("minuteWarningCustomer").item(0).getTextContent()));                        
                
                InfoRestaurant.setThank(eElementInfo.getElementsByTagName("thank").item(0).getTextContent());                        
                
            }
            
        } catch (Exception e) {
        }
    }
}
