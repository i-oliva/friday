package com.inosphere.friday.address;

import com.google.common.collect.ImmutableList;
import lombok.Setter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StreetAddressConverter implements Converter<String, Address> {
    private @Setter List<StreetAddressType> addressTypes = ImmutableList.of(
            new StreetNameFollowedByHouseNumberType(),
            new StreetNamePrefixedByHouseNumberType());

    @Override
    public Address convert(final String street) {
        final List<Address> convertedStreets = addressTypes.stream()
                .filter(at -> at.test(street))
                .map(at -> at.process(street))
                .collect(Collectors.toList());

        return convertedStreets.size() == 1 ? convertedStreets.get(0) : new Address(street);
    }
}
