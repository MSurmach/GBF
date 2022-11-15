package com.godeltech.gbf;

import org.junit.jupiter.api.Test;

class GbfApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void rotate() {
        int negative = 0;
        int neutral = 5;
        int positive = 10;
        int sum = negative + neutral + positive;
        double degree = 90 / sum;
        var positivePart = neutral / 2 + positive;
        var negativePart = neutral / 2 + negative;
        var result = 90.0 + (positivePart - negativePart) * degree;
        System.out.println(result);
    }


}
