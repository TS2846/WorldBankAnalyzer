package frontEnd;

import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.swing.JPasswordField;
public class PageLog extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	JFrame frame = new JFrame();
	JLabel labelUsername = new JLabel("Username");
	JLabel labelPassword = new JLabel("Password");
	JTextField userName = new JTextField();
	JPasswordField passWord = new JPasswordField();
	JLabel title = new JLabel("Login");
	JButton button = new JButton("Submit!");
	JLabel message = new JLabel();
	UserLogin ul;
	CountDownLatch loginSignal;
	
	private static PageLog instance;
	
	public static PageLog getInstance() {
		if (instance == null)
			instance = new PageLog();

		return instance;
	}

	private PageLog(){
		ul = new UserLogin();
		labelUsername.setBounds(50, 100, 75, 25);
		labelPassword.setBounds(50, 150, 75, 25);
		
		message.setBounds(125, 250, 250, 35);
		
		userName.setBounds(125, 100, 200, 25);
		passWord.setBounds(125, 150, 200, 25);   
		button.setBounds(125,200,100,25);
		button.addActionListener(this);
		
		
		frame.add(labelUsername);
		frame.add(labelPassword);
		frame.add(message); 
		frame.add(userName);
		frame.add(passWord);
		frame.add(button);
		
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(420, 420);
		frame.setLayout(null);
		frame.setVisible(true);
		
	}
	public void setCDLatch(CountDownLatch loginSignal) {
		this.loginSignal = loginSignal;
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()== button) {
			String userId = userName.getText();
			String userPassword = String.valueOf(passWord.getPassword());
			//String result = obj.getKey("GroupN");
			
			/* if both username and password are correct main gui opens after exiting */
			if(ul.check(userId, userPassword) ) {
				message.setForeground(Color.green);
				message.setText("Login Success");
				loginSignal.countDown();
				try {
					TimeUnit.SECONDS.sleep(2);
					this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				/* here we can call the main UI so when login page exits it presents the main UI*/
			}
//			/*if username is incorrect but password is correct*/
//			else if(userPassword.equals(res) && !(userId.equals("GroupN") && obj.get() )) {
//				message.setForeground(Color.black);
//				message.setText("username not found");
//			}
//			
//			/*if username is incorrect but password is correct*/
//			else if(!(userPassword.equals(res)) && (userId.equals("GroupN") && obj.get())) {
//				message.setForeground(Color.black);
//				message.setText("invalid password");
//			}
			/*if both username and password are incorrect login fails*/
			else {
				message.setForeground(Color.black);
				message.setText("Login Failed");
				}
			
		}		
	}

}
