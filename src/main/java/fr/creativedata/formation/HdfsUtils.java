package fr.creativedata.formation;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

/**
 * Created by aurelien on 03/10/15.
 */
public class HdfsUtils {

    public static FileSystem getFileSystemFromUri(String uri) throws Exception{
        Configuration conf = new Configuration();

        // Set FileSystem URI
        conf.set("fs.defaultFS", uri);
        // Because of Maven
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        // Set HADOOP user
        System.setProperty("HADOOP_USER_NAME", "formation");
        return FileSystem.get(conf);
    }

    public static Path createFile(FileSystem fs, Path newFolderPath, String fileName) throws IOException {
        Path newFilePath = new Path(newFolderPath + "/" + fileName);
        fs.createNewFile(newFilePath);
        return newFilePath;
    }

    public static Path createPath(FileSystem fs, Path newFolderPath, String fileName) throws IOException {
        Path newFilePath = new Path(newFolderPath + "/" + fileName);
//        fs.createNewFile(newFilePath);
        return newFilePath;
    }

    public static Path createDirectory(FileSystem fs, String path) throws IOException {
        Path workingDir=fs.getWorkingDirectory();
        Path newFolderPath= new Path(path);
        newFolderPath=Path.mergePaths(workingDir, newFolderPath);
        if(fs.exists(newFolderPath)) {
            // Delete existing Directory
            fs.delete(newFolderPath, true);
            System.out.println("Existing Folder Deleted.");
        }
        // Create new Directory
        fs.mkdirs(newFolderPath);
        System.out.println("Folder Created.");
        return newFolderPath;
    }
}
