package que;


public class QueueTest {

	
	public static void main(String[] args) {
		Queue A = new Queue();
		A.enqueue(1); A.enqueue(5); A.enqueue(9); A.enqueue(7); A.enqueue(8);
		System.out.println(A);
		System.out.println(A.peek());
		A.dequeue(); A.dequeue(); A.dequeue();
		System.out.println(A.peek());
		System.out.println(A);
		Queue B = new Queue();
		System.out.println(A.isEmpty());
		System.out.println(B.isEmpty());
		B.enqueue(7); B.enqueue(8);
		System.out.println(A.equals(B));
		A.enqueue(12);
		B.enqueue(13);
		System.out.println(A.equals(B));
		A.dequeueAll();
		System.out.println(A);
		System.out.println(A.isEmpty());
	}
}
