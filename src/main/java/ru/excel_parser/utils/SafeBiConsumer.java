package ru.excel_parser.utils;

import java.util.function.BiConsumer;

/**
 * Created by Dmitrii on 11.03.2016.
 */
@FunctionalInterface
public interface SafeBiConsumer<T, U>  {

    void accept(T t, U u) throws Exception;
}
