
package client;

import packets.packetClient;
import packets.requestType;

import java.util.Scanner;
class getInput {

    static boolean running = true;

    private Scanner s = new Scanner(System.in);
    private requestType byNameOrByIDget() {
        System.out.print("Do you want to get the file by name or by id (1 - name, 2 - id): ");

        if (s.nextLine().equals("1"))
            return requestType.getByName;

        return requestType.getByID;
    }

    private requestType byNameOrByIDdelete() {

        System.out.print("Do you want to get the file by name or by id (1 - name, 2 - id): ");

        if(s.nextLine().equals("1"))
            return requestType.deleteByName;

        return requestType.deleteByID;
    }

    String askFileName(requestType RT) {
        if(RT == requestType.deleteByID || RT == requestType.getByID)
            System.out.print("Enter ID: ");
        else
            System.out.print("Enter name of the file: ");

        return s.nextLine();
    }

     packetClient execute() {
        System.out.print("Enter action (1 - get a file, 2 - save a file, 3 - delete a file): ");

        String answer = s.nextLine();

        if (answer.equals("exit")) {
            System.out.println("Request was sent");
            running = false;
            return new packetClient(requestType.exit);
        }

        switch (answer) {
            case "1" -> { //get
                requestType RT = byNameOrByIDget();
                String file = askFileName(RT);
                System.out.println("Request was sent");
                return new packetClient(RT,file);
            }
            case "2" -> { //save
                String file = askFileName(requestType.save);
                byte[] content = new fileStorage().sendFile(file);
                String fileName = askFileServer();

                if(content == null)
                    return new packetClient(requestType.error);

                System.out.println("Request was sent");
                if(fileName.equals(""))
                    return new packetClient(requestType.save,file,content);
                else
                    return new packetClient(requestType.save,fileName,content);
            }
            case "3" -> { //delete
                requestType RT = byNameOrByIDdelete();
                String file = askFileName(RT);
                System.out.println("Request was sent");
                return new packetClient(RT,file);
            }
            default -> {
                return new packetClient(requestType.error);
            }
        }

    }
    private String askFileHardDrive() {
        System.out.print("The file was downloaded! Specify a name for it: ");

        return s.nextLine();
    }

    private String askFileServer() {
        System.out.print("Enter name of the file to be saved on server: ");

        return s.nextLine();
    }

}
