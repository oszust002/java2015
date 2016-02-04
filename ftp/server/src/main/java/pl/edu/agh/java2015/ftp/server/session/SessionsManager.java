package pl.edu.agh.java2015.ftp.server.session;

import pl.edu.agh.java2015.ftp.server.Filesystem;
import pl.edu.agh.java2015.ftp.server.database.DBFilesManager;
import pl.edu.agh.java2015.ftp.server.database.DBUserManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Kamil on 14.01.2016.
 */
public class SessionsManager {
    private static final int PORT = 21;
    private final ExecutorService executorService;
    private final DBUserManager userManager;
    private final DBFilesManager filesManager;
    private List<Session> connections = new LinkedList<Session>();
    private boolean isRunning = true;
    private ServerSocket serverSocket;


    public SessionsManager(DBUserManager manager, int threadPoolSize, DBFilesManager filesManager){
        userManager = manager;
        executorService = Executors.newFixedThreadPool(threadPoolSize);
        this.filesManager = filesManager;
    }

    public void start(){
        isRunning = true;
        try {
            serverSocket = new ServerSocket(PORT);
            while (isRunning) {
                Socket socket = serverSocket.accept();
                System.out.println("New connection accepted");
                startSession(socket);
            }
        }catch (SocketException ignore){

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startSession(Socket socket) throws IOException {
        executorService.execute(new Session(socket,userManager,
                new Filesystem(Paths.get(""),filesManager),
                executorService, connections));
    }

    public void disconnect(){
        connections.forEach(Session::disconnect);
        isRunning = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
