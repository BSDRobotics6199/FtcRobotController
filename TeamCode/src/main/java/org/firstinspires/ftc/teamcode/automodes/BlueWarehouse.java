package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Blue Warehouse", group="Auto")
public class BlueWarehouse extends AutoMode {

    public void doTasks(){
        move(1.25, Direction.FORWARD);
    }
}