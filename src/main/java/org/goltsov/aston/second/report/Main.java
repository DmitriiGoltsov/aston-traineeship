package org.goltsov.aston.second.report;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree myBST = new BinarySearchTree();

        myBST.insert(47);
        myBST.insert(21);
        myBST.insert(76);
        myBST.insert(18);
        myBST.insert(27);
        myBST.insert(52);
        myBST.insert(82);

        MinHeap myHeap = new MinHeap();
        myHeap.insert(99);
        myHeap.insert(72);
        myHeap.insert(61);
        myHeap.insert(58);
        myHeap.insert(10);
        myHeap.insert(75);

        // Проверка BST
        System.out.println("BST Contains 27:");
        System.out.println(myBST.contains(27)); // expecting true
        System.out.println(myBST.recursiveContains(27)); // expecting true

        System.out.println("\nBST Contains 17:");
        System.out.println(myBST.contains(17)); // expecting false
        System.out.println(myBST.recursiveContains(17)); // expecting false

        List<Integer> treeContentBFS = myBST.breadthFirstSearch();
        System.out.println("BFS: " + treeContentBFS + "\n");
        List<Integer> treeContentPreOrderDFS = myBST.preOrderDFS();
        System.out.println("preOrderDFS: " + treeContentPreOrderDFS);
        List<Integer> treeContentPostOrderDFS = myBST.postOrderDFS();
        System.out.println("postOrderDFS: " + treeContentPostOrderDFS);
        List<Integer> treeContentInOrderDFS = myBST.inOrderDFS();
        System.out.println("inOrderDFS: " + treeContentInOrderDFS);

        System.out.println();

        // Проверка кучи
        // expecting [10, 58, 72, 99, 61, 75]
        System.out.println("The content of my heap: " + myHeap.getHeap());

        Integer removedValue1 = myHeap.remove();

        /* expecting:
         * First Removed Value: 10
         * [58, 61, 72, 99, 75]
         */
        System.out.println("First Removed Value: " + removedValue1);
        System.out.println(myHeap.getHeap());

        Integer removedValue2 = myHeap.remove();

        /* expecting:
         * Second Removed Value: 58
         * [61, 75, 72, 99]
         */
        System.out.println("Second Removed Value: " + removedValue2);
        System.out.println(myHeap.getHeap());
    }
}
