package auction1;
import static auction1.Auction.mainDir;
import java.io.*;
import java.util.*;
import static auction1.Auction.userID;
import static auction1.Auction.accountType;
public class English {
    
    public static String winner;
    
    public static void main(int itemID) throws FileNotFoundException{
        displayDetails(itemID);
        join(itemID);
    }
    
    public static void join(int itemID) throws FileNotFoundException{
        String itemFile = mainDir + "/item_"+itemID+".txt";
        String updateProfile = mainDir + "/bidder_"+userID+".txt";
        Scanner kb = new Scanner(System.in);
        PrintWriter updateItemFile = new PrintWriter(new FileOutputStream(itemFile,true));
        Scanner readItemFile = new Scanner(new FileInputStream(itemFile));
        String[] data;
        double tempBid;
        String details;
        data = readItemFile.nextLine().split("__"); //read first line (details about the item)
        double highestBid;
        highestBid = Double.parseDouble(data[3]);
        if(data[5].equals("running")){
        System.out.println("[0] - Remove yourself");
        System.out.println("[-1] - Cancel Bid");
        System.out.print("Enter value of bid (-1 to cancel): ");
        double bid = kb.nextDouble();
            if(bid == -1){
                Bidder.main();
            }
           else if(bid == 0){
                FileIO.removeSelfFromAuction(itemID);
                Bidder.main();
            }
                while(readItemFile.hasNextLine()){
                    details = readItemFile.nextLine();
                    data = details.split("__");
                    tempBid = Double.parseDouble(data[1]);
                    if(tempBid>highestBid)
                        highestBid = tempBid;
                }
                readItemFile.close();
                if(bid>highestBid){
            updateItemFile.println(userID+"__"+bid);
            System.out.println("Successfull !");
            updateBidderProfile(itemID,bid);
            updateIntJoinedAuction();
            updateItemFile.close();
        }else{
            System.err.println("Please offer higher bid !");
            join(itemID);
        }
        } //end IF
        else{
            System.out.println("Sorry,the auction for this item has been closed");
            //System.out.println("The winner is "+winner);
            String winnerID = FileIO.highestBidPerson(itemID);
            System.out.println("The winner is "+FileIO.findProfileName(winnerID, accountType)+"("+FileIO.accountStatus(winnerID)+") with bid of RM "+FileIO.highestBidValue(itemID));
            Bidder.main();
        }
    }
    
    public static void displayDetails(int itemID) throws FileNotFoundException{
        String itemLocation = mainDir+"/item_"+itemID+".txt";
        System.out.println("\n===================================================");
        Scanner readItemDetails = new Scanner(new FileInputStream(itemLocation));
        String[] data;
        for(int i=0;readItemDetails.hasNextLine();i++){
            data = readItemDetails.nextLine().split("__");
            if(i==1){
                System.out.println("---=== [ Bidder Name\tBid Price ] ===---");
            }
            if(i==0){
                
                    System.out.println("Item ID : "+data[0]);
                    System.out.println("Item Name : "+data[2]);
                    System.out.println("Bid Starting Price : "+data[3]);
            }
            else{
                String userName = mainDir + "/bidder_"+data[0]+".txt";
                Scanner readName = new Scanner(new FileInputStream(userName));
                readName.nextLine();
                String bidderName = readName.nextLine();
                System.out.println("\t"+bidderName+"\t\tRM"+data[1]);
                winner = bidderName;
            }
        }
        readItemDetails.close();
    }
    
    public static void updateBidderProfile(int itemID,double bid) throws FileNotFoundException{
            String itemFile = mainDir + "/item_"+itemID+".txt";
            Scanner readItem = new Scanner(new FileInputStream(itemFile));
            String profileBidder = mainDir + "/bidder_"+userID+".txt";
            PrintWriter updateProfileBidder = new PrintWriter(new FileOutputStream(profileBidder,true));
            String details;
            String[] data;
            details = readItem.nextLine();
            data = details.split("__");
            updateProfileBidder.println(data[0]+"__"+data[2]+"__"+bid);
            updateProfileBidder.close();
            readItem.close();
    }
    
    public static void updateIntJoinedAuction() throws FileNotFoundException{
        String profileLoc = mainDir + "/bidder_"+userID+".txt";
        String tempLoc = mainDir + "/temp.txt";
        Scanner readProfile = new Scanner(new FileInputStream(profileLoc));
        PrintWriter copy = new PrintWriter(new FileOutputStream(tempLoc));
        String details;
        for(int i=0;readProfile.hasNextLine();i++){
            details = readProfile.nextLine();
            if(i==2){
                int auctionJoined = Integer.parseInt(details);
                auctionJoined++;
                details = auctionJoined+"";
            }
            copy.println(details);
        }
        copy.close();
        readProfile.close();
        
        Scanner readTemp = new Scanner(new FileInputStream(tempLoc));
        PrintWriter paste = new PrintWriter(new FileOutputStream(profileLoc));
        
        while(readTemp.hasNextLine()){
            paste.println(readTemp.nextLine());
        }
        
        paste.close();
        readTemp.close();
    }
}
