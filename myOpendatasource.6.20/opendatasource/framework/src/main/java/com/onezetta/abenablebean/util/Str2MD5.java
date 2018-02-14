package com.onezetta.abenablebean.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Str2MD5 {

    public static String MD5(String sourceStr, int len) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
            if( len == 32) 
            	return buf.toString();
            else if(len == 16)
            	return buf.toString().substring(8, 24);
            else if( len == 8)
            	return buf.toString().substring(8, 12);
            else
            	return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }
}