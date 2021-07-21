package com.ztech.enterpriseprogramming.Controller;

import com.ztech.enterpriseprogramming.DAO.FilmAdapter;
import com.ztech.enterpriseprogramming.Model.Film;
import com.ztech.enterpriseprogramming.Model.Response;
import com.ztech.enterpriseprogramming.Utils.UtilSerivces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@RestController
public class FilmController extends BaseRestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String homepage() {
        return "HomePage";
    }

    @GetMapping("/getAll")
    public String getAllFilm(@RequestParam("format") String format) throws IOException, JAXBException {
        String response = "";
        try {
            FilmAdapter filmAdapter = new FilmAdapter(jdbcTemplate);
            List<Film> filmList = filmAdapter.listFilm();
            if (format.equalsIgnoreCase("xml")) {
                response = UtilSerivces.ListToXMLConverter(filmList);
            } else {
                response = UtilSerivces.ListToJSONConverter(filmList);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    @GetMapping("/add")
    public Response addFilm(@RequestParam("format") String format ,
                          @RequestParam("title") String title,
                          @RequestParam("year") String year,
                          @RequestParam("director") String director,
                          @RequestParam("stars") String stars,
                          @RequestParam("review") String review) throws IOException, JAXBException {
        Response response = new Response();
        try {
            Film film = new Film(title, Integer.parseInt(year) , director, stars ,review);
            FilmAdapter filmAdapter = new FilmAdapter(jdbcTemplate);
            response = filmAdapter.insertFilm(film);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    @GetMapping("/delete")
    public Response addFilm(@RequestParam("id") String id) throws IOException, JAXBException {
        Response response = new Response();
        try {
            FilmAdapter filmAdapter = new FilmAdapter(jdbcTemplate);
            response = filmAdapter.deleteFilm(Integer.parseInt(id));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    @GetMapping("/retrieveFilm")
    public String getAllFilm(@RequestParam("format") String format ,@RequestParam("title") String title) throws IOException, JAXBException {
        String response = "";
        try {
            FilmAdapter filmAdapter = new FilmAdapter(jdbcTemplate);
            List<Film> filmList = filmAdapter.retrieveFilm(title);
            if (format.equalsIgnoreCase("xml")) {
                response = UtilSerivces.ListToXMLConverter(filmList);
            } else {
                response = UtilSerivces.ListToJSONConverter(filmList);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    @GetMapping("/update")
    public Response updateFilm(@RequestParam("format") String format ,
                            @RequestParam("title") String title,
                            @RequestParam("year") String year,
                            @RequestParam("director") String director,
                            @RequestParam("stars") String stars,
                            @RequestParam("review") String review,
                               @RequestParam("id") String id) throws IOException, JAXBException {
        Response response = new Response();
        try {
            Film film = new Film(Integer.parseInt(id) , title, Integer.parseInt(year) , director, stars ,review);
            FilmAdapter filmAdapter = new FilmAdapter(jdbcTemplate);
            response = filmAdapter.updateFilm(film);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return response;
    }
}
