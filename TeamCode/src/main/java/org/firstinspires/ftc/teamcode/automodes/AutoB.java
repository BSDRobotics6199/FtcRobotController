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
public class AutoB extends AutoTest {

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
}