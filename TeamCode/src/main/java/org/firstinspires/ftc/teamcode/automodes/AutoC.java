package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RoboOp;

@Autonomous(name="AutoC", group="Auto")
public class AutoC extends RoboOp {

    public final double speed = 0.69;
    public double timePassed;
    public boolean one;
    public double timer;
    public double offset;
    public static final double TILE_SIZE = 0.6096;
    public boolean clawSetup;
    public boolean claw;

    @Override
    public void init() {
        super.init();
        timer = runtime.time();
        offset = 0;
        drivePower = 0;
        clawSetup = false;
        one = false;
        claw = false;
    }

    @Override
    public void loop() {
        super.loop();
        timePassed = runtime.time() - offset;

        //向前走
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
            drivePower = 0.5;
            if ((timePassed * speed) > 1.5*TILE_SIZE) {
                drivePower = 0;
                offset = runtime.time();
                one = true;
            }
            return;
        }
    }
}