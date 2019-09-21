package mgr.inventory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class InventoryDAO {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "java";
	private String password = "itbank";
			
			private static InventoryDAO instance;
			
			public static InventoryDAO getInstance() {
				if(instance==null) {
					synchronized (InventoryDAO.class) {instance = new InventoryDAO();}}
				return instance;}
			
			public InventoryDAO() {
				try {Class.forName(driver);} 
				catch (ClassNotFoundException e) {e.printStackTrace();}}//������
			
			public Connection getConnection() {
				Connection conn = null;
				try {conn = DriverManager.getConnection(url, user, password);}
				catch (SQLException e) {e.printStackTrace();}
				return conn;}
			
			public void takeInventoryOrder(int itemNum, int itemMnt) {
				int number = itemNum;
				int mount  = itemMnt;
				
				Connection conn = this.getConnection();
				String sql = "update ��ǰ set ���=���-"+mount+" where ��ǰ��ȣ ="+number;	
				PreparedStatement pstmt = null;

				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.executeUpdate();}
				catch (SQLException e) {e.printStackTrace();}
				finally {
					try {
						if(pstmt!=null) pstmt.close();
						if(conn!=null) conn.close();} 
					catch (SQLException e) {e.printStackTrace();}}
			}//DB�� �ֹ� �ݿ�
			
			public ArrayList<InventoryDTO> getInventoryList(){//ȭ�鿡 ����� �Ѹ��� �뵵
				ArrayList<InventoryDTO> list = new ArrayList<InventoryDTO>();
				String sql = "select ��ǰ��ȣ,��ǰ��,����,��� from ��ǰ";
				Connection conn = getConnection();
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
				try {
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						InventoryDTO dto = new InventoryDTO();
						dto.setItemNum(rs.getInt("��ǰ��ȣ"));
						dto.setItem(rs.getString("��ǰ��"));
						dto.setPrice(rs.getInt("����"));
						dto.setStock(rs.getInt("���"));
						list.add(dto);
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
					list=null;
				}finally {
					try {
						if(rs != null) rs.close();
						if(pstmt != null) pstmt.close();
						if(conn != null) conn.close();
					} catch (SQLException e) {
						e.printStackTrace();		
					}
				}
				return list;
			}

			//DB�� ��ǰ�߰� ��Ű�� �޼ҵ�
			public void addInventory(InventoryDTO inventoryDTO) {
				String sql = "insert into ��ǰ values(?,?,?,?)";

				Connection conn = this.getConnection();
				PreparedStatement pstmt = null;
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, inventoryDTO.getItemNum());
					pstmt.setString(2, inventoryDTO.getItem());
					pstmt.setInt(3, inventoryDTO.getPrice());
					pstmt.setInt(4, inventoryDTO.getStock());

					pstmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (pstmt != null)
							pstmt.close();
						if (conn != null)
							conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}// addInventory
			
			//DB ��ǰ���� �޼ҵ�
			public void deleteInventory(String itemName) {

				String sql = "delete from ��ǰ where ��ǰ�� = ?";

				Connection conn = this.getConnection();
				PreparedStatement pstmt = null;
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, itemName);
					pstmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (pstmt != null)
							pstmt.close();
						if (conn != null)
							conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}//deleteInventory
			
			//DB�� ��ǰ���� �޼ҵ�
			public void modifyInventory(InventoryDTO inventoryDTO) {
				String sql = "update ��ǰ set ��ǰ��=?, ����=?, ���=? where ��ǰ��ȣ=?";

				Connection conn = this.getConnection();
				PreparedStatement pstmt = null;
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, inventoryDTO.getItem());
					pstmt.setInt(2, inventoryDTO.getPrice());
					pstmt.setInt(3, inventoryDTO.getStock());
					pstmt.setInt(4, inventoryDTO.getItemNum());

					pstmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (pstmt != null)
							pstmt.close();
						if (conn != null)
							conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}//modifyInventory
			
}//InventoryDAO class
