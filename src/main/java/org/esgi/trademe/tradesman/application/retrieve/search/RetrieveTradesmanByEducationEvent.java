package org.esgi.trademe.tradesman.application.retrieve.search;


import org.esgi.trademe.tradesman.domain.WorkDomain;
import org.esgi.trademe.kernel.event.ApplicationEvent;

import java.util.List;

public class RetrieveTradesmanByEducationEvent implements ApplicationEvent {
    private final List<WorkDomain> domain;

    public RetrieveTradesmanByEducationEvent(List<WorkDomain> domain) {
        this.domain = domain;
    }

    public static RetrieveTradesmanByEducationEvent of(List<WorkDomain> domain) {
        return new RetrieveTradesmanByEducationEvent(domain);
    }
}