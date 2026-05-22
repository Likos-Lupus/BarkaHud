package top.likoslupus.barkahud;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import top.likoslupus.barkahud.config.SpeedBarProfile;

import static org.junit.jupiter.api.Assertions.*;

class SpeedBarProfileTest {

    private static final int BAR_WIDTH = 182;

    @Test
    void allProfiles_translationKeyNotNull() {
        for (var profile : SpeedBarProfile.values()) {
            assertFalse(profile.translationKey.isBlank());
        }
    }

    @Nested
    class Packed {

        @Test
        void negativeSpeed_returnsZero() {
            assertEquals(0, SpeedBarProfile.PACKED.getProgress(-10, BAR_WIDTH));
        }

        @Test
        void zero_returnsZero() {
            assertEquals(0, SpeedBarProfile.PACKED.getProgress(0, BAR_WIDTH));
        }

        @Test
        void midRange_positive() {
            int progress = SpeedBarProfile.PACKED.getProgress(20, BAR_WIDTH);
            assertTrue(progress > 0);
            assertTrue(progress < BAR_WIDTH);
        }

        @Test
        void atMax_returnsFullWidth() {
            assertEquals(BAR_WIDTH, SpeedBarProfile.PACKED.getProgress(40, BAR_WIDTH));
        }

        @Test
        void aboveMax_returnsMinusOne() {
            assertEquals(-1, SpeedBarProfile.PACKED.getProgress(50, BAR_WIDTH));
        }

    }

    @Nested
    class Mixed {

        @Test
        void belowEight_returnsZero() {
            assertEquals(0, SpeedBarProfile.MIXED.getProgress(7, BAR_WIDTH));
        }

        @Test
        void atEight_returnsZero() {
            assertEquals(0, SpeedBarProfile.MIXED.getProgress(8, BAR_WIDTH));
        }

        @Test
        void midRange_positive() {
            int progress = SpeedBarProfile.MIXED.getProgress(30, BAR_WIDTH);
            assertTrue(progress > 0);
            assertTrue(progress < BAR_WIDTH);
        }

        @Test
        void atMax_returnsFullWidth() {
            assertEquals(BAR_WIDTH, SpeedBarProfile.MIXED.getProgress(70, BAR_WIDTH));
        }

        @Test
        void aboveMax_returnsMinusOne() {
            assertEquals(-1, SpeedBarProfile.MIXED.getProgress(71, BAR_WIDTH));
        }

    }

    @Nested
    class Blue {

        @Test
        void belowForty_returnsZero() {
            assertEquals(0, SpeedBarProfile.BLUE.getProgress(39, BAR_WIDTH));
        }

        @Test
        void atForty_returnsZero() {
            assertEquals(0, SpeedBarProfile.BLUE.getProgress(40, BAR_WIDTH));
        }

        @Test
        void midRange_positive() {
            int progress = SpeedBarProfile.BLUE.getProgress(55, BAR_WIDTH);
            assertTrue(progress > 0);
            assertTrue(progress < BAR_WIDTH);
        }

        @Test
        void atMax_returnsFullWidth() {
            assertEquals(BAR_WIDTH, SpeedBarProfile.BLUE.getProgress(70, BAR_WIDTH));
        }

        @Test
        void aboveMax_returnsMinusOne() {
            assertEquals(-1, SpeedBarProfile.BLUE.getProgress(71, BAR_WIDTH));
        }

    }

    @Nested
    class Progressive {

        @Test
        void negative_returnsZero() {
            assertEquals(0, SpeedBarProfile.PROGRESSIVE.getProgress(-1, BAR_WIDTH));
        }

        @Test
        void zero_returnsZero() {
            assertEquals(0, SpeedBarProfile.PROGRESSIVE.getProgress(0, BAR_WIDTH));
        }

        @Test
        void segmentOne_packedRange_positive() {
            int progress = SpeedBarProfile.PROGRESSIVE.getProgress(4, BAR_WIDTH);
            assertTrue(progress > 0);
        }

        @Test
        void segmentTwo_mixedRange_positive() {
            int progress = SpeedBarProfile.PROGRESSIVE.getProgress(20, BAR_WIDTH);
            assertTrue(progress > 0);
        }

        @Test
        void segmentThree_blueRange_positive() {
            int progress = SpeedBarProfile.PROGRESSIVE.getProgress(50, BAR_WIDTH);
            assertTrue(progress > 0);
        }

        @Test
        void segmentOne_monotonicWithinRange() {
            int prev = 0;
            for (double speed = 0.5; speed < 8; speed += 0.5) {
                int curr = SpeedBarProfile.PROGRESSIVE.getProgress(speed, BAR_WIDTH);
                assertTrue(curr >= prev, "Non-monotonic at speed " + speed);
                prev = curr;
            }
        }

        @Test
        void segmentTwo_monotonicWithinRange() {
            int prev = SpeedBarProfile.PROGRESSIVE.getProgress(8, BAR_WIDTH);
            for (double speed = 9; speed < 40; speed += 1) {
                int curr = SpeedBarProfile.PROGRESSIVE.getProgress(speed, BAR_WIDTH);
                assertTrue(curr >= prev, "Non-monotonic at speed " + speed);
                prev = curr;
            }
        }

        @Test
        void segmentThree_monotonicWithinRange() {
            int prev = SpeedBarProfile.PROGRESSIVE.getProgress(40, BAR_WIDTH);
            for (double speed = 41; speed <= 70; speed += 1) {
                int curr = SpeedBarProfile.PROGRESSIVE.getProgress(speed, BAR_WIDTH);
                assertTrue(curr >= prev, "Non-monotonic at speed " + speed);
                prev = curr;
            }
        }

        @Test
        void atMax_positiveAndBelowFullWidth() {
            int progress = SpeedBarProfile.PROGRESSIVE.getProgress(70, BAR_WIDTH);
            assertTrue(progress > 0);
            assertTrue(progress < BAR_WIDTH);
        }

        @Test
        void aboveMax_returnsMinusOne() {
            assertEquals(-1, SpeedBarProfile.PROGRESSIVE.getProgress(71, BAR_WIDTH));
        }

    }

}
