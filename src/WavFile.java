import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {
	public WavFile() {
		super();
	}

	public WavFile(String path) {
		super(path);
		readAndSetDurationFromFile();
	}

	public void readAndSetDurationFromFile() {
		WavParamReader.readParams(this.getPathname());
		this.duration = computeDuration(WavParamReader.getNumberOfFrames(), WavParamReader.getFrameRate());
	}

	@Override
	public String toString() {
		return String.format("%s - %s", super.toString(), this.formatDuration());
	}

	@Override
	public String[] fields() {
		// TODO Auto-generated method stub
		return null;
	}

	public static long computeDuration(long numberOfFrames, float frameRate) {
		return Math.round((numberOfFrames / frameRate) * 1000000L);
	}
}
