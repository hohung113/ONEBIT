package com.example.onebitmoblie.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordHelper {
    public static String hashPasswordMD5(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashedBytes = md.digest(password.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashedBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

    public static boolean verifyPasswordMD5(String inputPassword, String storedHash) throws NoSuchAlgorithmException {
        String hashedInput = hashPasswordMD5(inputPassword);
        return hashedInput.equals(storedHash);
    }

    public static void main(String[] args) {
        try {
            String password = "Hung123@";
            String hashedPassword = hashPasswordMD5(password);
            System.out.println("MD5 Hash: " + hashedPassword);

            boolean isMatch = verifyPasswordMD5("Hung123@", hashedPassword);
            System.out.println("Password Match: " + isMatch);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
