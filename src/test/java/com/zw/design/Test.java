package com.zw.design;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("@(\\S*)\\s+?");
        String msg = "@谁谁谁 @2312321 @我是2323     @@ad@ @adfasdf";
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }
}
