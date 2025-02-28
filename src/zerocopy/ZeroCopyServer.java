package zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.logging.Logger;

public class ZeroCopyServer {

    private static final Logger LOGGER = Logger.getLogger(ZeroCopyServer.class.getName());

    private static final int PORT = 8081;
    private static final String OUTPUT_FILE = "src/resources/zero-copy-received.txt";
    private static final int BUFFER_SIZE = 8192;

    public static void main(String[] args) {
        try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
            serverSocket.bind(new InetSocketAddress(PORT));
            LOGGER.info("ZeroCopyServer | Server started on port :" + PORT);

            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

            while (true) {
                readReceivedFileFromClient(serverSocket, buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readReceivedFileFromClient(ServerSocketChannel socketChannel, ByteBuffer buffer) throws IOException {
        try (SocketChannel clientSocket = socketChannel.accept();
             FileChannel fileChannel = FileChannel.open(Path.of(OUTPUT_FILE),
                     StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

            LOGGER.info("ZeroCopyServer | New connection from : " + socketChannel.socket().getInetAddress());

            while (clientSocket.read(buffer) > 0) {
                buffer.flip();
                fileChannel.write(buffer);
                buffer.clear();
            }

            LOGGER.info("ZeroCopyServer | File received successfully!");
        } catch (IOException e) {
            LOGGER.severe("ZeroCopyServer | An error occurred while reading file from client: "
                    + e.getMessage());
        }
    }
}
