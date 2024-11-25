
# README

## Project Overview

This project demonstrates a basic framework for hosting agents that can migrate between different server ports, maintaining their state during the migration process. The main components include a Host Server, Agent Listener, Agent Worker, and a Web Client. The agents interact with clients via a web browser and can migrate to new ports upon request while preserving their conversation state.

## Technologies Used

1. **Java:** The primary programming language for the implementation.
2. **Sockets:** For network communication between the server and clients.
3. **Multithreading:** To handle multiple client connections concurrently.
4. **HTML:** For creating the web interface for client interaction.
5. **BufferedReader & PrintStream:** For reading from and writing to sockets.

## Problem Solved

The main problem addressed by this project is the migration of agents between different server ports while maintaining their state. This is useful in distributed systems where agents need to move across different servers or ports without losing their context or conversation state.

### Key Functionalities

1. **Hosting Agents:** The Host Server listens for incoming requests and spawns Agent Listeners on new ports for each request.
2. **State Maintenance:** Each agent maintains its state, which is incremented with each valid client interaction.
3. **Agent Migration:** Agents can migrate to new ports upon receiving a specific command ("migrate") from the client, and the client is informed of the new location to continue the interaction.

## Project Structure

### Host Server

- Listens for incoming connections on a specified port.
- Spawns Agent Listeners for each new connection, each on a unique port.

### Agent Listener

- Runs on an assigned port and listens for client interactions.
- Spawns Agent Workers to handle each client request.
- Maintains the state of the agent and handles the migration process.

### Agent Worker

- Processes client requests, updates the agent's state, and handles migration.
- If migration is requested, it connects to the main server to get a new port, informs the client of the new port, and closes the current connection.

### Web Client

- Interacts with the agent via a web interface.
- Can request the agent to migrate to a new port while maintaining the conversation state.

## Learning Outcomes

1. **Network Programming:** Gained hands-on experience with Java Sockets and network communication.
2. **Multithreading:** Learned to handle multiple client connections concurrently using threads.
3. **State Management:** Understood techniques for maintaining and transferring state in a distributed environment.
4. **HTML Integration:** Integrated HTML with Java to create a simple web interface for client interactions.
5. **Agent Migration:** Implemented and understood the process of migrating agents across different ports while preserving their state.

## Usage

To execute the project:

1. Start the Host Server:
   ```sh
   java HostServer
   ```
2. Open a web browser and navigate to `http://localhost:4242`. Enter some text and press the submit button to interact with the agent.
3. Open a second web browser and navigate to the same URL to see independent interactions.
4. To request agent migration, enter the string "migrate" in the text box and submit. The agent will move to a new port and continue the interaction.

This project provides a foundational framework for understanding agent-based systems and can be extended for more complex scenarios and use cases.
