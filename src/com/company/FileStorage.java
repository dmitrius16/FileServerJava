//package server;
package com.company;
import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileStorage {
    //private final String folderPath = "C:\\Users\\sysoevd\\IdeaProjects\\File Server\\File Server\\task\\src\\server\\data\\";
    private final String folderPath = Paths.get("").toAbsolutePath().toString(); //new modification
    ///String s = currentRelativePath.toAbsolutePath().toString();

    private IdToNameMapper fileNames; // = new IdToNameMapper();
    private String createFullPath(String fileName) {
        return folderPath + "\\" + fileName;
    }
    public FileStorage() {
        File folder = new File(folderPath);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("id2map.dat"))) {
            fileNames = (IdToNameMapper)ois.readObject();
        } catch(Exception ex) {
            System.out.println("Id2Map corrupted");
            fileNames = new IdToNameMapper();
        }
    }

    public boolean isFileExists(String fileName) {
        return fileNames.isExist(fileName);
    }

    public void saveFileNamesInfo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("id2map.dat"))) {
            oos.writeObject(fileNames);
        } catch (Exception ex) {
            System.out.print("Can't save file names info. All data will be lost");
        }
    }

    public ServerCode put(String fileName, String content) {
        //check if file exists
        ServerCode result = ServerCode.FILE_EXIST;
        if (isFileExists(fileName) == false) {
            String fullFileName = createFullPath(fileName);
            fileNames.add(fileName);
            try (FileWriter fileWriter = new FileWriter(new File(fullFileName))) {
                fileWriter.write(content);
                result = ServerCode.OK_CODE;
            }catch (IOException ex) {
                ex.printStackTrace();
                result = ServerCode.ERR_CODE;
            }
        }
        return result;
    }

    public ServerCode delete(String fileName) {
        ServerCode result = ServerCode.ERR_CODE;
        if (isFileExists(fileName)) {
            File file = new File(createFullPath(fileName));
            if (file.delete()) {
                fileNames.remove(fileName);
                result = ServerCode.OK_CODE;
            }
        }
        return result;
    }

    public ServerCode get(String fileName, FileContent content){
        ServerCode result = ServerCode.ERR_CODE;
        if (isFileExists(fileName)) {
            try {
                content.setContent(Files.readAllBytes(Paths.get(createFullPath(fileName))));
                result = ServerCode.OK_CODE;
            } catch (IOException | OutOfMemoryError ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static class FileContent {
        private String content = "";

        public void setContent(String content) {
            this.content = content;
        }

        public void setContent(byte[] cont) {
            String contStr = new String(cont);
            content = contStr;
        }

        public String getContent() {
            return this.content;
        }
    }

    public enum ServerCode {
        OK_CODE("200"),
        FILE_EXIST("403"),
        ERR_CODE("404");

        private String repr;
        ServerCode(String strVal) {repr = strVal;}
        String getRepr() {return repr;}
    }

}
