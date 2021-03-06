package saarland.cispa.sopra.systemtests.simontests;

import saarland.cispa.sopra.systemtests.AntInfo;
import saarland.cispa.sopra.systemtests.GameInfo;
import saarland.cispa.sopra.systemtests.SystemTest;
import saarland.cispa.sopra.systemtests.WorldInfo;

public class SenseTestFriend extends SystemTest {
    @Override
    public void test(GameInfo gameInfo) {
        String map = "2\n2\n" +
            "AA\n" +
            "B.";
        String brain = "brain \"sample\" {\nsense left friend else 0\nmove else 0\njump 0\n}";
        String brain0 = "brain \"sample\" {\njump 0\n}";

       WorldInfo world =  gameInfo.simulate(3,42,map,brain,brain0);
       AntInfo ant = world.getAnt(0);
       if(ant.getRestTime() == 0){
           fail("friend was  left so ant did move");
       }

       brain = "brain \"sample\" {\nsense right friend else 0\nmove else 0\njump 0\n}";
       world =  gameInfo.simulate(3,42,map,brain,brain0);
       ant = world.getAnt(0);
        if(ant.getRestTime() != 0) {
            fail("friend was not  right so ant did not  move");
        }

        brain = "brain \"sample\" {\nsense ahead friend else 0\nmove else 0\njump 0\n}";
        world =  gameInfo.simulate(3,42,map,brain,brain0);
        ant = world.getAnt(0);
        if(ant.getRestTime() != 0) {
            fail("friend was not ahead so ant did not move");
        }

        brain = "brain \"sample\" {\nsense here friend else 0\nmove else 0\njump 0\n}";
        world =  gameInfo.simulate(3,42,map,brain,brain0);
        ant = world.getAnt(0);
        if(ant.getRestTime() == 0) {
            fail("here  friend so ant did  move");
        }




    }
}

