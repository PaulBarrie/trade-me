package org.esgi.trademe.trademan.application.retrieve.search;


import org.esgi.trademe.trademan.domain.WorkDomain;
import org.esgi.trademe.kernel.query.Query;

import java.util.List;

public final class RetrieveTradesmanByEducation implements Query {
    private final List<WorkDomain> domains;

    public RetrieveTradesmanByEducation(List<WorkDomain> domains) {
        this.domains = domains;
    }

    public List<WorkDomain> getDomains() {
        return domains;
    }
}
