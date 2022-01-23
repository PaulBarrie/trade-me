package org.esgi.trademe.trademan.application.retrieve.by_id;


import org.esgi.trademe.trademan.domain.TradesmanID;
import org.esgi.trademe.kernel.query.Query;

public final class RetrieveTradesmanByID implements Query {
    private final TradesmanID id;

    public RetrieveTradesmanByID(TradesmanID id) {
        this.id = id;
    }

    public TradesmanID getId() {
        return id;
    }
}
