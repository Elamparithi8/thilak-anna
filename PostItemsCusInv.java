package com.company;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PostItemsCusInv {
    public static void writeRequest(HttpURLConnection books_url,JSONObject key_value)
    {
        OutputStream out_stream = null;
        try{

            out_stream = books_url.getOutputStream();


            StringBuilder postData = new StringBuilder();

            postData.append(URLEncoder.encode("JSONString", "UTF-8"));

            postData.append("=");

            postData.append(URLEncoder.encode(key_value.toString(), "UTF-8"));

            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            out_stream.write(postDataBytes);


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (out_stream != null) {
                try {
                    out_stream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void readResponse(HttpURLConnection books_url)
    {   BufferedReader br = null;
        String json = "";
        try{
            br = new BufferedReader(new InputStreamReader(books_url.getInputStream()));
            while(br.ready())
            {

                json+=br.readLine()+"\n";

            }
            System.out.println(json);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void responseData(int code,String res_mssg)
    {
        if(code==201){
            System.out.println(res_mssg+" :-)");
        }
        else if(code==401)
        {
            System.out.println("Invalid Access token");
        }
        else
        {
            System.out.println("Some internal error");
        }
    }
    public static HttpURLConnection intializeConnection(String urls,String ac_to)
    {
        HttpURLConnection books_url = null;
        URL url = null;
        String access_token = ac_to;

        try {
            url = new URL(urls);
            books_url = (HttpURLConnection) url.openConnection();

            books_url.setRequestMethod("POST");

            books_url.setRequestProperty("Authorization", "Zoho-oauthtoken " + access_token);

            books_url.setRequestProperty("Content_Type", "application/x-www-form-urlencoded;charset=UTF-8");

            books_url.setDoOutput(true);
            books_url.setDoInput(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return books_url;
    }
    public static void createItem(String name,int rate,String item_type,String purchase_rate)
    {
        try {
            HttpURLConnection books_url = intializeConnection("https://books.zoho.com/api/v3/items?organization_id=725748814","1000.6f5a1cbffbb03231f8438e91df14816e.ae4722ea8151494aa9281d8194b296b6");

            JSONObject key_value = new JSONObject();
            key_value.put("name",name);
            key_value.put("rate",rate);
            key_value.put("purchase_rate",purchase_rate);
            key_value.put("item_type",item_type);

            writeRequest(books_url,key_value);
            readResponse(books_url);
            int code = books_url.getResponseCode();
            responseData(code,"Item added successfully :-)");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void createContacts(String name,String email,String num)
    {
        try {
            HttpURLConnection books_url = intializeConnection("https://books.zoho.com/api/v3/contacts?organization_id=725748814","1000.6f5a1cbffbb03231f8438e91df14816e.ae4722ea8151494aa9281d8194b296b6");

            JSONObject key_value = new JSONObject();

            key_value.put("contact_name",name);

            JSONArray array = new JSONArray();

            JSONObject contact_person = new JSONObject();

            key_value.put("contact_persons",array);

            contact_person.put("email",email);
            contact_person.put("mobile",num);
            array.put(contact_person);
            System.out.println(key_value);
            OutputStream out_stream = null;
            try{

                out_stream = books_url.getOutputStream();


                StringBuilder postData = new StringBuilder();

                postData.append(URLEncoder.encode("JSONString", "UTF-8"));

                postData.append("=");

                postData.append(URLEncoder.encode(key_value.toString(), "UTF-8"));

                byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                out_stream.write(postDataBytes);


            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                out_stream.flush();
            }
            readResponse(books_url);
            int code = books_url.getResponseCode();
            responseData(code,"Customer added successfully :-)");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void createInvoice(String cus_id,String inv_num,String item_id,int quant,String gateway,boolean confi)
    {
        try {

            HttpURLConnection books_url = intializeConnection("https://books.zoho.com/api/v3/invoices?organization_id=725748814","1000.6f5a1cbffbb03231f8438e91df14816e.ae4722ea8151494aa9281d8194b296b6");

            JSONObject key_value = new JSONObject();

            key_value.put("customer_id",cus_id);
            key_value.put("invoice_number",inv_num);

            JSONArray array = new JSONArray();

            JSONObject line_items = new JSONObject();

            line_items.put("item_id",item_id);
            line_items.put("quantity",quant);
            array.put(line_items);
            JSONObject all_gat = new JSONObject();
            key_value.put("line_items",array);

            JSONObject gat_json = new JSONObject();
            gat_json.put("gateway_name",gateway);
            JSONArray gat_array = new JSONArray();
            gat_array.put(gat_json);
            all_gat.put("payment_gateways",gat_array);
            key_value.put("payment_options",all_gat);
            System.out.println(key_value);

            writeRequest(books_url,key_value);
            readResponse(books_url);

            int code = books_url.getResponseCode();
            responseData(code,"Invoice created successfully :-)");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        //createItem("ak47",23000,"sales","21000");
        //createContacts("Mitchel Santer","elamparithi.v+01@zohotest.com","8976543210");
        createInvoice("2367932000000083001","INV-000002","2367932000000077002",3,"stripe",true);
    }
}
