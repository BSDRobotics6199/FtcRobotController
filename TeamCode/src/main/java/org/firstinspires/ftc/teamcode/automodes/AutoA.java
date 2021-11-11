package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.RoboOp;

@TeleOp(name="AutoA", group="Auto")
public class AutoA extends RoboOp {

    public final double speed = 312*0.001;
    public double timePassed;

    @Override
    public void init() {
        super.init();
        timePassed = 0;
        drivePower = 1;
    }

    @Override
    public void loop() {
        super.loop();
        if ((timePassed * speed) < 1.15){
            timePassed += getDt();
        } else {
            drivePower = 0;
            System.exit(0);
        }

    }
}