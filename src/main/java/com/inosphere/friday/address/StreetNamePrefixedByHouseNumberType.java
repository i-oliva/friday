package com.inosphere.friday.address;

import java.util.function.Supplier;
import java.util.regex.Matcher;

public class StreetNamePrefixedByHouseNumberType implements StreetAddressType {
    private static final String REGEX_CONDITION_WITH_COMMA = "^([0-9][0-9A-Za-z\\s]*)(?:,)(\\D.*)";
    private static final String REGEX_CONDITION_FALLBACK = "^([0-9][0-9A-Za-z]*)(\\D.*)";

    @Override
    public boolean test(String s) {
        return s != null && (s.matches(REGEX_CONDITION_WITH_COMMA) || s.matches(REGEX_CONDITION_FALLBACK));
    }

    @Override
    public Address process(String address) {
        final String regex = (address.matches(REGEX_CONDITION_WITH_COMMA)) ? REGEX_CONDITION_WITH_COMMA : REGEX_CONDITION_FALLBACK;
        // For simplicity we assume the street abbrev has the first letter capitalized and the rest lowercase all the time
        final Matcher matcher = addressMatcher.apply(address, regex);
        return (matcher.find()) ? addressExtractor.apply(streetSupplierFnc(matcher), houseNumberSupplierFnc(matcher)) : new Address(address);
    }

    private Supplier<String> streetSupplierFnc(final Matcher matcher) {
        return () -> matcher.group(2);
    }

    private Supplier<String> houseNumberSupplierFnc(final Matcher matcher) {
        return () -> matcher.group(1);
    }
}
