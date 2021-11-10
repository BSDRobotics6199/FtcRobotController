package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="Linear Op Mode test", group="Linear Opmode")
public class TestController extends RoboOp {

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void loop() {
        dt = runtime.time() - lastTime;
        telemetry.addData("DeltaTime", dt);

        //在这里设能量
        drivePower = -gamepad1.left_stick_y;
        turnPower = gamepad1.right_stick_x;
        strafePower = gamepad1.left_stick_x;

        if (gamepad1.circle) {
            gamepad1.rumble(1000);
        }
        if (gamepad1.cross) {
            servoSqueeze();
        }
        if (gamepad1.square) {
            servoExpand();
        }
        if (gamepad1.circle) {
            carouselClockwise();
        } else if (gamepad1.triangle) {
            carouselCounterClockwise();
        } else {
            carousel.setPower(0);
        }
        if (gamepad1.dpad_up) {
            incrementLift();
        }
        if (gamepad1.dpad_down) {
            decrementLift();
        }
        super.loop();
        //
    }
}