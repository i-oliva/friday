package com.inosphere.friday.address;

import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface StreetAddressType extends Predicate<String> {
    Address process(String address);

    BiFunction<String, String, Matcher> addressMatcher = (address, regex) -> {
        final Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(address);
    };

    BiFunction<Supplier<String>, Supplier<String>, Address> addressExtractor = (streetFnc, houseNumberFnc) -> {
        final String street = streetFnc.get().replace(",", "").trim();
        final String houseNumber = houseNumberFnc.get().replace(",", "").trim();
        return new Address(street, houseNumber);
    };
}
