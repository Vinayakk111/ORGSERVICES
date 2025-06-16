package com.app.vpk.utils;

@FunctionalInterface
interface ErrorLogger<T, U, V> {
    void log(T t, U u, V v);
}