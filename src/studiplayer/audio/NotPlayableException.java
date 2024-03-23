package studiplayer.audio;

public class NotPlayableException extends Exception {
	private static final long serialVersionUID = 1L;
	private final String pathname;

	public NotPlayableException(String pathname, String msg) {
		super(msg);
		this.pathname = pathname;
	}

	public NotPlayableException(String pathname, Throwable t) {
		super(t);
		this.pathname = pathname;
	}

	public NotPlayableException(String pathname, String msg, Throwable t) {
		super(msg, t);
		this.pathname = pathname;
	}

	@Override
	public String toString() {

		return String.format("NotPlayableException (%s): %s", this.pathname, super.toString());
	}
}
