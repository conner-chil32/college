/**
 *
 * @author Conner Childers
 * CSC 130 (9/21/2023)
 */

//  This class contains the tests, its all stored in the same file for convience
//  sake
public class LinkedlistAssignment1 { 
    public static void main(String[] args) {
       // Testing Stack
       cStack stack = new cStack(); // Creating a new instance of my cStack class
       stack.Push("Power");
       stack.Push("Great");
       stack.Push("With");
       System.out.println("Is Stack Empty?: " + stack.isEmpty());
       System.out.println(stack.Pop());
       System.out.println(stack.Pop());
       System.out.println(stack.Pop());
       System.out.println("Is Stack Empty?: " + stack.isEmpty());
       
       System.out.println("\n");
       
       //Testing Queue
       cQueue queue = new cQueue();	// Creating a new instance of my cQueue class
       queue.Enqueue("Comes");
       queue.Enqueue("Great");
       queue.Enqueue("Responsibility");
       System.out.println("Is Queue Empty?: " + queue.isEmpty());
       System.out.println(queue.Dequeue());
       System.out.println(queue.Dequeue());
       System.out.println(queue.Dequeue());
       System.out.println("Is Queue Empty?: " + queue.isEmpty());
    }
    
}

class cStack {
    LinkedList lk;
    
    public cStack() {
        lk = new LinkedList();
    }
    
    public String About() {
        return "This is Conner Childers' stack class";
    }
    
    public void Push(String item) {
        lk.AddHead(item);
    }
    
    public String Pop() {
        return lk.RemoveHead();
    }
    
    public Boolean isEmpty() {
        return lk.isEmpty();
    }
}

class cQueue {
    LinkedList lk;
    public cQueue() {
        lk = new LinkedList();
    }
    
    public String About() {
        return "This is Conner Childers' queue class";
    }
    
    public void Enqueue(String item) {
        lk.AddTail(item);
    }
    
    public String Dequeue() {
        return lk.RemoveHead();
    }
    
    public Boolean isEmpty() {
        return lk.isEmpty();
    }
}

class Node {

    public Node(Node next, String value) {

        this.next = next;
        this.value = value;
    }
    
    public void PrintList() {
        System.out.println(value);
        if(next != null) { // if the next node isnt null it recurses to the next node
            next.PrintList();
        }
    }
    
    Node next;
    String value;
}

class LinkedList {
    private Node head = null;
    private Node tail = null;
    
    public String About() {
        return "Conner Childers' Linked List Class";
    }
    
    public Boolean isEmpty() {
        return head == null; // if the head is null, the list is empty
    }
    
    public void AddHead(String value) {
        Node node = new Node(head, value); // creating a new node which is
                                           // is connected to the old head
        head = node; 			// reassigning the new head
        if(head.next == null) { // if this is first node, the tail and head are
                                // equal
            tail = head;
        }
    }
    
    public void AddTail(String value) {
        Node node = new Node(null, value); // creating a new tail node
        if(head == null) {  // if the list is empty, the head and tail are set
                            // to the new node, this prevent a pointer error
            head = node;
            tail = node;
        }
        tail.next = node;   // connecting the old tail to the new tail
        tail = node;        // setting the new tail
    }
    
    public String RemoveHead() {
        Node node = head;   // storing the values of the old head
        head = head.next;   // disconnecting the old head, and assigning the
                            // new one
        return node.value;
    }
    
    public void PrintList(){
        if(head != null) {
            head.PrintList();
        }
    }
}
