package com.company;
import java.util.Scanner;
import java.nio.file.Paths;
public class Main {
    private static final String menu = "Enter action (1 - get the file BY_NAME, 2 - get the file BY_ID," +
                                        "3 - create a file, 4 - delete the file BY_NAME, 5 - delete the file BY_ID):";
    private static final String enterFileName = "Enter file name: ";
    private static FileStorage fileStorage = new FileStorage();
    public static String getFileRequest(String fileName)
    {
        FileStorage.FileContent fileContent = new FileStorage.FileContent();
        ServerRespond respond = fileStorage.get(fileName, fileContent);
        if (respond.getCode() == ServerCode.OK_CODE) {
           System.out.println(fileContent.getContent());
        }
        return respond.toString();
    }

    public static String getFileRequest(int id) {
       return getFileRequest(fileStorage.getFileNameFromId(id));
    }

    public static String deleteFileRequest(String fileName) {
        ServerRespond respond = fileStorage.delete(fileName);
        return respond.toString();
    }

    public static String deleteFileRequest(int id) {
        return deleteFileRequest(fileStorage.getFileNameFromId(id));
    }

    public static String putFileRequest(String fileName, String content)
    {
        ServerRespond respond = fileStorage.put(fileName, content);
        return respond.toString();
    }


    public static void main(String[] args) {
        boolean exitCode = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Current path is: " + Paths.get("").toAbsolutePath().toString());
        while (!exitCode) {
            System.out.println(menu);
            String usrInp = scanner.nextLine();
            try {
                int menuItem = Integer.parseInt(usrInp);
                String result = "";
                String fileName = "";
                if (menuItem >= 1 && menuItem <= 5) {
                    System.out.print(enterFileName);
                    fileName = scanner.nextLine();
                }
                switch (menuItem) {
                    case 0:
                        exitCode = true;
                        fileStorage.saveFileNamesInfo();
                        result = "Exit from programm";
                        break;
                    case 1:
                        result = getFileRequest(fileName);
                        break;
                    case 2:
                        result = getFileRequest(Integer.parseInt(fileName));
                        break;
                    case 3:
                        System.out.print("Enter file content: ");
                        String fileCont = scanner.nextLine();
                        result = putFileRequest(fileName, fileCont);
                        break;
                    case 4: // delete file by name
                        result = deleteFileRequest(fileName);
                        break;
                    case 5: //delete by id
                        result = deleteFileRequest(Integer.parseInt(fileName));
                        break;
                    default:
                        result = "Choose correct item!";
                        break;
                }
                System.out.println(result);
            } catch (NumberFormatException ex) {
                if (usrInp.equals("exit")) {
                } else {
                    System.out.println("Not valid number! Try again");
                }
            }
        }
    }
}
