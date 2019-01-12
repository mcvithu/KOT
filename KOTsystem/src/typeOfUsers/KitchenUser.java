package typeOfUsers;

import java.util.Scanner;

import main.Main;

public class KitchenUser {
	static Scanner scan = new Scanner(System.in);
	private int type = 1;

	public void start() {
		KitchenUserFunction kuf = new KitchenUserFunction();
		// TODO Auto-generated method stubSystem.out.println();
		kuf.ShowOrders();

		System.out.println("\n\t1 -> Take Order \n\t0 ->s main manu");
		int take = scan.nextInt();

		if (type == take) {
			kuf.takeTheOrder();

		} else {
			Main.userType();
		}

	}

}
