package com.gougou.httpretry;

import java.util.Scanner;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class CloseConnectionExample {

    public static void main(String args[])throws Exception{

        //Create an HttpClient object
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try{
            //Create an HttpGet object
            HttpGet httpget = new HttpGet("http://www.tutorialspoint.com/");

            //Execute the Get request
            CloseableHttpResponse httpresponse = httpclient.execute(httpget);

            try{
                Scanner sc = new Scanner(httpresponse.getEntity().getContent());
                while(sc.hasNext()) {
                    System.out.println(sc.nextLine());
                }
            }finally{
                httpresponse.close();
            }
        }finally{
            httpclient.close();
        }
    }
}
