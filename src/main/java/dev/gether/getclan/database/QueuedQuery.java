/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package dev.gether.getclan.database;

import java.util.List;

public class QueuedQuery {
    private final String sql;
    private final List<Object> parameters;

    public QueuedQuery(String sql, List<Object> parameters) {
        this.sql = sql;
        this.parameters = parameters;
    }

    public String getSql() {
        return this.sql;
    }

    public List<Object> getParameters() {
        return this.parameters;
    }
}

