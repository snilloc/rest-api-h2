package com.snilloc.services;

public interface SessionService {

   /**
    * Generate a Session Id and encrypt the session id
    *
    * @return
    */
   public String generateSessionId();

   /**
    * Generate a Unique Session Id
    *
    * @return
    */
   public String getSessionId();
}

