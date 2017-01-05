package com.syject.data.api;

import java.util.List;

public class OptionsResponse {
    final Integer count;
    final Integer next;
    final Integer previous;
    final List<OptionsRequest> results;

    public OptionsResponse(Integer count, Integer next, Integer previous, List<OptionsRequest> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }
}
