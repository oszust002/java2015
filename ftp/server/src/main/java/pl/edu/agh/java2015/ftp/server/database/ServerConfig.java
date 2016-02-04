package pl.edu.agh.java2015.ftp.server.database;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Class with configuration properties for database connection
 * @author Kamil Osuch
 * @version 1.0
 */
public class ServerConfig {
    private File configFile = new File("config.properties");
    private Properties configProps;
    private String url = "jdbc:mysql://mysql.agh.edu.pl/oszust";
    private String login = "oszust";
    private String password = "KZiuB2mV";

    /**
     * Creates new {@link ServerConfig} and loads info from file
     */
    private ServerConfig(){
        try {
            loadProperties();
        } catch (IOException ignore) {

        }
    }

    /**
     * Gets URL to database
     * @return URL to database
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets new URL to database
     * @param url new URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets login to database
     * @return login to database
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets new login to database
     * @param login new login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Get password to database
     * @return password to database
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets new password to database
     * @param password new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Loads properties from the file to config
     * @throws IOException when problem with loading properties from the file shows up
     */
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

    /**
     * Save properties from config to file
     * @throws IOException when problem with saving properties to file shows up
     */
    public void saveProperties() throws IOException {
        configProps.setProperty("url", url);
        configProps.setProperty("login", login);
        configProps.setProperty("pass", password);
        OutputStream outputStream = new FileOutputStream(configFile);
        outputStream.close();
    }

    /**
     * Gets instance of ServerConfig
     * @return new {@link ServerConfig} instance
     */
    public static ServerConfig getInstance(){
        return new ServerConfig();
    }



}
