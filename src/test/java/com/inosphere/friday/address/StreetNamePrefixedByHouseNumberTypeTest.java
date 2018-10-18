package com.inosphere.friday.address;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class StreetNamePrefixedByHouseNumberTypeTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"4, rue de la revolution", true, new Address("rue de la revolution", "4")},
                {"200 Broadway Av", true, new Address("Broadway Av", "200")},
                {"200 D, Broadway Av", true, new Address("Broadway Av", "200 D")},
                {"200D ,Broadway Av", true, new Address("Broadway Av", "200D")},
                {"", false, null}
        });
    }

    private StreetNamePrefixedByHouseNumberType underTest;
    private String input;
    private final Boolean test;
    private Address output;

    public StreetNamePrefixedByHouseNumberTypeTest(String input, Boolean test, Address output) {
        this.input = input;
        this.test = test;
        this.output = output;
    }

    @Before
    public void setup() {
        this.underTest = new StreetNamePrefixedByHouseNumberType();
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
