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

public class BorderGUI {

	private JFrame frmBorderbot;

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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBorderbot = new JFrame();
		frmBorderbot.setResizable(false);
		frmBorderbot.setTitle("BorderBot");
		BorderLayout borderLayout = (BorderLayout) frmBorderbot.getContentPane().getLayout();
		borderLayout.setVgap(5);
		borderLayout.setHgap(5);
		frmBorderbot.setBounds(100, 100, 450, 300);
		frmBorderbot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelTop = new JPanel();
		frmBorderbot.getContentPane().add(panelTop, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("BorderBot v"+BorderBot.ver);
		panelTop.add(lblNewLabel);
		lblNewLabel.setBorder(new EmptyBorder(2, 2, 2, 2));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panelSouth = new JPanel();
		panelSouth.setBorder(new EmptyBorder(0, 5, 5, 5));
		frmBorderbot.getContentPane().add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setLayout(new BorderLayout(0, 0));
		
		JTextArea txtrTesting = new JTextArea();
		txtrTesting.setBackground(Color.BLACK);
		txtrTesting.setText("testing");
		txtrTesting.setForeground(Color.GREEN);
		txtrTesting.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelSouth.add(txtrTesting);
		txtrTesting.setRows(5);
		txtrTesting.setEditable(false);
		
		JPanel panelCenter = new JPanel();
		panelCenter.setBorder(new EmptyBorder(5, 5, 0, 5));
		frmBorderbot.getContentPane().add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new GridLayout(0, 3, 0, 0));
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		panelCenter.add(btnNewButton);
		
		JLabel lblCurrentPath = new JLabel("Current Path");
		lblCurrentPath.setHorizontalTextPosition(SwingConstants.CENTER);
		lblCurrentPath.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentPath.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCenter.add(lblCurrentPath);
		
		JButton btnNewButton_1 = new JButton("New button");
		panelCenter.add(btnNewButton_1);
	}

}
