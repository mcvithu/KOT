package typeOfUsers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.mysql.jdbc.PreparedStatement;

import dBConnection.DB;
import main.DBTableView;

public class WaiterFunction {

	public String orderId = null;
	public String tableName = null;

	public String getorderId() {
		return orderId;
	}

	public void setOrderid(String Orderid) {
		orderId = Orderid;
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

	Waiter w = new Waiter();

	public void getOrder() {

		order();

		System.out.println("**********Buy Another Food Item ? ********* \n\r1 -> yes \n\r2 -> No ");
		int input = scan.nextInt();

		if (input == 1) {
			order();
		} else {
			if (input == 2) {
				ShowOrders();
				w.open();
			} else {

				System.err.println("|****|| Please Select Your Correct Type ! ||****|");

				getOrder();
			}
		}
	}

	public void order() {

		System.out.println();
		System.out.print(" Type Food ID :- ");
		String input = scan.next();

		ResultSet re;
		PreparedStatement pps;
		Connection connc = null;

		try {
			connc = DB.getConnection();

			String query = "SELECT * FROM foods WHERE id = ?";

			pps = (PreparedStatement) connc.prepareStatement(query);

			pps.setString(1, input);

			re = pps.executeQuery();
			if (re.next()) {

				System.out.print("********How Many ******** ");
				System.out.println();
				int orderqty = scan.nextInt();

				ResultSet res;

				PreparedStatement ps;
				PreparedStatement preparedStmt;
				Connection conn = null;

				try {
					conn = DB.getConnection();
					ps = (PreparedStatement) conn.prepareStatement(query);
					ps.setString(1, input);
					res = ps.executeQuery();

					while (res.next()) {
						int OrderFoodId = res.getInt("id");
						String OrderFoodName = res.getString("name");
						int OrderStock = res.getInt("stock");
						Double OrderPrice = res.getDouble("Price");

						String Orders = "INSERT INTO `orders`(`orderId`, `foodId`, `foodName`, `Quantity`, `price`, `tableName`, `status`) VALUES (?,?,?,?,?,?,?)";

						preparedStmt = (PreparedStatement) conn.prepareStatement(Orders);
						preparedStmt.setString(1, getorderId());
						preparedStmt.setInt(2, OrderFoodId);
						preparedStmt.setString(3, OrderFoodName);
						preparedStmt.setInt(4, orderqty);
						preparedStmt.setDouble(5, (orderqty * OrderPrice));
						preparedStmt.setString(6, getTableId());
						preparedStmt.setString(7, "pending");
						preparedStmt.executeUpdate();

						Connection connset = DB.getConnection();

						String stock = "Update foods set stock = ? where id = ? ";
						PreparedStatement updatestock = (PreparedStatement) connset.prepareStatement(stock);
						updatestock.setInt(1, OrderStock - orderqty);
						updatestock.setString(2, input);
						updatestock.executeUpdate();
						connset.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {

					try {
						conn.close();
					} catch (SQLException e) {

						e.printStackTrace();
					}

				}

			} else {

				System.out.println("*********Please Select Available Food ! ********");

				order();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void ShowOrders() {
		System.out.println("Order ID :- " + getorderId());
		System.out.println("Table Name:- " + getTableId());
		Connection conn = null;

		try {

			conn = DB.getConnection();
			DBTableView.printOrderWithID(conn, "kotsystem.`orders`", getorderId());

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

	public void tables() {

		System.out.println(
				"****** Select The Table***** \n\t1 -> Table:- 01 \t2 -> Table:- 02 \n\t3 -> Table:- 03 \t4 -> Table:- 04 \n\t5 -> Table:- 05 \n\t\t\t0 - Close");

		int tableid = scan.nextInt();

		if (tableid == 1) {
			setTableId("Table-01");
		} else {
			if (tableid == 2) {
				setTableId("Table-02");
			} else {
				if (tableid == 3) {

					setTableId("Table-03");

				} else {
					if (tableid == 4) {
						setTableId("Table-04");

					} else {
						if (tableid == 5) {
							setTableId("Table-05");

						} else {

							System.err.println("|****| Please Select Your Correct Type ! |****|");

							tables();
						}

					}

				}

			}

		}
	}

	public void orderStatus() {
		Connection conn = null;

		try {

			conn = DB.getConnection();
			System.err.println("*************Pending Orders ************");
			DBTableView.printPendingOrderWithID(conn, "kotsystem.`orders`");
			System.err.println("************Cooking Orders *************");
			DBTableView.printActionOrderWithID(conn, "kotsystem.`orders`");
			System.err.println("*************Cooked Orders  *************");
			DBTableView.printDoneOrderWithID(conn, "kotsystem.`orders`");

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

	public void foodItems() {

		Connection conn = null;

		try {

			tables();

			System.out.println("****** Select Foods From this List!******* \n");
			conn = DB.getConnection();
			DBTableView.printTable(conn, "foods");

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

	public void createInvoice() {
		Connection conn = null;
		ResultSet order;
		double TotalAmount;
		PreparedStatement Orderlist;
		PreparedStatement Invoice;

		try {
			conn = DB.getConnection();
			DBTableView.printDoneOrderWithID(conn, "kotsystem.`orders`");

		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("*****Enter OrderiD*****");
		String orderID = scan.next();

		String ordervalue = "SELECT * FROM `orders` WHERE `orderId` = ?";
		String invoice = "INSERT INTO `invoice`(`tableId`, `orderID`,`discount `,`totalAmount`,`date`,`status`) VALUES (?,?,?,?,?.?)";
		try {
			Orderlist = (PreparedStatement) conn.prepareStatement(ordervalue);
			Orderlist.setString(1, orderID);
			order = Orderlist.executeQuery();
			if (!order.next()) {

				System.out.println("******Please Select Available OrderID ******-");

				createInvoice();
			} else {
				TotalAmount = addOrder(order);

				Invoice = (PreparedStatement) conn.prepareStatement(invoice);
				Invoice.setString(1, w.getTableId());
				Invoice.setString(2, orderID);
				Invoice.setDouble(3, TotalAmount);
				Invoice.setDouble(4, TotalAmount);
				Invoice.executeUpdate();

				w.setTableId(null);
				System.out.println("**Invoice Created successfully** ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		w.open();

	}

	private double addOrder(ResultSet order) {
		double totalAmount = 0;
		try {
			while (order.next()) {

				double Price = order.getDouble("price");

				totalAmount += Price;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return totalAmount;
	}

	public void printBill() {
		Connection conn = null;
		String orderID = null;
		String tableName = null;
		String invoiceID = null;
		String discount = null;
		String totalAmount = null;
		try {
			conn = DB.getConnection();
			DBTableView.printconfirmedBill(conn, "kotsystem.`invoice`");

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("*****Enter The invoice ID****** ");
		String invoiceId = scan.next();
		PreparedStatement getinvoice;
		ResultSet invoice;
		String Invoicevalue = "SELECT * FROM `invoice` WHERE `id` = ?";

		try {
			getinvoice = (PreparedStatement) conn.prepareStatement(Invoicevalue);
			getinvoice.setString(1, invoiceId);
			invoice = getinvoice.executeQuery();

			if (!invoice.next()) {

				System.out.println("******Please Select Available Invoice ID******");
				printBill();
			} else {

				orderID = invoice.getString("orderID");
				tableName = invoice.getString("tableId");
				invoiceID = invoice.getString("id");
				discount = invoice.getString("Discount");
				totalAmount = invoice.getString("totalAmount");

				System.out.println();
				System.out.println("Invoice No : " + invoiceID);
				System.out.println("Table No : " + tableName);
				DBTableView.printbillFoods(conn, orderID);
				System.out.println("Total Price : " + totalAmount);
				System.out.println("Discount : " + discount + "%");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
