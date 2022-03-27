package p3proiectir;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import p3proiectir.GUI.JTextFieldLimit;

/**
 * GUI Class containing everything for the New card,user,company menus
 */
public class GUI_Create_Options extends JFrame {
	String[] types= {"Debit","Business","Student"};
	JButton reset,back,submit;
	JLabel holderIdLabel,pinLabel,numbersLabel,tipLabel,userLabel;
	JTextField holderId,pin,tip,companyId,spendlim,university,user;
	JFormattedTextField numbers;
	JComboBox typelist = new JComboBox(types);
	
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
	 * Constructor for each option in the selection menu
	 * <p>
	 * The constructor creates the window differently based on
	 * the value of the parameter.
	 * <p>
	 * If i has the value 1, it builds the New card GUI
	 * If i has the value 2, it builds the New user GUI
	 * If i has the value 3, it builds the New company GUI
	 * <p>
	 * For the New card GUI, the window is rebuilt if the user
	 * selects another option from the JComboBox
	 *
	 * @param  i integer which tells the constructor which GUI to build
	 */
	public GUI_Create_Options(int i) {
		
		try { 
		    //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Color backgroundcolor=new Color(189, 190, 197);
			UIManager.put("Panel.background",backgroundcolor);
			UIManager.put("OptionPane.background", backgroundcolor);
			UIManager.put("OptionPane.messagebackground", backgroundcolor);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setSize(400,340);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel main = new JPanel(new FlowLayout(FlowLayout.LEFT));
		main.setPreferredSize(new Dimension(400,30));
		final JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
		top.setPreferredSize(new Dimension(400,110));
		final JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bottom.setPreferredSize(new Dimension(400,65));
		final JPanel bottombut = new JPanel(new FlowLayout(FlowLayout.CENTER));
		bottom.setPreferredSize(new Dimension(400,65));
		// createCard
		if(i==1) {
			JPanel typelistp=new JPanel(new FlowLayout(FlowLayout.LEFT));
			typelistp.setPreferredSize(new Dimension(53,30));
			tipLabel= new JLabel("Tip");
			typelist.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(typelist.getSelectedItem()=="Business") {
						bottom.removeAll();
						JPanel companyp = new JPanel(new FlowLayout(FlowLayout.LEFT));
						JPanel spendlimp = new JPanel(new FlowLayout(FlowLayout.LEFT));
						companyp.setPreferredSize(new Dimension(400,30));
						spendlimp.setPreferredSize(new Dimension(400,30));
						
						JLabel companyLabel=new JLabel("Company ID");
						companyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
						companyLabel.setPreferredSize(new Dimension(70,15));
						
						JLabel spendlimLabel=new JLabel("Spend Limit");
						spendlimLabel.setHorizontalAlignment(SwingConstants.RIGHT);
						spendlimLabel.setPreferredSize(new Dimension(70,15));
						
						companyId=new JTextField(20);
						spendlim=new JTextField(20);
						
						companyp.add(companyLabel);
						companyp.add(companyId);
						spendlimp.add(spendlimLabel);
						spendlimp.add(spendlim);
						
						bottom.add(companyp);
						bottom.add(spendlimp);
						bottom.revalidate();
						bottom.repaint();
					}
					else if(typelist.getSelectedItem()=="Student") {
						bottom.removeAll();
						JLabel universityLabel=new JLabel("University");
						universityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
						universityLabel.setPreferredSize(new Dimension(75,15));
						university=new JTextField(20);
						bottom.add(universityLabel);
						bottom.add(university);
						bottom.revalidate();
						bottom.repaint();
					}
					else {
						bottom.removeAll();
						JPanel spendlimp = new JPanel(new FlowLayout(FlowLayout.LEFT));
						spendlimp.setPreferredSize(new Dimension(400,30));
						JLabel spendlimLabel=new JLabel("Spend Limit");
						spendlimLabel.setHorizontalAlignment(SwingConstants.RIGHT);
						spendlimLabel.setPreferredSize(new Dimension(70,15));
						spendlim=new JTextField(20);
						
						spendlimp.add(spendlimLabel);
						spendlimp.add(spendlim);
						bottom.add(spendlimp);
						bottom.revalidate();
						bottom.repaint();
					}
	            }
			});
			
