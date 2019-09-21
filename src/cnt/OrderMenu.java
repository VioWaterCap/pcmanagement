package cnt;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import cnt.CntServer;
import member.bean.MemberDTO;
import mgr.InfoDTO;
import mgr.inventory.InventoryDAO;
import mgr.inventory.InventoryDTO;

@SuppressWarnings("serial")
public class OrderMenu extends JFrame implements ActionListener,ListSelectionListener {
	private ButtonGroup group;
	private Container con;
	private Font labelF,textFieldF;//�󺧰� �ؽ�Ʈ �ʵ� �� ������ ��Ʈ ����
	private JButton orderB, eraseB, cancelB;//�ֹ�,�����,���ư��� ��ư
	private JLabel itemNameL,itemuCostL,mntselL,otherTL,totCostL,messageL,wonL;
	private JRadioButton oneRB,twoRB,threeRB,otherRB;
	private JScrollPane scroll;
	private JTextField itemNameF, itemuCostF,totCostF,otherMntF;
	private JTextArea messageF;
	private DefaultListModel<InventoryDTO> model;
	private JList<InventoryDTO> list;
	private JPanel labelJP,itemInfoTJP,mntSelJP,otherSelJP,buttonJP,
				  messageTJP,totCostTJP;
	private int stock;
	private MemberDTO memberDTO;
	public OrderMenu(MemberDTO memberDTO) {
		this.memberDTO = memberDTO;

		con = getContentPane();
		con.setLayout(null);
		
		//1.COMPONENT ����
 		labelF = new Font("Bahnschrift Light Condensed",Font.BOLD,15);
 		textFieldF = new Font("Bahnschrift Light Condensed",Font.PLAIN,18);
			//1-1 ��� �ۼ�
		model = new DefaultListModel<InventoryDTO>();
        list = new JList<InventoryDTO>(model);
        list.setFont(textFieldF);
        
        	//1-2 ���� ǰ�� ��
        itemNameL = new JLabel("�����Ͻ� ��ǰ��");
        itemNameL.setFont(labelF);
        itemNameF = new JTextField("");
        itemNameF.setFont(textFieldF);
        itemNameF.setEditable(false);
        itemuCostL = new JLabel("��ǰ�� (����)����");
        itemuCostL.setFont(labelF);
        itemuCostF = new JTextField("");
        itemuCostF.setFont(textFieldF);
        itemuCostF.setEditable(false);
        wonL = new JLabel("(��)");
        
        	//1-3 ���� ���� �κ�
        mntselL = new JLabel("�ֹ��Ͻ� ����");
        mntselL.setFont(labelF);
        group = new ButtonGroup();
        oneRB = new JRadioButton("1��",true);
        oneRB.setFont(labelF);
        group.add(oneRB);
        twoRB = new JRadioButton("2��");
        twoRB.setFont(labelF);
        group.add(twoRB);
        threeRB = new JRadioButton("3��");
        threeRB.setFont(labelF);
        group.add(threeRB);
        otherRB = new JRadioButton("��Ÿ");
        otherRB.setFont(labelF);
        group.add(otherRB);
        otherMntF = new JTextField("");
        otherMntF.setFont(textFieldF);
        otherMntF.setEditable(false);
       
        
        otherTL = new JLabel("(��)");
        otherTL.setFont(labelF);
        
        	//1-4 �հ� �κ�
        totCostL = new JLabel("�ֹ� �հ�");
        totCostL.setFont(labelF);
        totCostF = new JTextField("");
        totCostF .setFont(textFieldF);
        totCostF.setEditable(false);
        
        	//1-5 �߰� �޼��� �κ�
        messageL = new JLabel("��Ÿ �ֹ�����");
        messageL.setFont(labelF);
        messageF = new JTextArea("��Ÿ ���ǻ����� �Է����ּ���.",2,10);
        messageF.setFont(textFieldF);
        messageF.setEditable(false);//��ǰ�� ȭ�鿡�� ������ ������ ��� �Ұ� ����.
        
        	//1-6 ��ư �κ�
		orderB = new JButton("�ֹ� ����");
		orderB.setFont(labelF);
		eraseB = new JButton("�����");
		orderB.setFont(labelF);
		cancelB = new JButton("���ư���");
		orderB.setFont(labelF);
		
		//2-1. JList�κ� ��ġ+��� �ҷ�����
		scroll = new JScrollPane(list);
		scroll.setBounds(10,10,480,300);//490,310
		con.add(scroll);
		
		InventoryDAO dao = InventoryDAO.getInstance();
		ArrayList<InventoryDTO> inventorylist = dao.getInventoryList();
		
		for(InventoryDTO dto : inventorylist) {
			model.addElement(dto);
		}
		
		//2-2 �� �κ� ��ġ
		labelJP = new JPanel(new GridLayout(5,1,10,20));
		labelJP.setBounds(10,330,130,300);//140,630
		labelJP.add(itemNameL); 		labelJP.add(itemuCostL);
		labelJP.add(mntselL);			labelJP.add(totCostL);
		labelJP.add(messageL);
		con.add(labelJP);
		
		//2-3�ؽ�Ʈ �κ� ��ġ+���� ���� �� ��ġ
		itemInfoTJP = new JPanel(new GridLayout(2,1,10,20));
		itemInfoTJP.setBounds(140,330,120,110);//260,440
		itemInfoTJP.add(itemNameF);		itemInfoTJP.add(itemuCostF);
		con.add(itemInfoTJP);
	
		wonL.setBounds(270,400,25,30);
		con.add(wonL);
		
		//2-4���� ���� �κ� ��ġ
		mntSelJP = new JPanel(new GridLayout(1,4,5,20));
		mntSelJP.setBounds(140,460,260,50);//400,500
		mntSelJP.add(oneRB);			mntSelJP.add(twoRB);
		mntSelJP.add(threeRB);			mntSelJP.add(otherRB);
		con.add(mntSelJP);
		
		//2-5��Ÿ ���� ���� �κ� ��ġ
		otherSelJP = new JPanel(new GridLayout(1,2,10,10));
		otherSelJP.setBounds(400,465,100,40);//500,505
		otherSelJP.add(otherMntF);		otherSelJP.add(otherTL);	
		con.add(otherSelJP);
		
		//2-6 �� �ݾ� textField ��ġ
		totCostTJP = new JPanel(new GridLayout(1,1,10,20));
		totCostTJP.setBounds(140,525,120,40);//260,565
		totCostTJP.add(totCostF);
		con.add(totCostTJP);
		
		//2-7 textArea ��ġ
		messageTJP = new JPanel(new GridLayout(1,1,10,20));
		messageTJP.setBounds(140,590,330,50);//470,640
		messageTJP.add(messageF);
		con.add(messageTJP);
		
		//3. ��ư ��ġ
		buttonJP = new JPanel(new GridLayout(1,3,20,20));
		buttonJP.setBounds(100,660,300,50);//400,710
		buttonJP.add(orderB); 			buttonJP.add(eraseB);
		buttonJP.add(cancelB);
		con.add(buttonJP);

		//4. Frame ����
				setTitle("��ǰ �ֹ�");
				setBounds(600, 200, 500, 750);
				setVisible(true);
				setResizable(false);
				addWindowListener(new WindowAdapter(){
					@Override
					public void	windowClosing(WindowEvent e){
						dispose();
					}
				});
				
				//5. event Ȱ��ȭ[�ʱ�:�ֹ�,����� ��Ȱ��ȭ]
				oneRB.addActionListener(this);//���� ���Զ� (��)Ȱ��ȭ�� ���� Ư���� ����
				twoRB.addActionListener(this);//���� ���Զ� (��)Ȱ��ȭ�� ���� Ư���� ����
				threeRB.addActionListener(this);//���� ���Զ� (��)Ȱ��ȭ�� ���� Ư���� ����
				otherRB.addActionListener(this);//���� ���Զ� (��)Ȱ��ȭ�� ���� Ư���� ����
				otherMntF.addActionListener(this);//���͸� �Է��ϸ� totCost�� �ݿ��ȴ�.+���� �ܿ��� �Է��� �� ���� ����
				orderB.addActionListener(this);
				orderB.setEnabled(false);
				eraseB.addActionListener(this);
				eraseB.setEnabled(false);
				cancelB.addActionListener(this);
				list.addListSelectionListener(this);
				

			}//OrderMenu() - ������

