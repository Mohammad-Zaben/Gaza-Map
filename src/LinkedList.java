
public class LinkedList {
	private Node front, back;
	private int size;

	public LinkedList() {
		front = back = null;
		size = 0;
	}

	public void addFirst(Object element) {
		Node newNode = new Node(element);
		if (size == 0) {
			front = back = newNode;
		} else {
			newNode.next = front;
			front = newNode;
		}
		size++;
	}

	public void addLast(Object element) {
		Node newNode = new Node(element);
		if (size == 0) {
			front = back = newNode;
		} else {
			back.next = newNode;
			back = newNode;
		}
		size++;
	}

	public void add(int index, Object element) {
		if (index == 0)
			addFirst(element);
		else if (index >= size)
			addLast(element);
		else {
			Node newNode = new Node(element);
			Node current = front;
			for (int i = 0; i < index - 1; i++)
				current = current.next;
			newNode.next = current.next;
			current.next = newNode;
			size++;
		}
	}

	public void add(Object element) {
		add(size, element);
	}

	public Object getFirst() {
		if (size == 0)
			return null;
		else
			return front.element;
	}

	public Object getLast() {
		if (size == 0)
			return null;
		else
			return back.element;
	}

	public Object get(int index) {
		if (size == 0)
			return null; // empty list
		else if (index == 0)
			return getFirst(); // first element
		else if (index == size - 1)
			return getLast(); // last element
		else if (index > 0 && index < size - 1) {
			Node current = front;
			for (int i = 0; i < index; i++)
				current = current.next;
			return current.element;
		} else
			return null; // out of boundary
	}

	public boolean removeFirst() {
		if (size == 0) {
			return false;
		} else if (size == 1) {
			front = back = null;
		} else {
			front = front.next;
		}
		size--;
		return true;

	}

	public boolean removeLast() {
		Node curent = front;
		if (size == 0) {
			return false;
		} else if (size == 1) {
			front = back = null;
		} else {
			for (int i = 0; i < size - 2; i++) {
				curent = curent.next;
			}
			curent.next = null;
			back = curent;
		}
		size--;
		return true;
	}

	public boolean remove(int index) {
		if (size == 0) {
			return false;
		} else if (index == 0) {
			removeFirst();
		} else if (index == size - 1) {
			removeLast();
		} else if (index > 0 && index < size - 1) {
			Node curent = front;
			for (int i = 0; i < index - 1; i++)
				curent = curent.next;
			curent.next = curent.next.next;
			;
			size--;
			return true;
		}
		return false;
	}

	public void clear() {
		front = back = null;
		size = 0;
	}

	public Object returnremovefirst() {
		Node curent = front;
		removeFirst();
		return curent;
	}

	public Object returnfirst() {
		Node curent = front;
		return curent;
	}

	public Object returnremove(int index) {
		if (size == 0) {
			return null;
		} else if (index == 0) {
			Node curent = front;
			removeFirst();
			return curent;
		} else if (index == size - 1) {
			Node curent = back;
			removeLast();
			return curent;
		} else if (index > 0 && index < size - 1) {
			Node curent1 = front, curent2;
			for (int i = 0; i < index - 1; i++)
				curent1 = curent1.next;
			curent2 = curent1.next;
			curent1.next = curent1.next.next;
			;
			size--;
			return curent2;
		}
		return null;
	}

	public int find(Object o) {
		for (int i = 0; i < size; i++) {
			if (get(i) == o) {
				return i;
			}
		}
		return -1;
	}

	public boolean removeWhitObject(Object object) {
		if (size == 0) {
			return false;
		} else {
			remove(find(object));
			return false;
		}

	}

	public void print() {
		Node curent = front;
		while (curent != null) {
			System.out.println(curent.element);
			curent = curent.next;
		}

	}

	public String toString() {
		String s = "";
		Node curent = front;
		while (curent != null) {
			s +=","+ curent.element;
			curent = curent.next;
		}
		return s;
	}

	public int size() {
		return size;
	}

}
