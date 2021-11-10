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
    public ArrayList<double[]> places;
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
        Position position = imu.getPosition();
        position.toUnit(DistanceUnit.METER);
        Orientation orientation = imu.getAngularOrientation();

        //走向第一点
        try {
            Thread thread;
            for (double[] place: places){
                thread = new Thread(new MoveToPosition(place[0], place[1], this));
                thread.start();
                thread.join();
            }
        } catch (InterruptedException ignored){

        }
        //停止
        System.exit(0);
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

}

class MoveToPosition implements Runnable {

    private double x, y;
    private AutoMode autoMode;

    public MoveToPosition(double x, double y, AutoMode autoMode){
        this.x = x;
        this.y = y;
        this.autoMode = autoMode;
    }

    @Override
    public void run() {
        //这个可能看起来很复杂，可是很简单，机器人会转向终点，然后会向终点走。
        Position position = autoMode.getImu().getPosition();
        position.toUnit(DistanceUnit.METER);
        double deltaRotation = 0;
        double[] inFront = autoMode.getInfront();
        //计算和转弯
        double turnNeeded = Math.atan(autoMode.getInfront()[1] / autoMode.getInfront()[0]);
        autoMode.setTurnPower(1);
        while (deltaRotation < turnNeeded) {
            double[] newFront = autoMode.getInfront();
            deltaRotation = Math.atan(inFront[1] - newFront[1] / inFront[0] - newFront[0]);
        }
        autoMode.setTurnPower(0);
        //计算和前进
        autoMode.setDrivePower(1);
        while (Math.abs(position.x - x) < 0.025 && Math.abs(position.y - y) < 0.025) {
            position = autoMode.getImu().getPosition();
            position.toUnit(DistanceUnit.METER);
        }
        autoMode.setDrivePower(0);
    }
}
