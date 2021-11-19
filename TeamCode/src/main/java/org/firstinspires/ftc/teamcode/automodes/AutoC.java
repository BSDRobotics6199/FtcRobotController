package org.firstinspires.ftc.teamcode.automodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RoboOp;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name="AutoC", group="Auto")
public class AutoC extends RoboOp {

    SampleMecanumDrive drive;

    public static final double METER_PER_INCH = 0.0254;
    public static final double METER_PER_TILE = 0.6096;

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
                drive.trajectoryBuilder(drive.getPoseEstimate()).forward((METER_PER_TILE*1.5)/METER_PER_INCH).build()
        );
    }
}