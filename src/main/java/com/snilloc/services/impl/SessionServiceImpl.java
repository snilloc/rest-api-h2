package com.snilloc.services.impl;

import com.snilloc.services.EncryptionService;
import com.snilloc.services.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Session Id Service that implement @see(SessionService)
 *
 */
public class SessionServiceImpl implements SessionService {

   private static Logger log = LoggerFactory.getLogger(SessionServiceImpl.class.getName());
   private static SecureRandom random = null;
   private static final char [] symbols;
   private static final int MAX_SESSION_ID_LENGTH = 16;

   /**
    * Default constructor for Session Id Service
    *
    */
   public SessionServiceImpl() {
      random = new SecureRandom();
   }

   /**
    * Generate a Session Id
    *
    * First generate a session id and return a encrypted session id
    *
    * @return a unique session Id
    */
   // Slow version, but more secure
   public String generateSessionId() {
      String ALGO = "SHA-1";
      try {
         //generate a random alpha numeric 32 bit character
         String sessionId =  "COL" + new BigInteger(130, random).toString(32);
         log.info("Random number: " + sessionId);
         EncryptionService encryptService = new EncryptionServiceImpl("MY SECRET KEY");
         String output = encryptService.encrypt(sessionId.toUpperCase());

         // TODO Verify sessionId does not exist in the DB


         log.info("Message digest: " + output);
         return output;
      }
      catch (Exception ex) {
         log.error("Error in generating session id", ex);
         System.err.println("Error in generating a session id" + ex.getMessage());
         return null;
      }
   }

   /* Quick version for non-secure */
   static {
      StringBuilder tmp = new StringBuilder();
      for (char ch = '0'; ch <= '9'; ch++) {
         tmp.append(ch);
      }
      for (char ch = 'a'; ch <= 'z'; ch++) {
         tmp.append(ch);
      }
      symbols = tmp.toString().toCharArray();
   }

   /**
    * Generate a session id without encryption
    *
    * @return a session id as a String
    */
   public String getSessionId() {
      char[] buf = new char[MAX_SESSION_ID_LENGTH];
      for (int i = 0; i < buf.length; i++) {
         buf[i] = symbols[random.nextInt(symbols.length)];
      }
      return new String(buf);
   }
}

