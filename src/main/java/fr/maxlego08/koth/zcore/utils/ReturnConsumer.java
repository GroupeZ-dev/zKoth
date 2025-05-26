package fr.maxlego08.koth.zcore.utils;

@FunctionalInterface
public interface ReturnConsumer<T> {

    String accept(T t);

}
