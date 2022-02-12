package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;


public class RoboOp extends OpMode {
    //注意，这个程序的很多部分都是按照示范写的
    //这个程序有MIT许可证，我们没有任何责任

    protected ElapsedTime runtime;
    protected DcMotor frontRight;
    protected DcMotor frontLeft;
    protected DcMotor backRight;
    protected DcMotor backLeft;
    protected DcMotor intake;
    //protected DcMotor intake;
    protected Servo leftFlip;
    protected Servo rightFlip;
    //protected ServoController servoController;
    //protected BNO055IMU imu;
    protected double lastTime;
    protected double dt;
    protected double drivePower, strafePower, turnPower;
    protected double leftFlipPosition;
    protected double rightFlipPosition;
    //-130
    //protected enum liftLevel { //TODO: attach ints to values
    //    RECEIVE, HUB_1, HUB_2, HUB_3
    //}
    //liftLevel level = liftLevel.RECEIVE;
    @Override
    public void init() {
        //开始， 准备变量
        runtime = new ElapsedTime();
        telemetry.addData("Status", "Started");
        telemetry.update();

        //假设有四个驾驶马达
        //登录马达
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class,"rearRight");
        backLeft = hardwareMap.get(DcMotor.class, "rearLeft");
        intake = hardwareMap.get(DcMotor.class, "intake");
        leftFlip = hardwareMap.get(Servo.class, "leftFlip");
        rightFlip = hardwareMap.get(Servo.class, "rightFlip");
        //Reverse the motors here
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Motors: ", hardwareMap.getAll(DcMotor.class));

        leftFlipPosition = leftFlip.getPosition();
        rightFlipPosition = rightFlip.getPosition();
        //liftTarget = lift.getCurrentPosition();
        //liftPositions = new int[]{5, 730, 1250, 2200};
        //准备imu
//        imu = hardwareMap.get(BNO055IMU.class, "imu");
//        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
//        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
//        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
//        parameters.loggingEnabled = true;
//        parameters.loggingTag = "IMU";
//        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
//        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
//        imu.initialize(parameters);
//
//        position = imu.getPosition();
    }

    @Override
    public void loop() {
        dt = runtime.time() - lastTime;
        //每一回合都会设马达力量
        frontRight.setPower(Range.clip(drivePower - turnPower - strafePower, -1, 1));
        backRight.setPower(Range.clip(-1 * drivePower + turnPower - strafePower, -1, 1));

        frontLeft.setPower(Range.clip(-1 * drivePower - turnPower - strafePower, -1, 1));
        backLeft.setPower(Range.clip(drivePower + turnPower - strafePower, -1, 1));


        //position = imu.getPosition();


        lastTime = runtime.time();

    }
    public void flipOut() {
        rightFlipPosition = rightFlipPosition + (0.1 * dt);
        leftFlipPosition = leftFlipPosition + (0.1 * dt);
    }
    public void flipIn() {
        rightFlipPosition = rightFlipPosition - (0.1 * dt);
        leftFlipPosition = leftFlipPosition - (0.1 * dt);
    }

    //增加升降机功能
}