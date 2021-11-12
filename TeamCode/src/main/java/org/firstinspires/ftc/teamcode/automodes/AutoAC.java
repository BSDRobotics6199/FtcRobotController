package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RoboOp;

@TeleOp(name="AutoAC", group="Auto")
public class AutoAC extends RoboOp {

    public final double speed = 0.2;
    public double timePassed;
    public boolean one;
    public static final double TILE_SIZE = 0.6096;

    @Override
    public void init() {
        super.init();
        timePassed = 0;
        drivePower = 0;
        one = false;
    }

    @Override
    public void loop() {
        super.loop();
        //向前走
        if (!one) {
            drivePower = 1;
            if ((timePassed * speed) < 1.25*TILE_SIZE) {
                timePassed += dt;
            } else {
                drivePower = 0;
                timePassed = 0;
                one = true;
            }
        }

    }
}