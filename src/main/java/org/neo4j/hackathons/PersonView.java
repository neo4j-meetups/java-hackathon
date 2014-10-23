package org.neo4j.hackathons;

import java.util.ArrayList;
import java.util.List;

import io.dropwizard.views.View;

public class PersonView extends View
{
    public PersonView() {
        super("person.ftl");
    }

    public String getName() {
        return "";
    }

    public int getBorn() {
        return 0;
    }

    public List<Movie> getMoviesActedIn() {
        return new ArrayList<>();
    }

    public List<Movie> getMoviesDirected() {
        return new ArrayList<>();
    }


}
