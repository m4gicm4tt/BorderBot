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
 * @author Matthew
 * 
 */
public class BorderBot {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		BorderBot myBot = new BorderBot();
		int[] result = myBot
				.findPicAInPicB(
						myBot.getImage(System.getProperty("user.dir")+"\\src\\org\\m4gicm4tt\\image.bmp"),
						myBot.getScreenshot(), 20);
		Robot myRobot;
		try {
			myRobot = new Robot();
			myRobot.mouseMove(result[0], result[1]);
			//myRobot.mousePress(InputEvent.getMaskForButton(MouseEvent.BUTTON1));
			//Thread.sleep(1000);
			//myRobot.mouseRelease(InputEvent.getMaskForButton(MouseEvent.BUTTON1));
		} catch (AWTException e) {
			e.printStackTrace();
		}
		System.out.println("result " + result[0] + ", " + result[1]);
	}
	private BufferedImage getScreenshot() {
		BufferedImage capture = null;
		try {
			Robot myRobot = new Robot();
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit()
					.getScreenSize());
			capture = myRobot.createScreenCapture(screenRect);
			ImageIO.write(
					capture,
					"bmp",
					new File(
							".\\printscreen.bmp"));

		} catch (AWTException | IOException e1) {
			e1.printStackTrace();
		}
		return capture;
	}
	private BufferedImage getImage(String path) {
		BufferedImage myPic = null;
		try {
			myPic = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return myPic;
	}
	private int[] findPicAInPicB(BufferedImage picA, BufferedImage picB,
			int variance) {
		int x_coord = -1;
		int y_coord = -1;
		Raster rasterPicA = picA.getData();
		Raster rasterPicB = picB.getData();

		int maxXofA = rasterPicA.getWidth();
		int maxYofA = rasterPicA.getHeight();

		int maxXofB = rasterPicB.getWidth();
		int maxYofB = rasterPicB.getHeight();

		int maxSamples = maxXofA * maxYofA * rasterPicA.getNumBands();
		boolean match = false, foundMatch = false;

		double[] picAbuf = new double[maxSamples];
		double[] picBbuf = new double[maxSamples];
		double[] picBbuf_1 = new double[rasterPicB.getNumBands()];
		double[] pointer;

		picAbuf = rasterPicA.getPixels(0, 0, maxXofA, maxYofA, picAbuf);
		// no partial matches that is why maxYofB - maxYofA
		for (int i = 0; (i <= (maxYofB - maxYofA)) && !foundMatch; i++) {
			for (int j = 0; (j <= (maxXofB - maxXofA)) && !foundMatch; j++) {
				if (match) {
					// TODO: this is very slow, add more levels of progression					
					picBbuf = rasterPicB.getPixels(j, i, maxXofA, maxYofA,
							picBbuf);
					pointer = picBbuf;
				} else {
					picBbuf_1 = rasterPicB.getPixel(j, i, picBbuf_1);
					pointer = picBbuf_1;
				}
				if (matchPixel(picAbuf, pointer, variance)) {
					if (match) {
						x_coord = j;
						y_coord = i;
						foundMatch = true;
					}
					if (j == 0) {
						i--;
						j = maxXofB - maxXofA;
					} else {
						j--;
					}
					match = true;
				} else
					match = false;
			}
		}
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
