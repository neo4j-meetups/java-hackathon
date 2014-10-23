package org.neo4j.hackathons;

import java.net.URI;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class MoviesApplication extends Application<MoviesConfiguration>
{
    public static void main( String[] args ) throws Exception
    {
        new MoviesApplication().run( args );
    }

    @Override
    public String getName()
    {
        return "The Movies Graph";
    }

    @Override
    public void initialize( Bootstrap<MoviesConfiguration> bootstrap )
    {
        bootstrap.addBundle( new ViewBundle() );
        bootstrap.addBundle( new AssetsBundle( "/assets/" ) );
    }

    @Override
    public void run( MoviesConfiguration configuration, Environment environment )
    {
        Neo4jDatabase neo4jDatabase = new Neo4jDatabase( URI.create( "http://localhost:7474" ) );

        final MoviesResource resource = new MoviesResource( neo4jDatabase );
        environment.jersey().register( resource );
    }

}
