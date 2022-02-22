import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.io.*;

// These tests illustrate some basic properties of the methods you have been asked
// to implement. They are not exhaustive.
// For the final assessment there will also be cases where various combinations of
// the methods will be tested. You are strongly recommended to create some tests for
// yourself to ensure that your implementations satisfy all the specifications. 

public class AssignmentTests {

	@Test
	public void testValidate() {
		// Tests the validate functiom
       
       SkipList skList = new SkipList(createBasicTestList()); // This is a basic skip list created for
                                                              // these tests       
       assertTrue(skList.validate());
	}	
	@Test
	public void testValidateNotValid() {	       
	       SkipList skList = new SkipList(createBasicTestList());
	       
	       // Invalidate the list
	       
	       skList.top.next.down= null; // set the down field in the top node to null. This makes the list invalid.       
	       assertFalse(skList.validate());
		}

	@Test
	public void testInsert() {     
	       SkipList skList = new SkipList(createBasicTestList());
	       
	       // Easy insert: only insert on the bottom layer
	       
	       SLNode temp= skList.top;
	       
	       skList.top= temp.insert(temp, 1, 0); // insert value 1 on the bottom layer only
	       
	       // Check inserted on the bottom level
	       
	       temp= skList.top.down;
	       
	       assertEquals(temp.next.data, 3); // It has not been inserted on level 1
	       
	       temp= temp.down;
	       
	       assertEquals(temp.next.data, 1); // it has been inserted on level 0
	       
		}	
	@Test
	public void testInsertTwoLevels() {
	       
	       SkipList skList = new SkipList(createBasicTestList());
	       
	       // Insert on level 1 and on level 0
	       
	       SLNode temp= skList.top;
	       
	       skList.top= temp.insert(temp, 1, 1);
	       
	       // Check inserted on level 1 and on level 2
	       
	       temp= skList.top.down;
	       
	       assertEquals(temp.next.data, 1); // Check the data
	       assertEquals(temp.next.level,1); // Check the level
	       
	       assertEquals(temp.next.next.data, 3);// check the connections
	       assertEquals(temp.next.down.data, 1);// check the connections	       
		}	

	
	// 
	@Test
 	public void testSearchFirstExact() { 
 		SkipList skList = new SkipList(createBasicTestList());
 		
 		SLNode inputList= skList.top;
 		
 		SLNode first= inputList.searchFirstExact(inputList, 7);	  // search for 7	
 		
 		assertEquals(first.level, 2); // find it first on level 2
 		
 		first= inputList.searchFirstExact(inputList, 3); // search for 3
 		
 		assertEquals(first.level, 1); // find it first on level 1
 		
 		first= inputList.searchFirstExact(inputList, 5); // search for 5
 		
 		assertEquals(first.level, 0); // find it first on level 0
 	}

	
	@Test
	 	public void testFindLastLevel() { 
	 		SkipList skList = new SkipList(createBasicTestList());
	 		
	 		int last= skList.findLastLevel(2); // find last item on level 2
	 		
	 		assertEquals(last, 7); // it is 7
	 		
	 		last= skList.findLastLevel(1); // find last item on level 1
	 		assertEquals(last, 7); // it is 7
	 		
	 		last= skList.findLastLevel(0); // find last item on level 0
	 		assertEquals(last, 9);	  // it is 9	
	 		
	 	}
	 	
	 	
	@Test	
	 	public void testCountAllNodes() { 
	 		SkipList skList = new SkipList(createBasicTestList());
	 		
            assertEquals(skList.countAllNodes(), 10);
	 		
	 	}
	 	
	@Test
	 	public void testTraverseAndAdd() {
	 		SkipList skList = new SkipList(createBasicTestList());
	 		assertEquals(skList.traverseAndAdd(2),7); // sum of all non-negative nodes on level 2 is 7
	 		assertEquals(skList.traverseAndAdd(1),10);  // sum of all non-negative nodes on level 1 is 10
	 		assertEquals(skList.traverseAndAdd(0),24);  // sum of all non-negative nodes on level 0 is 24
	 	}

