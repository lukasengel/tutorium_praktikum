package studiplayer.audio;

import java.util.Map;

public class TaggedFile extends SampledFile {
	private String album;

	public TaggedFile() {
		super();
	}

	public TaggedFile(String path) throws NotPlayableException {
		super(path);
		this.readAndStoreTags();
	}

	public String getAlbum() {
		return this.album;
	}

	public void readAndStoreTags() throws NotPlayableException {
		try {
			Map<String, Object> map = studiplayer.basic.TagReader.readTags(this.getPathname());

			final String title = (String) map.get("title");
			final String author = (String) map.get("author");
			final String album = (String) map.get("album");

			this.title = title != null ? title.trim() : this.title;
			this.author = author != null ? author.trim() : this.author;
			this.album = album != null ? album.trim() : "";
			this.duration = (Long) map.get("duration");
		} catch (Exception e) {
			throw new NotPlayableException(this.getPathname(), e);
		}
	}

	@Override
	public String toString() {
		return this.album.isEmpty() ? String.format("%s - %s", super.toString(), this.formatDuration())
				: String.format("%s - %s - %s", super.toString(), this.album, this.formatDuration());
	}
}
