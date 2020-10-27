package com.company;
import java.util.Scanner;
import java.nio.file.Paths;
public class Main {
    private static final String menu = "Enter action (1 - get the file, 2 - create a file, 3 - delete the file):";
    private static final String enterFileName = "Enter file name: ";
    private static FileStorage fileStorage = new FileStorage();
    public static String getFileRequest(String fileName)
    {
        FileStorage.FileContent fileContent = new FileStorage.FileContent();
        FileStorage.ServerCode code = fileStorage.get(fileName, fileContent);
        if (code == FileStorage.ServerCode.OK_CODE) {
           System.out.println(fileContent.getContent());
        }
        return code.getRepr();
    }

    public static String deleteFileRequest(String fileName)
    {
        FileStorage.ServerCode code = fileStorage.delete(fileName);
        return code.getRepr();
    }

    public static String putFileRequest(String fileName, String content)
    {
        FileStorage.ServerCode code = fileStorage.put(fileName, content);
        return code.getRepr();
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
                if (menuItem >= 1 && menuItem <= 3) {
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
                    case 3:
                        result = deleteFileRequest(fileName);
                        break;
                    case 2:
                        System.out.print("Enter file content: ");
                        String fileCont = scanner.nextLine();
                        result = putFileRequest(fileName, fileCont);
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
