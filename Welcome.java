package views;

import Service.SendOTPService;
import Service.UserService;
import Service.generateOTP;
import dao.userDAO;
import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Welcome {
    public void welcomescreen(){
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the FileHider App");
        System.out.println("Press 1 for LOGIN");
        System.out.println("Press 2 for SIGNUP");
        System.out.println("Press 0 for EXIT");
        int choice= 0;
        try{
            choice=Integer.parseInt(br.readLine());
        }catch(IOException e){
            e.printStackTrace();
        }
        switch (choice){
            case 1 -> login();
            case 2 -> signup();
            case 0 -> System.exit(0);
        }

    }

    private void signup() {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Your Name");
        String name =sc.nextLine();
        System.out.println("Enter Your Email");
        String email=sc.nextLine();
        try{
            if(userDAO.isExist(email)){
                System.out.println("User Already Exists.");
            }
            else {
                String genOTP = generateOTP.getOTP();
                SendOTPService.sendOTP(email,genOTP);
                System.out.println("Enter the OTP");
                String otp=sc.nextLine();
                if(otp.equals(genOTP)){
                    User user =new User(name,email);
                    UserService.saveUser(user);
                    System.out.println("Signed up successfully...");
                }
                else {
                    System.out.println("Invalid OTP");
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void login() {
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter Email");
        String email=sc.nextLine();
        try{
            if(userDAO.isExist(email)){
                String genOTP = generateOTP.getOTP();
                SendOTPService.sendOTP(email,genOTP);
                System.out.println("Enter the OTP");
                String otp=sc.nextLine();
                if(otp.equals(genOTP)){
                    new UserView(email).home();
                }
                else {
                    System.out.println("Invalid OTP");
                }
            }
            else {
                System.out.println("User Not Found");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }
}
