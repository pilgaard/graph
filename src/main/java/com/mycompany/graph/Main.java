/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.graph;

import java.util.List;

/**
 *
 * @author Emil
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Person> names = getNames();
        int size = names.size();
        graph(names, size);
        sql(names, size);
    }

    public static void graph(List<Person> names, int size) {
        for (int j = 1; j < 6; j++) {
        long time = 0;
        for (int i = 0; i < 20; i++) {
            int number = (int) (Math.random() * size);
            Person p = names.get(number);
            time += testGraph(p, j);
        }
            System.out.println("Graph dept " + j + "= " + time / 20 + " miliseconds");
        }
    }

    public static void sql(List<Person> names, int size) {
        for (int j = 1; j < 6; j++) {
            long time = 0;
            for (int i = 0; i < 20; i++) {
                int number = (int) (Math.random() * size);
                Person p = names.get(number);
                time += testSQL(p, j);
            }
            System.out.println("SQL dept " + j + "= " + time / 20 + " miliseconds");
        }
    }

    public static List<Person> getNames() {
        SQL endors = new SQL();
        return endors.getNames();
    }

    public static long testSQL(Person p, int dept) {
        SQL endors = new SQL();
        long start = System.currentTimeMillis();
        endors.makeCall(p.getId(), dept);
        long stop = System.currentTimeMillis();

        return stop - start;
    }

    public static long testGraph(Person p, int dept) {
        Graph graph = new Graph();
        long start = System.currentTimeMillis();
        graph.callGraph(p.getName(), dept);
        long stop = System.currentTimeMillis();

        return stop - start;
    }
}
