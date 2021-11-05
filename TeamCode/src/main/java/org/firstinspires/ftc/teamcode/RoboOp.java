package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.ServoImpl;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RoboOp extends OpMode {
    //Note this code did not directly come from the examples but is heavily inspired by the examples
    //along with the code from last year, I have made some changes but a decent amount of the logic
    //is paraphrased, this is merely just a foundation that we will work to change later.

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
    protected double leftClawPosition;
    protected double rightClawPosition;
    protected double lastTime;
    protected double dt;
    protected double drivePower, strafePower, turnPower;
    protected float liftPower;
    protected double x, y;
    private double leftClawDelta;
    private double rightClawDelta;
    @Override
    public void init() {
        //Starts the operation mode
        runtime = new ElapsedTime();
        telemetry.addData("Status", "Started");
        telemetry.update();

        //Assuming that there are four drive wheels
        //This registers them
        //TODO: add lift motor
        frontRight  = initializeMotor("right_front");
        frontLeft = initializeMotor("left_front");
        backRight = initializeMotor("right_rear");
        backLeft = initializeMotor("left_rear");
        lift = initializeMotor("lift");
        carousel = initializeMotor("carousel");
        leftClaw = hardwareMap.get(Servo.class, "left");
        rightClaw = hardwareMap.get(Servo.class, "right");
        rightClaw.setDirection(Servo.Direction.REVERSE);
        servoController = hardwareMap.getAll(ServoController.class).get(0);
        telemetry.addData("Motors: ", hardwareMap.getAll(DcMotor.class));
    }

    @Override
    public void loop() {
        //So this part makes sure that the power is not too high or too low, it also makes the robot
        //actually move, so from what I understand, it makes one side move faster than the other to
        //achieve the rotation
        frontRight.setPower(Range.clip(drivePower - turnPower - strafePower, -1, 1));
        backRight.setPower(Range.clip(drivePower - turnPower + strafePower, -1, 1));

        frontLeft.setPower(Range.clip(drivePower + turnPower + strafePower, -1, 1));
        backLeft.setPower(Range.clip(drivePower + turnPower - strafePower, -1, 1));


        lift.setPower((double)liftPower);
        lastTime = runtime.time();
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
                Range.clip(leftClaw.getPosition() + 1*dt, 0.69, 1));
        rightClaw.setPosition(
                Range.clip(rightClaw.getPosition() + 1*dt, 0.69, 1));
        telemetry.addData("Claw delta:", leftClawDelta);
        leftClawPosition = leftClaw.getPosition();
    }
    protected void servoSqueeze2() {
        //TODO: change the threshold based on normal operation
        leftClawDelta = leftClaw.getPosition() - leftClawPosition;
        if (leftClawDelta>0.01) {
            leftClaw.setPosition(
                    Range.clip(leftClaw.getPosition() - 1*dt, 0.69, 1));
            rightClaw.setPosition(
                    Range.clip(rightClaw.getPosition() - 1*dt, 0.69, 1));
        }
        leftClawPosition = leftClaw.getPosition();
    }
    protected void servoExpand(/*int leftBound, int rightBound*/){
        rightClawDelta = Math.abs(servoController.getServoPosition(1)-rightClawPosition);
        leftClaw.setPosition(
                Range.clip(leftClaw.getPosition() - 1*dt, 0.69, 1));
        rightClaw.setPosition(
                Range.clip(rightClaw.getPosition() - 1*dt, 0.69, 1));
        if (Math.abs(leftClawPosition-servoController.getServoPosition(0))<0.01) {
            telemetry.addData("leftStrained" , true);
        }
        leftClawPosition = leftClaw.getPosition();
        telemetry.addData("RClaw delta:", rightClawDelta);
        rightClawPosition = servoController.getServoPosition(1);
    }
    protected void carouselClockwise(){
        carousel.setPower(-1);
    }
    protected void carouselCounterClockwise(){
        carousel.setPower(1);
    }
    //adds lift functionality
}
