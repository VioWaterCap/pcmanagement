package mgr.inventory;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import mgr.ErrorFrame;

@SuppressWarnings("serial")
public class InvAddFrame extends JFrame implements ActionListener {

	private JPanel labelJP, textfieldJP, ConfirmBtnJP;
	private Font labelF, textF, buttonF;

	private JLabel numL, itemL, priceL, stockL;
	private JTextField numT, itemT, priceT, stockT;
	private JButton addB, cancelB;

	@SuppressWarnings("rawtypes")
	private Vector vector;
	private DefaultTableModel model;

	private final String BLCFont = "Bahnschrift Light Condensed";
	
	//��ǰ�߰� ������
	public InvAddFrame() {

		Container con = getContentPane();
		con.setLayout(null);

		labelF = new Font(BLCFont, Font.BOLD + Font.ITALIC, 15);
		textF = new Font(BLCFont, Font.PLAIN, 18);
		buttonF = new Font(BLCFont, Font.BOLD, 15);

		model = new DefaultTableModel(vector, 0) {
			public boolean isCellEditable(int r, int c) {
				return (c != 0) ? true : false;
			}
		};

		getInventoryList();
		
		//��ǰ�߰� �� 
		numL = new JLabel("��ǰ��ȣ");
		numL.setFont(labelF);
		itemL = new JLabel("ǰ ��");
		itemL.setFont(labelF);
		priceL = new JLabel("�� ��");
		priceL.setFont(labelF);
		stockL = new JLabel("�� ��");
		stockL.setFont(labelF);

		//��ǰ�߰� �ؽ�Ʈ�ʵ�
		numT = new JTextField(10);
		numT.setFont(textF);
		itemT = new JTextField(10);
		itemT.setFont(textF);
		priceT = new JTextField(10);
		priceT.setFont(textF);
		stockT = new JTextField(10);
		stockT.setFont(textF);
		
		//Ȯ�� ��ҹ�ư ��ư����
		addB = new JButton("�߰�");
		addB.setFont(buttonF);
		cancelB = new JButton("���");
		cancelB.setFont(buttonF);
		
		//���ͳ��̳� 
		setView(con);

		setTitle("��ǰ �߰�");
		setBounds(600, 200, 500, 350);
		setVisible(true);

		//�̺�Ʈ �߻�
		addB.addActionListener(this);
		cancelB.addActionListener(this);
	}

	// �κ��丮 ���̺��� ��� �����͸� �ҷ��� ȭ�鿡 ����ִ� �޼ҵ�
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void getInventoryList() {

		InventoryDAO inventoryDAO = InventoryDAO.getInstance();
		ArrayList<InventoryDTO> list = inventoryDAO.getInventoryList();

		for (InventoryDTO inventoryDTO : list) {

			Vector vec = new Vector<String>();
			vec.addElement(inventoryDTO.getItemNum());
			vec.addElement(inventoryDTO.getItem());
			vec.addElement(Integer.toString(inventoryDTO.getPrice()));
			vec.addElement(Integer.toString(inventoryDTO.getStock()));

			model.addRow(vec);
		}
	}

	// ȭ�� ������ ���� Label, Textfield, Button ���� �޼ҵ�
	private void setView(Container con) {

		// Label ��ġ ����
		labelJP = new JPanel(new GridLayout(4, 1, 0, 20));
		labelJP.setBounds(30, 40, 90, 155);
		labelJP.add(numL);
		labelJP.add(itemL);
		labelJP.add(priceL);
		labelJP.add(stockL);
		con.add(labelJP);

		// Textfield ��ġ ����
		textfieldJP = new JPanel(new GridLayout(4, 1, 0, 15));
		textfieldJP.setBounds(150, 40, 230, 150);
		textfieldJP.add(numT);
		textfieldJP.add(itemT);
		textfieldJP.add(priceT);
		textfieldJP.add(stockT);
		con.add(textfieldJP);

		// Button ��ġ ����
		ConfirmBtnJP = new JPanel(new GridLayout(1, 2, 20, 5));
		ConfirmBtnJP.setBounds(120, 230, 250, 50);
		ConfirmBtnJP.add(addB);
		ConfirmBtnJP.add(cancelB);
		con.add(ConfirmBtnJP);
	}
	
	//�̺�Ʈ 
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addB) {
			addInventory();
			dispose();
			new InvMntFrame();
		}else if(e.getSource() == cancelB) {
			new InvMntFrame();
			dispose();
		}

	}

	//��ǰ�߰� �޼ҵ�
	public void addInventory() {

		InventoryDTO inventoryDTO = new InventoryDTO();

		int itemNum;
		String item;
		int price;
		int stock;
		
		try {
			itemNum = Integer.parseInt(numT.getText());
			item = itemT.getText();
			price = Integer.parseInt(priceT.getText());
			stock = Integer.parseInt(stockT.getText());
		} catch (NumberFormatException e) {
			System.out.println(e);
			new ErrorFrame("�߸��� ������ �Է� ���� �־����ϴ�.");
			return;
		} 

		inventoryDTO.setItemNum(itemNum);
		inventoryDTO.setItem(item);
		inventoryDTO.setPrice(price);
		inventoryDTO.setStock(stock);

		InventoryDAO inventoryDAO = InventoryDAO.getInstance();

		inventoryDAO.addInventory(inventoryDTO);
		
		



	}//addInventory

}