package saarland.cispa.sopra.systemtests.simontests;

import saarland.cispa.sopra.systemtests.GameInfo;
import saarland.cispa.sopra.systemtests.SystemTest;
import saarland.cispa.sopra.systemtests.WorldInfo;

public class MarkTest6 extends SystemTest {
    @Override
    public void test(GameInfo gameInfo) {
        String map = "2\n2\n" +
            "A.\n" +
            "B.";
        String brainA = "brain \"sample\" {\nmark 3\njump 0\n}";
        String brainB = "brain \"sample\" {\nsense ahead marker 3 else 2\njump 0\nturn right\njump 0\n}";
        String dir = "northeast";
        WorldInfo world = gameInfo.simulate(2, 42, map, brainA, brainB);
        boolean[] arr = world.getFieldAt(0, 0).getMarkers().get('A');
        if (!arr[3]){
            fail("3 marked");
        }
        if(!(dir.equals(world.getAnt(1).getDirection()))){
            fail("can not sense opponent marker");
        }
    }
    }

