package com.wmu.churchlogger;
// This is our project. and it is cool

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

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
import javax.swing.LayoutStyle.ComponentPlacement;

public class ChurchLoggerWindow extends JFrame{
	private static final long serialVersionUID = 1L;

	private JLabel member_label, notes_label, bible_label, money_label;
	private JLabel selected_label;
	private JPanel card_panel, navigation_panel, member_panel, program_panel;
	private CardLayout cardLayout;
	private JTable table;

	private DBAccess database;
	private JPanel attendance_panel;
	private JButton btnAddRecord;
	private JScrollPane scrollPane_1;


	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public ChurchLoggerWindow(DBAccess database) {
		this.database = database;
		initialize();
	}

	/**
	 * Initialize the contents of the this.
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
		this.getContentPane().setBackground(SystemColor.text);
		this.setBounds(100, 100, 655, 505);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new CardLayout(0, 0));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		program_panel = new JPanel();
		program_panel.setBackground(SystemColor.text);
		this.getContentPane().add(program_panel, "name_7551650918463");

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


		// Fills member table with every member and information
		changeTableContents();

		scrollPane.setViewportView(table);
		member_panel.setLayout(gl_member_panel);
		
		attendance_panel = new JPanel();
		attendance_panel.setBackground(new Color(230, 230, 250));
		card_panel.add(attendance_panel, "attendance_panel");
		
		btnAddRecord = new JButton("Add Record");
		btnAddRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Add a new date
				addDate();
			}
		});
		btnAddRecord.setFont(new Font("Dialog", Font.BOLD, 14));
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBackground(Color.BLACK);
		GroupLayout gl_attendance_panel = new GroupLayout(attendance_panel);
		gl_attendance_panel.setHorizontalGroup(
			gl_attendance_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_attendance_panel.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_attendance_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
						.addComponent(btnAddRecord, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_attendance_panel.setVerticalGroup(
			gl_attendance_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_attendance_panel.createSequentialGroup()
					.addGap(6)
					.addComponent(btnAddRecord)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
					.addContainerGap())
		);
		attendance_panel.setLayout(gl_attendance_panel);

		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

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

	public void changeTableContents(){
		try {
			table.setModel(database.updateMemberTable());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.revalidate();
	}

	public void memberIconClicked(){
		System.out.println("Member icon clicked");
		resetLabels(member_label);
		//update the contents of the table
		changeTableContents();
		//show
		cardLayout.show(card_panel, "member_panel");
	}

	public void moneyIconClicked(){
		System.out.println("Money icon clicked");
		resetLabels(money_label);
		cardLayout.show(card_panel, "attendance_panel");

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

	public void addMember() {
		ProgramManager.openWindow(new AddMemberWindow(database));
		//need a way to make sure ^ finishes before this next code runs
		changeTableContents();
	}
	
	public void addDate(){
		ProgramManager.openWindow(new AddDateWindow(database));
	}

	public JFrame getFrame() {
		return this;
	}
}
