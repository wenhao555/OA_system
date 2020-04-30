package com.utils;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Objects;

public class UrlConnectionUtil{

    //public static final String IP = "http://10.128.162.248:8080";
    public static final String IP = "http://62.234.8.132:8080";

    public String sendRequest(String urlParam) {

        HttpURLConnection con = null;

        BufferedReader buffer = null;
        StringBuffer resultBuffer = null;

        try {
            URL url = new URL(urlParam);
            con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");

            // 设置请求需要返回的数据类型和字符集类型
            con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            // 允许写出
            con.setDoOutput(true);
            // 允许读入
            con.setDoInput(true);
            // 不使用缓存
            con.setUseCaches(false);
            // 得到响应流
            InputStream inputStream = con.getInputStream();
            // 将响应流转换成字符串
            resultBuffer = new StringBuffer();

            String line;
            buffer = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            while ((line = buffer.readLine()) != null) {
                resultBuffer.append(line);
            }
            return resultBuffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public String sendRequest(String urlParam, String paramList) {

        HttpURLConnection con = null;

        BufferedReader buffer = null;
        StringBuffer resultBuffer = null;

        try {
            URL url = new URL(urlParam);
            con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");

            // 设置请求需要返回的数据类型和字符集类型
            con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            // 允许写出
            con.setDoOutput(true);
            // 允许读入
            con.setDoInput(true);
            // 不使用缓存
            con.setUseCaches(false);


            OutputStream out = con.getOutputStream();

            out.write(paramList.getBytes());

            out.flush();

            out.close();

            // 得到响应流
            InputStream inputStream = con.getInputStream();
            // 将响应流转换成字符串
            resultBuffer = new StringBuffer();

            String line;
            buffer = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            while ((line = buffer.readLine()) != null) {
                resultBuffer.append(line);
            }
            return resultBuffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}