	@Test	
	 	public void testFindShortestPath() { 
	 		SkipList skList = new SkipList(createBasicTestList());
	 			 		
	 		int[] path= skList.findShortestPath(4);	 	// 4 is not in the list
	 		assertNull(path);           
	 		                                            
	 		
	 		
	 		int[] path2= skList.findShortestPath(9);	 	// take the shortcut along the top level 
	 		int[] answer2 = {-1, 7, 7, 7, 9};

	 			for(int i=0; i < 4; i++) {
	 					assertEquals(path2[i], answer2[i]);
	 				}
	 	}
	@Test 	
	 	public void testLengthShortest() { 
	 		SkipList skList = new SkipList(createBasicTestList());
	 		
	 		
	 		int  length= skList.lengthShortest(5);	 	// There are two such paths

			assertEquals(length, 5); // But both have the same length
	 		
			length= skList.lengthShortest(9);	 	// 

			assertEquals(length, 5);
	 	}
	 	
	@Test	
	 	public void testSubsequence() { 
	 		SkipList skList = new SkipList(createBasicTestList());
	 		
	 		//SLNode inputList= skList.top;
	 		
	 		int[] path= skList.subsequence(2, 8);	 	// list of data from nodes whose values lie between
	 		                                            // 2 and 8 
	 		int[] answer = {3, 5, 7};
	 		
	 		for(int i=0; i < 2; i++) {
			assertEquals(path[i], answer[i]);
	 		}
	 	}
	 
	@Test
	 	public void testRemove() { 
		SkipList skList = new SkipList(createBasicTestList()); // Make a valid list
		
		SLNode first= skList.top;
		
		SLNode removed= first.remove(first, 5); // Remove nodes with data value 5
		
		SLNode temp= removed.down.down;
		
		temp= temp.next;
		assertEquals(temp.data, 3); // Check that 5 is gone but the rest of the list remains
		temp= temp.next;
		assertEquals(temp.data, 7);
	 	
		
		skList = new SkipList(createBasicTestList()); // Renew the list

		
		first= skList.top;
	 	
		
    	removed= first.remove(first, 3); // remove 3 this time
		
		temp= removed.down;
		
		assertEquals(temp.next.data, 7);
		
		temp= temp.down;
		
		assertEquals(temp.next.data, 5);
}
	 	
	@Test	
	 	public void testSearchFirstAtLeast() { 
	 		
	 		SkipList skList = new SkipList(createBasicTestList());
	 		
	 		SLNode first= skList.top;
	 		SLNode found= first.searchFirstAtLeast(first, 6);
	 		
	 		assertEquals(found.data, 7);
	 		assertEquals(found.level, 2); // found on the second level
	 		
	 		found= first.searchFirstAtLeast(first, 20);
	 		
	 		assertNull(found); // not found at all
	 	}
	 	
	 	
	
	public SLNode createBasicTestList(){
		
		// This creates a basic test list with three levels:
		
		// Level 2: -1 (dummy),       7	
		// Level 1: -1 (dummy), 3,    7
		// Level 0: -1 (dummy), 3, 5, 7, 9

		// Note that in the final testing for the assessment, this list WILL NOT BE USED
		// It WILL be used when you submit for trial automarking.
		
		SLNode firstNode= new SLNode(-1);
		int i= 3;
		SLNode prev= firstNode;
		SLNode temp;
		
		SLNode nodeThree=null;
		SLNode nodeSeven=null;
		
		// bottom level
		while (i < 10) {
			temp= new SLNode(i);
			if (i==3) {nodeThree= temp;} // save pointers to nodes 3, 7 to make the next level
			if (i==7) {nodeSeven= temp;}
			prev.next= temp;
			prev= temp;
			i= i+2;
		}
		
		// level 1
		
		temp= new SLNode();
		temp.down= firstNode;
		firstNode= temp;
		firstNode.level= 1;
		prev= firstNode;
		prev.next= new SLNode(3);
		temp= prev.next;
		temp.level= 1;
		temp.down= nodeThree;
		prev= temp;
		
		prev.next= new SLNode(7);
		temp= prev.next;
		temp.level= 1;
		temp.down= nodeSeven; // save pointer to the newly inserted node 7 on level 1
		nodeSeven= temp;
		temp.next= null;
		
		//top level
		
		temp= new SLNode();
		temp.down= firstNode;
		firstNode= temp;
		firstNode.level= 2;
		prev= firstNode;
		prev.next= new SLNode(7);
		temp= prev.next;
		temp.level= 2;
		temp.down= nodeSeven;
		temp.next= null;
				
	 return firstNode;
		
	}	
}