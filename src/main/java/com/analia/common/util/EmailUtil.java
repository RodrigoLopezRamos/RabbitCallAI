package com.analia.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmailUtil {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private EmailUtil() {
    }

    public static boolean validate(final String emailString) {
        Matcher result = EmailUtil.pattern.matcher(emailString);
        return result.matches();
    }
}