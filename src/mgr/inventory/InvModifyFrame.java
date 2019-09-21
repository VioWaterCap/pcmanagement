package mgr.inventory;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import mgr.ErrorFrame;

@SuppressWarnings("serial")
public class InvModifyFrame extends JFrame implements ActionListener {

	private JButton btn_modify, btn_cancel;

	private JPanel labelJP, textfieldJP, ConfirmBtnJP;
	private Font labelF, textF, buttonF;

	private JLabel numL, itemL, priceL, stockL;
	private JTextField numT, itemT, priceT, stockT;

	
	@SuppressWarnings("rawtypes")
	private Vector vector;
	@SuppressWarnings("unused")
	private DefaultTableModel model;

	InventoryDTO inventoryDTO;
	private final String BLCFont = "Bahnschrift Light Condensed";

	public InvModifyFrame(InventoryDTO inventoryDTO) {

		this.inventoryDTO = inventoryDTO;
		Container con = getContentPane();
		con.setLayout(null);

		//��Ʈ ����
		labelF = new Font(BLCFont, Font.BOLD + Font.ITALIC, 15);
		textF = new Font(BLCFont, Font.PLAIN, 18);
		buttonF = new Font(BLCFont, Font.BOLD, 15);

		
		model = new DefaultTableModel(vector, 0) {
			public boolean isCellEditable(int r, int c) {
				return (c != 0) ? true : false;
			}
		};
		
		//�� ����
		numL = new JLabel("��ǰ��ȣ");
		numL.setFont(labelF);
		itemL = new JLabel("ǰ ��");
		itemL.setFont(labelF);
		priceL = new JLabel("�� ��");
		priceL.setFont(labelF);
		stockL = new JLabel("�� ��");
		stockL.setFont(labelF);
		
		//�ؽ�Ʈ�ʵ� ����
		numT = new JTextField(10);
		numT.setFont(textF);
		numT.setText(Integer.toString(inventoryDTO.getItemNum()));
		numT.setEditable(false);
		itemT = new JTextField(10);
		itemT.setFont(textF);
		itemT.setText(inventoryDTO.getItem());
		priceT = new JTextField(10);
		priceT.setFont(textF);
		priceT.setText(Integer.toString(inventoryDTO.getPrice()));
		stockT = new JTextField(10);
		stockT.setFont(textF);
		stockT.setText(Integer.toString(inventoryDTO.getStock()));
		
		//��ư ����
		btn_modify = new JButton("����");
		btn_modify.setFont(buttonF);
		btn_cancel = new JButton("���");
		btn_cancel.setFont(buttonF);

		setView(con);

		setTitle("��ǰ ����");
		setBounds(600, 200, 500, 350);
		setVisible(true);

		btn_modify.addActionListener(this);
		btn_cancel.addActionListener(this);

	}

	// ȭ�� ������ ���� Label, TextField, Button ���� �޼ҵ�
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
		ConfirmBtnJP.add(btn_modify);
		ConfirmBtnJP.add(btn_cancel);
		con.add(ConfirmBtnJP);
	}
	
	//��ǰ���� �̺�Ʈ
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_modify) {
			modifyInventory();
			new InvMntFrame();
		}else if(e.getSource() == btn_cancel) {
			new InvMntFrame();
		}
		
		dispose();
	}
	
	//��ǰ ���� �޼ҵ�
	public void modifyInventory() {

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
		inventoryDAO.modifyInventory(inventoryDTO);

	}

}
