package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;


@TeleOp(name="Linear Op Mode test", group="Linear Opmode")
public class TestController extends RoboOp {
    double liftTarget;
    boolean pastDpadUp;
    boolean pastDpadDown;
    @Override
    public void init() {
        super.init();
        liftTarget = lift.getCurrentPosition();
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
        if (gamepad1.dpad_up && !pastDpadUp) {
            incrementLift();
        }
        if (gamepad1.dpad_down && !pastDpadDown) {
            decrementLift();
        }
        //if ((gamepad1.right_trigger<0.1) || (gamepad1.left_trigger<0.1)) {
            liftTarget += (gamepad1.right_trigger - gamepad1.left_trigger);
        //}
        if (gamepad1.right_bumper) {
            liftPower+= (dt*0.5);
        }
        if (gamepad1.left_bumper) {
            liftPower-= (dt*0.5);
        }
        //imu不工作
        //telemetry.addData("Position: ",  position.x + " " + position.y + " " + position.z);
        /*liftTarget += (gamepad1.right_trigger - gamepad1.left_trigger);
        lift.setTargetPosition((int)liftTarget);
        lift.setPower(Range.clip(Math.abs((gamepad1.right_trigger - gamepad1.left_trigger)), 0, 1));*/
        pastDpadDown = gamepad1.dpad_down;
        pastDpadUp = gamepad1.dpad_up;
        super.loop();
        //a
    }
}