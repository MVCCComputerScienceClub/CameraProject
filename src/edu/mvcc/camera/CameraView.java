package edu.mvcc.camera;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.IllegalFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
		
		String ip;
		boolean ok;
		
		do
		{
			ip = JOptionPane.showInputDialog("IP:", null);
			ok = ipOk(ip);
			
			if (!ok)
				JOptionPane.showMessageDialog(null, "Invalid IP. Please enter it again.");
		} while (!ok);

		int port;
		
		do
		{
			try
			{
				port = Integer.parseInt(JOptionPane.showInputDialog("Port:", null));
			}
			catch (IllegalFormatException e) { port = -1; }
			
			if (port == -1)
				JOptionPane.showMessageDialog(null, "Invalid port. Please enter it again.");
		} while (port == -1);
		
//		This line was for testing purposes.
//		It would be better to implement an automatic finding
//		feature.
//		camera = new NetworkCamera("http://10.1.7.102:80/videostream.cgi", "admin", "", true, true);
		
		camera = new NetworkCamera("http://" + ip + ":" + port + "/videostream.cgi", "admin", "", true, true);
		
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
	
	/**
	 * This method checks if the input String <ip> is a valid IP Address
	 * @param ip - String to check
	 * @return boolean is valid IP address
	 */
	private boolean ipOk(String ip)
	{
		if (ip == null || ip.isEmpty()) return false;
		
		ip = ip.trim();
		
		if (ip.length() < 7 || ip.length() > 15) return false;
		
		Pattern pat = Pattern.compile("[0-9]{1,3}" + "\\." + "[0-9]{1,3}" + "\\." + "[0-9]{1,3}" + "\\." + "[0-9]{1,3}");
		Matcher mat = pat.matcher(ip);
		return mat.matches();
	}
	
	public static void main(String[] args) { new CameraView(); }
}