package com.godeltech.gbf.factory;

public interface Factory<T, P> {
    T get(P arg);
}
