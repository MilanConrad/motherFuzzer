package saarland.cispa.sopra;

import saarland.cispa.sopra.systemtests.FieldInfo;

public class Pickup extends Instruction {

    private final int jumpPC;

    public Pickup(int jumpPC){this.jumpPC = jumpPC;}
    @Override
    public void execute(World world, Ant ant) {

        if(ant.getField().getType()==('.')){
            Normal field = (Normal) ant.getField();
            if((field.getFood() != 0)){
                ant.setHasFood(true);
                field.removeFood();
                ant.increasePC();

            }

            else{
                ant.setPc(jumpPC);
            }

        }

        Field field = (Field) ant.getField();
        field.setChanged();
    }
    @Override
    public String toString(){
        return "direction" + "else" + jumpPC;
    }
}