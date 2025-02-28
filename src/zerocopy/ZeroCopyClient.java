package zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.text.MessageFormat;
import java.util.logging.Logger;

public class ZeroCopyClient {

    private static final Logger LOGGER = Logger.getLogger(ZeroCopyClient.class.getName());

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8081;
    private static final String FILE_PATH = "src/resources/zero-copy.txt";

    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
             FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
             FileChannel fileChannel = fileInputStream.getChannel()) {

            LOGGER.info("ZeroCopyClient | Connected with server : "
                    + socketChannel.socket().getInetAddress() + ":" + socketChannel.socket().getPort());

            long start = System.currentTimeMillis();
            long transferredBytes = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

            String logMessage = MessageFormat.format(
                    "ZeroCopyClient | Send all bytes to server! Transferred bytes: {0} and time taken: {1}",
                    transferredBytes, (System.currentTimeMillis() - start));

            LOGGER.info(logMessage);

        } catch (IOException e) {
            LOGGER.severe("ZeroCopyClient | An error occurred while sending file to server: "
                    + e.getMessage());
        }
    }
}
