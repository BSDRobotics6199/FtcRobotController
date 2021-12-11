package org.firstinspires.ftc.teamcode.automodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name="AutoB", group="Auto")
public abstract class AutoMode extends OpMode {

    SampleMecanumDrive drive;

    public static final double METER_PER_INCH = 0.0254;
    public static final double METER_PER_TILE = 0.6096;

    public DcMotor carousel;

    public enum Direction {
        FORWARD,
        LEFT,
        RIGHT,
        BACK,
        CLOCKWISE,
        COUNTER_CLOCKWISE
    }

    private boolean tasks;

    @Override
    public void init() {
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d());

        carousel = hardwareMap.get(DcMotor.class, "carousel");
        tasks = false;
    }

    @Override
    public void loop() {
        if (!tasks){
            doTasks();
            tasks = true;
        }
    }

    public abstract void doTasks();

    public void move(double tiles, Direction direction){
        TrajectoryBuilder builder = drive.trajectoryBuilder(drive.getPoseEstimate());
        switch (direction){
            case FORWARD:
                builder.forward(tiles*METER_PER_TILE/METER_PER_INCH);
                break;
            case LEFT:
                builder.strafeLeft(tiles*METER_PER_TILE/METER_PER_INCH);
                break;
            case RIGHT:
                builder.strafeRight(tiles*METER_PER_TILE/METER_PER_INCH);
                break;
            case BACK:
                builder.back(tiles*METER_PER_TILE/METER_PER_INCH);
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
        carousel(500, Direction.CLOCKWISE);
    }
    private void pause(){
        delay(500);
    }

    private void dash(){
        carousel(1500, Direction.CLOCKWISE);
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

}