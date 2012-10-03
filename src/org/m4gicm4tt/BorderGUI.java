package org.m4gicm4tt;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Window.Type;
import java.awt.Frame;
import javax.swing.JFileChooser;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BorderGUI implements ActionListener {

	private static BorderBot bBot;
	private JFrame frmBorderbot;
	private JButton btnNewButton;

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

		bBot = new BorderBot();
		bBot.runMachine();
	}

	/**
	 * Create the application.
	 */
	public BorderGUI() {
		initialize();
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

		btnNewButton = new JButton("Set Path");
		btnNewButton.addActionListener(this);
		panel.add(btnNewButton, BorderLayout.NORTH);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 35));

		JTextArea txtrTesting = new JTextArea();
		panel.add(txtrTesting, BorderLayout.CENTER);
		txtrTesting.setBackground(Color.BLACK);
		txtrTesting.setText("testing");
		txtrTesting.setForeground(Color.GREEN);
		txtrTesting.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtrTesting.setRows(10);
		txtrTesting.setEditable(false);

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

		JLabel lblNewLabel_2 = new JLabel("Not Set");
		panel_1.add(lblNewLabel_2, BorderLayout.CENTER);
		lblNewLabel_2.setBorder(new EmptyBorder(2, 2, 2, 2));
	}

	public void actionPerformed(ActionEvent arg0) {
		System.out.println(btnNewButton.getText());
		if (btnNewButton.getText().equals("Set Path")) {
			JFileChooser jFile = new JFileChooser("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Borderlands 2\\Binaries\\Win32\\");
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"executibles", "exe");
			jFile.setFileFilter(filter);
			int returnVal = jFile.showOpenDialog(btnNewButton);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("You chose to open this file: "
						+ jFile.getSelectedFile().getAbsolutePath());

				bBot.setExecPath(jFile.getSelectedFile().getAbsolutePath());
			}
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					btnNewButton.setText("Start");
				}
			});
		} else if (btnNewButton.getText().equals("Start")) {
			bBot.startMachine();
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					btnNewButton.setText("Stop");
				}
			});
		} else if (btnNewButton.getText().equals("Stop")) {
			bBot.stopMachine();
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					btnNewButton.setText("Start");
				}
			});
		}
	}

}
