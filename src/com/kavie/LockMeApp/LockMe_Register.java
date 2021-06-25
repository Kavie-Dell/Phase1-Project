package com.kavie.LockMeApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class LockMe_Register {
	private HashMap<String, String> allUsrCreds = new HashMap<String,String>();
	

		private void addNwUser(String addNwUsr,String pwdNwUsr) {
			
			File dbFile = new File("database.txt");
			
			FileWriter fileWriter = null;
			
			try {
				if(dbFile.exists()) {
					fileWriter = new FileWriter(dbFile,true);
					@SuppressWarnings("resource")
					Scanner s = new Scanner(dbFile);
					if(s.hasNext())
						fileWriter.append("\n"+addNwUsr);
					else
						fileWriter.append(addNwUsr);
					fileWriter.append("\n"+pwdNwUsr);
					System.out.println("Successfully registered");
					
				}else {
					throw new FileNotFoundException("Could not find file: "+dbFile.getName());
				}
				
			}
			catch (IOException e) {
				System.out.println("Please try again later.");
			} 
			try {
					fileWriter.close();
				} 
			catch (IOException e) {
					e.printStackTrace();
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
			String usrTmp= new String("");
			while(s.hasNextLine()) {
				
				if(ln%2==0)
					usrTmp=s.nextLine();
				else if(ln%2!=0)
					allUsrCreds.put(usrTmp, s.nextLine());
				
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


	public void retUsrCred() {
		Scanner input = new Scanner(System.in);
		System.out.println(" ============================================ ");
		System.out.println(" +                                          + ");
		System.out.println(" +         Welcome to LockedMe App          + ");
		System.out.println(" +            Registration Page             + ");
		System.out.println(" +                                          + ");
		System.out.println(" =============================================");
		
		
		System.out.println("Please enter your Username below : ");
		String userName= input.nextLine();
		

		if(userName.equals("")) {
			System.out.println("Empty entry, Please input your username again.");
			reRegisterForm(input);
			return;
		}
		
		storeUsrsCred();
		
		if(usrNmExist(userName)==true) {
			System.out.println("Username already exists, Please try a different username.");
			reRegisterForm(input);
			return;
		}
		
		System.out.println("Please enter a password : ");
		System.out.println(" ============================================ ");
		System.out.println(" +       !!!! Password Criteria !!!!         +");
		System.out.println(" +       atleast 6 characters long           +");
		System.out.println(" +                    AND                    +");
		System.out.println(" + minimum 1 uppercase character e.g (A,B,C) +");
		System.out.println(" +                    AND                    +");
		System.out.println(" +     minimum 1 number value e.g (1,2,3)    +");
		System.out.println(" ============================================ ");
		
		String password= input.nextLine();
		

		if(password.equals("")) {
			System.out.println("Empty entry, Please input your password again.");
			reRegisterForm(input);
			return;
		}
		

		if(chkPwd(password)==false) {
			reRegisterForm(input);
			return;
		}
		System.out.println("Re-enter your password for confirmation");
		String password2=input.nextLine();
		

		if(password.equals(password2))
			addNwUser(userName,password);
		else {
			System.out.println("Password you've entered doesn't match, Please input your password again.");
			reRegisterForm(input);
		}
	}
	

	private void reRegisterForm(Scanner input) {
		System.out.println("===============");
		System.out.println("1: Try again");
		System.out.println("2: Exit");
		System.out.println("===============");
		String inputvalue=input.nextLine();
		switch(inputvalue) {
		case "1":
			retUsrCred();
			break;
		case "2":
			System.out.println("You have logout successfully.");
			return;
		default:
			System.out.println("Incorrect entry, Please enter the correct input as stated above.");
			reRegisterForm(input);
			break;
		}
	}
	

	private boolean chkPwd(String pwdCriteria) {
		if(pwdCriteria.length() < 6) {
			System.out.println("Your password should be minimum 6 characters long");
			return false;
		}
		if(Pattern.matches(".*[0-9].*", pwdCriteria) == false) {
			System.out.println("Your password should have minimum 1 number");
			return false;
		}
		if(Pattern.matches(".*[A-Z].*", pwdCriteria) == false) {
			System.out.println("Your password should have minimum 1 uppercase character.");
			return false;
		}
		return true;
	}
	

	private boolean usrNmExist(String user) {
		if(allUsrCreds.containsKey(user))
			return true;
		return false;
	}
	

}
