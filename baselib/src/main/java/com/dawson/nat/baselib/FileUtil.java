package com.dawson.nat.baselib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * 文件操作工具
 *
 * @author dawson
 */
public class FileUtil {
    public static boolean write(String file, String content) {
        //写入相应的文件
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(file, false));
            out.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //清楚缓存
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String read(String file) {
        //读取文件(字符流)
        BufferedReader in = null;
        StringBuilder sbRes = new StringBuilder();
        try {
            in = new BufferedReader(new FileReader(file));
            //读取数据
            //循环取出数据
            String str = null;
            while ((str = in.readLine()) != null) {
                sbRes.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sbRes.toString();
    }
}
