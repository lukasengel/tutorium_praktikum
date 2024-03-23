package studiplayer.audio;

public class AudioFileFactory {
	public static AudioFile createAudioFile(String path) throws NotPlayableException {
		// Extract file extension
		final int lastDotIndex = path.lastIndexOf('.');
		final String extension = lastDotIndex > -1 ? path.substring(lastDotIndex + 1) : path;

		// Use file extension to determine which AudioFile instance to create
		return switch (extension.toLowerCase()) {
		case "ogg", "mp3" -> new TaggedFile(path);
		case "wav" -> new WavFile(path);
		default -> throw new NotPlayableException(path, String.format("Unknown suffix for AudioFile \"%s\"", path));
		};
	}
}
