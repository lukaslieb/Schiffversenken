/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.prg2.proj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Lukas
 */
public class Netzwerk {
    private String port;
    private String hostname;
    
    public Netzwerk(String port, String hostname){
        this.port = port;
        this.hostname = hostname;
        try (Socket client = new Socket(hostname, 1337)){
            PrintWriter outStream = new PrintWriter(client.getOutputStream());
            BufferedReader inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outStream.println("test");
            outStream.flush();
            String line;
            while((line = inStream.readLine()) != null){
                System.out.println(line);
            }
        }
        catch(Exception ex){
            System.err.println("Error: " + ex.getMessage());
        }
    }
}
