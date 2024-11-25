/* 1.0 2022-01-12 Rohan Dhoyda, JokeServer.java and JokeClient.java



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

class JokeInformation implements Serializable{ // Must be serializable to send 1 bit after another over the network.
  String UserName; //The username of client
  String Jokerequestreceived; //The Joke received from client
  String Jokegiven; //The Joke given back to client
  String messageToClient;
  int JokeCount; // The Joke count of how many Jokes have been given to a particular client
  
}

class JokeWorker extends Thread {    // This is a concept of multithreading means many of the worker thread will work simultaneously
  Socket sock;                        // Class member, socket, local to JokeWorker.
  JokeWorker (Socket u) {sock = u;}  // Constructor, assign arg  to local sock

  public void run(){
    try{
      
      
      InputStream InStream = sock.getInputStream();  // The input stream to the socket
      ObjectInputStream ObjectIS = new ObjectInputStream(InStream); //The Server waits for the client to make a connection request on the input stream

      JokeInformation InObject = (JokeInformation) ObjectIS.readObject(); // We now have random access to the Joke Information object.
      
      OutputStream outStream = sock.getOutputStream(); // The output Stream from the socket
      ObjectOutputStream objectOS = new ObjectOutputStream(outStream); // The Server waits for client to make a connection request at the output stream

      System.out.println("\nCLIENT REQUEST:\n"); // The connection is established between client and the server and the server gets the requested Joke from the client
      System.out.println("Name of the user " + InObject.UserName);//The username of the client for eg: Rohan
      System.out.println("Joke request from client: " + InObject.Jokerequestreceived); // The Joke requested from the client
      System.out.println("connections count: " + (InObject.JokeCount + 1)); //It increases the connection count everytime after a conncetion is established from the client side
      
      InObject.Jokegiven = getRandomJoke(); //A random collected will be selected for the client which is defined in an array
      InObject.JokeCount++; //It will increment the Joke count everytime a user requests for a Joke
      InObject.messageToClient =
	String.format("Thanks %s for sending the Joke %s", InObject.UserName, InObject.Jokerequestreceived); //The server will thank the client for sending the Joke and in response will try to send back user requested Joke
      
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
  // A string of random Joke is made for a client that needs to be sent back
  String getRandomJoke(){

    String[] JokeArray = new String[]
      {
       "JOKE A:The Client was sleeping on his desk, how will he ping the server ??", 
       "JOKE B:If you are a burden, go bring him a Pepsi Can so that the Client will now be connected on LAN", 
       "JOKE C:If Computer was a waiter at restaurant and the customer is a client, it will say I am the designated Server to your Client", 
       "JOKE D:I am your big fan said a client to cool down a Server heavily loaded with different client"
      };
    int i=JokeArray.length,count=0;
    int[] j= new int [4];
    int randomArrayIndex = (int) (Math.random() * JokeArray.length);
    
    if(count>0) {
    	
    	int k=0;
    	j[k]=randomArrayIndex;
    	k++;
    		for(int m=0;m<JokeArray.length;m++) {
    			if(j[m]!=randomArrayIndex)
    			return (JokeArray[randomArrayIndex]);
    		}
    			
    }
    count++;
    
    return (JokeArray[randomArrayIndex]);
  }
  // A String of random proverb is made for a client to be sent back
  String getRandomProv(){

	    String[] ProvArray = new String[]
	      {
	       "Proverb 1: Behind Every Successful person is a substantial amount of Coffee", 
	       "Proverb 2: Where there is no Struggle there is no success", 
	       "Proverb 3: Listen to your heart, because it somehow knows what you want to become", 
	       "Proverb 4: Stick together TEAM"
	      };
	    
	    int randomArrayIndex = (int) (Math.random() * ProvArray.length);
	    return (ProvArray[randomArrayIndex]);
	  }
  /*
 
  */
	  
  
}

public class JokeServer {
  public static void main(String[] args) throws Exception
  {
    int q_len = 6; /* It means that this Joke server can perform 6 simultaneous operation at a time or you can say 6 client at a time*/
    int SP = 45565; //The server port assigned is 45565
    Socket sock;
    
    System.out.println
      ("Rohan Dhoyda's Joke Server 1.0 starting up, listening at port " + SP + ".\n");
    
   
    ServerSocket servSock = new ServerSocket(SP, q_len); //The Server Waits for the Client to make a connection request
    System.out.println("ServerSocket waiting for client to initiate connections..."); // Server Socket is now waiting for a client to make a connection
    
    while (true) { // we can make use of ctrl-c to terminate the process
      sock = servSock.accept();   // When Server code makes use of accept method, it will not execute until a client connection is made
      System.out.println("Connection initiated with " + sock); 
      new JokeWorker(sock).start();// Here we make use of multithreading concept so that it immediately listen for the next connection.
    }
  }
}







