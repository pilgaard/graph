/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.graph;

import java.util.ArrayList;
import java.util.List;
import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.traversal.*;

/**
 *
 * @author Emil
 */
public class Graph {

    private final String dept1 = "MATCH(p:Person {name: {name} })-[:ENDORSES]->(b:Person) RETURN DISTINCT b.id as id, b.name as name;";
    private final String dept2 = "MATCH(p:Person {name: {name} })-[:ENDORSES]->(:Person)-[:ENDORSES]->(b:Person) RETURN DISTINCT b.id as id, b.name as name;";
    private final String dept3 = "MATCH(p:Person {name: {name} })-[:ENDORSES]->(:Person)-[:ENDORSES]->(:Person)-[:ENDORSES]->(b:Person) RETURN DISTINCT b.id as id, b.name as name;";
    private final String dept4 = "MATCH(p:Person {name: {name} })-[:ENDORSES]->(:Person)-[:ENDORSES]->(:Person)-[:ENDORSES]->(:Person)-[:ENDORSES]->(b:Person) RETURN DISTINCT b.id as id, b.name as name;";
    private final String dept5 = "MATCH(p:Person {name: {name} })-[:ENDORSES]->(:Person)-[:ENDORSES]->(:Person)-[:ENDORSES]->(:Person)-[:ENDORSES]->(:Person)-[:ENDORSES]->(b:Person) RETURN DISTINCT b.id as id, b.name as name;";

    public Graph() {
    }

    public List<Person> callGraph(String name, int niveau) {
        List<Person> names = new ArrayList<>();
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "class"));
        Session session = driver.session();
        String dept = "";
        switch (niveau) {
            case 1:
                dept = dept1;
                break;
            case 2:
                dept = dept2;
                break;
            case 3:
                dept = dept3;
                break;
            case 4:
                dept = dept4;
                break;
            case 5:
                dept = dept5;
                break;
        }
        StatementResult result = session.run(dept, parameters("name", name));

        while (result.hasNext()) {
            Record record = result.next();
            Person p = new Person();
            p.setId(record.get("id").asInt());
            p.setName(record.get("name").asString());
            names.add(p);
        }
        session.close();
        driver.close();
        return names;
    }
}
