package com.rukawa.algorithm.base.class25;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * create by hqh on 2023/1/7
 */
public class Hash {

    private MessageDigest hash;

    public Hash(String algorithm) {
        try {
            hash = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String hashCode(String input) {
        return DatatypeConverter.printHexBinary(hash.digest(input.getBytes())).toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println("支持的算法：");
        for (String messageDigest : Security.getAlgorithms("MessageDigest")) {
            System.out.println(messageDigest);
        }
        System.out.println("===================================");

        String algorithm = "MD5";
        Hash hash = new Hash(algorithm);
        String input1 = "hanqinghai&zhangxinyue1";
        String input2 = "hanqinghai&zhangxinyue2";
        String input3 = "hanqinghai&zhangxinyue3";
        String input4 = "hanqinghai&zhangxinyue4";
        String input5 = "hanqinghai&zhangxinyue5";

        System.out.println(hash.hashCode(input1));
        System.out.println(hash.hashCode(input2));
        System.out.println(hash.hashCode(input3));
        System.out.println(hash.hashCode(input4));
        System.out.println(hash.hashCode(input5));
    }
}
