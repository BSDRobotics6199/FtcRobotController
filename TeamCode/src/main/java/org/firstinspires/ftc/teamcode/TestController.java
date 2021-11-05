package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
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
        dt = runtime.time() - lastTime;
        telemetry.addData("DeltaTime", dt);
        //Here you will have to make the main logic of the operation mode
        //Ok apparently when you have the stick forwards it is negative y so you have to negate it
        drivePower = -gamepad1.left_stick_y;
        turnPower = gamepad1.right_stick_x;
        strafePower = gamepad1.left_stick_x;

        //So this part makes sure that the power is not too high or too low, it also makes the robot
        //actually move, so from what I understand, it makes one side move faster than the other to
        //achieve the rotation

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
        if (gamepad1.circle) {
            carouselClockwise();
        } else if (gamepad1.triangle) {
            carouselCounterClockwise();
        } else {
            carousel.setPower(0);
        }
        liftTarget = liftTarget + gamepad1.right_trigger - gamepad1.left_trigger;
        super.loop();
        //
    }
}