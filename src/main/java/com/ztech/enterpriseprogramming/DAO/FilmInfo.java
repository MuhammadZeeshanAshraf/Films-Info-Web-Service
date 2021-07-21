package com.ztech.enterpriseprogramming.DAO;

import com.ztech.enterpriseprogramming.Model.Film;
import com.ztech.enterpriseprogramming.Model.Response;

import java.util.Collection;

public interface FilmInfo {
    Response insertFilm (Film filmInfo);

    Response updateFilm (Film filmInfo);

    Response deleteFilm(int FilmID);

    Film getFilm(int id);

    Collection<Film> listFilm ();

    Collection<Film> retrieveFilm(String searchStr);
}
