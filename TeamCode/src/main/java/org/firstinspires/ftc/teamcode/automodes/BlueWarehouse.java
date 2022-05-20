package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Blue Warehouse", group="Auto")
public class BlueWarehouse extends AutoMode {

    public void doTasks(){
        setLiftLevel(liftLevel.HUB_1);
        moveTiles(1, Direction.RIGHTWARD);
        moveTiles(1.5, Direction.FORWARD);
        intake(2000, Direction.BACKWARD);

        moveTiles(1.5, Direction.BACKWARD);
        moveTiles(2.25, Direction.LEFTWARD);
        moveTiles(1, Direction.FORWARD);

        playString("That sounds like a skill issue ");
    }
}