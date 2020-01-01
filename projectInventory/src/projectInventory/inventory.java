package projectInventory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class inventory {
	
	public static String message;
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) throws SQLException {
		//this program is designed to send only single command at once 
		message = sc.nextLine();
		String[] message_piece = message.split("\\s+");
		String command = message_piece[0].toLowerCase();
		String item_Name = message_piece[1];
		String item_Brought = message_piece[2];
		String item_Sold = message_piece[3];
		String item_Availability = "0";
		String dbUrl = "jdbc:oracle:thin@myserver:1521:krishna00170";
		String username = "c##krish";
		String pass = "lion";
		Connection myConn = DriverManager.getConnection(dbUrl, username, pass);
		Statement mystmt = myConn.createStatement();
		
	
		switch(command){
			case "create":
				String q1 = "insert into inventoryReport values('" +item_Name+ "', '" +item_Brought+  
                "', '" +item_Sold+ "', '" +item_Availability+ "')"; 
				mystmt.executeUpdate(q1);
				break;
				
			case "updatebuy":
				item_Availability = message_piece[2];
				String q2 = "UPDATE inventoryReport set Quantity = '" + item_Availability + 
	                    "' WHERE ItemName = '" +item_Name+ "'"; 
				String c2 = "UPDATE inventoryReport set ItemValue = (BoughtCost * Quantity) "
						+ "'WHERE ItemName = '" +item_Name + "'";
				mystmt.executeUpdate(q2);
				mystmt.executeUpdate(c2);
				break;
				
			case "updatesell":
				item_Availability = message_piece[2];
				String q3 = "UPDATE inventoryReport set Quantity = '" + item_Availability +  
	                    "' WHERE ItemName = '" +item_Name+ "'"; 
				String c3 = "UPDATE inventoryReport set ItemValue = (BoughtCost * Quantity) "
						+ "'WHERE ItemName = '" +item_Name + "'";
				mystmt.executeUpdate(q3);
				mystmt.executeUpdate(c3);
				break;
				
			case "updatesellprice":
				item_Sold= message_piece[2];
				String q4 = "UPDATE inventoryReport set SellingCost = '" + item_Sold +  
	                    "' WHERE ItemName = '" +item_Name+ "'"; 
				mystmt.executeUpdate(q4);
				break;
				
			case "report":
				ResultSet rs = mystmt.executeQuery("SELECT * FROM inventoryReport");
		         System.out.println("Item Name		  	Brought At		    Sold At				Availability			value");
		         System.out.println("-----------		-------------		-----------			---------------			--------");
		         while (rs.next()) {
		            String ItemName = rs.getString("ItemName");
		            Double BroughtAt = rs.getDouble("BoughtCost");
		            Double SoldAt = rs.getDouble("SellingCost");
		            int Availability = rs.getInt("Availability");
		            Double value = rs.getDouble("value");
		            System.out.println(ItemName+"			"+BroughtAt+"			"+SoldAt+"			"+Availability+"			"+value);
		         }
		         System.out.println("-----------		-------------		-----------			---------------			--------");
		         int totalValue = mystmt.executeUpdate("SELECT sum(ItemValue) from inventoryManagement");
		         System.out.println("Total Value 																	"+totalValue);
		         System.out.println("Profit since previous report																");
		         break;
		         
			case "delete":
				String q6 = "DELETE from inventoryReport WHERE ItemName = '" + item_Name + "'"; 
				mystmt.executeUpdate(q6);
				break;
				
			default:
				System.out.println ("PLEASE CHECK YOUR COMMAND OR YOU MIGHT HAVE CHOOSEN A WRONG COMMAND.");
		}
		
		// TODO Auto-generated method stub

	}

}
