package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red Warehouse", group="Auto")
public class RedWarehouse extends AutoMode {

    public void doTasks(){
        move(1.25, Direction.FORWARD);
    }
}