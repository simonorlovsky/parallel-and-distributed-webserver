/*
Adopted from: Webserver.java -- Matt Mahoney, mmahoney@cs.fit.edu
*/

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.LinkedList;

// A Webserver waits for clients to connect, then starts a separate
// thread to handle the request.
public class WebServer {
  private static ServerSocket serverSocket;

  public static void main(String[] args) throws IOException {
    serverSocket=new ServerSocket(8888);  // Start, listen on port 80
    int maxNum = Integer.parseInt(args[0]);
    String rootDir = "/"+args[1];
    int curNum = 0;

    // Linked list to keep track of our threads
    LinkedList<ClientHandler> threads = new LinkedList<ClientHandler>();
    while(true) {
      try {
        if(curNum < maxNum) {
          Socket s=serverSocket.accept();  // Wait for a client to connect
          threads.add(new ClientHandler(s,rootDir));
          curNum++;
        }
        else {
          //System.out.println("thread killed");
          threads.remove().join();         // Cleaning up state.. exiting thread
          Socket s=serverSocket.accept();  // Wait for a client to connect
          threads.add(new ClientHandler(s,rootDir));  // Handle the client in a separate thread
        }
      }
      catch (Exception x) {
        System.out.println(x);
      }
    }
  }
}

// A ClientHandler reads an HTTP request and responds
class ClientHandler extends Thread {
  private Socket socket;  // The accepted socket from the Webserver
  private String dir;

  // Start the thread in the constructor
  public ClientHandler(Socket s, String dir) {
    socket=s;
    this.dir = dir;
    start();
  }

  // Read the HTTP request, respond, and close the connection
  @Override
  public void run() {
    try {
      //System.out.println(Thread.currentThread());
      // Open connections to the socket
      BufferedReader in=new BufferedReader(new InputStreamReader(
        socket.getInputStream()));
      PrintStream out=new PrintStream(new BufferedOutputStream(
        socket.getOutputStream()));

      // Read filename from first input line "GET /filename.html ..."
      // or if not in this format, treat as a file not found.
      String s = in.readLine();
      //System.out.println(s);  // Log the request

      // Attempt to serve the file.  Catch FileNotFoundException and
      // return an HTTP error "404 Not Found".  Treat invalid requests
      // the same way.
      String filename = "";
      StringTokenizer st = new StringTokenizer(s);
      try {
        // Parse the filename from the GET command
        if (st.hasMoreElements() && st.nextToken().equalsIgnoreCase("GET") && st.hasMoreElements())
          filename = dir + st.nextToken();
        else
          throw new FileNotFoundException();  // Bad request

        // Append trailing "/" with "index.html"
        if (filename.endsWith("/"))
          filename += "index.html";

        // Remove leading / from filename
        while (filename.indexOf("/")==0)
          filename = filename.substring(1);

        // Replace "/" with "\" in path for PC-based servers
        filename = filename.replace('/', File.separator.charAt(0));

        // Check for illegal characters to prevent access to superdirectories
        if (filename.indexOf("..")>=0 || filename.indexOf(':')>=0
            || filename.indexOf('|')>=0)
          throw new FileNotFoundException();

        // Open the file (may throw FileNotFoundException)
        InputStream f = new FileInputStream(filename);

        // Determine the MIME type and print HTTP header
        String mimeType="text/plain";
        if (filename.endsWith(".html") || filename.endsWith(".htm"))
          mimeType = "text/html";
        else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg"))
          mimeType = "image/jpeg";
        else if (filename.endsWith(".gif"))
          mimeType = "image/gif";
        else if (filename.endsWith(".class"))
          mimeType = "application/octet-stream";
        else if (filename.endsWith(".css"))
          mimeType = "text/css";
        else if (filename.endsWith(".js"))
          mimeType = "text/javascript";
        out.print("HTTP/1.0 200 OK\r\n"+
          "Content-type: "+mimeType+"\r\n\r\n");

        // Send file contents to client, then close the connection
        byte[] a = new byte[4096];
        int n;
        while ((n = f.read(a))>0)
          out.write(a, 0, n);
        out.close();
      }
      catch (FileNotFoundException e) {
        out.println("HTTP/1.0 404 Not Found\r\n"+
          "Content-type: text/html\r\n\r\n"+
          "<html><body><h2>404 NOT FOUND</h2><p>"+filename+" not found</p></body></html>\n");
        out.close();
      }
      // If we hover over something that is not found on our computer, catch gracefully
      catch (NullPointerException e) {
        out.println("HTTP/1.0 404 Not Found\r\n"+
          "Content-type: text/html\r\n\r\n"+
          "<html><body>"+filename+" not found</body></html>\n");
        out.close();
      }
    }
    catch (IOException x) {
      System.out.println("IOException caught...");
      x.printStackTrace();
      System.exit(1);
    }
  }
}
