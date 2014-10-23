package org.neo4j.hackathons;


public class PersonListView extends io.dropwizard.views.View
{

    protected PersonListView(  )
    {
        super( "person_list.ftl" );
    }
}
