package auction1;
import static auction1.Auction.mainDir;
import java.io.*;
import java.util.*;
import static auction1.Auction.userID;
import static auction1.Auction.accountType;

public class FileIO {
    
    public static double highestBidValue(int itemID) throws FileNotFoundException{
        String itemLocation = mainDir + "/item_"+itemID+".txt";
        Scanner readFile = new Scanner(new FileInputStream(itemLocation));
        double highest=0;
        String details;
        String[] data;
        for(int i=0;readFile.hasNextLine();i++){
            data = (readFile.nextLine()).split("__");
            if(i==0){
                highest = Double.parseDouble(data[3]);
            }
            else{
                if(Double.parseDouble(data[1]) >= highest)
                    highest = Double.parseDouble(data[1]);
            }
        }
        return highest;
    }
    public static double secondHighestBidValue(int itemID) throws FileNotFoundException{
     String itemLocation = mainDir + "/item_"+itemID+".txt";
        Scanner readFile = new Scanner(new FileInputStream(itemLocation));
        LinkedList<Double> xc = new LinkedList<>();
        double highest=0;
        String details;
        String[] data;
        for(int i=0;readFile.hasNextLine();i++){
            data = (readFile.nextLine()).split("__");
            if(i==0){
                highest = Double.parseDouble(data[3]);
            }
            else{
                xc.addLast(Double.parseDouble(data[1]));
            }
        }
       

        Collections.sort(xc);
        xc.removeLast();
        return xc.getLast();
    }
    
    public static String highestBidPerson(int itemID) throws FileNotFoundException{
        String itemLocation = mainDir + "/item_"+itemID+".txt";
        Scanner readFile = new Scanner(new FileInputStream(itemLocation));
        double highest=0;
        String details;
        String highestPerson = null;
        String[] data;
        for(int i=0;readFile.hasNextLine();i++){
            data = (readFile.nextLine()).split("__");
            if(i==0){
                highest = Double.parseDouble(data[3]);
            }
            else{
                if(Double.parseDouble(data[1]) >= highest){
                    highest = Double.parseDouble(data[1]);
                    highestPerson = data[0];
                }
            }
        }
        return highestPerson;
    }
    
    public static String findProfileName(String userID,String accountType) throws FileNotFoundException{
        String username = null;
        String loginLink = mainDir + "/" + accountType + "_login.txt";
        Scanner readLogin = new Scanner(new FileInputStream(loginLink));
        String details;
        String[] data;
        while(readLogin.hasNextLine()){
            details = readLogin.nextLine();
            data = details.split("__");
            if(data[0].equals(userID+""))
                username = data[1];
        }
        return username;
    }
    
    public static void removeSelfFromAuction(int itemID) throws FileNotFoundException{
        String profileLink = mainDir + "/bidder_" + userID + ".txt";
        String itemLink = mainDir + "/item_" + itemID + ".txt";
        String temp = mainDir + "/temp.txt";
        
        //Remove bidder from item file
        Scanner readItem = new Scanner(new FileInputStream(itemLink));
        PrintWriter copy = new PrintWriter(new FileOutputStream(temp));
        String details;
        String[] data;
        for(int i=0;readItem.hasNextLine();i++){
            details = readItem.nextLine();
            data = details.split("__");
            if(data[0].equals(userID)){
                continue;
            }
        copy.println(details);
        }
        copy.close();
        readItem.close();
        
        Scanner readTemp = new Scanner(new FileInputStream(temp));
        PrintWriter paste = new PrintWriter(new FileOutputStream(profileLink));
        while(readTemp.hasNextLine()){
            paste.println(readTemp.nextLine());
        }
        paste.close();
        readTemp.close();
        
        //Remove bid item from his own profile
        Scanner readProfile = new Scanner(new FileInputStream(itemLink));
        PrintWriter copyA = new PrintWriter(new FileOutputStream(temp));
        for(int i=0;readProfile.hasNextLine();i++){
            details = readProfile.nextLine();
            if(i<5){
                copyA.println(details);
            }else{
                data = details.split("__");
                if(!data[0].equals(itemID)){
                    copyA.println(details);
                }
            }
        }
        readProfile.close();
        copyA.close();
        
        Scanner readTempB = new Scanner(new FileInputStream(temp));
        PrintWriter pasteBidder = new PrintWriter(new FileOutputStream(itemLink));
        while(readTempB.hasNextLine()){
            pasteBidder.println(readTempB.nextLine());
        }
        pasteBidder.close();
        readTempB.close();
    }
    
