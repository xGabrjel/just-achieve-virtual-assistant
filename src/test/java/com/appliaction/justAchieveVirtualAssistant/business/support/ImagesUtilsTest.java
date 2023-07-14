package com.appliaction.justAchieveVirtualAssistant.business.support;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ImagesUtilsTest {

    @Test
    void compressImageWorksCorrectly() {
        // given
        byte[] input = "Hello World!".getBytes();

        // when
        byte[] compressed = ImagesUtils.compressImage(input);

        // then
        assertNotNull(compressed);
        assertFalse(Arrays.equals(input, compressed));
    }

    @Test
    void decompressImageWorksCorrectly() {
        // given
        byte[] input = "Hello World!".getBytes();
        byte[] compressed = ImagesUtils.compressImage(input);

        // when
        byte[] decompressed = ImagesUtils.decompressImage(compressed);

        // then
        assertNotNull(decompressed);
        assertArrayEquals(input, decompressed);
    }
}