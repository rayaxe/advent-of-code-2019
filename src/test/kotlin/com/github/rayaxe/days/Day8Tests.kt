package com.github.rayaxe.days

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day8Tests {

    @Nested
    inner class Day8Part2 {

        @Test
        fun example1() {
            val encodedImage = "0222112222120000"
            assertEquals("0110", day8Part2(encodedImage, 2, 2))
        }
    }
}