    public static String checkTypeOfAuction(String itemID) throws FileNotFoundException{
        String itemLoc = mainDir + "/item_"+itemID + ".txt";
        Scanner readFile = new Scanner(new FileInputStream(itemLoc));
        String details;
        String[] data;
        details = readFile.nextLine();
        data = details.split("__");
        if(data[4].equals("1"))
            return "English";
        else if(data[4].equals("2"))
            return "Vickrey";
        else if(data[4].equals("3"))
            return "Reserved";
        else if(data[4].equals("4"))
            return "Blind";
        else
            return null;
    }
    
    public static boolean changeStatusToClosed(int itemID) throws FileNotFoundException{
        boolean stats = false;
        String itemLocation = mainDir + "/item_"+itemID+".txt";
        String runningItem = mainDir + "/running_item.txt";
        String auctionerLocation = mainDir + "/auctioner_"+userID+".txt";
        String tempLocation = mainDir + "/temp.txt";
        
        //Change the status in item_xxx.txt file
        Scanner readItemFile = new Scanner(new FileInputStream(itemLocation));
        PrintWriter copy = new PrintWriter(new FileOutputStream(tempLocation));
        for(int i=0;readItemFile.hasNextLine();i++){
            String details = readItemFile.nextLine();
            String[] data = details.split("__");
            if(i == 0){
                data[5] = "closed";
                details = data[0]+"__"+data[1]+"__"+data[2]+"__"+data[3]+"__"+data[4]+"__"+data[5];
            }
            copy.println(details);
        }
        copy.close();
        readItemFile.close();
        Scanner readTemp = new Scanner(new FileInputStream(tempLocation));
        PrintWriter paste = new PrintWriter(new FileOutputStream(itemLocation));
        while(readTemp.hasNextLine()){
            paste.println(readTemp.nextLine());
        }
        paste.close();
        readTemp.close();
        
        //Change status in auctioner profile file ( auctioner_xxx.txt )
        Scanner readAuctionerFile = new Scanner(new FileInputStream(auctionerLocation));
        PrintWriter copyAuctionerFile = new PrintWriter(new FileOutputStream(tempLocation));
        for(int i=0;readAuctionerFile.hasNextLine();i++){
            String details = readAuctionerFile.nextLine();
            String[] data = details.split("__");
            if(i == 5){
                data[2] = "closed";
                details = data[0]+"__"+data[1]+"__"+data[2]+"__"+data[3]+"__"+data[4];
            }
            copyAuctionerFile.println(details);
        }
        copyAuctionerFile.close();
        readAuctionerFile.close();
        Scanner readTemp2 = new Scanner(new FileInputStream(tempLocation));
        PrintWriter pasteAuctionerFile = new PrintWriter(new FileOutputStream(auctionerLocation));
        while(readTemp2.hasNextLine()){
            pasteAuctionerFile.println(readTemp2.nextLine());
        }
        pasteAuctionerFile.close();
        readTemp2.close();
        
        return true;
    }
    public static boolean checkUserJoin(int itemID) throws FileNotFoundException{ //return true kalau jumpa userID
    String itemLoc = mainDir + "/item_"+ itemID + ".txt";
    String details;
    String[] data;
    Scanner readFile = new Scanner(new FileInputStream(itemLoc));
    while(readFile.hasNextLine()){
        details = readFile.nextLine();
        data = details.split("__");
        if(data[0].equals(userID)){
            return true;
        }
    }
    readFile.close();
    return false;
    }
    
