package br.com.craftlife.api.auth;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256PasswordEncoder implements PasswordEncoder {

    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Tratar exceção
        } catch (UnsupportedEncodingException e) {
            // Tratar exceção
        }
        return null;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return sha256(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return validatePassword(rawPassword.toString(), encodedPassword);
    }

    private boolean validatePassword(String password, String expected) {
        String[] parts = expected.split("\\$");
        String actual = sha256(sha256(password) + parts[2]);
        return parts[3].toLowerCase().equals(actual.toLowerCase());
    }

}