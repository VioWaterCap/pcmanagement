package cnt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import member.bean.MemberDTO;
import mgr.InfoDTO;
import mgr.InfoMsg;
import mgr.inventory.InventoryDAO;

@SuppressWarnings("serial")
public class CntServer extends JPanel implements Runnable {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private String serverIP="172.30.1.6";//�� pc���� �ٲ� ���� IP
	private String userID;//����ID
	private String userName;

	private static ArrayList<CntServer> monitorList = new ArrayList<CntServer>();
	
	public static CntServer getlogin(MemberDTO memberDTO){
		CntServer monitor=null;
		monitor = new CntServer(memberDTO); 
		monitorList.add(monitor);
		new WorkingCntFrame(memberDTO);//�α���ȭ�� ����
		return monitor;
	}//���� ������
	
	public static CntServer getInstance(MemberDTO dto) {
		CntServer cnt = null;
		for(CntServer user : monitorList) {if(user.userID.equals(dto.getId())) cnt = user;}
		return cnt;
	}//'THE' CntServer ȣ��
	
	public CntServer(MemberDTO memberDTO) {
		MemberDTO memberdto = memberDTO;
		userID = memberdto.getId();
		userName = memberdto.getUserName();
	}//�⺻ ������
	
	public void service(){		
		try{
			socket = new Socket(serverIP, 9555);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

		}catch(UnknownHostException e){
			System.out.println("������ ã�� �� �����ϴ�");
			e.printStackTrace();
			System.exit(0);
		}catch(IOException e){
			System.out.println("������ ������ �ȵǾ����ϴ�");
			e.printStackTrace();
			System.exit(0);
		}

		Thread t = new Thread(this);//������ ����
		t.start();//������ ����

	}//service()

	public void getOrder(InfoDTO infodto) {
		InfoDTO dto = infodto;
		dto.setUserID(userID);
		dto.setUserName(userName);
		dto.setCommand(InfoMsg.ORDER);
		try{
			oos.writeObject(dto);
			oos.flush();
		}catch(IOException io){
			io.printStackTrace();
		}
	}//getOrder() �޼ҵ� : PC�ڸ��� ȸ��/��ȸ�� ���θ� �߰��ؼ� managerServer�� �����Ѵ�.
	
	public void getExit(InfoDTO infodto) {
		InfoDTO dto = infodto;
		dto.setUserID(userID);
		dto.setUserName(userName);
		dto.setCommand(InfoMsg.EXIT);
		try{
			oos.writeObject(dto);
			oos.flush();
		}catch(IOException io){
			io.printStackTrace();
		}
	}//getExit() �޼ҵ� : PC���� �� �������� ���� ���� ��û.
	@Override
	public void run(){
		//�޴� ��
		InfoDTO dto=null;

		while(true){
			try{
				dto = (InfoDTO)ois.readObject();//�����κ��� ���� �޼��� �б�
					//��ü �޼���
				if(dto.getCommand()==InfoMsg.NOTICE) {//��ü ����-��ΰ� ������.
					String notice ="[����] "+dto.getMessage();
					JOptionPane.showMessageDialog(null,notice,"[�����ڷκ����� �޼���]",JOptionPane.INFORMATION_MESSAGE);}
					//�Ϲ� �޼���
				else if(!dto.getUserID().equals(userID)) continue;//���� �޼����� ���� �ʴ´�.
				else{
					if(dto.getCommand()==InfoMsg.EXIT){
						try {oos.close();
						ois.close();//���� �ٲ�
						socket.close();}
						catch (IOException | NullPointerException e) {e.printStackTrace();}	
						break;}

					else if(dto.getCommand()==InfoMsg.DELIVERED) {//�޼��� ���Ž� ��� �ݿ�-���������� �޼����� �����ϸ� ��ǰ�� ������ ������ �����ϰ�, ��� �ݿ��Ѵ�. 
						int itemNum = dto.getItemNum();
						int itemMnt = dto.getItemMnt();
						InventoryDAO dao = InventoryDAO.getInstance();
						dao.takeInventoryOrder(itemNum,itemMnt);}}}// Ȯ���ϸ� DB �ݿ�
			catch(IOException e){e.printStackTrace();}
			catch(ClassNotFoundException e){e.printStackTrace();}

		}//while
		System.exit(0);
	}//run()
	public void sendMessage(InfoDTO infodto) {//****************
		InfoDTO dto = infodto;
		dto.setUserName(userName);
		dto.setCommand(InfoMsg.SEND);
		dto.setMessage(infodto.getMessage());
		try{
			oos.writeObject(dto);
			oos.flush();
		}catch(IOException io){
			io.printStackTrace();
		}
	}
	
	public void sendMonitorList() {
		for(CntServer data : monitorList) {
			InfoDTO dto = new InfoDTO();
			dto.setCntServer(data);
			try {
				oos.writeObject(data);
				oos.flush();
			}catch(IOException io) {io.printStackTrace();}
		}
	}
	
}//Class End
