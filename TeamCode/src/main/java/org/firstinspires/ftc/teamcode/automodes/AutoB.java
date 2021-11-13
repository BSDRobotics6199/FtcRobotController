package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RoboOp;

@Autonomous(name="AutoB", group="Auto")
public class AutoB extends RoboOp {

    public final double speed = 0.69;
    public double timePassed;
    public boolean one, two, three;
    public double timer;
    public double offset;
    public static final double TILE_SIZE = 0.6096;
    public boolean clawSetup;
    public boolean claw;
    public boolean start;

    @Override
    public void init() {
        super.init();
        timer = runtime.time();
        offset = 0;
        drivePower = 0;
        clawSetup = false;
        one = false;
        two = false;
        three = false;
        claw = false;
        start = false;

    }

    @Override
    public void loop() {
        super.loop();
        if (!start){
            start = true;
            offset = runtime.time();
        }
        timePassed = runtime.time() - offset;

        //向后走
        if (!clawSetup) {
            if (timePassed > 3) {
                offset = timePassed;
                clawSetup = true;
            } else if (timePassed > 2) {
                leftClaw.setPosition(0.45);
            } else {
                rightClaw.setPosition(0.45);
            }
            if (!claw) {
                leftClaw.setPosition(0.45);
                rightClaw.setPosition(0.45);
                claw = true;
            }

            return;
        }
        if (!one) {
            drivePower = -0.5;
            if ((timePassed * speed) > 1.5*TILE_SIZE) {
                drivePower = 0;
                offset = runtime.time();
                one = true;
            }
            return;
        }

        //转盘
        if (!two) {
            carousel.setPower(0.04);
            if ((timePassed) > 4) {
                carousel.setPower(0);
                offset = runtime.time();
                two = true;
            }
            return;
        }

        //向右走
        if (!three) {
            strafePower = 0.5;
            if ((timePassed * speed) > 1.5*TILE_SIZE) {
                strafePower = 0;
                offset = runtime.time();
                three = true;
            }
            return;
        }
    }
}