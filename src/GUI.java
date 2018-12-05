import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.hsqldb.Server;

import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class GUI extends JFrame {

	private JPanel contentPane;
	public String code = "";
	public static String directory = "C:\\Users\\James\\Documents\\FinalDB";
	public static ResultSet rs;
	Scanner reader = new Scanner(System.in);
	public static String databaseName = "Final";
	Server hsqlServer = null;
	Connection connection;
	public static data db = new data(databaseName, directory);
	public static JFrame newFrame = new JFrame();
	/**
	 * Launch the application.
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		
		//create and connect to the database
		db.start();
		db.connect(directory, databaseName);
		
		//Launch the window
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		//disconnects and stops the SQL server
		db.disconnect();
		db.stop();
	}
	
	//Sets the database, just added in-case a class would need it
	public void setDB(data db1) {
		db = db1;
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 488, 313);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		JFrame frame = new JFrame();
		
		JTextArea textArea = new JTextArea();
		
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
		JButton insert = new JButton("INSERT");
		insert.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textArea.setText("INSERT INTO tableName (Col1, Col2, etc...) VALUES (val1, val2, etc...);");
				System.out.println("INSERT");
			}
		});
		//Below does all the creation of the buttons
		JButton create = new JButton("CREATE");
		create.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea.setText("CREATE  TABLE tablename (COL1, COL2, ETC...);");
				System.out.println("CREATE");
			}
		});
		
		JButton update = new JButton("UPDATE");
		update.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea.setText("UPDATE TableName Set Col1 = value1, ..., Where Condition;");
				System.out.println("UPDATE");
			}
		});
		
		JButton select = new JButton("SELECT");
		select.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea.setText("SELECT condition FROM tableName;");
				System.out.println("SELECT");
			}
		});
		
		JButton delete = new JButton("DELETE");
		delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea.setText("DELETE FROM tableName WHERE condition;");
				System.out.println("DELETE");
			}
		});
		
		JButton avg = new JButton("AVG");
		avg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea.append("AVG ");
				System.out.println("AVG");
			}
		});
		
		JButton sum = new JButton("SUM");
		sum.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea.append("SUM ");
				System.out.println("SUM");
			}
		});
		
		JButton count = new JButton("COUNT");
		count.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea.append("COUNT ");
				System.out.println("SUM");
			}
		});
		
		JButton min = new JButton("MIN");
		min.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea.append("MIN ");
				System.out.println("MIN");
			}
		});
		
		JButton max = new JButton("MAX");
		max.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea.append("MAX ");
				System.out.println("MAX");
			}
		});
		
		JButton execute = new JButton("EXECUTE");		
		execute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				code = textArea.getText();
				String output= "";
				String[] temp = code.split(" ");
				try {
					if(!(temp[0].toLowerCase().equals("select")))
						db.doStuff(code);
					else {
						rs = db.fetch(code);
						ResultSetMetaData rsmd = rs.getMetaData();
						int numberOfColumns = rsmd.getColumnCount();
						for (int i = 1; i <= numberOfColumns; i++) {
							if (i > 1)
								output += (",          ");
							String columnName = rsmd.getColumnName(i);
							output += (columnName);
						}
						output += ("\n");
						while (rs.next()) {
							for (int i = 1; i <= numberOfColumns; i++) { 
								if (i > 1)
									output += (",          ");
								String columnValue = rs.getString(i);
								output += (columnValue);
							}
							output += ("\n");
						}
						
						JOptionPane.showMessageDialog(frame, output);

					}
				} catch (SQLException e1) {
					
					textArea.setText("INVALID CODE");
					
				}
				System.out.println("EXECUTE");
			}
		});

		JLabel lblAdvancedUsersCan = new JLabel("Advanced users can enter their own code here");
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(27)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(execute)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(16)
								.addComponent(avg)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(sum)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(count)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(min)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(max))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(insert)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(create)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(update)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(select)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(delete))
							.addComponent(lblAdvancedUsersCan)
							.addComponent(textArea)))
					.addContainerGap(62, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(insert)
						.addComponent(create)
						.addComponent(update)
						.addComponent(select)
						.addComponent(delete))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(avg)
						.addComponent(sum)
						.addComponent(count)
						.addComponent(min)
						.addComponent(max))
					.addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
					.addComponent(lblAdvancedUsersCan)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(execute))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
