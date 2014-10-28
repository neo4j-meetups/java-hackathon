package org.neo4j.hackathons;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        // Make sure Neo4j Driver is registered
        try
        {
            Class.forName("org.neo4j.jdbc.Driver");
            Connection connection = DriverManager.getConnection( "jdbc:neo4j://localhost:7474/" );

            final MoviesResource resource = new MoviesResource( connection );
            environment.jersey().register( resource );
        }
        catch ( ClassNotFoundException | SQLException e )
        {
            e.printStackTrace();
        }
    }

}
