package ru.excel_parser.utils;

import java.io.IOException;

/**
 * Created by Dmitrii on 11.03.2016.
 */
@FunctionalInterface
public interface FactoryConverterIO<T, U> {

    U get(T arg) throws IOException;
}
