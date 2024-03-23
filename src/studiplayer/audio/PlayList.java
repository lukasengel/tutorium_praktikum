package studiplayer.audio;

import java.io.*;
import java.util.*;

public class PlayList implements Iterable<AudioFile> {
	private LinkedList<AudioFile> list;
	private Iterator<AudioFile> iterator;
	private AudioFile current;
	private String search;
	private SortCriterion sortCriterion;

	public PlayList() {
		this.sortCriterion = SortCriterion.DEFAULT;
		this.list = new LinkedList<AudioFile>();
		this.updateIterator();
	}

	public PlayList(String m3uPathname) {
		this.sortCriterion = SortCriterion.DEFAULT;
		this.loadFromM3U(m3uPathname);
	}

	public List<AudioFile> getList() {
		return this.list;
	}

	public String getSearch() {
		return this.search;
	}

	public void setSearch(String search) {
		this.search = search;
		this.updateIterator();
	}

	public SortCriterion getSortCriterion() {
		return this.sortCriterion;
	}

	public void setSortCriterion(SortCriterion sortCriterion) {
		this.sortCriterion = sortCriterion;
		this.updateIterator();
	}

	public void add(AudioFile file) {
		this.list.add(file);
		this.updateIterator();
	}

	public void remove(AudioFile file) {
		this.list.remove(file);
		this.updateIterator();
	}

	public int size() {
		return this.list.size();
	}

	public AudioFile currentAudioFile() {
		return this.current;
	}

	public void nextSong() {
		// If we have reached the end of the playlist, jump to the beginning
		if (!this.iterator.hasNext()) {
			this.updateIterator();
			return;
		}
		
		this.current = this.iterator.next();
	}

	public void jumpToAudioFile(AudioFile audioFile) {
		((ControllablePlayListIterator) this.iterator).jumpToAudioFile(audioFile);
		this.current = audioFile;
	}

	public void loadFromM3U(String pathname) {
		// Reset playlist and initialize iterator
		this.list = new LinkedList<AudioFile>();
		this.updateIterator();

		// Open M3U file
		try (Scanner scanner = new Scanner(new File(pathname))) {

			// Iterate through every line
			while (scanner.hasNextLine()) {
				final String line = scanner.nextLine();

				// Ignore blank lines and comments
				if (!line.isBlank() && !line.trim().startsWith("#")) {
					try {
						this.list.add(AudioFileFactory.createAudioFile(line));
					} catch (NotPlayableException e) {
						e.printStackTrace();
					}
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

	@Override
	public Iterator<AudioFile> iterator() {
		return new ControllablePlayListIterator(this.list, this.search, this.sortCriterion);
	}

	private void updateIterator() {
		// Update iterator based upon current configuration
		this.iterator = this.iterator();

		// Select the first element, if available
		this.current = this.iterator.hasNext() ? this.iterator.next() : null;
	}
	
	@Override
	public String toString() {
		return list.toString();
	}
}
