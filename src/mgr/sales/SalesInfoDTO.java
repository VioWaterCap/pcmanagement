package mgr.sales;


public class SalesInfoDTO {
	private int TransactionNum;//seq ����
	private int productNum;//�ĺ��ڵ�
	private String productName;//��ǰ��
	private int productMnt;//��ǰ����
	private int revenue;//�����հ�(����)
	private String saleDate;//��/�� �ջ꿡 ����� �ڷ�
	private boolean isPaid;//���Կ���
	private int totDeposit;
	private String perchaseID;
	
	public int getTransactionNum() {
		return TransactionNum;
	}
	public void setTransactionNum(int transactionNum) {
		TransactionNum = transactionNum;
	}
	public int getProductNum() {
		return productNum;
	}
	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public int getProductMnt() {
		return productMnt;
	}
	public void setProductMnt(int productMnt) {
		this.productMnt = productMnt;
	}
	public int getRevenue() {
		return revenue;
	}
	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	public boolean getIsPaid() {
		return isPaid;
	}
	public void setIsPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	public int getTotDeposit() {
		return totDeposit;
	}
	public void setTotDeposit(int totDeposit) {
		this.totDeposit = totDeposit;
	}	
	
	public String getPerchaseID() {
		return perchaseID;
	}
	public void setPerchaseID(String perchaseID) {
		this.perchaseID = perchaseID;
	}
	@Override
	public String toString() {//���� ���� ��Ͽ��� �������� ���
		String saleInfoTitle; 
		saleInfoTitle ="["+productName+"]  "+productMnt+"(��) : (��)   "+revenue+"(��)";//����ǰ��� ����
		return saleInfoTitle;}

}
