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
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.loggingEnabled = false;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

    }
    double offsetX = 0;
    double offsetY = 0;
    @Override
    public void loop() {
        dt = runtime.time() - lastTime;
        telemetry.addData("IMU data", imu.getPosition().x + " " + imu.getPosition().y + " " +
                imu.getPosition().z);

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