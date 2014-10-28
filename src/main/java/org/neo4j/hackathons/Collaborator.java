package org.neo4j.hackathons;

public class Collaborator
{
    private Person person;
    private int times;

    public Collaborator(Person person, int times)
    {
        this.person = person;
        this.times = times;
    }

    public String getName() {
        return person.getName();
    }

    public int getTimes()
    {
        return times;
    }
}
