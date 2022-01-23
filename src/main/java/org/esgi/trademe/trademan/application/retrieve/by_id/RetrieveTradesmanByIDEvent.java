package org.esgi.trademe.trademan.application.retrieve.by_id;


import org.esgi.trademe.trademan.domain.TradesmanID;
import org.esgi.trademe.kernel.event.ApplicationEvent;

public final class RetrieveTradesmanByIDEvent implements ApplicationEvent {
    private final TradesmanID tradesmanID;

    public RetrieveTradesmanByIDEvent(TradesmanID tradesmanID) {
        this.tradesmanID = tradesmanID;
    }

    public static RetrieveTradesmanByIDEvent of(TradesmanID tradesmanID) {
        return new RetrieveTradesmanByIDEvent(tradesmanID);
    }
}
