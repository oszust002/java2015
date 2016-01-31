package pl.edu.agh.java2015.ftp.server.command;
import pl.edu.agh.java2015.ftp.server.response.ResponseType;
import pl.edu.agh.java2015.ftp.server.session.Session;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
                    synchronized (PassiveConnection.this) {
                        socket = serverSocket.accept();
                        System.out.println("Passive connection accepted on port: " + getPort());
                        serverSocket.close();
                        PassiveConnection.this.notify();
                    }
                } catch (SocketException ignore){
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getData(OutputStream outputStream){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                waitingForConnection();
                try {
                    byte buffer[] = new byte[1024];
                    int number;
                    InputStream inputStream = socket.getInputStream();
                    while((number = inputStream.read(buffer))>-1) {
                        outputStream.write(buffer, 0, number);
                    }
                    outputStream.close();
                    session.endPassiveConnection();
                    session.sendResponse(ResponseType.TRANSFER_COMPLETE);
                    socket.close();
                } catch (Exception e) {
                    disconnect();
                    session.sendResponse(ResponseType.ACTION_ABORTED_LOCAL);
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
                    session.endPassiveConnection();
                    session.sendResponse(ResponseType.TRANSFER_COMPLETE);
                } catch (Exception e) {
                    session.endPassiveConnection();
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

        public void disconnect() {
            try {
                if(socket!=null)
                    socket.close();
                serverSocket.close();
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
