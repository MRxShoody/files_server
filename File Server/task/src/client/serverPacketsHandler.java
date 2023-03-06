package client;

import packets.packetServer;
import packets.requestType;
class responseHandler {

     void execute(packetServer packetServer){
        switch (packetServer.getHttpPanswer()){
            case NOTFOUND -> System.out.println("The response says that this file is not found!");
            case DELETED -> System.out.println("The response says that the file was successfully deleted!");
            case CREATED -> System.out.println("Response says that file is saved! ID = "+ packetServer.getNameOrID());
            case FORBIDDEN -> System.out.println("The response says that creating the file was forbidden!");
            case CONTENT -> {
                String fileName = new getInput().askFileName(requestType.save);
                new fileStorage().putFile(fileName,packetServer.getContent());
                System.out.println("File saved on the hard drive!");
            }
            case ERROR -> System.out.println("Error in request");
            case EXITED -> System.out.println("EXITED");
        }
    }
}
