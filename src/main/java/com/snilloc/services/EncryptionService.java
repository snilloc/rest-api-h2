package com.snilloc.services;

public interface EncryptionService {

    /**
     * Encrypt the input string
     *
     * @param input
     * @return
     */
    public String encrypt(String input);

    /**
     * Decrypt the input String
     *
     * @param input
     * @return
     */
    public String decrypt(String input);
}
