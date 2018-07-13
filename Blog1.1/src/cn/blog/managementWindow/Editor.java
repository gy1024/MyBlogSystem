package cn.blog.managementWindow;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;

import javax.swing.JEditorPane;
import java.awt.Font;


public class Editor extends JDialog {
	private static final long serialVersionUID = -7333026510770564954L;
	JFormattedTextField formattedTextField = null;
	JEditorPane editorPane = null;
	public Editor() {
		setBounds(100, 100, 700, 450);
		getContentPane().setLayout(null);
		JLabel label = new JLabel("标题：");
		label.setBounds(19, 9, 36, 31);
		getContentPane().add(label);
		
		formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(65, 10, 450, 30);
		getContentPane().add(formattedTextField);
		
		editorPane = new JEditorPane();
		editorPane.setBounds(10, 50, 664, 352);
		getContentPane().add(editorPane);
		
		JLabel lblNewLabel = new JLabel("关闭窗口自动保存");
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 17));
		lblNewLabel.setBounds(525, 9, 149, 31);
		getContentPane().add(lblNewLabel);
	}
	public void setTitle(String value){
		formattedTextField.setValue(value);
	}
	public void setContent(String value){
		editorPane.setText(value);
	}
	public String getTitle(){
		return formattedTextField.getText();
	}
	public String getContent(){
		return editorPane.getText();
	}
}
