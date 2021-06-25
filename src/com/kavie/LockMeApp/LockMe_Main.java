package com.kavie.LockMeApp;

import java.util.Scanner;

public class LockMe_Main {


	public static void main(String[] args) {
		
		lockedmemainscreen();
		loginregisterform();
		
	}
	
	public static void loginregisterform() {
		
		System.out.println(" ========================== ");
		System.out.println("1: Login");
		System.out.println("2: Register"); 
		System.out.println("3: Exit");
		System.out.println(" ========================== ");
		
		Scanner userinput = new Scanner(System.in);
		
		String choice = userinput.nextLine();
		
		switch(choice) {
		
		case "1":
			LockMe_Login lgMeth= new LockMe_Login();
			lgMeth.login();
			
			LockMe_Operations fileMeth = new LockMe_Operations();
			fileMeth.fileOperation(lgMeth);
			break;
			
		case "2":
			LockMe_Register rgstrMeth = new LockMe_Register();
			rgstrMeth.retUsrCred();
			postRgstr();
			break;
		case "3":
			System.out.println("You have logout successfully.");
			break;
		default:
			System.out.println("Invalid input. Please retry again.");
			retryloginregisterform(userinput);
		}
		
		
	}
	
    public static void postRgstr() {
		
    	System.out.println(" ====================== ");
    	System.out.println("1: Login");
		System.out.println("2: Exit");
		System.out.println(" ====================== ");
		
		Scanner input = new Scanner(System.in);
		
		String inputNumber = input.nextLine();
		
		switch(inputNumber) {
		
		case "1":
			LockMe_Login lgMeth= new LockMe_Login();
			lgMeth.login();
			
			LockMe_Operations fileMeth = new LockMe_Operations();
			fileMeth.fileOperation(lgMeth);
			break;
		case "2":
			System.out.println("You have logout successfully.");
			break;
		default:
			System.out.println("Invalid input. Please retry again.");
			retryloginregisterform(input);
		}
		
		
	}
	
	public static void retryloginregisterform(Scanner input) {
		
		System.out.println("1: Try again.");
		System.out.println("2: Exit.");
		
		String retryinput= input.nextLine();
			
		switch(retryinput) {
		case "1":
			loginregisterform();
			break;
		case "2":
			System.out.println("You have logout successfully.");
			return;
		default:
			System.out.println("Invalid input. Please retry again.");
		}
	}
	
		public static void lockedmemainscreen() {
			
			System.out.println(" ============================================ ");
			System.out.println(" +                                          + ");
			System.out.println(" +         Welcome to LockedMe App          + ");
			System.out.println(" +            Digital Locker                + ");
			System.out.println(" +          Built by Kavie Chelvam          + ");
			System.out.println(" +                                          + ");
			System.out.println(" ============================================ ");
		}

}
