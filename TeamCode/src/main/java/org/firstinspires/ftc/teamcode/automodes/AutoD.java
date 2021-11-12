package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RoboOp;

@TeleOp(name="AutoD", group="Auto")
public class AutoD extends RoboOp {

    public final double speed = (312.0f/60.0f)*0.100*Math.PI;
    public double timePassed;
    public boolean one, two, three;
    public static final double TILE_SIZE = 0.6096;

    @Override
    public void init() {
        super.init();
        timePassed = 0;
        drivePower = 0;
        one = false;
        two = false;
        three = false;
    }

    @Override
    public void loop() {
        super.loop();
        //向左走
        if (!one) {
            strafePower = -1;
            if ((timePassed * speed) < 1.25*TILE_SIZE) {
                timePassed += dt;
            } else {
                strafePower = 0;
                timePassed = 0;
                one = true;
            }
            return;
        }

        //转盘
        if (!two) {
            carousel.setPower(0.25);
            if ((timePassed) < 2) {
                timePassed += dt;
            } else {
                carousel.setPower(0);
                timePassed = 0;
                two = true;
            }
            return;
        }

        //向前走
        if (!three) {
            drivePower = 1;
            if ((timePassed * speed) < 1.25*TILE_SIZE) {
                timePassed += dt;
            } else {
                drivePower = 0;
                timePassed = 0;
                three = true;
            }
            return;

        }
    }
}