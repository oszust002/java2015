package pl.edu.agh.java2015.ftp.server;

import pl.edu.agh.java2015.ftp.server.exceptions.filesystem.FilesystemException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Kamil on 15.01.2016.
 */
public class Filesystem {
    private final Path currentDirectoryPath;

    public Filesystem(Path currentDirectoryPath){
        this.currentDirectoryPath = currentDirectoryPath;
    }

    public String showFilesOnPath(Path path){
        path = getIfRelative(path);
        if(!Files.isDirectory(path))
            throw new FilesystemException();
        StringBuilder stringBuilder = new StringBuilder();
        File directory = new File(path.toUri());
        File[] files = directory.listFiles();
        for(File file: files){
            if(file.isFile())
                stringBuilder.append(file.getName()+"\n");
            else if(file.isDirectory())
                stringBuilder.append(file.getName()+"/\n");
        }
        return stringBuilder.toString();
    }

    private Path getIfRelative(Path path) {
        if(!path.isAbsolute())
            path = currentDirectoryPath.resolve(path);
        return path;
    }
}
