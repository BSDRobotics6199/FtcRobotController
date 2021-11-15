package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

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
    protected DcMotor lift;
    protected DcMotor carousel;
    protected Servo leftClaw;
    protected Servo rightClaw;
    protected ServoController servoController;
    protected BNO055IMU imu;
    protected double leftClawPosition;
    protected double rightClawPosition;
    protected double lastTime;
    protected double dt;
    protected double drivePower, strafePower, turnPower;
    protected double liftTarget;
    protected double x, y;
    private double leftClawDelta;
    private double rightClawDelta;
    //TODO: Set liftPositons array for floor then shipping hub levels behind the robot
    protected int[] liftPositions = new int[5];
    protected double liftPower;
    protected Position position;
    protected double servoPosition;
    protected double carouselSpeed;
    //-130
    protected enum liftLevel {
        FLOOR, IDLE, HUB_1, HUB_2, HUB_3
    }
    liftLevel level = liftLevel.FLOOR;
    @Override
    public void init() {
        //开始， 准备变量
        runtime = new ElapsedTime();
        telemetry.addData("Status", "Started");
        telemetry.update();

        //假设有四个驾驶马达
        //登录马达
        frontRight  = initializeMotor("right_front");
        frontLeft = initializeMotor("left_front");
        backRight = initializeMotor("right_rear");
        backLeft = initializeMotor("left_rear");
        lift = hardwareMap.get(DcMotor.class, "lift");
        lift.setTargetPosition(lift.getCurrentPosition());
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        carousel = hardwareMap.get(DcMotor.class, "carousel");
        carousel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftClaw = hardwareMap.get(Servo.class, "left");
        rightClaw = hardwareMap.get(Servo.class, "right");
        rightClaw.setDirection(Servo.Direction.REVERSE);
        servoController = hardwareMap.getAll(ServoController.class).get(0);
        telemetry.addData("Motors: ", hardwareMap.getAll(DcMotor.class));
        liftTarget = lift.getCurrentPosition();
        liftPositions = new int[]{(int) liftTarget, (int) liftTarget - 80, (int) liftTarget - 376, (
                int) liftTarget - 426, (int) liftTarget - 476};
        servoPosition = 0.9;
        liftPower = 0.1;
        carouselSpeed = 1;
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
        backRight.setPower(Range.clip(drivePower - turnPower + strafePower, -1, 1));

        frontLeft.setPower(Range.clip(drivePower + turnPower + strafePower, -1, 1));
        backLeft.setPower(Range.clip(drivePower + turnPower - strafePower, -1, 1));

        //position = imu.getPosition();
        lastTime = runtime.time();
        telemetry.addData("left servo1: ", leftClaw.getPosition());
        telemetry.addData("Lift power: ", liftPower);
        telemetry.addData("Arm target: ",  lift.getTargetPosition());
        telemetry.addData("Arm position: ",  lift.getCurrentPosition());
        telemetry.addData("Arm Enum: ",  level);
        //做好，修一下
        //Position position = imu.getPosition();
        //position.toUnit(DistanceUnit.METER);/
        // telemetry.addData("Position: ",  position.x + " " + position.y + " " + position.z);
        if (level != liftLevel.FLOOR) {
            lift.setTargetPosition((int) liftTarget);
            lift.setPower(liftPower);
        }

    }

    protected DcMotor initializeMotor(String hardwareID) {
        DcMotor returnMotor = hardwareMap.get(DcMotor.class, hardwareID);
        returnMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        returnMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (hardwareID.substring(0,4).equalsIgnoreCase("left")) {
            returnMotor.setDirection(DcMotor.Direction.REVERSE);
        }
        return returnMotor;
    }
    protected void servoSqueeze(/*int leftBound, int rightBound*/) {
        leftClawDelta = Math.abs(leftClaw.getPosition() - leftClawPosition);
        leftClaw.setPosition(
                0.36);
        rightClaw.setPosition(
                0.36);
        telemetry.addData("Claw position:", leftClaw.getPosition());
        leftClawPosition = leftClaw.getPosition();
    }
    protected void servoExpand(/*int leftBound, int rightBound*/){
        rightClawDelta = Math.abs(servoController.getServoPosition(1)-rightClawPosition);
        leftClaw.setPosition(
                0.45);
        rightClaw.setPosition(
                0.45);
        if (Math.abs(leftClawPosition-servoController.getServoPosition(0))<0.01) {
            telemetry.addData("leftStrained" , true);
        }
        leftClawPosition = leftClaw.getPosition();
        telemetry.addData("Right claw position:", servoController.getServoPosition(1));
        rightClawPosition = servoController.getServoPosition(1);
    }
    protected void setLiftLevel(liftLevel level) {
        if (level == liftLevel.FLOOR) {
            liftTarget = liftPositions[0];
        } else if (level == liftLevel.IDLE) {
            liftTarget = liftPositions[1];
        } else if (level == liftLevel.HUB_1) {
            liftTarget = liftPositions[2];
        } else if (level == liftLevel.HUB_2) {
            liftTarget = liftPositions[3];
        } else if (level == liftLevel.HUB_3) {
            liftTarget = liftPositions[4];
        }
        this.level = level;
    }
    protected void incrementLift() {
        if (level == liftLevel.FLOOR) {
            liftTarget = lift.getCurrentPosition();
            liftPositions = new int[]{(int) liftTarget, (int) liftTarget - 80, (int) liftTarget - 376, (
                    int) liftTarget - 426, (int) liftTarget - 476};
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftTarget = liftPositions[1];
            level = liftLevel.IDLE;
        } else if (level == liftLevel.IDLE) {
            liftTarget = liftPositions[2];
            level = liftLevel.HUB_1;
        } else if (level == liftLevel.HUB_1) {
            liftTarget = liftPositions[3];
            level = liftLevel.HUB_2;
        } else if (level == liftLevel.HUB_2) {
            liftTarget = liftPositions[4];
            level = liftLevel.HUB_3;
        }
    }
    protected void decrementLift() {
        if (level== liftLevel.IDLE) {
            liftTarget = liftPositions[0];
            level = liftLevel.FLOOR;
            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lift.setPower(0.1);
        } else if (level == liftLevel.HUB_1) {
            liftTarget = liftPositions[1];
            level = liftLevel.IDLE;
        } else if (level == liftLevel.HUB_2) {
            liftTarget = liftPositions[2];
            level = liftLevel.HUB_1;
        } else if (level == liftLevel.HUB_3) {
            liftTarget = liftPositions[3];
            level = liftLevel.HUB_2;
        }
    }
    protected void carouselClockwise(){
        carousel.setPower(-1*carouselSpeed);
    }
    protected void carouselCounterClockwise(){
        carousel.setPower(carouselSpeed);
    }
    //增加升降机功能
}