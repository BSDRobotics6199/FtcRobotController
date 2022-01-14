package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;


@TeleOp(name="DriverControl", group="Linear Opmode")
public class TestController extends RoboOp {
    boolean pastDpadUp;
    boolean pastDpadDown;
    int count = 0;
    @Override
    public void init() {
        super.init();
        liftTarget = lift.getCurrentPosition();
        /*
        leftClaw.setPosition(0);
        try {
            wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rightClaw.setPosition(0);
        */
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
        turnPower = gamepad1.right_stick_x;


        //intake.setPower(-1*gamepad2.left_stick_y);
        if (gamepad2.triangle) {
            carouselCounterClockwise();
        }else if (gamepad2.circle){
            carouselClockwise();
        } else {
            carousel.setPower(0);
        }
        //
        if (gamepad1.left_trigger > 0) {
            int newPos = lift.getTargetPosition() + 25;
            if (newPos > 22) {
                newPos = 22;
            }
            lift.setTargetPosition(newPos);
            lift.setPower(0.4);

        } else if (gamepad1.left_bumper){
            int newPos = lift.getTargetPosition() - 25;
            if (newPos < -1902) {
                newPos = -1902;
            }
            lift.setTargetPosition(newPos);
            lift.setPower(0.4);
        }
        //0.228333333
        //0.305
        //0.765

        //Uncomment this section to use the box

        if (gamepad1.triangle) {
            box.setPosition(0.7650);
        }

        if (gamepad1.circle){
            box.setPosition(0.3050);
        }

        if (gamepad1.cross){
            box.setPosition(0.2260);
        }


        //This code is purely for testing purposes
        /*
        if (gamepad1.triangle) {
            reverseMotor(frontLeft);
        }

        if (gamepad1.circle){
            reverseMotor(frontRight);
        }

        if (gamepad1.cross){
            reverseMotor(backRight);
        }

        if (gamepad1.square){
            reverseMotor(backLeft);
        }
        */


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