			main.add(typelistp);
			main.add(tipLabel);
			main.add(typelist);
		
			JPanel spendlimp = new JPanel(new FlowLayout(FlowLayout.LEFT));
			spendlimp.setPreferredSize(new Dimension(400,30));
			JLabel spendlimLabel=new JLabel("Spend Limit");
			spendlimLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			spendlimLabel.setPreferredSize(new Dimension(70,15));
			spendlim=new JTextField(20);
			
			spendlimp.add(spendlimLabel);
			spendlimp.add(spendlim);
			bottom.add(spendlimp);
			
			holderIdLabel= new JLabel("Holder Id");
			holderIdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			holderIdLabel.setPreferredSize(new Dimension(70,10));
			holderId=new JTextField(20);
			
			pinLabel= new JLabel("Pin");
			pinLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			pinLabel.setPreferredSize(new Dimension(70,10));
			pin=new JTextField(4);
			
			
			numbersLabel= new JLabel("Numbers");
			numbersLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			numbersLabel.setPreferredSize(new Dimension(70,10));
			numbers= new JFormattedTextField();
			numbers.setPreferredSize(new Dimension(224,20));
			try {
				/* SETS FORMAT FOR NUMBERS INPUT*/
				numbers.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("####-####-####-####")));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//numbers.setDocument(new JTextFieldLimit(20));
			
			JPanel holderp = new JPanel(new FlowLayout(FlowLayout.LEFT)),pinp=new JPanel(new FlowLayout(FlowLayout.LEFT)),numbersp=new JPanel(new FlowLayout(FlowLayout.LEFT));
			
			holderp.setPreferredSize(new Dimension(400,30));
			pinp.setPreferredSize(new Dimension(400,30));
			numbersp.setPreferredSize(new Dimension(400,30));
			
			holderp.add(holderIdLabel);
			holderp.add(holderId);
			top.add(holderp);
			
			pinp.add(pinLabel);
			pinp.add(pin);
			top.add(pinp);
			numbersp.add(numbersLabel);
			numbersp.add(numbers);
			top.add(numbersp);
			
			
			back = CustomButtonUI("Back");
			back.setPreferredSize(new Dimension(120,20));
			back.setHorizontalAlignment(SwingConstants.CENTER);
			back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
							dispose();
							GUI_Create_Menu createmenu=new GUI_Create_Menu();
							createmenu.setVisible(true);
							createmenu.setLocationRelativeTo(null);
							URL iconURL = null;
							iconURL= Main.class.getResource("atm.png");
							ImageIcon icon = new ImageIcon(iconURL);
							createmenu.setIconImage(icon.getImage());
				}
			});
			
			submit = CustomButtonUI("Submit");
			submit.setPreferredSize(new Dimension(120,20));
			submit.setHorizontalAlignment(SwingConstants.CENTER);
			submit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String cardnum=numbers.getText();
					String tip=typelist.getSelectedItem().toString();
					int holderid=Integer.parseInt(holderId.getText());
					int pinin=Integer.parseInt(pin.getText());
					if(tip=="Business") {
						int companyid=Integer.parseInt(companyId.getText());
						double spendlimit=Double.parseDouble(spendlim.getText());
						//System.out.println(companyid);
						//System.out.println(spendlimit);
						Actions.create_card_business(holderid,pinin,cardnum,tip,companyid,spendlimit);
					}
					else if(tip=="Student") {
						String universitystring=university.getText();
						Actions.create_card_student(holderid, pinin, cardnum, tip, universitystring);
					}
					
				}
			});
			
			reset = CustomButtonUI("Reset");
			reset.setPreferredSize(new Dimension(120,20));
			reset.setHorizontalAlignment(SwingConstants.CENTER);
			reset.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
							dispose();
							GUI_Create_Options createoptions=new GUI_Create_Options(1);
							createoptions.setVisible(true);
							createoptions.setLocationRelativeTo(null);
							URL iconURL = null;
							iconURL= Main.class.getResource("atm.png");
							ImageIcon icon = new ImageIcon(iconURL);
							createoptions.setIconImage(icon.getImage());
							createoptions.setTitle("New card");
				}
			});
			
			bottombut.add(back);
			bottombut.add(submit);
			bottombut.add(reset);
			
		} // END OF IF???
		else if(i==2) {
			userLabel= new JLabel("Holder name");
			userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			userLabel.setPreferredSize(new Dimension(80,10));
			user=new JTextField(20);
			
			top.add(userLabel);
			top.add(user);
			
			back = CustomButtonUI("Back");
			back.setPreferredSize(new Dimension(120,20));
			back.setHorizontalAlignment(SwingConstants.CENTER);
			back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
							dispose();
							GUI_Create_Menu createmenu=new GUI_Create_Menu();
							createmenu.setVisible(true);
							createmenu.setLocationRelativeTo(null);
							URL iconURL = null;
							iconURL= Main.class.getResource("atm.png");
							ImageIcon icon = new ImageIcon(iconURL);
							createmenu.setIconImage(icon.getImage());
				}
			});
			
			submit = CustomButtonUI("Submit");
			submit.setPreferredSize(new Dimension(120,20));
			submit.setHorizontalAlignment(SwingConstants.CENTER);
			submit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String username=user.getText();
					JOptionPane.showMessageDialog(user, "The account id is:"+Actions.create_account(username));
					
				}
			});
			
			reset = CustomButtonUI("Reset");
			reset.setPreferredSize(new Dimension(120,20));
			reset.setHorizontalAlignment(SwingConstants.CENTER);
			reset.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
							dispose();
							GUI_Create_Options createoptions=new GUI_Create_Options(2);
							createoptions.setVisible(true);
							createoptions.setLocationRelativeTo(null);
							URL iconURL = null;
							iconURL= Main.class.getResource("atm.png");
							ImageIcon icon = new ImageIcon(iconURL);
							createoptions.setIconImage(icon.getImage());
							createoptions.setTitle("New user");
				}
			});
			
			bottombut.add(back);
			bottombut.add(submit);
			bottombut.add(reset);
		}
		else if(i==3) {
			userLabel= new JLabel("Company name");
			userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			userLabel.setPreferredSize(new Dimension(90,15));
			user=new JTextField(20);
			
			top.add(userLabel);
			top.add(user);
			
			back = CustomButtonUI("Back");
			back.setPreferredSize(new Dimension(120,20));
			back.setHorizontalAlignment(SwingConstants.CENTER);
			back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
							dispose();
							GUI_Create_Menu createmenu=new GUI_Create_Menu();
							createmenu.setVisible(true);
							createmenu.setLocationRelativeTo(null);
							URL iconURL = null;
							iconURL= Main.class.getResource("atm.png");
							ImageIcon icon = new ImageIcon(iconURL);
							createmenu.setIconImage(icon.getImage());
				}
			});
			
			submit = CustomButtonUI("Submit");
			submit.setPreferredSize(new Dimension(120,20));
			submit.setHorizontalAlignment(SwingConstants.CENTER);
			submit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String username=user.getText();
					JOptionPane.showMessageDialog(user, "The company id is:"+Actions.create_company(username));
					
				}
			});
			
			reset = CustomButtonUI("Reset");
			reset.setPreferredSize(new Dimension(120,20));
			reset.setHorizontalAlignment(SwingConstants.CENTER);
			reset.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
							dispose();
							GUI_Create_Options createoptions=new GUI_Create_Options(3);
							createoptions.setVisible(true);
							createoptions.setLocationRelativeTo(null);
							URL iconURL = null;
							iconURL= Main.class.getResource("atm.png");
							ImageIcon icon = new ImageIcon(iconURL);
							createoptions.setIconImage(icon.getImage());
							createoptions.setTitle("New company");
				}
			});
			
			bottombut.add(back);
			bottombut.add(submit);
			bottombut.add(reset);
		}
		
		this.add(main);
		this.add(top);
		this.add(bottom);
		this.add(bottombut);
		
	}
}
