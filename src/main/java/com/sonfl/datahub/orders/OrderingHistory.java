package com.sonfl.datahub.orders;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class OrderingHistory {

    private List<Long> history = new LinkedList<>();


    public void add(Long l) {
        history.add(0, l);
    }

    public List<Long> getLast(int n) {
        return history.subList(0, n < history.size() ? n : history.size());
    }

}
