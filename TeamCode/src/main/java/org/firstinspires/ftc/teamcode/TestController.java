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

    }

    @Override
    public void loop() {
        telemetry.addData("DeltaTime", dt);

        //在这里设能量
        drivePower = curve(--gamepad1.left_stick_y);
        strafePower = curve(gamepad1.left_stick_x);
        lift.setPower(0.4);
        turnPower = gamepad1.right_stick_x;


        //intake.setPower(-1*gamepad2.left_stick_y);
        /*if (gamepad2.triangle) {
            carouselCounterClockwise();
        }else */if (gamepad2.circle){
            carouselClockwise();
        } else {
            carousel.setPower(0);
        }
        //
        if (gamepad1.left_trigger > 0.005) {
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

        if (/*gamepad1.circle || */gamepad1.square){
            box.setPosition(0.3050);
        }

        if (gamepad1.cross){
            box.setPosition(0.2260);
        }
        if (gamepad1.right_bumper) {
            intakeClockwise();
        }
        if (gamepad1.right_trigger>0.005) {
            intakeCounterClockwise();
        }
        /*
        if (gamepad1.dpad_up) {
            lift.setTargetPosition(22);
        }

        if (gamepad1.dpad_right || gamepad1.dpad_left){
            lift.setTargetPosition(940);
        }

        if (gamepad1.dpad_down){
            lift.setTargetPosition(-1902);
        }

         */




        //imu他妈的没用肥沃
        //telemetry.addData("Position: ",  position.x + " " + position.y + " " + position.z);\
        pastDpadDown = gamepad1.dpad_down;
        pastDpadUp = gamepad1.dpad_up;
        count++;
        super.loop();
        //a
    }

    public double curve(double value){
        return -(Math.log((2/(value+1)))/Math.log(Math.E))/4;
    }
}