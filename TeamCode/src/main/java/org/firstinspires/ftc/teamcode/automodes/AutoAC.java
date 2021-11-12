package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.RoboOp;

@TeleOp(name="AutoAC", group="Auto")
public class AutoAC extends RoboOp {

    public final double speed = (312.0f/60.0f)*0.05*Math.PI;
    public double timePassed;
    public boolean one;

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
            if ((timePassed * speed) < 1.25) {
                timePassed += dt;
            } else {
                drivePower = 0;
                timePassed = 0;
                one = true;
            }
        }

    }
}