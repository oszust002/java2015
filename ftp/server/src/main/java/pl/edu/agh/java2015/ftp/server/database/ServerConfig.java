package pl.edu.agh.java2015.ftp.server.database;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by Kamil on 03.02.2016.
 */
public class ServerConfig {
    private File configFile = new File("config.properties");
    private Properties configProps;
    private String url = "jdbc:mysql://mysql.agh.edu.pl/oszust";
    private String login = "oszust";
    private String password = "KZiuB2mV";

    private ServerConfig(){
        try {
            loadProperties();
        } catch (IOException ignore) {

        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private  void loadProperties() throws IOException {
        Properties defaultProps = new Properties();
        defaultProps.setProperty("url", "jdbc:mysql://mysql.agh.edu.pl/oszust");
        defaultProps.setProperty("login", "oszust");
        defaultProps.setProperty("pass", "KZiuB2mV");

        configProps = new Properties(defaultProps);

        InputStream inputStream = new FileInputStream(configFile);
        configProps.load(inputStream);
        inputStream.close();
    }

    public void saveProperties() throws IOException {
        configProps.setProperty("url", url);
        configProps.setProperty("login", login);
        configProps.setProperty("pass", password);
        OutputStream outputStream = new FileOutputStream(configFile);
        outputStream.close();
    }

    public static ServerConfig getInstance(){
        return new ServerConfig();
    }



}
