import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Font;

public class GUI extends JFrame {

	private static JPanel contentPane;
	private JFrame frame;
	private JTextArea textArea;
	private JScrollPane scroll;
	private JButton avg;
	private JButton min;
	private JButton max;
	private JButton count;
	private JButton sum;
	private JButton insert;
	private JButton create;
	private JButton update;
	private JButton select;
	private JButton delete;
	private JButton drop;
	private JButton execute;
	public String code = "";
	public static String directory;
	public static ResultSet rs;
	Scanner reader = new Scanner(System.in);
	public static String databaseName;
	Server hsqlServer = null;
	Connection connection;
	public static data db;
	public static JFrame newFrame = new JFrame();
	private JLabel aggFnctnBtnsLbl;
	private JTextField directoryTxt;
	private JLabel lblDirectory;
	private JLabel lblDirectoryName;
	private JTextField dbNameTxt;
	/**
	 * Launch the application.
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {

		//Launch the window
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
					System.out.println("Created GUI");
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("running");
			}
		});
		
		//create and connect to the database
		data.start();
		data.connect(directory, databaseName);

		//disconnects and stops the SQL server
		//		db.disconnect();
		//		System.out.println("disconnect");
		//		db.stop();
		//		System.out.println("Stop");
	}

	//Sets the database, just added in-case a class would need it
	public void setDB(data db1) {
		db = db1;
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setForeground(new Color(250, 235, 215));
		setTitle("GUI");
		System.out.println("GUI");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 526, 392);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(204, 204, 204));
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		frame = new JFrame();

		textArea = new JTextArea();
		
		directoryTxt = new JTextField();
		directoryTxt.setColumns(10);

		dbNameTxt = new JTextField();
		dbNameTxt.setColumns(10);

		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		insert = new JButton("INSERT");
		insert.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				textArea.setText("INSERT INTO tableName (Col1, Col2, etc...) VALUES (val1, val2, etc...);");
				System.out.println("INSERT");
			}
		});

		//Below does all the creation of the buttons
		create = new JButton("CREATE");
		create.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				textArea.setText("CREATE TABLE tablename (COL1, COL2, ETC...);");
				System.out.println("CREATE");
			}
		});

		update = new JButton("UPDATE");
		update.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				textArea.setText("UPDATE TableName Set Col1 = value1, ..., Where Condition;");
				System.out.println("UPDATE");
			}
		});

		select = new JButton("SELECT");
		select.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				textArea.setText("SELECT condition FROM tableName;");
				System.out.println("SELECT");
			}
		});

		delete = new JButton("DELETE");
		delete.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				textArea.setText("DELETE FROM tableName WHERE condition;");
				System.out.println("DELETE");
			}
		});
		drop = new JButton("DROP");
		drop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		drop.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				textArea.setText("DROP TABLE tableName");
			}
		});

		//creation of all aggregate function buttons
		avg = new JButton("AVG");
		avg.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				textArea.setText("SELECT AVG(columnName) FROM tableName \nGROUP BY columnName");
				System.out.println("AVG");
			}
		});

		sum = new JButton("SUM");
		sum.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				textArea.setText("SELECT SUM(columnName) FROM tableName \nGROUP BY columnName");
				System.out.println("SUM");
			}
		});

		count = new JButton("COUNT");
		count.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				textArea.setText("SELECT COUNT(columnName) FROM tableName \nGROUP BY columnName");
				System.out.println("SUM");
			}
		});

		min = new JButton("MIN");
		min.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				textArea.setText("SELECT MIN(columnName) FROM tableName \nGROUP BY columnName");
				System.out.println("MIN");
			}
		});

		max = new JButton("MAX");
		max.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				textArea.setText("SELECT MAX(columnName) FROM tableName \nGROUP BY columnName");
				System.out.println("MAX");
			}
		});

		execute = new JButton("EXECUTE");		
		execute.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				directory = directoryTxt.getText();
				code = textArea.getText();
				databaseName = dbNameTxt.getText();
				db = new data(databaseName, directory);
				String output= "";
				String[] temp = code.split(" ");
				textArea.setText("");
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
					System.out.println(e1.getMessage());
				}
				System.out.println("EXECUTE");
			}
		});

		JLabel advancedLbl = new JLabel("Advanced users can enter their own code here");
		advancedLbl.setFont(new Font("Tahoma", Font.BOLD, 12));

		aggFnctnBtnsLbl = new JLabel("Aggregate function buttons");
		aggFnctnBtnsLbl.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDirectory = new JLabel("Directory:");
		lblDirectory.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDirectoryName = new JLabel("Directory Name:");
		lblDirectoryName.setFont(new Font("Tahoma", Font.BOLD, 12));

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(75)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(avg)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(sum)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(count)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(min)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(max))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(77)
									.addComponent(aggFnctnBtnsLbl))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(insert)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(create)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(update)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(select)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(delete)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(drop))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblDirectory)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(directoryTxt, GroupLayout.PREFERRED_SIZE, 381, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblDirectoryName)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dbNameTxt, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(advancedLbl))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(execute)
								.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 459, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(41, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDirectory)
						.addComponent(directoryTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDirectoryName)
						.addComponent(dbNameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(aggFnctnBtnsLbl)
							.addPreferredGap(ComponentPlacement.UNRELATED))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(insert)
								.addComponent(create)
								.addComponent(update)
								.addComponent(select)
								.addComponent(delete)
								.addComponent(drop))
							.addGap(34)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(avg)
						.addComponent(sum)
						.addComponent(count)
						.addComponent(min)
						.addComponent(max))
					.addGap(18)
					.addComponent(advancedLbl)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addComponent(execute)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{scroll, textArea, avg, sum, count, min, max, aggFnctnBtnsLbl, insert, create, update, select, delete, drop, lblDirectory, directoryTxt, lblDirectoryName, dbNameTxt, advancedLbl, execute}));
	}
}