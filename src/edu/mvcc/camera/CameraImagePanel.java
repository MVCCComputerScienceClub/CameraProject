package edu.mvcc.camera;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class CameraImagePanel extends JPanel
{
	private NetworkCamera cam;
	private BufferedImage image;
	
	public CameraImagePanel(NetworkCamera cam)
	{
		this.cam = cam;
		image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);
		image = toImage(this.cam.grab());
	}
	
	public BufferedImage getImage() { return image; }
	public void setImage(BufferedImage i) { image = i; }
	
	private BufferedImage toImage(int[][] values)
	{
		BufferedImage image = new BufferedImage(values.length, values[0].length, BufferedImage.TYPE_INT_ARGB);
		
		int w = values.length;
		int h = values[0].length;
		
		for (int x = 0; x < w; x++)
		{
			for (int y = 0; y < h; y++)
			{
//				System.out.print(values[x][y] + " ");
				image.setRGB(x, y, values[x][y]);
			}
			
//			System.out.println();
		}
		
		return image;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		image = toImage(cam.grab());
		g.drawImage(image, 0, 0, null);
	}
}