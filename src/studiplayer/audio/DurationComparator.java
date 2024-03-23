package studiplayer.audio;

import java.util.Comparator;

public class DurationComparator implements Comparator<AudioFile> {

	@Override
	public int compare(AudioFile o1, AudioFile o2) {
		// Null check
		if (o1 == null || o2 == null) {
			throw new NullPointerException("Cannot compare null objects.");
		}

		// Initialize duration with negative value
		// Ensures that objects which are not instances of SampledFile end up first
		long duration1 = -1;
		long duration2 = -1;

		// Extract duration, if objects are instances of SampledFile
		if (o1 instanceof SampledFile) {
			SampledFile f1 = (SampledFile) o1;
			duration1 = f1.getDuration();
		}

		if (o2 instanceof SampledFile) {
			SampledFile f2 = (SampledFile) o2;
			duration2 = f2.getDuration();
		}

		return Long.compare(duration1, duration2);
	}
}