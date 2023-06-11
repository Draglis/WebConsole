package net.draglis;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.NukkitRunnable;
import net.draglis.listener.PacketListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class WebConsole extends PluginBase {

    private static WebConsole instance;
    private ServerSocket serverSocket = null;

    @Override
    public void onEnable() {
        instance = this;

        // [Resource Initialization]
        this.saveAllResources();

        // [init]
        this.initListeners();

        // [start]
        this.startServer(this.getConfig().getInt("port"));
    }

    private void saveAllResources() {
        URL jarUrl = getClass().getProtectionDomain()
                .getCodeSource()
                .getLocation();

        try (ZipInputStream zip = new ZipInputStream(jarUrl.openStream())) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                String name = entry.getName();
                if (!name.contains("net/draglis") && !name.contains("plugin.yml") && !entry.isDirectory()) {
                    if (name.contains("WebConsole")) {
                        saveResource(name, name.contains("content.txt"));
                    } else {
                        saveResource(name, false);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static WebConsole getInstance() {
        return instance;
    }

    private void initListeners() {
        this.getServer().getPluginManager().registerEvents(new PacketListener(), this);
    }

    public void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);

            this.getLogger().info("Listening for connections on port " + port);

            new NukkitRunnable() {
                @Override
                public void run() {
                    acceptConnections();
                }
            }.runTaskTimerAsynchronously(this, 20, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void acceptConnections() {
        try {
            Socket connectionSocket = serverSocket.accept();
            // Create new thread to handle client request
            Thread connectionThread = new Thread(new Connection(connectionSocket, this.getDataFolder().getPath()));

            // Start the connection thread
            connectionThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}