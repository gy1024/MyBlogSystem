package cn.blog.managementWindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import cn.blog.httpserver.HTTPServer;
import cn.blog.httpserver.HTTPServer.ContextHandler;
import cn.blog.httpserver.HTTPServer.FileContextHandler;
import cn.blog.httpserver.HTTPServer.Request;
import cn.blog.httpserver.HTTPServer.Response;
import cn.blog.httpserver.HTTPServer.VirtualHost;
import cn.blog.changeHtml.ChangeHtml;
import cn.blog.entity.Articel;
import cn.blog.overrideSwing.MyTable;
import cn.blog.xmlutil.FunctionXML;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class MainWindow {
	private JFrame frame;
	private Editor editor = null;
	public static String content = "";
	private static String[] columnNames = {"编号","标题"};
	private static DefaultTableModel tableModel;
	private static FunctionXML functionXml = FunctionXML.ininstance("data.xml");
	private static List<Articel> list;
	private static Object[][] data; //表格中数据
	private JTable table;
	private HTTPServer server;//服务器
	private boolean flag = true;
	private JLabel displayArea;//动态显示时间
	private static String date;//时间
	private JPopupMenu menu;//邮件菜单
	private ClickEvent ce;//点击事件处理        ClickEvent是MainWindow的内部类方便访问静态值
	JScrollPane jsp;
	static{//初始化博客列表
		refreshList();
		Date d = new Date();
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy年MM月dd日");
		date = formatDate.format(d);
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public MainWindow() {
		initialize();
		ce = new ClickEvent();
		ce.RightMouseTable();
		ce.configTimeArea();
	}
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("个人博客管理系统");
		frame.setBounds(100, 100, 400, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		UIManager.put("Button.font", new java.awt.Font("宋体", 0, 12));
		UIManager.put("Label.font", new java.awt.Font("宋体", 0, 11));
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (InstantiationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Toolkit kit = Toolkit.getDefaultToolkit();              //定义工具包  
		Dimension screenSize = kit.getScreenSize();             //获取屏幕的尺寸  
		//窗口居中显示
		frame.setIconImage(new ImageIcon("logo.png").getImage());
		frame.setLocation(screenSize.width/2-frame.getWidth()/2, screenSize.height/2-frame.getHeight()/2);
		frame.getContentPane().setLayout(null);
		
		JButton button = new JButton("写博客");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ce.addArticel();
			}
		});
		button.setBounds(245, 10, 139, 32);
		frame.getContentPane().add(button);
		
		JButton btnNewButton = new JButton("删除选中博客");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ce.delete();
			}
		});
		btnNewButton.setBounds(245, 94, 139, 32);
		frame.getContentPane().add(btnNewButton);
		
		JButton button_1 = new JButton("修改选中博客");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ce.midify();
			}
		});
		button_1.setBounds(245, 52, 139, 32);
		frame.getContentPane().add(button_1);
		
		JButton button_3 = new JButton("启动博客服务器");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ce.changeHtml();
				ce.runHttpSever();
			}
		});
		button_3.setBounds(245, 136, 139, 32);
		frame.getContentPane().add(button_3);
		
		JButton button_4 = new JButton("关闭博客服务器");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ce.closeHttpServer();
			}
		});
		button_4.setBounds(245, 178, 139, 32);
		frame.getContentPane().add(button_4);
		JLabel lblNewLabel = new JLabel("友情提示：");
		lblNewLabel.setBounds(245, 258, 144, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JButton button_5 = new JButton("关于我们");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 About about = new About();
				 about.setVisible(true);
			}
		});
		button_5.setBounds(245, 220, 139, 32);
		frame.getContentPane().add(button_5);
		
		JLabel lbln = new JLabel("启动服务器后您的访问地址");
		lbln.setBounds(245, 278, 144, 15);
		frame.getContentPane().add(lbln);

		JLabel label = null;
		try {
			label = new JLabel(InetAddress.getLocalHost().getHostAddress()+":8888");
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		label.setBounds(256, 303, 122, 15);
		frame.getContentPane().add(label);
		
		JLabel lblVerson = new JLabel("Verson 1.2");
		lblVerson.setBounds(312, 377, 68, 15);
		lblVerson.setForeground(Color.RED);
		frame.getContentPane().add(lblVerson);
		
		JLabel lblBy = new JLabel("By 胡家郡、王帅、孔斐");
		lblBy.setForeground(Color.RED);
		lblBy.setBounds(245, 353, 156, 15);
		frame.getContentPane().add(lblBy);
		tableModel = new DefaultTableModel(data, columnNames);
		table = new MyTable(tableModel);
		//表格被双击事件
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2&&e.getButton()==MouseEvent.BUTTON1){
					ce.midify();
				}
			}
		});
		jsp= new JScrollPane(table);
		frame.getContentPane().add(jsp);
		jsp.setBounds(0, 0, 234, 422);
		
		displayArea = new JLabel();
		displayArea.setBounds(245, 402, 139, 15);
		displayArea.setFont(new Font("黑体",Font.BOLD,11));
		frame.getContentPane().add(displayArea);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
	    table.getColumnModel().getColumn(1).setPreferredWidth(184);
	}
	private static void refreshList(){ //数据初始化的方法
		list = functionXml.getArticelList();
		Iterator<Articel> ite = list.iterator();
		String[][] arr = new String[list.size()][2];
		int count = 0;
		while(ite.hasNext()){
			Articel articel = ite.next();
			arr[count][0] = articel.getId();
			arr[count][1] = articel.getTitle();
			count++;
		}
		data = arr;
	}
	//点击事件处理的内部类
	class ClickEvent{
		//右键选中JScrollPane弹出添加菜单
		public void RightMouseTable(){
			JMenuItem mAdd,mRun,mStop;
			JMenuItem mMidify, mDelete;
	        menu = new JPopupMenu();  
	        mMidify = new JMenuItem("修改");  
	        menu.add(mMidify);  
	        mDelete = new JMenuItem("删除");  
	        menu.add(mDelete);
	        mAdd = new JMenuItem("添加博客");  
	        menu.add(mAdd);   
	        mRun = new JMenuItem("开启服务器");  
	        menu.add(mRun);
	        mStop = new JMenuItem("关闭服务器");  
	        menu.add(mStop);
	        table.addMouseListener(new MouseAdapter() {  
		        public void mouseClicked(MouseEvent e) {  
		        	if (e.getButton()==MouseEvent.BUTTON3) {  
		        		//弹出右键菜单  
		        		menu.show(table, e.getX(), e.getY()); 
		        	}  
		        }     
	        });
	        mAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addArticel();
				}
			});
	        mRun.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					changeHtml();
					runHttpSever();
				}
			});
	        mStop.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					closeHttpServer();
				}
			});
	        mMidify.addActionListener(new ActionListener() {     
	        	public void actionPerformed(ActionEvent e) {  
	        		midify();
	         }  
	        });  
	        mDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					delete();
				}
			});
		}
		//刷新时间
		public void configTimeArea() {  
	        Timer tmr = new Timer();  
	        tmr.scheduleAtFixedRate(new JLabelTimerTask(), new Date(), 1000);  
	    }
		//关闭服务器
		public void closeHttpServer(){
			if(flag){
				JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
						"服务器没有启动！", "系统提示", JOptionPane.ERROR_MESSAGE); 
			}else{
				server.stop();
				flag = true;
				JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
						"服务器已关闭!", "系统提示", JOptionPane.WARNING_MESSAGE);
			}
		}
		//启动HTTP服务器
		public void runHttpSever(){
			if(flag){
				try {
					File dir = new File("webroot");
					int port = 8888;
			        server = new HTTPServer(port);
			        VirtualHost host = server.getVirtualHost(null);
			        host.setAllowGeneratedIndex(true); 
			        host.addContext("/", new FileContextHandler(dir));
			        host.addContext("/api/time", new ContextHandler() {
			            public int serve(Request req, Response resp) throws IOException {
			                long now = System.currentTimeMillis();
			                resp.getHeaders().add("Content-Type", "text/plain");
			                resp.send(200, String.format("%tF %<tT", now));
			                return 0;
			            }
			        });
			        server.start();
			        System.out.println("HTTPServer正在监听端口 " + port);
			        JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
							"服务器已开启！\n您可以访问http://127.0.0.1:8888浏览你的网站", "系统提示", JOptionPane.WARNING_MESSAGE);
			        flag = false;
			    } catch (Exception e1) {
			        System.err.println("error: " + e1);
			    }
			}else{
				JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
						"请不要重复启动服务器！", "系统提示", JOptionPane.ERROR_MESSAGE); 
			}
		}
		//内容转换成html
		public void changeHtml(){
			List<Articel> list = functionXml.getArticelList();
			ChangeHtml ch = new ChangeHtml(list);
			ch.batchChange();
		}
		//添加文章（空白不保存）
		public void addArticel(){
			if(editor==null){
				editor = new Editor();
			}else{
				editor=null;
				editor = new Editor();
			}
			editor.setVisible(true);
			editor.addWindowListener(new WindowListener(){
				public void windowClosed(WindowEvent we){}
				public void windowOpened(WindowEvent e) {}
				public void windowClosing(WindowEvent e) {}
				public void windowIconified(WindowEvent e) {}
				public void windowDeiconified(WindowEvent e) {}
				public void windowActivated(WindowEvent e) {}
				public void windowDeactivated(WindowEvent e) {/*窗口被禁止时调用*/
					if(editor.getTitle().length()!=0||editor.getContent().length()!=0){
						String title = editor.getTitle();
						String content = editor.getContent();
						Articel articel = new Articel(title, content, date );
						System.out.println(articel.toString());
						functionXml.addArticel(articel);
						tableModel.addRow(new Object[]{articel.getId(),articel.getTitle()});
						tableModel.fireTableDataChanged();
						editor.dispose();
						if(editor != null){
							editor = null;
							System.out.println("编辑器已关闭！");
						}
					}
				}
			});
		}
		//删除的方法
		public void delete(){
			int selectTheLine = table.getSelectedRow();
			if(selectTheLine>=0){
				String id = (String) tableModel.getValueAt(selectTheLine, 0);
				Articel articel = functionXml.searchArticel(id);
				if(functionXml.removeArticel(articel)){
					System.out.println("删除成功！");
					tableModel.removeRow(selectTheLine) ;
					tableModel.fireTableDataChanged();
				}else{
					System.out.println("删除出现错误！");
				}
			}else{
				JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
						"请选择您要删除的文章", "系统提示", JOptionPane.ERROR_MESSAGE); 
			}
		}
		//修改的方法
		public void midify(){
			int selectTheLine = table.getSelectedRow();
			if(selectTheLine>=0){
				if(editor==null){
					editor = new Editor();
				}else{
					editor=null;
					editor = new Editor();
				}
				editor.setVisible(true);
				String id = (String) tableModel.getValueAt(selectTheLine, 0);
				Articel articel = functionXml.searchArticel(id);
				editor.setTitle(articel.getTitle());
				editor.setContent(articel.getContent());
				editor.addWindowListener(new WindowListener(){
					public void windowClosed(WindowEvent we){}
					public void windowOpened(WindowEvent e) {}
					public void windowClosing(WindowEvent e) {}
					public void windowIconified(WindowEvent e) {}
					public void windowDeiconified(WindowEvent e) {}
					public void windowActivated(WindowEvent e) {}
					public void windowDeactivated(WindowEvent e) {/*窗口被禁止时调用*/ 
						String title = editor.getTitle();
						articel.setTitle(title);
						articel.setContent(editor.getContent());;
						functionXml.midifArticel(articel);
						tableModel.setValueAt(title, selectTheLine, 1);
						tableModel.fireTableDataChanged();
						editor.dispose();
						if(editor != null){
							editor = null;
							System.out.println("修改窗口已关闭！");
						}
					}
				});
			}else{
				JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
						"请选择您要修改的文章", "系统提示", JOptionPane.ERROR_MESSAGE); 
			}
		}
	}
	class JLabelTimerTask extends TimerTask {  
		private String DEFAULT_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
		private String time;
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DEFAULT_TIME_FORMAT);  
        @Override  
        public void run() {  
            time = dateFormatter.format(Calendar.getInstance().getTime());  
            displayArea.setText(time);  
        }  
    }
}