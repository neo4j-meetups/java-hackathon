package org.neo4j.hackathons;

import io.dropwizard.views.View;

public class PersonView extends View
{
    public PersonView() {
        super("person.ftl");
    }
}
