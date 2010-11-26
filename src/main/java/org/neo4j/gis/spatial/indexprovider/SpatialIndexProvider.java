package org.neo4j.gis.spatial.indexprovider;

import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexProvider;
import org.neo4j.graphdb.index.RelationshipIndex;
import org.neo4j.helpers.Service;
import org.neo4j.kernel.Config;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.KernelExtension;

@Service.Implementation( KernelExtension.class )
public class SpatialIndexProvider extends IndexProvider
{

    private static final String DATASOURCE_NAME = "nioneodb";
    private EmbeddedGraphDatabase db;


    public SpatialIndexProvider( )
    {
        super( "spatial" );
        
    }


    @Override
    protected void load( KernelData kernel )
    {
        System.out.println( "loading spatial index" );
        db = (EmbeddedGraphDatabase)kernel.graphDatabase();
//        db.getConfig().getTxModule().registerDataSource( Config.DEFAULT_DATA_SOURCE_NAME,
//                Config., resourceId, params );
    }


    @Override
    public String getDataSourceName()
    {
        return DATASOURCE_NAME;
    }


    @Override
    public Index<Node> nodeIndex( String indexName, Map<String, String> config )
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public RelationshipIndex relationshipIndex( String indexName,
            Map<String, String> config )
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Map<String, String> fillInDefaults( Map<String, String> config )
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public boolean configMatches( Map<String, String> storedConfig,
            Map<String, String> config )
    {
        // TODO Auto-generated method stub
        return false;
    }

}
