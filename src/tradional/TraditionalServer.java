package tradional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TraditionalServer {

    private static final Logger LOGGER = Logger.getLogger(TraditionalServer.class.getName());

    private static final int PORT = 8080;
    private static final String OUTPUT_FILE = "src/resources/traditional-received.txt";

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LOGGER.info("TraditionalServer | Server started on port :" + PORT);

            while (true) {
                readReceivedFileFromClient(serverSocket.accept());
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    private static void readReceivedFileFromClient(Socket socket) {
        try (socket;
             InputStream inputStream = socket.getInputStream();
             FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_FILE)) {
            LOGGER.info("TraditionalServer | New connection from : " + socket.getInetAddress());

            byte[] buffer = new byte[8192]; // 8KB buffer
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            LOGGER.info("TraditionalServer | File received successfully!");
        } catch (IOException e) {
            LOGGER.severe("TraditionalServer | An error occurred while reading file from client: "
                    + e.getMessage());
        }
    }
}
