package mgr;
//�ӽ�-�޼����� �������� ����, ������ �޼����� ������

import java.io.Serializable;

import cnt.CntServer;

@SuppressWarnings("serial")
public class InfoDTO implements Serializable {
	private String userName;
	private String userID;//(190331 ����) �ĺ��ڸ� id�� ���������ν� id����+��Ŀ���� ����
	private String itemName;
	private int uPrice,itemNum,itemMnt,totCost,userTime;
	private String message;
	private InfoMsg command;
	private CntServer cntServer;
	//=========GETTER % SETTER==========================
	
	public String getUserName() {return userName;}
	public void setUserName(String userName) {this.userName = userName;}
	
	public String getItemName() {return itemName;}
	public void setItemName(String itemName) {this.itemName = itemName;}
	
	public int getuPrice() {return uPrice;}
	public void setuPrice(int uPrice) {this.uPrice = uPrice;}
	
	public int getItemNum() {return itemNum;}
	public void setItemNum(int itemNum) {this.itemNum = itemNum;}
	
	public int getItemMnt() {return itemMnt;}
	public void setItemMnt(int itemMnt) {this.itemMnt = itemMnt;}
	
	public int getTotCost() {return totCost;}
	public void setTotCost(int totCost) {this.totCost = totCost;}
	
	public int getUserTime() {return userTime;}
	public void setUserTime(int userTime) {this.userTime = userTime;}
	
	public String getMessage() {return message;}
	public void setMessage(String message) {this.message = message;}
	
	public InfoMsg getCommand() {return command;}
	public void setCommand(InfoMsg command) {this.command = command;}
	
	public String getUserID() {return userID;}
	public void setUserID(String userID) {this.userID = userID;}
	
	public CntServer getCntServer() {
		return cntServer;
	}
	public void setCntServer(CntServer cntServer) {
		this.cntServer = cntServer;
	}
	
	
}//InfoDTO class
	