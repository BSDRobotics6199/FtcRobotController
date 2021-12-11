package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Blue Carousel", group="Auto")
public class BlueCarousel extends AutoMode {

    public void doTasks(){
        setLiftLevel(liftLevel.HUB_1);
        move(1, Direction.LEFT);
        move(1, Direction.FORWARD);
        intake(2000, Direction.BACK);

        move(1, Direction.BACK);
        move(2, Direction.RIGHT);
        carousel(2000, Direction.CLOCKWISE);

        playString("That sounds like a skill issue ");
    }
}