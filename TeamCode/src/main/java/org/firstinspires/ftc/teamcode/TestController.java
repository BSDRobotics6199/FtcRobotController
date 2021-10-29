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
public class TestController extends OpMode {
    //Note this code did not directly come from the examples but is heavily inspired by the examples
    //along with the code from last year, I have made some changes but a decent amount of the logic
    //is paraphrased, this is merely just a foundation that we will work to change later.

    private ElapsedTime runtime;
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;
    private Servo leftClaw;
    private Servo rightClaw;
    private ServoController servoController;
    private int leftClawPort;
    private int rightClawPort;
    private double leftClawPosition;
    @Override
    public void init() {
        //Starts the operation mode
        runtime = new ElapsedTime();
        telemetry.addData("Status", "Started");
        telemetry.update();

        //Assuming that there are four drive wheels
        //This registers them
        frontRight  = initializeMotor("frontRight");
        frontLeft = initializeMotor("frontLeft");
        backRight = initializeMotor("backRight");
        backLeft = initializeMotor("backLeft");
        servoController = hardwareMap.getAll(ServoController.class).get(0);
        telemetry.addData("ServoXs: ", hardwareMap.getAll(ServoImplEx.class));
        telemetry.addData("ServoXs: ", hardwareMap.getAll(ServoImpl.class));
    }

    @Override
    public void loop() {
        //Here you will have to make the main logic of the operation mode
        //Ok apparently when you have the stick forwards it is negative y so you have to negate it
        float drivePower = -gamepad1.left_stick_y;
        float turnPower = gamepad1.right_stick_x;
        float strafePower = gamepad1.left_stick_x;

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

    private DcMotor initializeMotor(String hardwareID) {
        DcMotor returnMotor = hardwareMap.get(DcMotor.class, hardwareID);
        returnMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        returnMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (hardwareID.substring(hardwareID.length()-4).equalsIgnoreCase("Left")) {
            returnMotor.setDirection(DcMotor.Direction.REVERSE);
        }
        return returnMotor;
    }
    private void servoSqueeze(/*int leftBound, int rightBound*/) {
        servoController.setServoPosition(0,
                Range.clip(servoController.getServoPosition(0) - 0.1, 0, 1));
        servoController.setServoPosition(1,
                Range.clip(servoController.getServoPosition(1) - 0.1, 0, 1));
        if (Math.abs(leftClawPosition-servoController.getServoPosition(0))<0.01) {
            telemetry.addData("leftStrained" , true);
        }
        leftClawPosition = servoController.getServoPosition(0);
        telemetry.addData("Right position:", servoController.getServoPosition(0));
    }

    private void servoExpand(/*int leftBound, int rightBound*/){
        servoController.setServoPosition(0,
                Range.clip(servoController.getServoPosition(0) - 0.1, 0, 1));
        servoController.setServoPosition(1,
                Range.clip(servoController.getServoPosition(1) - 0.1, 0, 1));
        if (Math.abs(leftClawPosition-servoController.getServoPosition(0))<0.01) {
            telemetry.addData("leftStrained" , true);
        }
        leftClawPosition = servoController.getServoPosition(0);
        telemetry.addData("Left position:", servoController.getServoPosition(0));
    }
}
