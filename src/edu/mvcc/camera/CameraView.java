package edu.mvcc.camera;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CameraView
{
	private static final int WIDTH = 640;
	private static final int HEIGHT = 960;
	
	private NetworkCamera camera;
	
	private JFrame frame;
	private JPanel panel;
	private CameraImagePanel content;
	
	public CameraView()
	{
		frame = new JFrame();
		frame.setTitle("Camera");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		
		camera = new NetworkCamera("http://10.1.7.102:80/videostream.cgi", "admin", "", true, true);
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		
		content = new CameraImagePanel(camera);
		panel.add(content);
		
		JButton button = new JButton("Disconnect");
		
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				camera.disconnect();
				System.exit(0);
			}
		});
		panel.add(button);
		
		frame.setContentPane(panel);
		frame.setVisible(true);
		
		while (true)
			content.repaint();
	}
	
	public static void main(String[] args) { new CameraView(); }
}