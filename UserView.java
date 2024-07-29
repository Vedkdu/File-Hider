package views;

import dao.DataDAO;
import model.data;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;
    UserView(String email){
        this.email=email;
    }
    public void home(){
        do{
            System.out.println("Welcome "+this.email);
            System.out.println("Press 1 to Show Hidden files");
            System.out.println("Press 2 to Hide a new file");
            System.out.println("Press 3 to Un-hide file");
            System.out.println("Press 0 to exit");
            Scanner sc =new Scanner(System.in);
            int ch=Integer.parseInt(sc.nextLine());

            switch (ch){
                case 1->{
                    try {
                        List<data> files= DataDAO.getAllFiles(this.email);
                        System.out.println("ID - File Name");
                        for(data file:files){
                            System.out.println(file.getId()+" - "+file.getFilename());
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 2->{
                    System.out.println("Enter the file path");
                    String path=sc.nextLine();
                    File f=new File(path);
                    data file =new data(0,f.getName(),path,this.email);
                    try {
                        DataDAO.hideFile(file);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 3->{
                    List<data> files= null;
                    try {
                        files = DataDAO.getAllFiles(this.email);

                    System.out.println("ID - File Name");
                    for(data file:files){
                        System.out.println(file.getId()+" - "+file.getFilename());
                    }
                    System.out.println("Enter the id of file to Un-Hide");
                    int id=Integer.parseInt(sc.nextLine());
                    boolean isVaild=false;
                    for(data file: files){
                        if(file.getId()==id){
                            isVaild=true;
                            break;
                        }
                    }
                    if(isVaild==true){
                        DataDAO.unHide(id);
                    }
                    else {
                        System.out.println("Invalid ID");
                    }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case 0->{
                    System.exit(0);
                }
            }
        }while(true);
    }
}
