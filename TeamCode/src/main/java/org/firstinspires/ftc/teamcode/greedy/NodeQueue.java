package org.firstinspires.ftc.teamcode.greedy;

import java.util.ArrayList;

public class NodeQueue {

    ArrayList<Node> queue;

    public NodeQueue(){
        queue = new ArrayList<>();
    }

    public void put(Node node){
        queue.add(node);
    }

    public Node get(){
        Node lowest = queue.get(0);
        for (Node node: queue){
            if (node.getH() < lowest.getH()){
                lowest = node;
            }
        }
        queue.remove(lowest);
        return lowest;
    }
}