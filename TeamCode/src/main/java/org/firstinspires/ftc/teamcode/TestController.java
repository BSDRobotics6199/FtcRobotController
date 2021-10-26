package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="Linear Op Mode test", group="Linear Opmode")
public class TestController extends OpMode {

    private ElapsedTime runtime;

    @Override
    public void init() {
        runtime = new ElapsedTime();
        telemetry.addData("Status", "Started");
        telemetry.update();
    }

    @Override
    public void loop() {
        if (gamepad1.touchpad){
            telemetry.addData("Elapsed time (when key pressed): ", runtime.time());
            telemetry.update();
        }
    }
}
