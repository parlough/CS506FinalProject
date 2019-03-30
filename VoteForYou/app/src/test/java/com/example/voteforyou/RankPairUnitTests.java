package com.example.voteforyou;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class RankPairUnitTests {

    /**
     * This test ensure the RankPair class works correctly.
     */
    @Test
    public void ensureRankPairWorksCorrectly() {
        // Basic test of one object methods.
        RankPair rpTestObj = new RankPair();
        String candidateName = "Kanye 2020";
        int rankValue = 100000;
        rpTestObj = new RankPair(candidateName, rankValue);
        assertEquals(rpTestObj.getCandidateName(), candidateName);
        assertEquals(rpTestObj.getRanking(), rankValue);
        assertEquals(rpTestObj.toString(), "Kanye 2020:  100000");

        // Here we ensure that two objects compare correctly.
        RankPair rpAdditionalTestObj = new RankPair();
        String addlCandidateName = "Beyonce";
        int addlRankValue = 100000;
        rpAdditionalTestObj = new RankPair(addlCandidateName, addlRankValue);
        assertEquals(rpTestObj.compare(rpTestObj, rpAdditionalTestObj), 0);

        // Additional compare testing.
        RankPair rpAdditionalTestObj2 = new RankPair();
        String addlCandidateName2 = "Beyonce";
        int addlRankValue2 = 100000000;
        rpAdditionalTestObj2 = new RankPair(addlCandidateName2, addlRankValue2);
        assertEquals(rpTestObj.compare(rpTestObj, rpAdditionalTestObj2), -1);
        assertEquals(rpTestObj.compare(rpAdditionalTestObj2, rpTestObj), 1);
    }

}
