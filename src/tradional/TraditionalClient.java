package tradional;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.logging.Logger;

public class TraditionalClient {

    private static final Logger LOGGER = Logger.getLogger(TraditionalClient.class.getName());

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;
    private static final String FILE_PATH = "src/resources/traditional.txt";
    private static final int BUFFER_SIZE = 8192;

    public static void main(String[] args) {

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
             OutputStream outputStream = socket.getOutputStream()) {

            LOGGER.info("TraditionalClient | Connected with server : "
                    + socket.getInetAddress() +  ":" + socket.getPort());

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            long transferredBytes = 0;

            long start = System.currentTimeMillis();

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                transferredBytes += bytesRead;
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.flush();

            String logMessage = MessageFormat.format(
                    "TraditionalClient | Send all bytes to server! Transferred bytes: {0} and time taken: {1}",
                    transferredBytes, (System.currentTimeMillis() - start));

            LOGGER.info(logMessage);
        } catch (IOException e) {
            LOGGER.severe("TraditionalClient | An error occurred while sending file to server: "
                    + e.getMessage());
        }
    }
}
