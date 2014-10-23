package org.neo4j.hackathons;

import java.util.List;

import io.dropwizard.views.View;

public class MovieListView extends View
{
    private List<Movie> movies;

    public MovieListView( List<Movie> movies ) {
        super("movie_list.ftl");
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
