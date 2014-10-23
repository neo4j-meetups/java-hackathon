package org.neo4j.hackathons;

import java.util.ArrayList;
import java.util.List;

import io.dropwizard.views.View;

public class MovieView extends View
{

    protected MovieView(  )
    {
        super("movie.ftl");
    }

    public String getTitle() {
        return "";
    }

    public int getReleased() {
        return 0;
    }

    public List<Person> getActors() {
        return new ArrayList<>();
    }

    public String getDirector() {
        return "";
    }
}
