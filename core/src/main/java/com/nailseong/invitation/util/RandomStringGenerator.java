package com.nailseong.invitation.util;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomStringGenerator {

    private RandomStringGenerator() {
    }

    public static String get(final int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
