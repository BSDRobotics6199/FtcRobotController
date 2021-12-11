package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red Carousel", group="Auto")
public class RedCarousel extends AutoMode {

    public void doTasks(){
        move(1, Direction.BACK);
        carousel(10000, Direction.COUNTER_CLOCKWISE);
        move(1, Direction.LEFT);
    }
}