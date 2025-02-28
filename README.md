# data-transfer-java

This project demonstrates two methods of file transfer between a client and a server using Java: traditional and zero-copy techniques. 

## Components

### Traditional File Transfer

1. **TraditionalClient.java**: A client that connects to a server and sends a file using standard I/O streams.
2. **TraditionalServer.java**: A server that listens for incoming connections and receives files from clients.

### Zero-Copy File Transfer

1. **ZeroCopyClient.java**: A client that connects to a server and sends a file using Java NIO's `FileChannel` for efficient file transfer.
2. **ZeroCopyServer.java**: A server that listens for incoming connections and receives files from clients using zero-copy techniques.


## How to Run

1. **Compile the Java files**:
   Navigate to the `src` directory and compile the Java files using:
   ```bash
   javac tradional/*.java zerocopy/*.java
   ```

2. **Run the Servers**:
   Open two terminal windows and run the servers:
   - For traditional file transfer:
     ```bash
     java tradional.TraditionalServer
     ```
   - For zero-copy file transfer:
     ```bash
     java zerocopy.ZeroCopyServer
     ```

3. **Run the Clients**:
   In separate terminal windows, run the clients:
   - For traditional file transfer:
     ```bash
     java tradional.TraditionalClient
     ```
   - For zero-copy file transfer:
     ```bash
     java zerocopy.ZeroCopyClient
     ```

## Configuration

- **Server Host**: The server is set to run on `localhost`.
- **Ports**: 
  - Traditional server runs on port `8080`.
  - Zero-copy server runs on port `8081`.
- **File Paths**: 
  - The clients read files from `src/resources/traditional.txt` and `src/resources/zero-copy.txt`.
  - The servers write received files to `src/resources/traditional-received.txt` and `src/resources/zero-copy-received.txt`.

## Logging

The project uses Java's built-in logging framework to log important events and errors during file transfer.

## Notes

- Ensure that the resource files (`traditional.txt` and `zero-copy.txt`) exist in the specified paths before running the clients.
- This project is intended for educational purposes to demonstrate file transfer techniques in Java.
