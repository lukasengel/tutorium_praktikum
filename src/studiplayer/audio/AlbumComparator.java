package studiplayer.audio;

import java.util.Comparator;

public class AlbumComparator implements Comparator<AudioFile> {

	@Override
	public int compare(AudioFile o1, AudioFile o2) {
		// Null check
		if (o1 == null || o2 == null) {
			throw new NullPointerException("Cannot compare null objects.");
		}

		// Check if AudioFile objects are instances of the correct type
		boolean isCorrect1 = o1 instanceof TaggedFile;
		boolean isCorrect2 = o2 instanceof TaggedFile;

		// Both objects are of the correct class -> compare album
		if (isCorrect1 && isCorrect2) {
			final TaggedFile t1 = (TaggedFile) o1;
			final TaggedFile t2 = (TaggedFile) o2;
		
			return t1.getAlbum().compareTo(t2.getAlbum());
		}

		// Both objects are of the wrong type -> return zero
		if (!isCorrect1 && !isCorrect2) {
			return 0;
		}

		// 1 if o1 is correct, -1 if o2 is correct -> wrong item is smaller
		return isCorrect1 ? 1 : -1;
	}
}
