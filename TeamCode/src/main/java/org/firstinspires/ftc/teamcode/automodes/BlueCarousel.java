package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Blue Carousel", group="Auto")
public class BlueCarousel extends AutoMode {

    public void doTasks(){
        setLiftLevel(liftLevel.HUB_1);
        moveTiles(1, Direction.LEFTWARD);
        moveTiles(1.5, Direction.FORWARD);
        intake(2000, Direction.BACKWARD);

        moveTiles(1, Direction.BACKWARD);
        moveTiles(2, Direction.RIGHTWARD);
        carousel(10000, Direction.CLOCKWISE);

        playString("That sounds like a skill issue ");
    }
}