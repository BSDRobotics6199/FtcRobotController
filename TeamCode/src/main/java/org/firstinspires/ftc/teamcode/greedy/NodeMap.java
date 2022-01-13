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

    public ArrayList<Node> greedyBestFirst(Node start, Node end){
        NodeQueue openSet = new NodeQueue();
        ArrayList<Node> closedSet = new ArrayList<>();

        start.setH(Double.MAX_VALUE);
        Node currentNode = null;
        openSet.put(start);
        while (currentNode != end){
            currentNode = openSet.get();
            ArrayList<Node> neighbors = getNeighbors(currentNode);
            for (Node neighbor: neighbors){
                if (closedSet.contains(neighbor)){
                    break;
                }
                neighbor.setH(Math.abs(neighbor.getX() - end.getX()) + Math.abs(neighbor.getY() - end.getY()));
                neighbor.setPrevious(currentNode);
                if (neighbor.getH() < currentNode.getH()){
                    openSet.put(neighbor);
                }
            }
            closedSet.add(currentNode);
        }
        ArrayList<Node> reverseSolution = new ArrayList<>();
        ArrayList<Node> solution = new ArrayList<>();
        while (currentNode.getPrevious() != null){
            reverseSolution.add(currentNode);
            currentNode = currentNode.getPrevious();
        }
        for (int i = 0; i < reverseSolution.size(); i++){
            solution.set(reverseSolution.size() - i, reverseSolution.get(i));
        }
        return solution;
    }
}