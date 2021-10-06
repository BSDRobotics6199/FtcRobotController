package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Linear Op Mode test", group="Linear Opmode")
public class TestController extends LinearOpMode {

    private ElapsedTime runtime;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        runtime = new ElapsedTime();
        while (opModeIsActive()){
            //Code body
            if (gamepad1.x){
                System.out.println("Test");
                System.out.println("Elapsed time: " + runtime.time());
            }
        }
    }
}
