package Atm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Customer {
@SuppressWarnings({ "resource", "unused" })
public static void main(String[] args) throws Exception{
		Scanner s= new Scanner(System.in);
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/Mad","root","root");
		Statement st= c.createStatement();
		ResultSet rs;
		int total=0;
		rs=st.executeQuery("Select value from atm");
		while(rs.next()) total+=rs.getInt(1);
		System.out.println("1. Check Balance");
		System.out.println("2. Withdraw Money");
		System.out.println("3. Transfer Money");
		int choice=s.nextInt();
		int acc_no=0,pin_no=0,acc_bal=0,amount=0,acno=0,w_amount=0,h_no=0;
		String name="";
		boolean b=true;
		switch(choice) {
		case 1: 
			System.out.println("Enter your account number : ");
			acno=s.nextInt();
			rs=st.executeQuery("Select count(*) from customers where acc_no="+acno);
			while(rs.next()) {
				if(rs.getInt(1)==0)
					b=false;
			}
			if(!b) System.out.println("Account number not available , try again !");
			else {
			rs=st.executeQuery("select * from customers where acc_no="+acno);
			while(rs.next()) {
				acc_no=rs.getInt(1);
				name=rs.getString(2);
				pin_no=rs.getInt(3);
				acc_bal=rs.getInt(4);
			}
			System.out.println("Enter your pin number : ");
			int pin=s.nextInt();
			if(pin==pin_no) {
				System.out.println("Fetching Account details...");
				System.out.println("Account Number : "+acc_no);
				System.out.println("Account Holder Name : "+name);
				System.out.println("Account Balance : Rs."+acc_bal);
			}
			else {
				System.out.println("Account number and Pin Number does not match , try again !");
			}
			}
			break;
		case 2:
			
				System.out.println("Enter your account number : ");
				acno=s.nextInt();
				rs=st.executeQuery("Select count(*) from customers where acc_no="+acno);
				while(rs.next()) {
					if(rs.getInt(1)==0)
						b=false;
				}
				if(!b) System.out.println("Account number not available , try again !");
				else {
				rs=st.executeQuery("select * from customers where acc_no="+acno);
				while(rs.next()) {
					acc_no=rs.getInt(1);
					name=rs.getString(2);
					pin_no=rs.getInt(3);
					acc_bal=rs.getInt(4);
				}
				System.out.println("Enter your pin number : ");
				int pin=s.nextInt();
				if(pin==pin_no) {
					System.out.println("Enter amount to be withdrawn ");
					amount=s.nextInt();
					int temp=0,temp1=amount;
					if((amount>10000 || amount<100) && amount%100==0 )
						System.out.println("Amount to be withdrawn should be in range 100 and 10000");
					else if(amount>acc_bal)
						System.out.println("Account balance is lower than the entered withdrawal amount");
					else if(amount>total)
						System.out.println("ATM does not have enough money to vend !");
					else {
							while(amount>3000) {
								w_amount+=2000;
								st.executeUpdate("update customers set acc_bal=acc_bal-2000 where acc_no="+acc_no);
								st.executeUpdate("update atm set number=number-1 where denomination=2000");
								st.executeUpdate("update atm set value=value-2000 where denomination=2000");
								amount-=2000;
								temp+=2000;
							}
							while(amount>1000) {
								w_amount+=500;
								st.executeUpdate("update customers set acc_bal=acc_bal-500 where acc_no="+acc_no);
								st.executeUpdate("update atm set number=number-1 where denomination=500");
								st.executeUpdate("update atm set value=value-500 where denomination=500");
								amount-=500;
								temp+=500;
							}
							while(amount>0) {
								h_no+=100;
								amount-=100;
								temp+=100;
							}
							w_amount+=h_no;
							st.executeUpdate("update customers set acc_bal=acc_bal-"+h_no+" where acc_no="+acc_no);
							st.executeUpdate("update atm set number=number-"+(h_no/100) +" where denomination=100");
							st.executeUpdate("update atm set value=value-"+h_no +" where denomination=100");
							
					}
					System.out.println("Amount "+temp+" is withdrawn !");
					if(temp!=temp1) {
						System.out.println("Sorry :( "+(temp1-temp)+" is not available in the ATM");
					}
				}
				else {
					System.out.println("Account number and Pin Number does not match , try again !");
				}
			}
			break;
		case 3:
			System.out.println("Enter your account number : ");
			acno=s.nextInt();
			rs=st.executeQuery("Select count(*) from customers where acc_no="+acno);
			while(rs.next()) {
				if(rs.getInt(1)==0)
					b=false;
			}
			if(!b) System.out.println("Account number not available , try again !");
			else {
			rs=st.executeQuery("select * from customers where acc_no="+acno);
			while(rs.next()) {
				acc_no=rs.getInt(1);
				name=rs.getString(2);
				pin_no=rs.getInt(3);
				acc_bal=rs.getInt(4);
			}
			System.out.println("Enter your pin number : ");
			int pin=s.nextInt();
			if(pin==pin_no) {
				System.out.println("Enter the account number you want to transfer your money");
				int aacno=s.nextInt();
				boolean bb=true;
				rs=st.executeQuery("Select count(*) from customers where acc_no="+aacno);
				while(rs.next()) {
					if(rs.getInt(1)==0)
						bb=false;
				}
				if(!b) System.out.println("Account number not available , try again !");
				else {
					System.out.println("Enter the amount to transfer : ");
					int temp3=s.nextInt();
					if(temp3>acc_bal) {
						System.out.println("Your Account Balance is too low for this transaction !");
					}
					else {
						st.executeUpdate("update customers set acc_bal=acc_bal-"+temp3+" where acc_no="+acc_no);
						st.executeUpdate("update customers set acc_bal=acc_bal+"+temp3+" where acc_no="+aacno);
						System.out.println("Amount transfered successfully !");
					}
				}
			}
			else {
				System.out.println("Account No. and Pin No. does not match !");
			}
		}
			break;
		default:System.out.println("Enter valid option !");
		
	}
		s.close();
}
}

