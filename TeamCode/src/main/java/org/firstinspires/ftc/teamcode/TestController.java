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

    }

    @Override
    public void loop() {
        telemetry.addData("DeltaTime", dt);
        telemetry.addData("Servo Position", leftFlipPosition + " " + rightFlipPosition);


        //在这里设能量
        drivePower = -gamepad1.left_stick_y;
        strafePower = gamepad1.left_stick_x;
        turnPower = gamepad1.right_stick_x;
        if (gamepad1.right_bumper) {
            drivePower = Range.clip(drivePower * 0.1, -1 , 1);
        }
        turnPower = Range.clip(turnPower + 0.1*(gamepad1.right_trigger - gamepad1.left_trigger), -1 ,1);

        if (gamepad2.right_bumper) {
            intake.setPower(-1);
        } else {
            intake.setPower(0);
        }
        intake.setPower(intake.getPower() + gamepad2.right_trigger);

        if (gamepad2.triangle) {
            flipOut();
        } else if (gamepad2.cross) {
            flipIn();
        }

        count++;
        super.loop();
        //a
    }

    public double curve(double value){
        return -(Math.log(((2/(value+1))-1))/Math.log(Math.E))/4;
    }
}