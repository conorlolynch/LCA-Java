import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;




class LCATest {
	
    @Test
    public void testConstructor()
    {
      new LCA();
      new DAG();
    }
    
    
    @Test
    void testDAG() {
    	
    	// Test creating a DAG object with no nodes 
    	DAG dag = new DAG();
    	assertEquals("Test creating a DAG object with no nodes",0, dag.node_map.size());
    	assertEquals("Test creating a DAG object with no nodes",0, dag.children.size());
    	assertEquals("Test creating a DAG object with no nodes",0, dag.parents.size());
    	assertEquals("Test creating a DAG object with no nodes",0, dag.depth.size());
    	
    	
    	// Test finding LCA of empty DAG
    	assertEquals("Test finding LCA of empty DAG",null, dag.findLCA("A","A"));
    	
    	
    	// Test finding LCA of DAG with one node
    	dag.insertInto("A", null, null);
    	assertEquals("Test finding LCA of DAG with one element where target nodes are the root ","A", dag.findLCA("A","A"));
    	assertEquals("Test finding LCA of DAG with one element where one node isnt in DAG ",null, dag.findLCA("A","Z"));
    	assertEquals("Test finding LCA of DAG with one element where one node isnt in DAG ",null, dag.findLCA("Z","A"));
    	
    	
    	// Test finding LCA of DAG with two elements
    	dag = new DAG();
    	dag.addEdge("A", "B");
    	assertEquals("Test finding LCA of DAG with two elements","A", dag.findLCA("A","B"));
    	assertEquals("Test finding LCA of DAG with two elements","B", dag.findLCA("B","B"));
    	
    	
    	// Test finding LCA of DAG with multiple elements
    	dag.addEdge("A", "C");
    	dag.addEdge("A", "D");
    	dag.addEdge("B", "G");
    	dag.addEdge("B", "E");
    	dag.addEdge("C", "E");
    	dag.addEdge("C", "F");
    	dag.addEdge("D", "F");
    	dag.addEdge("E", "G");
    	dag.addEdge("E", "H");
    	dag.addEdge("F", "H");
    	
    	assertEquals("LCA(B,C) = A","A", dag.findLCA("B","C"));
    	assertEquals("LCA(B,D) = A","A", dag.findLCA("B","D"));
    	assertEquals("LCA(C,D) = A","A", dag.findLCA("C","D"));
    	assertEquals("LCA(B,E) = B","B", dag.findLCA("B","E"));
    	assertEquals("LCA(C,E) = C","C", dag.findLCA("C","E"));
    	assertEquals("LCA(C,F) = C","C", dag.findLCA("C","F"));
    	assertEquals("LCA(E,D) = A","A", dag.findLCA("E","D"));
    	assertEquals("LCA(G,H) = E","E", dag.findLCA("G","H"));
    	assertEquals("LCA(G,F) = C","C", dag.findLCA("G","F"));
    	assertEquals("LCA(F,B) = A","A", dag.findLCA("F","B"));
    	assertEquals("LCA(H,C) = C","C", dag.findLCA("H","C"));
    	
    	
    	// Test finding LCA of DAG with unusual structure 
    	dag = new DAG();
    	dag.addEdge("A", "B");
    	dag.addEdge("B", "C");
    	dag.addEdge("C", "D");
    	dag.addEdge("D", "E");
    	
    	assertEquals("LCA(A,B) = A","A", dag.findLCA("A","B"));
    	assertEquals("LCA(C,B) = B","B", dag.findLCA("C","B"));
    	assertEquals("LCA(E,E) = E","E", dag.findLCA("E","E"));
    	assertEquals("LCA(A,E) = A","A", dag.findLCA("A","E"));
    	

    }

    
    
	@Test

	void testFindPath() {
		// findPath(Node root, int target, ArrayList<Integer> pathArr)
		LCA lca = new LCA();
		
		// Test passing Null root
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		assertEquals( "Testing findPath with null root", false, lca.findPath(null, 2, tmp) );
		
		// Test passing negative target 
		BT bt = new BT(8);
		tmp.clear();
		assertEquals( "Testing findPath with negative key as target", false, lca.findPath(bt.root, -1, tmp) );
		
		// Test passing null as pathArr
		tmp.clear();
		assertEquals( "Testing findPath with null value as pathArr", false, lca.findPath(bt.root, 10, null) );
		
		// Test on BT with one element
		tmp.clear();
		assertEquals( "Testing findPath on BT with one element", true, lca.findPath(bt.root, 8, tmp) );
		
		// Test on BT with multiple elements
		bt.put(9);
		bt.put(10);
		bt.put(1);
		bt.put(4);
		bt.put(3);
		bt.put(5);
		bt.put(2);
		bt.put(7);
		bt.put(6);
		
		tmp.clear();
		assertEquals( "Testing findPath for node with key 4 in BT", true, lca.findPath(bt.root, 4, tmp) );
		
		tmp.clear();
		assertEquals( "Testing findPath for node with key 8 in BT", true, lca.findPath(bt.root, 8, tmp) );
		
		tmp.clear();
		assertEquals( "Testing findPath for node with key 9 in BT", true, lca.findPath(bt.root, 9, tmp) );
		
		
	}

	
	