			@Override
			public void actionPerformed(ActionEvent e) {
			   if(otherRB.isSelected()) {
				   otherMntF.setEditable(true);
				   if(e.getSource()==otherMntF && !otherMntF.getText().equals("")) totCostF.setText(getTotal());}
			   
			   else if(oneRB.isSelected()||twoRB.isSelected() || threeRB.isSelected()) {//�ٸ� ���� ������ ���� �Է�â ��Ȱ��ȭ ����
				   otherMntF.setText("");
				   otherMntF.setEditable(false);
				   totCostF.setText(getTotal()+"");}
			   
		       if(e.getSource()==cancelB) {
		    	   erase();
		    	   dispose();}
		       else if(e.getSource()==eraseB){
		    	   erase();}
		       
		       else if(e.getSource()==orderB) {
		   			int sel = JOptionPane.showConfirmDialog(this,"���Ÿ� Ȯ���Ͻðڽ��ϱ�?","[���� Ȯ��]",JOptionPane.YES_NO_OPTION);
		   			if(sel == JOptionPane.YES_OPTION) pushOrderInfo();
		   			else return; 
		   			dispose();}//�ֹ��� �Ϸ�Ǹ� ��� ȭ��â�� ��ħ.
			}//actionPerformed() �޼ҵ�

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(list.getSelectedIndex()==-1) return;
				InventoryDTO inventoryDTO = list.getSelectedValue();
				stock = inventoryDTO.getStock();
				
