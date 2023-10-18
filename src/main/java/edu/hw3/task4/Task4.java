package edu.hw3.task4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Task4 {
    private static final String ROMAN_ONE = "I";
    private static final String ROMAN_FIVE = "V";
    private static final String ROMAN_TEN = "X";
    private static final String ROMAN_FIFTY = "L";
    private static final String ROMAN_HUNDRED = "C";
    private static final String ROMAN_FIVE_HUNDRED = "D";
    private static final String ROMAN_THOUSAND = "M";
    private static final int RADIX = 10;
    private static final int MINIMAL_NUMBER_ALLOWED = 1;
    private static final int MAXIMAL_NUMBER_ALLOWED = 3999;
    private static final int MAX_UNIT_COUNT = 3;

    private static final List<List<String>> LOOKUP_TABLES = List.of(
            makeLookupTable(ROMAN_ONE, ROMAN_FIVE, ROMAN_TEN),
            makeLookupTable(ROMAN_TEN, ROMAN_FIFTY, ROMAN_HUNDRED),
            makeLookupTable(ROMAN_HUNDRED, ROMAN_FIVE_HUNDRED, ROMAN_THOUSAND),
            makeThousandsLookupTable()
    );

    public static String convertToRoman(int number) {
        if (number < MINIMAL_NUMBER_ALLOWED || number > MAXIMAL_NUMBER_ALLOWED) {
            throw new IllegalArgumentException("The number must be between "
                    + MINIMAL_NUMBER_ALLOWED + " and " + MAXIMAL_NUMBER_ALLOWED + ": " + number);
        }

        int num = number;
        StringBuilder sb = new StringBuilder();
        for (List<String> lookupTable : LOOKUP_TABLES) {
            sb.append(lookupTable.get(num % RADIX));
            num /= RADIX;
        }
        return sb.reverse().toString();
    }

    private static List<String> makeThousandsLookupTable() {
        List<String> lookupTable = new ArrayList<>(MAX_UNIT_COUNT + 1);
        addUnits(ROMAN_THOUSAND, "", lookupTable);
        return Collections.unmodifiableList(lookupTable);
    }

    private static List<String> makeLookupTable(String one, String five, String ten) {
        List<String> lookupTable = new ArrayList<>(RADIX);

        // the strings are reversed (e.g. VI for 4 and IIIV for 8)
        // because the StringBuilder is reversed in convertToRoman
        addUnits(one, "", lookupTable);
        lookupTable.add(five + one);
        addUnits(one, five, lookupTable);
        lookupTable.add(ten + one);

        return Collections.unmodifiableList(lookupTable);
    }

    private static void addUnits(String one, String five, List<String> lookupTable) {
        for (int ones = 0; ones <= MAX_UNIT_COUNT; ones++) {
            lookupTable.add(one.repeat(ones) + five);
        }
    }

    private Task4() {
    }
}
