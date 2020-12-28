package com.uzykj.chinatruck.utils;

import java.util.Random;

/**
 * @author ghostxbh
 */
public class ToolUtils {
    public static String getVercode(int size) {
        String chars = "abcdefghjklmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ0123456789";
        String vercode = "";
        Random r = new Random();
        for (int i = 0; i < size; ++i) {
            vercode = vercode + chars.charAt(r.nextInt(chars.length()));
        }
        return vercode;
    }

    public static void main(String[] args) {
        int i = 0;
        while (i < 20) {
            System.out.println(getVercode(24));
            i++;
        }
    }
}
