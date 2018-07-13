package TestClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import cn.blogServer.entity.DataPackage;
import cn.blogServer.entity.User;

public class Client {
	private final static String HOST = "192.168.41.199";
	private final static int POST = 9573;
	public static void main(String[] args){
//		login();
//		regist();
//		upload();
		download();
	}
	public static void login(){
		//建立连接
		Socket socket = null;
		DataPackage response = null;
		try {
			socket = new Socket(HOST,POST);
			//创建数据包裹
			DataPackage request = new DataPackage("login",new User("admin", "admin"));
			//发送数据
			ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(request);
			os.flush();//刷新缓冲区
			socket.shutdownOutput();
			//接收数据
			ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
			response = (DataPackage)is.readObject();
			socket.shutdownInput();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(response.getRequest());
	}
	public static void regist(){
		Socket socket = null;
		DataPackage response = null;
		try {
			socket = new Socket(HOST,POST);
			//创建数据包裹
			DataPackage request = new DataPackage("regist",new User("hujiajun", "hujiajun"));
			//发送数据
			ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(request);
			os.flush();//刷新缓冲区
			socket.shutdownOutput();
			//接收数据
			ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
			response = (DataPackage)is.readObject();
			socket.shutdownInput();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(response.getRequest());
	}
	public static void upload(){
		Socket socket = null;
		DataPackage response = null;
		try {
			socket = new Socket(HOST,POST);
			//创建数据包裹
			FileInputStream fis = new FileInputStream(new File("data.xml"));
			byte[] b = new byte[fis.available()];
			fis.read(b);
			fis.close();
			DataPackage request = new DataPackage("upload",b);
			//发送数据
			ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(request);
			os.flush();//刷新缓冲区
			socket.shutdownOutput();
			//接收数据
			ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
			response = (DataPackage)is.readObject();
			socket.shutdownInput();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(response.getRequest());
	}
	public static void download(){
		Socket socket = null;
		DataPackage response = null;
		try {
			socket = new Socket(HOST,POST);
			//创建数据包裹
			DataPackage request = new DataPackage("download",new User("hujiajun", "hujiajun"));
			//发送数据
			ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(request);
			os.flush();//刷新缓冲区
			socket.shutdownOutput();
			//接收数据
			ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
			response = (DataPackage)is.readObject();
			socket.shutdownInput();
			FileOutputStream fos = new FileOutputStream(new File("cs.xml"));
			fos.write((byte[])response.getObj());
			fos.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(response.getRequest());
	}
}
