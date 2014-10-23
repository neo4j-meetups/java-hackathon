package org.neo4j.hackathons;

import io.dropwizard.views.View;

public class MovieView extends View
{

    protected MovieView(  )
    {
        super("movie.ftl");
    }
}
