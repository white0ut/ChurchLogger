package com.wmu.churchlogger;
// This is our project. and it is cool

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChurchLoggerWindow {

	private JFrame frame;
	private JLabel member_label, notes_label, bible_label, money_label;
	private JLabel selected_label;
	private JPanel card_panel, navigation_panel, money_panel, member_panel, program_panel;
	private CardLayout cardLayout;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		//Initialize database connection to MySQL database
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChurchLoggerWindow window = new ChurchLoggerWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		if(args[0].equals("login window") || args[0].equals("new user window")){
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						ChurchLoggerWindow window = new ChurchLoggerWindow();
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	
		//need to close database connection
	}
	

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public ChurchLoggerWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If they can't use nimbus...
		}
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.text);
		frame.setBounds(100, 100, 655, 505);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

		program_panel = new JPanel();
		program_panel.setBackground(SystemColor.text);
		frame.getContentPane().add(program_panel, "name_7551650918463");

		navigation_panel = new JPanel();
		navigation_panel.setMaximumSize(new Dimension(20, 32767));
		navigation_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		navigation_panel.setBackground(Color.WHITE);

		money_label = new JLabel("");
		money_label.setBackground(SystemColor.text);
		money_label.setOpaque(true);
		money_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				moneyIconClicked();
			}
		});
		money_label.setIcon(new ImageIcon(this.getClass().getResource("/res/money.png")));

		bible_label = new JLabel("");
		bible_label.setBackground(SystemColor.text);
		bible_label.setOpaque(true);
		bible_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bibleIconClicked();
			}
		});
		bible_label.setIcon(new ImageIcon(this.getClass().getResource("/res/bible.png")));

		notes_label = new JLabel("");
		notes_label.setIcon(new ImageIcon(this.getClass().getResource("/res/notes.png")));
		notes_label.setBackground(SystemColor.text);
		notes_label.setOpaque(true);
		notes_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				notesIconClicked();
			}
		});


		member_label = new JLabel("");
		selected_label = member_label;
		member_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				memberIconClicked();
			}
		});
		member_label.setOpaque(true);
		member_label.setIcon(new ImageIcon(this.getClass().getResource("/res/member.png")));
		member_label.setBorder(new EmptyBorder(1, 1, 1, 0));
		member_label.setBackground(SystemColor.text);
		GroupLayout gl_navigation_panel = new GroupLayout(navigation_panel);
		gl_navigation_panel.setHorizontalGroup(
				gl_navigation_panel.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 104, Short.MAX_VALUE)
				.addGroup(gl_navigation_panel.createSequentialGroup()
						.addContainerGap(22, Short.MAX_VALUE)
						.addGroup(gl_navigation_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(money_label, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
								.addComponent(bible_label, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
								.addComponent(notes_label, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
								.addComponent(member_label, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)))
				);
		gl_navigation_panel.setVerticalGroup(
				gl_navigation_panel.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 411, Short.MAX_VALUE)
				.addGroup(gl_navigation_panel.createSequentialGroup()
						.addGap(49)
						.addComponent(member_label, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
						.addGap(27)
						.addComponent(money_label, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
						.addGap(27)
						.addComponent(bible_label, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
						.addGap(31)
						.addComponent(notes_label, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		navigation_panel.setLayout(gl_navigation_panel);
		program_panel.setLayout(new BoxLayout(program_panel, BoxLayout.X_AXIS));
		program_panel.add(navigation_panel);

		card_panel = new JPanel();
		program_panel.add(card_panel);

		cardLayout = new CardLayout(0,0);
		card_panel.setLayout(cardLayout);

		money_panel = new JPanel();
		card_panel.add(money_panel, "money_panel");

		member_panel = new JPanel();
		member_panel.setBackground(new Color(230, 230, 250));
		card_panel.add(member_panel, "member_panel");

		JButton button = new JButton("Add Member");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addMember();
			}


		});
		button.setFont(new Font("Dialog", Font.BOLD, 14));

		JButton button_1 = new JButton("Remove Member");
		button_1.setFont(new Font("Dialog", Font.BOLD, 14));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.BLACK);

		JTextArea textArea = new JTextArea();
		textArea.setText("This is where any notes about the member will go.");
		textArea.setFont(new Font("Dialog", Font.PLAIN, 14));
		GroupLayout gl_member_panel = new GroupLayout(member_panel);
		gl_member_panel.setHorizontalGroup(
				gl_member_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_member_panel.createSequentialGroup()
						.addGap(6)
						.addGroup(gl_member_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_member_panel.createSequentialGroup()
										.addComponent(button, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(button_1))
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
										.addGroup(gl_member_panel.createSequentialGroup()
												.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
												.addGap(13)))
												.addGap(6))
				);
		gl_member_panel.setVerticalGroup(
				gl_member_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_member_panel.createSequentialGroup()
						.addGap(6)
						.addGroup(gl_member_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(button)
								.addComponent(button_1))
								.addGap(6)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
								.addGap(6)
								.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
				);

		table = new JTable();

		
		//update member table with data from database
		//updateMemberTable();

		table.setModel(new DefaultTableModel(
				new Integer[][] {
						{80, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
						{null, null, null, null},
				},
				new String[] {
						"Name", "Member Since", "Age", "Status"
				}
				));
		scrollPane.setViewportView(table);
		member_panel.setLayout(gl_member_panel);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File    ");
		mnFile.setFont(new Font("Dialog", Font.BOLD, 14));
		menuBar.add(mnFile);

		JMenu mnEdit = new JMenu("Edit    ");
		mnEdit.setFont(new Font("Dialog", Font.BOLD, 14));
		menuBar.add(mnEdit);

		JMenu mnHelp = new JMenu("Help    ");
		mnHelp.setFont(new Font("Dialog", Font.BOLD, 14));
		menuBar.add(mnHelp);
	}

	public void memberIconClicked(){
		System.out.println("Member icon clicked");
		resetLabels(member_label);
		cardLayout.show(card_panel, "member_panel");
	}

	public void moneyIconClicked(){
		System.out.println("Money icon clicked");
		resetLabels(money_label);
		cardLayout.show(card_panel, "money_panel");

	}

	public void notesIconClicked(){
		System.out.println("Notes icon clicked");
		resetLabels(notes_label);
	}

	public void bibleIconClicked(){
		System.out.println("Bible icon clicked");
		resetLabels(bible_label);
	}

	public void resetLabels(JLabel new_selected_label){
		selected_label.setBackground(Color.white);
		selected_label = new_selected_label;
		selected_label.setBackground(new Color(230, 230, 250));
	}
	

	/**
	 * Data needs to come in order
	 * @throws SQLException 
	 */
	private void updateMemberTable(Connection connection, String sql) throws SQLException{
		DefaultTableModel tableModel = new DefaultTableModel();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		String[] recordString = null;
		int i;
		
		
		//add column headers
		Object[] columnHeadings = new String[] {
				"First Name", "Last Name", "Phone", "Email", "Join Date", "Stree Address", "Zip" 
			};
		tableModel.setColumnIdentifiers(columnHeadings);
		
		while(rs.next()){
			for(i = 0; i < columnCount; i++){
				recordString[i] = rs.getNString(i + 1);
			}
			
			tableModel.addRow(recordString);
		}
		
		table.setModel(tableModel);
		
	}
	

	public void addMember() {
		// TODO Auto-generated method stub
		
	}
}
