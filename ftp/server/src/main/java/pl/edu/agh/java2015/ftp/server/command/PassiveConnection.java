package pl.edu.agh.java2015.ftp.server.command;

import pl.edu.agh.java2015.ftp.server.response.ResponseType;
import pl.edu.agh.java2015.ftp.server.session.Session;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * Created by Kamil on 15.01.2016.
 */
public class PassiveConnection {

    private ServerSocket serverSocket;
    private final Session session;
    private Socket socket = null;
    ExecutorService executorService;

    public PassiveConnection(Session session, ExecutorService executorService){
        this.session = session;
        this.executorService = executorService;
        try {
            this.serverSocket = new ServerSocket(0);
            System.out.println("Started passive connection on port: " + serverSocket.getLocalPort());
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
        accept();
    }

    private void accept(){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        socket = serverSocket.accept();
                        System.out.println("Passive connection accepted on port: " + getPort());
                        serverSocket.close();
                        this.notify();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendFileList(String list){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                waitingForConnection();
                try {
                    socket.getOutputStream().write(list.getBytes());
                    socket.getOutputStream().flush();
                    socket.close();
                    session.sendResponse(ResponseType.TRANSFER_COMPLETE);
                } catch (Exception e) {
                    session.sendResponse(ResponseType.ACTION_ABORTED_LOCAL);
                    e.printStackTrace();
                }
            }
        });
    }

    private void waitingForConnection(){
        try {
            synchronized (this){
                while (socket == null){
                    this.wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }
}
