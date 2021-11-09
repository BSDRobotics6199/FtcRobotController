package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

public class AutoMode extends RoboOp {
    //TODO: Run diagnostics to get


    @Override
    public void init() {
        super.init();
        //Ok essentially you want to be able to set what each of the independent values to be here
        /*
        x =;
        y =;
        angle =;
        turnSpeed =;
        velocity =;
        */

    }

    @Override
    public void loop() {
        super.loop();
        Position position = imu.getPosition();
        Orientation orientation = imu.getAngularOrientation();
    }
}

