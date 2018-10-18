package com.inosphere.friday.address;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class StreetNameFollowedByHouseNumberTypeTest {
    private StreetNameFollowedByHouseNumberType underTest;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Winterallee 3", true, new Address("Winterallee", "3")},
                {"Winterallee444 3", true, new Address("Winterallee444", "3")},
                {"Blaufeldweg 123B", true, new Address("Blaufeldweg", "123B")},
                {"Auf der Vogelwiese 23 b", true, new Address("Auf der Vogelwiese", "23 b")},
                {"Calle Aduana , 29", true, new Address("Calle Aduana", "29")},
                {"Calle Aduana ,29", true, new Address("Calle Aduana", "29")},
                {"Calle 39, No 1540", true, new Address("Calle 39", "No 1540")},
                {"Called No Aduana No 1540", true, new Address("Called No Aduana", "No 1540")},
                {"Calle No Aduana", false, null},
                {"", false, null},
                {"", false, null}
        });
    }

    private String input;
    private final Boolean test;
    private Address output;

    public StreetNameFollowedByHouseNumberTypeTest(String input, Boolean test, Address output) {
        this.input = input;
        this.test = test;
        this.output = output;
    }

    @Before
    public void setup() {
        this.underTest = new StreetNameFollowedByHouseNumberType();
    }

    @Test
    public void addressConversionTest() {
        final boolean testResult = underTest.test(input);
        assertThat(testResult).isEqualTo(test);

        if (testResult) {
            assertThat(underTest.process(input)).isEqualTo(output);
        }
    }
}
