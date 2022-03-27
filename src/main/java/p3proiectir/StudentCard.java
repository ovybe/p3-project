package p3proiectir;
/**
 * Class for one of the three card types.
 */
public class StudentCard extends Card{
	String university_name;
	public StudentCard(String numbers, String holdername, int holderid, int pin, int id, double balance,String uname) {
		super(numbers, holdername, holderid, pin, id, balance);
		this.university_name=uname;
	}
	// GETTERS
	public String getUniversityName(){
		return this.university_name;
	}
	// SETTERS
	public void setUniversityName(String uname){
		this.university_name=uname;
	}
	// METHODS
	/**
	 * (NOT USED) Deposits 1500 ron to the holder.
	 */
	public void sendBursa(){
		this.balance=this.balance+1500;
		Actions.deposit(this.holderid, 1500);
	}
	// TO_STRING
	@Override
	public String toString(){
		return "---Card---"+'\n'+"Nume:"+holdername+'\n'+"Numbers:"+numbers+'\n'+"Pin:"+pin+'\n'+"ID:"+id+'\n'+"Balance:"+balance+'\n'+"University Name:"+university_name+'\n';
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
		double tax=0;
		System.out.println("Taxa:");
		System.out.println(tax);
		System.out.println("Valoare:");
		if(balance>=(money+tax)) {
			this.balance=this.balance-money-tax;
			return Actions.withdraw(this.holderid, money+tax);
			}
		return 1;
		/*System.out.println(this.getBalance());
		}
		else {
			System.out.println("Not enough money to withdraw");
			System.out.println("Amount required (with tax):"+(money+tax));
			System.out.println("Current amount:"+this.balance);
		}*/
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
		if(money<=balance) {
			balance-=money;
			return Actions.send_money(user_id, money, holderid);
			}
		else {
			System.out.println("Send money action failed! Not enough money!");
			System.out.println("You need:"+money);
			System.out.println("You have:"+this.balance);
			return 1;
		}
	}
}
