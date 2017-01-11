package com.syject.data.api.entities;

import com.syject.data.entities.Options;

import java.util.List;

public class OptionsResponse {
    public final Integer count;
    public final Integer next;
    public final Integer previous;
    public final List<Options> results;

    public OptionsResponse(Integer count, Integer next, Integer previous, List<Options> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }
}
