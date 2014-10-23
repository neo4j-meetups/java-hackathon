package org.neo4j.hackathons;

import java.util.List;

import io.dropwizard.views.View;

public class PersonView extends View
{
    private String name;
    private int born;
    private List<Movie> moviesActedIn;
    private List<Movie> moviesDirected;

    public PersonView( String name, int born, List<Movie> moviesActedIn, List<Movie> moviesDirected ) {
        super("person.ftl");
        this.name = name;
        this.born = born;
        this.moviesActedIn = moviesActedIn;
        this.moviesDirected = moviesDirected;
    }

    public String getName()
    {
        return name;
    }

    public int getBorn()
    {
        return born;
    }

    public List<Movie> getMoviesActedIn()
    {
        return moviesActedIn;
    }

    public List<Movie> getMoviesDirected()
    {
        return moviesDirected;
    }
}