	@Test
	void testFindLCA() {
		
		LCA lca = new LCA();
		
		
		// Test on empty binary tree
		assertEquals( "Testing on empty binary tree", -1, lca.findLCA(null, 1, 2) );
		
		
		// test on binary tree with 1 element
		BT bt = new BT(1);
		assertEquals( "Testing on binary tree with one element with one element in and one element not in BT", -1, lca.findLCA(bt, 1, 2) );
		assertEquals( "Testing on binary tree with one element where destination nodes is the root", 1, lca.findLCA(bt, 1, 1) );
		assertEquals( "Testing on binary tree with one element where first element has negative key", -1, lca.findLCA(bt, -1, 1) );
		assertEquals( "Testing on binary tree with one element where second element has negative key", -1, lca.findLCA(bt, 1, -1) );
		
		
		// Test on binary tree with root and left child
		bt.put(0);
		assertEquals( "Testing on binary tree with root and left child and both elements in BT", 1, lca.findLCA(bt, 1, 0) );
		assertEquals( "Testing on binary tree with root and left child but one element not in BT", -1, lca.findLCA(bt, 1, 2) );

		
		// Test on binary tree with root and right child
		BT bt2 = new BT(8);
		bt2.put(9);
		assertEquals( "Testing on binary tree with root and right child and both elements in BT", 8, lca.findLCA(bt2, 8, 9) );
		assertEquals( "Testing on binary tree with root and right child but one element not in BT", -1, lca.findLCA(bt2, 8, 20) );
		
		
		
		// Test for regular correct inputs
		/*       8
        		/ \
       		   1   9
        		\   \
         		 4   10
        		/ \
       		   3   5
      		  /     \
     		 2       7
            		/
           		   6
		 */
		
		bt2.put(10);
		bt2.put(1);
		bt2.put(4);
		bt2.put(3);
		bt2.put(5);
		bt2.put(2);
		bt2.put(7);
		bt2.put(6);
		
		assertEquals( "LCA of (1,9) should be 8", 8, lca.findLCA(bt2, 1, 9) );
		assertEquals( "LCA of (1,10) should be 8", 8, lca.findLCA(bt2, 1, 10) );
		assertEquals( "LCA of (1,4) should be 1", 1, lca.findLCA(bt2, 1, 4) );
		assertEquals( "LCA of (3,5) should be 4", 4, lca.findLCA(bt2, 3, 5) );
		assertEquals( "LCA of (3,6) should be 4", 4, lca.findLCA(bt2, 3, 6) );
		assertEquals( "LCA of (6,2) should be 4", 4, lca.findLCA(bt2, 6, 2) );
		assertEquals( "LCA of (10,9) should be 9", 9, lca.findLCA(bt2, 10, 9) );
		assertEquals( "LCA of (8,6) should be 8", 8, lca.findLCA(bt2, 8, 6) );
		
		
		// Test for nodes not in tree
		assertEquals( "LCA of (8,200) should be -1", -1, lca.findLCA(bt2, 8, 200) );
		assertEquals( "LCA of (0,8) should be -1", -1, lca.findLCA(bt2, 0, 8) );
		
		
		// Test on unusual structure of trees
		BT bt3 = new BT(10);
		bt3.put(9);
		bt3.put(8);
		bt3.put(7);
		bt3.put(6);
		bt3.put(5);
		bt3.put(4);
		
		assertEquals( "LCA of (10,9) should be 10", 10, lca.findLCA(bt3, 10, 9) );
		assertEquals( "LCA of (9,8) should be 9", 9, lca.findLCA(bt3, 9, 8) );
		assertEquals( "LCA of (10,4) should be 10", 10, lca.findLCA(bt3, 10, 4) );
		assertEquals( "LCA of (10,4) should be 10", 10, lca.findLCA(bt3, 10, 4) );
		assertEquals( "LCA of (4,4) should be 4", 4, lca.findLCA(bt3, 4, 4) );
		
		
		
		BT bt4 = new BT(10);
		bt4.put(11);
		bt4.put(12);
		bt4.put(13);
		bt4.put(14);
		bt4.put(15);
		bt4.put(16);
		
		assertEquals( "LCA of (10,11) should be 10", 10, lca.findLCA(bt4, 10, 11) );
		assertEquals( "LCA of (11,12) should be 11", 11, lca.findLCA(bt4, 11, 12) );
		assertEquals( "LCA of (15,14) should be 14", 14, lca.findLCA(bt4, 15, 14) );
		assertEquals( "LCA of (15,16) should be 15", 15, lca.findLCA(bt4, 15, 16) );
		assertEquals( "LCA of (16,16) should be 16", 16, lca.findLCA(bt4, 16, 16) );
		
		
	}
	
	

}
