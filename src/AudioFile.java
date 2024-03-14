import java.io.File;

public abstract class AudioFile {
	private String pathname;
	private String filename;
	protected String author;
	protected String title;

	public AudioFile() {
	}

	public AudioFile(String path) {
		this.parsePathname(path);
		this.parseFilename(this.filename);

		final boolean readable = new File(this.pathname).canRead();
		if (!readable) {
			throw new RuntimeException(String.format("Cannot read file \"%s\"", this.pathname));
		}
	}

	public String getPathname() {
		return this.pathname;
	}

	public String getFilename() {
		return this.filename;
	}

	public String getAuthor() {
		return this.author;
	}

	public String getTitle() {
		return this.title;
	}

	public void parsePathname(String path) {
		// Normalize path separators
		String temp = path.replace('\\', '/');

		while (temp.contains("//")) {
			temp = temp.replaceAll("//", "/");
		}

		// Replace with platform separator
		final char platformSeparator = getPlatformSeparator();

		if (platformSeparator != '/' && temp.contains("/")) {
			temp = temp.replace('/', platformSeparator);
		}

		// If the platform is not Windows, handle DOS-style drive letter.
		if (temp.indexOf(':') == 1 && !isWindows()) {
			final char driveLetter = temp.charAt(0);
			temp = String.format("%c%c%s", platformSeparator, driveLetter, temp.substring(2));
		}

		// If the path contains no divider, the pathname equals the filename
		final int lastSeparatorIndex = temp.lastIndexOf(platformSeparator);

		if (lastSeparatorIndex == -1) {
			this.pathname = temp.trim();
			this.filename = temp.trim();
		} else {
			// Filename is expected to be trimmed
			// Pathname is not, for whatever reason
			this.pathname = temp;
			this.filename = temp.substring(lastSeparatorIndex + 1).trim();
		}
	}

	public void parseFilename(String filename) {
		// Remove file extension
		final int lastDotIndex = filename.lastIndexOf('.');
		final String temp = lastDotIndex > -1 ? filename.substring(0, lastDotIndex) : filename;

		// Separate author and title
		final String[] split = temp.split(" - ", 2);

		// If the filename contains no divider, use filename as title
		if (split.length == 1) {
			this.author = "";
			this.title = split[0].trim();
		} else {
			this.author = split[0].trim();
			this.title = split[1].trim();
		}
	}

	@Override
	public String toString() {
		return this.author.isEmpty() ? this.title : String.format("%s - %s", this.author, this.title);
	}

	private static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
	}

	private static char getPlatformSeparator() {
		return System.getProperty("file.separator").charAt(0);
	}

	public abstract void play();

	public abstract void togglePause();

	public abstract void stop();

	public abstract String formatDuration();

	public abstract String formatPosition();

	public abstract String[] fields();
}
