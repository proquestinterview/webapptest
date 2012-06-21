package com.proquest.interview.util;

import org.apache.log4j.Logger;

public class ErrorLogger {

	static Logger logger = Logger.getLogger(ErrorLogger.class);
	
	/** 
	 * Print a pared down stacktrace when reporting handled exceptions
	 */
	static public void filterStackTrace(Exception e) {

		StackTraceElement[] stackTraceElements = e.getStackTrace();

		logger.error("\n" + e);

		for (int i = 0; i < stackTraceElements.length; i++) {

			StackTraceElement ste = stackTraceElements[i];

			if (i == 0 ||
				ste.getClassName().contains("com.proquest") ||
				ste.getClassName().contains("org.apache.jsp")) {

				System.err.println("\tat " +
					ste.getClassName() + "." +
					ste.getMethodName() + "(" +
					ste.getFileName() + ":" +
					ste.getLineNumber() + ")"
				);

			}
		}

		// print the path to the first referenced compiled jsp
		for (StackTraceElement ste : stackTraceElements) {

			if (ste.getClassName().contains("org.apache.jsp")) {

				// Determine path to jsp source file from compiled jsp class name
				System.err.println("WPath: " + ste.getClassName().replace(".", "\\") + "\\" + ste.getFileName() + ":" + ste.getLineNumber());
				System.err.println("LPath: /var/tomcat/webapps/ROOT/" + ste.getClassName().replace(".", "/") + "/" + ste.getFileName() + ":" + ste.getLineNumber());
				break;

			}
		}
	}

}
