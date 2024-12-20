/* 1.0 2023-01-12 Rohan Dhoyda, JokeServer.java and JokeClient.java

Name: Rohan Dhoyda
Data: 2023-01-22
Java Version: 19.0.1
Command-line compilation: > javac *.java [executed twice]
Running these programs:

Client and server can run on the same machine in different processes (in separate terminal windows), or on
different machines across the Internet depending on the argument passed to the JokeClient program.

To run on localhost:

Terminal/CMD window 1> java JokeServer
Terminal/CMD window 2> java JokeClient
Terminal/CMD window 3> java JokeClient
[...]
Terminal/CMD window N> java JokeClient

Alternatively, to run over the Internet:

Terminal/CMD window 1> java JokeServer
Terminal/CMD window 2> java JokeClient 172.16.0.98 [But use the actual IP address of the JokeServer]
[...]

Files needed: JokeServer.java, JokeClient.java

Notes:

Using TCP/IP, we send and receive Jokes from client to server and back again. You can run as many simultaneous
JokeClients as you wish and the JokeServer will keep all conversations separate (each request being handled
by a different JokeWorker).

--------------------

Thanks:

https://www.comrevo.com/2019/07/Sending-objects-over-sockets-Java-example-How-to-send-serialized-object-over-network-in-Java.html (Code dated 2019-07-09, by Ramesh)
https://rollbar.com/blog/java-socketexception/#
Also: Hughes, Shoffner and Winslow for Inet code.

--------------------

When a user requests a new Joke from the server, A TCP/IP connection is established between the client and the server.
As the request is fulfilled by the server i.e when it gives a random Joke in response to what Joke was asked by
the client this TCP/IP connection is then terminated which states that we are running a system under a Connectionless,
Stateless protocol. The server also keeps a track of how many Jokes have been given back to the client and it increments
the Joke count each time a user requests a Joke from the server. If we can make track of Joke then we can also make
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

public class JokeClient {
  private static int clientJokeCount = 0;
  private static int clientProvCount = 0;
  public static void main(String argu[]) {
    JokeClient cc = new JokeClient(argu);
    cc.run(argu);
  }

  public JokeClient(String argu[]) { // 
    System.out.println("\nDo you want to use this constructor\n");
  }

  public void run(String argu[]) {
    
      String sNAME;
      if (argu.length < 1) sNAME = "localhost"; // The server name is the local host
      else sNAME = argu[0];

    String JokeFromClient = "";
    String ProvFromClient = "";
	Scanner consoleIn = new Scanner(System.in); // A new Scanner will be generated
	 // A new Scanner will be generated
	System.out.print("To Request a Joke you need to give your name: "); // This statement will ask a user to write down their name because if they dont give their name they wont be able to request for a server
	System.out.print("To Request a Proverb you need to give your name: "); 
	System.out.flush ();
	String UserName = consoleIn.nextLine();
	System.out.println("Hi " + UserName); // This will print Hi and the user name
	//We are making use of do while loop because this loop will perform this function over and over until someone types quit
	do {
	  System.out.print("Choose a Joke you want: ");// After writing user name this statement will ask the user to enter the Joke
	  JokeFromClient = consoleIn.nextLine();
	  if (JokeFromClient.indexOf("quit") < 0){ 
	    getJoke(UserName, JokeFromClient, sNAME);
	  }
	} while (JokeFromClient.indexOf("quit") < 0); //When a user enters quit it will quit the process and we will get informed of how many Jokes we got
	System.out.println ("Cancelled by user request.");
	System.out.println (UserName + ", You sent and received " + clientJokeCount + " Jokes.");
	
  }

  void getJoke(String UserName, String JokeFromClient, String sName) {
    
    try{
      JokeInformation JokeObj = new JokeInformation(); // We are now setting up a Joke information object and this is defined in Joke server code which needs to be compiled first
      JokeObj.UserName = UserName; // Will give the username to the Joke server
      JokeObj.Jokerequestreceived = JokeFromClient; // The Joke request sent from client to the server
      JokeObj.JokeCount = clientJokeCount; // Will count the total of Joke
      
      // Socket socket = new Socket("JUNKhost", 45565); // Demonstrate the UH exception below.
      Socket socket = new Socket(sName, 45565);  //The Server is listening to the socket for a client to make a connection on port 45565
      System.out.println("\nWe have successfully connected to the JokeServer at port 45,565"); //When the connection is sucessfully established it will print this statement
      
      OutputStream OutputStream = socket.getOutputStream(); //The output stream will get a port from the Operating system we never know
      ObjectOutputStream oos = new ObjectOutputStream(OutputStream); // To serialize the object.
      
      oos.writeObject(JokeObj); // Marshal the serialized java object to the network.
      System.out.println("We have sent the serialized values to the JokeServer's server socket");

      InputStream InStream = socket.getInputStream(); //The input stream to the server will open up at port 45565
      ObjectInputStream ois = new ObjectInputStream(InStream);
      JokeInformation InObject = (JokeInformation) ois.readObject(); // We will now have random access to the Joke information

      
      clientJokeCount = InObject.JokeCount; // Save in local class variable.
      

      System.out.println("\nRESPONSE RECEIVED:"); // This means there is a response received from the Joke server
      System.out.println(InObject.messageToClient);
      System.out.println("The Joke sent back is: " + InObject.Jokegiven); //The Joke which is given back to the Joke client
      System.out.println("The Joke count is: " + InObject.JokeCount + "\n"); // The Joke count is how many Joke a client requested from a server
      System.out.println("Closing the connection to the server.\n"); // After the client receives the Joke the connection between client and server is terminated
      socket.close(); // If we want to close the socket we write this following code
    } catch (ConnectException CE){
      System.out.println("\nOh no. The JokeServer refused our connection! Is it running?\n");
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
  //copy pasta
  
	  
}

/*

------------------------------------ OUTPUT ------------------------------------ 

SERVER:

Microsoft Windows [Version 10.0.22621.963]
(c) Microsoft Corporation. All rights reserved.

C:\Distributed System\Joke Server>javac JokeServer.java

C:\Distributed System\Joke Server>java JokeServer
Rohan Dhoyda's Joke Server 1.0 starting up, listening at port 45565.

ServerSocket waiting for client to initiate connections...
Connecti*on initiated with Socket[addr=/127.0.0.1,port=61348,localport=45565]

CLIENT REQUEST:

Name of the user Rohan
Joke request from client: cyan
connections count: 1
Closing the client socket connection...
Connection initiated with Socket[addr=/127.0.0.1,port=61363,localport=45565]

CLIENT REQUEST:

Name of the user Rohan
Joke request from client: blue
connections count: 2
Closing the client socket connection...
--------------------------------------------------------
CLIENT:

Do you want to use this constructor

To Request a Joke you need to give your name: Rohan
Hi Rohan
Choose a Joke you want: cyan

We have successfully connected to the JokeServer at port 45,565
We have sent the serialized values to the JokeServer's server socket

RESPONSE RECEIVED:
Thanks Rohan for sending the Joke cyan
The Joke sent back is: Soft White
The Joke count is: 1

Closing the connection to the server.

Choose a Joke you want: blue

We have successfully connected to the JokeServer at port 45,565
We have sent the serialized values to the JokeServer's server socket

RESPONSE RECEIVED:
Thanks Rohan for sending the Joke blue
The Joke sent back is: Magenta
The Joke count is: 2

Closing the connection to the server.

*/



 




