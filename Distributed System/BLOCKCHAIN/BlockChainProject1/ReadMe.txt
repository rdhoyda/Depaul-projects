Blockchain Project Readme
Project Summary
This Java-based blockchain project demonstrates the core concepts of decentralized ledger technology. The project involves creating, managing, and verifying blocks within a blockchain using socket communication, JSON serialization, and cryptographic techniques. This system simulates a simple, multi-process blockchain environment where data records are securely created, validated, and stored.

Technologies Used
Java: The primary programming language for implementing the blockchain and networking logic.
Gson: A library for serializing and deserializing Java objects to and from JSON.
SHA-256 Hashing: Used for creating secure block hashes.
RSA Encryption: For generating and managing public/private key pairs to ensure secure communication.
Socket Programming: Facilitates inter-process communication.
Priority Blocking Queue: Manages unverified blocks based on their priority.
Key Features
Block Creation and Management:

Implemented BlockRecord class to represent individual blocks, containing fields such as Block ID, Timestamp, Previous Hash, and various data attributes (e.g., Name, SSN, Diagnosis).
Blocks are serialized to JSON format for persistence and communication.
Networking and Communication:

Developed a Blockchain class to manage the blockchain system, including block creation, serialization, and socket communication setup for inter-process communication.
Implemented servers for handling public key distribution and receiving unverified blocks.
Security and Hashing:

Utilized SHA-256 hashing for generating secure block hashes.
Implemented RSA encryption for generating and managing public/private key pairs.
JSON Serialization:

Used Gson library to convert Java objects (BlockRecord) into JSON format for data persistence and inter-process communication.
Priority Queue:

Implemented a priority blocking queue for managing unverified blocks, sorted by their timestamp priority.
Learning Outcomes
Blockchain Fundamentals:

Gained a deep understanding of blockchain concepts, including block creation, hashing, and distributed ledger management.
Cryptography:

Learned to implement cryptographic techniques such as SHA-256 hashing and RSA encryption to ensure data integrity and secure communication.
Networking:

Developed skills in socket programming to enable inter-process communication in a decentralized system.
JSON Handling:

Utilized the Gson library for effective JSON serialization and deserialization, facilitating data exchange between processes.
Multi-Process Coordination:

Implemented a system where multiple processes contribute to the blockchain, coordinating their actions through socket communication and shared data structures.
Problem Solved
This project simulates a simple blockchain environment, addressing key challenges such as:

Secure block creation and verification.
Efficient inter-process communication in a decentralized system.
Managing and prioritizing unverified blocks.
Ensuring data integrity through cryptographic techniques.
Resume Highlights
Blockchain Development: Designed and implemented a decentralized ledger system in Java, utilizing cryptographic techniques and JSON serialization.
Networking and Communication: Developed a multi-process coordination mechanism using socket programming.
Cryptography: Implemented SHA-256 hashing and RSA encryption for secure data handling.
Data Serialization: Leveraged the Gson library for effective JSON serialization and deserialization.
Problem-Solving: Addressed key challenges in blockchain management, inter-process communication, and data integrity.
