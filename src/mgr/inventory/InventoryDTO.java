package mgr.inventory;

import java.io.*;

@SuppressWarnings("serial")
public class InventoryDTO implements Serializable {
	private int itemNum;//��ǰ��-key(seq�� ������ �����ؾ� ��)
	private String item;//��ǰ��
	private int price;//���� ����
	private int stock;//���
	
	public InventoryDTO() {}

	public InventoryDTO(int itemNum, String item, int price, int stock) {
		this.itemNum = itemNum;
		this.item = item;
		this.price = price;
		this.stock = stock;}
	
	public int getItemNum() {return itemNum;}
	public void setItemNum(int itemNum) {this.itemNum = itemNum;}
	
	public String getItem() {return item;}
	public void setItem(String item) {this.item = item;}
	
	public int getPrice() {return price;}
	public void setPrice(int price) {this.price = price;}
	
	public int getStock() {return stock;}
	public void setStock(int stock) {this.stock = stock;}
	
	@Override
	public String toString() {//���� ���� ��Ͽ��� �������� ���
		String itemTitle; 
		if (stock<=0) itemTitle ="["+item+"]  "+price+"(��/��) [ǰ��]";//ǰ���ÿ��� ǰ���̶�� ���� �߰�
		else itemTitle ="["+item+"]  "+price+"(��/��)";
		return itemTitle;}

}//InventortyDTO Class
