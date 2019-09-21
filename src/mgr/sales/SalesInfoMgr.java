package mgr.sales;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//�Ż� Ȯ�� Ŭ����

@SuppressWarnings("serial")
public class SalesInfoMgr extends JFrame implements ActionListener{
	private JButton totalViewB,todateViewB,monthViewB,nonPaidViewB,cancelB;
	private DefaultTableModel model;
	private JTable tableSale;
	private JPanel tableJP,buttonJP;
	private JScrollPane scroll;
	private ArrayList<SalesInfoDTO> saleInfolist;
	private Vector<String> vector;
	public SalesInfoMgr() {
		Container con = getContentPane();
		con.setLayout(null);

		//1-1.���̺� Ÿ��Ʋ ����
				vector = new Vector<String>();
				vector.addElement("�ŷ���ȣ");
				vector.addElement("��ǰ��ȣ");
				vector.addElement("��ǰ��");
				vector.addElement("�������");
				vector.addElement("�����");
				vector.addElement("��������");
				vector.addElement("���Կ���");
				vector.addElement("������ID");
		//1-2.tableModel ���� 
				model = new DefaultTableModel(vector,0){
					public boolean isCellEditable(int r, int c) {
						return false;//������ ���� �Ұ�
					}
				};
				
		//1-3.JTable ����+ ����
				tableSale = new JTable(model);
				tableSale.getTableHeader().setLayout(new FlowLayout(FlowLayout.CENTER));
				tableSale.getTableHeader().setReorderingAllowed(false);//�÷� �̵�����
				tableSale.getTableHeader().setResizingAllowed(false);//ũ�� ���� �Ұ�
				scroll = new JScrollPane(tableSale);
				
		//1-4. Panel����
				tableJP = new JPanel();
				tableJP.setLayout(new BorderLayout());
				tableJP.add(scroll,BorderLayout.CENTER);
				tableJP.setBounds(20,20,660,380);//680,400
				con.add(tableJP);
				
		//2-1. ��ư ����
				totalViewB = new JButton("��ü");
				todateViewB = new JButton("���ϸŻ�");
				monthViewB = new JButton("����Ż�");
				nonPaidViewB = new JButton("�̰����ŷ�");
				cancelB = new JButton("���ư���");
				
		//2-2. Panel����
				buttonJP = new JPanel(new GridLayout(1,5,20,10));
				buttonJP.setBounds(20,420,660,40);//680,480
				buttonJP.add(totalViewB);	buttonJP.add(todateViewB);	buttonJP.add(monthViewB);
				buttonJP.add(nonPaidViewB); buttonJP.add(cancelB);
				con.add(buttonJP);
		
		//2-3 ���̺� ������ �ҷ�����
				SalesInfoDAO dao = SalesInfoDAO.getInstance();
				saleInfolist = dao.getSalesInfoList(SalesInfoSel.SEARCHALL);
				showView();
				
 		//3. Frame ����
 		setUndecorated(true);//����â �����
 		setTitle("�Ż� ����");
 		setBounds(450,100,700,500);
 		setResizable(false);
 		setVisible(true);
 		
 		//4. Event ����
 		totalViewB.addActionListener(this);
 		todateViewB.addActionListener(this);
 		monthViewB.addActionListener(this); 		
 		nonPaidViewB.addActionListener(this);		
 		cancelB.addActionListener(this);
 }

	public void showView() {//���������� ������ ��ü��ȸ, ���������� ������ �Ϻ� ��ȸ
		model.setRowCount(0);//������ �ʱ�ȭ
		for(SalesInfoDTO dto : saleInfolist){
			Vector<Object> v = new Vector<Object>();
			v.add(dto.getTransactionNum()+"");
			v.add(dto.getProductNum());
			v.add(dto.getProductName());
			v.add(dto.getProductMnt());
			v.add(dto.getRevenue());
			v.add(dto.getSaleDate());
			v.add(dto.getIsPaid());
			v.add(dto.getPerchaseID());
			model.addRow(v);
		}
	}//totalView() �޼ҵ� : ��ü����
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==cancelB) dispose();
		else if(e.getSource()==totalViewB) {
			SalesInfoDAO dao = SalesInfoDAO.getInstance();
			saleInfolist = dao.getSalesInfoList(SalesInfoSel.SEARCHALL);
			showView();}
		else if(e.getSource()==todateViewB)  {
			SalesInfoDAO dao = SalesInfoDAO.getInstance();
			saleInfolist = dao.getSalesInfoList(SalesInfoSel.SEARCHTODAY);
			showView();}
		else if(e.getSource()==monthViewB)  {
			SalesInfoDAO dao = SalesInfoDAO.getInstance();
			saleInfolist = dao.getSalesInfoList(SalesInfoSel.SEARCHMONTH);
			showView();}
		else if(e.getSource()==nonPaidViewB)  {
			SalesInfoDAO dao = SalesInfoDAO.getInstance();
			saleInfolist = dao.getSalesInfoList(SalesInfoSel.SEARCHNONPAID);
			showView();}
		
		
	}

	

}
