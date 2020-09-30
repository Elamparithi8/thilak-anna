package com.company;



import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PostItemsCusInv {
    public static void createItem(String ac_to,String name,int rate,String item_type,String purchase_rate)
    {
        try {
            HttpURLConnection books_url = null;
            BufferedReader br = null;
            URL url = null;
            String json = "";
            String access_token = ac_to;

            url = new URL("https://books.zoho.com/api/v3/items?organization_id=725748814");

            books_url = (HttpURLConnection) url.openConnection();

            books_url.setRequestMethod("POST");

            books_url.setRequestProperty("Authorization", "Zoho-oauthtoken "+access_token);

            books_url.setRequestProperty("Content_Type", "application/x-www-form-urlencoded;charset=UTF-8");

            books_url.setDoOutput(true);


            JSONObject key_value = new JSONObject();
            key_value.put("name",name);
            key_value.put("rate",rate);
            key_value.put("purchase_rate",purchase_rate);
            key_value.put("item_type",item_type);



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

            br = new BufferedReader(new InputStreamReader(books_url.getInputStream()));

            while(br.ready())
            {

                json+=br.readLine()+"\n";

            }
            System.out.println(json);

            int code = books_url.getResponseCode();
            if(code==201){
                System.out.println("Item added :-)");
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
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void createContacts(String ac_to,String name,String email,String num)
    {
        try {
            HttpURLConnection books_url = null;
            BufferedReader br = null;
            URL url = null;
            String json = "";
            String access_token = ac_to;

            url = new URL("https://books.zoho.com/api/v3/contacts?organization_id=725748814");

            books_url = (HttpURLConnection) url.openConnection();

            books_url.setRequestMethod("POST");

            books_url.setRequestProperty("Authorization", "Zoho-oauthtoken "+access_token);

            books_url.setRequestProperty("Content_Type", "application/x-www-form-urlencoded;charset=UTF-8");

            books_url.setDoOutput(true);
            books_url.setDoInput(true);


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

            br = new BufferedReader(new InputStreamReader(books_url.getInputStream()));

            while(br.ready())
            {

                json+=br.readLine()+"\n";

            }
            System.out.println(json);

            int code = books_url.getResponseCode();
            if(code==201){
                System.out.println("Contact added :-)");
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
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void createInvoice(String ac_to,String cus_id,String inv_num,String item_id,int quant,String gateway,boolean confi)
    {
        try {
            HttpURLConnection books_url = null;
            BufferedReader br = null;
            URL url = null;
            String json = "";
            String access_token = ac_to;

            url = new URL("https://books.zoho.com/api/v3/invoices?organization_id=725748814");

            books_url = (HttpURLConnection) url.openConnection();

            books_url.setRequestMethod("POST");

            books_url.setRequestProperty("Authorization", "Zoho-oauthtoken "+access_token);

            books_url.setRequestProperty("Content_Type", "application/x-www-form-urlencoded;charset=UTF-8");

            books_url.setDoOutput(true);
            books_url.setDoInput(true);


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

            br = new BufferedReader(new InputStreamReader(books_url.getInputStream()));

            while(br.ready())
            {

                json+=br.readLine()+"\n";

            }
            System.out.println(json);

            int code = books_url.getResponseCode();
            if(code==201){
                System.out.println("Invoice added :-)");
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
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        //createItem("1000.088b0e99d33ec6f05eadda30f1ca31fc.4b9768c29ac3cc796a8662685abef536","ak47",23000,"sales","21000");
        //createContacts("1000.e91761fee60cb6d72489a0b69469d0e6.9b88c9a94da9aeadcfaa1e9075266764","Mitchel Starc","elamparithi.v+01@zohotest.com","9876543213");
        //createInvoice("1000.17f21737b1cd9fadb0d9fe546a028519.2f1057532995cc066a1404806ec488b6","2367932000000083001","INV-000001","2367932000000077002",3,"stripe",true);
    }
}
