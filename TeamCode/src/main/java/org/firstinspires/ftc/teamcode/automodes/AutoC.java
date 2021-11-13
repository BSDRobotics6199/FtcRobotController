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

        one = false;
    }

    @Override
    public void loop() {
        super.loop();

        //向前走
        if (!one) {
            drivePower = 0.5;
            if ((timePassed * speed) > 1.5*TILE_SIZE) {
                drivePower = 0;
                offset = runtime.time();
                one = true;
            }
        }
        telemetry.addData("AutoDT", timePassed);
        telemetry.addData("Distance", timePassed * speed);
        telemetry.addData("one", one);

    }
}