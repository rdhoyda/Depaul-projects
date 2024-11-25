/* 1.0 2022-01-12 Rohan Dhoyda, ColorServer.java and ColorClient.java


Name: Rohan Dhoyda
Data: 2023-01-21
Java Version: 19.0.1
Command-line compilation: > javac *.java [executed twice]
Running these programs:

Client and server can run on the same machine in different processes (in separate terminal windows), or on
different machines across the Internet depending on the argument passed to the ColorClient program.

To run on localhost:

Terminal/CMD window 1> java ColorServer
Terminal/CMD window 2> java ColorClient
Terminal/CMD window 3> java ColorClient
[...]
Terminal/CMD window N> java ColorClient

Alternatively, to run over the Internet:

Terminal/CMD window 1> java ColorServer
Terminal/CMD window 2> java ColorClient 172.16.0.98 [But use the actual IP address of the ColorServer]
[...]

Files needed: ColorServer.java, ColorClient.java

Notes:

Using TCP/IP, we send and receive colors from client to server and back again. You can run as many simultaneous
ColorClients as you wish and the ColorServer will keep all conversations separate (each request being handled
by a different ColorWorker).

--------------------

Thanks:

https://www.comrevo.com/2019/07/Sending-objects-over-sockets-Java-example-How-to-send-serialized-object-over-network-in-Java.html (Code dated 2019-07-09, by Ramesh)
https://rollbar.com/blog/java-socketexception/#
Also: Hughes, Shoffner and Winslow for Inet code.

--------------------

When a user requests a new color from the server, A TCP/IP connection is established between the client and the server.
As the request is fulfilled by the server i.e when it gives a random color in response to what color was asked by
the client this TCP/IP connection is then terminated which states that we are running a system under a Connectionless,
Stateless protocol. The server also keeps a track of how many colors have been given back to the client and it increments
the color count each time a user requests a color from the server. If we can make track of color then we can also make
it useful for tracking banking transactions or we can also make a track record of our Monthly Expenditure and even it can
be useful to keep a track record of total sales for eg. A pizza restaurant.

For those that are new to network programming, my strong recommendation is that you TYPE IN this entire pair
of programs yourself. It won't take very long. You'll make mistakes that you'll have to diagnose, and you'll
make changes you feel are closer to your style. But you will learn the *langauge* of multi-process,
multi-threaded network programming. And, you will always have a running model, which limits frustration.

If you use my code, be SURE to include the "thanks" links as well.

*/

import java.io.*;
import java.net.*;

class ColorInformation implements Serializable{ // Must be serializable to send 1 bit after another over the network.
  String UserName; //The username of client
  String colorrequestreceived; //The color received from client
  String colorgiven; //The color given back to client
  String messageToClient;
  int colorCount; // The color count of how many colors have been given to a particular client
}

class ColorWorker extends Thread {    // This is a concept of multithreading means many of the worker thread will work simultaneously
  Socket sock;                        // Class member, socket, local to ColorWorker.
  ColorWorker (Socket u) {sock = u;}  // Constructor, assign arg  to local sock

