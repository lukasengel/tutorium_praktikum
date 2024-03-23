package studiplayer.audio;

public abstract class SampledFile extends AudioFile {
	protected long duration;

	public SampledFile() {
		super();
	}

	public SampledFile(String path) throws NotPlayableException {
		super(path);
	}

	public long getDuration() {
		return this.duration;
	}

	@Override
	public void play() throws NotPlayableException {
		try {
			studiplayer.basic.BasicPlayer.play(this.getPathname());
		} catch (Exception e) {
			throw new NotPlayableException(this.getPathname(), e);
		}
	}

	@Override
	public void togglePause() {
		studiplayer.basic.BasicPlayer.togglePause();
	}

	@Override
	public void stop() {
		studiplayer.basic.BasicPlayer.stop();
	}

	@Override
	public String formatDuration() {
		return timeFormatter(this.duration);
	}

	@Override
	public String formatPosition() {
		return timeFormatter(studiplayer.basic.BasicPlayer.getPosition());
	}

	public static String timeFormatter(long timeInMicroSeconds) {
		if (timeInMicroSeconds < 0) {
			throw new IllegalArgumentException("Illegal number of microsoconds: " + timeInMicroSeconds);
		}

		final long minutes = Math.floorDiv(timeInMicroSeconds, 60000000);
		final long seconds = Math.floorDiv(timeInMicroSeconds % 60000000, 1000000);

		if (minutes > 99) {
			throw new IllegalArgumentException("Number of minutes exceeds allowed format: " + minutes);
		}

		return String.format("%02d:%02d", minutes, seconds);
	}
}
