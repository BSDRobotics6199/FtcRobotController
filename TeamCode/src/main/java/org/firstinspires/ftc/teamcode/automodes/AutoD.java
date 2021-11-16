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
    public boolean start;

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

        timePassed = runtime.time() - offset;

        //向左走
        if (!clawSetup) {
            if (timePassed > 3) {
                offset = timePassed;
                clawSetup = true;
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
            if ((timePassed * speed) > 1.5*TILE_SIZE) {
                strafePower = 0;
                offset = runtime.time();
                one = true;
            }
            return;
        }

        //转盘
        if (!two) {
            carousel.setPower(-0.04);
            if ((timePassed) > 4) {
                carousel.setPower(0);
                offset = runtime.time();
                two = true;
            }
            return;
        }

        //向前走
        if (!three) {
            drivePower = 0.5;
            if ((timePassed * speed) > 1.5*TILE_SIZE) {
                drivePower = 0;
                offset = runtime.time();
                three = true;
            }
            return;

        }
    }
}