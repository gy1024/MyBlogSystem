package cn.blog.managementWindow;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cn.blog.client.Client;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {
	private static final long serialVersionUID = -1954144175231665571L;
	private JPanel contentPane;
	private JFormattedTextField userNamet;
	private JFormattedTextField userName;
	private JFormattedTextField passwordt;
	private JPasswordField password;
	private static Login frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame= new Login();
					frame.setVisible(true);
					frame.setResizable(false);
					ImageIcon background=new ImageIcon("img/bg.jpg");
					JLabel bg = new JLabel(background);
					bg.setBounds(0,-72, frame.getWidth(),frame.getWidth());
					frame.getContentPane().add(bg);
					Toolkit kit = Toolkit.getDefaultToolkit();              //定义工具包  
					Dimension screenSize = kit.getScreenSize();
					frame.setLocation(screenSize.width/2-frame.getWidth()/2, screenSize.height/2-frame.getHeight()/2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setBounds(100, 100, 506, 361);
		setTitle("个人博客系统-登录");
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		JButton btnNewButton = new JButton("登录");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userName!=null&&password!=null){
					String name = userName.getText();
					String pwd = new String(password.getPassword());
					if(Client.login(name, pwd)){
						Client.download(name,pwd);
						MainWindow win = new MainWindow();
						frame.setVisible(false);
						win.getFrame().setVisible(true);
					}else{
						JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
								"账号或密码错误!请重新输入!", "系统提示", JOptionPane.ERROR_MESSAGE);
						password.setText("");
					}
				}else{
					JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
							"请输入账号密码后再登录!", "系统提示", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(120, 261, 93, 33);
		contentPane.add(btnNewButton);
		
		JButton button = new JButton("注册");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register re = new Register();
				re.setVisible(true);
			}
		});
		button.setBounds(269, 261, 93, 33);
		contentPane.add(button);
		
		userNamet = new JFormattedTextField("请输入账号");
		userNamet.setBounds(120, 145, 242, 33);
		contentPane.add(userNamet);
		//鼠标按下提示消失
		userNamet.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				getContentPane().remove(userNamet);
				userName = new JFormattedTextField();
				userName.setBounds(120, 145, 242, 33);
				getContentPane().add(userName);
			}
		});
		passwordt = new JFormattedTextField("请输入密码");
		passwordt.setBounds(120, 202, 242, 33);
		contentPane.add(passwordt);
		

		passwordt.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				getContentPane().remove(passwordt);
				password = new JPasswordField();
				password.setBounds(120, 202, 242, 33);
				getContentPane().add(password);
			}
		});
		
	}
}
