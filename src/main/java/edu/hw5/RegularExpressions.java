package edu.hw5;

public final class RegularExpressions {
    public static final String SPECIAL_CHARACTERS = ".*[~!@#\\$%\\^&\\*\\|].*";
    public static final String RUSSIAN_LICENCE_PLATE =
            "^[АВЕКМНОРСТУХ](?!000)\\d{3}[АВЕКМНОРСТУХ]{2}([1-9]\\d{2}|(?!00)\\d{2})$";

    public static final String AT_LEAST_THREE_CHARACTERS_AND_THIRD_CHARACTER_IS_ZERO = "[01]{2}0[0|1]*";
    public static final String SAME_FIRST_AND_LAST_CHARACTERS = "0.*0|1.*1|0|1";
    public static final String LENGTH_BETWEEN_ONE_AND_TWO = "[0|1]{1,3}";
    public static final String ODD_LENGTH;

    public static final String STARTS_WITH_ZERO_AND_HAS_ODD_LENGTH_OR_STARTS_WITH_ONE_AND_HAS_EVEN_LENGTH;

    static {
        String evenLength = "([0|1]{2})*";
        ODD_LENGTH = "[0|1]" + evenLength;
        STARTS_WITH_ZERO_AND_HAS_ODD_LENGTH_OR_STARTS_WITH_ONE_AND_HAS_EVEN_LENGTH =
                "0" + evenLength + "|1" + ODD_LENGTH;
    }

    public static final String NUMBER_OF_ZEROS_IS_A_MULTIPLE_OF_3 = "1*(1*01*01*01*)*";
    public static final String ANYTHING_BUT_11_OR_111 = "(?!^11$|^111$)[01]*";
    public static final String EVERY_ODD_CHARACTER_IS_1 = "(1[01])*1?";
    public static final String AT_LEAST_TWO_ZEROS_AND_NO_MORE_THAN_ONE_ONE = "1?0{2,}|01?0+|0{2,}1?";
    public static final String NO_CONSECUTIVE_ONES = "(?![01]*11[01]*)[01]*";

    private RegularExpressions() {
    }
}
