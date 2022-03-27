package p3proiectir;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;

/**
 * Class for the selection menu
 */
public class GUI_Create_Menu extends JFrame {

	
	JButton createCard,createUser,createCompany,back;
	
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
	 * Constructor for the selection menu.
	 */
	public GUI_Create_Menu() {
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
		this.setSize(200,150);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
		main.setPreferredSize(new Dimension(300,150));
		
		createCard=CustomButtonUI("New card");
		createCard.setPreferredSize(new Dimension(40,20));
		createCard.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		createCard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				GUI_Create_Options createcard=new GUI_Create_Options(1);
				createcard.setVisible(true);
				createcard.setLocationRelativeTo(null);
				URL iconURL = null;
				iconURL= Main.class.getResource("atm.png");
				ImageIcon icon = new ImageIcon(iconURL);
				createcard.setIconImage(icon.getImage());
				createcard.setTitle("New card");
            }
		});
		createUser=CustomButtonUI("New user");
		createUser.setPreferredSize(new Dimension(40,20));
		createUser.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		createUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				GUI_Create_Options createcard=new GUI_Create_Options(2);
				createcard.setVisible(true);
				createcard.setLocationRelativeTo(null);
				URL iconURL = null;
				iconURL= Main.class.getResource("atm.png");
				ImageIcon icon = new ImageIcon(iconURL);
				createcard.setIconImage(icon.getImage());
				createcard.setTitle("New user");
            }
		});
		createCompany=CustomButtonUI("New company");
		createCompany.setPreferredSize(new Dimension(40,20));
		createCompany.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		createCompany.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				GUI_Create_Options createcard=new GUI_Create_Options(3);
				createcard.setVisible(true);
				createcard.setLocationRelativeTo(null);
				URL iconURL = null;
				iconURL= Main.class.getResource("atm.png");
				ImageIcon icon = new ImageIcon(iconURL);
				createcard.setIconImage(icon.getImage());
				createcard.setTitle("New company");
            }
		});
		back=CustomButtonUI("Back");
		back.setPreferredSize(new Dimension(40,20));
		back.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		back.addActionListener(new ActionListener() {
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
		
		
		main.add(createCard);
		main.add(createUser);
		main.add(createCompany);
		main.add(back);
		
		this.add(main);
		
		
	}
}
