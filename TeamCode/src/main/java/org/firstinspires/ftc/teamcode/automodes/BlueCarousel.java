package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Blue Carousel", group="Auto")
public class BlueCarousel extends AutoMode {

    public void doTasks(){
        move(1, Direction.BACK);
        carousel(5000, Direction.COUNTER_CLOCKWISE);
        move(1, Direction.RIGHT);
        playString("That sounds likes a skill issue ");
    }
}