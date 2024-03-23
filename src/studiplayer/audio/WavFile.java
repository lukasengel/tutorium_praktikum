package studiplayer.audio;

import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {
	public WavFile() {
		super();
	}

	public WavFile(String path) throws NotPlayableException {
		super(path);
		readAndSetDurationFromFile();
	}

	public void readAndSetDurationFromFile() throws NotPlayableException {
		try {
			WavParamReader.readParams(this.getPathname());
			this.duration = computeDuration(WavParamReader.getNumberOfFrames(), WavParamReader.getFrameRate());
		} catch (Exception e) {
			throw new NotPlayableException(this.getPathname(), e);
		}

	}

	@Override
	public String toString() {
		return String.format("%s - %s", super.toString(), this.formatDuration());
	}

	public static long computeDuration(long numberOfFrames, float frameRate) {
		return Math.round((numberOfFrames / frameRate) * 1000000L);
	}
}
