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
        if (level == liftLevel.RECEIVE) {
            turnPower = 0.3*gamepad1.right_stick_x;
        } else {
            turnPower = gamepad1.right_stick_x;
        }

        if (gamepad1.cross) {
            intake.setPower(liftPower);
        } else if (gamepad1.square) {
            intake.setPower(-1*liftPower);
        } else {
            intake.setPower(0);
        }
        if (gamepad1.triangle) {
            carouselCounterClockwise();
        }else if (gamepad1.circle){
            carouselClockwise();
        } else {
            carousel.setPower(0);
        }
        /*if (gamepad1.dpad_down)
            setLiftLevel(liftLevel.RECEIVE);
        else if (gamepad1.dpad_left)
            setLiftLevel(liftLevel.HUB_1);
        else if (gamepad1.dpad_up)
            setLiftLevel(liftLevel.HUB_2);
        else if (gamepad1.dpad_right)
            setLiftLevel(liftLevel.HUB_3);*/
        //if ((gamepad1.right_trigger<0.1) || (gamepad1.left_trigger<0.1)) {
        //}
        if (gamepad1.right_bumper) {
            liftPower+= (dt*0.1);
        }
        if (gamepad1.left_bumper) {
            liftPower-= (dt*0.1);
        }
        liftTarget += 5*(gamepad1.right_trigger - gamepad1.left_trigger);
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