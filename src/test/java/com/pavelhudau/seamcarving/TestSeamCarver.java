package com.pavelhudau.seamcarving;

import edu.princeton.cs.algs4.Picture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

public class TestSeamCarver {
    private static Picture picture3x4 = null;
    private static Picture picture6x5 = null;
    private static Picture surfersPic = null;

    @Test
    void testConstructorWhenPictureIsNullTHenException() {
        assertThrows(IllegalArgumentException.class, () -> new SeamCarver(null));
    }

    @Test
    void testEnergy() {
        Picture pic = getPicture3x4();
        SeamCarver carver = new SeamCarver(pic);
        assertEquals(Math.sqrt(52024), carver.energy(1, 2));
        assertEquals(Math.sqrt(52225), carver.energy(1, 1));

        assertEquals(1000, carver.energy(0, 0));
        assertEquals(1000, carver.energy(0, 1));
        assertEquals(1000, carver.energy(0, 2));
        assertEquals(1000, carver.energy(0, 3));

        assertEquals(1000, carver.energy(2, 0));
        assertEquals(1000, carver.energy(2, 1));
        assertEquals(1000, carver.energy(2, 2));
        assertEquals(1000, carver.energy(2, 3));

        assertEquals(1000, carver.energy(1, 0));

        assertEquals(1000, carver.energy(1, 3));
    }

    @Test
    void testPicture() {
        Picture pic = getPicture3x4();
        SeamCarver carver = new SeamCarver(pic);
        assertNotNull(carver.picture());
        assertEquals(pic, carver.picture());
    }

    @Test
    void testWidth() {
        SeamCarver carver = new SeamCarver(getPicture3x4());
        assertEquals(3, carver.width());
    }

    @Test
    void testHeight() {
        SeamCarver carver = new SeamCarver(getPicture3x4());
        assertEquals(4, carver.height());
    }

    @ParameterizedTest
    @CsvSource({
            "-1, -1",
            "-1, 2",
            "2, -1",
            "2, 5",
            "5, 2",
            "5, 5"
    })
    void testEnergyWhenXOrYAreOutsideOrBoundaries(int x, int y) {
        SeamCarver carver = new SeamCarver(getPicture3x4());
        assertThrows(IllegalArgumentException.class, () -> carver.energy(x, y));
    }

    @Test
    void testFindVerticalSeam() {
        // GIVEN
        SeamCarver carver = new SeamCarver(getPicture6x5());
        int[] expectedVerticalSeam = new int[]{3, 4, 3, 2, 1};

        // WHEN
        int[] verticalSeam = carver.findVerticalSeam();

        // THEN
        assertEquals(expectedVerticalSeam.length, verticalSeam.length);
        for (int i = 0; i < expectedVerticalSeam.length; i++) {
            assertEquals(expectedVerticalSeam[i], verticalSeam[i]);
        }
    }

    @Test
    void testFindVerticalSeamOnALargerPicture() {
        // GIVEN
        Picture surfersPic = getSurfersPic();
        SeamCarver carver = new SeamCarver(surfersPic);

        // WHEN
        int[] verticalSeam = carver.findVerticalSeam();

        // THEN
        assertEquals(surfersPic.height(), verticalSeam.length);
    }

    @Test
    void testFindHorizontalSeam() {
        // GIVEN
        SeamCarver carver = new SeamCarver(getPicture6x5());
        int[] expectedHorizontalSeam = new int[]{1, 2, 1, 2, 1, 0};

        // WHEN
        int[] horizontalSeam = carver.findHorizontalSeam();

        // THEN
        assertEquals(expectedHorizontalSeam.length, horizontalSeam.length);
        for (int i = 0; i < expectedHorizontalSeam.length; i++) {
            assertEquals(expectedHorizontalSeam[i], horizontalSeam[i]);
        }
    }

    @Test
    void testFindHorizontalSeamOnALargerPicture() {
        // GIVEN
        Picture surfersPic = getSurfersPic();
        SeamCarver carver = new SeamCarver(surfersPic);

        // WHEN
        int[] verticalSeam = carver.findHorizontalSeam();

        // THEN
        assertEquals(surfersPic.width(), verticalSeam.length);
    }