  public void run(){
    try{
      
      
      InputStream InStream = sock.getInputStream();  // The input stream to the socket
      ObjectInputStream ObjectIS = new ObjectInputStream(InStream); //The Server waits for the client to make a connection request on the input stream

      ColorInformation InObject = (ColorInformation) ObjectIS.readObject(); // We now have random access to the color Information object.
      
      OutputStream outStream = sock.getOutputStream(); // The output Stream from the socket
      ObjectOutputStream objectOS = new ObjectOutputStream(outStream); // The Server waits for client to make a connection request at the output stream

      System.out.println("\nCLIENT REQUEST:\n"); // The connection is established between client and the server and the server gets the requested color from the client
      System.out.println("Name of the user " + InObject.UserName);//The username of the client for eg: Rohan
      System.out.println("Color request from client: " + InObject.colorrequestreceived); // The color requested from the client
      System.out.println("connections count: " + (InObject.colorCount + 1)); //It increases the connection count everytime after a conncetion is established from the client side
      
      InObject.colorgiven = getRandomColor(); //A random collected will be selected for the client which is defined in an array
      InObject.colorCount++; //It will increment the color count everytime a user requests for a color
      InObject.messageToClient =
	String.format("Thanks %s for sending the color %s", InObject.UserName, InObject.colorrequestreceived); //The server will thank the client for sending the color and in response will try to send back user requested color
      
      objectOS.writeObject(InObject); // Send it back.
      
      System.out.println("Closing the client socket connection..."); //After the request is fullfilled it will print this statement which state that the response of the client is fullfilled hence terminating the connection between client and server
      sock.close(); // This will close the client and server socket connection
      
    } catch(ClassNotFoundException CNF){
      CNF.printStackTrace(); // Class is defined in the server code.
    } catch (IOException x){
      System.out.println("Server error.");
      x.printStackTrace();
    }
  }
  // A string of random color is made for a client that needs to be sent back
  String getRandomColor(){

    String[] colorArray = new String[]
      {
       "Red", "Blue", "Green", "Yellow", "Magenta","Aqua","Orange","Soft White","Cyan","Brown","Violet","Pink","Maroon"
      };
    
    int randomArrayIndex = (int) (Math.random() * colorArray.length);
    return (colorArray[randomArrayIndex]);
  }
}

public class ColorServer {
  public static void main(String[] args) throws Exception
  {
    int q_len = 6; /* It means that this color server can perform 6 simultaneous operation at a time or you can say 6 client at a time*/
    int SP = 45565; //The server port assigned is 45565
    Socket sock;
    
    System.out.println
      ("Rohan Dhoyda's Color Server 1.0 starting up, listening at port " + SP + ".\n");
    
   
    ServerSocket servSock = new ServerSocket(SP, q_len); //The Server Waits for the Client to make a connection request
    System.out.println("ServerSocket waiting for client to initiate connections..."); // Server Socket is now waiting for a client to make a connection
    
    while (true) { // we can make use of ctrl-c to terminate the process
      sock = servSock.accept();   // When Server code makes use of accept method, it will not execute until a client connection is made
      System.out.println("Connection initiated with " + sock); 
      new ColorWorker(sock).start();// Here we make use of multithreading concept so that it immediately listen for the next connection.
    }
  }
}

/*

------------------------------------ OUTPUT ------------------------------------ 

SERVER:

Microsoft Windows [Version 10.0.22621.963]
(c) Microsoft Corporation. All rights reserved.

C:\Distributed System\Color Server>javac ColorServer.java

C:\Distributed System\Color Server>java ColorServer
Rohan Dhoyda's Color Server 1.0 starting up, listening at port 45565.

ServerSocket waiting for client to initiate connections...
Connection initiated with Socket[addr=/127.0.0.1,port=61348,localport=45565]

CLIENT REQUEST:

Name of the user Rohan
Color request from client: cyan
connections count: 1
Closing the client socket connection...
Connection initiated with Socket[addr=/127.0.0.1,port=61363,localport=45565]

CLIENT REQUEST:

Name of the user Rohan
Color request from client: blue
connections count: 2
Closing the client socket connection...
--------------------------------------------------------
CLIENT:

Do you want to use this constructor

To Request a color you need to give your name: Rohan
Hi Rohan
Choose a color you want: cyan

We have successfully connected to the ColorServer at port 45,565
We have sent the serialized values to the ColorServer's server socket

RESPONSE RECEIVED:
Thanks Rohan for sending the color cyan
The color sent back is: Soft White
The color count is: 1

Closing the connection to the server.

Choose a color you want: blue

We have successfully connected to the ColorServer at port 45,565
We have sent the serialized values to the ColorServer's server socket

RESPONSE RECEIVED:
Thanks Rohan for sending the color blue
The color sent back is: Magenta
The color count is: 2

Closing the connection to the server.

*/
