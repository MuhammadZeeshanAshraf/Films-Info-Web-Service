package com.ztech.enterpriseprogramming.DAO;

import com.ztech.enterpriseprogramming.Model.Film;
import com.ztech.enterpriseprogramming.Model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class FilmAdapter implements FilmInfo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public FilmAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Response insertFilm(Film filmInfo) {
        Response response = new Response();
        String insertIntoSql = "insert into films(title,year,director,stars,review) values ('" + filmInfo.getTitle() + "'," + filmInfo.getYear() + ",'" + filmInfo.getDirector() + "','" + filmInfo.getStars() + "','" + filmInfo.getReview() + "')";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        return connection.prepareStatement(insertIntoSql, new String[]{"id"});
                    }
                }, keyHolder);
        int id = 0;
        id = keyHolder.getKey().intValue();
        if (id == 0) {
            response.setStatus(false);
            response.setMessage("Films has not been added Successfully");
        } else {
            response.setStatus(true);
            response.setMessage("Films has been added Successfully---->ID of Inserted Film: " + id);
        }
        return response;
    }

    @Override
    public Response updateFilm(Film filmInfo) {
        Response response = new Response();
        String query = "UPDATE films SET title = ?, year = ?, director = ?, stars = ?, review = ?  where id = ?";
        int status = jdbcTemplate.update(query, filmInfo.getTitle(),
                filmInfo.getYear(), filmInfo.getDirector(), filmInfo.getStars(), filmInfo.getReview() , filmInfo.getId());
        if (status != 0) {
            response.setStatus(true);
            response.setMessage("Film data updated for ID " + filmInfo.getId());
        } else {
            response.setStatus(false);
            response.setMessage("No Film found with ID " + filmInfo.getId());
        }
        return response;
    }

    @Override
    public Response deleteFilm(int FilmID) {
        Response response = new Response();
        String sql = "DELETE FROM films WHERE id = ?";
        Object[] args = new Object[]{FilmID};
        if (jdbcTemplate.update(sql, args) == 1) {
            response.setStatus(true);
            response.setMessage("Film deleted successfully");
        } else {
            response.setStatus(false);
            response.setMessage("Film Doesn't Exists");
        }
        return response;
    }

    @Override
    public Film getFilm(int id) {
        return null;
    }

    @Override
    public List<Film> listFilm() {
        String sql = "SELECT * FROM films";
        List<Film> filmList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Film.class));
        return filmList;
    }

    @Override
    public List<Film> retrieveFilm(String searchStr) {
        String sql = "SELECT * FROM films where title='" + searchStr + "'";
        List<Film> filmList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Film.class));
        return filmList;
    }
}
