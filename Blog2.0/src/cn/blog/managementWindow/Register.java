package cn.blog.managementWindow;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cn.blog.client.Client;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Register extends JDialog {
	private static final long serialVersionUID = 6466377931112465453L;
	private JTextField userName;
	private JPasswordField password;
	private JPasswordField confirmPwd;
	/**
	 * Launch the application.
	 */
	public Register() {
		setBounds(100, 100, 302, 259);
		getContentPane().setLayout(null);
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		setLocation(screenSize.width/2-this.getWidth()/2, screenSize.height/2-this.getHeight()/2);
		setResizable(false);
		JLabel label = new JLabel("注   册");
		label.setFont(new Font("宋体",1, 22));
		label.setBounds(99, 10, 95, 37);
		getContentPane().add(label);
		
		JLabel label1 = new JLabel("账号：");
		label1.setBounds(38, 76, 42, 15);
		getContentPane().add(label1);
		
		userName = new JTextField();
		userName.setBounds(90, 73, 164, 20);
		getContentPane().add(userName);
		userName.setColumns(10);
		
		JLabel label_1 = new JLabel("密码：");
		label_1.setBounds(38, 111, 42, 15);
		getContentPane().add(label_1);
		
		password = new JPasswordField();
		password.setColumns(10);
		password.setBounds(90, 108, 164, 20);
		getContentPane().add(password);
		
		JLabel label_2 = new JLabel("确认密码：");
		label_2.setBounds(10, 147, 70, 15);
		getContentPane().add(label_2);
		
		confirmPwd = new JPasswordField();
		confirmPwd.setColumns(10);
		confirmPwd.setBounds(90, 144, 164, 20);
		getContentPane().add(confirmPwd);
		
		JButton btnNewButton = new JButton("注册");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pwd = new String(password.getPassword());
				String confPwd = new String(confirmPwd.getPassword());
				if(!userName.getText().equals("")&&!pwd.equals("")){
					if(!confPwd.equals(pwd)){
						JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
								"两次密码不一样!请重新输入!", "系统提示", JOptionPane.ERROR_MESSAGE);
						password.setText("");
						confirmPwd.setText("");
					}else{
						if(Client.regist(userName.getText(), pwd)){
							JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
									"注册成功！", "系统提示", JOptionPane.WARNING_MESSAGE);
							password.setText("");
							confirmPwd.setText("");
							userName.setText("");
						}else{
							JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
									"帐号以被注册!", "系统提示", JOptionPane.ERROR_MESSAGE);
						}
					}
				}else{
					JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
							"请填写信息进行注册!", "系统提示", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(38, 172, 224, 32);
		getContentPane().add(btnNewButton);
	}
}
