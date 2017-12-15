/*
 * Copyright (c) 2002-2017 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.doc.tools;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.visualization.graphviz.AsciiDocStyle;
import org.neo4j.visualization.graphviz.GraphStyle;
import org.neo4j.visualization.graphviz.GraphvizWriter;
import org.neo4j.walk.Walker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class SpatialGraphVizHelper extends org.neo4j.visualization.asciidoc.AsciidocHelper {

    private static final String ILLEGAL_STRINGS = "[:\\(\\)\t;&/\\\\]";

    public static String createGraphVizWithNodeId(
            String title, GraphDatabaseService graph, String identifier
    ) {
        return createGraphViz(
                title, graph, identifier, AsciiDocStyle.withAutomaticRelationshipTypeColors(), ""
        );
    }

    public static String createGraphViz(String title, GraphDatabaseService graph, String identifier, GraphStyle graphStyle, String graphvizOptions) {
        try (Transaction tx = graph.beginTx()) {
            GraphvizWriter writer = new GraphvizWriter( graphStyle );
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                writer.emit( out, Walker.fullGraph( graph ) );
            } catch (IOException e) {
                e.printStackTrace();
            }

            String safeTitle = title.replaceAll( ILLEGAL_STRINGS, "" );

            tx.success();

            String fontsDir = "target/tools/bin/fonts";
            String colorSet = "neoviz";
            String graphAttrs = "";

            try {
                String result = "." + title + "\n[graphviz, "
                        + (safeTitle + "-" + identifier).replace( " ", "-" )
                        + ", svg]\n"
                        + "----\n" +
                        new GraphVizConfig(
                                out.toString( StandardCharsets.UTF_8.name()),
                                fontsDir,
                                colorSet, graphAttrs
                        ).get() + "\n" +
                        "----\n";
                System.out.println(result);
                return result;
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}