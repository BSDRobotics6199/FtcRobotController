package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RoboOp;

@Autonomous(name="AutoD", group="Auto")
public class AutoD extends RoboOp {

    public final double speed = 0.69;
    public double timePassed;
    public boolean one, two, three;
    public static final double TILE_SIZE = 0.6096;
    public boolean clawSetup;
    public double offset;
    public double timer;
    public boolean claw;

    @Override
    public void init() {
        super.init();
        drivePower = 0;
        timer = runtime.time();
        offset = 0;
        clawSetup  = false;
        one = false;
        two = false;
        three = false;
        claw  = false;
    }

    @Override
    public void loop() {
        super.loop();
        timePassed = runtime.time() - offset;

        //向左走
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
            strafePower = -0.5;
            if ((timePassed * speed) < 1.5*TILE_SIZE) {
                timePassed += dt;
            } else {
                strafePower = 0;
                offset = runtime.time();
                one = true;
            }
            return;
        }

        //转盘
        if (!two) {
            carousel.setPower(-0.08);
            if ((timePassed) < 2) {
                timePassed += dt;
            } else {
                carousel.setPower(0);
                offset = runtime.time();
                two = true;
            }
            return;
        }

        //向前走
        if (!three) {
            drivePower = 0.5;
            if ((timePassed * speed) < 1.4*TILE_SIZE) {
                timePassed += dt;
            } else {
                drivePower = 0;
                offset = runtime.time();
                three = true;
            }
            return;

        }
    }
}