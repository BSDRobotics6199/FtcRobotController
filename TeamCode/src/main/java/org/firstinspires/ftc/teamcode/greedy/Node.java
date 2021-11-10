package org.firstinspires.ftc.teamcode.greedy;

public class Node {
    private double x, y, h;


    public Node(double x, double y){
        this.x = x;
        this.y = y;
        this.h = Double.MAX_VALUE;

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }
}
