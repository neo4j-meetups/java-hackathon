package org.neo4j.hackathons;

import java.util.ArrayList;
import java.util.List;

import io.dropwizard.views.View;

public class MovieListView extends View
{

    public MovieListView(  )
    {
        super("movie_list.ftl");
    }

    public List<Movie> getMovies () {
        return new ArrayList<>(  );
    }
}
