package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

import java.util.ArrayList;

public class AutoMode extends RoboOp {
    //TODO: Run diagnostics to get


    @Override
    public void init() {
        super.init();

        double[] targetA;
        double[] targetB;
        double[] targetC;
        double[] targetD;
    }

    @Override
    public void loop() {
        super.loop();
        Position position = imu.getPosition();
        Orientation orientation = imu.getAngularOrientation();


    }

    public ArrayList<Node> greedPath(int nodesPerBlock){
        ArrayList<Node> path = new ArrayList<>();
    }
}

