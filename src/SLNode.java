import java.util.*;

class SLNode {

	int data; // Contents of the node
	int level; // the level of this node
	SLNode next; // points to the next node on this level;
	SLNode down; // points to the node containing the duplicated data on the level below

	SLNode() {
		data = -1;
		level = 0;
		next = null;
		down = null; // makes a dummy node on level 0
	}

	SLNode(int newData) { // makes a node containing data on level 0
		data = newData;
		level = 0;
		next = null;
		down = null;
	}

	SLNode(SLNode below) { // constructor to duplicate node below
		if (below != null) {
			data = below.data; // duplicate data
			level = below.level + 1; // level is one up
			next = null;
			down = below; // points to the node being duplicated
		}
	}

	SLNode searchFirstExact(SLNode sList, int key) { // ToDo - P level
		// Pre : sList is valid
		// Post : returns the address of the node with the *highest* level
		// in sList whose data field matches the search term.
		SLNode temp;
		SLNode pointer = sList;

		SkipList b = new SkipList(sList);
		if (b.validate() != true) {
			return null;
		}

		for (int i = sList.level; i >= 0; i--) {
			boolean end = false;
			temp = pointer;

			while (end != true) {

				if (temp.data == key) {

					return temp;

				} else {
					if (temp.next != null) {
						temp = temp.next;
					} else
						end = true;
				}
			}
			if (pointer.down != null) {
				pointer = pointer.down;
			}

		}

		return null;

	}

	SLNode searchFirstAtLeast(SLNode sList, int key) { // ToDo -- D/HD level
		// Pre : sList is valid
		// Post : returns the address of the node on the *highest* level in sList
		// so that all items reachable in the list from there have data value *at least*
		// the value of key, and any non-reachable item has data value strictly
		// less than the key.
		// Returns null if there is no such node.

		SLNode list = sList;
		SLNode pointer = list;
		SLNode temp;

		SkipList b = new SkipList(sList);
		if (b.validate() != true) {
			return null;
		}

		for (int i = list.level; i >= 0; i--) {
			temp = pointer;

			while (temp.next != null) {
				if (temp.next.data >= key) {
					return temp.next;

				}

				if (temp.next != null) {
					temp = temp.next;
				}

			}

			if (pointer.down != null) {
				pointer = pointer.down;
			}
		}
		return null;
	}

	SLNode insert(SLNode sList, int toBeInserted, int topLevel) { // ToDo -- P level
		// Pre : sList is valid, and topLevel is no more than sList.level+1
		// Post : returns the first node of the list with the value toBeInserted
		// inserted in sList
		// at level topLevel and on all levels below.
		// The returned list should be valid.


		// Details for insertion:
		// (1) if topLevel == sList.level+1, create a new level and insert
		// the node in this level
		// (2) For all other levels (below topLevel), find the place to insert: it must
		// be in between the
		// node that is less than, and before the node that is greater than
		// toBeInserted'
		// Do not insert if toBeInserted is already in the list.
		// (3) add the down links as appropriate..
		return null;
	}

	SLNode remove(SLNode sList, int toBeRemoved) { // ToDo D/HD Level
		// Pre: Assume that each data value occurs exactly once on each level
		// (individually).
		// sList is valid
		// Post : returns the first node of the skip list with any nodes containing
		// values equal to
		// toBeRemoved removed (i.e. on every level)
		// The returned list should contain all other nodes and must be valid.
		SLNode list = sList;
		SLNode pointer = list;
		SLNode temp;

		SkipList b = new SkipList(sList);
		if (b.validate() != true) {
			return null;
		}

		for (int i = list.level; i >= 0; i--) {
			temp = pointer;

			while (temp.next != null) {

				if (temp.next.data == toBeRemoved) {

					if (temp.next.next != null) {
						temp.next = temp.next.next;
					} else {
						temp.next = null;

					}
				}
				if (temp.next != null) {
					temp = temp.next;
				}
			}

			if (pointer.down != null) {
				pointer = pointer.down;
			}
		}
		return list;
	}
}

