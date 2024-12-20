/* MiniWebServer.java

For CSC435, when you copy this to MiniWebserver.java: Add the appropriate class header HERE.

Copyright (C) 2020 with all rights reserved. Clark Elliott

1.1




Point your browser to:

http://localhost:2540/  or...
http://localhost:2540/WebAdd.fake-cgi  or...
http://localhost:2540/any/string/you/want.abc

...to get a response back. Keep in mind there may be FavIco requests.

Use the WebAdd.html form to submit a query string to WebResponse, based on
the input to the form. You can probably "click on" the file in your
directory. Locally it will have a URL of something like:

file:///C:/Users/Elliott/435/java/MiniWebserver/WebAdd.html

You should see:

Hello Browser World N
...along with some request information.

See WebAdd.html source HTML below.

To complete the MiniWebserver.java assignment: (a) Modify this file (or
start your own from scratch) so that your MiniWebserver returns (from the
WebAdd.html request) HTML containing the person's name and the sum of the
two numbers. (b) Continue modifications so that the return screen is valid
HTML that has the user's name entered by default (but modifiable), the two
numbers entered by default (but modifiable), and a working Submit button. In
other words, the user can keep pressing submit to get a response an infinite
number of times.

You can use the Firefox console (control-shift E / Network / Inspector) to
see the Internet traffic. (Note: drag the top line up to give a bigger console
window.)

You can use Wireshark to view the traffic as well.

HTML Reference site:
https://www.w3schools.com/

You may find that including the following in your HTML header helps with
facivon problems (Thanks Thomas K.!):

<head> <link rel="icon" href="data:,"> </head>

https://stackoverflow.com/questions/1321878/how-to-prevent-favicon-ico-requests

For the MiniWebserver assignment answer these questions briefly in YOUR OWN
WORDS here in your comments:

1. How MIME-types are used to tell the browser what data is coming.
Ans: MIME TYPE is an identifier for a particular type or format of information. The client and the server communicate to each other by passing MIME TYPE
back and forth explaining how to interpret the bodies of the messages. The MIME Type can image/jpg, image/png, text/plain and in our case it is text/html. 
The server is suppossed to look at this Mime Types and based on this decide how to interpret the data. The browser with the help of MIME Type 
is able to recognize this data which has been sent via HTTP by the webserver. The browser is then able to choose a suitable displaying method.

2. How you would return the contents of requested files of type HTML
(text/html)
Ans: 
3. How you would return the contents of requested files of type TEXT
(text/plain)



*/

import java.io.*;  // This will import input output libraries

import java.net.*; // This will import Java networking libraries

static class ListenWorker extends Thread {
    Socket sock;                   
    ListenWorker (Socket u) {sock = u;} 

    public void run(){
      PrintStream out = null;   
      BufferedReader in = null; 

      try {
        out = new PrintStream(sock.getOutputStream()); 
        in = new BufferedReader(new InputStreamReader(sock.getInputStream())); 

        String request = in.readLine();
        String[] parts = request.split(" ");
        String url = parts[1];
        if (!url.contains("WebAdd.html")) {
          // Send 404 response for invalid URL
          String HTMLResponse = "<html> <h1> 404 Not Found </h1> </html>";
          out.println("HTTP/1.1 404 Not Found");
          out.println("Connection: close");
          int Len = HTMLResponse.length();
          out.println("Content-Length: " + Integer.toString(Len));
          out.println("Content-Type: text/html \r\n\r\n");
          out.println(HTMLResponse);
        } else {
          // Parse the query string from WebAdd.html
          String queryString = url.substring(url.indexOf("?") + 1);
          String[] params = queryString.split("&");
          String name = "";
          int num1 = 0;
          int num2 = 0;
          for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue[0].equals("name")) {
              name = keyValue[1];
            } else if (keyValue[0].equals("num1")) {
              num1 = Integer.parseInt(keyValue[1]);
            } else if (keyValue[0].equals("num2")) {
              num2 = Integer.parseInt(keyValue[1]);
            }
          }
          // Return the name and sum of two numbers as HTML
          String HTMLResponse = "<html> <h1> Welcome " + name + "</h1> <p> Sum of " + num1 + " and " + num2 + " is: " + (num1 + num2) + "</p> </html>";
          out.println("HTTP/1.1 200 OK");
          out.println("Connection: close");
          int Len = HTMLResponse.length();



	
      sock.close(); // This will close the connection with the client which means the client will be able to re establish a connection with the server, but not the server;
    } catch (IOException x) {
      System.out.println("Error: The connection needs to reset again. Listening again...");
    }
  }
}

public class MiniWebserver {

  static int i = 0;

  public static void main(String a[]) throws IOException {
    int q_len = 500; /* It means that this color server can perform 500 simultaneous operation at a time or you can say 500 client at a time*/
    int port = 2540; // The server port assigned is port number 2540
    Socket sock;

    ServerSocket servsock = new ServerSocket(port, q_len);

    System.out.println("Rohan Dhoyda's MiniWebserver coonection established at port 2540.");
    System.out.println("Point Firefox browser to http://localhost:2540/abc.\n");
    while (true) {
      // waiting for the next client connection:
      sock = servsock.accept(); //The accept()  method is called by the server to validate an incoming request to the socket
      new ListenWorker (sock).start();
    }
  }
}

/*
Save the following as WebAdd.html:

<HTML>
<BODY>
<H1> WebAdd </H1>

<FORM method="GET" action="http://localhost:2540/WebAdd.fake-cgi">

Enter your name and two numbers. My program will return the sum:<p>

<INPUT TYPE="text" NAME="person" size=20 value="YourName"><P>

<INPUT TYPE="text" NAME="num1" size=5 value="4"> <br>
<INPUT TYPE="text" NAME="num2" size=5 value="5"> <p>

<INPUT TYPE="submit" VALUE="Submit Numbers">

</FORM> </BODY></HTML>

*/