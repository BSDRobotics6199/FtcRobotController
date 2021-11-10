package org.firstinspires.ftc.teamcode.automodes;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.RoboOp;

public class AutoA extends RoboOp {
    //TODO: Run diagnostics to get
    //尺寸， 我在用米
    public static final double TILE_SIZE = 0.6096;
    public static final double TOTAL_SIZE = 3.6576;

    @Override
    public void init() {
        super.init();

        double[] parkingSpot = new double[]{};
        double[] carouselSpot = new double[]{};
        double[] cornerSpot = new double[]{};

        //0.3556 米宽， 0.4572 米长
    }

    @Override
    public void loop() {
        super.loop();
        Position position = imu.getPosition();
        position.toUnit(DistanceUnit.METER);
        Orientation orientation = imu.getAngularOrientation();

        //停止
        System.exit(0);
    }

}