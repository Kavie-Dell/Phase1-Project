package com.kavie.LockMeApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class LockMe_Login {
	private String username;  
	private String password;  
	
	private String usrInputNm; 
	private String usrInputPwd; 
		
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String loginAttempt = new String("Failed"); 
	

	public String getLoginAttempt() {
		return loginAttempt;
	}

	private HashMap<String, String> allUsrCreds = new HashMap<String,String>(); 
	
	@SuppressWarnings("resource")
	public void login() {
		
		System.out.println(" ============================================ ");
		System.out.println(" +                                          + ");
		System.out.println(" +           WELCOME TO LOCKME APP          + ");
		System.out.println(" +                 Login Page               + ");
		System.out.println(" +                                          + ");
		System.out.println(" ============================================");
		
		System.out.println("Enter your Username below :");
		
		Scanner usrInputMenu = new Scanner(System.in);
		usrInputNm = usrInputMenu.nextLine();
		
		if(usrInputNm.equals("")) {
			System.out.println("Empty entry detected, Please input your username again.");
			reLoginForm(usrInputMenu);
			return;
		}
		
		storeUsrsCred();
		
		if(ifUsrNmNotExist()==true) {
			System.out.println("Invalid Username! Please input the correct username again.");
			reLoginForm(usrInputMenu);
			return;
		}
		
		System.out.println("Enter your password below: ");
		usrInputPwd = usrInputMenu.nextLine();
		
		if(usrInputPwd.equals("")) {
			System.out.println("Empty entry, Please input your password again.");
			reLoginForm(usrInputMenu);
			return;
		}
		
		if(chkPwd()==true) {
			System.out.println("Welcome " + usrInputNm + ", your login is successful.");
			loginAttempt = "Success";
			username = usrInputNm;
			setPassword(usrInputPwd);
			return;
		}
		
		else {
			System.out.println("Incorrect password, Please input your password again.");
			reLoginForm(usrInputMenu);
			return;
		}
		
		
		
	}
	
		private boolean chkPwd() {	
			
			if(allUsrCreds.get(usrInputNm).equals(usrInputPwd))
				return true;
			return false;
		}
		
		private boolean ifUsrNmNotExist() {
			if(allUsrCreds.containsKey(usrInputNm))
				return false;
			return true;
		}
	
	private void reLoginForm(Scanner input) {
		System.out.println("1: Try again ");
		System.out.println("2: Exit ");
		String inputvalue=input.nextLine();
		switch(inputvalue) {
		case "1":
			login();
			break;
		case "2":
			System.out.println("You have logout successfully.");
			return;
		default:
			System.out.println("Incorrect entry, Please enter the correct input.");
			reLoginForm(input);
			break;
		}
	}
	 
		private void storeUsrsCred() {
			File dbFile = new File("database.txt");
			Scanner s;
			try {
				if(dbFile.exists()==false)
					dbFile.createNewFile();
				s = new Scanner(dbFile);
				int ln=0;
				String tmpUsr= new String("");
				while(s.hasNextLine()) {
					
					if(ln%2==0)
						tmpUsr=s.nextLine();
					else if(ln%2!=0)
						allUsrCreds.put(tmpUsr, s.nextLine());
					
					ln++;	
				}
				s.close();
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
}
