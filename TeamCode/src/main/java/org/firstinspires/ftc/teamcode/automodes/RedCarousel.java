package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red Carousel", group="Auto")
public class RedCarousel extends AutoMode {

    public void doTasks(){
        move(0.6175017979949189, Direction.BACKWARD);
        turn(1.5707963267948966, Direction.CLOCKWISE);
        move(0.41710201499881805, Direction.FORWARD);
        move(0.41641726799934986, Direction.BACKWARD);
        turn(1.5707963267948966, Direction.COUNTERCLOCKWISE);
        move(1.3003461070002231, Direction.FORWARD);
    }
}