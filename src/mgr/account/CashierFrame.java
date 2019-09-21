package mgr.account;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import member.bean.MemberDTO;
import member.dao.MemberDAO;
import mgr.sales.SalesInfoDAO;
import mgr.sales.SalesInfoDTO;
import mgr.sales.SalesInfoSel;
//�� ���� class
@SuppressWarnings("serial")
public class CashierFrame extends JFrame implements ActionListener {
	private Container con;
	private DefaultListModel<SalesInfoDTO> model;
	private Font labelF,buttonF,textF,textFieldF;
	private JButton payAllB,payPCB,payItemB,cancelB;//��ü ����, pc���ݸ� ����,������
	private JLabel wonL,itemPaymentL,pcPaymentL, totPaymentL, leftPaymentL;
	private JTextField itemPaymentT,pcPaymentT, totPaymentT, leftPaymentT; 
	private JPanel wonJP,paymentLJP,paymentTJP,buttonJP;
	private JList<SalesInfoDTO> list;
	private JScrollPane scroll;
	private MemberDTO memberDTO;//�ӽ�
	public CashierFrame(MemberDTO memberDTO) {//����Ʈ���� ������ �� Ȱ��ȭ �Ǹ�, �� MemberDTO�� ������ �������� �ȴ�.
		this.memberDTO = memberDTO;
		con = getContentPane();
		con.setLayout(null);
		SalesInfoDAO dao = SalesInfoDAO.getInstance();
		
		//1.COMPONENT ����
 		labelF = new Font("Bahnschrift Light Condensed",Font.BOLD,15);
 		buttonF = new Font("Bahnschrift Light Condensed",Font.BOLD,15);
 		textF = new Font("Bahnschrift Light Condensed",Font.PLAIN,18);
 		textFieldF = new Font("Bahnschrift Light Condensed",Font.PLAIN,18);
 		
 			//1-1 ��� �ۼ�
 		model = new DefaultListModel<SalesInfoDTO>();
 		list = new JList<SalesInfoDTO>(model);
 		list.setFont(textFieldF);
			
 			//1-2 �󺧺� �ۼ� 	
 		wonL = new JLabel("(����:   ��)",SwingConstants.RIGHT);
 		wonL.setFont(labelF);
 		itemPaymentL = new JLabel("��ǰ ���ž�",SwingConstants.CENTER);
 		itemPaymentL.setFont(labelF);
 		pcPaymentL = new JLabel("PC�� ����",SwingConstants.CENTER);
 		pcPaymentL.setFont(labelF);
 		totPaymentL = new JLabel("�� �ݾ�",SwingConstants.CENTER);
 		totPaymentL.setFont(labelF);
 		leftPaymentL = new JLabel("�����Ͻ� �ݾ�",SwingConstants.CENTER);
 		leftPaymentL.setFont(labelF);
 		
			//1-3 �ؽ�Ʈ�� �ۼ� 	 	
 		itemPaymentT = new JTextField();
 		itemPaymentT.setFont(textF);
 		int boughtItem =dao.getItemSum(memberDTO,true);
 		itemPaymentT.setText(boughtItem+"");
 		itemPaymentT.setEditable(false);
 		pcPaymentT = new JTextField();
 		pcPaymentT.setFont(textF);
 		int pcPay = memberDTO.getPostPayment();
 		pcPaymentT.setText(pcPay+"");
 		pcPaymentT.setEditable(false);
 		totPaymentT = new JTextField();
 		totPaymentT.setFont(textF);
 		int totPay = boughtItem + pcPay;
 		totPaymentT.setText(totPay+"");
 		totPaymentT.setEditable(false);
 		
 		leftPaymentT = new JTextField();
 		leftPaymentT.setFont(textF);
 		int unPaidItem =dao.getItemSum(memberDTO,false);
 		int unPaidTotal = pcPay + unPaidItem;
 		leftPaymentT.setText(unPaidTotal+"");
 		leftPaymentT.setEditable(false);
 		
 			//1-4 ��ư�� �ۼ� 	 
 		payAllB = new JButton("���ΰ���");
 		payAllB.setFont(buttonF);
 		payPCB = new JButton("PC��ݰ���");//server�� ����� ��쿡�� ��Ȱ��ȭ
 		payPCB.setFont(buttonF);
 		payItemB = new JButton("��ǰ����");//������ ��ǰ�� ������ ��Ȱ��ȭ, server�� ����� ��쵵 ��Ȱ��ȭ
 		payItemB.setFont(buttonF);		
 		cancelB = new JButton("���ư���");
 		cancelB.setFont(buttonF);
 		
 		//2-1. JList�κ� ��ġ+��� �ҷ�����
 		scroll = new JScrollPane(list);
 		scroll.setBounds(10,10,530,250);//540,260
 		con.add(scroll);
 				
 		ArrayList<SalesInfoDTO> uPurchaselist = dao.getPersonPurchase(memberDTO);//�����׸� �ҷ�����
 				
 		for(SalesInfoDTO dto : uPurchaselist) {
 			model.addElement(dto);}//���Ե��� ���� ��쿡�� �߰�
 		
 		//2-2. �󺧺� ��ġ
 		wonJP = new JPanel(new GridLayout(1,1,10,10));
 		wonJP.setBounds(420,280,80,50);//500,330
 		wonJP.add(wonL);
 		con.add(wonJP);
 		
 		paymentLJP= new JPanel(new GridLayout(1,4,10,10));
 		paymentLJP.setBounds(10,330,530,50);//540,380
 		paymentLJP.add(itemPaymentL); 		paymentLJP.add(pcPaymentL);
 		paymentLJP.add(totPaymentL); 		paymentLJP.add(leftPaymentL);
 		con.add(paymentLJP);
 		
 		//2-3. �ؽ�Ʈ�� ��ġ 		
 		paymentTJP= new JPanel(new GridLayout(1,4,10,10));
 		paymentTJP.setBounds(10,380,530,50);//540,420
 		paymentTJP.add(itemPaymentT); 		paymentTJP.add(pcPaymentT);
 		paymentTJP.add(totPaymentT); 		paymentTJP.add(leftPaymentT);	
 		con.add(paymentTJP);
 		
 		//2-3. ��ư�� ��ġ 	 		
 		buttonJP= new JPanel(new GridLayout(1,4,10,10));
 		buttonJP.setBounds(10,450,530,50);//540,500
 		buttonJP.add(payAllB); 		buttonJP.add(payPCB); 		buttonJP.add(payItemB);
 		buttonJP.add(cancelB);		
 		con.add(buttonJP);
 		
 		//3. Frame ����
 		setUndecorated(true);//����â �����
 		setTitle("��� ���");
 		setBounds(450,100,550,550);
 		setResizable(false);
 		setVisible(true);
 		
 		//4. event ����
 		payAllB.addActionListener(this);	payPCB.addActionListener(this);
 		payItemB.addActionListener(this);   cancelB.addActionListener(this);   
 		
 		//5. ���� ����(�ý����� ��÷� üũ�ϰ� ������.)
 		Thread t = new Thread() {
			@Override
			public void run() {
				while(true) {
					if(memberDTO.getPostPayment()==0 || model.isEmpty()) payAllB.setEnabled(false);//�� �� �ϳ��� ���ҽÿ��� ��� ����
					else payAllB.setEnabled(true);
					if(memberDTO.getPostPayment()==0) {payPCB.setEnabled(false);}
					else {payPCB.setEnabled(true);}
					if(model.isEmpty()) payItemB.setEnabled(false);//������ ���� ������ ��� ����.		
				 	else payItemB.setEnabled(true);
				}//while
			}//run();
		};//
		t.start();
 				
		
	}//CashierFrame() ������

