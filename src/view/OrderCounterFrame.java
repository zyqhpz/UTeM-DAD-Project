package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

import model.ItemProduct;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class OrderCounterFrame extends JFrame {

	private JPanel contentPane;
	private JTable table_1;
	private JTable tableItemProducts;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderCounterFrame frame = new OrderCounterFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OrderCounterFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// set the size of the frame to 16:9 ratio
		setSize(1280, 720);
		// set the location to center of the screen (x, y) = (0, 0)
		setLocation(0, 0);
		setLocationRelativeTo(null);
		setTitle("Order Counter");
		// setResizable(false);

		// retrieve ObjectInputStream from server and set it to the content pane of the
		// frame
		Socket socket;

		JTable table;

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		List<ItemProduct> itemProducts = new ArrayList<ItemProduct>();
		try {
			// Server information
			int serverPortNo = 8087;
			InetAddress serverAddress = InetAddress.getLocalHost();

			socket = new Socket(serverAddress, serverPortNo);

			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);

			try {
				// Read object from the server
				Object obj = ois.readObject();

				// Add the object into itemProduct list
				if (obj instanceof ArrayList<?>) {
					ArrayList<?> al = (ArrayList<?>) obj;
					if (al.size() > 0) {
						for (int i = 0; i < al.size(); i++) {
							Object o = al.get(i);
							if (o instanceof ItemProduct) {
								ItemProduct v = (ItemProduct) o;
								itemProducts.add(v);
							}
						}
					}
				}

				ois.close();
				is.close();
				socket.close();

				System.out.println("\n\t ItemProducts fetched from server \n");

				// String[] colNames = new String[] { "Sender", "Subject", "Body" };
				String[] colNames = new String[] { "ItemProductId", "Name", "LabelName", "Price" };

				// String[][] rowData = new String[itemProducts.size() /
				// colNames.length][colNames.length];
				// for (int i = 0; i < rowData.length; i++) {
				// for (int j = 0; j < rowData[i].length; j++) {
				// // ItemProduct itemProduct = itemProducts.get(i * colNames.length + j);
				// // ItemProduct itemProduct = itemProducts.getItemProduct(i * colNames.length
				// +
				// // j);
				// // rowData[i][j] = itemProducts.get(i * colNames.length + j).toString();
				// rowData[i][j] = itemProduct.getName() + "";
				// // rowData[i][j] = itemProducts.get(i * colNames.length + j);
				// }
				// }

				String[] names = new String[itemProducts.size()];

				for (int i = 0; i < itemProducts.size(); i++) {
					names[i] = itemProducts.get(i).getName();
				}

				// ListModel model = new JList(itemProducts.toArray()).getModel();
				// ListModel model = new JList(names).getModel();
				// JList list = new JList(model);

				// // insert the itemProducts into the JList
				// // JList list = new JList(rowData);
				// // JList<ItemProduct> list = new JList<ItemProduct>(
				// // itemProducts.toArray(new ItemProduct[itemProducts.size()]));
				// list.setVisibleRowCount(itemProducts.size() / colNames.length);
				// list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
				// list.setVisible(true);
				// list.setBounds(70, 442, 559, 161);
				// contentPane.add(list);

				// tableItemProducts = new JTable();
				// tableItemProducts.setModel(new DefaultTableModel(

				// new Object[][] {
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// { null, null, null },
				// },
				// new String[] {
				// "ItemProductID", "Name", "Price"
				// }) {
				// boolean[] columnEditables = new boolean[] {
				// false, false, false
				// };

				// public boolean isCellEditable(int row, int column) {
				// return columnEditables[column];
				// }
				// });
				// // set itemProducts to the table
				// for (int i = 0; i < itemProducts.size(); i++) {
				// ItemProduct itemProduct = itemProducts.get(i);
				// tableItemProducts.setValueAt(itemProduct.getItemProductId(), i, 0);
				// tableItemProducts.setValueAt(itemProduct.getName(), i, 1);
				// tableItemProducts.setValueAt(itemProduct.getPrice(), i, 2);
				// }
				// tableItemProducts.setBounds(84, 107, 530, 519);
				// // contentPane.add(new JScrollPane(tableItemProducts));
				// tableItemProducts.setPreferredScrollableViewportSize(new Dimension(500, 80));

				JTable tableItemProducts = getTableItemProducts(itemProducts);

				JScrollPane scrollPane = new JScrollPane(tableItemProducts);
				// scrollPane.add(tableItemProducts);
				scrollPane.setBounds(95, 164, 452, 366);
				contentPane.add(scrollPane);

				// set itemProduct list to JTable
				// JTable table = new JTable(itemProducts);
				// JTable table = new JTable(rowData, colNames);
				// table = new JTable(rowData, colNames);
				// table.setBounds(56, 78, 559, 334);
				// contentPane.add(new JScrollPane(table));
				// contentPane.add(table);

			} catch (Exception e) {
				System.out.println("\n\t Error fetching ItemProducts from server \n");
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		// contentPane.add(new JScrollPane(table));

		// setBounds(100, 100, 450, 300);

		// contentPane.add(new JScrollPane(table));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// table_1 = new JTable();
		// table_1.setBounds(56, 78, 559, 334);
		// contentPane.add(table_1);

		JLabel orderCounterTitle = new JLabel("Tea Order Counter");
		orderCounterTitle.setHorizontalAlignment(SwingConstants.CENTER);
		orderCounterTitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
		orderCounterTitle.setBounds(190, 31, 283, 36);
		contentPane.add(orderCounterTitle);

		// JScrollPane scrollPane = new JScrollPane();
		// scrollPane.setBounds(95, 164, 688, 440);
		// contentPane.add(scrollPane);

		// tableItemProducts = new JTable();
		// tableItemProducts.setModel(new DefaultTableModel(
		// new Object[][] {
		// },
		// new String[] {
		// "ItemProductID", "Name", "Price"
		// }
		// ));
		// tableItemProducts.setBounds(84, 107, 530, 298);
		// contentPane.add(tableItemProducts);

		// JList list = new JList();
		// list.setBounds(70, 453, 559, 150);
		// contentPane.add(list);
	}

	/**
	 * This method retrieve products from ObjectInputStream and display them in the
	 * frame.
	 */
	public void displayProducts() {

	}

	public JTable getTableItemProducts(List<ItemProduct> itemProducts) {

		JTable tableItemProducts = new JTable();
		tableItemProducts.setModel(new DefaultTableModel(
				new Object[][] {
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
						{ null, null, null },
				},
				new String[] {
						"ID", "Name", "Price (RM)"
				}) {
			boolean[] columnEditables = new boolean[] {
					false, false, false
			};

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableItemProducts.getColumnModel().getColumn(0).setResizable(false);
		tableItemProducts.getColumnModel().getColumn(1).setResizable(false);
		tableItemProducts.getColumnModel().getColumn(2).setResizable(false);
		// set itemProducts to the table
		for (int i = 0; i < itemProducts.size(); i++) {
			ItemProduct itemProduct = itemProducts.get(i);
			tableItemProducts.setValueAt(itemProduct.getItemProductId(), i, 0);
			tableItemProducts.setValueAt(itemProduct.getName(), i, 1);
			tableItemProducts.setValueAt(itemProduct.getPrice(), i, 2);
		}
		tableItemProducts.setBounds(84, 107, 530, 519);
		// contentPane.add(new JScrollPane(tableItemProducts));
		tableItemProducts.setPreferredScrollableViewportSize(new Dimension(500, 80));

		// CUSTOM WIDTH
		TableColumn col0 = tableItemProducts.getColumnModel().getColumn(0);
		TableColumn col1 = tableItemProducts.getColumnModel().getColumn(1);
		TableColumn col2 = tableItemProducts.getColumnModel().getColumn(2);

		col0.setPreferredWidth(10);
		col1.setPreferredWidth(250);
		col2.setPreferredWidth(70);
		return tableItemProducts;
	}
}
