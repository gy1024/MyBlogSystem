package cn.blogServer.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import cn.blogServer.entity.DataPackage;
import cn.blogServer.entity.User;
import cn.blogServer.xml.UserXmlMethod;

public class Server {
	private final static int POST = 9573;
	private static UserXmlMethod userXmlMethod;
	static{
		userXmlMethod = UserXmlMethod.ininstance("Users.xml");
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		System.out.println("服务器已启动！");
		//保存用户登录信息，用于上传文件时的文件分类保存！
		Map<String,String> map = new HashMap<String,String>();
		@SuppressWarnings("resource")
		ServerSocket ss = new ServerSocket(POST);
		while(true){
			Socket socket = ss.accept();
			socket.setSoTimeout(1000);
			String ip = socket.getInetAddress().getHostAddress();
			//接收数据
			ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
			DataPackage request = (DataPackage)is.readObject();
			socket.shutdownInput();
			Object req = request.getObj();
			System.out.println(ip+"--"+request.getRequest());
			DataPackage response = null;
			switch(request.getRequest()){
			//登录
				case"login":
					User login = userXmlMethod.searchUser(((User)req).getUserName());
					if(login!=null){
						if(login.getUserPwd().equals(((User)req).getUserPwd())){
							map.put(ip,((User)req).getUserName());
							response = new DataPackage("succeed", null);
						}else{
							response = new DataPackage("passworderr", null);
						}
					}else{
						response = new DataPackage("none", null);;
					}
					break;
			//注册
				case"regist":
					Integer id = (int)(Math.random()*90000)+10000;
					System.out.println(((User)req).getUserName());
					User regist = userXmlMethod.searchUser(((User)req).getUserName());
					if(regist!=null){
						response = new DataPackage("fail",null);
					}else{
						userXmlMethod.addUser(new User(id.toString(), ((User)req).getUserName(), ((User)req).getUserPwd()));
						response = new DataPackage("succeed",null);
					}
					break;
				case"upload":
					FileOutputStream fos = new FileOutputStream(new File("userdata/"+map.get(ip)+".xml"));
					fos.write(((byte[])req));
					fos.close();
					response = new DataPackage("succeed",null);
					break;
				case"download":
					File file = null;
					if(new File("userdata/"+((User)req).getUserName()+".xml").exists()){
						file = new File("userdata/"+((User)req).getUserName()+".xml");
					}else{
						file = new File("default.xml");
					}
					FileInputStream fis = new FileInputStream(file);
					byte[] b = new byte[fis.available()];
					fis.read(b);
					response = new DataPackage("succeed",b);
					break;
			}
			//发送数据
			ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(response);
			socket.shutdownOutput();
			socket.close();
		}
	}
}
