package auction1;
import java.util.*;
import java.io.*;
public class Auction {
    public static String mainDir = "/Users/salman/NetBeansProjects/auction1/";
    public static String accountType,userID,accountName,dir;
    
    public static void main(String[] args) throws FileNotFoundException {
        main();
    }
    public static void main() throws FileNotFoundException{
        File theDir = new File(mainDir);
        if(!theDir.exists())
            theDir.mkdir();
        System.out.println("-= Welcome to the Auction Program ! =-");
        System.out.println("\n[ Menu ]");
        System.out.println("< 1 > - Login account");
        System.out.println("< 2 > - Create an account");
        System.out.print("\nPlease pick your option : ");
        Scanner kb = new Scanner(System.in);
        int choice = kb.nextInt();
        switch(choice){
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            default : System.out.println("Error !");
        }
        
    }
    public static void login() throws FileNotFoundException{
        Scanner kb = new Scanner(System.in);
        System.out.println("\n[ Login ]");
        System.out.println("< 1 > - Auctioner");
        System.out.println("< 2 > - Bidder");
        System.out.print("\nSelect account type : ");
        int type = kb.nextInt();
        if(type!=1 && type!=2){
            System.err.println("ERROR !");
         login();  
        }
        if(type == 1)
            dir = mainDir+"/auctioner_login.txt";
        else
            dir = mainDir+"/bidder_login.txt";
        System.out.print("Username : ");
        kb.nextLine();
        String username = kb.nextLine();
        System.out.print("Password : ");
        String password = kb.nextLine();
        try {
            Scanner check = new Scanner(new FileInputStream(dir));
        } catch (FileNotFoundException ex) {
            System.out.println("No records were found !\nYou have to register first !");
            register();
        }
        Scanner read = new Scanner(new FileInputStream(dir));
        String[] details;
        boolean checkUsernamePass = false;
        while(read.hasNextLine()){
            details = read.nextLine().split("__");
            if(username.equals(details[1])&&password.equals(details[2])){
                checkUsernamePass = true;
                System.out.println("Login successful !");
                userID = details[0];
                accountName = details[1];
                if(type==1){
                    accountType = "auctioner";
                    Auctioner.main();
                }
                else{
                    accountType = "bidder";
                    Bidder.main();
                }
            }
        }
        if(!checkUsernamePass){
        System.out.println("Login failed !");
        login();
        }
    }
    public static void register() throws FileNotFoundException{
        Scanner kb = new Scanner(System.in);
        System.out.println("\n[ New Registration ]");
        System.out.println("< 1 > - Auctioner");
        System.out.println("< 2 > - Bidder");
        System.out.print("\nSelect account type : ");
        int type = kb.nextInt();
        if(type!=1 && type!=2){
            System.err.println("ERROR !");
         register();  
        }
        String profile;
        if(type == 1){
            profile = mainDir + "/auctioner_";
            dir = mainDir+"/auctioner_login.txt";
        }
        else{
            profile = mainDir + "/bidder_";
            dir = mainDir+"/bidder_login.txt";
        }
        System.out.print("New Username : ");
        kb.nextLine();
        String username = kb.nextLine();
        System.out.print("New Password : ");
        String password = kb.nextLine();
        Random id = new Random();
        int userID = id.nextInt(9999)+10000;
        System.out.println("You've successfully resgistered !\n");
        
        try {
            Scanner check = new Scanner(new FileInputStream(dir));
        } catch (FileNotFoundException ex){
            PrintWriter createFile = new PrintWriter(new FileOutputStream(dir));
            createFile.close();
        }
        //Each auctioner or bidder will have their own profile page (textfile)
        //it will list the details such as name,auction item list,freq of bid,etc
        PrintWriter write = new PrintWriter(new FileOutputStream(dir,true));
        String details = userID+"__"+username+"__"+password;
        write.println(details);
        write.close();
        profile = profile + userID + ".txt";
        PrintWriter createProfile = new PrintWriter(new FileOutputStream(profile));
            createProfile.println(userID);
            createProfile.println(username);
            createProfile.println("0");
            createProfile.println("0");
            createProfile.println("==========");
        createProfile.close();
        login();
    }
    
}
