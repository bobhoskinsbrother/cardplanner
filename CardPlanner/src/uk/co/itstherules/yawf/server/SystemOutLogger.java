package uk.co.itstherules.yawf.server;

import org.eclipse.jetty.util.log.Logger;

public final class SystemOutLogger implements Logger {
	@Override
	public void debug(Throwable throwable) {
		System.out.println(throwable.getMessage());
	}

	@Override
	public void debug(String s, Object... aobj) {
		System.out.println(s);
		for (Object object : aobj) { System.out.println(object); }
	}

    @Override public void debug(String s, long l) {
        debug(s,new Object());
    }

    @Override
	public void debug(String s, Throwable throwable) {
		System.out.println(s);
		System.out.println(throwable.getMessage());
	}

	@Override
	public Logger getLogger(String s) {
		return this;
	}

	@Override
	public String getName() {
		return "SOUT";
	}

	@Override
	public void ignore(Throwable throwable) {
		debug(throwable);
	}

	@Override
	public void info(Throwable throwable) {
		debug(throwable);
	}

	@Override
	public void info(String s, Object... aobj) {
		debug(s, aobj);
	}

	@Override
	public void info(String s, Throwable throwable) {
		debug(s,throwable);
	}

	@Override
	public boolean isDebugEnabled() {
		return false;
	}

	@Override
	public void setDebugEnabled(boolean flag) {
	}

	@Override
	public void warn(Throwable throwable) {
		debug(throwable);
	}

	@Override
	public void warn(String s, Object... aobj) {
		debug(s,aobj);
	}

	@Override
	public void warn(String s, Throwable throwable) {
		debug(s, throwable);
	}
}