/**
 * 
 */
package org.m4gicm4tt;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * @author Matthew
 * 
 */
public class BorderBot {

	private enum state {
		stop, init, idle, start, goToShift, inShift, enterShiftCode, waitForOk
	}

	public state myState = state.idle;
	public static final String ver = "1.0";
	public File imageToCompare = null;
	public String execPath = "";

	/**
	 * @param args
	 * 
	 *            public static void main(String[] args) { ImageSearch iSearch =
	 *            new ImageSearch(); int[] result = iSearch.findPicAInPicB(
	 *            iSearch.getImage(System.getProperty("user.dir") +
	 *            "\\src\\org\\m4gicm4tt\\image.bmp"), iSearch.getScreenshot(),
	 *            20); Robot myRobot;
	 * 
	 *            try { myRobot = new Robot(); myRobot.mouseMove(result[0],
	 *            result[1]); //
	 *            myRobot.mousePress(InputEvent.getMaskForButton(MouseEvent
	 *            .BUTTON1)); // Thread.sleep(1000); //
	 *            myRobot.mouseRelease(InputEvent
	 *            .getMaskForButton(MouseEvent.BUTTON1)); } catch (AWTException
	 *            e) { e.printStackTrace(); } System.out.println("result " +
	 *            result[0] + ", " + result[1]); }
	 */
	public void setImageToCompare(File f) {
		imageToCompare = f;
	}

	public void setExecPath(String s) {
		execPath = s;
	}

	public void runMachine() {
		while (myState != state.stop) {
			stateMachine();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void stopMachine() {
		myState = state.idle;
	}

	public void startMachine() {
		myState = state.init;
	}

	private boolean findPic(String s) {
		int[] result = findPicCoords(s);
		return ((result[0] != -1) && (result[1] != -1));
	}

	private int[] findPicCoords(String s) {
		ImageSearch iSearch = new ImageSearch();
		int[] result = iSearch.findPicAInPicB(
				iSearch.getImage(System.getProperty("user.dir")
						+ "\\src\\org\\m4gicm4tt\\" + s),
				iSearch.getScreenshot(), 20);
		System.out.println("result " + result[0] + ", " + result[1]);
		return result;
	}

	public void stateMachine() {
		Robot myRobot;
		// ImageSearch iSearch;
		// int[] result;

		switch (myState) {
		case init:
			Runtime rt = Runtime.getRuntime();
			try {
				Process proc = rt.exec(execPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			myState = state.start;
			break;
		case start:
			try {
				myRobot = new Robot();
				myRobot.mouseMove(200, 50);
				myRobot.mousePress(InputEvent
						.getMaskForButton(MouseEvent.BUTTON1));
				Thread.sleep(500);
				myRobot.mouseRelease(InputEvent
						.getMaskForButton(MouseEvent.BUTTON1));
			} catch (AWTException | InterruptedException e) {
				e.printStackTrace();
			}
			if (findPic("mainMenu.bmp"))
				myState = state.goToShift;
			break;
		case goToShift:
			try {
				myRobot = new Robot();
				for (int i = 0; i < 6; i++) {
					myRobot.keyPress(KeyEvent.VK_DOWN);
					Thread.sleep(100);
					myRobot.keyRelease(KeyEvent.VK_DOWN);
					Thread.sleep(100);
				}
				myRobot.keyPress(KeyEvent.VK_ENTER);
				Thread.sleep(100);
				myRobot.keyRelease(KeyEvent.VK_ENTER);
				Thread.sleep(100);
			} catch (AWTException | InterruptedException e) {
				e.printStackTrace();
			}
			myState = state.inShift;
			break;
		case inShift:
			if (findPic("shiftMenu.bmp")) {
				try {
					myRobot = new Robot();
					myRobot.keyPress(KeyEvent.VK_ENTER);
					Thread.sleep(500);
					myRobot.keyRelease(KeyEvent.VK_ENTER);
					Thread.sleep(500);
				} catch (AWTException | InterruptedException e) {
					e.printStackTrace();
				}
				myState = state.enterShiftCode;
			}
			break;
		case enterShiftCode:
			int[] res = findPicCoords("shiftCode.bmp");
			if (((res[0] != -1) && (res[1] != -1))) {
				try {
					myRobot = new Robot();
					myRobot.mouseMove(res[0], res[1]);
					Thread.sleep(100);
					myRobot.mouseMove(res[0]-1, res[1]);
					myRobot.mousePress(InputEvent
							.getMaskForButton(MouseEvent.BUTTON1));
					Thread.sleep(500);
					myRobot.mouseRelease(InputEvent
							.getMaskForButton(MouseEvent.BUTTON1));
				} catch (AWTException | InterruptedException e) {
					e.printStackTrace();
				}
				myState = state.waitForOk;
			}
			break;
		case waitForOk:

			myState = state.idle;
			break;
		}
	}
}
