package studiplayer.audio;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class ControllablePlayListIterator implements Iterator<AudioFile> {
	private List<AudioFile> list;
	private int current;

	public ControllablePlayListIterator(List<AudioFile> list) {
		this.list = list;
		this.current = 0;
	}

	public ControllablePlayListIterator(List<AudioFile> list, String search, SortCriterion sortCriterion) {
		// Callback for filter
		Predicate<AudioFile> filterBySearch = (AudioFile audioFile) -> {

			return (search == null || search.isBlank())
					|| audioFile.getAuthor().toLowerCase().contains(search.toLowerCase())
					|| audioFile.getTitle().toLowerCase().contains(search.toLowerCase())
					|| (audioFile instanceof TaggedFile && ((TaggedFile) audioFile).getAlbum().toLowerCase().contains(search.toLowerCase()));
		};

		// Determine which Comparable implementation to use
		Comparator<AudioFile> comparatorBySortCriterion = switch (sortCriterion) {
		case SortCriterion.ALBUM -> new AlbumComparator();
		case SortCriterion.AUTHOR -> new AuthorComparator();
		case SortCriterion.DURATION -> new DurationComparator();
		case SortCriterion.TITLE -> new TitleComparator();
		default -> Comparator.comparingInt((af) -> 0);
		};

		this.list = list.stream().filter(filterBySearch).sorted(comparatorBySortCriterion).toList();
		this.current = 0;
	}

	@Override
	public boolean hasNext() {
		return (this.current) < this.list.size();
	}

	@Override
	public AudioFile next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}

		return this.list.get(this.current++);
	}

	public AudioFile jumpToAudioFile(AudioFile file) {
		final int index = list.indexOf(file);

		if (index != -1) {
			this.current = index + 1;
			return file;
		}

		return null;
	}
}
