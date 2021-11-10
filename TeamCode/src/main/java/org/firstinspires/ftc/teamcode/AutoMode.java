package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.checkerframework.dataflow.qual.TerminatesExecution;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.greedy.Node;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public class AutoMode extends RoboOp {
    //TODO: Run diagnostics to get
    //尺寸， 我在用米
    public static final double TILE_SIZE = 0.6096;
    public static final double TOTAL_SIZE = 3.6576;
    public double startingAngle;
    public static final double WIDTH = 0.3556;
    public static final double LENGTH = 0.4572;
    //在这里设地点
    public static double[] PARKING_SPOT = new double[]{};
    public static double[] CAROUSEL_SPOT = new double[]{};
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
            Thread runThread1 = new Thread(new MoveToPosition(CAROUSEL_SPOT[0], CAROUSEL_SPOT[1], this));
            runThread1.start();
            runThread1.join();
            //走向第二点
            Thread runThread2 = new Thread(new MoveToPosition(PARKING_SPOT[0], PARKING_SPOT[1], this));
            runThread2.start();
            runThread2.join();
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
        //拿到前中
        return new double[] {Math.cos(startingAngle) * LENGTH, Math.sin(startingAngle) * LENGTH};
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
        position = autoMode.getImu().getPosition();
        position.toUnit(DistanceUnit.METER);
        double[] inFront = autoMode.getInfront();
        double turnNeeded = Math.atan(autoMode.getInfront()[1] / autoMode.getInfront()[0]);
        autoMode.turnPower = 1;
        while (deltaRotation < turnNeeded) {
            double[] newFront = autoMode.getInfront();
            deltaRotation = Math.atan(inFront[1] - newFront[1] / inFront[0] - newFront[0]);
        }
        autoMode.turnPower = 0;
        autoMode.drivePower = 1;
        while (Math.abs(position.x - x) < 0.025 && Math.abs(position.y - y) < 0.025) {
            position = autoMode.getImu().getPosition();
            position.toUnit(DistanceUnit.METER);
        }
        autoMode.drivePower = 0;
    }
}
