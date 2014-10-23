package org.neo4j.hackathons;

import java.util.List;

import io.dropwizard.views.View;

public class MovieView extends View
{

    private String title;
    private int released;
    private List<Person> actors;
    private String director;

    public MovieView( String title, int released, List<Person> actors, String director )
    {
        super("movie.ftl");
        this.title = title;
        this.released = released;
        this.actors = actors;
        this.director = director;
    }

    public String getTitle()
    {
        return title;
    }

    public int getReleased()
    {
        return released;
    }

    public List<Person> getActors()
    {
        return actors;
    }

    public String getDirector()
    {
        return director;
    }
}
