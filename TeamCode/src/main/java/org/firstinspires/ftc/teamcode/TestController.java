package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;


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
        telemetry.addData("Lift Position", lift.getTargetPosition());


        //在这里设能量
        if (lift.getTargetPosition()>(-500)) {
            drivePower = -0.3*(curve(gamepad1.left_stick_y));
            strafePower = 0.3*(curve(gamepad1.left_stick_x));
            turnPower = 0.1*(curve(gamepad1.right_stick_x));
        } else {
            drivePower = -1*(curve(gamepad1.left_stick_y));
            strafePower = curve(gamepad1.left_stick_x);
            lift.setPower(0.4);
            turnPower = curve(gamepad1.right_stick_x);
        }
        if (gamepad1.right_bumper) {
            drivePower = Range.clip(drivePower - 0.1, -1 , 1);
        }
        turnPower = Range.clip(turnPower + 0.1*(gamepad1.right_trigger - gamepad1.left_trigger), -1 ,1);
        //intake.setPower(-1*gamepad2.left_stick_y);
        if (gamepad2.right_trigger>0.005) {
            carouselCounterClockwise();
        } else if (gamepad2.left_trigger > 0.005){
            carouselClockwise();
        } else {
            carousel.setPower(0);
        }

        int newPos = lift.getTargetPosition() + Math.round(25 * gamepad2.left_stick_y);
        if (newPos > 22) {
            newPos = 22;
        }
        if (newPos < -1902) {
            newPos = -1902;
        }
        lift.setTargetPosition(newPos);
        lift.setPower(0.4);

        //0.228333333
        //0.305
        //0.765

        //Uncomment this section to use the box

        if (gamepad2.triangle) {
            box.setPosition(0.7650);
        }

        if (gamepad2.circle || gamepad2.square){
            box.setPosition(0.3050);
        }

        if (gamepad2.cross){
            box.setPosition(0.2260);
        }
        if (gamepad2.right_bumper) {
            intakeClockwise();
        } else if (gamepad2.left_bumper) {
            intakeCounterClockwise();
        } else { intake.setPower(0); }

        if (gamepad2.dpad_down) {
            cap.setPosition(cap.getPosition() + 0.5*dt);
        } else if (gamepad2.dpad_up) {
            cap.setPosition(cap.getPosition() - 0.5*dt);
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
        count++;
        super.loop();
        //a
    }

    public double curve(double value){
        return -(Math.log(((2/(value+1))-1))/Math.log(Math.E))/4;
    }
}