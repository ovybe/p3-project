package p3proiectir;
/**
 * Abstract class to be used by the 3 types of cards
 */
abstract public class Card {
String numbers,holdername;
int pin,id,holderid;
double balance;
public Card(String numbers, String holdername,int holderid,int pin,int id,double balance){
	this.numbers=numbers;
	this.holdername=holdername;
	this.holderid=holderid;
	this.pin=pin;
	this.id=id;
	this.balance=balance;
	//this.dobanda=dobanda;
}
// GETTERS
public String getNumbers(){
	return numbers;
}
public String getHoldername(){
	return holdername;
}
public int getPin(){
	return pin;
}
public int getID(){
	return id;
}
public int getHolderID(){
	return holderid;
}
public double getBalance(){
	return balance;
}
/*public double getDobanda(){
	return dobanda;
}*/
public int send_money(int user_id,double money) {
	if(money<=balance) {
		balance-=money;
		return Actions.send_money(user_id, money, id);
		}
	else {
		System.out.println("Send money action failed! Not enough money!");
		System.out.println("You need:"+money);
		System.out.println("You have:"+this.balance);
		return 1;
	}
}
abstract public int deposit(double money);
abstract public int withdraw(double money);
// SETTERS
public void setNumbers(String numbers) {
	this.numbers=numbers;
}
public void setHoldername(String holdername) {
	this.holdername=holdername;
}
public void setPin(int pin) {
	this.pin=pin;
}
public void setID(int id) {
	this.id=id;
}
public void setHolderID(int id) {
	this.holderid=id;
}
public void setBalance(double balance) {
	this.balance=balance;
}
/*
public void setDobanda(double dobanda) {
	this.dobanda=dobanda;
}*/
// TO_STRING
public String to_String(){
	return "---Card---"+'\n'+"Nume:"+holdername+'\n'+"Numbers:"+numbers+'\n'+"Pin:"+pin+'\n'+"ID:"+id+'\n'+"Balance:"+balance+'\n';
}
}
