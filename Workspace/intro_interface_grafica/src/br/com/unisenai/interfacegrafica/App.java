package br.com.unisenai.interfacegrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class App extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
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
	public App() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		BorderLayout layout = new BorderLayout();
		contentPane.setLayout(layout);
		
		JButton btnSouth = new JButton("Sul");
		JButton btnNorth = new JButton("Norte");
		JButton btnEast = new JButton("Leste");
		JButton btnWest = new JButton("Oeste");
		JButton btnCenter = new JButton("Centro");
		
		contentPane.add(btnSouth,BorderLayout.SOUTH);
		contentPane.add(btnNorth,BorderLayout.NORTH);
		contentPane.add(btnEast,BorderLayout.EAST);
		contentPane.add(btnWest,BorderLayout.WEST);
		contentPane.add(btnCenter,BorderLayout.CENTER);
		
		btnSouth.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Click no Sul");
			}
		});
		
		btnNorth.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Click no Norte");
			}
		});
		
		btnEast.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Click no Leste");
			}
		});
		
		btnWest.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Click no Oeste");
			}
		});
		
		btnCenter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Click no Centro");
			}
		});
		
		setContentPane(contentPane);
	}

}