package com.company;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

class IdToNameMapper implements Serializable {

    private static final long serialVersionUID = 7L;
    private Map<Integer, String> id2name = new HashMap<>();
    private Map<String, Integer> name2ID = new HashMap<>();
    private int currentID;

    public IdToNameMapper() {
        currentID = 1;
    }

    /**
     add file name to mapper and return ID
     if id positive iperation sucessful else faild occur
     */
    public int add(String fileName){
        //check if file has already exist
        int fileId = 0;
        if (!name2ID.containsKey(fileName)) {
            fileId = generateID();
            id2name.put(fileId, fileName);
            name2ID.put(fileName, fileId);
        }
        return fileId;
    }

    boolean isExist(String fileName) {
        return name2ID.containsKey(fileName);
    }

    /**
     * remove file from filestorage
     * @param fileName - name removed file
     * @return
     *  0 - file not found
     *  ID - if file successully deleted
     */
    public int remove(String fileName){
        int id = 0;
        if (name2ID.containsKey(fileName)) {
            id = name2ID.get(fileName);
            name2ID.remove(fileName);
            id2name.remove(id);

        }
        return id;
    }



    private int generateID() {
        int temp = currentID;
        currentID++;
        return temp;
    }
}