	@Override
	public void actionPerformed(ActionEvent e) {
		SalesInfoDAO dao = SalesInfoDAO.getInstance();
		int uPay = Integer.parseInt(leftPaymentT.getText());
		String payMsg;
		int ruPaying;
		if(e.getSource()== payAllB) {
			payMsg = "(��) "+uPay+"(��)�Դϴ�. ���� ���� �����Ͻðڽ��ϱ�?";
			ruPaying = JOptionPane.showConfirmDialog(this,payMsg,"[���� ����]",JOptionPane.OK_CANCEL_OPTION);
			if(ruPaying==JOptionPane.CANCEL_OPTION) {
				JOptionPane.showMessageDialog(this,"���� ȭ������ ���ư��ϴ�.","[���� ���]",JOptionPane.INFORMATION_MESSAGE);
				return;}
			JOptionPane.showMessageDialog(this,"���� ** PC���� �̿����ּż� ����帳�ϴ�. �� ����ּ���~","[���� �Ϸ�]",JOptionPane.INFORMATION_MESSAGE);
			dao.getPaid(memberDTO, SalesInfoSel.TOTALPAY);
			memberDTO.setPostPayment(0); dispose();}//payAll:���� ����
	
		else if(e.getSource() ==payPCB) {
			payMsg = "(��) "+uPay+"(��)�Դϴ�. ���� ���� �����Ͻðڽ��ϱ�?";
			ruPaying = JOptionPane.showConfirmDialog(this,payMsg,"[���� ����]",JOptionPane.OK_CANCEL_OPTION);
			if(ruPaying==JOptionPane.CANCEL_OPTION) {
				JOptionPane.showMessageDialog(this,"���� ȭ������ ���ư��ϴ�.","[���� ���]",JOptionPane.INFORMATION_MESSAGE);
				return;}
			JOptionPane.showMessageDialog(this,"���� ** PC���� �̿����ּż� ����帳�ϴ�. �� ����ּ���~","[���� �Ϸ�]",JOptionPane.INFORMATION_MESSAGE);
			dao.getPaid(memberDTO, SalesInfoSel.PCPAY);
			memberDTO.setPostPayment(0); dispose();}//payPC : PC ��� ����
	
		else if(e.getSource() ==payItemB) {
			if(memberDTO.getTime()*25>= uPay){
				payMsg = "���ұ����� �����Ͻðڽ��ϱ�?";
				ruPaying = JOptionPane.showConfirmDialog(this,payMsg,"[���ұ� ���]",JOptionPane.OK_CANCEL_OPTION);
				if(ruPaying==JOptionPane.OK_OPTION) {
					int time = (memberDTO.getTime()*25 - uPay)/25;
					memberDTO.setTime(time);
					MemberDAO memberDAO = MemberDAO.getInstance();
					memberDAO.setTime(time, memberDTO.getId());
				}
				if(ruPaying==JOptionPane.CANCEL_OPTION) {
					payMsg = "(��) "+uPay+"(��)�Դϴ�. ���� ���� �����Ͻðڽ��ϱ�?";
					ruPaying = JOptionPane.showConfirmDialog(this,payMsg,"[���� ����]",JOptionPane.OK_CANCEL_OPTION);
					if(ruPaying==JOptionPane.CANCEL_OPTION) {
						JOptionPane.showMessageDialog(this,"���� ȭ������ ���ư��ϴ�.","[���� ���]",JOptionPane.INFORMATION_MESSAGE);
						return;}}
				}//���ұ��� ��ǰ��뺸�� ū ��쿡�� ���� ����
				
			else {
				payMsg = "(��) "+uPay+"(��)�Դϴ�. ���� ���� �����Ͻðڽ��ϱ�?";
				ruPaying = JOptionPane.showConfirmDialog(this,payMsg,"[���� ����]",JOptionPane.OK_CANCEL_OPTION);
				if(ruPaying==JOptionPane.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(this,"���� ȭ������ ���ư��ϴ�.","[���� ���]",JOptionPane.INFORMATION_MESSAGE);
					return;}}
			JOptionPane.showMessageDialog(this,"���� ** PC���� �̿����ּż� ����帳�ϴ�. �� ����ּ���~","[���� �Ϸ�]",JOptionPane.INFORMATION_MESSAGE);
			dao.getPaid(memberDTO,SalesInfoSel.ITEMPAY);  dispose();}//payItem : Item ����
		
		else if(e.getSource() ==cancelB) {dispose();}
			
		
	}//actionPerformed �޼ҵ�

	
}//CashierFrame class
