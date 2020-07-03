package io.github.todo.config;

public class TodoConfig {

    private final String username="todo";
    private final String password="todo";
    private final String dbname="todaDatabase";
    private final String clusterName="Cluster0";

    public final String db_URL="mongodb+srv://"
            .concat(username)
            .concat(":")
            .concat(password)
            .concat("@")
            .concat(clusterName)
            .concat(".drqmm.mongodb.net/")
            .concat(dbname)
            .concat("?retryWrites=true&w=majority");



}
