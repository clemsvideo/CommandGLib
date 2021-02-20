package net.feedthemadness.glib.command.utils;

import java.util.Scanner;

//TODO: log4J (apache)
public class TerminalWrapper {

	private Scanner sc;

	private boolean newLine = true;

	public TerminalWrapper(boolean useScanner) {
		if (useScanner) {
			sc = new Scanner(System.in);
		}
	}

	public void close() {
	    if (sc != null) {
			sc.close();
		}
	}

	public void log(String message) {
		if (!this.newLine) System.out.println();
		System.out.println(message);
		this.newLine = true;
	}

	public void update(String message) {
		System.out.print('\r' + message);
		this.newLine = false;
	}

	public void info(String message) {
		StringBuilder sb = new StringBuilder();
		if (!this.newLine) sb.append('\n');

		sb.append("[INFO] - ").append(message);

		System.out.println(sb.toString());
		this.newLine = true;
	}

	public void warning(String message) {
		StringBuilder sb = new StringBuilder();
		if (!this.newLine) sb.append('\n');

		sb.append("[WARNING] - ").append(message);

		System.out.println(sb.toString());
		this.newLine = true;
	}

	public void severe(String message) {
		StringBuilder sb = new StringBuilder();
		if (!this.newLine) sb.append('\n');

		sb.append("[SEVERE] - ").append(message);

		System.out.println(sb.toString());
		this.newLine = true;
	}

	public void error(String error) {
		StringBuilder sb = new StringBuilder();
		if (!this.newLine) sb.append('\n');

		sb.append("[ERROR] - ").append(error);

		System.err.println(sb.toString());
		this.newLine = true;
	}

	public boolean requestBoolean(String message) {
	    if (sc == null) return false;
	    
		StringBuilder sb = new StringBuilder();
		if (!this.newLine) sb.append('\n');

		sb.append(message).append(" > ");
		System.out.print(sb.toString());
		this.newLine = true;

		boolean result = sc.nextBoolean();
		sc.nextLine();
		return result;
	}

	public int requestInt(String message) {
		if (sc == null) return 0;

		StringBuilder sb = new StringBuilder();
		if (!this.newLine) sb.append('\n');

		sb.append(message).append(" > ");
		System.out.print(sb.toString());
		this.newLine = true;

		int result = sc.nextInt();
		sc.nextLine();
		return result;
	}

	public float requestFloat(String message) {
		if (sc == null) return 0;

		StringBuilder sb = new StringBuilder();
		if (!this.newLine) sb.append('\n');

		sb.append(message).append(" > ");
		System.out.print(sb.toString());
		this.newLine = true;

		float result = sc.nextFloat();
		sc.nextLine();
		return result;
	}

	public long requestLong(String message) {
		if (sc == null) return 0;

		StringBuilder sb = new StringBuilder();
		if (!this.newLine) sb.append('\n');

		sb.append(message).append(" > ");
		System.out.print(sb.toString());
		this.newLine = true;

		long result = sc.nextLong();
		sc.nextLine();
		return result;
	}

	public double requestDouble(String message) {
		if (sc == null) return 0;

		StringBuilder sb = new StringBuilder();
		if (!this.newLine) sb.append('\n');

		sb.append(message).append(" > ");
		System.out.print(sb.toString());
		this.newLine = true;

		double result = sc.nextDouble();
		sc.nextLine();
		return result;
	}

	public String requestString(String message) {
		if (sc == null) return "";

		StringBuilder sb = new StringBuilder();
		if (!this.newLine) sb.append('\n');

		sb.append(message).append(" > ");
		System.out.print(sb.toString());
		this.newLine = true;
		
		return sc.nextLine();
	}

}