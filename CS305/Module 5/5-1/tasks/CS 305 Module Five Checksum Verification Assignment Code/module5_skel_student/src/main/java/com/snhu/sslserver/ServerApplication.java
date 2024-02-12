package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.MessageDigest;

@SpringBootApplication
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}

// Code by Vudeh
// This annotation marks the class as a REST controller, meaning it's ready to handle HTTP requests.
@RestController
class ServerController {
    
    // This annotation defines a GET endpoint for a path "/hash". It will trigger the greeting method when accessed.
    @GetMapping("/hash")
    // The greeting method takes one request parameter named 'name'. If the parameter is not provided, it defaults to "Victor Udeh".
    // The method returns a string response which is the output of the myHash method.
    public String greeting(@RequestParam(value = "name", defaultValue = "Victor Udeh") String name) throws Exception {
        return myHash(name);
    }
    
    // This private helper method takes a string input and returns a SHA-256 checksum of the input as a hex string.
    private String myHash(String data) throws Exception {
        // Get an instance of the MessageDigest class for SHA-256 hashing.
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        // Update the digest using the bytes of the provided string.
        md.update(data.getBytes());
        
        // Complete the hash computation and get the resulting byte array.
        byte[] digest = md.digest();
        // Initialize a StringBuffer to build the hex string representation of the hash.
        StringBuffer hexString = new StringBuffer();
        
        // Loop through all bytes in the hash byte array.
        for (int i = 0; i < digest.length; i++) {
            // Convert each byte to a two-digit hexadecimal value.
            String hex = Integer.toHexString(0xff & digest[i]);
            // If the hex string is only one character, prepend a '0'.
            if (hex.length() == 1) {
                hexString.append('0');
            }
            // Append the hex string representation of the byte to the StringBuffer.
            hexString.append(hex);
        }
        
        // Construct the final string to return, inserting the original data and the checksum.
        // It formats the string to include HTML paragraph elements for better readability in a web browser.
        return String.format("<p>Hello %s!</p><p>Data: %s</p><p>SHA-256 Checksum: %s</p>", data, data, hexString.toString());
    }
}

