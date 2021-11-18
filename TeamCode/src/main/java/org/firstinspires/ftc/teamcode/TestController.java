package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="Linear Op Mode test", group="Linear Opmode")
public class TestController extends RoboOp {
    boolean pastDpadUp;
    boolean pastDpadDown;
    int count = 0;
    @Override
    public void init() {
        super.init();
        liftTarget = lift.getCurrentPosition();
        liftTarget -= 30;/*
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
        drivePower = -gamepad1.left_stick_y;
        strafePower = gamepad1.left_stick_x;
        if (level == liftLevel.FLOOR) {
            turnPower = 0.3*gamepad1.right_stick_x;
        } else {
            turnPower = gamepad1.right_stick_x;
        }

        if (gamepad1.cross) {
            servoSqueeze();
        }
        if (gamepad1.square) {
            servoExpand();
        }
        if (gamepad1.triangle) {
            carouselCounterClockwise();
        }else if (gamepad1.circle){
            carouselClockwise();
        } else {
            carousel.setPower(0);
        }
        if (gamepad1.dpad_up && !pastDpadUp) {
            incrementLift();
        }
        if (gamepad1.dpad_down && !pastDpadDown) {
            decrementLift();
        }
        //if ((gamepad1.right_trigger<0.1) || (gamepad1.left_trigger<0.1)) {
        incdec += (gamepad1.right_trigger - gamepad1.left_trigger);
        //}
        if (gamepad1.right_bumper) {
            carouselSpeed+= (dt*0.1);
        }
        if (gamepad1.left_bumper) {
            carouselSpeed-= (dt*0.1);
        }
        //imu不工作
        //telemetry.addData("Position: ",  position.x + " " + position.y + " " + position.z);\
        pastDpadDown = gamepad1.dpad_down;
        pastDpadUp = gamepad1.dpad_up;
        count++;
        super.loop();
        //a
    }
}