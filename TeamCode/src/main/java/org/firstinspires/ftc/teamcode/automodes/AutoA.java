package org.firstinspires.ftc.teamcode.automodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoboOp;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import java.util.ArrayList;

@Autonomous(name="AutoA", group="Auto")
public class AutoA extends RoboOp {

    SampleMecanumDrive drive;
    ArrayList<Trajectory> trajectories = new ArrayList<>();
    ArrayList<Vector2d> locations;

    @Override
    public void init() {
        super.init();
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d());
        for (Vector2d location: locations){
            drive.updatePoseEstimate();
            trajectories.add(
                    drive.trajectoryBuilder(drive.getPoseEstimate()).lineTo(location).build()
            );
        }
    }

    @Override
    public void loop() {
        super.loop();
        if (!drive.isBusy()) {
            drive.followTrajectory(trajectories.get(0));
            trajectories.remove(0);
        }
    }
}