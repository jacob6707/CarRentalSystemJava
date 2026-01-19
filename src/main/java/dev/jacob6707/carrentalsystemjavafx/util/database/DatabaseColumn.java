package dev.jacob6707.carrentalsystemjavafx.util.database;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseColumn {
    String value();
}
