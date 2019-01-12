package typeOfUsers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import dBConnection.DB;
import main.DBTableView;

public class ManagerFunction {
	static Scanner scan = new Scanner(System.in);
	private double discount;
	private double billAmount;

	public double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}

	public double getDiscount() {
		return discount;
	}

	Manager m = new Manager();

	public void PendingInvoice() {
		Connection conn = null;
		try {
			conn = DB.getConnection();
			DBTableView.printPendingOrderWithID(conn, "kotsystem.`invoice`");

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void Invoices() {
		Connection conn = null;
		try {
			conn = DB.getConnection();
			DBTableView.printTable(conn, "kotsystem.`invoice`");

		} catch (Exception e) {
			// TODO: handle exception
		}
		m.start();

	}

	public void setDiscount() {
		System.out.println("Enter The Discount ? (0-100 %)");
		double discount = scan.nextDouble();
		if (discount < 100 || discount > 0) {

			this.discount = discount;
		} else {
			System.out.println("***** Discount Can be 0 to 100 percentage  *****");
			setDiscount();
		}
	}

	public void Bill() {

		System.out.println("**Enter The Invoice ID **");
		String InvoiceId = scan.next();

		Connection conn = null;
		PreparedStatement getInvoice;
		PreparedStatement UpdateInvoice;
		ResultSet invoice;

		try {
			conn = DB.getConnection();
			String getSQL = "SELECT * FROM `invoice` WHERE `id` = ?";
			getInvoice = (PreparedStatement) conn.prepareStatement(getSQL);
			getInvoice.setString(1, InvoiceId);
			invoice = getInvoice.executeQuery();
			if (!invoice.next()) {

				System.out.println("*****Please Select Available invoice ID ******");
			} else {
				String updateInvoice = "UPDATE `invoice` SET `discount`=? ,`totalAmount`=?,`status`=? WHERE `id` = ?";
				setDiscount();

				double amount = invoice.getDouble("totalAmount");
				double billAmount;
				if (getDiscount() > 0) {
					billAmount = (amount) - (amount * getDiscount() / 100);
				} else {
					billAmount = amount;
				}
				setBillAmount(billAmount);
				System.out.println(getDiscount());
				UpdateInvoice = (PreparedStatement) conn.prepareStatement(updateInvoice);
				UpdateInvoice.setDouble(1, getDiscount());
				UpdateInvoice.setDouble(2, billAmount);
				UpdateInvoice.setString(3, "confirmed");
				UpdateInvoice.setString(4, InvoiceId);
				UpdateInvoice.executeUpdate();

				System.out.println("Bill Confirmed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		m.start();
	}

}
