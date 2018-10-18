package com.inosphere.friday.address;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class StreetAddressConverterTest {
    private StreetNameFollowedByHouseNumberType suffixed = spy(StreetNameFollowedByHouseNumberType.class);
    private StreetNamePrefixedByHouseNumberType prefixed = spy(StreetNamePrefixedByHouseNumberType.class);

    @Autowired
    private StreetAddressConverter converter = new StreetAddressConverter();

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        converter.setAddressTypes(ImmutableList.of(suffixed, prefixed));
    }

    @Test
    public void prefixedByHouseNumberStreetAddressShouldPass() throws Exception {
        final String result = "{\"street\":\"Winterallee\",\"houseNumber\":\"3\"}";
        this.mockMvc.perform(get("/Winterallee 3"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(result)));

        verify(suffixed).process(anyString());
        verify(prefixed, never()).process(anyString());
    }

    @Test
    public void suffixedByHouseNumberStreetAddressShouldPass() throws Exception {
        final String result = "{\"street\":\"Winterallee\",\"houseNumber\":\"3d\"}";
        this.mockMvc.perform(get("/3d Winterallee"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(result)));

        verify(prefixed).process(anyString());
        verify(suffixed, never()).process(anyString());
    }

    @Test
    public void callWithNoParameter() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isNotFound());
    }
}
