package client;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
class fileStorage {

    void putFile(String fileName, byte[] content){
        try {
            Files.write(getFilePath(fileName), content);
        }catch (Exception e){
            System.out.println("Couldn't create new file");
        }
    }

     void deleteFile(String fileName){
        try {
            Files.delete(getFilePath(fileName));
        }catch (Exception e){
            System.out.println("Couldn't delete file");
        }
    }
     byte[] sendFile(String fileName){
        try {
            if(!Files.exists(getFilePath(fileName)))
                return null;

             byte[] content = Files.readAllBytes(getFilePath(fileName));
             deleteFile(fileName);
             return content;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Couldn't get file content");
            return null;
        }
    }

    private Path getFilePath(String fileName){
        String dataPath = "C:\\Users\\jacqu\\IdeaProjects\\File Server\\File Server\\task\\src\\client\\data\\";;
            return Paths.get(String.join("",dataPath,fileName));
    }
}


