package com.whatsapp.rest.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class Stripe 
{
	public static int parseCustomerDB(String json)
	{
		JSONParser parser = new JSONParser(); 
		JSONObject json_object;
		int  check = 0;
		try {
			json_object = (JSONObject) parser.parse(json);
			String name = (String) json_object.get("name");
			String id = (String) json_object.get("id");
			String email = (String) json_object.get("email");
			String phone = (String) json_object.get("phone");
			json_object = (JSONObject) json_object.get("address");				
			String line1 = (String) json_object.get("line1");
			String city = (String) json_object.get("city");
			String state = (String) json_object.get("state");;
			String country = (String) json_object.get("country");
			check = Dbhandling.insertCustomer(id,email,name,line1,city,state,country,phone);
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return check;
	}
	
	public static int parsePaymentDB(String c_id,String json,int diff_json)
	{
		JSONParser parser = new JSONParser(); 
		int check = 0;
		try{
			JSONObject json_object = (JSONObject) parser.parse(json);
			
			if(diff_json==0){
				JSONArray json_array = (JSONArray) json_object.get("data");	
				json_object = (JSONObject) (json_array.get(0));
				//System.out.println(json_object);
				String id = (String) json_object.get("id");
				String type = (String) json_object.get("type");
				json_object = (JSONObject) (json_object.get("billing_details"));
				String name = (String) json_object.get("name");
				json_object = (JSONObject) parser.parse(json);
				json_array = (JSONArray) json_object.get("data");
				json_object = (JSONObject) (json_array.get(0));
				json_object = (JSONObject) (json_object.get("card"));
				String brand = (String) json_object.get("brand");
				long expiry_month =  (Long) json_object.get("exp_month");					
				long expiry_year =   (Long) json_object.get("exp_year");
				String funding = (String) json_object.get("funding");
				String last4 = (String) json_object.get("last4");
				check = Dbhandling.insertPaymentMethod(c_id,name,id,last4,brand,type,funding,expiry_month,expiry_year);
			}
			else{
				String id = (String) json_object.get("id");
				String name = Dbhandling.getName(c_id);
				String type = (String) json_object.get("type");
				json_object = (JSONObject) (json_object.get("card"));
				String brand = (String) json_object.get("brand");
				long expiry_month =  (Long) json_object.get("exp_month");					
				long expiry_year =   (Long) json_object.get("exp_year");
				String funding = (String) json_object.get("funding");
				String last4 = (String) json_object.get("last4");
				check = Dbhandling.insertPaymentMethod(c_id,name,id,last4,brand,type,funding,expiry_month,expiry_year);
			}
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return check;
		
		
	
	}
	public static void getCustomer(String c_id,String apikey)
	{
		try {
		HttpURLConnection stripe_url = null;
		BufferedReader br = null;
		URL url = null;
		String json = "";
		try {
			url = new URL("https://api.stripe.com/v1/customers/"+c_id);
			
			stripe_url =  (HttpURLConnection) url.openConnection();
			
			stripe_url.setRequestMethod("GET");
			
			stripe_url.setRequestProperty("Authorization", "Bearer "+apikey);
			
			System.out.println(stripe_url.getResponseMessage());
			
			System.out.println(stripe_url.getHeaderFieldKey(1));
			
			br = new BufferedReader(new InputStreamReader(stripe_url.getInputStream()));
			
			while(br.ready())
			{
			
				json+=br.readLine()+"\n";
			
			}
			
			
			//System.out.println(json);
			
				
			
			int check = parseCustomerDB(json);
			if(check>0)
			{
				System.out.println("inserted in db");
			}
			else
			{
				System.out.println("exception occured");
			}
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
			
		}
		finally
		{
				br.close();
		}
	}
	catch (Exception e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}	
	
	}
	public static void getPaymentMethods(String c_id,String apikey)
	{
		try {
			HttpURLConnection stripe_url = null;
			BufferedReader br = null;
			URL url = null;
			String json = "";
			try {
				url = new URL("https://api.stripe.com/v1/payment_methods?customer="+c_id+"&type=card");
				
				stripe_url =  (HttpURLConnection) url.openConnection();
				
				stripe_url.setRequestMethod("GET");
				
				stripe_url.setRequestProperty("Authorization", "Bearer "+apikey);
				
				System.out.println(stripe_url.getResponseMessage());
				
				br = new BufferedReader(new InputStreamReader(stripe_url.getInputStream()));
				
				while(br.ready())
				{
				
					json+=br.readLine()+"\n";
				
				}
				
				
				//System.out.println(json);
				
				int check = parsePaymentDB(c_id,json,0);
				
				if(check>0)
				{
					System.out.println("inserted in db");
				}
				else
				{
					System.out.println("exception occured");
				}
			
			}
			catch (IOException e)
			{
				e.printStackTrace();
				
			}
			finally
			{
					br.close();
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}	
	}
	public static void createCustomer(String apikey,String name,String line1,String city,String code,String state,String email,String phone,String country)
	{
		try {
			HttpURLConnection stripe_url = null;
			BufferedReader br = null;
			URL url = null;
			String json = "";
			try {
				url = new URL("https://api.stripe.com/v1/customers");
				
				stripe_url =  (HttpURLConnection) url.openConnection();
				
				stripe_url.setRequestMethod("POST");
				
				stripe_url.setRequestProperty("Authorization", "Bearer "+apikey);
				
				stripe_url.setRequestProperty("Content_Type", "application/x-www-form-urlencoded");
				
				stripe_url.setDoOutput(true);
				
				Map<String,Object> params = new LinkedHashMap<String, Object>();
				
		
		        params.put("name", name);
		        params.put("address[line1]",line1);
		        params.put("address[city]",city);
		        params.put("address[postal_code]",code);
		        params.put("address[country]",country);
		        params.put("address[state]",state);
		        params.put("email",email);
		        params.put("phone", phone);
		        

		        StringBuilder postData = new StringBuilder();
		        
		        for (Map.Entry<String,Object> param : params.entrySet()) {
		            if (postData.length() != 0) postData.append('&');
		            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
		            postData.append('=');
		            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		        }
		        System.out.println(postData);
		        
		        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
				
				
		        OutputStream out_stream = null;
				try{
					
				out_stream = stripe_url.getOutputStream();
				
				out_stream.write(postDataBytes);
				
				
				System.out.println(stripe_url.getResponseMessage());
				
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					out_stream.flush();
				}
				
				br = new BufferedReader(new InputStreamReader(stripe_url.getInputStream()));
				
				while(br.ready())
				{
				
					json+=br.readLine()+"\n";
				
				}
				
				
				System.out.println(json);
				
				int check = parseCustomerDB(json);
				
				if(check>0)
				{
					System.out.println("inserted in db");
				}
				else
				{
					System.out.println("exception occured");
				}
			
			}
			catch (IOException e)
			{
				e.printStackTrace();
				
			}
			finally
			{
					br.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void createPayment(String apikey,String type,String number,String exp_month,String exp_year,String cvc,String c_id){
		try {
			HttpURLConnection stripe_url = null;
			BufferedReader br = null;
			URL url = null;
			String json = "";
			try {
				url = new URL("https://api.stripe.com/v1/payment_methods");
				
				stripe_url =  (HttpURLConnection) url.openConnection();
				
				stripe_url.setRequestMethod("POST");
				
				stripe_url.setRequestProperty("Authorization", "Bearer "+apikey);
				
				stripe_url.setRequestProperty("Content_Type", "application/x-www-form-urlencoded");
				
				stripe_url.setDoOutput(true);
				
				Map<String,Object> params = new LinkedHashMap<String, Object>();
				
		
		        params.put("type",type);
		        params.put("card[number]",number);
		        params.put("card[exp_month]",exp_month);
		        params.put("card[exp_year]",exp_year);
		        params.put("card[cvc]",cvc);		        

		        StringBuilder postData = new StringBuilder();
		        
		        for (Map.Entry<String,Object> param : params.entrySet()) {
		            if (postData.length() != 0) postData.append('&');
		            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
		            postData.append('=');
		            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		        }
		        System.out.println(postData);
		        
		        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
				
				
		        OutputStream out_stream = null;
				try{
					
				out_stream = stripe_url.getOutputStream();
				
				out_stream.write(postDataBytes);
				
				
				System.out.println(stripe_url.getResponseMessage());
				
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					out_stream.flush();
				}
				
				br = new BufferedReader(new InputStreamReader(stripe_url.getInputStream()));
				
				while(br.ready())
				{
				
					json+=br.readLine()+"\n";
				
				}
				
				
				System.out.println(json);
				JSONParser parser = new JSONParser(); 
				JSONObject json_object = (JSONObject) parser.parse(json);
				attachPaymentToCustomer(apikey,(String) json_object.get("id"),c_id);
			
			}
			catch (IOException e)
			{
				e.printStackTrace();
				
			}
			finally
			{
					br.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void attachPaymentToCustomer(String apikey,String pay_id,String c_id)
	{
		try {
			HttpURLConnection stripe_url = null;
			BufferedReader br = null;
			URL url = null;
			String json = "";
			try {
				url = new URL("https://api.stripe.com/v1/payment_methods/"+pay_id+"/attach");
				
				stripe_url =  (HttpURLConnection) url.openConnection();
				
				stripe_url.setRequestMethod("POST");
				
				stripe_url.setRequestProperty("Authorization", "Bearer "+apikey);
				
				stripe_url.setRequestProperty("Content_Type", "application/x-www-form-urlencoded");
				
				stripe_url.setDoInput(true);
				
				stripe_url.setDoOutput(true);
				
				
				
				Map<String,Object> params = new LinkedHashMap<String, Object>();
				
		        params.put("customer", c_id);
		        
		        StringBuilder postData = new StringBuilder();
		        
		        for (Map.Entry<String,Object> param : params.entrySet()) {
		            if (postData.length() != 0) postData.append('&');
		            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
		            postData.append('=');
		            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		        }
		        System.out.println(postData);
		        
		        byte[] postDataBytes = postData.toString().getBytes("UTF-8");			
				
		        OutputStream out_stream = null;
				try{
					
				out_stream = stripe_url.getOutputStream();
				
				out_stream.write(postDataBytes);
				
				
				System.out.println(stripe_url.getResponseMessage());
				
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					out_stream.flush();
				}
				
				br = new BufferedReader(new InputStreamReader(stripe_url.getInputStream()));
				
				while(br.ready())
				{
				
					json+=br.readLine()+"\n";
				
				}
				
				
				System.out.println(json);
				
				int check = parsePaymentDB(c_id,json,1);
				
				if(check>0)
				{
					System.out.println("inserted in db");
				}
				else
				{
					System.out.println("exception occured");
				}
			
			}
			catch (IOException e)
			{
				e.printStackTrace();
				
			}
			finally
			{
					br.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void main( String[] args )
    {
		//getCustomer("cus_HpKcuyeqG10bk3","sk_test_51HDUSHKdaZhEhwgpgGK6dtMexQRs4NedNA2xI4HUmeJ0EVfsAQ5yASwS4Vuv5DXynqiNl5hTFhqgApZ8f5ubddA200jC8JpZAF");
		//getPaymentMethods("cus_HpKaHEgjuvvoGQ","sk_test_51HDUSHKdaZhEhwgpgGK6dtMexQRs4NedNA2xI4HUmeJ0EVfsAQ5yASwS4Vuv5DXynqiNl5hTFhqgApZ8f5ubddA200jC8JpZAF");
		//createCustomer("sk_test_51HDUSHKdaZhEhwgpgGK6dtMexQRs4NedNA2xI4HUmeJ0EVfsAQ5yASwS4Vuv5DXynqiNl5hTFhqgApZ8f5ubddA200jC8JpZAF","Steve Austin","10 ,  St Flr, Prashdham Bldh,  Narayan Dhuruv Street","Mumbai","400003","MH","parithi002@gmail.com","+919360619395","IN");	
		createPayment("sk_test_51HDUSHKdaZhEhwgpgGK6dtMexQRs4NedNA2xI4HUmeJ0EVfsAQ5yASwS4Vuv5DXynqiNl5hTFhqgApZ8f5ubddA200jC8JpZAF","card","4242424242424242","8","24","123","cus_HpKcuyeqG10bk3");
    }
}
