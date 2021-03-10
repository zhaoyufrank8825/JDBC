//  Zhao Yu, 1001450700, yxz0700



import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.* ;
import oracle.jdbc.driver.OracleDriver;


public class Demo1 {
 
    public static void main(String[] args) {
         // TODO Auto-generated method stub
  
	  try {
		  
		  // Connect to my UTA Omega Oracle database, putting my account and password in the parameter
		  // Register Oracle Driver and Create Statement for using SQL in future
		  DriverManager.registerDriver(new OracleDriver());    
		  Connection cn = DriverManager.getConnection("jdbc:oracle:thin:@//acaddbprod-1.uta.edu:1523/pcse1p.data.uta.edu","yxz0700","Yingying8825");  
		  Statement st = cn.createStatement();
		  System.out.println("Connection Set Up");
		  
		  // Load the records from my computer to the UTA database, using for loop to handle the path
		  // Records are in the same file, just need to change the file name
		  String[] files = {"EMPLOYEE", "DEPARTMENT", "DEPT_LOCATIONS", "PROJECT", "WORKS_ON", "DEPENDENT"};
		  for(String filename: files) {
			  String path = "C:\\Users\\zhaoy\\OneDrive\\Desktop\\uta\\2021 spring\\5330 Database\\hw1\\DataFiles-1\\"+filename+".txt";
			  BufferedReader br = new BufferedReader(new FileReader(path));
			  String line = null;
			  while ((line = br.readLine()) != null)
			  { 
				  
				  // Replace the filename and line every time, the insert SQL generate easily and correctly.
				  // And then run the SQL to save the records to the database
			      String sql ="INSERT INTO "+filename+" VALUES (" + line + ")";
				  st.executeQuery(sql);
			  }
			  br.close();
			  System.out.println("Insert "+filename+" finished!");
		  }
	      
		  // To avoid circular reference, insert the records first, and add foreign key in the end.
		  // By doing this, we avoid to add foreign key in the table, and then set foreign key to null, renew the foreign key in the end.
		  // Make the create table and load the records more easy.
	      String sql = "ALTER TABLE EMPLOYEE ADD FOREIGN KEY(Dno) REFERENCES DEPARTMENT(Dnumber) ON DELETE SET NULL";
	      st.executeQuery(sql);
	      System.out.print("ALTER TABLE EMPLOYEE SUCCESSFULLY");
	      st.close();
	      cn.close();
	   
	  } catch (Exception e) {
		   System.out.println("Some problem in connection");
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	  }
   }
}