     public static boolean changeStatusToReject(int itemID) throws FileNotFoundException{
        boolean stats = false;
        String itemLocation = mainDir + "/item_"+itemID+".txt";
        String runningItem = mainDir + "/running_item.txt";
        String auctionerLocation = mainDir + "/auctioner_"+userID+".txt";
        String tempLocation = mainDir + "/temp.txt";
        
        //Change the status in item_xxx.txt file
        Scanner readItemFile = new Scanner(new FileInputStream(itemLocation));
        PrintWriter copy = new PrintWriter(new FileOutputStream(tempLocation));
        for(int i=0;readItemFile.hasNextLine();i++){
            String details = readItemFile.nextLine();
            String[] data = details.split("__");
            if(i == 0){
                data[5] = "reject";
                details = data[0]+"__"+data[1]+"__"+data[2]+"__"+data[3]+"__"+data[4]+"__"+data[5];
            }
            copy.println(details);
        }
        copy.close();
        readItemFile.close();
        Scanner readTemp = new Scanner(new FileInputStream(tempLocation));
        PrintWriter paste = new PrintWriter(new FileOutputStream(itemLocation));
        while(readTemp.hasNextLine()){
            paste.println(readTemp.nextLine());
        }
        paste.close();
        readTemp.close();
        
        //Change status in auctioner profile file ( auctioner_xxx.txt )
        Scanner readAuctionerFile = new Scanner(new FileInputStream(auctionerLocation));
        PrintWriter copyAuctionerFile = new PrintWriter(new FileOutputStream(tempLocation));
        for(int i=0;readAuctionerFile.hasNextLine();i++){
            String details = readAuctionerFile.nextLine();
            String[] data = details.split("__");
            if(i == 5){
                data[2] = "reject";
                details = data[0]+"__"+data[1]+"__"+data[2]+"__"+data[3]+"__"+data[4];
            }
            copyAuctionerFile.println(details);
        }
        copyAuctionerFile.close();
        readAuctionerFile.close();
        Scanner readTemp2 = new Scanner(new FileInputStream(tempLocation));
        PrintWriter pasteAuctionerFile = new PrintWriter(new FileOutputStream(auctionerLocation));
        while(readTemp2.hasNextLine()){
            pasteAuctionerFile.println(readTemp2.nextLine());
        }
        pasteAuctionerFile.close();
        readTemp2.close();
        
        return true;
    }
     
    
    public static void changePassword(String password) throws FileNotFoundException{
        String tempLink = mainDir + "/temp.txt";
        String loginDBLink = mainDir + "/" + accountType + "_login.txt";
        String details;
        Scanner readDetailsUser = new Scanner(new FileInputStream(loginDBLink));
        PrintWriter writeOnTempUser = new PrintWriter(new FileOutputStream(tempLink));
        String[] data;
        for(int i=0;readDetailsUser.hasNextLine();i++){
            details = readDetailsUser.nextLine();
            data = details.split("__");
            if(data[0].equals(userID)){
                details = data[0] + "__" + data[1] + "__" + password;
            }
            writeOnTempUser.println(details);
        }
        readDetailsUser.close();
        writeOnTempUser.close();
        
        Scanner readTempUser = new Scanner(new FileInputStream(tempLink));
        PrintWriter writeOnUser = new PrintWriter(new FileOutputStream(loginDBLink));
        while(readTempUser.hasNextLine()){
            writeOnUser.println(readTempUser.nextLine());
        }
        writeOnUser.close();
        readTempUser.close();
        
    }
    
    public static void changeUsername(String username) throws FileNotFoundException{
        
        String tempLink = mainDir + "/temp.txt";
        String loginDB = mainDir + "/" + accountType + "_" + userID + ".txt";
        String profileLink = mainDir + "/" + accountType + "_login.txt";
        
        //Edit the profile username on profile of user
        Scanner readDetails = new Scanner(new FileInputStream(loginDB));
        PrintWriter writeOnTemp = new PrintWriter(new FileOutputStream(tempLink));
        String details;
        for(int i=0;readDetails.hasNextLine();i++){
            details = readDetails.nextLine();
            if(i==1){
                details = username;
            }
            writeOnTemp.println(details);
        }
        readDetails.close();
        writeOnTemp.close();
        
        Scanner readTemp = new Scanner(new FileInputStream(tempLink));
        PrintWriter writeOnLogin = new PrintWriter(new FileOutputStream(loginDB));
        while(readTemp.hasNextLine()){
            writeOnLogin.println(readTemp.nextLine());
        }
        writeOnLogin.close();
        readTemp.close();
        
        //Edit the profile username on loginDB
        Scanner readDetailsUser = new Scanner(new FileInputStream(profileLink));
        PrintWriter writeOnTempUser = new PrintWriter(new FileOutputStream(tempLink));
        String[] data;
        for(int i=0;readDetailsUser.hasNextLine();i++){
            details = readDetailsUser.nextLine();
            data = details.split("__");
            if(data[0].equals(userID)){
                details = data[0] + "__" + username + "__" + data[2];
            }
            writeOnTempUser.println(details);
        }
        readDetailsUser.close();
        writeOnTempUser.close();
        
        Scanner readTempUser = new Scanner(new FileInputStream(tempLink));
        PrintWriter writeOnUser = new PrintWriter(new FileOutputStream(profileLink));
        while(readTempUser.hasNextLine()){
            writeOnUser.println(readTempUser.nextLine());
        }
        writeOnUser.close();
        readTempUser.close();
    }
    
    public static String getBidderStatus(int i){
        if(i>=0 && i<11)
            return "Newbie";
        else if(i<21)
            return "Intermediate";
        else
        return "Pro";
    }
    
    public static String checkIfItemClosed(int idItem) throws FileNotFoundException{
        String item = mainDir + "/item_"+idItem+".txt";
        Scanner readItem = new Scanner(new FileInputStream(item));
        String details = readItem.nextLine();
        String[] data = details.split("__");
       
        return data[5];
    }
    
