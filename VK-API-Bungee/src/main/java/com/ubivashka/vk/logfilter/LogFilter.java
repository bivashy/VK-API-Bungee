package com.ubivashka.vk.logfilter;

import java.util.logging.LogRecord;
import net.md_5.bungee.api.ProxyServer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;

public class LogFilter implements Filter {
	public LogFilter() {
		loadDefaultFilter();
		Logger coreLogger = (Logger) LogManager.getRootLogger();
		coreLogger.addFilter(this);
	}

	private void loadDefaultFilter() {
		java.util.logging.Logger logger = ProxyServer.getInstance().getLogger();
		logger.setFilter(new java.util.logging.Filter() {
			public boolean isLoggable(LogRecord record) {
				String message = record.getMessage();
				if (message.startsWith("Request: https://api.vk.com/")
						|| message.startsWith("Request: https://lp.vk.com/") || message.startsWith("ERROR StatusLogger")
						|| message.startsWith("SLF4J: Failed to load class")
						|| message.startsWith("SLF4J: Defaulting to no-operation")
						|| message.startsWith("SLF4J: See http:"))
					return false;
				return true;
			}
		});
	}

	public Filter.Result checkMessage(String message) {
		if (message.startsWith("Request: https://api.vk.com/") || message.startsWith("Request: https://lp.vk.com/")
				|| message.startsWith("ERROR StatusLogger"))
			return Filter.Result.DENY;
		return Filter.Result.NEUTRAL;
	}

	public LifeCycle.State getState() {
		try {
			return LifeCycle.State.STARTED;
		} catch (Exception exception) {
			return null;
		}
	}

	public void initialize() {
	}

	public boolean isStarted() {
		return true;
	}

	public boolean isStopped() {
		return false;
	}

	public void start() {
	}

	public void stop() {
	}

	public Filter.Result filter(LogEvent event) {
		return checkMessage(event.getMessage().getFormattedMessage());
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object... arg4) {
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4) {
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, Object message, Throwable arg4) {
		return checkMessage(message.toString());
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, Message message, Throwable arg4) {
		return checkMessage(message.getFormattedMessage());
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5) {
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5,
			Object arg6) {
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5,
			Object arg6, Object arg7) {
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5,
			Object arg6, Object arg7, Object arg8) {
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5,
			Object arg6, Object arg7, Object arg8, Object arg9) {
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5,
			Object arg6, Object arg7, Object arg8, Object arg9, Object arg10) {
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5,
			Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11) {
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5,
			Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12) {
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5,
			Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12,
			Object arg13) {
		return checkMessage(message);
	}

	public Filter.Result getOnMatch() {
		return Filter.Result.NEUTRAL;
	}

	public Filter.Result getOnMismatch() {
		return Filter.Result.NEUTRAL;
	}
}
