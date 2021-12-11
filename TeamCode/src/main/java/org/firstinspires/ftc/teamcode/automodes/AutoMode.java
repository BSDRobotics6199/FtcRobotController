package org.firstinspires.ftc.teamcode.automodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;


@Autonomous(name="AutoB", group="Auto")
public abstract class AutoMode extends LinearOpMode {

    SampleMecanumDrive drive;

    public static final double METER_PER_INCH = 0.0254;
    public static final double METER_PER_TILE = 0.6096;

    public DcMotor carousel;
    public DcMotor lift;
    public CRServo intake;
    public double liftTarget;
    public enum Direction {
        FORWARD,
        LEFT,
        RIGHT,
        BACK,
        CLOCKWISE,
        COUNTER_CLOCKWISE
    }

    public enum liftLevel{
        RECEIVE,
        HUB_1,
        HUB_2,
        HUB_3,
    }
    liftLevel level = liftLevel.RECEIVE;
    protected int[] liftPositions = new int[]{5, 730, 1250, 2200};
    private boolean tasks;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d());

        carousel = hardwareMap.get(DcMotor.class, "carousel");
        lift = hardwareMap.get(DcMotor.class, "lift");
        intake = hardwareMap.get(CRServo.class, "intake");

        tasks = false;


        waitForStart();

        if (!tasks){
            doTasks();
            tasks = true;
        }
    }

    public abstract void doTasks();

    public void setLiftLevel(liftLevel newLevel) {
        if (level == liftLevel.RECEIVE) {
            liftTarget = liftPositions[0];
        } else if (level == liftLevel.HUB_1) {
            liftTarget = liftPositions[1];
        } else if (level == liftLevel.HUB_2) {
            liftTarget = liftPositions[2];
        } else if (level == liftLevel.HUB_3) {
            liftTarget = liftPositions[3];
        }
        level = newLevel;
        while (Math.abs(lift.getCurrentPosition() - (int)liftTarget) > 3) {
            lift.setTargetPosition((int)liftTarget);
            lift.setPower(1);
        }
    }

    public void intake(long milliseconds, Direction direction){
        switch (direction){
            case BACK:
                intake.setPower(-1);
                break;
            case FORWARD:
                intake.setPower(1);
                break;
        }

        delay(milliseconds);
    }

    public void move(double tiles, Direction direction){
        TrajectoryBuilder builder = drive.trajectoryBuilder(drive.getPoseEstimate());
        switch (direction){
            case FORWARD:
                builder.forward(tiles*24);
                break;
            case LEFT:
                builder.strafeLeft(tiles*24);
                break;
            case RIGHT:
                builder.strafeRight(tiles*24);
                break;
            case BACK:
                builder.back(tiles*24);
                break;

        }
        drive.followTrajectory(builder.build());
    }

    public void turn(double angle, Direction direction){
        switch (direction) {
            case COUNTER_CLOCKWISE:
                drive.turn(-angle * Math.PI / 180);
                break;
            case CLOCKWISE:
                drive.turn(angle * Math.PI / 180);
                break;
        }
    }

    public void carousel(long milliseconds, Direction direction){
        switch (direction){
            case CLOCKWISE:
                carousel.setPower(0.25);
                break;
            case COUNTER_CLOCKWISE:
                carousel.setPower(-0.25);
                break;
            default:
                return;
        }
        delay(milliseconds);
        carousel.setPower(0);
    }

    public void delay(long milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void spin(double times, Direction direction){
        turn(times*360, direction);
    }

    private void dot(){
        carousel(1000, Direction.CLOCKWISE);
    }
    private void pause(){
        delay(1000);
    }

    private void dash(){
        carousel(3000, Direction.CLOCKWISE);
    }

    public void playString(String string){

        for (char letter: string.toCharArray()){
            switch (letter){
                case 'A':
                    dot();
                    pause();
                    dash();
                    break;
                case 'B':
                    dash();
                    pause();
                    dot();
                    pause();
                    dot();
                    pause();
                    dot();
                   break;
                case 'C':
                    dash();
                    pause();
                    dot();
                    pause();
                    dash();
                    pause();
                    dot();
                    break;
                case 'D':
                    dash();
                    pause();
                    dot();
                    pause();
                    dot();
                    break;
                case 'E':
                    dot();
                    break;
                case 'F':
                    dot();
                    pause();
                    dot();
                    pause();
                    dash();
                    pause();
                    dot();
                    break;
                case 'G':
                    dash();
                    pause();
                    dash();
                    pause();
                    dot();
                    break;
                case 'H':
                    dot();
                    pause();
                    dot();
                    pause();
                    dot();
                    pause();
                    dot();
                    break;
                case 'I':
                    dot();
                    pause();
                    dot();
                    break;
                case 'J':
                    dot();
                    pause();
                    dash();
                    pause();
                    dash();
                    pause();
                    dash();
                    break;
                case 'K':
                    dash();
                    pause();
                    dot();
                    pause();
                    dash();
                    break;
                case 'L':
                    dot();
                    pause();
                    dash();
                    pause();
                    dot();
                    pause();
                    dot();
                    break;
                case 'M':
                    dash();
                    pause();
                    dash();
                    break;
                case 'N':
                    dash();
                    pause();
                    break;
                case 'O':
                    dash();
                    pause();
                    dash();
                    pause();
                    dash();
                    break;
                case 'P':
                    dot();
                    pause();
                    dash();
                    pause();
                    dash();
                    pause();
                    dot();
                    break;
                case 'Q':
                    dash();
                    pause();
                    dash();
                    pause();
                    dot();
                    pause();
                    dash();
                    break;
                case 'R':
                    dot();
                    pause();
                    dash();
                    pause();
                    dot();
                    break;
                case 'S':
                    dot();
                    pause();
                    dot();
                    pause();
                    dot();
                    break;
                case 'T':
                    dash();
                    break;
                case 'U':
                    dot();
                    pause();
                    dot();
                    pause();
                    dash();
                    break;
                case 'V':
                    dot();
                    pause();
                    dot();
                    pause();
                    dot();
                    dash();
                    break;
                case 'W':
                    dot();
                    pause();
                    dash();
                    pause();
                    dash();
                    break;
                case 'X':
                    dash();
                    pause();
                    dot();
                    pause();
                    dot();
                    pause();
                    dash();
                    break;
                case 'Y':
                    dash();
                    pause();
                    dot();
                    pause();
                    dash();
                    pause();
                    dash();
                    break;
                case 'Z':
                    dash();
                    pause();
                    dash();
                    pause();
                    dot();
                    pause();
                    dot();
                    break;
                case ' ':
                    pause();
                    pause();
                    pause();
                    pause();
            }
            pause();
            pause();
            pause();
        }
    }

    public void liftTo(){

    }

}