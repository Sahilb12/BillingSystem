
package com.database;

import java.sql.*;
import java.util.ArrayList;

public class AnalysisRecord {
    
    public static ArrayList<ArrayList<String>> top10Food()
    {
         ArrayList<ArrayList<String>> a = new ArrayList<>();
        try
        {
            Connection con = DbConnect.takeConnection();
            String query="select sum(quantity),item_name from orders group by item_name order by item_name desc limit 10";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                ArrayList<String> b = new ArrayList<>();
                b.add(rs.getString(1));
                b.add(rs.getString(2));
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
    
    public static ArrayList<ArrayList<String>> top10Customers()
    {
         ArrayList<ArrayList<String>> a = new ArrayList<>();
        try
        {
            Connection con = DbConnect.takeConnection();
            String query="select count(customer_contact),customer_contact from customer group by customer_contact limit 10";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                ArrayList<String> b = new ArrayList<>();
                b.add(rs.getString(1));
                b.add(rs.getString(2));
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
    
    public static ArrayList<ArrayList<String>> betweenDates(String date1, String date2)
    {
         ArrayList<ArrayList<String>> a = new ArrayList<>();
        try
        {
            Connection con = DbConnect.takeConnection();
            String query="select sum(bill_amount), date from customer where date between ? and ? group by date order by date";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, date1);
            ps.setString(2, date2);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                ArrayList<String> b = new ArrayList<>();
                b.add(rs.getString(1));
                b.add(rs.getString(2));
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
    
    public static ArrayList<ArrayList<String>> yearWise(String year)
    {
         ArrayList<ArrayList<String>> a = new ArrayList<>();
        try
        {
            Connection con = DbConnect.takeConnection();
            String query="select date, bill_amount from customer where date like '"+year+"%' order by date";
            PreparedStatement ps = con.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                ArrayList<String> b = new ArrayList<>();
                b.add(rs.getString(1));
                b.add(rs.getString(2));
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
}
