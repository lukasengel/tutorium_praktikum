import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class PlayList {
	private LinkedList<AudioFile> list;
	private int current;

	public PlayList() {
		this.list = new LinkedList<AudioFile>();
		this.current = 0;
	}

	public PlayList(String m3uPathname) {
		this.loadFromM3U(m3uPathname);
	}

	public List<AudioFile> getList() {
		return this.list;
	}

	public int getCurrent() {
		return this.current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public void add(AudioFile file) {
		this.list.add(file);
	}

	public void remove(AudioFile file) {
		this.list.remove(file);
	}

	public int size() {
		return this.list.size();
	}

	public AudioFile currentAudioFile() {
		return this.current < this.list.size() ? this.list.get(this.current) : null;
	}

	public void nextSong() {
		this.current = this.current + 1 < this.list.size() ? this.current + 1 : 0;
	}

	public void loadFromM3U(String pathname) {
		// Reset playlist and current position
		this.list = new LinkedList<AudioFile>();
		this.current = 0;

		// Open M3U file
		try (Scanner scanner = new Scanner(new File(pathname))) {
			
			// Iterate through every line
			while (scanner.hasNextLine()) {
				final String line = scanner.nextLine();

				// Ignore blank lines and comments
				if (!line.isBlank() && !line.trim().startsWith("#")) {
					this.list.add(AudioFileFactory.createAudioFile(line));
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public void saveAsM3U(String pathname) {
		// Get platform line separator
		String lineSeparator = System.getProperty("line.separator");
		
		// Open M3U file
		try (FileWriter writer = new FileWriter(pathname)) {
			
			// Write pathname of all audio files separated with line breaks
			for (AudioFile file : this.list) {
				writer.write(file.getPathname() + lineSeparator);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
