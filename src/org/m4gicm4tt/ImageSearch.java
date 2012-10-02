/**
 * 
 */
package org.m4gicm4tt;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author MBOCKNEK
 * 
 */
public class ImageSearch {

	public BufferedImage getScreenshot() {
		BufferedImage capture = null;
		try {
			Robot myRobot = new Robot();
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit()
					.getScreenSize());
			capture = myRobot.createScreenCapture(screenRect);
			ImageIO.write(capture, "bmp", new File(".\\printscreen.bmp"));

		} catch (AWTException | IOException e1) {
			e1.printStackTrace();
		}
		return capture;
	}

	public BufferedImage getImage(String path) {
		BufferedImage myPic = null;
		try {
			myPic = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return myPic;
	}

	public int[] findPicAInPicB(BufferedImage picA, BufferedImage picB,
			int variance) {
		int x_coord = -1;
		int y_coord = -1;

		int num0 = 0, num4 = 0, num8 = 0, numSlow = 0;

		Raster rasterPicA = picA.getData();
		Raster rasterPicB = picB.getData();

		int maxXofA = rasterPicA.getWidth();
		int maxYofA = rasterPicA.getHeight();

		int maxXofB = rasterPicB.getWidth();
		int maxYofB = rasterPicB.getHeight();

		int maxSamples = maxXofA * maxYofA * rasterPicA.getNumBands();
		boolean foundMatch = false;
		int match = 0;
		double[] picAbuf = new double[maxSamples];
		double[] picBbuf = new double[maxSamples];
		double[] picBbuf_1 = new double[rasterPicB.getNumBands()];
		// TODO: must be greater then 4px
		double[] picBbuf_4 = new double[rasterPicB.getNumBands() * 4];
		// TODO: must be greater then 8px
		double[] picBbuf_8 = new double[rasterPicB.getNumBands() * 8];
		double[] pointer;

		picAbuf = rasterPicA.getPixels(0, 0, maxXofA, maxYofA, picAbuf);
		// no partial matches that is why maxYofB - maxYofA
		for (int i = 0; (i <= (maxYofB - maxYofA)) && !foundMatch; i++) {
			for (int j = 0; (j <= (maxXofB - maxXofA)) && !foundMatch; j++) {
				if (match == 3) {
					// TODO: this is very slow, add more levels of progression
					picBbuf = rasterPicB.getPixels(j, i, maxXofA, maxYofA,
							picBbuf);
					pointer = picBbuf;
					numSlow++;
				} else if (match == 2) {
					picBbuf_8 = rasterPicB.getPixels(j, i, 8, 1, picBbuf_8);
					pointer = picBbuf_8;
					num8++;
				} else if (match == 1) {
					picBbuf_4 = rasterPicB.getPixels(j, i, 4, 1, picBbuf_4);
					pointer = picBbuf_4;
					num4++;
				} else {
					picBbuf_1 = rasterPicB.getPixel(j, i, picBbuf_1);
					pointer = picBbuf_1;
					num0++;
				}
				if (matchPixel(picAbuf, pointer, variance)) {
					if (match == 3) {
						x_coord = j;
						y_coord = i;
						foundMatch = true;
						match = 0; // doesn't matter
					}
					match++;
					if (j == 0) {
						i--;
						j = maxXofB - maxXofA;
					} else {
						j--;
					}
				} else
					match = 0;
			}
		}
		System.out.println("num0 " + num0 + ", " + "num4 " + num4 + ", "
				+ "num8 " + num8 + ", " + "numSlow " + numSlow);
		return new int[] { x_coord, y_coord };
	}

	private boolean matchPixel(double[] picA, double[] picB, int variance) {
		for (int i = 0; i < picB.length; i++) {
			if (Math.abs(picA[i] - picB[i]) >= variance) {
				return false;
			}
		}
		return true;
	}
}
