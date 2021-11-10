package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


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
    private int[] liftPositions = {246, -130, -190, -230};
    //-130
    enum liftLevel {
        FLOOR, HUB_1, HUB_2, HUB_3
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
        carousel = initializeMotor("carousel");
        leftClaw = hardwareMap.get(Servo.class, "left");
        rightClaw = hardwareMap.get(Servo.class, "right");
        rightClaw.setDirection(Servo.Direction.REVERSE);
        servoController = hardwareMap.getAll(ServoController.class).get(0);
        telemetry.addData("Motors: ", hardwareMap.getAll(DcMotor.class));
        liftTarget = lift.getCurrentPosition();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
    }

    @Override
    public void loop() {
        //每一回合都会设马达力量
        frontRight.setPower(Range.clip(drivePower - turnPower - strafePower, -1, 1));
        backRight.setPower(Range.clip(drivePower - turnPower + strafePower, -1, 1));

        frontLeft.setPower(Range.clip(drivePower + turnPower + strafePower, -1, 1));
        backLeft.setPower(Range.clip(drivePower + turnPower - strafePower, -1, 1));


        lastTime = runtime.time();
        telemetry.addData("left servo1: ", leftClaw.getPosition());
        telemetry.addData("left servo2: ", servoController.getServoPosition(0));
        telemetry.addData("Arm target: ",  lift.getTargetPosition());
        telemetry.addData("Arm position: ",  lift.getCurrentPosition());
        telemetry.addData("liftTarget: " , liftTarget);
        telemetry.addData("rightTrigger: ", gamepad1.right_trigger);
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
                0.69);
        rightClaw.setPosition(
                0.69);
        telemetry.addData("Claw position:", leftClaw.getPosition());
        leftClawPosition = leftClaw.getPosition();
    }
    protected void servoSqueeze2() {
        leftClawDelta = leftClaw.getPosition() - leftClawPosition;
        if (leftClawDelta>0.01) {
            leftClaw.setPosition(
                    1);
            rightClaw.setPosition(
                    1);
        }
        leftClawPosition = leftClaw.getPosition();
    }
    protected void servoExpand(/*int leftBound, int rightBound*/){
        rightClawDelta = Math.abs(servoController.getServoPosition(1)-rightClawPosition);
        leftClaw.setPosition(
                1);
        rightClaw.setPosition(
                1);
        if (Math.abs(leftClawPosition-servoController.getServoPosition(0))<0.01) {
            telemetry.addData("leftStrained" , true);
        }
        leftClawPosition = leftClaw.getPosition();
        telemetry.addData("Right claw position:", servoController.getServoPosition(1));
        rightClawPosition = servoController.getServoPosition(1);
    }
    protected void setLiftLevel(liftLevel level) {
        if (level == liftLevel.FLOOR) {
            lift.setTargetPosition(liftPositions[0]);
            lift.setPower(1);
        } else if (level == liftLevel.HUB_1) {
            lift.setTargetPosition(liftPositions[1]);
            lift.setPower(1);
        } else if (level == liftLevel.HUB_2) {
            lift.setTargetPosition(liftPositions[1]);
            lift.setPower(1);
        } else if (level == liftLevel.HUB_3) {
            lift.setTargetPosition(liftPositions[3]);
            lift.setPower(1);
        }
        this.level = level;
    }
    protected void incrementLift() {
        if (level == liftLevel.FLOOR) {
            lift.setTargetPosition(liftPositions[1]);
            lift.setPower(1);
            level = liftLevel.HUB_1;
        } else if (level == liftLevel.HUB_1) {
            lift.setTargetPosition(liftPositions[2]);
            lift.setPower(1);
            level = liftLevel.HUB_2;
        } else if (level == liftLevel.HUB_2) {
            lift.setTargetPosition(liftPositions[3]);
            lift.setPower(1);
            level = liftLevel.HUB_3;
        }
    }protected void decrementLift() {
        if (level == liftLevel.HUB_1) {
            lift.setTargetPosition(liftPositions[0]);
            lift.setPower(1);
            level = liftLevel.FLOOR;
        } else if (level == liftLevel.HUB_2) {
            lift.setTargetPosition(liftPositions[1]);
            lift.setPower(1);
            level = liftLevel.HUB_1;
        } else if (level == liftLevel.HUB_3) {
            lift.setTargetPosition(liftPositions[2]);
            lift.setPower(1);
            level = liftLevel.HUB_2;
        }
    }
    protected void carouselClockwise(){
        carousel.setPower(-1);
    }
    protected void carouselCounterClockwise(){
        carousel.setPower(1);
    }
    //增加升降机功能

    public DcMotor getFrontRight() {
        return frontRight;
    }

    public void setFrontRight(DcMotor frontRight) {
        this.frontRight = frontRight;
    }

    public DcMotor getFrontLeft() {
        return frontLeft;
    }

    public void setFrontLeft(DcMotor frontLeft) {
        this.frontLeft = frontLeft;
    }

    public DcMotor getBackRight() {
        return backRight;
    }

    public void setBackRight(DcMotor backRight) {
        this.backRight = backRight;
    }

    public DcMotor getBackLeft() {
        return backLeft;
    }

    public void setBackLeft(DcMotor backLeft) {
        this.backLeft = backLeft;
    }

    public DcMotor getLift() {
        return lift;
    }

    public void setLift(DcMotor lift) {
        this.lift = lift;
    }

    public DcMotor getCarousel() {
        return carousel;
    }

    public void setCarousel(DcMotor carousel) {
        this.carousel = carousel;
    }

    public Servo getLeftClaw() {
        return leftClaw;
    }

    public void setLeftClaw(Servo leftClaw) {
        this.leftClaw = leftClaw;
    }

    public Servo getRightClaw() {
        return rightClaw;
    }

    public void setRightClaw(Servo rightClaw) {
        this.rightClaw = rightClaw;
    }

    public ServoController getServoController() {
        return servoController;
    }

    public void setServoController(ServoController servoController) {
        this.servoController = servoController;
    }

    public BNO055IMU getImu() {
        return imu;
    }

    public void setImu(BNO055IMU imu) {
        this.imu = imu;
    }

    public double getLeftClawPosition() {
        return leftClawPosition;
    }

    public void setLeftClawPosition(double leftClawPosition) {
        this.leftClawPosition = leftClawPosition;
    }

    public double getRightClawPosition() {
        return rightClawPosition;
    }

    public void setRightClawPosition(double rightClawPosition) {
        this.rightClawPosition = rightClawPosition;
    }

    public double getLastTime() {
        return lastTime;
    }

    public void setLastTime(double lastTime) {
        this.lastTime = lastTime;
    }

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public double getDrivePower() {
        return drivePower;
    }

    public void setDrivePower(double drivePower) {
        this.drivePower = drivePower;
    }

    public double getStrafePower() {
        return strafePower;
    }

    public void setStrafePower(double strafePower) {
        this.strafePower = strafePower;
    }

    public double getTurnPower() {
        return turnPower;
    }

    public void setTurnPower(double turnPower) {
        this.turnPower = turnPower;
    }

    public double getLiftTarget() {
        return liftTarget;
    }

    public void setLiftTarget(double liftTarget) {
        this.liftTarget = liftTarget;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getLeftClawDelta() {
        return leftClawDelta;
    }

    public void setLeftClawDelta(double leftClawDelta) {
        this.leftClawDelta = leftClawDelta;
    }

    public double getRightClawDelta() {
        return rightClawDelta;
    }

    public void setRightClawDelta(double rightClawDelta) {
        this.rightClawDelta = rightClawDelta;
    }

    public int[] getLiftPositions() {
        return liftPositions;
    }

    public void setLiftPositions(int[] liftPositions) {
        this.liftPositions = liftPositions;
    }

    public liftLevel getLevel() {
        return level;
    }

    public void setLevel(liftLevel level) {
        this.level = level;
    }
}