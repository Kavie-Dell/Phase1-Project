package com.kavie.LockMeApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;


public class LockMe_Operations {
	private String username;
	private HashMap<String, String[]> userDatabase = new HashMap <String,String[]>();
	
	private HashMap<String, String> allCreds = new HashMap<String,String>(); 
		
	public  void fileOperation(LockMe_Login lgMeth) {
		
		String usrLoginStats=lgMeth.getLoginAttempt();
		
		if(usrLoginStats.equals("Failed"))
			return;
		
		username=lgMeth.getUsername();
	
		lockedMeMenu();
		
	}
	
	@SuppressWarnings("resource")
	private void process() {
		
		Scanner usrInput=new Scanner(System.in);
		String swtchInput = usrInput.nextLine();
		
		switch(swtchInput) {
		case "1":
			addNwAcc(usrInput);
			break;
		case "2":
			delAccCred(usrInput);
			break;
		case "3":
			modAccPwd(usrInput); 
			break;
		case "4":
			dispAllPwd(); 
			break;
		case "6":
			delYrAcc(usrInput);
			break;
		case "5":
			System.out.println("You have logout successfully.");
			return;
		default:
			System.out.println("Incorrect entry, Please enter the corret input as stated above.");
		}
		
	}
	
		private void getAllUsrCreds() {
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
						allCreds.put(tmpUsr, s.nextLine());
					
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
		
		private void addNwAcc(Scanner nwUsrCrdInput) {
			
			System.out.println("Please enter the website's name: ");
			String website=nwUsrCrdInput.nextLine();
			
			
			if(website.equals("")) {
				System.out.println("Empty entry, Please input the website's detail again : ");
				reAddNwAppsWeb(nwUsrCrdInput);
				return;
			}
			
			try {
				getUsrDb();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			if(ifWebAppsExists(website)==true){
				System.out.println("Website's name already exists");
				reAddNwAppsWeb(nwUsrCrdInput);
				return;
			}
			
			
			System.out.println("Please enter the website's username: ");
			String username1=nwUsrCrdInput.nextLine();
			
			
			if(username1.equals("")) {
				System.out.println("Empty entry, Please input your username again: ");
				reAddNwAppsWeb(nwUsrCrdInput);
				return;
			}
			
			System.out.println("Please enter the website's password: ");
			String password1=nwUsrCrdInput.nextLine();
			
			if(password1.equals("")) {
				System.out.println("Empty entry, Please input your password again : ");
				reAddNwAppsWeb(nwUsrCrdInput);
				return;
			}
			
			System.out.println("Please confirm the website's password again : ");
			String password2=nwUsrCrdInput.nextLine();
			
			if(password1.equals(password2)) {
				addAppWebDtl(website,username1,password2);
				System.out.println("Your credentials are stored successfully");
				reAddNwAppsWeb(nwUsrCrdInput); 
			}
			else {
				System.out.println("Password does not match. Please enter password again.");
				reAddNwAppsWeb(nwUsrCrdInput);
			}
			
		}
		
		private void getUsrDb() throws FileNotFoundException {
			File fileObj = new File(username+".txt");
			try {
				
				if(fileObj.exists()==false)
					fileObj.createNewFile();
				Scanner scannerReader = new Scanner(fileObj);
				
				while(scannerReader.hasNextLine()) {
					String tempKey=scannerReader.nextLine();
					String usernameAndPwd[]= new String[2];
					usernameAndPwd[0]=scannerReader.nextLine();
					usernameAndPwd[1]=scannerReader.nextLine();
					userDatabase.put(tempKey, usernameAndPwd);
				}
				scannerReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		
		}
		
		private void addAppWebDtl(String webSite, String tempUsername, String password) {
			File fileObj = new File(username+".txt");
			
			FileWriter fileWriter = null;
			
			try {
					if(fileObj.exists()) {
					fileWriter = new FileWriter(fileObj,true);
					@SuppressWarnings("resource")
					Scanner scannerReader = new Scanner(fileObj);
					if(scannerReader.hasNext())
						fileWriter.append("\n"+webSite);
					else
						fileWriter.append(webSite);
					fileWriter.append("\n"+tempUsername);
					fileWriter.append("\n"+password);
					
				}else {
					throw new FileNotFoundException("Could not find file: "+fileObj.getName());
				}
				
			}
			catch (IOException e) {
				System.out.println("Please try again.");
			} 
			try {
					fileWriter.close();
				} 
			catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		private boolean ifWebAppsExists(String website) {
			if(userDatabase.containsKey(website))
				return true;
			return false;
		}
		
		private void reAddNwAppsWeb(Scanner input){
			System.out.println("============================================");
			System.out.println("1: Add website's credentials");
			System.out.println("2: Main Menu");
			System.out.println("3: Logout");
			System.out.println("============================================");
			
			String inputvalue=input.nextLine();
			switch(inputvalue) {
			case "1":
				addNwAcc(input);
				break;
			case "2":
				lockedMeMenu();
				break;
			case "3":
				System.out.println("You have logout successfully.");
				return;
			default:
				System.out.println("Incorrect entry, Please enter the corret input");
				reAddNwAppsWeb(input);
				break;
			}
		}
		
		private void delAccCred(Scanner input) {
			
			
			System.out.println("Please enter the website's password: ");
			String website=input.nextLine();
			
			
			if(website.equals("")) {
				System.out.println("Empty entry, Please input the website's detail again : ");
				reDelAppWebs(input);
				return;
			}
			
			try {
				getUsrDb();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			if(ifWebAppsExists(website)==false){
				System.out.println("Could not find website");
				reDelAppWebs(input);
				return;
			}
			rmAppsWebDB(website);
			System.out.println("Website's information has been removed.");
			reDelAppWebs(input);
			
		}
		
		private void writeUsrtoDb() {
			File fileObj = new File(username+".txt");
			
			FileWriter fileWriter = null;
			
			try {
				if(fileObj.exists()) {
						
					fileWriter = new FileWriter(fileObj);
					
					Iterator<Entry<String, String[]>> iterator = userDatabase.entrySet().iterator();
					
					int iterationCounter=0;
					
					while(iterator.hasNext()) {
						@SuppressWarnings("rawtypes")
						Map.Entry mapElemnt = (Map.Entry)iterator.next();
						if(iterationCounter!=0)	
							fileWriter.append("\n"+(String) mapElemnt.getKey());
						else	
							fileWriter.append((String) mapElemnt.getKey());
						String tempArray[]=(String[]) mapElemnt.getValue();
						fileWriter.append("\n"+tempArray[0]);
						fileWriter.append("\n"+tempArray[1]);
						iterationCounter++;
					}
					
				}else {
					throw new FileNotFoundException("Could not find file:  "+fileObj.getName());
				}
				
			}
			catch (IOException e) {
				System.out.println("Please try again");
			} 
			try {
					fileWriter.close();
				} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void rmAppsWebDB(String website) {
			userDatabase.remove(website);
			writeUsrtoDb();
		}
		
		private void reDelAppWebs(Scanner input){
			System.out.println("==========================================================");
			System.out.println("1: Delete website's credentials");
			System.out.println("2: Main Menu");
			System.out.println("3: Logout");
			System.out.println("==========================================================");
			
			String inputvalue=input.nextLine();
			switch(inputvalue) {
			case "1":
				delAccCred(input);
				break;
			case "2":
				lockedMeMenu();
				break;
			case "3":
				System.out.println("You have logout successfully.");
				return;
			default:
				System.out.println("Incorrect entry, Please enter the correct input.");
				reDelAppWebs(input);
				break;
			}
		}
		
		private void modAccPwd(Scanner input) {
			System.out.println("Please enter the website's name below : ");
			String website=input.nextLine();
			
			
			if(website.equals("")) {
				System.out.println("Empty entry, Please input the website's detail again.");
				tryAgainChangeWebsitePassword(input);
				return;
			}
			
			try {
				getUsrDb();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			if(ifWebAppsExists(website)==false){
				System.out.println("Could not find website");
				tryAgainChangeWebsitePassword(input);
				return;
			}
			
			System.out.println("Please enter the website's password: ");
			String password1=input.nextLine();
			
			if(password1.equals("")) {
				System.out.println("Empty entry, Please input your password again.");
				tryAgainChangeWebsitePassword(input);
				return;
			}
			
			System.out.println("Please re-enter the website's password: ");
			String password2=input.nextLine();
			
			if(password1.equals(password2)) {
				mdAppWebPwdDb(website,password2);
				System.out.println("Password changed successfully ");
				tryAgainChangeWebsitePassword(input);
			}
			else {
				System.out.println("Password does not match, Please input your password again.");
				tryAgainChangeWebsitePassword(input);
			}
		}
		
		private void mdAppWebPwdDb(String website ,String password) {
			String tmpValArr[]=userDatabase.get(website);
			tmpValArr[1]=password;
			userDatabase.replace(website, tmpValArr);
			writeUsrtoDb();
		}
		
		private void tryAgainChangeWebsitePassword(Scanner input){
			System.out.println("=======================================================");
			System.out.println("1: Change Password to website's credentials ");
			System.out.println("2: Main Menu");
			System.out.println("3: Logout");
			System.out.println("=======================================================");
			
			String inputvalue=input.nextLine();
			switch(inputvalue) {
			case "1":
				modAccPwd(input);
				break;
			case "2":
				lockedMeMenu();
				break;
			case "3":
				System.out.println("You have logout successfully.");
				return;
			default:
				System.out.println("Incorrect entry, Please enter the corret input.");
				tryAgainChangeWebsitePassword(input);
				break;
			}
		}
		
		private void dispAllPwd() {
			
			try {
				getUsrDb();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			if(userDatabase.isEmpty())
				System.out.println("No data available. Please add your credentials");
			
			else {
				System.out.println("********************************************************************");
				dispAppsWebsCredDb();
				System.out.println("********************************************************************");
			}
			optToDispAppWebsDtl();
			
		}
		
		private void dispAppsWebsCredDb() {
			
			Iterator<Entry<String, String[]>> iterator = userDatabase.entrySet().iterator();
			
			while(iterator.hasNext()) {
				System.out.println("********************************************************************");
				@SuppressWarnings("rawtypes")
				Map.Entry mapElemnt = (Map.Entry)iterator.next();
				System.out.println("Website : "+mapElemnt.getKey());
				String tempArray[]=(String[]) mapElemnt.getValue();
				System.out.println("Username: "+tempArray[0]);
				System.out.println("Password: "+tempArray[1]);
				System.out.println("********************************************************************");
			}
			
		}
		
		private void optToDispAppWebsDtl(){
			System.out.println("1: Display all your stored credentials");
			System.out.println("2: Main Menu");
			System.out.println("3: Logout");
			@SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);
			String inputvalue=input.nextLine();
			switch(inputvalue) {
			case "1":
				dispAllPwd();
				break;
			case "2":
				lockedMeMenu();
				break;
			case "3":
				System.out.println("You have logout successfully.");
				return;
			default:
				System.out.println("Incorrect entry, Please enter the correct input.");
				optToDispAppWebsDtl();
				break;
			}
		}
		
		private void delYrAcc(Scanner input) {
			System.out.println("1: Confirm Account Deletion");
			System.out.println("2: Main Menu");
			System.out.println("3: Logout");
			
			String userInput = input.nextLine();
			switch(userInput) {
			case "1":
				delUsrFile(); 
				delUsrInfoDb();
				System.out.println("Your account has been successfully deleted from our database");
				break;
			case "2":
				lockedMeMenu();
				break;
			case "3":
				System.out.println("You have logout successfully.");
				break;
			default: 
				System.out.println("Incorrect entry, Please enter the correct input");
				delYrAcc(input);
			}
		}
		
		private void delUsrInfoDb() {
			getAllUsrCreds();
			
			if(allCreds.containsKey(username))
				allCreds.remove(username);
			setAllUsrCreds(); 
		}
		

		private void setAllUsrCreds() {
			File fileObj = new File("database.txt");
			
			FileWriter fileWriter = null;
			
			try {
				if(fileObj.exists()) {
						
					fileWriter = new FileWriter(fileObj);
					
					Iterator<Entry<String, String>> iterator = allCreds.entrySet().iterator();
					
					int iterationCounter=0;
					
					while(iterator.hasNext()) {
						@SuppressWarnings("rawtypes")
						Map.Entry mapElemnt = (Map.Entry)iterator.next();
						if(iterationCounter!=0)	
							fileWriter.append("\n"+(String) mapElemnt.getKey());
						else	
							fileWriter.append((String) mapElemnt.getKey());
						
						fileWriter.append("\n"+(String) mapElemnt.getValue());
						
						iterationCounter++;
					}
					
				}else {
					throw new FileNotFoundException("Could not find file: "+fileObj.getName());
				}
				
			}
			catch (IOException e) {
				System.out.println("Please try again");
			} 
			try {
					fileWriter.close();
				} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		private void delUsrFile() {
			File usrFile = new File(username+".txt");
			if(usrFile.exists()==true)
				usrFile.delete();
		}
		

		private void lockedMeMenu() {
			System.out.println(" ===============================================");
			System.out.println(" + LockedMe Menu :                              +");
			System.out.println(" + 1: Add account credentials                   +");
			System.out.println(" + 2: Remove account credentials                +");
			System.out.println(" + 3: Change password                           +");
			System.out.println(" + 4: Display all user credentials              +");
			System.out.println(" + 5: Exit                                      +");
			System.out.println(" + 6: Permanently delete your account           +");
			System.out.println(" ===============================================");
			process();
		}

}
