package studiplayer.audio;

import java.util.Comparator;

public class AuthorComparator implements Comparator<AudioFile> {

	@Override
	public int compare(AudioFile o1, AudioFile o2) {
		// Null check
		if (o1 == null || o2 == null) {
			throw new NullPointerException("Cannot compare null objects.");
		}
		
		// Compare - getAuthor() cannot return null
		return o1.getAuthor().compareTo(o2.getAuthor());
	}
}
