package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.RoboOp;

import java.util.ArrayList;

public class AutoMode extends RoboOp {
    //尺寸， 我在用米
    public static final double TILE_SIZE = 0.6096;
    public static final double TOTAL_SIZE = 3.6576;
    public double startingAngle;
    public static final double WIDTH = 0.3556;
    public static final double LENGTH = 0.4572;
    //在这里设地点
    @Override
    public void init() {
        super.init();

        //设开始角度
        //角度从右边开始向左转
        //startingAngle =

        //0.3556 米宽， 0.4572 米长

    }

    @Override
    public void loop(){
        super.loop();
    }

    public BNO055IMU getImu(){
        return imu;
        //cosh = a
    }

    public double getStartingAngle(){
        return startingAngle;
    }

    public double[] getInfront(){
        //拿到前中位置
        Orientation orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return new double[] {Math.cos(orientation.firstAngle) * LENGTH, Math.sin(orientation.firstAngle) * LENGTH};
    }

    public void moveToPoint(){
        Position position = imu.getPosition();
        position.toUnit(DistanceUnit.METER);
        double deltaRotation = 0;
        double[] inFront = getInfront();
        //计算和转弯
        double turnNeeded = Math.atan(getInfront()[1] / getInfront()[0]);
        setTurnPower(1);
        while (deltaRotation < turnNeeded) {
            double[] newFront = getInfront();
            deltaRotation = Math.atan(inFront[1] - newFront[1] / inFront[0] - newFront[0]);
        }
        setTurnPower(0);
        //计算和前进
        setDrivePower(1);
        while (Math.abs(position.x - x) < 0.025 && Math.abs(position.y - y) < 0.025) {
            position = imu.getPosition();
            position.toUnit(DistanceUnit.METER);
        }
        setDrivePower(0);
    }

}
