package p3proiectir;

import java.awt.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.DocumentFilter;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;


/**
 * GUI Class containing everything for the Login screen
 */
public class GUI extends JFrame{
	/*Class to set fieldlimit*/
	class JTextFieldLimit extends PlainDocument {
		   private int limit;
		   
		   JTextFieldLimit(int limit) {
		      super();
		      this.limit = limit;
		   }
		   JTextFieldLimit(int limit, boolean upper) {
		      super();
		      this.limit = limit;
		   }
		   public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		      if (str == null)
		         return;
		      if ((getLength() + str.length()) <= limit) {
		         super.insertString(offset, str, attr);
		      }
		   }
		}
	/**
	 * Creates a customized JButton and returns it
	 * <p>
	 * The method makes a new JButton with the input taken
	 * from the parameter, then returns it.
	 * <p>
	 * It is used to avoid duplicate code for customizing buttons.
	 *
	 * @param  input String that will be the button's text
	 * @return A new customized JButton
	 */
	public JButton CustomButtonUI(String input) {
		JButton newbutton=new JButton(input);
		newbutton.setPreferredSize(new Dimension(40,20));
		newbutton.setBackground(new Color(59, 89, 182));
		newbutton.setForeground(Color.WHITE);
		newbutton.setFocusPainted(false);
		newbutton.setFont(new Font("Tahoma", Font.BOLD, 12));
        return newbutton;
	}

	
	JLabel numberLabel,passLabel;
	JTextField number,pass;
	JFormattedTextField numberfil;
	JButton createCard,login;
	
	/**
	 * Constructor for the Login screen
	 */
	public GUI() {
		try { 
			Color backgroundcolor=new Color(189, 190, 197);
			UIManager.put("Panel.background",backgroundcolor);
			UIManager.put("OptionPane.background", backgroundcolor);
			UIManager.put("OptionPane.messagebackground", backgroundcolor);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setSize(400,300);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		JPanel top,middle,middlelower,bottom;
		top = new JPanel(new FlowLayout(FlowLayout.CENTER));
		top.setPreferredSize(new Dimension(250,60));
		//top.setAlignmentX(SwingConstants.CENTER);
		
		JLabel title = new JLabel("<html><span style='color: #3b59b6;'>ATM</span></html>");
		title.setFont (title.getFont().deriveFont(44.0f));
		JLabel version = new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Version 0.85<br>Created by Ovidiu Butiu</html>");
		
		top.add(title);
		top.add(version);
		
		this.add(top);
		
		middle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		middle.setPreferredSize(new Dimension(350,30));
		
		middlelower = new JPanel(new FlowLayout(FlowLayout.LEFT));
		middlelower.setPreferredSize(new Dimension(350,30));
		
		numberLabel = new JLabel("Number");
		numberLabel.setPreferredSize(new Dimension(60,10));
		numberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		passLabel = new JLabel("Pin");
		passLabel.setPreferredSize(new Dimension(60,10));
		passLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//number = new JTextField(20);
		//number.setDocument(new JTextFieldLimit(20));
		numberfil=new JFormattedTextField();
		numberfil.setPreferredSize(new Dimension(135,20));
		try {
			numberfil.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("####-####-####-####")));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		numberfil.setDocument(new JTextFieldLimit(20));
		
		pass = new JTextField(4);
		pass.setDocument(new JTextFieldLimit(4));
		
		middle.add(numberLabel);
		middle.add(numberfil);
		middlelower.add(passLabel);
		middlelower.add(pass);
		
		this.add(middle);
		this.add(middlelower);
		
		bottom = new JPanel(new GridLayout(0,2));
		bottom.setPreferredSize(new Dimension(390,40));
		
		createCard=CustomButtonUI("New card");
		login=CustomButtonUI("Login");
		login.setPreferredSize(new Dimension(40,20));
		
		bottom.add(createCard);
		bottom.add(login);
		
		this.add(bottom);
		
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Card currentCard= (Card) Actions.get_db_info(numberfil.getText(),pass.getText());

					if(currentCard!=null) {
						dispose();
						GUI_Menu bankmenu=new GUI_Menu(currentCard);
						bankmenu.setVisible(true);
						bankmenu.setLocationRelativeTo(null);
						URL iconURL = null;
						iconURL= Main.class.getResource("atm.png");
						ImageIcon icon = new ImageIcon(iconURL);
						bankmenu.setIconImage(icon.getImage());
						bankmenu.setTitle("ATM");
					}
					else {
						// ADD ERROR JFRAME;
	                	JOptionPane.showMessageDialog(login, "Error! Invalid card number or pin.");
					}
				}
				catch(SQLException ev1){
					if(ev1.getClass()==SQLSyntaxErrorException.class)
                	JOptionPane.showMessageDialog(login, "Error! Invalid card number or pin.");
					else
					JOptionPane.showMessageDialog(login, ev1);
				}
			}
		});
		
		createCard.addActionListener(new ActionListener() {
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
		
	}
	
}
