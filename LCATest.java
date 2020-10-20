import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;




class LCATest {
	
    @Test
    public void testConstructor()
    {
      new LCA();
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
