package auction1;
import static auction1.Auction.mainDir;
import java.util.*;
import java.io.*;
import static auction1.Auction.userID;
import static auction1.Auction.accountType;

public class Bidder {
    public static void main() throws FileNotFoundException{
        System.out.println("\n[ Bidder menu ]");
        System.out.println("< 1 > - Find Auction");
        System.out.println("< 2 > - View Profile");
        System.out.println("< 3 > - Change Username");
        System.out.println("< 4 > - Change Password");
        System.out.println("< 5 > - Logout");
        System.out.println("< 6 > - Delete Account");
        Scanner kb = new Scanner(System.in);
        System.out.print("Option : ");
        int option = kb.nextInt();
        //switch case goes here
        switch(option){
            case 1 : findAuction();
                break;
            case 2 : viewProfile();
                break;
            case 3 : changeUsername();
                break;
            case 4 : changePassword();
                break;
            case 5 : System.err.println("[ Logged Out ]");
                Auction.main();
                break;
            case 6 : FileIO.deleteAccount(userID);
                break;
            default : main();
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
    public static void findAuction() throws FileNotFoundException{
        System.out.println("\n[ Find Auction ]");
        System.out.println("< 1 > - English Auction");
        System.out.println("< 2 > - Vickrey Auction");
        System.out.println("< 3 > - Reserve Auction");
        System.out.println("< 4 > - Blind Auction");
        System.out.println("< 5 > - Display All");
        System.out.print("Please pick the type of auction you favor : ");
        Scanner kb = new Scanner(System.in);
        int auctionType = kb.nextInt();
        if(auctionType == 5)
            listAllAuction();
        else
            listAuctionByType(auctionType);
    }
    public static void listAuctionByType(int auctionType) throws FileNotFoundException{
        String auctionListLocation = mainDir+"/running_item.txt";
        Scanner readAuctionList = new Scanner(new FileInputStream(auctionListLocation));
        String details;
        String[] data;
        System.out.println("\nItem ID\tItem Name\tStart price\tSeller ID");
        while(readAuctionList.hasNextLine()){
            data = readAuctionList.nextLine().split("__");
            //data[0] = itemID
            //data[1] = itemName
            //data[2] = Start price
            //data[3] = auctionType
            //data[4] = sellerID
            if(Integer.parseInt(data[3]) == auctionType)
            System.out.println(data[0]+"\t"+data[1]+"\t\t"+data[2]+"\t\t"+data[4]);
        }
        readAuctionList.close();
        userPickAuction();
    }
    
    public static void listAllAuction() throws FileNotFoundException{
        String auctionListLocation = mainDir+"/running_item.txt";
        Scanner readAuctionList = new Scanner(new FileInputStream(auctionListLocation));
        String details;
        String[] data;
        System.out.println("\n[ Item ID -- Item Name -- Start price -- Auction Type -- Seller ID -- Status ]\n");
        while(readAuctionList.hasNextLine()){
            data = readAuctionList.nextLine().split("__");
            if(data[3].equals("1"))
                data[3] = "English Auction";
            else if(data[3].equals("2"))
                data[3] = "Vickrey Auction";
            else if(data[3].equals("3"))
                data[3] = "Reserve Auction";
            else if(data[3].equals("4"))
                data[3] = "Blind Auction";
            System.out.println(data[0]+" -- "+data[1]+" -- RM "+data[2]+" -- ("+data[3]+") -- "+FileIO.findProfileName(data[4], "auctioner")+" ("+FileIO.checkIfItemClosed(Integer.parseInt(data[0]))+")");
        }
        readAuctionList.close();
        userPickAuction();
    }
    public static void viewProfile() throws FileNotFoundException{
        System.out.println("< 1 > - View My Profile");
        System.out.println("< 2 > - View Other User Profile");
        Scanner kb = new Scanner(System.in);
        System.out.print("Your pick : ");
        int pick = kb.nextInt();
        if(pick == 2)
            FileIO.visitProfile();
        else if(pick == 1)
            FileIO.visitOthersProfile(userID,accountType);
        else
            viewProfile();
    }
    public static void userPickAuction() throws FileNotFoundException{
        System.out.print("\nEnter the item ID of auction you would like to join : ");
        Scanner kb = new Scanner(System.in);
        int itemID = kb.nextInt();
        String itemLocation = mainDir+"/item_"+itemID+".txt";
        Scanner readItemFile = new Scanner(new FileInputStream(itemLocation));
        String[] data;
        data = readItemFile.nextLine().split("__");
        readItemFile.close();
        if(data[4].equals("1"))
            English.main(itemID);
        if(data[4].equals("2"))
            Vickrey.main(itemID);
        if(data[4].equals("3"))
            Reverse.main(itemID);
        if(data[4].equals("4"))
            Blind.main(itemID);
        
    }
    
}
