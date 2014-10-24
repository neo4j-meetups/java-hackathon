package org.neo4j.hackathons;

public class Comment
{

    private String name;
    private String text;
    private long date;

    public Comment( String name, String text, long date )
    {
        this.name = name;
        this.text = text;
        this.date = date;
    }

    public String getName()
    {
        return name;
    }

    public String getText()
    {
        return text;
    }

    public long getDate()
    {
        return date;
    }
}
