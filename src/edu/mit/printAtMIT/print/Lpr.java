package edu.mit.printAtMIT.print;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.Socket;

import android.util.Log;

/**
 * @author Andre Gutowski
 * 
 *         {@code} <b>Example</b> <br />
 *         <i>try { <br />
 *         if (args.length != 3) {<br />
 *         System.out.println("Useage: lpr HostName PrinterName PrintFile");<br />
 *         return;<br />
 *         }<br />
 *         jLpr myLpr = new jLpr();<br />
 *         myLpr.setPrintRaw(true);<br />
 *         myLpr.setUseOutOfBoundPorts(true);<br />
 *         myLpr.printFile(args[2], args[0], args[1]);<br />
 *         System.out.println("Printed");<br />
 *         }<br />
 *         catch (Exception e) {<br />
 *         System.out.println(e);<br />
 *         }</i><br />
 * 
 */
public class Lpr {

	private boolean printRaw = true;
	private boolean useOutOfBoundsPorts = false;
	private static int jobNumber = 0;

	/**
	 * By default jLpr prints all files as raw binary data, if you need to use
	 * the text formatting of the spooler on your host set this value to false
	 * 
	 */
	public void setPrintRaw(boolean printRawData) {
		printRaw = printRawData;
	}

	public boolean getPrintRaw() {
		return (printRaw);
	}

	/**
	 * The RFC for lpr specifies the use of local ports numbered 721 - 731,
	 * however TCP/IP also requires that any port that is used will not be
	 * released for 3 minutes which means that jLpr will get stuck on the 12th
	 * job if prints are sent quickly.
	 * 
	 * To resolve this issue you can use out of bounds ports which most print
	 * servers will support
	 * 
	 * The default for this is off
	 */
	public void setUseOutOfBoundPorts(boolean OutOfBoundsPorts) {
		useOutOfBoundsPorts = OutOfBoundsPorts;
	}

	public boolean getUseOutOfBoundPorts() {
		return (useOutOfBoundsPorts);
	}

	private Socket getSocket(String hostName) throws IOException,
			InterruptedException {
		if (useOutOfBoundsPorts) {
			Log.d("LPR", "outofbounds");
			return (new Socket(hostName, 515));
		} else {
			Socket tmpSocket = null;
			for (int j = 0; (j < 30) && (tmpSocket == null); j++) {
				for (int i = 721; (i <= 731) && (tmpSocket == null); i++) {
					try {
						// tmpSocket = new Socket(hostName, 515,
						// InetAddress.getLocalHost(), i);
						tmpSocket = new Socket(hostName, 515);

					} catch (BindException ignored) {
					}
				}
				if (tmpSocket == null) {
					Thread.sleep(10000);
				}

			}
			if (tmpSocket == null) {
				throw new BindException("jLpr Can't bind to local port/address");
			}
			return (tmpSocket);
		}
	}

	/**
	 * Print a file to a network host or printer
	 * 
	 * @param fileName
	 *            The path to the file to be printed
	 * @param hostName
	 *            The host name or IP address of the print server
	 * @param printerName
	 *            The name of the remote queue or the port on the print server
	 * @param documentName
	 *            The name of the document as displayed in the spooler of the
	 *            host
	 */
	public void printFile(File f, String userName, String hostName,
			String printerName, String documentName) throws IOException,
			InterruptedException {
		String controlFile = "";
		byte buffer[];
		String s;
		String strJobNumber;

		// Job number cycles from 001 to 999
		if (++jobNumber >= 1000) {
			jobNumber = 1;
		}
		strJobNumber = "" + jobNumber;
		while (strJobNumber.length() < 3) {
			strJobNumber = "0" + strJobNumber;
		}

		Socket socketLpr = getSocket(hostName);
		socketLpr.setSoTimeout(30000);
		OutputStream sOut = socketLpr.getOutputStream();
		InputStream sIn = socketLpr.getInputStream();

		// Open printer
		s = "\002" + printerName + "\n";
		sOut.write(s.getBytes());
		sOut.flush();
		acknowledge(sIn, "lpr Failed to open printer");

		// Send control file
		controlFile += "H" + hostName + "\n";
		controlFile += "P" + userName + "\n";
		controlFile += ((printRaw) ? "o" : "p") + "dfA" + strJobNumber
				+ hostName + "\n";
		// controlFile += "ldfA" + strJobNumber + hostName + "\n";
		// controlFile += "p" + documentName + "\n";
		controlFile += "UdfA" + strJobNumber + hostName + "\n";
		controlFile += "N" + documentName + "\n";

		s = "\002" + (controlFile.length()) + " cfA" + strJobNumber + hostName
				+ "\n";
		sOut.write(s.getBytes());

		acknowledge(sIn, "lpr Failed to send control header");

		buffer = controlFile.getBytes();
		sOut.write(buffer);
		buffer[0] = 0;
		sOut.write(buffer, 0, 1);
		sOut.flush();

		acknowledge(sIn, "jLpr Failed to send control file");

		// Send print file
		if (!(f.exists() && f.isFile() && f.canRead())) {
			throw new IOException("jLpr Error opening print file");
		}
		s = "\003" + (f.length()) + " dfA" + strJobNumber + hostName + "\n";
		sOut.write(s.getBytes());
		sOut.flush();
		acknowledge(sIn, "jLpr Failed to send print file command");

		FileInputStream fs = new FileInputStream(f);

		int readCounter;
		do {
			readCounter = fs.read(buffer);
			if (readCounter > 0) {
				sOut.write(buffer, 0, readCounter);
			}
		} while (readCounter > 0);
		buffer[0] = 0;
		sOut.write(buffer, 0, 1);
		sOut.flush();
		acknowledge(sIn, "jLpr Failed to send print file");

		socketLpr.close();
	}

	private void acknowledge(InputStream in, String alert) throws IOException {
		System.out.println("alert: " + alert);
		if (in.read() != 0) {
			throw new IOException(alert);
		}
	}

}
