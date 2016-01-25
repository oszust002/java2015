package pl.edu.agh.java2015.ftp.server;

import pl.edu.agh.java2015.ftp.server.database.DBFilesManager;
import pl.edu.agh.java2015.ftp.server.exceptions.FileAlreadyExistsException;
import pl.edu.agh.java2015.ftp.server.exceptions.filesystem.FileException;
import pl.edu.agh.java2015.ftp.server.exceptions.filesystem.FileNotExistsException;
import pl.edu.agh.java2015.ftp.server.exceptions.filesystem.NotDirectoryException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Kamil on 15.01.2016.
 */
public class Filesystem {
    private Path currentDirectoryPath;
    private final DBFilesManager filesManager;
    private final Path absolute = Paths.get(System.getProperty("user.dir")+"\\ftp");
    private Permissions permissions;


    public Filesystem(Path currentDirectoryPath, DBFilesManager filesManager){
        this.currentDirectoryPath = currentDirectoryPath;
        this.filesManager = filesManager;
    }

    public void setPermissions(User user){
        permissions = new Permissions(user);
    }

    public String showFilesOnPath(Path path) throws NotDirectoryException {
        path = getIfRelative(path);
        if(!Files.isDirectory(path))
            throw new NotDirectoryException(path.toString());
        StringBuilder stringBuilder = new StringBuilder();
        File directory = new File(path.toUri());
        File[] files = directory.listFiles();
        for(File file: files){
            if(filesManager.fileExists(getStringFilePath(file))) {
                if (file.isFile())
                    stringBuilder.append(file.getName() + "\r\n");
                else if (file.isDirectory())
                    stringBuilder.append(file.getName() + "/\r\n");
            }
        }
        return stringBuilder.toString();
    }

    private String  getStringFilePath(File file) {
        if(currentDirectoryPath.toString().equals(""))
            return file.getName();
        else
            return currentDirectoryPath.toString()+"\\"+file.getName();
    }

    private Path getIfRelative(Path path) {
        if(!path.isAbsolute()) {
            path = currentDirectoryPath.resolve(path);
            path = absolute.resolve(path);
        }
        return path;
    }

    public Path getCurrentDirectoryPath(){
        return currentDirectoryPath;
    }

    public void changeDirectory(String path) throws FileException {

        Path newPath = normalizePath(path);
        if(isPathRoot(newPath)) {
            currentDirectoryPath = Paths.get("");
            System.out.println("New current directory: /");
        }
        else if(filesManager.fileExists(newPath.toString())){
            if(isPathToDirectory(newPath)) {
                currentDirectoryPath = newPath;
                System.out.println("New current directory: /"+newPath);
            }
            else
                throw new NotDirectoryException(newPath.toString());
        }
        else
            throw new FileNotExistsException(newPath.toString());

    }

    public void createDirectory(String path) throws FileException {
        Path newPath = normalizePath(path);
        Path parentPath = getParentPath(newPath);
        if(filesManager.fileExists(newPath.toString()))
            throw new FileAlreadyExistsException(newPath.toString());
        if(!parentPath.toString().equals("") && !filesManager.fileExists(parentPath.toString())){
            throw new FileNotExistsException(parentPath.toString());
        }
        if(!isPathToDirectory(parentPath))
            throw new NotDirectoryException(parentPath.toString());
        try {
            Files.createDirectory(absolute.resolve(newPath));
            permissions.addFileToDatabase(newPath);
        } catch (IOException e) {
            throw new FileException(e);
        }

    }

    private Path getParentPath(Path newPath) {
        if(newPath.getParent() == null)
            return Paths.get("");
        else
            return newPath.getParent();
    }

    private Path normalizePath(String path) {
        if(isAbsolutePath(path)){
            path = path.substring(1,path.length());
            return Paths.get(path);
        }
        else {
             return currentDirectoryPath.resolve(path).normalize();
        }
    }

    private boolean isAbsolutePath(String path) {
        return path.charAt(0) == '/' || path.charAt(0) == '\\';
    }

    private boolean isPathToDirectory(Path newPath) {
        Path path = absolute.resolve(newPath);
        return Files.isDirectory(path);
    }

    private boolean isPathRoot(Path path){
        return path.toString().equals("") || path.toString().contains("..");
    }

    private class Permissions{
        private final User user;

        public Permissions(User user){
            this.user = user;
        }

        public void addFileToDatabase(Path path){
            filesManager.addFileOrDirectory(path, user);
        }
    }
}
