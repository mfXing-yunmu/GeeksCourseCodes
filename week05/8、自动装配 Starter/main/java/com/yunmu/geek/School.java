package com.yunmu.geek;

import java.util.List;

/**
 * @author mfXing
 */
public class School {
    private List<Klass> klasses;

    public School(List<Klass> klasses) {
        this.klasses = klasses;
    }

    @Override
    public String toString() {
        return "School::" + klasses.toString();
    }
}
