package cn.blog.overrideSwing;
 
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
 
 
public class RePanel extends JPanel{
	private static final long serialVersionUID = 5964502054570996474L;

	protected void paintComponent(Graphics g){
        super.paintComponent(g);                             
        ImageIcon image=new ImageIcon(getClass().getResource("/Images/project-2.jpg"));        //获取图像
        image.setImage(image.getImage().getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_FAST)); //调整图像的分辨率以适应容器     
        image.paintIcon(this, g,0, 0);
    }          
}