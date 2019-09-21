package mgr.member;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import member.bean.MemberDTO;
import member.dao.MemberDAO;

@SuppressWarnings("serial")
public class ClientMgtFrame extends JFrame implements ActionListener {
	//private ArrayList<MemberDTO> list;
	private JButton modifyCntB, deleteCntB, nonMemB;
	private DefaultTableModel model;
	private JTable tableCnt;
	private JPanel tableJP,buttonJP;
	private JScrollPane scroll;
	private Vector<String> vector;
	
	public ClientMgtFrame() {
		
		
		Container con = getContentPane();
		con.setLayout(null);
		
		//1-1.���̺� Ÿ��Ʋ ����
		vector = new Vector<String>();
		vector.addElement("ID");
		vector.addElement("NAME");
		vector.addElement("TEL1");
		vector.addElement("TEL2");
		vector.addElement("TEL3");
		vector.addElement("EMAIL1");
		vector.addElement("EMAIL2");
		
		//1-2.tablemodel ����
		model = new DefaultTableModel(vector,0){
			public boolean isCellEditable(int r, int c) {
				return (c!=0) ? true : false;
			}
		};
		
		//1-3.JTable ����
		tableCnt = new JTable(model);
		scroll = new JScrollPane(tableCnt);
		
		
		//1-4. Panel����
		tableJP = new JPanel();
		tableJP.setLayout(new BorderLayout());
		tableJP.add(scroll,BorderLayout.CENTER);
		tableJP.setBounds(20,20,660,380);//680,400
		con.add(tableJP);
	
		//2-1. ��ư ����
		modifyCntB = new JButton("ȸ�� ���� ����");
		deleteCntB = new JButton("ȸ�� ���� ����");
		nonMemB = new JButton("��ȸ�� ����");
		
		//2-2. Panel����
		buttonJP = new JPanel(new GridLayout(1,3,10,10));
		buttonJP.setBounds(150,420,400,40);//550,460
		buttonJP.add(modifyCntB); buttonJP.add(deleteCntB);
		buttonJP.add(nonMemB);
		con.add(buttonJP);
		
 		//3. Frame ����
 		setUndecorated(true);//����â �����
 		setTitle("ȸ�� ����");
 		setBounds(450,100,700,500);
 		setResizable(false);
 		setVisible(true);
 		
 		//4. Event ����
 		modifyCntB.addActionListener(this);
 		deleteCntB.addActionListener(this);
 		nonMemB.addActionListener(this);
 		
 		
 		//db�� �ִ� ȸ������ ������ �����ͼ� �ѷ���*************************
 		MemberDAO memberDAO = MemberDAO.getInstance();
 		ArrayList<MemberDTO> userList = memberDAO.getUserList();//db���ִ� ���� ����Ʈ �̾ƿ�
 		
 		for(MemberDTO memberDTO : userList) {//���Ϳ� memberDTO �߰��� model�� �߰�
 			System.out.println(memberDTO.getId());
 			Vector<String >vector  = new Vector<String>();
 			vector.addElement(memberDTO.getId());
 			vector.addElement(memberDTO.getUserName());
 			vector.addElement(memberDTO.getTel1());
 			vector.addElement(memberDTO.getTel2());
 			vector.addElement(memberDTO.getTel3());
 			vector.addElement(memberDTO.getEmaiL1());
 			vector.addElement(memberDTO.getEmail2());
 			
 			model.addRow(vector);
 			
 		}
 		
 	
 		
	}//ClientMgtFrame()-������ 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(modifyCntB == e.getSource()) {//****************************************
			
			if(tableCnt.getSelectedRow()!=-1) { //���̺� ���þ��ϰ� ��ư������ �޼��� ���, ���ý� ����â ���
				MemberDTO memberDTO = new MemberDTO();
				//���õ� ���� ���� ������
				memberDTO.setId((String) tableCnt.getValueAt(tableCnt.getSelectedRow(), 0));
				memberDTO.setUserName((String) tableCnt.getValueAt(tableCnt.getSelectedRow(), 1));
				memberDTO.setTel1((String) tableCnt.getValueAt(tableCnt.getSelectedRow(), 2));
				memberDTO.setTel2((String) tableCnt.getValueAt(tableCnt.getSelectedRow(), 3));
				memberDTO.setTel3((String) tableCnt.getValueAt(tableCnt.getSelectedRow(), 4));
				memberDTO.setEmaiL1((String) tableCnt.getValueAt(tableCnt.getSelectedRow(), 5));
				memberDTO.setEmail2((String) tableCnt.getValueAt(tableCnt.getSelectedRow(), 6));
				new InfoModify(memberDTO);
				dispose();
				
			}else {
				JOptionPane.showMessageDialog(this, "���������� �������ּ���", "����", JOptionPane.INFORMATION_MESSAGE);
			}
		}else if(deleteCntB == e.getSource()) {
			if(tableCnt.getSelectedRow()!=-1) {
				int result = JOptionPane.showConfirmDialog(this, "�ش� ȸ�������� �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION) {
					//���õ� ���� id���� ������
					String id = (String) tableCnt.getValueAt(tableCnt.getSelectedRow(), 0);
					MemberDAO memberDAO = MemberDAO.getInstance();
					memberDAO.deleteInfo(id);//db���� ȸ����������
					JOptionPane.showMessageDialog(this, "���� �Ϸ� �Ǿ����ϴ�", "����", JOptionPane.INFORMATION_MESSAGE);
					dispose();
					new ClientMgtFrame();
					
				}
			}else {
				JOptionPane.showMessageDialog(this, "���������� �������ּ���", "����", JOptionPane.INFORMATION_MESSAGE);
			}
		}else if(nonMemB==e.getSource()) {
			int checkSW=0;
			String nonMemNum = null;
			
			MemberDAO memberDAO = MemberDAO.getInstance();
			ArrayList<String> list = memberDAO.getNonMemNumList();
			
			while(true) {
				//3�ڸ��� ���� ��ȸ����ȣ
				int num =(int)(Math.random()*999);
				nonMemNum= String.valueOf(num);
				
				//db���̺� ��ȸ�� ��ȣ�� �ִ��� ������ üũ�ϴ� ����
				for(String nonNum : list) {
					if(nonMemNum.equals(nonNum)) {
						checkSW=1;
						break;
					}
					else checkSW=0;
				}
				if(checkSW==0) break;
				
			}
			
			
			memberDAO.setNonMem(nonMemNum);
			JOptionPane.showMessageDialog(this, "��ȸ�� ��ȣ : "+nonMemNum, "����", JOptionPane.INFORMATION_MESSAGE);
		}

	
	}


}//ClientMgtFrame Class
