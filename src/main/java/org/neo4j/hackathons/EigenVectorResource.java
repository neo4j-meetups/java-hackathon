package org.neo4j.hackathons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphalgo.CostEvaluator;
import org.neo4j.graphalgo.impl.centrality.EigenvectorCentralityPower;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.core.NodeProxy;

@Path("/ev")
public class EigenVectorResource
{
    private GraphDatabaseService db;

    static class NodeCentrality {
        private String name;
        private Double centrality;

        NodeCentrality(String name, Double centrality) {

            this.name = name;
            this.centrality = centrality;
        }

        @Override
        public String toString()
        {
            return "NodeCentrality{" +
                    "name='" + name + '\'' +
                    ", centrality=" + centrality +
                    '}';
        }
    }

    private static final DynamicRelationshipType REACTION = DynamicRelationshipType.withName( "reaction" );

    public EigenVectorResource(GraphDatabaseService db)
    {
        this.db = db;
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public JsonNode index()
    {



        ExecutionResult result = new ExecutionEngine( db ).execute( "MATCH (hdac:Genes) WHERE hdac.gene_name =~ " +
                "\"HDAC\\\\d{1," +
                "}\" AND NOT (hdac.chromosome " +
                "=~ '.*PATCH.*') WITH COLLECT(DISTINCT id(hdac)) AS hdacs MATCH (hdac1) WHERE id(hdac1) in hdacs " +
                "MATCH (hdac1)-[:reaction]->(neighbour1) WITH hdacs + COLLECT(DISTINCT id(neighbour1)) AS " +
                "hdacAndFriends MATCH (haf1) WHERE id(haf1) in hdacAndFriends MATCH (haf2) WHERE id(haf2) in " +
                "hdacAndFriends WITH haf1, haf2 RETURN DISTINCT haf1", new HashMap<String, Object>() );

        List<Long> nodeIds = new ArrayList<>();

        for ( Map<String, Object> row : result )
        {
            NodeProxy node = (NodeProxy) row.get( "haf1" );
            nodeIds.add(node.getId());
        }

        List<NodeCentrality> centralities = new ArrayList<>();
        try ( Transaction tx = db.beginTx() )
        {

            Set<Node> nodes = new HashSet<>(  );
            Set<Relationship> relationships = new HashSet<>(  );
            for ( Long nodeId : nodeIds )
            {
                Node node = db.getNodeById( nodeId );
                nodes.add(node);

                Iterable<Relationship> rels = node.getRelationships( Direction.OUTGOING, REACTION );

                for ( Relationship rel : rels )
                {
                    relationships.add( rel );
                }
            }

            EigenvectorCentralityPower ev = new EigenvectorCentralityPower( Direction.OUTGOING, new CostEvaluator<Double>()

            {
                @Override
                public Double getCost( Relationship relationship, Direction direction )
                {
                    return 1.0;
                }
            }, nodes, relationships
                    , 1.0 );

            ev.calculate();

            ev.runIterations( 5 );

            for ( Node node : nodes )
            {
                Double centrality = ev.getCentrality( node );

                if(centrality == null) {
                    centrality = 0.0;
                }

                centralities.add( new NodeCentrality( node.getProperty( "gene_name" ).toString(), centrality ) );

            }
        }

        Collections.sort( centralities, new Comparator<NodeCentrality>()
        {
            @Override
            public int compare( NodeCentrality o1, NodeCentrality o2 )
            {
                if ( o1.centrality == null )
                {
                    return 1;
                }
                if ( o2.centrality == null )
                {
                    return 1;
                }
                return -1 * o1.centrality.compareTo( o2.centrality );
            }
        } );


        ObjectNode root = JsonNodeFactory.instance.objectNode();


        for ( NodeCentrality centrality : centralities )
        {
            root.put(centrality.name, centrality.centrality);
        }

        return root;

    }
}
