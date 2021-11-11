package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Linear Op Mode measure", group="Linear Opmode")
public class Measure extends RoboOp {
    boolean pastTriangle = false;
    @Override
    public void init() {
        super.init();
    }
    double offsetX = 0;
    double offsetY = 0;
    @Override
    public void loop() {
        dt = runtime.time() - lastTime;
        telemetry.addData("DeltaTime", dt);

        //在这里设能量
        drivePower = -gamepad1.left_stick_y;
        turnPower = gamepad1.right_stick_x;
        strafePower = gamepad1.left_stick_x;
        if (gamepad1.triangle && !pastTriangle) {
            offsetX = imu.getPosition().x;
            offsetY = imu.getPosition().y;
        }

        super.loop();
        pastTriangle = gamepad1.triangle;
    }
}