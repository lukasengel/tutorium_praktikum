public abstract class SampledFile extends AudioFile {
	protected long duration;

	public SampledFile() {
		super();
	}

	public SampledFile(String path) {
		super(path);
	}

	public long getDuration() {
		return this.duration;
	}

	@Override
	public void play() {
		studiplayer.basic.BasicPlayer.play(this.getPathname());
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
		final long minutes = Math.floorDiv(timeInMicroSeconds, 60000000);
		final long seconds = Math.floorDiv(timeInMicroSeconds % 60000000, 1000000);

		return String.format("%02d:%02d", minutes, seconds);
	}
}
