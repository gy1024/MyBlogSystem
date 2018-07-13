package cn.blog.client;

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
	public static boolean login(String userName,String userPwd){
		//建立连接
		Socket socket = null;
		DataPackage response = null;
		try {
			socket = new Socket(HOST,POST);
			//创建数据包裹
			DataPackage request = new DataPackage("login",new User(userName, userPwd));
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
		if(response.getRequest().equals("succeed")){
			return true;
		}else{
			return false;
		}
	}
	public static boolean regist(String userName,String userPwd){
		Socket socket = null;
		DataPackage response = null;
		try {
			socket = new Socket(HOST,POST);
			//创建数据包裹
			DataPackage request = new DataPackage("regist",new User(userName, userPwd));
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
		if(response.getRequest().equals("succeed")){
			return true;
		}else{
			return false;
		}
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
	public static void download(String userName,String userPwd){
		Socket socket = null;
		DataPackage response = null;
		try {
			socket = new Socket(HOST,POST);
			//创建数据包裹
			DataPackage request = new DataPackage("download",new User(userName, userPwd));
			//发送数据
			ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(request);
			os.flush();//刷新缓冲区
			socket.shutdownOutput();
			//接收数据
			ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
			response = (DataPackage)is.readObject();
			socket.shutdownInput();
			FileOutputStream fos = new FileOutputStream(new File("data.xml"));
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
