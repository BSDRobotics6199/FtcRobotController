package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="DriverControl", group="Linear Opmode")
public class TestController extends RoboOp {
    boolean pastDpadUp;
    boolean pastDpadDown;
    int count = 0;
    @Override
    public void init() {
        super.init();
        liftTarget = lift.getCurrentPosition();/*
        leftClaw.setPosition(0);
        try {
            wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rightClaw.setPosition(0);*/
    }

    @Override
    public void loop() {
        telemetry.addData("DeltaTime", dt);

        //在这里设能量
        drivePower = curve(-gamepad1.left_stick_y);
        strafePower = curve(gamepad1.left_stick_x);
        lift.setPower(0.4);
//        if (level == liftLevel.RECEIVE) {
//            turnPower = (0.7*gamepad1.right_stick_x + 0.3*gamepad2.right_stick_x);
//        } else {
//            turnPower = gamepad1.right_stick_x;
//        }


        //intake.setPower(-1*gamepad2.left_stick_y);
        if (gamepad2.triangle) {
            carouselCounterClockwise();
        }else if (gamepad2.circle){
            carouselClockwise();
        } else {
            carousel.setPower(0);
        }

        if (gamepad1.right_trigger > 0) {
            int newPos = lift.getTargetPosition() - 15;
            if (newPos >= -245) {
                lift.setTargetPosition(newPos);
            }
            lift.setPower(0.4);
        } else if (gamepad1.right_bumper){
            int newPos = lift.getTargetPosition() + 15;
            if (newPos <= 1715) {
                lift.setTargetPosition(newPos);
            }

            lift.setPower(0.4);
        }

        if (gamepad1.left_trigger > 0) {
            box.setPosition(box.getPosition() + 0.01);
        } else if (gamepad1.left_bumper){
            box.setPosition(box.getPosition() - 0.01);
        }


        liftTarget += 10*(gamepad2.right_trigger - gamepad2.left_trigger);
        //imu他妈的没用肥沃
        //telemetry.addData("Position: ",  position.x + " " + position.y + " " + position.z);\
        pastDpadDown = gamepad1.dpad_down;
        pastDpadUp = gamepad1.dpad_up;
        count++;
        super.loop();
        //a
    }

    public double curve(double value){
        return 2f/(1f+Math.pow(Math.E, -4f*value))-1f;
    }
}