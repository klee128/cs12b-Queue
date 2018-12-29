package que;

public class Queue implements QueueInterface{

	//Fields for stuff
	private Node head, tail; //reference to first Node in List
	private int numItems; //numm items in queue
	
	
	//to make a new node
	private class Node{
		Object item;
		Node next;
		
		Node(Object x){
			item = x;
			next = null;
		}
	}
	
	
	//constructor for Queue class
	public Queue() {
		head = null;
		tail = null;
		numItems = 0;
	}
	
	
	
	//-----------------------------------------------
	//this is all the things from Queue Interface
	
	public boolean isEmpty() {
		if (numItems == 0) {
			return true;
		}
		return false;
	}

	
	public int length() {
		return numItems;
	}

	
	public void enqueue(Object newItem) {
		Node N  = new Node(newItem);
		if(this.isEmpty() == true) {
			this.head = N;
			this.tail = N;
			N.next = null;
		}
		else {
			this.tail.next = N;
			this.tail = N;
		}
		numItems++;
			
	}

	
	public Object dequeue() throws QueueEmptyException {
		if (this.isEmpty() == true) {
			throw new QueueEmptyException("Queue Error: dequeue() called on empty queue.");
		}
		Object x = head.item;
		head = head.next;
		numItems--;
		return x;
	}

	
	public Object peek() throws QueueEmptyException {
		if (this.isEmpty() == true) {
			throw new QueueEmptyException("Queue Error; peek() called on empty queue.");
		}
		return head.item;
	}

	
	public void dequeueAll() throws QueueEmptyException {
		if (this.isEmpty() == true) {
			throw new QueueEmptyException("Queue Error; dequeueAll() called on empty queue.");
		}
		
		head = null;
		tail = null;
		numItems = 0;
		
	}

	public String toString() {
		
		String temp = "";
		Node tempN = this.head;
		for(int i = 0; i < this.length(); i++) {
			temp = temp + tempN.item.toString() + " ";
			tempN = tempN.next;
		}
		
		
		return temp;
	}

}
