package edu.hw5;

public final class RegularExpressions {
    public static final String SPECIAL_CHARACTERS = ".*[~!@#\\$%\\^&\\*\\|].*";
    public static final String RUSSIAN_LICENCE_PLATE =
            "^[АВЕКМНОРСТУХ](?!000)\\d{3}[АВЕКМНОРСТУХ]{2}([1-9]\\d{2}|(?!00)\\d{2})$";

    public static final String AT_LEAST_THREE_CHARACTERS_AND_THIRD_CHARACTER_IS_ZERO = "[01]{2}0[0|1]*";
    public static final String SAME_FIRST_AND_LAST_CHARACTERS = "0.*0|1.*1|0|1";
    public static final String LENGTH_BETWEEN_ONE_AND_TWO = "[0|1]{1,3}";
    public static final String ODD_LENGTH = "[0|1]([0|1]{2})*";

    private RegularExpressions() {
    }
}
