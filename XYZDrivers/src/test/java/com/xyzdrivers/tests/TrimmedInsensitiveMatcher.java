/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyzdrivers.tests;

import org.mockito.ArgumentMatcher;

/**
 *
 * @author Toneo
 */
public class TrimmedInsensitiveMatcher implements ArgumentMatcher<String> {

    private final String expected;

    public TrimmedInsensitiveMatcher(String expected) {
        if (expected != null) {
            this.expected = expected.trim().toLowerCase();
        } else {
            this.expected = null;
        }
    }

    @Override
    public boolean matches(String actual) {
        // Special case required for matching null - otherwise we dereference null pointers!
        if (actual == null) {
            return expected == null;
        }

        return actual.trim().toLowerCase().equals(expected);
    }

    @Override
    public String toString() {
        return "<Trimmed insensitive> " + expected;
    }

}
