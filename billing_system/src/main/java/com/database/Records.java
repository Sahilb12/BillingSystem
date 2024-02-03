
package com.database;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Records {
    
    public static boolean checkLogin(String username,String password,String department)
    {
        boolean status=false;
       try
       {
           Connection con = DbConnect.takeConnection();
           String query = "select * from user_info where username=? and password=? and department=?";
           PreparedStatement ps = con.prepareStatement(query);
           ps.setString(1, username);
           ps.setString(2,password);
           ps.setString(3,department);
           
           ResultSet rs = ps.executeQuery();
           while(rs.next())
           {
               status = true;
               break;
           }
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }
        return status;
    }
    
    public static ArrayList<ArrayList<String>> getAllItems()
    {
       ArrayList<ArrayList<String>> a = new ArrayList<ArrayList<String>>();
       try
       {
           Connection con = DbConnect.takeConnection();
           String query = "select item_name,price from food_items order by item_name";
           PreparedStatement ps = con.prepareStatement(query);
     
           ResultSet rs = ps.executeQuery();
           while(rs.next())
           {
               ArrayList<String> b = new ArrayList<String>();
               b.add(rs.getString(1));
               b.add(rs.getString(2));
               a.add(b);
           }
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }
        return a;
    }
    
    public static String getPrice(String item)
    {
       String price = null;
       try
       {
           Connection con = DbConnect.takeConnection();
           String query = "select price from food_items where item_name=?";
           PreparedStatement ps = con.prepareStatement(query);
           ps.setString(1, item);
           ResultSet rs = ps.executeQuery();
           while(rs.next())
           {
               price = rs.getString(1);
               break;
           }
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }
        return price;
    }
    
    public static int orderDetails(String cashier, String customer_name, String customer_contact, double grandTotal)
    {
        int status=0;
           try
           {
               Connection con = DbConnect.takeConnection();
               String query = "insert into customer(customer_name,customer_contact,bill_amount,date,cashier) values(?,?,?,?,?)";
               PreparedStatement ps = con.prepareStatement(query);
               ps.setString(1,customer_name);
               ps.setString(2,customer_contact);
               ps.setString(3,""+grandTotal);
               
               java.util.Date d = new java.util.Date();
               SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
               String date = sdf.format(d);
               
               ps.setString(4,date);
               ps.setString(5,cashier);
               
               status = ps.executeUpdate();
               con.close();
           }
           catch(Exception e)
           {
               e.printStackTrace();
           }
        return status;
    }
    
    public static int customerOrder(String cashier, String customer_name, String customer_contact, double grandTotal, ArrayList<ArrayList<String>> order)
    {
        int order_status=0;
        int status = orderDetails(cashier,customer_name,customer_contact,grandTotal);
        
        if(status==1)
        {
            int billNo = getLastBillNO();
            
            try
           {
               Connection con = DbConnect.takeConnection();
               String query = "insert into orders(item_name,price,quantity,total,bill_no) values(?,?,?,?,?)";
               PreparedStatement ps = con.prepareStatement(query);
              con.setAutoCommit(false);
               
               for(ArrayList<String> a: order)
               {
                   ps.setString(1, a.get(1));
                   ps.setString(2, a.get(2));
                   ps.setString(3, a.get(3));
                   ps.setString(4, a.get(4));
                   ps.setInt(5, billNo);
                   ps.addBatch();
               }
               
               ps.executeBatch();
               con.commit();
               order_status=1;
               con.close();
           }
           catch(Exception e)
           {
               e.printStackTrace();
           }
        }   
        return order_status;
    }
    
    public static int getLastBillNO()
    {
        int billNo=0;
           try
           {
               Connection con = DbConnect.takeConnection();
               String query = "select bill_no from customer order by bill_no desc";
               PreparedStatement ps = con.prepareStatement(query);
              
               ResultSet rs = ps.executeQuery();
               while(rs.next())
               {
                   billNo = rs.getInt(1);
                   break;
               }
               con.close();
           }
           catch(Exception e)
           {
               e.printStackTrace();
           }
        return billNo;
    }
    
    public static ArrayList<ArrayList<String>> getAllFoodItems()
    {
        ArrayList<ArrayList<String>> a = new ArrayList<>();
           try
           {
               Connection con = DbConnect.takeConnection();
               String query = "select * from food_items order by item_name";
               PreparedStatement ps = con.prepareStatement(query);
              
               ResultSet rs = ps.executeQuery();
               while(rs.next())
               {
                    ArrayList<String> b = new ArrayList<String>();
                    b.add(rs.getString(1));
                    b.add(rs.getString(2));
                    b.add(rs.getString(3));
                    a.add(b);
               }
               con.close();
           }
           catch(Exception e)
           {
               e.printStackTrace();
           }
        return a;
    }
    
    public static int setFoodItem(String item_name,String item_price)
    {
       int status = 0;
           try
           {
               Connection con = DbConnect.takeConnection();
               String query = "insert into food_items(item_name,price) values(?,?)";
               PreparedStatement ps = con.prepareStatement(query);
               ps.setString(1, item_name);
               ps.setString(2, item_price);
               status = ps.executeUpdate();
               con.close();
           }
           catch(Exception e)
           {
               e.printStackTrace();
           }
        return status;
    }
    
    
}

