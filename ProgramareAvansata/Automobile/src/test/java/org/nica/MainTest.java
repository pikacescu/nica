package org.nica;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {


    @Test
    void test1 () {System.out.println("salut");}


    @Test
    void test2 ()
    {
        String x = "salut";
        if (x == "salut")
        {
            System.out.println("x is salut");
        }
        System.out.println("salut: " + Main.x);
    }


}