package p3proiectir;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import org.apache.ibatis.jdbc.ScriptRunner;

import com.mysql.cj.jdbc.MysqlDataSource;
/**
 * Contains most of the methods related to making changes in the DB
 */
public class Actions {
	/**
	 * Runs the SQL script to create tables if they don't already exist.
	 *
	 * @return 0 If all went well, the error as a string if something went wrong
	 */
	public static String SQLOnLaunch(){
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("javaatm");
		dataSource.setPassword("new_password");
		dataSource.setURL("jdbc:mysql://localhost:3306/atm_db");
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		}
		try {
		    ScriptRunner runner = new ScriptRunner(conn);
		    runner.runScript(new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("atmdb.sql"))));
		} catch (Exception e) {
			//e.printStackTrace();
		    return e.toString();
		}
		return "0";
	}
	/**
	 * Connects to the database and transfers money between users
	 * <p>
	 * Connects to the database, checks if the user id exists.
	 * If it does, the data is modified and the transfer takes place.
	 *
	 * @param  user_id id of the user the money is sent to.
	 * @param  money amount of money sent
	 * @param  sender_id id of the sender
	 * @return 0 If all went well, 3 if user doesn't exist
	 */
	public static int send_money(int user_id,double money,int sender_id){
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("javaatm");
		dataSource.setPassword("new_password");
		dataSource.setURL("jdbc:mysql://localhost:3306/atm_db");
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement stmnt=null;
		try {
			String query ="SELECT * FROM ACCOUNT WHERE IDACCOUNT=?";
			stmnt = conn.prepareStatement(query);
			stmnt.setInt(1, user_id);
			ResultSet rs=stmnt.executeQuery();	
			if(rs.next()==false) {
				return 3;
			}
			query ="UPDATE ACCOUNT SET balance=balance-? WHERE IDACCOUNT=?";
			stmnt = conn.prepareStatement(query);
			stmnt.setDouble(1, money);
			stmnt.setInt(2,sender_id);
			stmnt.executeUpdate();
			
			query ="UPDATE ACCOUNT SET balance=balance+? WHERE IDACCOUNT=?";
			stmnt = conn.prepareStatement(query);
			stmnt.setDouble(1, money);
			stmnt.setInt(2,user_id);
			stmnt.executeUpdate();
			System.out.println("Money sent successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmnt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * Gets card info from the database
	 * <p>
	 * This is called when the user tries to log in, it checks the pin
	 * and the numbers and if they match with the data, the
	 * program takes the information from the database.
	 * The rest of the information is then extracted based on the card
	 * type and the card type specific class constructor is called.
	 *
	 * @param  cardNum card numbers inserted and used in the select
	 * @param  pinin pin inserted and used in the select
	 * @return 0 if everything goes well
	 */
	public static Object get_db_info(String cardNum, String pinin) throws SQLException{ // WILL GET CARD INFO
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("javaatm");
		dataSource.setPassword("new_password");
		dataSource.setURL("jdbc:mysql://localhost:3306/atm_db");
		Connection conn = null;
		conn = dataSource.getConnection();
		PreparedStatement stmt = null;
		String query="SELECT * FROM CARDMAIN WHERE NUMBERS=? AND PIN=?";
		stmt=conn.prepareStatement(query);
		stmt.setString(1, cardNum);
		stmt.setString(2,pinin);
		ResultSet rs = null;
		String hName,tip;
		int pin,id,hId;
		double balance;
		rs = stmt.executeQuery();
		if(rs.next()==false){
			return null;}
		hId=rs.getInt("IDHOLDER");
		pin=rs.getInt("PIN");
		id=rs.getInt("IDCARD");
		tip=rs.getString("TIP");
		
		query="SELECT * FROM ACCOUNT WHERE IDACCOUNT=?";
		stmt=conn.prepareStatement(query);
		stmt.setInt(1, hId);
		rs= stmt.executeQuery();
		rs.next();
		hName=rs.getString("NAME");
		balance=rs.getDouble("BALANCE");
		//dobanda=rs.getDouble("DOBANDA");
		
		
		if(tip.contains("business"))
		{
			query="SELECT * FROM CARDBUSINESS WHERE IDCARDBUSINESS=?";
			stmt=conn.prepareStatement(query);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			rs.next();
			int idcompanie=rs.getInt("COMPANYID");
			double max_spend,current_spend;
			max_spend=rs.getDouble("MAXSPENDLIMIT");
			current_spend=rs.getDouble("CURRENTSPENDLIMIT");
			
			// MIGHT UPDATE SPENDLIMIT ON RETRIEVAL
			
			query="SELECT * FROM COMPANY WHERE IDCOMPANY=?";
			stmt=conn.prepareStatement(query);
			stmt.setInt(1, idcompanie);
			rs = stmt.executeQuery();
			rs.next();
			
			String companie=rs.getString("CNAME");
			double cFunds=rs.getDouble("CFUNDS");
			Company newcompanie=new Company(idcompanie,companie,cFunds);
			rs.close();
			stmt.close();
			conn.close();
			return new BusinessCard(cardNum,hName,hId,pin,id,balance,newcompanie,current_spend,max_spend);
		}
		if(tip.contains("debit"))
		{
			query="SELECT * FROM CARDDEBIT WHERE IDCARDDEBIT=?";
			stmt=conn.prepareStatement(query);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			rs.next();
			double spendLimit=rs.getDouble("CURRENTSPENDLIMIT");
			double maxSpendLimit=rs.getDouble("MAXSPENDLIMIT");
			rs.close();
			stmt.close();
			conn.close();
			return new DebitCard(cardNum,hName,hId,pin,id,balance,spendLimit,maxSpendLimit);
		}
		if(tip.contains("student"))
		{
			query="SELECT * FROM CARDSTUDENT WHERE IDCARDSTUDENT=?";
			stmt=conn.prepareStatement(query);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			rs.next();
			String studyingUni=rs.getString("STUDYING");
			rs.close();
			stmt.close();
			conn.close();
			return new StudentCard(cardNum,hName,hId,pin,id,balance,studyingUni);
		}

		rs.close();
		stmt.close();
		conn.close();
		return 0;/*
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			String hName,num,tip;
			int pin,id,hId;
			double balance,dobanda;
			rs = stmt.executeQuery("SELECT * FROM CARDMAIN WHERE NUMBERS='"+cardNum+"' AND PIN="+pinin);
			if(rs.next()==false){
				return null;}
			hId=rs.getInt("IDHOLDER");
			num=cardNum;
			pin=rs.getInt("PIN");
			id=rs.getInt("IDCARD");
			tip=rs.getString("TIP");
			
			
			rs= stmt.executeQuery("SELECT * FROM ACCOUNT WHERE IDACCOUNT="+hId);
			rs.next();
			hName=rs.getString("NAME");
			balance=rs.getDouble("BALANCE");
			//dobanda=rs.getDouble("DOBANDA");
			
			
			if(tip.contains("business"))
			{
				rs = stmt.executeQuery("SELECT * FROM CARDBUSINESS WHERE IDCARDBUSINESS="+id);
				rs.next();
				int idcompanie=rs.getInt("COMPANYID");
				double max_spend,current_spend;
				max_spend=rs.getDouble("MAXSPENDLIMIT");
				current_spend=rs.getDouble("CURRENTSPENDLIMIT");
				
				// MIGHT UPDATE SPENDLIMIT ON RETRIEVAL
				
				rs = stmt.executeQuery("SELECT * FROM COMPANY WHERE IDCOMPANY="+idcompanie);
				rs.next();
				
				String companie=rs.getString("CNAME");
				double cFunds=rs.getDouble("CFUNDS");
				return new BusinessCard(cardNum,hName,hId,pin,id,balance,idcompanie,companie,cFunds,current_spend,max_spend);
			}
			if(tip.contains("debit"))
			{
				rs = stmt.executeQuery("SELECT * FROM CARDDEBIT WHERE IDCARDDEBIT="+id);
				rs.next();
				double spendLimit=rs.getDouble("CURRENTSPENDLIMIT");
				double maxSpendLimit=rs.getDouble("MAXSPENDLIMIT");
				return new DebitCard(cardNum,hName,hId,pin,id,balance,spendLimit,maxSpendLimit);
			}
			if(tip.contains("student"))
			{
				rs = stmt.executeQuery("SELECT * FROM CARDSTUDENT WHERE IDCARDSTUDENT="+id);
				rs.next();
				String studyingUni=rs.getString("STUDYING");
				return new StudentCard(cardNum,hName,hId,pin,id,balance,studyingUni);
			}
			 return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;*/
	}
	/**
	 * Adds money to the user's balance.
	 * <p>
	 * It updates the user account's balance
	 * in the account table and returns 0 if no issues
	 * occur.
	 *
	 * @param  user_id id of the account the money is gonna be deposited on.
	 * @param  money amount of money that will be added to the account.
	 * @return 0 if there are no issues.
	 */
	public static int deposit(int user_id,double money) {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("javaatm");
		dataSource.setPassword("new_password");
		dataSource.setURL("jdbc:mysql://localhost:3306/atm_db");
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement stmt = null;
		try {
			String query="UPDATE ACCOUNT SET balance=balance+? WHERE IDACCOUNT=?";
			stmt=conn.prepareStatement(query);
			stmt.setDouble(1,money);
			stmt.setInt(2,user_id);
			stmt.executeUpdate();
			System.out.println("Money deposited successfully");		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * Adds money to the company's funds.
	 * <p>
	 * It updates the company's funds
	 * in the company table and returns 0 if no issues
	 * occur.
	 *
	 * @param  cid id of the company the money is gonna be deposited to.
	 * @param  money amount of money that will be added to the funds.
	 * @return 0 if there are no issues.
	 */
	public static int cdeposit(int cid,double money) {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("javaatm");
		dataSource.setPassword("new_password");
		dataSource.setURL("jdbc:mysql://localhost:3306/atm_db");
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement stmt = null;
		try {
			String query="UPDATE COMPANY SET cfunds=cfunds+? WHERE IDCOMPANY=?";
			stmt=conn.prepareStatement(query);
			stmt.setDouble(1,money);
			stmt.setInt(2,cid);
			stmt.executeUpdate();
			System.out.println("Money deposited successfully");		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * Withdraws money from the user account's balance
	 * <p>
	 * It updates the user's balance and returns 0 if
	 * everything goes well.
	 *
	 * @param  user_id id of the user which the money is withdrawn from
	 * @param  money the amount of money withdrawn
	 * @return 0 if everything goes well.
	 */
	public static int withdraw(int user_id,double money) {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("javaatm");
		dataSource.setPassword("new_password");
		dataSource.setURL("jdbc:mysql://localhost:3306/atm_db");
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement stmt = null;
		try {
			String query="UPDATE ACCOUNT SET balance=balance-? WHERE IDACCOUNT=?";
			stmt=conn.prepareStatement(query);
			stmt.setDouble(1, money);
			stmt.setInt(2,user_id);
			stmt.executeUpdate();
			System.out.println("Money withdrawn successfully");		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * Withdraws money from the company's funds
	 * <p>
	 * It updates the company's funds and returns 0 if
	 * everything goes well.
	 *
	 * @param  cid id of the company which the money is withdrawn from
	 * @param  money the amount of money withdrawn
	 * @return 0 if everything goes well.
	 */
	public static int cwithdraw(int cid,double money) {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("javaatm");
		dataSource.setPassword("new_password");
		dataSource.setURL("jdbc:mysql://localhost:3306/atm_db");
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement stmt = null;
		try {
			String query="UPDATE COMPANY SET cfunds=cfunds-? WHERE IDCOMPANY=?";
			stmt=conn.prepareStatement(query);
			stmt.setDouble(1, money);
			stmt.setInt(2,cid);
			stmt.executeUpdate();
			System.out.println("Money withdrawn successfully");		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * Inserts a new business card into the database
	 * <p>
	 * It inserts a new card in the cardmain table and
	 * the extra information it has in the cardbusiness table.
	 * <p>
	 *
	 * @param  holderId id of the user who owns the card
	 * @param  pin password of the card
	 * @param  numbers the numbers of the card
	 * @param  tip the type of the card
	 * @param  companyid the id of the company
	 * @param  spendlimit the daily withdraw limit for company funds this card has.
	 * @return 0 if all goes well.
	 */
	public static int create_card_business(int holderId,int pin,String numbers,String tip,int companyid,double spendlimit){
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("javaatm");
		dataSource.setPassword("new_password");
		dataSource.setURL("jdbc:mysql://localhost:3306/atm_db");
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement stmt = null;
		try {
			String query="INSERT INTO CARDMAIN (`IDHOLDER`,`PIN`,`NUMBERS`,`TIP`) VALUES (?,?,?,?)";
			stmt=conn.prepareStatement(query);
			stmt.setInt(1,holderId);
			stmt.setInt(2, pin);
			stmt.setString(3, numbers);
			stmt.setString(4, tip);
			stmt.executeUpdate();
			ResultSet rs = null;
			rs=stmt.executeQuery("SELECT LAST_INSERT_ID() as ID;");
			rs.next();
			LocalDate current_date=java.time.LocalDate.now();
			query="INSERT INTO CARDBUSINESS (`IDCARDBUSINESS`,`COMPANYID`,`MAXSPENDLIMIT`,`CURRENTSPENDLIMIT`,`LAST_UPDATED`) VALUES (?,?,?,?,?)";
			stmt=conn.prepareStatement(query);
			stmt.setInt(1, rs.getInt("ID"));
			stmt.setInt(2, companyid);
			stmt.setDouble(3, spendlimit);
			stmt.setDouble(4, spendlimit);
			stmt.setString(5, current_date.toString());
			stmt.executeUpdate();
			System.out.println("Business card created successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * Inserts a new student card into the database
	 * <p>
	 * It inserts a new card in the cardmain table and
	 * the extra information it has in the cardstudent table.
	 * <p>
	 *
	 * @param  holderId id of the user who owns the card
	 * @param  pin password of the card
	 * @param  numbers the numbers of the card
	 * @param  tip the type of the card
	 * @param  university is the name of the university the user studies at
	 * @return 0 if all goes well.
	 */
	public static int create_card_student(int holderId,int pin,String numbers,String tip,String university){
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("javaatm");
		dataSource.setPassword("new_password");
		dataSource.setURL("jdbc:mysql://localhost:3306/atm_db");
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement stmt = null;
		try {
			String query="INSERT INTO CARDMAIN (`IDHOLDER`,`PIN`,`NUMBERS`,`TIP`) VALUES (?,?,?,?)";
			stmt=conn.prepareStatement(query);
			stmt.setInt(1,holderId);
			stmt.setInt(2, pin);
			stmt.setString(3, numbers);
			stmt.setString(4, tip);
			stmt.executeUpdate();
			ResultSet rs = null;
			rs=stmt.executeQuery("SELECT LAST_INSERT_ID() as ID;");
			rs.next();
			query="INSERT INTO CARDSTUDENT (`IDCARDSTUDENT`,`STUDYING`) VALUES (?,?)";
			stmt=conn.prepareStatement(query);
			stmt.setInt(1, rs.getInt("ID"));
			stmt.setString(2, university);
			stmt.executeUpdate();
			System.out.println("Student card created successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * Inserts a new account into the database
	 *
	 * @param  user the name the user will have
	 * @return id of the newly inserted user
	 */
	public static int create_account(String user){
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("javaatm");
		dataSource.setPassword("new_password");
		dataSource.setURL("jdbc:mysql://localhost:3306/atm_db");
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement stmt = null;
		try {
			String query="INSERT INTO ACCOUNT (`BALANCE`,`NAME`) VALUES (0,?)";
			stmt=conn.prepareStatement(query);
			stmt.setString(1, user);
			stmt.executeUpdate();
			ResultSet rs = null;
			rs=stmt.executeQuery("SELECT LAST_INSERT_ID() as ID;");
			rs.next();
			System.out.println("Account created successfully, returning id...");
			return rs.getInt("ID");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * Inserts a new company into the database
	 *
	 * @param  name the name the company will have
	 * @return id of the newly inserted company
	 */
	public static int create_company(String name){
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("javaatm");
		dataSource.setPassword("new_password");
		dataSource.setURL("jdbc:mysql://localhost:3306/atm_db");
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement stmt = null;
		try {
			String query="INSERT INTO COMPANY (`CNAME`,`CFUNDS`) VALUES (?,0)";
			stmt=conn.prepareStatement(query);
			stmt.setString(1, name);
			stmt.executeUpdate();
			ResultSet rs = null;
			rs=stmt.executeQuery("SELECT LAST_INSERT_ID() as ID;");
			rs.next();
			System.out.println("Company created successfully, returning id...");
			return rs.getInt("ID");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
