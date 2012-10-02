/**
 * 
 */
package org.m4gicm4tt;

import java.awt.AWTException;
import java.awt.Robot;

/**
 * @author Matthew
 * 
 */
public class BorderBot {

	// private enum state{start, launch, waitForPlay, waitForMenu, goToShift}
	public static final String ver = "1.0"; 

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ImageSearch iSearch = new ImageSearch();
		int[] result = iSearch.findPicAInPicB(
				iSearch.getImage(System.getProperty("user.dir")
						+ "\\src\\org\\m4gicm4tt\\image.bmp"),
						iSearch.getScreenshot(), 20);
		Robot myRobot;

		try {
			myRobot = new Robot();
			myRobot.mouseMove(result[0], result[1]);
			// myRobot.mousePress(InputEvent.getMaskForButton(MouseEvent.BUTTON1));
			// Thread.sleep(1000);
			// myRobot.mouseRelease(InputEvent.getMaskForButton(MouseEvent.BUTTON1));
		} catch (AWTException e) {
			e.printStackTrace();
		}
		System.out.println("result " + result[0] + ", " + result[1]);
	}

}
