package com.app.vpk.utils;


@FunctionalInterface
public interface InfoLogger<T, U, V> {
    void log(T t, U u, V v);
}