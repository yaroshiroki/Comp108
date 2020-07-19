/*

Name: Yaroslav Shiroki
Student ID: 201458436
University email address: Y.Shiroki@student.liverpool.ac.uk

Time Complexity and explanation:
f denotes initial cabinet size
n denotes the total number of requests
d denotes number of distinct requests
You can use any of the above notations or define additional notation as you wish.

appendIfMiss(): The time complexity of this function is (fnd)^2 - where the denotations are stated as above in the time complexity explanation.

moveToFront(): The time complexity of this function is (fnd)^2 - where the denotations are stated as above in the time complexity explanation.

freqCount(): The time complexity of this function is (fnd)^2 - where the denotations are stated as above in the time complexity explanation. I wasn't sure if the time complexity
would be affected by the helper functionn freqSort, as this has 2 nested loops and has the time complexity of f^2. As this is called within the most nested loop, this could 
increase the entire functions time complexity to ((f)^2) * ((fnd)^2). (This is based on what is complete as the function is not fully working).

*/

import java.util.*;
import java.io.*;


class COMP108A2List {

    private static Scanner keyboardInput = new Scanner (System.in);
    public static Node head, tail; // head and tail of the linked list
    private static final int MaxInitCount = 10;
    private static final int MaxReqCount = 100;
    public static int initCount;
    public static int reqCount;

    public static int[] reqData = new int[MaxReqCount]; // store the requests, accessible to all methods


    // DO NOT change the main method
    public static void main(String[] args) throws Exception {
        Node curr;
        int tmp=-1;
        int[] initData = new int[MaxInitCount];

        initCount = 0;
        reqCount = 0;
        head = null;
        tail = null;

        try {
//			System.out.println();
//			System.out.print("Enter the initial number of files in the cabinet (1-" + MaxInitCount + "): ");
            initCount = keyboardInput.nextInt();
            if (initCount > MaxInitCount || initCount <= 0)
                System.exit(0);
//			System.out.print("Enter the initial file IDs in the cabinet (" + initCount + " different +ve integers): ");
            for (int i=0; i<initCount; i++)
                initData[i] = keyboardInput.nextInt();
//			System.out.println();
//			System.out.print("Enter the number of file requests (1=" + MaxReqCount + "): ");
            reqCount = keyboardInput.nextInt();
            if (reqCount > MaxReqCount || reqCount <= 0)
                System.exit(0);
//			System.out.print("Enter the request file IDs (" + reqCount + " different +ve integers): ");
            for (int i=0; i<reqCount; i++)
                reqData[i] = keyboardInput.nextInt();
        }
        catch (Exception e) {
            keyboardInput.next();
            System.exit(0);
        }



        try {
            System.out.println("appendIfMiss...");
            // create a list with the input data
            // call appendIfMiss()
            for (int i=initCount-1; i>=0; i--) {
                insertNodeHead(new Node(initData[i]));
            }
            appendIfMiss();
        }
        catch (Exception e) {
            System.out.println("appendIfMiss exception! " + e);
        }

        try {
            System.out.println("moveToFront...");
            // empty the previous list and restart with the input data
            // then call moveToFront()
            emptyList();
            for (int i=initCount-1; i>=0; i--) {
                insertNodeHead(new Node(initData[i]));
            }
            moveToFront();
        }
        catch (Exception e) {
            System.out.println("moveToFront exception!");
        }

        try {
            System.out.println("freqCount...");
            // empty the previous list and restart with the input data
            // then call freqCount()
            emptyList();
            for (int i=initCount-1; i>=0; i--) {
                insertNodeHead(new Node(initData[i]));
            }
            freqCount();
        }
        catch (Exception e) {
            System.out.println("freqCount exception!");
        }
    }

    // append to end of list when miss
    static void appendIfMiss() {
		//number of comparisons in string format.
        String totalCosts = "";
        int n = 0;
		//current node.
        Node curr;
		//number of hits.
        int hit = 0;
		//loop through the requests.
        while (n < reqCount) {
			//number of comparisons set inside the loop, so through each run throug,
			//we can reset it back to 0.
            int cost = 0;
			//flag which changes if variable is in list or isn't.
            boolean inList = false;
			//sets the current node to the head of the list to start iteration.
            curr = head;
			//iterates through the list until the node is empty and until the value is found.
            while((!inList) && (curr != null)) {
				//first comparison occurs.
				//if reqData is the same as data in list then hit.
                if(curr.data == reqData[n]) {
                    inList = true;
                }
                else {
					//if not then move onto the next node.
                    curr = curr.next;
                }
				//cost is increased as we compared data with reqData.
                cost = cost + 1;
            }
			//format the cost as a String.
            totalCosts = totalCosts + cost + " ";
            if(inList) {
				//if inList then hit.
                hit = hit + 1;
            }
            else {
				//if request is not in the list then add the request to list.
                Node temp = new Node(reqData[n]);
				//add node.
                tail.next = temp;
				//set tail.
                tail = temp;
            }
            n = n + 1;
        }
		//print in the required format.
        System.out.println(totalCosts);
        System.out.println(hit + " h");
        printList();
    }


