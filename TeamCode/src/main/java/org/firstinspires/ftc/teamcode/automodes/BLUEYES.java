package org.firstinspires.ftc.teamcode.automodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="YEES (Blue)", group="Auto")
public class BLUEYES extends LinearOpMode {
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;

    @Override
    public void runOpMode() throws InterruptedException {
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "rearRight");
        backLeft = hardwareMap.get(DcMotor.class, "rearLeft");

        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        waitForStart();

        backRight.setPower(-0.5);
        frontRight.setPower(0.5);
        frontLeft.setPower(-0.5);
        backLeft.setPower(0.5);

        sleep(900);

        backRight.setPower(-0.25);
        frontRight.setPower(-0.25);
        frontLeft.setPower(-0.25);
        backLeft.setPower(-0.25);

        sleep(2000);

        backRight.setPower(0);
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }
}
