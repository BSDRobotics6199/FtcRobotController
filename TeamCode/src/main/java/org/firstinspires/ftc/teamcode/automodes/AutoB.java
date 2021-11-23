package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutoB", group="Auto")
public class AutoB extends AutoMode {

    @Override
    public void doTasks(){
        servoSqueeze();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        move(1, Direction.FORWARD);

        drive.turn(Math.PI);

        setLiftLevel(liftLevel.HUB_1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        move(1, Direction.LEFT);

        servoExpand();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        move(1/2, Direction.BACK);

        setLiftLevel(liftLevel.IDLE);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        drive.turn(3*Math.PI/2);

        move(1/2, Direction.LEFT);

        move(1.5, Direction.BACK);

        carousel.setPower(0.6);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        carousel.setPower(0);

        move(1, Direction.RIGHT);

        move(1/2, Direction.BACK);
    }
}