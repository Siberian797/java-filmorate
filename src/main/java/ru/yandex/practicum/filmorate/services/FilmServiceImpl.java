package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.FilmNotExistException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FilmServiceImpl implements FilmService {
    private final List<Film> allFilms = new ArrayList<>();

    @Override
    public Film addFilm(Film film) {
        int id = film.getId();
        allFilms.add(film);
        log.info("Film {} has been added to list", id);
        return getFilmById(id);
    }

    @Override
    public Film editFilm(Film film) {
        int id = film.getId();
        Film oldFilm = getFilmById(id);
        if (oldFilm != null) {
            allFilms.remove(oldFilm);
            allFilms.add(film);
            log.info("Film {} has been edited", id);
            return getFilmById(id);
        } else {
            throw new FilmNotExistException();
        }
    }

    @Override
    public List<Film> getAll() {
        log.info("All films have been uploaded");
        return allFilms;
    }

    private Film getFilmById(int id) {
        for (Film film : allFilms) {
            if (film.getId() == id) {
                return film;
            }
        }
        log.error("Film {} was not found", id);
        return null;
    }
}