				if(inventoryDTO.getStock()==0) {
					JOptionPane.showMessageDialog(this,"ǰ���� ��ǰ�� �����Ͻ� �� �����ϴ�.","[�˸�]",JOptionPane.INFORMATION_MESSAGE);
			  	   	orderB.setEnabled(false);
			  	   	eraseB.setEnabled(false);
			  	   	messageF.setEditable(false);
					return;
				}//ǰ���Ǹ� �Ұ�
				
				//�����ϸ� �ؽ�Ʈâ�� �Ѹ���.
				itemNameF.setText(inventoryDTO.getItem());
		  	   	itemuCostF.setText(inventoryDTO.getPrice()+"");
		  	   	orderB.setEnabled(true);
		  	   	eraseB.setEnabled(true);
		  	   	messageF.setEditable(true);
		  	   	messageF.setText("");
		  	   	totCostF.setText(getTotal());
		  	   	
			}//valueChanged() �޼ҵ�
			
			public void pushOrderInfo() {
				InfoDTO dto = new InfoDTO();
				
				//������ �������� ���� ����
				if(oneRB.isSelected()) dto.setItemMnt(1); 
				else if(twoRB.isSelected()) dto.setItemMnt(2);
				else if(threeRB.isSelected()) dto.setItemMnt(3);
				else if(otherRB.isSelected())dto.setItemMnt(Integer.parseInt(otherMntF.getText()));
				
				//����̻� �ֹ���
				if(dto.getItemMnt() > stock) {
					JOptionPane.showMessageDialog(this,"��� �̻��� ��ǰ�� �ֹ��Ͻ� �� �����ϴ�. ���� ���� ����: "+stock+"(��)",
												  "[�˸�]",JOptionPane.INFORMATION_MESSAGE); return;}
				
				//���� �� �ٸ� �׸񿡼� ���� ����
				dto.setItemNum(list.getSelectedValue().getItemNum());//������ ���й�ȣ(�ӽ� ������ key value)
				dto.setItemName(itemNameF.getText());
				dto.setuPrice(Integer.parseInt(itemuCostF.getText()));
				dto.setTotCost(Integer.parseInt(totCostF.getText()));
				dto.setMessage(messageF.getText());
				
				CntServer cnt = CntServer.getInstance(memberDTO);//������ �������� ���� ������ ȣ���Ѵ�.
				cnt.getOrder(dto);//client ������ �ֹ� ������ ����

			}//pushOrderInfo()-���� ���� Ȯ���ϴ� �޼ҵ�
			
			public String getTotal() {//������ ��ǰ�� ���� ��ü ��� ���
				int totalCost=0;
				if (list.isSelectionEmpty() || stock==0 || itemuCostF.getText().equals("") ) return "";//������ ���� ����,������ ���� ����, ��Ÿ�� �����ϰ� �ƹ��� �Է��� ���� ��.
				else {
					if(oneRB.isSelected())  totalCost=Integer.parseInt(itemuCostF.getText());
					else if(twoRB.isSelected())	 totalCost=Integer.parseInt(itemuCostF.getText())*2;
					else if(threeRB.isSelected())	 totalCost=Integer.parseInt(itemuCostF.getText())*3;
					else if(otherRB.isSelected() && !otherMntF.getText().equals(""))
						try{totalCost=Integer.parseInt(itemuCostF.getText())*Integer.parseInt(otherMntF.getText());}
						catch(NumberFormatException nf) {
					   		JOptionPane.showMessageDialog(this,"���ڸ� �Է��ϼ��� �մϴ�.","[����]",JOptionPane.INFORMATION_MESSAGE);
					   		totalCost =0;}
					else totalCost=0;}
				if (totalCost == 0) return "";//����� 0�̸� �ؽ�Ʈ�ʵ带 ����.
				return totalCost+"";
			}//getTotal()-�޼ҵ� : �հ踦 ������.
			
			public void erase() {//�׸� �ʱ�ȭ
				
		 	   itemNameF.setText("");
		 	   itemuCostF.setText("");
		 	   oneRB.setSelected(true); 	twoRB.setSelected(false);
		 	   threeRB.setSelected(false); 	otherRB.setSelected(false);
		 	   otherMntF.setText(""); 		otherMntF.setEditable(false);
		 	   totCostF.setText(""); 
		 	   messageF.setText("��Ÿ ���ǻ����� �Է����ּ���."); messageF.setEditable(false);
		 	   orderB.setEnabled(false);
		 	   eraseB.setEnabled(false);
		 	   messageF.setEditable(false);
		 	   list.clearSelection();//����Ʈ ���� ���� ����
		 	   
			}//erase() �޼ҵ�
			
		}//OrderMenu Class
