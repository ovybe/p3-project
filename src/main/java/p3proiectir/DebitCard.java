package p3proiectir;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * Class for one of the three card types.
 */
public class DebitCard extends Card{
	double sp_limit,max_sp_limit;
	public DebitCard(String numbers, String holdername, int holderid, int pin, int id, double balance,double sp_limit,double max_sp_limit) {
		super(numbers, holdername, holderid, pin, id, balance);
		this.sp_limit=sp_limit;
		this.max_sp_limit=max_sp_limit;
	}
	// GETTERS
	public double getSpendingLimit(){
		return sp_limit;
	}
	public double getMaxSpendingLimit(){
		return max_sp_limit;
	}
	// UPDATE SPENDINGLIMIT
	/**
	 * Updates the card's spending limit
	 * <p>
	 * The method takes the current date and compares it
	 * with the date the card has in the database's table.
	 * If the date is different, it updates it to today's date.
	 * If the date is equal to today's date, it updates the current
	 * spending limit in the database.
	 * <p>
	 * Used to reset/check the user's spending limit for withdrawal actions.
	 */
	public void updateSpendingLimit(){
		LocalDate current_date=java.time.LocalDate.now();
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
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs= stmt.executeQuery("SELECT * FROM CARDDEBIT WHERE IDCARDDEBIT="+id);
			rs.next();
			LocalDate last_updated=rs.getDate("LAST_UPDATED").toLocalDate();
			if(!last_updated.equals(current_date))
			{
				stmt.executeUpdate("UPDATE CARDDEBIT SET LAST_UPDATED='"+current_date+"', CURRENTSPENDLIMIT="+max_sp_limit+" WHERE IDCARDDEBIT="+id);
				sp_limit=max_sp_limit;	
			}
			else
				stmt.executeUpdate("UPDATE CARDDEBIT SET CURRENTSPENDLIMIT="+sp_limit+" WHERE IDCARDDEBIT="+id);		
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
	}
	// SETTERS
	public void setSpendingLimit(double sp_limit){
		this.sp_limit=sp_limit;
	}
	public void setMaxSpendingLimit(double max_sp_limit) {
		this.max_sp_limit = max_sp_limit;
	}
	// TO_STRING
	@Override
	public String toString(){
		return "---Card---"+'\n'+"Nume:"+holdername+'\n'+"Numbers:"+numbers+'\n'+"Pin:"+pin+'\n'+"ID:"+id+'\n'+"Balance:"+balance+'\n'+"Spending Limit:"+sp_limit+'\n';
	}
	// ABSTRACT METHODS
	/**
	 * Calls the deposit method in the Actions class
	 * <p>
	 *
	 * @param  money the amount of money the user deposits
	 * @return Whatever the deposit action returns.
	 */
	@Override
	public int deposit(double money) {
		this.balance=this.balance+money;
		return Actions.deposit(this.holderid, money);
	}
	/**
	 * Calls for the withdraw method in the Actions class.
	 * <p>
	 * It also checks if the user has enough money
	 * and if the user hasn't gone past the spending limit
	 * 
	 * @param money the amount of money withdrawn
	 * @return Whatever the withdraw action returns, unless it encounters a problem (then it returns 1 or 2)
	 */
	@Override
	public int withdraw(double money) {
		this.updateSpendingLimit();
		double tax=5;
		System.out.println("Taxa:");
		System.out.println(tax);
		System.out.println("Valoare:");
		if(balance>=(money+tax)) {
			if(money<=sp_limit) {
				sp_limit=sp_limit-money;
				this.balance=this.balance-money-tax;
				return Actions.withdraw(this.holderid, money+tax);
				//System.out.println(this.getBalance());
			}
			else {
				System.out.println("You are trying to withdraw over your spending limit for the day!");
				System.out.println("You tried to withdraw:"+money);
				System.out.println("Your remaining limit for today:"+sp_limit);
				System.out.println("Your max spending limit daily:"+max_sp_limit);
				return 1;
			}
		}
		else {
			System.out.println("Not enough money to withdraw");
			System.out.println("Amount required (with tax):"+(money+tax));
			System.out.println("Current amount:"+this.balance);
			return 2;
		}
	}
	/**
	 * Calls the send_money method from the Actions class
	 * <p>
	 * It also updates the spending limit and does the
	 * spending limit checks, returning an error if the
	 * user is over the spending limit.
	 * 
	 * @param  user_id integer type id of the user the money will be sent to
	 * @param  money amount of money that will be sent
	 * @return Whatever the send_money method returns, or 1/2 if an issue occurs
	 */
	@Override
	public int send_money(int user_id,double money) {
		this.updateSpendingLimit();
		if(money<=balance && money<=sp_limit) {
			balance-=money;
			return Actions.send_money(user_id, money, holderid);
			}
		else if(money<=sp_limit){
			System.out.println("Send money action failed! Not enough money!");
			System.out.println("You need:"+money);
			System.out.println("You have:"+this.balance);
			return 1;
		}
		else {
			System.out.println("Send money action failed! You tried to send more than your spending limit!");
			System.out.println("You sent:"+money);
			System.out.println("Your limit is:"+this.sp_limit);
			return 2;
		}
	}
}
