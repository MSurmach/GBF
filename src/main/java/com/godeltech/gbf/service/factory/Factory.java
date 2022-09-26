package com.godeltech.gbf.service.factory;

import com.godeltech.gbf.management.State;

public interface Factory<T> {
    T get(State state);
}
