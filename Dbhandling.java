//$Id$
package com.whatsapp.rest.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dbhandling{
	public static int insertCustomer(String id,String name,String email,String line1,String city,String state,String country,String phone)
	{
		Connection con = null;
		int inserted_or_not = 0;
		try{
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/stripe_gateway";
			String dbuser = "root";
			String dbpass = "";
			con =  DriverManager.getConnection(url, dbuser, dbpass);
			
			String insert_query = "insert into customer(id,email,name,line1,city,state,country,phone) values(?,?,?,?,?,?,?,?)";
			
			PreparedStatement pstmt = con.prepareStatement(insert_query);
			
			pstmt.setString(1,id);
			pstmt.setString(2,name);
			pstmt.setString(3,email);
			pstmt.setString(4,line1);
			pstmt.setString(5,city);
			pstmt.setString(6,state);
			pstmt.setString(7,country);
			pstmt.setString(8,phone);
		
			inserted_or_not = pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
			return inserted_or_not;
			
	}
	public static int insertPaymentMethod(String stripe_c_id,String name,String id,String last4,String brand_name,String type,String funding,long expiry_month,long expiry_year)
	{
		Connection con = null;
		int inserted_or_not = 0;
		try{
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/stripe_gateway";
			String dbuser = "root";
			String dbpass = "";
			con =  DriverManager.getConnection(url, dbuser, dbpass);
			
			String insert_query = "insert into payment_methods(c_pk_id,name,id,last4,brand_name,type,funding,expiry_month,expiry_year) values(?,?,?,?,?,?,?,?,?)";
			
			String select_query = "select pk_id from customer where id = ?";
			
			
			PreparedStatement pstmt = con.prepareStatement(select_query);
			pstmt.setString(1,stripe_c_id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			
			int c_pk_id = rs.getInt(1);
				 
			pstmt = con.prepareStatement(insert_query);
			pstmt.setInt(1,c_pk_id);
			pstmt.setString(2,name);
			pstmt.setString(3,id);
			pstmt.setString(4,last4);
			pstmt.setString(5,brand_name);
			pstmt.setString(6,type);
			pstmt.setString(7,funding);
			pstmt.setLong(8,expiry_month);
			pstmt.setLong(9,expiry_year);
			
			inserted_or_not = pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
			return inserted_or_not;
			
	}
	public static String getName(String c_id)
	{
		Connection con = null;
		String name ="";
		try{
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/stripe_gateway";
			String dbuser = "root";
			String dbpass = "";
			con =  DriverManager.getConnection(url, dbuser, dbpass);
			
			String select_query = "select name from customer where id = ?";
			
			
			PreparedStatement pstmt = con.prepareStatement(select_query);
			pstmt.setString(1,c_id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			 name = rs.getString(1);
		}	
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return name;
		
	}
}
