package mgr;

import java.net.*;
import java.io.*;
import java.util.*;

	//���񼭹���,������
public class MainServer extends Thread {
	private ServerSocket serverSocket;
	private ArrayList<CntSHandler> list;
	
	
	public MainServer(){
		this.start();
	}//������
	
	@Override
	public void run(){
		try{
			serverSocket = new ServerSocket(9555);
			System.out.println("�����غ�Ϸ�...");

			list = new ArrayList<CntSHandler>();
		while(true){
			Socket socket = serverSocket.accept();
			CntSHandler handler = new CntSHandler(socket,list);
			handler.start();//������ ����-������ ����(run())
			list.add(handler);
		}//while
		}catch(IOException e){e.printStackTrace();}
	}//run() �޼ҵ�

}//MainServer class
