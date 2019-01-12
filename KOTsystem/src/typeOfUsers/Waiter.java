package typeOfUsers;

import java.util.Scanner;
import java.util.Random;
import main.Main;

public class Waiter {
	public String orderId = null;
	public String tableName = null;
	public String getOrderid() {
		return orderId;
	}
	public void setOrderid(String orderid) {
		orderId = orderid;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	private String tableId = null;

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	
	
	static Scanner scan = new Scanner(System.in);
	
	Random rand = new Random(); 
    

	
	

	public void open() {
		WaiterFunction wf= new WaiterFunction();

		System.out.println();
		System.out.println("******* Hi Sir..! *******");
		System.out.println("\t1 - New Order \n\t2 - Edit  or Delete Order \n\t3 - View Order Status   \n\t4 - Make Invoice \n\t5 - Bill \n\t0 main Manu");
		int input = scan.nextInt();
		
		

		if (input == 1) {
			wf.foodItems();
			wf.setOrderid(wf.getTableName()+ rand.nextInt(100));
			wf.getOrder();
		} else {
			if (input == 2) {
				//UpdateOrder();
				open();
		} else {
			if (input == 3) {
				
				wf.orderStatus();
				open();
				
			} else {
				if (input == 4) {
					wf.createInvoice();
					
				} else {if (input == 5) {
					wf.printBill();
					
				} else {
					if (input == 0) {
						Main.userType();
						
					} else {
						
						System.err.println("|****| Please Select Your Correct Type ! |****|");
						
						open();
						
					}
				}
					
				}

			}

		}

		}
	}
	



}


