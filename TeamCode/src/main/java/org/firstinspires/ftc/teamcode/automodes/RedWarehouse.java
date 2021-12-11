package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red Warehouse", group="Auto")
public class RedWarehouse extends AutoMode {

    public void doTasks(){
        move(1.25, Direction.FORWARD);
        move(1, Direction.LEFT);
        move(1, Direction.FORWARD);
        turn(90, Direction.COUNTER_CLOCKWISE);
        playString("That sounds likes a skill issue ");
    }
}