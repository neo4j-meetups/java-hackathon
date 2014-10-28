package org.neo4j.hackathons;

import java.util.ArrayList;
import java.util.List;

import io.dropwizard.views.View;

public class PersonView extends View
{
    private final String name;
    private final int born;
    private final List<Movie> movies;
    private List<Movie> moviesDirected;
    private List<Collaborator> collaborators;

    public PersonView( String name, int born, List<Movie> movies, List<Movie> moviesDirected, List<Collaborator>
            collaborators ) {
        super("person.ftl");
        this.name = name;
        this.born = born;
        this.movies = movies;
        this.moviesDirected = moviesDirected;
        this.collaborators = collaborators;
    }

    public String getName() {
        return name;
    }

    public int getBorn() {
        return born;
        
    }

    public List<Movie> getMoviesActedIn() {
        return movies;
    }

    public List<Movie> getMoviesDirected() {
        return moviesDirected;
    }

    public List<Collaborator> getCollaborators()
    {
        return collaborators;
    }
}
