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
 * Class that takes responsibility for transferring all the data between client and server
 * @author Kamil Osuch
 * @version 1.0
 */
public class PassiveConnection {
    private ServerSocket serverSocket;
    private final Session session;
    private Socket socket = null;
    ExecutorService executorService;

    /**
     * Creates passive connection for the specified client session
     * @param session {@link Session} specified for client
     * @param executorService Executor service that takes tasks to execute on non-used threads
     */
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

    /**
     * Accepts passive connection from the client
     */
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

    /**
     * Get the data which was sent by the user and buffers it to specified OutputStream in passive connection
     * @param outputStream OutputStream of a file where file will be sent
     */
    public void getData(OutputStream outputStream){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                waitingForConnection();
                try {
                    byte buffer[] = new byte[1024];
                    int number;
                    InputStream inputStream = socket.getInputStream();
                    while ((number = inputStream.read(buffer)) > -1) {
                        outputStream.write(buffer, 0, number);
                    }
                    outputStream.close();
                    session.endPassiveConnection();
                    session.sendResponse(ResponseType.TRANSFER_COMPLETE);
                    socket.close();
                }catch (SocketException ignore){

                } catch (Exception e) {
                    session.endPassiveConnection();
                    session.sendResponse(ResponseType.ACTION_ABORTED_LOCAL);
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Sends data from specified InputStream to the client output stream in passive connection
     * @param inputStream InputStream of a file which will be sent
     */
    public void sendData(InputStream inputStream){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                waitingForConnection();
                try{
                    byte[] buffer = new byte[1024];
                    int number;
                    OutputStream outputStream = socket.getOutputStream();
                    while((number = inputStream.read(buffer))>-1){
                        outputStream.write(buffer, 0, number);
                    }
                    inputStream.close();
                    session.endPassiveConnection();
                    session.sendResponse(ResponseType.TRANSFER_COMPLETE);
                    socket.close();
                } catch (SocketException ignore){
                } catch (Exception e){
                    session.endPassiveConnection();
                    session.sendResponse(ResponseType.ACTION_ABORTED_LOCAL);
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Sends list of files given in the argument
     * @param list list of files given from the {@link pl.edu.agh.java2015.ftp.server.Filesystem}
     */
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

    /**
     * Waits for connection with client on passive connection
     */
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

    /**
     * Returns port on which serverSocket is created
     * @return port of ServerSocket
     */
    public int getPort() {
            return serverSocket.getLocalPort();
        }

    /**
     * Disconnects passive connection
     * @throws RuntimeException when problem with closing Socket or ServerSocket
     */
    public void disconnect() {
        try {
            if(socket!=null)
                socket.close();
            serverSocket.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Aborts passive connection with the client
     * @return true if Socket was close, false otherwise
     */
    public boolean abort() {
        if(socket != null) {
            try {
                socket.close();
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
