package hu.elte.progtech1.cwjkl1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import hu.elte.progtech1.cwjkl1.Leny.Nap;

import java.util.Random;

public class HomokjaroTest {
    int water;
    final String name = "Gipsz";
    Homokjaro jakab;
    int hadWater, hadDistance;
    boolean wasAlive;
    Random randomGenerator;

    @Before
    public void setUp() throws Exception {
        randomGenerator = new Random();
        water = randomGenerator.nextInt(Homokjaro.maxWater);
        jakab = new Homokjaro(name, water);

        hadWater = jakab.getWater();
        hadDistance = jakab.getDistance();
        wasAlive = jakab.isLiving();
        assertTrue("A verseny kezdetekor élnie kéne", wasAlive);
    }

    @Test
    public void napos() throws Exception {
        int naposidx = Nap.n.getValue();

        jakab.napos();
        int expWater = hadWater + Homokjaro.savedWater[naposidx] - Homokjaro.consumedWater[naposidx];
        assertEquals("Napos időben megfelelő mennyiségű vizet fogyaszt",
                (expWater < 0 ? 0:expWater),
                jakab.getWater());

        if(jakab.isLiving()) {
            assertEquals("Napos időben az elvárásoknak megfelelően halad előre",
                    (hadDistance + Homokjaro.moveDistance[naposidx]),
                    jakab.getDistance());
        }

        for(int i = jakab.getWater(); i >= 0; i-= Homokjaro.consumedWater[naposidx]) {
            jakab.napos();
        }
        assertFalse("Ha elég sokáig masíroz napos időben szomjan kell haljon",
                jakab.isLiving());
    }

    @Test
    public void felhos() throws Exception {
        int felhosidx = Nap.f.getValue();

        jakab.felhos();

        assertEquals("Felhős időben megfelelő mennyiségű vizet fogyaszt",
                (hadWater + Homokjaro.savedWater[felhosidx] - Homokjaro.consumedWater[felhosidx]),
                jakab.getWater());

        if(jakab.isLiving()) {
            assertEquals("Felhős időben az elvárásoknak megfelelően halad előre",
                    (hadDistance + Homokjaro.moveDistance[felhosidx]),
                    jakab.getDistance());
        }
    }

    @Test
    public void esos() throws Exception {
        int esosidx = Nap.e.getValue();

        jakab.esos();
        int expWater = hadWater + Homokjaro.savedWater[esosidx] - Homokjaro.consumedWater[esosidx];
        assertEquals("Esős időben megfelelő mennyiségű vizet fogyaszt",
                (expWater > Homokjaro.maxWater ? Homokjaro.maxWater:expWater),
                jakab.getWater());

        assertEquals("Esős időben az elvárásoknak megfelelően halad előre",
                (hadDistance + Homokjaro.moveDistance[esosidx]),
                jakab.getDistance());

        for(int i = jakab.getWater(); i <= jakab.getMaxWater() + 1; i += Homokjaro.savedWater[esosidx]) {
            jakab.esos();
        }
        assertEquals("ha elég sokat marad esőben egy idő után már nem tud több vizet elraktározni",
                jakab.getMaxWater(),
                jakab.getWater());
    }

}