class SkipList {
	SLNode top; // The top node in a Skip List
	// class invariant:
	// This defines a valid Skip List structure for SkipList;
	// The first node on every level has data field (set to -1);
	// All other data fields have value at least zero;
	// All nodes on the same level form an (increasing) sorted linked list;
	// All nodes are reachable from top, by following links (next or down);
	// Any node on a non-zero level has a path to the zero layer following down
	// links, going through
	// nodes with strictly decreasing levels, but all with the same data;
	// The nodes on the same level form a subset of node (data values) on the level
	// below.

	SkipList(SLNode sList) { // Constructor
		// Pre: sList is valid
		top = sList;
	}

	int findLastLevel(int lvl) { // ToDo -- P level
		// POST: returns the value in the last node on level lvl

		SLNode temp = top;
		int num = -1;

		int val = (lvl - 0) * (0 - temp.level) / (temp.level - 0) + temp.level;

		for (int i = 0; i < val; i++) {
			temp = temp.down;
		}

		while (temp.next != null) {

			temp = temp.next;
			num = temp.data;

		}

		return num;
	}

	int[] findShortestPath(int key) { // ToDo -- CR level
		// POST: Returns the array of node values (including dummy values) on any
		// shortest path from
		// the current top node to any node containing data value key on the bottom
		// level
		// Return null if no node's data value matches key.

		// Note: in general there could be more than one shortest path. In the final
		// testing this case will not arise.
		SLNode temp = top;
		int count = 1;
		int last = 0;
		int arrSize = lengthShortest(key);
		int arr[] = new int[arrSize];
		int arrPointer = 0;

		if (arrSize == 0) {
			return null;
		}

		while (temp != null && last != 2) {

			if (temp.data == key) {
				int downCounter = 0;

				while (downCounter != 2) {
					if (arrPointer < arrSize) {
						arr[arrPointer] = temp.data;
						arrPointer++;
					}
					if (temp.down != null) {
						temp = temp.down;
						count++;
					}

					if (temp.down == null) {
						downCounter++;
					}

				}
				for (int i = 0; i < arr.length; i++) {
					// System.out.print(arr[i]);
				}
				return arr;
			}

			if (temp.next != null) {
				if (temp.next.data <= key) {
					arr[arrPointer] = temp.data;
					arrPointer++;
					count++;
					temp = temp.next;
				} else if (temp.next.data > key && temp.down == null) {
					return null;

				} else {
					if (temp.down != null) {
						arr[arrPointer] = temp.data;
						arrPointer++;
						temp = temp.down;
						count++;
					}

				}

			}

			else {
				if (temp.down != null) {
				}
				arr[arrPointer] = temp.data;
				arrPointer++;
				temp = temp.down;
				count++;
			}

			if (temp.next == null && temp.down == null) {
				last++;
			}

		}

		// System.out.println(0);
		return null;
	}

	int lengthShortest(int key) { // ToDo -- CR level
		// POST: Returns the length of the shortest path from
		// the current top node to any node containing data value key on the bottom
		// level.
		// Returns 0 if there is no such node whose data value matches key

		SLNode temp = top;
		int count = 1;
		int last = 0;

		while (temp != null && last != 2) {

			if (temp.data == key) {

				while (temp.down != null) {
					temp = temp.down;
					count++;
				}
				return count;
			}

			if (temp.next != null) {
				if (temp.next.data <= key) {

					count++;
					temp = temp.next;
				} else if (temp.next.data > key && temp.down == null) {
					return 0;

				} else {
					if (temp.down != null) {
						temp = temp.down;
						count++;
					}

				}

			}

			if (temp.down != null) {
				temp = temp.down;
				count++;
			}

			if (temp.next == null && temp.down == null) {
				last++;
			}

		}

		return 0;

	}

