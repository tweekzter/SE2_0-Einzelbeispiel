package com.example.se2_0_einzelbeispiel;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Handles a general server request. Sends the passed request to the specified host.
 * @author Manuel Simon #00326348
 */
public class ServerRequest implements Runnable {
    private String request;
    private String response;
    private String host;
    private int port;

    /**
     * Sets up the Server Request.
     *
     * @param hostAndPort - hostname and port with following syntax: hostname.com:12345
     * @param request
     */
    public ServerRequest(String hostAndPort, String request) {
        this.request = request;

        String[] host = hostAndPort.split(":");
        this.host = host[0];
        this.port = Integer.parseInt(host[1]);
    }


    public void run() {

        try {
            Socket socket = new Socket(host, port);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.writeBytes(request+"\n");
            response = in.readLine();

            socket.close();

        } catch(IOException ex) {
            response = "Verbindungsfehler";
        }
    }

    public String getResponse() {
        return response;
    }
}
