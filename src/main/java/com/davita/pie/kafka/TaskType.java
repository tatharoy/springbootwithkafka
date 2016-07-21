package com.davita.pie.kafka;

/**
 * Created by taroy on 4/28/16.
 */
public enum TaskType {


    CREATE("create","create"),

    UPDATE("update","update"),

    DELETE("delete","delete");

    private final String type;
    private  final String partition;


    private TaskType(String atype, String apartition) {
        type = atype;
        partition = apartition;
    }

    public String getType() {
        return type;
    }

    public String getPartition() {
        return partition;
    }
}
