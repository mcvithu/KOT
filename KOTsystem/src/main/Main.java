package main;

import java.util.Scanner;
import typeOfUsers.*;

public class Main {
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		Welcome();
		userType();
	}

	public static void Welcome() {

		System.out.println(
				"|-----------------------------------||                    ||--------------------------------------------|");
		System.out.println(
				"|-----------------------------------|| WelCome to Castzro ||--------------------------------------------|");
		System.out.println(
				"|-----------------------------------||                    ||--------------------------------------------|");

	}

	public static void userType() {

		System.out.println();

		System.out.println("*** Hi Sir..!  Type Your User Number ***");
		System.out.println("\r1 -> Waiter \n\r2 -> KitchenStaff \n\r3 -> ManagementStaff \n\r0 -> Close");
		System.out.println("\n\r Press A Key (1-3 And 0)");
		int input = scan.nextInt();

		if (input == 1) {
			Waiter waiter = new Waiter();
			waiter.open();
		} else {
			if (input == 2) {
				KitchenUser kUser = new KitchenUser();
				kUser.start();
			} else {
				if (input == 3) {
					Manager m = new Manager();
					m.start();

				} else {
					if (input == 0) {
						Close();

					} else {
						System.err.println("----------------------------------------------");
						System.err.println("|****| Please Select Your Correct Type ! |****|");
						System.err.println("----------------------------------------------");

						userType();

					}

				}

			}

		}
	}
	public static void Close() {

		System.out.println(
				"|-----------------------------------||                        ||--------------------------------------------|");
		System.out.println(
				"|-----------------------------------|| Thank You For Joing Us ||--------------------------------------------|");
		System.out.println(
				"|-----------------------------------||                        ||--------------------------------------------|");

	}

}
