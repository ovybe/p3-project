package p3proiectir;

import javax.swing.*;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.Color;

/**
 * GUI class for the ATM main screen
 */
public class GUI_Menu extends JFrame{

	JLabel username,id,cardNum,other;
	JButton deposit,withdraw,checkBalance,sendMoney,exit;
	
	public JButton CustomButtonUI(String input) {
		JButton newbutton=new JButton(input);
		newbutton.setPreferredSize(new Dimension(40,20));
		newbutton.setBackground(new Color(59, 89, 182));
		newbutton.setForeground(Color.WHITE);
		newbutton.setFocusPainted(false);
		newbutton.setFont(new Font("Tahoma", Font.BOLD, 12));
        return newbutton;
	}
	
	/**
	 * Constructor for the ATM Screen
	 */
	public GUI_Menu(final Card cardIn){
		try { 
		    //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Color backgroundcolor=new Color(189, 190, 197);
			UIManager.put("Panel.background",backgroundcolor);
			UIManager.put("OptionPane.background", backgroundcolor);
			UIManager.put("OptionPane.messagebackground", backgroundcolor);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setSize(600,480);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel info1,info2,info3,info4;
		info1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		info1.setPreferredSize(new Dimension(600,30));
		info2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		info2.setPreferredSize(new Dimension(600,30));
		info3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		info3.setPreferredSize(new Dimension(600,30));
		
		username=new JLabel("Welcome, "+cardIn.getHoldername()+" !");
		id=new JLabel("USER ID:"+cardIn.getHolderID());
		cardNum=new JLabel("Card Number: "+cardIn.getNumbers());
		
		info1.add(username);
		info2.add(id);
		info3.add(cardNum);
		
		this.add(info1);
		this.add(info2);
		this.add(info3);
		
		if(cardIn.getClass().equals(StudentCard.class)) {
			info4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
			info4.setPreferredSize(new Dimension(600,30));
			other=new JLabel("University: "+((StudentCard)cardIn).getUniversityName());
			info4.add(other);
			this.add(info4);
		}
		else if(cardIn.getClass().equals(BusinessCard.class)) {
			info4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
			info4.setPreferredSize(new Dimension(600,30));
			other=new JLabel("Business Company: "+((BusinessCard)cardIn).getCompanyName());
			info4.add(other);
			this.add(info4);
			//System.out.println("I happened");
		}
		
		
		JPanel middlebuttons;
		middlebuttons = new JPanel(new BorderLayout());
		middlebuttons.setPreferredSize(new Dimension(575,120));
		deposit=CustomButtonUI("Deposit");
		deposit.setPreferredSize(new Dimension(120,40));
		deposit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String money;
            do {
                 money= JOptionPane.showInputDialog(deposit,
                        "Input the amount of money:", null);
                if (money!=null && money.matches("^[0-9]*$")) {
                	// NOTHING DONE HERE YET
                } else if(money!=null){
                	JOptionPane.showMessageDialog(deposit, "Please only insert numbers!");
                }
            } while (money!=null && !money.matches("^[0-9]*$"));
            
            if(money!=null && money.length()>0) {
            if(cardIn.deposit(Double.parseDouble(money))==0) {
            	// INSERT SUCCESSFUL OUTPUT
            	JOptionPane.showMessageDialog(deposit, "Sucessfully deposited "+money+" ron to your account!");
            	}else {
            	// INSERT FAILURE!!!
            	JOptionPane.showMessageDialog(deposit, "Failed to deposit "+money+" ron!");
            	}
			}
            }
		});
		
		if(cardIn.getClass().equals(DebitCard.class)) {
			((DebitCard) cardIn).updateSpendingLimit();
			withdraw=CustomButtonUI("Withdraw");
			withdraw.setPreferredSize(new Dimension(120,40));
			withdraw.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					String money;
	            do {
	                 money= JOptionPane.showInputDialog(withdraw,
	                        "Input the amount of money:", null);
	                if (money!=null && money.matches("^[0-9]*$")) {
	                	// NOTHING DONE HERE YET
	                } else if(money!=null){
	                	JOptionPane.showMessageDialog(withdraw, "Please only insert numbers!");
	                }
	            } while (money!=null && !money.matches("^[0-9]*$"));
	            
	            if(money!=null && money.length()>0) {
	            	int k=((DebitCard) cardIn).withdraw(Double.parseDouble(money));
	            if(k==0) {
	            		// INSERT SUCCESSFUL OUTPUT
	            		JOptionPane.showMessageDialog(withdraw, "Sucessfully withdrawn "+money+" ron from funds!");
	            	}else if(k==1){
	            		// INSERT FAILURE!!!
	            		JOptionPane.showMessageDialog(withdraw, "Failed to withdraw "+money+" ron from funds! You are past your limit! \nCurrent limit: "+((DebitCard) cardIn).getSpendingLimit()+"\nMax Limit: "+((DebitCard) cardIn).getMaxSpendingLimit());
	            	}
	            	else {
	            		JOptionPane.showMessageDialog(withdraw, "Failed to withdraw "+money+" ron from funds! Not enough money!\nAmount required (With tax): "+(cardIn.getBalance()+5)+"\nCurrent Amount:"+cardIn.getBalance());
	            	}
				}
	            }
			});
			sendMoney=CustomButtonUI("Send Money");
			sendMoney.setPreferredSize(new Dimension(120,40));
			sendMoney.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {		
				String id;
	            do {
	                 id= JOptionPane.showInputDialog(sendMoney,
	                        "User ID to send the money to:", null);
	                if (id!=null && id.matches("^[0-9]*$")) {
	                	// NOTHING DONE HERE YET
	                } else if(id!=null){
	                	JOptionPane.showMessageDialog(sendMoney, "Please only insert numbers!");
	                }
	            } while (id!=null && !id.matches("^[0-9]*$"));
	            if(id!=null) {
					String money;
		            do {
		                 money= JOptionPane.showInputDialog(sendMoney,
		                        "Input the amount of money:", null);
		                if (money!=null && money.matches("^[0-9]*$")) {
		                	// NOTHING DONE HERE YET
		                } else if(money!=null){
		                	JOptionPane.showMessageDialog(sendMoney, "Please only insert numbers!");
		                }
		            } while (money!=null && !money.matches("^[0-9]*$"));
		            
		            if(money!=null && money.length()>0) {
		            	int l=cardIn.send_money(Integer.parseInt(id),Double.parseDouble(money));
		                if(l==0) {
		                	// INSERT SUCCESSFUL OUTPUT
		                	JOptionPane.showMessageDialog(withdraw, "Sucessfully sent "+money+" ron to user!");
		                	}else if(l==1){
		                	// INSERT FAILURE!!!
		                	JOptionPane.showMessageDialog(withdraw, "Failed to send "+money+" ron! Not enough money to send!\nYou have: "+cardIn.getBalance());
		                	}
		                	else if(l==2){
				            	JOptionPane.showMessageDialog(withdraw, "Failed to send "+money+" ron to user id '"+id+"' from funds! You are past your limit! \nCurrent limit: "+((DebitCard) cardIn).getSpendingLimit()+"\nMax Limit: "+((DebitCard) cardIn).getMaxSpendingLimit());
		                	}
		                	else if(l==3) {
			                	JOptionPane.showMessageDialog(withdraw, "Failed to send "+money+" ron! Invalid user id '"+id+"'!");
		                	}
		    			}
		            
					}
	            }
			});
		}
		else {
			withdraw=CustomButtonUI("Withdraw");
			withdraw.setPreferredSize(new Dimension(120,40));
			withdraw.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String money;
					do {
						money= JOptionPane.showInputDialog(withdraw,
								"Input the amount of money:", null);
						if (money!=null && money.matches("^[0-9]*$")) {
							// NOTHING DONE HERE YET
						} else if(money!=null){
							JOptionPane.showMessageDialog(withdraw, "Please only insert numbers!");
						}
					} while (money!=null && !money.matches("^[0-9]*$"));
					
					if(money!=null && money.length()>0) {
						if(cardIn.withdraw(Double.parseDouble(money))==0) {
							// INSERT SUCCESSFUL OUTPUT
							JOptionPane.showMessageDialog(withdraw, "Sucessfully withdrawn "+money+" ron to your account!");
						}else {
							// INSERT FAILURE!!!
							JOptionPane.showMessageDialog(withdraw, "Failed to withdraw "+money+" ron!");
						}
					}
				}
			});
		sendMoney=CustomButtonUI("Send Money");
		sendMoney.setPreferredSize(new Dimension(120,40));
		sendMoney.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
			String id;
            do {
                 id= JOptionPane.showInputDialog(sendMoney,
                        "User ID to send the money to:", null);
                if (id!=null && id.matches("^[0-9]*$")) {
                	// NOTHING DONE HERE YET
                } else if(id!=null){
                	JOptionPane.showMessageDialog(sendMoney, "Please only insert numbers!");
                }
            } while (id!=null && !id.matches("^[0-9]*$"));
            if(id!=null) {
				String money;
	            do {
	                 money= JOptionPane.showInputDialog(sendMoney,
	                        "Input the amount of money:", null);
	                if (money!=null && money.matches("^[0-9]*$")) {
	                	// NOTHING DONE HERE YET
	                } else if(money!=null){
	                	JOptionPane.showMessageDialog(sendMoney, "Please only insert numbers!");
	                }
	            } while (money!=null && !money.matches("^[0-9]*$"));
	            
	            if(money!=null && money.length()>0) {
	                if(cardIn.send_money(Integer.parseInt(id),Double.parseDouble(money))==0) {
	                	// INSERT SUCCESSFUL OUTPUT
	                	JOptionPane.showMessageDialog(withdraw, "Sucessfully sent "+money+" ron to user!");
	                	}else {
	                	// INSERT FAILURE!!!
	                	JOptionPane.showMessageDialog(withdraw, "Failed to send "+money+" ron!");
	                	}
	    			}
	            
				}
            }
		});
		}
		
		middlebuttons.add(deposit,BorderLayout.WEST);
		middlebuttons.add(withdraw,BorderLayout.EAST);
		middlebuttons.add(sendMoney,BorderLayout.CENTER);
		this.add(middlebuttons);
		
		JPanel bottombuttons;
		bottombuttons = new JPanel(new BorderLayout());
		bottombuttons.setPreferredSize(new Dimension(575,120));
		
		checkBalance=CustomButtonUI("Check Balance");
		checkBalance.setPreferredSize(new Dimension(120,40));
		checkBalance.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
            	JOptionPane.showMessageDialog(checkBalance, "Current balance: "+cardIn.getBalance());
				}
		});
		exit=CustomButtonUI("Log Out");
		exit.setPreferredSize(new Dimension(120,20));
		//exit.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				GUI createback=new GUI();
				createback.setVisible(true);
				createback.setLocationRelativeTo(null);
				URL iconURL = null;
				iconURL= Main.class.getResource("atm.png");
				ImageIcon icon = new ImageIcon(iconURL);
				createback.setIconImage(icon.getImage());
				createback.setTitle("ATM Login");
            }
		});
		
		bottombuttons.add(checkBalance,BorderLayout.WEST);
		bottombuttons.add(exit,BorderLayout.EAST);
		
		if(cardIn.getClass().equals(BusinessCard.class)) {
			((BusinessCard) cardIn).updateSpendingLimit();
			final JButton checkBalanceCompany=CustomButtonUI("Check Company Funds");
			checkBalanceCompany.setPreferredSize(new Dimension(180,40));
			checkBalanceCompany.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {		
	            	JOptionPane.showMessageDialog(checkBalanceCompany, "Company Funds: "+((BusinessCard)cardIn).getcFunds());
					}
			});
			
			final JButton withdrawCompany=CustomButtonUI("Withdraw Company Funds");
			withdrawCompany.setPreferredSize(new Dimension(120,40));
			withdrawCompany.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String money;
	            do {
	                 money= JOptionPane.showInputDialog(withdrawCompany,
	                        "Input the amount of money:", null);
	                if (money!=null && money.matches("^[0-9]*$")) {
	                	// NOTHING DONE HERE YET
	                } else if(money!=null){
	                	JOptionPane.showMessageDialog(withdrawCompany, "Please only insert numbers!");
	                }
	            } while (money!=null && !money.matches("^[0-9]*$"));
	            
	            if(money!=null && money.length()>0) {
	            	int k=((BusinessCard) cardIn).cwithdraw(Double.parseDouble(money));
	            if(k==0) {
	            		// INSERT SUCCESSFUL OUTPUT
	            		JOptionPane.showMessageDialog(withdrawCompany, "Sucessfully withdrawn "+money+" ron from company funds!");
	            	}else if(k==1){
	            		// INSERT FAILURE!!!
	            		JOptionPane.showMessageDialog(withdrawCompany, "Failed to withdraw "+money+" ron from company funds! You are past your limit! \nCurrent limit: "+((BusinessCard) cardIn).getCurrentSpLimit()+"\nMax Limit: "+((BusinessCard) cardIn).getMaxSpLimit());
	            	}
	            	else {
	            		JOptionPane.showMessageDialog(withdrawCompany, "Failed to withdraw "+money+" ron from company funds! Not enough money!");
	            	}
				}
	            }
			});
			
			final JButton depositCompany=CustomButtonUI("Deposit To Company Funds");
			depositCompany.setPreferredSize(new Dimension(120,40));
			depositCompany.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String money;
	            do {
	                 money= JOptionPane.showInputDialog(depositCompany,
	                        "Input the amount of money from your funds:", null);
	                if (money!=null && money.matches("^[0-9]*$")) {
	                	// NOTHING DONE HERE YET
	                } else if(money!=null){
	                	JOptionPane.showMessageDialog(depositCompany, "Please only insert numbers!");
	                }
	            } while (money!=null && !money.matches("^[0-9]*$"));
	            
	            if(money!=null && money.length()>0) {
	            if(((BusinessCard)cardIn).cdeposit(Double.parseDouble(money))==0) {
	            	// INSERT SUCCESSFUL OUTPUT
	            	JOptionPane.showMessageDialog(depositCompany, "Sucessfully deposited "+money+" ron to company funds!");
	            	}else {
	            	// INSERT FAILURE!!!
	            	JOptionPane.showMessageDialog(depositCompany, "Failed to deposit "+money+" ron!");
	            	}
				}
	            }
			});
			
			bottombuttons.add(depositCompany,BorderLayout.SOUTH);
			bottombuttons.add(withdrawCompany,BorderLayout.NORTH);
			bottombuttons.add(checkBalanceCompany,BorderLayout.CENTER);
			
		}
		

		this.add(bottombuttons);
	}
}
