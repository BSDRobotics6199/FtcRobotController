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

        carousel = hardwareMap.get(DcMotor.class, "YourMother");
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

    public void turn(double angle){
        drive.turn(angle*Math.PI/180);
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
        stall(milliseconds);
        carousel.setPower(0);
    }

    public void stall(long milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}