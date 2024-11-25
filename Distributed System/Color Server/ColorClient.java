/* 1.0 2023-01-12 Rohan Dhoyda, ColorServer.java and ColorClient.java

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
import java.util.Scanner;

public class ColorClient {
  private static int clientColorCount = 0;
  public static void main(String argu[]) {
    ColorClient cc = new ColorClient(argu);
    cc.run(argu);
  }

  public ColorClient(String argu[]) { // 
    System.out.println("\nDo you want to use this constructor\n");
  }

  public void run(String argu[]) {
    
      String sNAME;
      if (argu.length < 1) sNAME = "localhost"; // The server name is the local host
      else sNAME = argu[0];

    String colorFromClient = "";
	Scanner consoleIn = new Scanner(System.in); // A new Scanner will be generated
	System.out.print("To Request a color you need to give your name: "); // This statement will ask a user to write down their name because if they dont give their name they wont be able to request for a server
	System.out.flush ();
	String UserName = consoleIn.nextLine();
	System.out.println("Hi " + UserName); // This will print Hi and the user name
	//We are making use of do while loop because this loop will perform this function over and over until someone types quit
	do {
	  System.out.print("Choose a color you want: ");// After writing user name this statement will ask the user to enter the color
	  colorFromClient = consoleIn.nextLine();
	  if (colorFromClient.indexOf("quit") < 0){ 
	    getColor(UserName, colorFromClient, sNAME);
	  }
	} while (colorFromClient.indexOf("quit") < 0); //When a user enters quit it will quit the process and we will get informed of how many colors we got
	System.out.println ("Cancelled by user request.");
	System.out.println (UserName + ", You sent and received " + clientColorCount + " Colors.");
  }

  void getColor(String UserName, String colorFromClient, String sName){
    
    try{
      ColorInformation colorObj = new ColorInformation(); // We are now setting up a color information object and this is defined in color server code which needs to be compiled first
      colorObj.UserName = UserName; // Will give the username to the color server
      colorObj.colorrequestreceived = colorFromClient; // The color request sent from client to the server
      colorObj.colorCount = clientColorCount; // Will count the total of color
      
      // Socket socket = new Socket("JUNKhost", 45565); // Demonstrate the UH exception below.
      Socket socket = new Socket(sName, 45565);  //The Server is listening to the socket for a client to make a connection on port 45565
      System.out.println("\nWe have successfully connected to the ColorServer at port 45,565"); //When the connection is sucessfully established it will print this statement
      
      OutputStream OutputStream = socket.getOutputStream(); //The output stream will get a port from the Operating system we never know
      ObjectOutputStream oos = new ObjectOutputStream(OutputStream); // To serialize the object.
      
      oos.writeObject(colorObj); // Marshal the serialized java object to the network.
      System.out.println("We have sent the serialized values to the ColorServer's server socket");

      InputStream InStream = socket.getInputStream(); //The input stream to the server will open up at port 45565
      ObjectInputStream ois = new ObjectInputStream(InStream);
      ColorInformation InObject = (ColorInformation) ois.readObject(); // We will now have random access to the color information

      
      clientColorCount = InObject.colorCount; // Save in local class variable.

      System.out.println("\nRESPONSE RECEIVED:"); // This means there is a response received from the color server
      System.out.println(InObject.messageToClient);
      System.out.println("The color sent back is: " + InObject.colorgiven); //The color which is given back to the color client
      System.out.println("The color count is: " + InObject.colorCount + "\n"); // The color count is how many color a client requested from a server
      
      
      System.out.println("Closing the connection to the server.\n"); // After the client receives the color the connection between client and server is terminated
      socket.close(); // If we want to close the socket we write this following code
    } catch (ConnectException CE){
      System.out.println("\nOh no. The ColorServer refused our connection! Is it running?\n");
      CE.printStackTrace();
    } catch (UnknownHostException UH){
      System.out.println("\nUnknown Host problem.\n"); 
      UH.printStackTrace();
    } catch(ClassNotFoundException CNF){
	CNF.printStackTrace();
    } catch (IOException IOE){
      IOE.printStackTrace(); 
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



 