	boolean validate() { // ToDo -- CR level
		// POST: Returns true if the list is valid (i.e. satisfies the class invariant).
		// Otherwise returns false.

		SLNode temp;
		SLNode pointer;
		/*
		 * 1. Starting from any node a in the skip list, all nodes b, c, d, etc.
		 * reachable by following the next field, i.e. b= a.next. c= b.next, d = c.next
		 * have the same level number, but have strictly increasing data values.
		 * 
		 * 2. Starting from any node a in the skip list, all nodes b, c, d, etc.
		 * reachable by following the down field, i.e. b= a.down. c= b.down, d = c.down
		 * have the same data value, but strictly decreasing level numbers, and the
		 * level numbers decrease in steps of 1.
		 * 
		 * 3. Any node which is the last node on a particular level has its next field
		 * set to null.
		 * 
		 * 4. Any node with its level field set to 0 has its down field set to null.
		 * 
		 * 5. Any node with its level field set to a value at least 1 has its down field
		 * set to a non-null node;
		 * 
		 * 6. The first node on any level is a “dummy” node with its data field set to
		 * -1. Here we use -1 as a special value to indicate that it is the first node
		 * on a level. All other nodes have their data fields set to a value at least 0.
		 */

		pointer = top;

		for (int i = top.level; i >= 0; i--) {
			temp = pointer;

			while (temp.next != null) {

				if (temp.data < temp.next.data && temp.level == temp.next.level) {
					if (temp.next != null) {
						temp = temp.next;
					}

				} else {
					return false;
				}

			}
			if (pointer.down != null) {
				pointer = pointer.down;
			}
		}

		
			
		pointer = top;

		for (int i = top.level; i > 0; i--) {
			boolean end = false;
			temp = pointer;

			while (end != true) {
				if (temp.down != null) {
					if (temp.next != null) {
						temp = temp.next;
					} else
						end = true;
				} else {
					return false;
				}
			}

			if (pointer.down != null) {
				pointer = pointer.down;

			}

		}

		pointer = top;
		temp = pointer;

		while (temp.down != null) {
			if (temp.data != temp.down.data) {
				return false;

			} else {
				temp = temp.down;
			}

		}

		return true;
	}

	int traverseAndAdd(int selectedLevel) { // ToDo -- P level
		// POST: Returns the sum of the non-negative-valued nodes on level equal to
		// selectedLevel
		// Return 0 if selected level does not appear in the list
		SLNode temp = top;
		int sum = 0;

		int val = (selectedLevel - 0) * (0 - temp.level) / (temp.level - 0) + temp.level;

		if (val > temp.level || val < 0) {
			return 0;
		}

		for (int i = 0; i < val; i++) {
			temp = temp.down;
		}

		while (temp.next != null) {

			temp = temp.next;
			if (temp.data >= 0) {
				sum += temp.data;
			}

		}
		return sum;
	}

	int[] subsequence(int start, int end) { // ToDo D/HD level
		// POST: Returns the (increasing) sorted array of all nodes drawn from (bottom
		// level of original list)
		// such that all the nodes have data at least value start and at most value end

		SLNode pointer = top;
		SLNode temp;
		ArrayList<Integer> arr = new ArrayList<Integer>();

		while (pointer.down != null) {
			pointer = pointer.down;
		}

		temp = pointer;

		while (temp.next != null) {
			if (temp.data < start && temp.next.data >= start) {
				while (temp.next.data <= end) {
					arr.add(temp.next.data);

					if (temp.next != null) {
						temp = temp.next;
					}
				}
			}
			if (temp.next != null) {
				temp = temp.next;
			}
		}

		int[] array = arr.stream().filter(i -> i != null).mapToInt(i -> i).toArray();

		return array;
	}

	int countAllNodes() { // ToDo -- P level
		// POST: Returns the total number of SLNodes in the current Skip List
		// (The count includes all "dummy" nodes at the start of each level.)

		int count = 0;

		SLNode temp;
		SLNode pointer = top;

		for (int i = top.level; i >= 0; i--) {

			temp = pointer;
			count++;

			while (temp.next != null) {
				temp = temp.next;
				count++;
			}

			if (pointer.down != null) {
				pointer = pointer.down;

			}
		}

		return count;
	}

}
