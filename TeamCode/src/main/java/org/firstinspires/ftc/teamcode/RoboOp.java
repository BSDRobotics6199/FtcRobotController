package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
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
    protected DcMotor lift;
    protected DcMotor carousel;
    protected CRServo intake;
    protected ServoController servoController;
    protected BNO055IMU imu;
    protected double lastTime;
    protected double dt;
    protected double drivePower, strafePower, turnPower;
    protected double liftTarget;
    protected double liftTarget2;
    protected double x, y;
    //TODO: Set liftPositons array for floor then shipping hub levels behind the robot
    protected int[] liftPositions = new int[4];
    protected double liftPower;
    protected Position position;
    protected double servoPosition;
    protected double carouselSpeed;
    //-130
    protected enum liftLevel { //TODO: attach ints to values
        RECEIVE, HUB_1, HUB_2, HUB_3
    }
    liftLevel level = liftLevel.RECEIVE;
    @Override
    public void init() {
        //开始， 准备变量
        runtime = new ElapsedTime();
        telemetry.addData("Status", "Started");
        telemetry.update();

        //假设有四个驾驶马达
        //登录马达
        frontRight  = initializeMotor("frontRight");
        frontLeft = initializeMotor("frontLeft");
        backRight = initializeMotor("rearRight");
        backLeft = initializeMotor("rearLeft");
        lift = hardwareMap.get(DcMotor.class, "lift");
        lift.setTargetPosition(lift.getCurrentPosition());
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        carousel = hardwareMap.get(DcMotor.class, "carousel");
        carousel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intake = hardwareMap.get(CRServo.class, "intake");
        telemetry.addData("Motors: ", hardwareMap.getAll(DcMotor.class));
        liftTarget = lift.getCurrentPosition();
        liftTarget2 = lift.getCurrentPosition();
        liftPositions = new int[]{0, 1, 2, 3}; //TODO: set slide lift levels
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
        backRight.setPower(Range.clip(drivePower - turnPower - strafePower, -1, 1));

        frontLeft.setPower(Range.clip(-1*drivePower - turnPower + strafePower, -1, 1));
        backLeft.setPower(Range.clip(-1*drivePower - turnPower + strafePower, -1, 1));
        telemetry.addData("Lift target: ", lift.getTargetPosition());
        telemetry.addData("Liftpower: ", liftPower);
        //position = imu.getPosition();
        lastTime = runtime.time();
        //TODO: add compliance + slide data outputs
        //做好，修一下
        //Position position = imu.getPosition();
        //position.toUnit(DistanceUnit.METER);/
        // telemetry.addData("Position: ",  position.x + " " + position.y + " " + position.z);
        /*if (level != liftLevel.RECEIVE) {
            if (Math.abs(lift.getTargetPosition() - (int)liftTarget)>5) {
                liftTarget2 = lift.getTargetPosition() + incdec;
            } else {
                liftTarget2 = liftTarget;
            }
            lift.setTargetPosition((int) liftTarget2);
            lift.setPower(liftPower);
        }*/
        lift.setTargetPosition((int)liftTarget);
        lift.setPower(1);
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
    protected void setLiftLevel(liftLevel level) {
        if (level == liftLevel.RECEIVE) {
            liftTarget = liftPositions[0];
        } else if (level == liftLevel.HUB_1) {
            liftTarget = liftPositions[1];
        } else if (level == liftLevel.HUB_2) {
            liftTarget = liftPositions[2];
        } else if (level == liftLevel.HUB_3) {
            liftTarget = liftPositions[3];
        }
        this.level = level;
    }
    protected void incrementLift() {
        if (level == liftLevel.RECEIVE) {
            liftTarget = lift.getCurrentPosition();
            liftPositions = new int[]{(int) liftTarget, (int) liftTarget - 80, (int) liftTarget - 376, (
                    int) liftTarget - 426, (int) liftTarget - 476};
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftTarget = liftPositions[1];
            level = liftLevel.HUB_1;
        } else if (level == liftLevel.HUB_1) {
            liftTarget = liftPositions[2];
            level = liftLevel.HUB_2;
        } else if (level == liftLevel.HUB_2) {
            liftTarget = liftPositions[3];
            level = liftLevel.HUB_3;
        }
        lift.setTargetPosition((int)liftTarget);
    }
    protected void decrementLift() {
        if (level== liftLevel.HUB_1) {
            liftTarget = liftPositions[0];
            level = liftLevel.RECEIVE;
            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lift.setPower(0.1);
        } else if (level == liftLevel.HUB_2) {
            liftTarget = liftPositions[1];
            level = liftLevel.HUB_1;
        } else if (level == liftLevel.HUB_3) {
            liftTarget = liftPositions[2];
            level = liftLevel.HUB_2;
        }
        lift.setTargetPosition((int)liftTarget);
    }
    protected void intakeClockwise() {
        carousel.setPower(liftPower);
    }
    protected void intakeCounterClockwise() {
        carousel.setPower(-1*liftPower);
    }
    protected void carouselClockwise(){ carousel.setPower(liftPower); }
    protected void carouselCounterClockwise(){ carousel.setPower(liftPower*-1); }
    //增加升降机功能
}