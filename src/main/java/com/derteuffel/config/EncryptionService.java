package com.derteuffel.config;

/**
 * Created by derteuffel on 01/11/2018.
 */
public interface EncryptionService {
    String encryptString(String input);
    boolean checkPassword(String plainPassword, String encryptedPassword);
}
