package com.inosphere.friday.address;

import com.google.common.collect.ImmutableSet;

import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreetNameFollowedByHouseNumberType implements StreetAddressType {
    private static final String REGEX_CONDITION_WITHOUT_NUMBER_ABBREV = "^(\\D[\\D0-9]+)(\\s|,)([0-9][0-9A-Za-z\\s]*)$";
    private static final String REGEX_CONDITION_WITH_NUMBER_ABBREV = "^([\\D0-9]+)(%s){1}(?:\\s*)([0-9][0-9A-Za-z\\s]*)?+$";
    private static final Set<String> STREET_NUMBER_ABBREV = ImmutableSet.of("No", "Nr");

    @Override
    public boolean test(String s) {
        return s != null && (s.matches(REGEX_CONDITION_WITHOUT_NUMBER_ABBREV) || isStreetWithNumberAbbrev(s));
    }

    @Override
    public Address process(String streetAddress) {
        final String regex = (isStreetWithNumberAbbrev(streetAddress)) ? getStreetWithNumberAbbrevRegex(streetAddress) : REGEX_CONDITION_WITHOUT_NUMBER_ABBREV;

        // For simplicity we assume the street abbrev has the first letter capitalized and the rest lowercase all the time
        final Matcher matcher = addressMatcher.apply(streetAddress, regex);
        return (matcher.find()) ? addressExtractor.apply(streetSupplierFnc(matcher), houseNumberSupplierFnc(matcher)) : new Address(streetAddress);
    }

    private boolean isStreetWithNumberAbbrev(String str) {
        return STREET_NUMBER_ABBREV.stream()
                .map(abbrev -> String.format(REGEX_CONDITION_WITH_NUMBER_ABBREV, abbrev))
                .anyMatch(str::matches);
    }

    private String getStreetWithNumberAbbrevRegex(String str) {
        return STREET_NUMBER_ABBREV.stream()
                .map(abbrev -> String.format(REGEX_CONDITION_WITH_NUMBER_ABBREV, abbrev))
                .filter(str::matches)
                .findFirst().orElse(".*");
    }

    private Supplier<String> streetSupplierFnc(final Matcher matcher) {
        return () -> matcher.group(1);
    }

    private Supplier<String> houseNumberSupplierFnc(final Matcher matcher) {
        return () -> IntStream.range(2, matcher.groupCount() + 1)
                .mapToObj(matcher::group)
                .map(s -> s.replace(",", ""))
                .map(String::trim)
                .collect(Collectors.joining(" "));
    }
}