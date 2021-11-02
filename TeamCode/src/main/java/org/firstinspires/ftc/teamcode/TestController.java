package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.ServoImpl;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="Linear Op Mode test", group="Linear Opmode")
public class TestController extends RoboOp {

    @Override
    public void init() {
       super.init();
    }

    @Override
    public void loop() {
        super.loop();
        //Here you will have to make the main logic of the operation mode
        //Ok apparently when you have the stick forwards it is negative y so you have to negate it
        float drivePower = -gamepad1.left_stick_y;
        float turnPower = gamepad1.right_stick_x;
        float strafePower = gamepad1.left_stick_x;
        dt = runtime.time() - lastTime;

        //So this part makes sure that the power is not too high or too low, it also makes the robot
        //actually move, so from what I understand, it makes one side move faster than the other to
        //achieve the rotation
        frontRight.setPower(Range.clip(drivePower - turnPower - strafePower, -1, 1));
        backRight.setPower(Range.clip(drivePower - turnPower + strafePower, -1, 1));

        frontLeft.setPower(Range.clip(drivePower + turnPower + strafePower, -1, 1));
        backLeft.setPower(Range.clip(drivePower + turnPower - strafePower, -1, 1));

        //funny it rumbles if you press circle
        if (gamepad1.circle) {
            gamepad1.rumble(1000);
        }
        if (gamepad1.cross) {
            servoSqueeze();
        }
        if (gamepad1.square) {
            servoExpand();
        }
    }
}
