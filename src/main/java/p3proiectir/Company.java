package p3proiectir;
/**
 * Company class for BusinessCard
 */
public class Company {
	private int id;
	private String name;
	private double funds;
	public Company(int companyid,String cname,double cfunds){
		id=companyid;
		name=cname;
		funds=cfunds;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getFunds() {
		return funds;
	}
	public void setFunds(double funds) {
		this.funds = funds;
	}
	
}
