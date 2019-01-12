package typeOfUsers;

import java.util.Scanner;

public class Manager {
	static Scanner scan = new Scanner(System.in);

	public void start() {
		ManagerFunction mf = new ManagerFunction();
		System.out.println();
		System.out.println(
				"***** Hi Sir ? ***** \n\t1 -> Invoice Detials \n\t2 -> Meanage Customer Bill Statement \n\t0 -> main manu");
		int mType = scan.nextInt();

		if (mType == 1) {
			mf.Invoices();

		} else {
			if (mType == 2) {
				mf.PendingInvoice();
				mf.Bill();

			} else {
				System.err.println("Please Select Correct Option !");

				start();

			}
		}

	}

}
