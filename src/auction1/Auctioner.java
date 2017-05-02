package auction1;
import static auction1.Auction.mainDir;
import java.util.*;
import java.io.*;
import static auction1.Auction.userID;
import static auction1.Auction.accountType;

public class Auctioner {
    public static void main() throws FileNotFoundException{
        System.out.println("\n[ Auctioner menu ]");
        System.out.println("< 1 > - Open/Create Auction");
        System.out.println("< 2 > - Close Auction");
        System.out.println("< 3 > - View Profile");
        System.out.println("< 4 > - Change Username");
        System.out.println("< 5 > - Change Password");
        System.out.println("< 6 > - Logout");
        System.out.println("< 7 > - Delete Account");
        System.out.print("Option : ");
        Scanner kb = new Scanner(System.in);
        int option = kb.nextInt();
        if(!(option>=1 && option<=7)){
            System.err.println("Error !");
            main();
        }
        else{
            switch(option){
                case 1:addItem();
                    break;
                case 2:editItem();
                    break;
                case 3:viewProfile();
                    break;
                case 4:changeUsername();
                    break;
                case 5:changePassword();
                    break;
                case 6:Auction.main();
                    break;
                case 7:FileIO.deleteAccount(userID);
                    break;
                default : System.err.println("Error !");
                    main();
            }
        }
    }
    public static void addItem() throws FileNotFoundException{
        Scanner kb = new Scanner(System.in);
        Random rnd = new Random();
        int itemID = rnd.nextInt(99999)+900000;
        System.out.print("Item name : ");
        String itemName = kb.nextLine();
        System.out.print("Starting bid : ");
        double startBid = kb.nextDouble();
        System.out.println("\n[ Auction Type ]");
        System.out.println("< 1 > - English");
        System.out.println("< 2 > - Vickrey");
        System.out.println("< 3 > - Reserve");
        System.out.println("< 4 > - Blind");
        System.out.print("Pick auction type : ");
        int auctionType = kb.nextInt();
        if(!(auctionType>=1 && auctionType<=4)){
            System.err.println("Error !");
            main();
        }
        String itemDetails;
        itemDetails = itemID +"__"+ userID +"__"+ itemName +"__"+ startBid +"__"+ auctionType +"__running";
        String itemDir = mainDir + "/item_"+itemID+".txt";
        PrintWriter createFileItem = new PrintWriter(new FileOutputStream(itemDir));
        createFileItem.println(itemDetails);
        createFileItem.close();
        
        //To read the number of auction the auctioner did,and update it
        String profileLink = mainDir+"/auctioner_"+userID+".txt";
        String tempLink = mainDir+"/temp.txt";
        Scanner readProfile = new Scanner(new FileInputStream(profileLink));
        PrintWriter updateProfile = new PrintWriter(new FileOutputStream(tempLink));
        String readDetails;
        for(int i=0;readProfile.hasNextLine();i++){
            readDetails = readProfile.nextLine();
            if(i==2){
                int numOfAuction = Integer.parseInt(readDetails);
                numOfAuction++;
                readDetails = numOfAuction+"";
            }
            updateProfile.println(readDetails);
        }
        readProfile.close();
        updateProfile.close();
        Scanner copyFile = new Scanner(new FileInputStream(tempLink));
        PrintWriter copier = new PrintWriter(new FileOutputStream(profileLink));
        for(int i=0;copyFile.hasNextLine();i++){
            readDetails = copyFile.nextLine();
            copier.println(readDetails);
        }
        copyFile.close();
        copier.close();
        
        PrintWriter updateAuctionerProfile = new PrintWriter(new FileOutputStream(profileLink,true));
        String itemDetailsOnProfile;
        itemDetailsOnProfile = itemID+"__"+itemName+"__running__"+startBid+"__"+auctionType;
        updateAuctionerProfile.println(itemDetailsOnProfile);
        updateAuctionerProfile.close();
        
        String runningItemLink = mainDir+"/running_item.txt";
        PrintWriter addRunningItem = new PrintWriter(new FileOutputStream(runningItemLink,true));
        String itemDetailsUpdate = itemID+"__"+itemName+"__"+startBid+"__"+auctionType+"__"+userID;
        addRunningItem.println(itemDetailsUpdate);
        addRunningItem.close();
        main();
    }

    
    
    public static void editItem() throws FileNotFoundException{
        String profileDir = mainDir + "/auctioner_"+userID+".txt";
        String itemDir = mainDir + "/running_item.txt";
        Scanner readProfile = new Scanner(new FileInputStream(profileDir));
        String[] data;
        readProfile.nextLine();
        readProfile.nextLine();
        readProfile.nextLine();
        readProfile.nextLine();
        readProfile.nextLine();
        double price;
        if(readProfile.hasNextLine())
            System.out.println("\n [ Item ID -- Item Name -- Status -- Start Bid Price -- Current Bid -- Auction Type ]");
        for(int i=0;readProfile.hasNextLine();i++){
            String details = readProfile.nextLine();
            data = details.split("__");
            int itemID = Integer.parseInt(data[0]);
            String itemLocation = mainDir + "/item_"+itemID+".txt";
            String highPerson = FileIO.highestBidPerson(itemID);
            Double highBid = FileIO.highestBidValue(itemID);
            System.out.println(itemID+" -- "+data[1]+" -- "+data[2]+" -- "+data[3]+" -- "+highBid+"("+FileIO.findProfileName(highPerson, "bidder")+") -- "+FileIO.auctionType(Integer.parseInt(data[4])));
        }
        readProfile.close();
        System.out.println("\n[0] - Main menu");
        System.out.print("Enter item ID to close that auction : ");
        Scanner kb = new Scanner(System.in);
        int itemID = kb.nextInt();
        if(itemID == 0)
            Auctioner.main();
        if(FileIO.checkTypeOfAuction(itemID+"").equals("Reserved")){
            System.out.println("< 1 > - Close Auction (Find winner)");
            System.out.println("< 2 > - Reject Auction (Cancel Auction)");
            System.out.print("Enter your choice : ");
            int s = kb.nextInt();
            if(s == 1){
                FileIO.changeStatusToClosed(itemID);
            }else if(s == 2){
                FileIO.changeStatusToReject(itemID);
            }
                
        }else{
        if(itemID == 0)
            Auctioner.main();    
        else{
        if(FileIO.changeStatusToClosed(itemID))
            System.out.println("Success !");
        main();
        }
    }
    }
    
        public static void changeUsername() throws FileNotFoundException{
        System.out.print("Please enter your new username : ");
        Scanner kb = new Scanner(System.in);
        String username = kb.nextLine();
        FileIO.changeUsername(username);
        main();
    }
        
    public static void changePassword() throws FileNotFoundException{
        System.out.print("Please enter your new password : ");
        Scanner kb = new Scanner(System.in);
        String password = kb.nextLine();
        FileIO.changePassword(password);
        main();
    }
    
    public static void viewProfile() throws FileNotFoundException{
    System.out.println("< 1 > - View My Profile");
    System.out.println("< 2 > - View Other User Profile");
    Scanner kb = new Scanner(System.in);
    System.out.print("Your option : ");
    int pick = kb.nextInt();
    if(pick == 2)
        FileIO.visitProfile();
    else if(pick == 1){
        FileIO.visitOthersProfile(userID,accountType);
        main();
    }
    else{
        viewProfile();
        main();
    }
    }
    
}
