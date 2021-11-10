package org.firstinspires.ftc.teamcode.greedy;

import java.util.ArrayList;

public class NodeMap {
    Node[][] nodeArray;
    int width;
    int height;

    public NodeMap(Node[][] nodeArray){
        this.nodeArray = nodeArray;
        this.width = nodeArray.length;
        this.height = nodeArray[0].length;
    }

    public NodeMap(int width, int height){
        nodeArray = new Node[height][width];
    }

    public Node getNode(int row, int column){
        return nodeArray[row][column];
    }

    public void setNode(int row, int column, Node node){
        nodeArray[row][column] = node;
    }

    public int[] findNode(Node node){
        for (int i = 0; i < nodeArray.length; i ++){
            for (int j = 0; j < nodeArray[i].length; j++){
                if (nodeArray[i][j] == node){
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    public ArrayList<Node> getNeighbors(Node node){
        ArrayList<Node> neighbors = new ArrayList<>();
        int[] position = findNode(node);
        try {
            neighbors.add(getNode(position[0]+1, position[1]));
        }catch (IndexOutOfBoundsException e){

        }
        try {
            neighbors.add(getNode(position[0]-1, position[1]));
        }catch (IndexOutOfBoundsException e){

        }
        try {
            neighbors.add(getNode(position[0], position[1]+1));
        }catch (IndexOutOfBoundsException e){

        }
        try {
            neighbors.add(getNode(position[0], position[1]-1));
        }catch (IndexOutOfBoundsException e){

        }
        return neighbors;

    }
}