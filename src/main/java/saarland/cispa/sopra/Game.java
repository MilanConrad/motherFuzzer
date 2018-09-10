package saarland.cispa.sopra;

import saarland.cispa.sopra.systemtests.AntInfo;
import saarland.cispa.sopra.systemtests.GameInfo;
import saarland.cispa.sopra.systemtests.WorldInfo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Game implements GameInfo {

    private LoggerInfo loggerInfo;
    private World world;



    public void setLoggerInfo(String path){
        if(path == null){
            loggerInfo = new NOPLogger();
        }
        else{
            File loggerFile = new File(path);
            loggerInfo = new JSONLogger(loggerFile);
        }

    }

    @Override
    public WorldInfo simulate(int rounds, long seed, File world1, File... brains) {
        initialize(seed, world1/*, brains*/);
        for (int count = 0; count < rounds; count++) {
            simulateOnce();
        }

        loggerInfo.writeToFile();
        return world;
    }

    @Override
    public WorldInfo simulate(int rounds, long seed, String world, String... brains) {


        File[] filearray = new File[brains.length];

        int count = 0;
        for (String brain : brains) {
            filearray[count] = new File(brain);
            count++;
        }
        File worldfile = new File(world);

        return simulate(rounds, seed, worldfile, filearray);

    }

    private void simulateOnce() {

        List<AntInfo> ants = world.getAnts();
        for (AntInfo ant : ants) {
            oneAnt((Ant) ant);
        }

        loggerInfo.addRoundInfo(world.logChanges(), world.getPoints(), world.getNumOfAntsInSwarm());
    }

    private void oneAnt(Ant ant) {

        if (ant.getRestTime() == 0 && !ant.isDead()) {
            ant.getNextInstruction().execute(world, ant);
        } else {
            ant.decreaseResttime();
        }
    }

    private void initialize(long seed, File world1 /*File[] brains*/) {

        //  Map<Character, Swarm> swarms;
        try {
            //   swarms = BrainParser.parse(brains);
            Instruction[] brainB = new Instruction[2];
            brainB[0] = new Turn(TurnDirection.left);
            brainB[1] = new Move(0);
            Swarm swarmA = new Swarm('A', brainB, "brainA");
            Swarm swarmB = new Swarm('B', brainB, "brainB");
            HashMap<Character, Swarm> swarms = new HashMap<>();
            swarms.put('A', swarmA);
            swarms.put('B', swarmB);
            world = WorldParser.parseMap(world1, seed, swarms);
            loggerInfo.addInitialRound(world.getFields(), swarms);
        } catch (IOException e) {
            e.notifyAll();
        }


    }
}