    public static String auctionType(int i){
        if(i==1)
            return "English Auction";
        else if(i==2)
            return "Vickrey Auction";
        else if(i==3)
            return "Reserved Auction";
        else if(i==4)
            return "Blind Auction";
        else
            return null;
    }
    public static void visitProfile() throws FileNotFoundException{
        System.out.println("\n[1] - Auctioner");
        System.out.println("[2] - Bidder");
        System.out.print("Please choose who you want to view : ");
        Scanner kb = new Scanner(System.in);
        int type = kb.nextInt();
        String loginDB = mainDir;
        String accType = "blabla";
        if(type==1){
            loginDB = mainDir + "/auctioner_login.txt";
            accType = "auctioner";
        }
        else{
            loginDB = mainDir + "/bidder_login.txt";
            accType = "bidder";
        }
        System.out.println("Login link : "+loginDB);
        String details;
        String[] data;
        System.out.println("\nUser ID -- Username");
        Scanner readLoginDB = new Scanner(new FileInputStream(loginDB));
        while(readLoginDB.hasNextLine()){
            details = readLoginDB.nextLine();
            data = details.split("__");
            System.out.println(data[0]+" -- "+data[1]);
        }
        readLoginDB.close();
        String id;
        System.out.print("\nPlease enter the user ID you would like to visit : ");
        kb.nextLine();
        id = kb.nextLine();
        visitOthersProfile(id,accType);
    }
    
    public static boolean checkStatusIfClosed(int itemID) throws FileNotFoundException{
        String itemLoc = mainDir + "/item_"+itemID + ".txt";
        Scanner readItem = new Scanner(new FileInputStream(itemLoc));
        String[] data;
        data = readItem.nextLine().split("__");
        if(data[5].equals("closed")){
            return true;
        }
        else
            return false;
    }
    
    public static void visitOthersProfile(String ID,String account) throws FileNotFoundException{
        String readProfileLink = mainDir + "/" + account + "_" + ID + ".txt";
        Scanner readProfile = new Scanner(new FileInputStream(readProfileLink));
        String details;
        String[] data;
        System.out.println("");
        System.out.println("User ID : "+readProfile.nextLine());
        System.out.println("Username : "+readProfile.nextLine());
        details = readProfile.nextLine();
            if(account.equals("auctioner"))
                System.out.println("Items auction : "+details+" ("+getBidderStatus(Integer.parseInt(details))+")");
            else if(account.equals("bidder"))
                System.out.println("Auction joined : "+details+" ("+getBidderStatus(Integer.parseInt(details))+")");
        readProfile.nextLine();
        readProfile.nextLine();
        System.out.println("");
        
        if(readProfile.hasNextLine()){
            if(account.equals("auctioner"))
            System.out.println("\nItem ID - Item Name - Status - Starting Bid - Auction Type");
        else
            System.out.println("Item ID - Item Name - Bid Offered");
        System.out.println("");
            for(int i=0;readProfile.hasNextLine();i++){
                details = readProfile.nextLine();
                    data = details.split("__");
                    if(account.equals("auctioner"))
                        System.out.println(data[0]+" - "+data[1]+" - "+data[2]+" - "+data[3]+" - "+auctionType(Integer.parseInt(data[4])));
                    else
                        System.out.println(data[0]+" - "+data[1]+" - "+data[2]);
            }
        }
        readProfile.close();
    }
    
    public static void deleteAccount(String userID) throws FileNotFoundException{
        String accountLoc = mainDir + "/bidder_login.txt";
        String temp = mainDir + "/temp.txt";
        
//COPY
        Scanner readDB = new Scanner(new FileInputStream(accountLoc));
        PrintWriter writeTemp = new PrintWriter(new FileOutputStream(temp));
        String details;
        String[] data;
        while(readDB.hasNextLine()){
            details = readDB.nextLine();
            data = details.split("__");
            if(data[0].equals(userID)){
                continue;
            }          
            writeTemp.println(details);
        }
        writeTemp.close();
        readDB.close();
//PASTE
        Scanner readTemp = new Scanner(new FileInputStream(temp));
        PrintWriter writeDB = new PrintWriter(new FileOutputStream(accountLoc));
        while(readTemp.hasNextLine()){
            writeDB.println(readTemp.nextLine());
        }
        writeDB.close();
        readTemp.close();
        
    }
    
    public static String accountStatus(String winnerID) throws FileNotFoundException{
        String winnerFile = mainDir + "/bidder_"+winnerID+".txt";
        Scanner readFile = new Scanner(new FileInputStream(winnerFile));
        readFile.nextLine();
        readFile.nextLine();
        String status = readFile.nextLine();
        readFile.close();
        int stat = Integer.parseInt(status);
        if(stat<11)
            return "Newbie";
        else if(stat<21)
            return "Intermediate";
        else
            return "Pro";
    }
}
