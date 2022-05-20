package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Red TeleOP", group="Linear Opmode")
public class RedTeleOP extends RoboOp {
    boolean pastDpadUp;
    boolean pastDpadDown;
    int count = 0;
    @Override
    public void init() {
        super.init();

    }

    @Override
    public void loop() {
        telemetry.addData("DeltaTime", dt);
        telemetry.addData("Servo Position", leftFlipPosition + " " + rightFlipPosition);

        //在这里设能量
        drivePower = gamepad1.left_stick_y - (0.5*gamepad2.left_stick_y);
        strafePower = -gamepad1.left_stick_x + (0.5*gamepad2.left_stick_x);
        turnPower = gamepad1.right_stick_x + (0.5*gamepad2.right_stick_x);
        if (gamepad1.right_bumper) {
            drivePower = Range.clip(drivePower * 0.1, -1 , 1);
        }
        turnPower = Range.clip(turnPower + 0.1*(gamepad1.right_trigger - gamepad1.left_trigger), -1 ,1);

        if (gamepad2.right_bumper) {
            intake.setPower(1);
        } else {
            intake.setPower(0);
        }
        intake.setPower(intake.getPower() - gamepad2.right_trigger);
        telemetry.addData("intake power", intake.getPower());
        telemetry.addData("Drive Multiplier", driveVariable);
        if (gamepad2.triangle) {
            rightFlipOut();
        } else if (gamepad2.cross) {
            rightFlipIn();
        }
        if (gamepad2.left_bumper){
            capstone.setPower(-0.65);
        } else if (gamepad2.left_trigger>0.01){
            capstone.setPower(0.65);
        } else {
            capstone.setPower(0);
        }
        if (gamepad1.left_bumper) {
            driveVariable = Range.clip(driveVariable - 0.5*dt, 0.1, 1);
        } else if (gamepad1.right_bumper) {
            driveVariable = Range.clip(driveVariable + 0.5*dt, 0.1, 1);
        }
        count++;
        super.loop();
        //a
    }

    public double curve(double value){
        return -(Math.log(((2/(value+1))-1))/Math.log(Math.E))/4;
    }
}