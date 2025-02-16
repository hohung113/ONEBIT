//package com.example.onebitmoblie.common;
//
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.Base64;
//
//public class PasswordHelper {
//    // Hashing using SHA-256
//    public static String hashPassword(String password) throws NoSuchAlgorithmException {
//        MessageDigest md = MessageDigest.getInstance("SHA-256");
//        byte[] hashedBytes = md.digest(password.getBytes());
//        return Base64.getEncoder().encodeToString(hashedBytes);
//    }
//
//    // Verify Password
//    public static boolean verifyPassword(String inputPassword, String storedHash) throws NoSuchAlgorithmException {
//        String hashedInput = hashPassword(inputPassword);
//        return hashedInput.equals(storedHash);
//    }
//
//    public static void main(String[] args) throws NoSuchAlgorithmException {
//        String password = "MySecurePassword123";
//        String hashedPassword = hashPassword(password);
//
//        System.out.println("Hashed Password: " + hashedPassword);
//        System.out.println("Password Verified: " + verifyPassword("MySecurePassword123", hashedPassword)); // true
//        System.out.println("Password Verified: " + verifyPassword("WrongPassword", hashedPassword));       // false
//    }
//}
