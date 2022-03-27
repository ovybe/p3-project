package p3proiectir;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class TestClasses {
	@Test
	void testCardGetters() {
		StudentCard card1=new StudentCard("1111-1111-1111-1111","Ion Andrei",3,1143,1,500.34,"UVT");
		
		 Assertions.assertEquals("1111-1111-1111-1111", card1.getNumbers(), "Numbers:1111-1111-1111-1111");
		 Assertions.assertEquals("Ion Andrei", card1.getHoldername(), "Name:Ion Andrei");
		 Assertions.assertEquals(1143, card1.getPin(), "Pin:1143");
		 Assertions.assertEquals(1, card1.getID(), "ID:1");
		 Assertions.assertEquals(500.34, card1.getBalance(), "Balance:500.34");
	}
	@Test
	void testCardSetters() {
		StudentCard card1=new StudentCard("1111-1111-1111-1111","Ion Andrei",4,1143,1,500.34,"UVT");
		
		card1.setNumbers("1133-1111-1111-1111");
		card1.setHoldername("Maria Popescu");
		card1.setPin(1111);
		card1.setID(255);
		card1.setBalance(500);
		
		 Assertions.assertEquals("1133-1111-1111-1111", card1.getNumbers(), "Setter check Numbers");
		 Assertions.assertEquals("Maria Popescu", card1.getHoldername(), "Setter check Holdername");
		 Assertions.assertEquals(1111, card1.getPin(), "Setter check Pin");
		 Assertions.assertEquals(255, card1.getID(), "Setter check ID");
		 Assertions.assertEquals(500.00, card1.getBalance(), "Setter check Balance");
	}
	@Test
	void testStudentCardGetters(){
		StudentCard cardS=new StudentCard("1111-1111-1111-1111","Ion Andrei",3,1143,1,500.34,"UVT");
		Assertions.assertEquals("UVT", cardS.getUniversityName(), "Universitate Get check");
	}
	@Test
	void testStudentCardSetters(){
		StudentCard cardS=new StudentCard("1111-1111-1111-1111","Ion Andrei",7,1143,1,500.34,"UVT");
		cardS.setUniversityName("UPT");
		Assertions.assertEquals("UPT", cardS.getUniversityName(), "Universitate Set check");
	}
	@Test
	void testBusinessCardGetters(){
		BusinessCard cardB=new BusinessCard("1111-1111-1111-1111","Ion Andrei",8,1143,1,500.34,new Company(5,"IBM",40000),500,500);
		Assertions.assertEquals("IBM", cardB.getCompanyName(), "Company name Get check");
		Assertions.assertEquals(40000.00, cardB.getcFunds(), "Company funds Get check");
	}
	@Test
	void testBusinessCardSetters(){
		BusinessCard cardB=new BusinessCard("1111-1111-1111-1111","Ion Andrei",6,1143,1,500.34,new Company(5,"IBM",40000),333,333);
		cardB.setCompanyName("Nokia");
		cardB.setcFunds(405550.3);
		Assertions.assertEquals("Nokia", cardB.getCompanyName(), "Company name Set check");
		Assertions.assertEquals(405550.30, cardB.getcFunds(), "Company funds Set check");
	}
	@Test
	void testDebitCardGetters(){
		DebitCard cardD=new DebitCard("1111-1111-1111-1111","Ion Andrei",3,1143,1,500.34,500,500);
		Assertions.assertEquals(500.00, cardD.getSpendingLimit(), "Spending Limit Get check");
	}
	@Test
	void testDebitCardSetters(){
		DebitCard cardD=new DebitCard("1111-1111-1111-1111","Ion Andrei",2,1143,1,500.34,500,500);
		cardD.setSpendingLimit(333.333);
		Assertions.assertEquals(333.333, cardD.getSpendingLimit(), "Spending Limit Set check");
	}
}
