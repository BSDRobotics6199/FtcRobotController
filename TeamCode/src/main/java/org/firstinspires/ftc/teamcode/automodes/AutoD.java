package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RoboOp;

@TeleOp(name="AutoD", group="Auto")
public class AutoD extends RoboOp {

    public final double speed = 0.69;
    public double timePassed;
    public boolean one, two, three;
    public static final double TILE_SIZE = 0.6096;
    public int clawSetup;

    @Override
    public void init() {
        super.init();
        timePassed = 0;
        drivePower = 0;

        //零是要离开墙，一是要张开爪，二是要回到墙，三是完成
        clawSetup = 0;
        one = false;
        two = false;
        three = false;
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

        //向左走
        if (!one) {
            strafePower = -0.5;
            if ((timePassed * speed) < 1.5*TILE_SIZE) {
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
            drivePower = 0.5;
            if ((timePassed * speed) < 1.5*TILE_SIZE) {
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