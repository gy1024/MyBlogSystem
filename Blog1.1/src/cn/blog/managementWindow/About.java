package cn.blog.managementWindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class About extends JDialog {

	private static final long serialVersionUID = 1747715513914158610L;
	private final JPanel contentPanel = new JPanel();

	public About() {
		setTitle("关于我们");
		setBounds(100, 100, 227, 167);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("作者：胡家郡");
		lblNewLabel.setBounds(25, 53, 81, 15);
		contentPanel.add(lblNewLabel);
		
		JLabel lblJavase = new JLabel("JavaSE练习项目");
		lblJavase.setBounds(25, 21, 197, 27);
		contentPanel.add(lblJavase);
		
		JLabel lblqqcom = new JLabel("邮箱：354690757@qq.com");
		lblqqcom.setBounds(25, 74, 197, 27);
		contentPanel.add(lblqqcom);
		Toolkit kit = Toolkit.getDefaultToolkit();              //定义工具包  
		Dimension screenSize = kit.getScreenSize();
		setLocation(screenSize.width/2-getWidth()/2, screenSize.height/2-getHeight()/2);
		
	}

}
