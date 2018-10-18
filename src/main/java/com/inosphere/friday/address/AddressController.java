package com.inosphere.friday.address;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class AddressController {
    private final StreetAddressConverter addressConverter;

    // Lets have a get method for better testability
    @GetMapping("/{street}")
    public Address processAddress(@PathVariable String street) {
        return addressConverter.convert(street);
    }
}
