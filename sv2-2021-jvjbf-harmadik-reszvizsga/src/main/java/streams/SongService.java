package streams;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SongService {
    private List<Song> songs = new LinkedList<>();

    public void addSong(Song song) {
        songs.add(song);
    }

    public Optional<Song> shortestSong() {
        return songs.stream()
                .min(Comparator.comparing(Song::getLength));
    }

    public List<Song> findSongByTitle(String title) {
        return songs.stream()
                .filter(song -> song.getTitle().equals(title))
                .toList();
    }

    public boolean isPerformerInSong(Song song, String performer) {
        return song.getPerformers().stream()
                .anyMatch(p -> p.equals(performer));
    }

    public List<String> titlesBeforeDate(LocalDate releasedBefore) {
        return songs.stream()
                .filter(s -> s.getRelease().isBefore(releasedBefore))
                .map(Song::getTitle)
                .toList();
    }
}
