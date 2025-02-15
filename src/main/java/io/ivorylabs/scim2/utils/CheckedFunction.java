package io.ivorylabs.scim2.utils;

@FunctionalInterface
public interface CheckedFunction<T, U, R> {

    R apply(T t, U u) throws Exception;

}
