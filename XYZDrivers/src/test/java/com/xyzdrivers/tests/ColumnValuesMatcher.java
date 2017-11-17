/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyzdrivers.tests;

import com.xyzdrivers.services.ColumnValuePair;
import java.util.List;
import org.mockito.ArgumentMatcher;

/**
 *
 * @author Toneo
 *
 * Ensures that an argument of type <code>List&lt;ColumnValuePair&gt;</code>
 * contains all and only the expected set of ColumnValuePairs.
 */
public class ColumnValuesMatcher implements ArgumentMatcher<List<ColumnValuePair>> {

    private final List<ColumnValuePair> expectedValues;

    public ColumnValuesMatcher(List<ColumnValuePair> expectedValues) {

        this.expectedValues = expectedValues;

    }

    @Override
    public boolean matches(List<ColumnValuePair> actualValues) {

        // If null compare differently so we avoid dereferencing a null reference
        if (actualValues == null || expectedValues == null) {
            return actualValues == expectedValues;
        }

        if (actualValues.size() != expectedValues.size()) {
            return false;
        }

        for (ColumnValuePair expected : expectedValues) {

            boolean found = false;
            for (ColumnValuePair actual : actualValues) {

                if (!actual.getColumnName().equals(expected.getColumnName())) {
                    continue;
                }

                if (!actual.getValue().equals(expected.getValue())) {
                    continue;
                }

                found = true;
                break;

            }

            if (!found) {
                return false;
            }

        }

        return true;

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        // Outputs the expected results in the form [a = b, c = d]
        builder.append("[");

        int lastIndex = expectedValues.size() - 1;

        // Append all but the second-to-last elements with a comma suffix
        for (int i = 0; i < lastIndex; i++) {
            builder.append(expectedValues.get(i).toString());
            builder.append(", ");
        }

        // The final element should not have a suffix.
        builder.append(expectedValues.get(lastIndex).toString());

        builder.append("]");

        return builder.toString();
    }

}
