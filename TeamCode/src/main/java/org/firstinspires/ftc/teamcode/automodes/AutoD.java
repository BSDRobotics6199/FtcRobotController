package org.firstinspires.ftc.teamcode.automodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RoboOp;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name="AutoD", group="Auto")
public class AutoD extends AutoTest {

    public void doTasks(){
        drive.followTrajectory(
                drive.trajectoryBuilder(drive.getPoseEstimate()).strafeLeft((METER_PER_TILE/2)/METER_PER_INCH).build()
        );

        carousel.setPower(-0.6);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        carousel.setPower(0);

        drive.followTrajectory(
                drive.trajectoryBuilder(drive.getPoseEstimate()).forward(METER_PER_TILE/METER_PER_INCH).build()
        );

        drive.followTrajectory(
                drive.trajectoryBuilder(drive.getPoseEstimate()).strafeLeft((METER_PER_TILE/2)/METER_PER_INCH).build()
        );
    }
}