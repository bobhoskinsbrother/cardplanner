package uk.co.itstherules.cardplanner.swing;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import javax.swing.JPanel;

import uk.co.itstherules.yawf.server.StandaloneServerApplication;

public class Console extends WindowAdapter implements WindowListener, ActionListener, Runnable {
	
	private final Frame frame;
	private final PipedInputStream standardInput = new PipedInputStream();
	private final PipedInputStream errorInput = new PipedInputStream();
	private boolean quit;
	private final Thread serverThread;
	private final Thread standardThread;
	private final Thread errorThread;

	private TextArea textArea;

	public static void main(String[] args) {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "CardPlanner");
		new Console();
	}
	
	public Console() {
		frame = new Frame("CardPlanner");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension((int) (screenSize.width / 2), (int) (screenSize.height / 2));
		int x = (int) (frameSize.width / 2);
		int y = (int) (frameSize.height / 2);
		frame.setBounds(x, y, frameSize.width, frameSize.height);
		textArea = new TextArea();
		textArea.setEditable(false);
		Button button = new Button("Clear");
		Panel panel = new Panel();
		JPanel title = new ImagePanel("./lib/logo.png");
		panel.setLayout(new BorderLayout());
		panel.add(title, BorderLayout.NORTH);
		panel.add(textArea, BorderLayout.CENTER);
		panel.add(button, BorderLayout.SOUTH);
		frame.add(panel);
		frame.setVisible(true);
		frame.addWindowListener(this);
		button.addActionListener(this);
		pipeStreams();
		quit = false; 

		serverThread = new Thread(new StandaloneServerApplication("/CardPlanner", "./lib/CardPlanner.war", new Integer(9999)));
		serverThread.setDaemon(true);
		serverThread.start();

		standardThread = new Thread(this);
		standardThread.setDaemon(true);
		standardThread.start();

		errorThread = new Thread(this);
		errorThread.setDaemon(true);
		errorThread.start();
	}

	private void pipeStreams() {
	    try {
			PipedOutputStream standardOutput = new PipedOutputStream(this.standardInput);
			System.setOut(new PrintStream(standardOutput, true));
		} catch (java.io.IOException io) {
			textArea.append("Couldn't redirect STDOUT to this console\n"
			        + io.getMessage());
		} catch (SecurityException se) {
			textArea.append("Couldn't redirect STDOUT to this console\n"
			        + se.getMessage());
		}
		try {
			PipedOutputStream errorOutput = new PipedOutputStream(this.errorInput);
			System.setErr(new PrintStream(errorOutput, true));
		} catch (java.io.IOException io) {
			textArea.append("Couldn't redirect STDERR to this console\n"
			        + io.getMessage());
		} catch (SecurityException se) {
			textArea.append("Couldn't redirect STDERR to this console\n"
			        + se.getMessage());
		}
    }

	public synchronized void actionPerformed(ActionEvent evt) {
		textArea.setText("");
	}

	public synchronized String readLine(PipedInputStream inputStream) throws IOException {
		String input = "";
		while (!input.endsWith("\n") && !input.endsWith("\r\n") && !quit) {
			int available = inputStream.available();
			if (available == 0)
				break;
			byte b[] = new byte[available];
			inputStream.read(b);
			input = input + new String(b, 0, b.length);
		} 
		return input;
	}

	public synchronized void run() {
		try {
			while (Thread.currentThread() == standardThread) {
				try {
					this.wait(100);
				} catch (InterruptedException ie) {
				}
				if (standardInput.available() != 0) {
					String input = this.readLine(standardInput);
					textArea.append(input);
				}
				if (quit)
					return;
			}
			while (Thread.currentThread() == errorThread) {
				try {
					this.wait(100);
				} catch (InterruptedException ie) {
				}
				if (errorInput.available() != 0) {
					String input = this.readLine(errorInput);
					textArea.append(input);
				}
				if (quit)
					return;
			}
		} catch (Exception e) {
			textArea.append("\nConsole reports an Internal error.");
			textArea.append("The error is: " + e);
		}
	}

	public synchronized void windowClosed(WindowEvent evt) {
		quit = true;
		this.notifyAll();
		boolean standardOpen = true;
		boolean serverOpen = true;
		boolean errorOpen = true;
		while(serverOpen) {
			try {
				serverThread.join(1000);
				serverOpen = false;
			} catch (Exception e) {
			}
		}
		while(standardOpen) {
			try {
				standardThread.join(1000);
				standardInput.close();
				standardOpen = false;
			} catch (Exception e) {
			}
		}
		while(errorOpen) {
			try {
				errorThread.join(1000);
				errorInput.close();
				errorOpen = false;
			} catch (Exception e) {
			}
		}
		System.exit(0);
	}

	public synchronized void windowClosing(WindowEvent evt) {
		frame.setVisible(false);
		frame.dispose();
	}
}