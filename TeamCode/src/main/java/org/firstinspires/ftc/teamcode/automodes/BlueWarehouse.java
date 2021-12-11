package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Blue Warehouse", group="Auto")
public class BlueWarehouse extends AutoMode {

    public void doTasks(){
        setLiftLevel(liftLevel.HUB_1);
        move(1, Direction.RIGHT);
        move(1, Direction.FORWARD);
        intake(2000, Direction.BACK);

        move(1, Direction.BACK);
        move(2.25, Direction.LEFT);
        move(1, Direction.FORWARD);
        turn(90, Direction.COUNTER_CLOCKWISE);

        playString("That sounds like a skill issue ");
    }
}