    // move the file requested to the beginning of the list
    static void moveToFront() {
        String totalCosts = "";
        int hit = 0;
        int n = 0;
		//current node.
        Node curr;
		//previous node.
        Node prev;
        boolean inList = false;
		//loops through request.
        while (n < reqCount) {
            int cost = 0;
            curr = head;
			//initialising prev beforore it's called.
            prev = curr;
            while (curr != null) {
                cost = cost + 1;
                if (curr.data == reqData[n]) {
                    inList = true;
                    hit = hit + 1;
					//if curr is already head then break.
                    if (curr == head) {
                        break;
                    }
					//swaps the prev next to currrent next.
                    prev.next = curr.next;
					//makes curr.next head and,
                    curr.next = head;
					//makes head curr.
                    head = curr;
                    break;
                }
				//updates curr and prev takes old node.
                prev = curr;
                curr = curr.next;
            }
            totalCosts = totalCosts + cost + " ";
            if (!inList) {
				//adds node to head of linked list.
                insertNodeHead(new Node(reqData[n]));
            }
            else {
                inList = false;
            }
            n = n + 1;
        }
        System.out.println(totalCosts);
        System.out.println(hit + " h");
        printList();
    }

    // move the file requested so that order is by non-increasing frequency
    static void freqCount() {
        String totalCosts = "";
        int hit = 0;
        int n = 0;
        Node curr;
		Node prev;
        boolean inList = false;
        while (n < reqCount) {
            int cost = 0;
            curr = head;
			prev = curr;
			prev.freq = curr.freq;
            while (curr != null) {
				//comparison occurs.
                cost = cost + 1;
                if (curr.data == reqData[n]) {
					//hit if the data in the list is the same as reqData.
                    hit = hit + 1;
					//increments the node freq if a hit has been detected.
                    curr.freq = curr.freq + 1;
					if (curr.freq > prev.freq) {
						prev.next = curr.next;
						curr.next = head;
						head = curr;
					}
                    inList = true;
                    curr = head;
					//using lab freqSort, we can sort the list by frequency.
                    freqSort();
					//once statement is complete, break and end.
                    break;
                }
                curr = curr.next;
            }
            totalCosts = totalCosts + cost + " ";
            if (!inList) {
				//adds data to tail
                tail.next = new Node(reqData[n]);
				//declaring the tail again.
                tail = tail.next;
            }
			//using lab again sort by frequency.
            freqSort();
            inList = false;
            n = n + 1;
        }
		//print.
        System.out.println(totalCosts);
        System.out.println(hit + " h");
        printList();
    }
	//sorts by frequency using the frequency sort algorithm in the linked list.
    static void freqSort() {
        Node curr=null, min=null, node=null;
        if (head != null) {
            curr = head;
            // find the i-th smallest number
            while (curr.next != null) {
                // go through the list to find the minimum
                min = curr;
                node = curr.next;
                while (node != null) {
                    if (node.freq < min.freq)
                        min = node;
                    node = node.next;
                }
                if (curr != min) {
                    swapNode(curr, min);
                }
                curr = curr.next;
            }
        }
    }
	//swaps the nodes when called.
    static void swapNode(Node a, Node b) {
        int tmp;
        tmp = a.data;
        a.data = b.data;
        b.data = tmp;
    }


    static void insertNodeHead(Node newNode) {

        newNode.next = head;
        if (head == null)
            tail = newNode;
        head = newNode;
    }

    // DO NOT change this method
    // delete the node at the head of the linked list
    static Node deleteHead() {
        Node curr;

        curr = head;
        if (curr != null) {
            head = head.next;
            if (head == null)
                tail = null;
        }
        return curr;
    }

    // DO NOT change this method
    // print the content of the list in two orders:
    // from head to tail
    static void printList() {
        Node curr;

        System.out.print("List: ");
        curr = head;
        while (curr != null) {
            System.out.print(curr.data + " ");
            curr = curr.next;
        }
        System.out.println();
    }


    // DO NOT change this method
    static void emptyList() {

        while (head != null)
            deleteHead();
    }

}

// You should not edit this class unless you want to implement
// a doubly linked list.
class Node {
    public int data;
    public Node next;
    public int freq;

    // constructor to create a new node with data equals to parameter i
    public Node (int i) {
        next = null;
        data = i;
        freq = 1;
    }
}