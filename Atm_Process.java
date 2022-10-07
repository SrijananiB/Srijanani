package Atm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
public class Atm_Process {
	public static void main(String[] args) throws Exception{
		Scanner s=new Scanner(System.in);
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/Mad","root","root");
		Statement st= c.createStatement();
		ResultSet rs;
		System.out.println("1. Load cash into ATM");
		System.out.println("2. Show customer details");
		System.out.println("3. Check ATM Balance");
		int choice=s.nextInt();
		switch(choice) {
		case 1:System.out.println("Enter the Denominations");
		  System.out.println("2000 = ");
		  int d1=s.nextInt();
		  System.out.println("500 = ");
		  int d2=s.nextInt();
		  System.out.println("100 = ");
		  int d3=s.nextInt();
		  rs=st.executeQuery("Select count(*) from atm");
		  int x=0;
		  while(rs.next()) {
		  x=rs.getInt(1);
		  }
		  if(x==0) {
		  st.executeUpdate("Insert into atm values(2000,"+d1+","+(2000*d1)+")");
		  st.executeUpdate("Insert into atm values(500,"+d2+","+(500*d2)+")");
		  st.executeUpdate("Insert into atm values(100,"+d3+","+(100*d3)+")");
		  }
		  else {
		  st.executeUpdate("update atm set number=number+"+d1+" where denomination=2000");
		  st.executeUpdate("update atm set number=number+"+d2+" where denomination=500");
		  st.executeUpdate("update atm set number=number+"+d3+" where denomination=100");
		  st.executeUpdate("update atm set value=value+"+(d1*2000)+" where denomination=2000");
		  st.executeUpdate("update atm set value=value+"+(d2*500)+" where denomination=500");
		  st.executeUpdate("update atm set value=value+"+(d3*100)+" where denomination=100");

		  }
		  break;
		case 2: rs=st.executeQuery("Select * from customers");
		System.out.println("Acc_no Acc_Holder_Name Pin_No Acc_Bal");
		System.out.println();
		while(rs.next()) {
		System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "
		+ "  "+rs.getInt(4));
		}
		break;
		case 3:
		int total=0;
		rs=st.executeQuery("Select * from atm");
		System.out.println("Denomination Number Value");
		System.out.println();
		while(rs.next()) {
		//System.out.println(rs.getInt(1)+" "+rs.getInt(2)+" "+rs.getInt(3));
		System.out.printf("%04d%03d%d\n",rs.getInt(1),rs.getInt(2),rs.getInt(3));
		total+=rs.getInt(3);
		}
		System.out.println();
		System.out.println("Total amount in ATM = "+total);
		break;
		default:System.out.println("Enter valid option !");
		}
		s.close();
		}

		}
}
