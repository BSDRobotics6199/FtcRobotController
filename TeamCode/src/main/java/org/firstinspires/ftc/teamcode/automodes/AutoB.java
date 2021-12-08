package org.firstinspires.ftc.teamcode.automodes;

import android.hardware.camera2.params.MeteringRectangle;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.sun.tools.javac.tree.JCTree;

import org.firstinspires.ftc.teamcode.RoboOp;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.ArrayList;

@Autonomous(name="AutoB", group="Auto")
public class AutoB extends RoboOp {

    SampleMecanumDrive drive;

    public static final double METER_PER_INCH = 0.0254;
    public static final double METER_PER_TILE = 0.6096;

    public enum Direction {
        FORWARD,
        LEFT,
        RIGHT,
        BACK
    }

    private boolean tasks;

    @Override
    public void init() {
        super.init();
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d());

        tasks = false;
    }

    @Override
    public void loop() {
        if (!tasks){
            doTasks();
            tasks = true;
        }
    }

    public void doTasks(){
        drive.followTrajectory(
                drive.trajectoryBuilder(drive.getPoseEstimate()).back((METER_PER_TILE/2)/METER_PER_INCH).build()
        );
        carousel.setPower(0.6);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        carousel.setPower(0);
        drive.followTrajectory(
                drive.trajectoryBuilder(drive.getPoseEstimate()).strafeRight(METER_PER_TILE/METER_PER_INCH).build()
        );
        drive.followTrajectory(
                drive.trajectoryBuilder(drive.getPoseEstimate()).back((METER_PER_TILE/2)/METER_PER_INCH).build()
        );
    }

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
}