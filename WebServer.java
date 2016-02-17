/*
 * Copyright (c) 1995, 2014, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.net.*;
import java.io.*;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Date;

public class WebServer {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java WebServer <root dir>");
            System.exit(1);
        }

        String inputFilePath = args[0]+"/index.html";
        int portNumber = 8888;

        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
            // Initiate conversation with client
            // String request = "";
            // String inputLine;
            // while((inputLine = in.readLine()) != null) {
            //   request += inputLine+"\r\n";
            //   //System.out.println(request);
            //   if(inputLine == null) {
            //     System.out.println("NULL");
            //   }
            // }
            // System.out.println("out");
            out.println(processInput("",inputFilePath));


        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    // Given input request and inputFilePath, return either a 404 response if the file was not found
    // or a 200/OK along with the requested html
    public static String processInput(String input, String inputFile) {
      String theOutput = "";
      Scanner scanner = null;
      try {
          scanner = new Scanner(new File(inputFile));
      } catch (FileNotFoundException e) {
          System.err.println(e);
          return "HTTP/1.0 404 NOT FOUND\r\nDate: "+new Date() + "\r\nContent-Type: text/html\r\n\r\n"+
          "<h1><b>404 FILE NOT FOUND</b></h1>";

      }
      int totalCount = 0;

      while (scanner.hasNextLine()) {
          System.out.println("still in loop");
          String line = scanner.nextLine();
          totalCount += line.length();
          theOutput = theOutput+line;
      }

      return "HTTP/1.1 200 OK\r\nDate: "+(new Date()).toString() +"\r\nContent-Type: text/html\r\nContent-Length:"+totalCount+"\r\n\r\n"+theOutput;
    }
}
