package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Linear Op Mode test", group="Linear Opmode")
public class Measure extends RoboOp {
    boolean pastTriangle = false;
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
        if (gamepad1.triangle && !pastTriangle) {
            BNO055IMU.CalibrationData data = new BNO055IMU.CalibrationData();
            data.dxMag = 0;
            data.dyMag = 0;
            imu.writeCalibrationData(data);
        }

        super.loop();
        pastTriangle = gamepad1.triangle;
    }
}