package org.m4gicm4tt;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
//import java.awt.Window.Type;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class BorderGUI implements UpdateField {

	private JFrame frmBorderbot;
	private JTextArea txtrTesting;
	private BorderBot bBot;
	private JLabel lblNewLabel_2;

	// private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BorderGUI window = new BorderGUI();
					window.frmBorderbot.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BorderGUI() {
		bBot = new BorderBot(this);
		initialize();
		new Thread(bBot).start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBorderbot = new JFrame();
		frmBorderbot.setResizable(false);
		frmBorderbot.setTitle("BorderBot");
		BorderLayout borderLayout = (BorderLayout) frmBorderbot
				.getContentPane().getLayout();
		borderLayout.setVgap(5);
		borderLayout.setHgap(5);
		frmBorderbot.setBounds(100, 100, 448, 371);
		frmBorderbot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panelTop = new JPanel();
		panelTop.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		frmBorderbot.getContentPane().add(panelTop, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("BorderBot v" + BorderBot.ver);
		panelTop.add(lblNewLabel);
		lblNewLabel.setBorder(new EmptyBorder(2, 2, 2, 2));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panelCenter = new JPanel();
		panelCenter.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		frmBorderbot.getContentPane().add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelCenter.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		JButton btnNewButton = new JButton("Set Path");
		btnNewButton.setFocusPainted(false);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JButton button = (JButton) e.getSource();
				// System.out.println(button.getText());
				if (button.getText().equals("Set Path")) {
					JFileChooser jFile = new JFileChooser(
							"C:\\Program Files (x86)\\Steam\\steamapps\\common\\Borderlands 2\\Binaries\\Win32\\");
					FileNameExtensionFilter filter = new FileNameExtensionFilter(
							"executibles", "exe");
					jFile.setFileFilter(filter);
					int returnVal = jFile.showOpenDialog(button);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						// updateText("You chose to open this file: "
						// + jFile.getSelectedFile().getAbsolutePath());
						lblNewLabel_2.setText(jFile.getSelectedFile()
								.getAbsolutePath());
						bBot.setExecPath(jFile.getSelectedFile()
								.getAbsolutePath());
					}
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							button.setText("Start");
						}
					});
				} else if (button.getText().equals("Start")) {
					bBot.startMachine();
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							button.setText("Stop");
						}
					});
				} else if (button.getText().equals("Stop")) {
					bBot.stopMachine();
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							button.setText("Start");
						}
					});
				}
			}
		});

		panel.add(btnNewButton, BorderLayout.NORTH);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 35));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelCenter.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		txtrTesting = new JTextArea();

		txtrTesting.setBackground(Color.BLACK);
		txtrTesting.setText("testing");
		txtrTesting.setForeground(Color.GREEN);
		txtrTesting.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtrTesting.setRows(9);
		txtrTesting.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(txtrTesting);
		panel_2.add(scrollPane);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		scrollPane.getVerticalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {
					public void adjustmentValueChanged(AdjustmentEvent e) {
						txtrTesting.select(txtrTesting.getCaretPosition() * 13,
								0);
					}
				});

		JPanel panelSouth = new JPanel();
		panelSouth.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		frmBorderbot.getContentPane().add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelSouth.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_1 = new JLabel("Path:");
		panel_1.add(lblNewLabel_1, BorderLayout.WEST);
		lblNewLabel_1.setBorder(new EmptyBorder(2, 2, 2, 2));

		lblNewLabel_2 = new JLabel("Not Set");
		panel_1.add(lblNewLabel_2, BorderLayout.CENTER);
		lblNewLabel_2.setBorder(new EmptyBorder(2, 2, 2, 2));
	}

	@Override
	public void updateText(String s) {
		// TODO Auto-generated method stub
		txtrTesting.append("\n" + s);
	}

}
