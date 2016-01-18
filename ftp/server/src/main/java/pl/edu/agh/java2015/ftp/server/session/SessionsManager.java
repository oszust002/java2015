package pl.edu.agh.java2015.ftp.server.session;

import pl.edu.agh.java2015.ftp.server.Filesystem;
import pl.edu.agh.java2015.ftp.server.database.DBUserManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Kamil on 14.01.2016.
 */
public class SessionsManager {
    private static final int PORT = 21;
    private final ExecutorService executorService;
    private final DBUserManager userManager;


    public SessionsManager(DBUserManager manager, int threadPoolSize){
        userManager = manager;
        executorService = Executors.newCachedThreadPool();
    }

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("New connection accepted");
                startSession(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startSession(Socket socket) throws IOException {
        executorService.submit(new Session(socket,userManager,
                new Filesystem(Paths.get(System.getProperty("user.dir")+"\\ftp")),
                executorService));
    }

}
