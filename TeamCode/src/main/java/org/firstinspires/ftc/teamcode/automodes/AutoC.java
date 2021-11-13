package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RoboOp;

@TeleOp(name="AutoC", group="Auto")
public class AutoC extends RoboOp {

    public final double speed = 0.69;
    public double timePassed;
    public boolean one;
    public double timer;
    public double offset;
    public static final double TILE_SIZE = 0.6096;
    public int clawSetup;

    @Override
    public void init() {
        super.init();
        timer = runtime.time();
        offset = 0;
        drivePower = 0;

        //零是要离开墙，一是要张开爪，二是要回到墙，三是完成
        clawSetup = 0;
        one = false;
    }

    @Override
    public void loop() {
        super.loop();

        //离开墙
        if (clawSetup == 0) {
            strafePower = -0.5;
            if ((timePassed * speed) < 0.5*TILE_SIZE) {
                timePassed += dt;
            } else {
                strafePower = 0;
                timePassed = 0;
                clawSetup++;
            }
            return;
        }

        //张开爪
        if (clawSetup == 1) {
            leftClaw.setPosition(0.45);
            rightClaw.setPosition(0.45);
            if (timePassed < 2) {
                timePassed += dt;
            } else {
                timePassed = 0;
                clawSetup++;
            }
            return;
        }

        //回到墙
        if (clawSetup == 2) {
            strafePower = 0.5;
            if ((timePassed * speed) < 0.5*TILE_SIZE) {
                timePassed += dt;
            } else {
                strafePower = 0;
                timePassed = 0;
                clawSetup++;
            }
            return;
        }

        //向前走
        timePassed = runtime.time() - offset;
        if (!one) {
            drivePower = 0.5;
            if ((timePassed * speed) > 1.5*TILE_SIZE) {
                drivePower = 0;
                timePassed = 0;
                one = true;
            }
        }
        telemetry.addData("AutoDT", timePassed);
        telemetry.addData("Distance", timePassed * speed);
        telemetry.addData("one", one);

    }
}