package tn.sdf.pfesdf.services;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTP;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class FTPServiceImp {

    static FTPClient ftp = new FTPClient();
    static String TMP_UPLOAD_FOLDER = "C:/tmp/";
    static String SERVER_DOMAIN = "127.0.0.1";
    //static String SERVER_USERNAME = "epiz_31568550";
    //static String SERVER_PASSWORD = "qIxSJYucjl";
    static String SERVER_USERNAME = "eya";
    static String SERVER_PASSWORD = "baguette22";


    public static String uFileUpload(MultipartFile file, String TypePhoto, Long clientId) throws IOException {
        if (file.isEmpty()) {
            System.out.println("Empty File");
            return "Empty File";
        } else {
            File f1 = new File(TMP_UPLOAD_FOLDER);
            boolean bool = f1.mkdir();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(TMP_UPLOAD_FOLDER + file.getOriginalFilename());
            System.out.println(path.toString());
            Files.write(path, bytes);
            System.out.println("File successfully uploaded to local storage : " + file.getOriginalFilename());
            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
            int reply;
            ftp.connect(SERVER_DOMAIN);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.out.println("Exception in connecting to FTP Server");
            }
            ftp.login(SERVER_USERNAME, SERVER_PASSWORD);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();
            try {
                InputStream input = new FileInputStream(new File(TMP_UPLOAD_FOLDER + file.getOriginalFilename()));
                System.out.println(input);
                ftp.makeDirectory("/profile"+clientId.toString());
                ftp.makeDirectory("/profile"+clientId.toString()+"/"+TypePhoto);
                ftp.storeFile("/profile"+clientId.toString()+"/"+TypePhoto+"/" + file.getOriginalFilename(), input);
                ftp.logout();
                ftp.disconnect();
                System.out.println("File Uploaded !");
                input.close();
                Files.delete(path);
                System.out.println("File deleted in C:\\tmp");
            } catch (Exception e) {
                System.out.println("Error uploading file to remote server");
            }
        }
        return "";
    }
    public static byte[] getFileBytes(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fis.read(bytes);
        fis.close();
        return bytes;
    }





    public static String uFileremove(String fileName,String TypePhoto,Long clientId) throws IOException {
        try {
            ftp.connect(SERVER_DOMAIN);
            int replyCode = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Connect failed");
                return "Connect failed";
            }
            boolean success = ftp.login(SERVER_USERNAME, SERVER_PASSWORD);
            if (!success) {
                System.out.println("Could not login to the server");
                return "Could not login to the server";
            }
            String fileToDelete = "/profile"+clientId.toString()+"/"+TypePhoto+"/"+fileName;
            boolean deleted = ftp.deleteFile(fileToDelete);
            if (deleted) {
                System.out.println("The file was deleted successfully.");
            } else {
                System.out.println("Could not delete the  file, it may not exist.");
            }
        } catch (IOException ex) {
            System.out.println("Oh no, there was an error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftp.isConnected()) {
                    ftp.logout();
                    ftp.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return "";
    }
}
