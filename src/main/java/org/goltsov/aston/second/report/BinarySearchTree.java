package org.goltsov.aston.second.report;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;

public class BinarySearchTree {

    private Node root;

    public Node getRoot() {
        return this.root;
    }

    public boolean insert(int value) {
        Node newNode = new Node(value);

        if (root == null) {
            root = newNode;
            return true;
        }

        Node temp = root;
        while (true) {

            if (newNode.getValue() == temp.getValue()) {
                return false;
            }

            if (newNode.getValue() < temp.getValue()) {
                if (temp.getLeft() == null) {
                    temp.setLeft(newNode);
                    return true;
                }

                temp = temp.getLeft();
            } else {
                if (temp.getRight() == null) {
                    temp.setRight(newNode);
                    return true;
                }
                temp = temp.getRight();
            }
        }
    }

    public boolean contains(int value) {

        if (root == null) {
            return false;
        }

        Node temp = root;
        while (temp != null) {

            if (value < temp.getValue()) {
                temp = temp.getLeft();
            } else if (value > temp.getValue()) {
                temp = temp.getRight();
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean recursiveContains(int value) {
        return recursiveContains(this.root, value);
    }

    public boolean recursiveContains(Node currentNode, int value) {

        if (currentNode == null) {
            return false;
        }

        if (currentNode.getValue() == value) {
            return true;
        }

        if (value < currentNode.getValue()) {
            return recursiveContains(currentNode.getLeft(), value);
        } else {
            return recursiveContains(currentNode.getRight(), value);
        }
    }

    public List<Integer> breadthFirstSearch() {
        Node currentNode = this.root;

        Queue<Node> queue = new LinkedList<>();
        List<Integer> results = new ArrayList<>();
        queue.add(currentNode);

        while(queue.size() > 0) {
            currentNode = queue.remove();
            results.add(currentNode.getValue());

            if (currentNode.getLeft() != null) {
                queue.add(currentNode.getLeft());
            }

            if (currentNode.getRight() != null) {
                queue.add(currentNode.getRight());
            }
        }

        return results;
    }

    public List<Integer> preOrderDFS() {

        List<Integer> results = new ArrayList<>();

        class Traverse {
            Traverse(Node currentNode) {
                results.add(currentNode.getValue());
                if (currentNode.getLeft() != null) {
                    new Traverse(currentNode.getLeft());
                }
                if (currentNode.getRight() != null) {
                    new Traverse(currentNode.getRight());
                }
            }
        }

        new Traverse(this.root);
        return results;
    }

    public List<Integer> postOrderDFS() {

        List<Integer> results = new ArrayList<>();

        class Traverse {
            Traverse(Node currentNode) {

                if (currentNode.getLeft() != null) {
                    new Traverse(currentNode.getLeft());
                }
                if (currentNode.getRight() != null) {
                    new Traverse(currentNode.getRight());
                }
                results.add(currentNode.getValue());
            }
        }

        new Traverse(this.root);
        return results;
    }

    public List<Integer> inOrderDFS() {

        List<Integer> results = new ArrayList<>();

        class Traverse {
            Traverse(Node currentNode) {

                if (currentNode.getLeft() != null) {
                    new Traverse(currentNode.getLeft());
                }

                results.add(currentNode.getValue());

                if (currentNode.getRight() != null) {
                    new Traverse(currentNode.getRight());
                }
            }
        }

        new Traverse(this.root);
        return results;
    }

}