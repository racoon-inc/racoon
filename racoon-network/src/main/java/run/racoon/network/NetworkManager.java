package run.racoon.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import run.racoon.commons.storage.Storage;
import run.racoon.commons.storage.configuration.Configuration;
import run.racoon.network.packet.receivable.AckRow;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NetworkManager {
    private static final Logger LOG = LoggerFactory.getLogger(NetworkManager.class);

    private final Selector serverSelector;
    private final ServerSocketChannel serverSocketChannel;

    public NetworkManager(Configuration config, Storage storage) throws IOException {
        this.serverSelector = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.bind(new InetSocketAddress("localhost", 21111));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(serverSelector, SelectionKey.OP_ACCEPT);
    }

    public void startNetwork() throws IOException {
        while (true) {
            if (serverSelector.select() == 0) {
                continue;
            }

            Set<SelectionKey> readyKeys = serverSelector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if(key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel)  key.channel();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(serverSelector, SelectionKey.OP_READ);
                    continue;
                }

                if(key.isReadable()) {
                    SocketChannel client = (SocketChannel) key.channel();

                    int BUFFER_SIZE = 1024;
                    ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                    try {
                        client.read(buffer);
                        AckRow ackRow = new AckRow(buffer.array());
                        System.out.println(ackRow);
                    }
                    catch (Exception e) {
                        client.close();
                        LOG.error("Error:", e);
                        continue;
                    }
                }
            }
        }
    }
}
