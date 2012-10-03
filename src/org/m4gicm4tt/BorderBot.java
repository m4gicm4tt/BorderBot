/**
 * 
 */
package org.m4gicm4tt;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
//import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * @author Matthew
 * 
 */
public class BorderBot implements Runnable {

	public state myState = state.idle;
	public static final String ver = "1.0a";
	public File imageToCompare = null;
	public String execPath = "";

	private final boolean SEND_DEBUG_SYS_OUT = false;
	private BorderGUI bGUI;

	private enum state {
		stop, init, idle, start, goToShift, inShift, enterShiftCode, waitForOk
	}

	public BorderBot(BorderGUI borderGUI) {
		// TODO Auto-generated constructor stub
		bGUI = borderGUI;
		// runMachine();
	}

	public void setImageToCompare(File f) {
		imageToCompare = f;
	}

	public void setExecPath(String s) {
		execPath = s;
	}

	@Override
	public void run() {
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
		ImageSearch iSearch = new ImageSearch(this);
		int[] result = iSearch.findPicAInPicB(
				iSearch.getImage(System.getProperty("user.dir")
				// + "\\src\\org\\m4gicm4tt\\" + s),
						+ "\\" + s), iSearch.getScreenshot(), 20);
		//sendDebug("result " + result[0] + ", " + result[1]);
		return result;
	}

	public void sendDebug(String s) {
		if (SEND_DEBUG_SYS_OUT) {
			System.out.println(s);
		} else {
			bGUI.updateText(s);
		}
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
				myRobot.mousePress(InputEvent.BUTTON1_MASK);
				Thread.sleep(500);
				myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
			} catch (AWTException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (findPic("mainMenu.bmp")){
				myState = state.goToShift;
				sendDebug(myState.toString());
			}
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
			} catch (AWTException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			myState = state.inShift;
			sendDebug(myState.toString());
			break;
		case inShift:
			if (findPic("shiftMenu.bmp")) {
				try {
					myRobot = new Robot();
					myRobot.keyPress(KeyEvent.VK_ENTER);
					Thread.sleep(500);
					myRobot.keyRelease(KeyEvent.VK_ENTER);
					Thread.sleep(500);
				} catch (AWTException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myState = state.enterShiftCode;
				sendDebug(myState.toString());
			}
			break;
		case enterShiftCode:
			int[] res = findPicCoords("shiftCode.bmp");
			if (((res[0] != -1) && (res[1] != -1))) {
				try {
					myRobot = new Robot();
					myRobot.mouseMove(res[0], res[1]);
					Thread.sleep(100);
					myRobot.mouseMove(res[0] - 1, res[1]);
					myRobot.mousePress(InputEvent.BUTTON1_MASK);
					Thread.sleep(500);
					myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
				} catch (AWTException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myState = state.waitForOk;
				sendDebug(myState.toString());
			}
			break;
		case waitForOk:

			myState = state.idle;
			break;
		}
	}
}
