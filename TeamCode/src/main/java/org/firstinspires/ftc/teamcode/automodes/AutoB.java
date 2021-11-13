package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RoboOp;

@TeleOp(name="AutoB", group="Auto")
public class AutoB extends RoboOp {

    public final double speed = 0.69;
    public double timePassed;
    public boolean one, two, three;
    public double timer;
    public double offset;
    public static final double TILE_SIZE = 0.6096;

    @Override
    public void init() {
        super.init();
        timer = runtime.time();
        offset = 0;
        drivePower = 0;

        one = false;
        two = false;
        three = false;
    }

    @Override
    public void loop() {
        super.loop();
        timePassed = runtime.time() - offset;

        //向左走
        if (!one) {
            drivePower = -0.5;
            if ((timePassed * speed) < 1.5*TILE_SIZE) {
                timePassed += dt;
            } else {
                drivePower = 0;
                offset = runtime.time();
                one = true;
            }
            return;
        }

        //转盘
        if (!two) {
            carousel.setPower(0.1);
            if ((timePassed) < 2) {
                timePassed += dt;
            } else {
                carousel.setPower(0);
                offset = runtime.time();
                two = true;
            }
            return;
        }

        //向右走
        if (!three) {
            strafePower = 0.5;
            if ((timePassed * speed) < 1.5*TILE_SIZE) {
                timePassed += dt;
            } else {
                strafePower = 0;
                offset = runtime.time();
                three = true;
            }
            return;
        }
    }
}