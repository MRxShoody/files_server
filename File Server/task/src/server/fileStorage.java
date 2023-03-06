package server;

import packets.answerType;
import packets.packetClient;
import packets.packetServer;
import packets.requestType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

class fileStorage {

    static HashMap<String, String> map;

    static void takeHashMap(){
        File file = new File("C:\\Users\\jacqu\\IdeaProjects\\File Server\\File Server\\task\\src\\server\\data\\hashMapData.txt");
        try {
            FileInputStream inputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            map = (HashMap<String, String>) objectInputStream.readObject();
            System.out.println(map);

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


     synchronized static void storeHashMap(){
        File file = new File("C:\\Users\\jacqu\\IdeaProjects\\File Server\\File Server\\task\\src\\server\\data\\hashMapData.txt");
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(map);
            objectOutputStream.flush();
            objectOutputStream.close();
            objectOutputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

     synchronized packetServer putFile(packetClient packetClient){


        String fileName = packetClient.getFile();

        if(Files.exists(Path.of(getFilePath(fileName)))){
            return new packetServer(answerType.FORBIDDEN);
        }

        try{
            FileOutputStream fileOutputStream = new FileOutputStream(getFilePath(fileName));
            fileOutputStream.write(packetClient.getContent());
            fileOutputStream.flush();
            fileOutputStream.close();

            String ID = String.valueOf(map.size()+1);
            map.put(ID, getFilePath(fileName));

            System.out.println(map);

            return new packetServer(answerType.CREATED,ID);
        }catch (Exception ignored){
            return new packetServer(answerType.NOTFOUND);
        }
    }

     synchronized packetServer deleteFile(packetClient packetClient) {
        String fileName;

        if (packetClient.getRT() == requestType.deleteByID){

            fileName = packetClient.getFile();

            if (!map.containsKey(fileName))
                return new packetServer(answerType.NOTFOUND);

            fileName = map.get(fileName);
            map.remove(packetClient.getFile());

        }else{

            fileName = getFilePath(packetClient.getFile());

            if(!Files.exists(Path.of(fileName)) || !map.containsValue(fileName))
                return new packetServer(answerType.NOTFOUND);

            map.values().remove(fileName);
        }

        try {

            System.out.println(map);

            Files.delete(Path.of(fileName));

            return new packetServer(answerType.DELETED);
        } catch (Exception e) {
            e.printStackTrace();
            return new packetServer(answerType.NOTFOUND);
        }
    }

     synchronized packetServer getFile(packetClient packetClient) {

        String fileName;
        if (packetClient.getRT() == requestType.getByID){

        if (!map.containsKey(packetClient.getFile()))
            return new packetServer(answerType.NOTFOUND);

        fileName = map.get(packetClient.getFile());

        }
        else {

            fileName = getFilePath(packetClient.getFile());

            if(!Files.exists(Path.of(fileName)))
                return new packetServer(answerType.NOTFOUND);
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            byte[] content = fileInputStream.readAllBytes();
            fileInputStream.close();

            System.out.println(map);


            return new packetServer(answerType.CONTENT,content);
        }catch (Exception ignored){
            return new packetServer(answerType.NOTFOUND);
        }
    }

    private static String getFilePath(String fileName){
        String dataPath = "C:\\Users\\jacqu\\IdeaProjects\\File Server\\File Server\\task\\src\\server\\data\\";;
        return String.join("",dataPath,fileName);
    }



}
