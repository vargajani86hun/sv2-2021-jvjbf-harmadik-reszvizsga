package movietheatres;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.*;

public class MovieTheatreService {
    private Map<String, List<Movie>> shows = new HashMap<>();

    public Map<String, List<Movie>> getShows() {
        return Collections.unmodifiableMap(shows);
    }

    public void readFromFile(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("[-;]");

                if (!shows.containsKey(values[0])) {
                    addTheatre(values[0]);
                }

                shows.get(values[0]).add(new Movie(values[1], LocalTime.parse(values[2])));
                shows.get(values[0]).sort(Comparator.comparing(Movie::getStartTime));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not read file!", ioe);
        }
    }

    public List<String> findMovie(String title) {
        List<String> result = new ArrayList<>();
        for (String theatre: shows.keySet()) {
            if (shows.get(theatre).stream().map(Movie::getTitle).anyMatch(t -> t.equals(title))) {
                result.add(theatre);
            }
        }
        return result;
    }

    public LocalTime findLatestShow(String title) {
        LocalTime latestTime = LocalTime.MIN;
        for (String theatre: shows.keySet()) {
            LocalTime actual = shows.get(theatre).stream().
                    filter(m -> m.getTitle().equals(title))
                    .map(Movie::getStartTime)
                    .max(Comparator.naturalOrder()).orElse(LocalTime.MIN);
            if (latestTime.isBefore(actual)) {
                latestTime = actual;
            }
        }
        if (latestTime == LocalTime.MIN) {
            throw new IllegalArgumentException();
        }
        return latestTime;
    }

    private void addTheatre(String name) {
        shows.put(name, new LinkedList<>());
    }
}
