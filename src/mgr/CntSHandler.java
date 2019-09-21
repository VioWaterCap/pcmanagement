package mgr;


import java.net.*;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;
import javax.swing.*;

import cnt.CntServer;
import mgr.InfoDTO;
import mgr.InfoMsg;
import mgr.CntSHandler;
import mgr.sales.SalesInfoDAO;
import mgr.sales.SalesInfoDTO;

//���� ������
class CntSHandler extends Thread {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private ArrayList<CntSHandler> list;
	private static ArrayList<CntServer> monitorList;
	public CntSHandler(Socket socket, ArrayList<CntSHandler> list){
		try{
			this.socket = socket;
			this.list = list;
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());}
		catch(IOException e){
			System.out.println("Ŭ���̾�Ʈ���� ���ῡ �����Ͽ����ϴ�.");
			e.printStackTrace();
			System.exit(0);}
	}//������
	
	@Override
	public void run(){
		//�޴� ��
		InfoDTO infodto = null;
		String userName = null;
		String itemName = null;
		String message = null;
		int itemNum=0;
		int itemMnt=0;

		
		while(true){
			try{
				infodto = (InfoDTO)ois.readObject();
				//monitorList.add(infodto.getCntServer());���� �߻�
				if(infodto.getCommand()==InfoMsg.EXIT){
					//���ҿ�ݰ� ������� �����ϴ� �޼ҵ� �ʿ�
					InfoDTO sendDTO = new InfoDTO();
					sendDTO.setUserID(infodto.getUserID());
					sendDTO.setUserName(infodto.getUserName());
					sendDTO.setCommand(InfoMsg.EXIT);
					oos.writeObject(sendDTO);
					oos.flush();
					ois.close();
					oos.close();
					socket.close();
					
					String exitAccepted = "["+infodto.getUserID()+"]���� PC ����� �����Ͽ� �α׾ƿ��ϼ̽��ϴ�.";
					JOptionPane.showMessageDialog(null,exitAccepted,"[��� ���� �˸�]",JOptionPane.INFORMATION_MESSAGE);
					
					break;}//exit �ڸǵ�
				
				else if(infodto.getCommand()==InfoMsg.ORDER) {//�ֹ� ����
					itemNum = infodto.getItemNum();
					userName = infodto.getUserName();
					itemName = infodto.getItemName();
					itemMnt = infodto.getItemMnt();
					message = infodto.getMessage();
					
					String orderAccepted = "["+userName+"]���� [��ǰ��ȣ]"+itemNum+" [��ǰ��]"+itemName+"�� "
											+itemMnt+"�� �ֹ��ϼ̽��ϴ�.\n[�߰����ǻ���]"+message;
					JOptionPane.showMessageDialog(null,orderAccepted,"[��ǰ �ֹ�]",JOptionPane.INFORMATION_MESSAGE);
					
					deliverConfirm(infodto);//Ȯ�� ������ ���߹���
					}	
					else if(infodto.getCommand()==InfoMsg.SEND) {					
					JOptionPane.showMessageDialog(null,"["+infodto.getUserName()+"]"+infodto.getMessage(),"[�޼��� ����]",JOptionPane.INFORMATION_MESSAGE);}
						try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
				}//try
		
			catch(ClassNotFoundException e){e.printStackTrace();}
			catch(IOException io){io.printStackTrace();}
		}//while
	}//run	

	public void deliverConfirm(InfoDTO infodto) {//commit�ϱ� ���� ���� Ȯ�� �۾�-���ݿ� �� û��
		InfoDTO sendDTO = infodto;
		sendDTO.setCommand(InfoMsg.DELIVERED);//Ŀ�ǵ常 �ٲ㼭 ��ȯ
		broadcast(sendDTO);
		updateAccount(infodto);//�Ż� �ݿ�
		}//deliverConfirm() �޼ҵ�

	public void updateAccount(InfoDTO infodto) {
		SalesInfoDAO dao = SalesInfoDAO.getInstance();
		int transactionNum= dao.getSeq();//�ŷ���ȣ ȹ��
		
		InfoDTO infoDTO = infodto;
		SalesInfoDTO salesInfoDTO = new SalesInfoDTO();
		
		salesInfoDTO.setTransactionNum(transactionNum);//�ŷ���ȣ
		salesInfoDTO.setProductNum(infoDTO.getItemNum());//��ǰ��ȣ
		salesInfoDTO.setProductMnt(infoDTO.getItemMnt());//��ǰ ����
		salesInfoDTO.setProductName(infoDTO.getItemName());//��ǰ�̸�
		salesInfoDTO.setRevenue(infoDTO.getTotCost());//�Ǵ� �Ż��
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");//���ó�¥ ����+String format
		salesInfoDTO.setSaleDate(sdf.format(today)+"");
		salesInfoDTO.setIsPaid(false);
		salesInfoDTO.setPerchaseID(infodto.getUserID());
		dao.updatingTransaction(salesInfoDTO);

	}
	
	public void broadcast(InfoDTO sendDTO){//�ϴ� �Ѹ��� catch��.
		try{
			for(CntSHandler data : list){
				data.oos.writeObject(sendDTO);
				data.oos.flush();
			}//list�� �Ѹ���
		}catch(IOException e){
			e.printStackTrace();}
	}//broadcast �Լ�

	public static ArrayList<CntServer> getMonitorList() {
		return monitorList;
	}
	
	
}
