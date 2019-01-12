package typeOfUsers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import dBConnection.DB;
import main.DBTableView;

public class KitchenUserFunction {
	static Scanner scan = new Scanner(System.in);
	private String orderID = null;
	KitchenUser ku=new KitchenUser();
	
	public void ShowOrders() {
		Connection conn = null;
	try {

		conn = DB.getConnection();
		System.err.println("***********************Pending Food Orders ************************");
		DBTableView.printPendingOrderWithID(conn, "kotsystem.`orders`");
		System.err.println("*****************|Cooking Food Orders *****************");
		DBTableView.printActionOrderWithID(conn, "kotsystem.`orders`");			

	} catch (Exception e) {
		e.printStackTrace();
	}

	finally {

		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
public void ShowTakeOrders() {
	Connection conn = null;
	try {

		conn = DB.getConnection();
		
     

	} catch (Exception e) {
		e.printStackTrace();
	}

	finally {

		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
public void takeTheOrder() {
	
	System.out.println("\n\t Enter the order ID*");
	orderID = scan.next();
	Connection conn = null;
	PreparedStatement pps;
	ResultSet re;


	try {
		conn = DB.getConnection();

		String query = "SELECT * FROM `orders` WHERE `orderId` = ? ";
		pps = (PreparedStatement) conn.prepareStatement(query);
		pps.setString(1, orderID);
		
		re = pps.executeQuery(); 
		if (!re.next()) {
			
			System.out.println("******Please Select Available Order ******");

			takeTheOrder();
		}else {
			try {
			Statement stment = conn.createStatement();
			String value = "UPDATE `orders` SET `status`='Action' WHERE `OrderId` = '"+orderID+"'";
			stment.executeUpdate(value);
		    System.out.println("Order token successfully ");
		    ShowTakeOrders();
		    foodIsReady();
			}catch (SQLException e) {
			      e.printStackTrace();
		    }
		}
	}catch (Exception e) {
		// TODO: handle exception
	}
	
}
public void foodIsReady() {
	System.out.println("The Food Is Ready \n\t 1 - yes ");
	int Ready = scan.nextInt();

	if (Ready==1) {
		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stmt = conn.createStatement();

			String sql = "UPDATE `orders` SET `status`='done' WHERE `OrderId` = '"+orderID+"'";
		    stmt.executeUpdate(sql);
		    System.out.println("Order Update is successfully ");
		   ku. start();
			}catch (SQLException e) {
			      e.printStackTrace();
		    }
		
	} else {
		ku.start();
	}

}

}