    @Test
    void testRemoveVerticalSeam() {
        // GIVEN
        Picture surfersPic = getSurfersPic();
        SeamCarver carver = new SeamCarver(surfersPic);

        // WHEN
        int seamsToRemove = 100;
        for (int i = 0; i < seamsToRemove; i++) {
            carver.removeVerticalSeam(carver.findVerticalSeam());
        }

        //carver.picture().save("src/main/resources/HJoceanSmall_v_seam.png");

        // THEN
        assertEquals(surfersPic.width() - seamsToRemove, carver.width());
        assertEquals(surfersPic.height(), carver.height());
    }

    @ParameterizedTest
    @CsvSource({
            "3, 1",
            "1, 3",
    })
    void testRemoveVerticalSeamWhenSeamHasInvalidCoordinates(int invalidA, int invalidB) {
        SeamCarver carver = new SeamCarver(getPicture6x5());

        final int[] invalidSeamFront = carver.findVerticalSeam();
        invalidSeamFront[0] = invalidA;
        invalidSeamFront[1] = invalidB;
        assertThrows(IllegalArgumentException.class, () -> carver.removeVerticalSeam(invalidSeamFront));

        int[] invalidSeamBack = carver.findVerticalSeam();
        invalidSeamBack[invalidSeamBack.length-2] = invalidA;
        invalidSeamBack[invalidSeamBack.length-1] = invalidB;
        assertThrows(IllegalArgumentException.class, () -> carver.removeVerticalSeam(invalidSeamBack));
    }

    @Test
    void testRemoveHorizontalSeam() {
        // GIVEN
        Picture surfersPic = getSurfersPic();
        SeamCarver carver = new SeamCarver(surfersPic);

        // WHEN
        int seamsToRemove = 50;
        for (int i = 0; i < seamsToRemove; i++) {
            carver.removeHorizontalSeam(carver.findHorizontalSeam());
        }

        //carver.picture().save("src/main/resources/HJoceanSmall_h_seam.png");

        // THEN
        assertEquals(surfersPic.height() - seamsToRemove, carver.height());
        assertEquals(surfersPic.width(), carver.width());
    }

    @ParameterizedTest
    @CsvSource({
            "3, 1",
            "1, 3",
    })
    void testRemoveHorizontalSeamWhenSeamHasInvalidCoordinates(int invalidA, int invalidB) {
        SeamCarver carver = new SeamCarver(getPicture6x5());

        final int[] invalidSeamFront = carver.findHorizontalSeam();
        invalidSeamFront[0] = invalidA;
        invalidSeamFront[1] = invalidB;
        assertThrows(IllegalArgumentException.class, () -> carver.removeHorizontalSeam(invalidSeamFront));

        int[] invalidSeamBack = carver.findHorizontalSeam();
        invalidSeamBack[invalidSeamBack.length-2] = invalidA;
        invalidSeamBack[invalidSeamBack.length-1] = invalidB;
        assertThrows(IllegalArgumentException.class, () -> carver.removeHorizontalSeam(invalidSeamBack));
    }

    private static Picture getPicture3x4() {
        if (picture3x4 == null) {
            picture3x4 = new Picture(3, 4);
            picture3x4.set(0, 0, new Color(255, 101, 51));
            picture3x4.set(1, 0, new Color(255, 101, 153));
            picture3x4.set(2, 0, new Color(255, 101, 255));

            picture3x4.set(0, 1, new Color(255, 153, 51));
            picture3x4.set(1, 1, new Color(255, 153, 153));
            picture3x4.set(2, 1, new Color(255, 153, 255));

            picture3x4.set(0, 2, new Color(255, 203, 51));
            picture3x4.set(1, 2, new Color(255, 204, 153));
            picture3x4.set(2, 2, new Color(255, 205, 255));

            picture3x4.set(0, 3, new Color(255, 255, 51));
            picture3x4.set(1, 3, new Color(255, 255, 153));
            picture3x4.set(2, 3, new Color(255, 255, 255));
        }

        return picture3x4;
    }

    private static Picture getPicture6x5() {
        if (picture6x5 == null) {
            picture6x5 = new Picture("src/main/resources/6x5.png");
        }
        return picture6x5;
    }

    private static Picture getSurfersPic() {
        if (surfersPic == null) {
            surfersPic = new Picture("src/main/resources/HJoceanSmall.png");
        }
        return surfersPic;
    }
}
