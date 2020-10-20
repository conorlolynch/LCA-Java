import java.util.ArrayList;

class Node{
	public int key;
	public Node left;
	public Node right;
	
	public Node(int data) {
		if (data >= 0) {
			this.key = data;
			this.left = null;
			this.right = null;
		}
	}
}


class BT{
	public Node root;
	public int number_elements;
	
	public BT(int data) {
		if (data >= 0) {
			this.root = new Node(data);
		}
	}
	
	
	
	
	/** 
	 * 	Recursively called to add a value to a Binary Tree 
	**/
	
	private boolean addValue(Node r, int data) {
		boolean return_bool = false;
		
		
		if (r != null) {
			try {
				if (data < r.key) {
					// Now check to see if left sub tree exists
					if (r.left != null) {
						return_bool = addValue(r.left, data);
					}
					else {
						// We want to add this left sub tree
						r.left = new Node(data);
						this.number_elements++;
						return_bool = true;
					}
				}
				else if (data > r.key) {
					// Now check to see if right sub tree exists
					if (r.right != null) {
						return_bool = addValue(r.right, data);
					}
					else {
						// We want to add this left sub tree
						r.right = new Node(data);
						this.number_elements++;
						return_bool = true;
					}
				}
				else {
					// Don't add duplicates 
					return false;
				}
			}
			catch (Exception e) {
				return false;
			}
		}
		
		return return_bool;
	}
	
	
	
	/** 
	 * 	Put a node into the Binary Tree
	 */
	
	
	public boolean put(int value) {
		boolean return_bool = false;
		if (value >= 0) {
			try {
				// Check to see if a root node exists
				if (this.root != null) {
					return_bool = addValue(this.root, value);
				}
				else {
					this.root = new Node(value);
					this.number_elements++;
				}
				
			}
			catch (Exception e) {
				return false;
			}
		}
		return return_bool;
	}
}




public class LCA {
	

	public boolean findPath(Node root, int target, ArrayList<Integer> pathArr) {
		
		boolean found = false;
		
		if (root != null) {
			if (target >= 0) {
				try {
					if (target < root.key) {
						// Add this node to the path array
						pathArr.add(root.key);
						
						// Then move onto the next node
						found = findPath(root.left, target, pathArr);
					}
					else if (target > root.key) {
						// Add this node to the path array
						pathArr.add(root.key);
						
						// Then move onto the next node
						found = findPath(root.right, target, pathArr);
					}
					else {
						// We have arrived at the node we were looking for
						pathArr.add(root.key);
						return true;
					}
				}
				catch (Exception e) {
					found = false;
				}
			}
		}
		
		return found;
	}
	
	
	
	
	public int findLCA(BT tree, int nodeKey1, int nodeKey2) {
		
		if (tree != null) {
			// Create arrays to store paths to destination nodes
			ArrayList<Integer> path1 = new ArrayList<Integer>();
			ArrayList<Integer> path2 = new ArrayList<Integer>();
			
			
			// Find the nodes that connect root to node1 and node2
			boolean foundPath1 = findPath(tree.root, nodeKey1, path1);
			boolean foundPath2 = findPath(tree.root, nodeKey2, path2);
			
//			System.out.println(path1.toString());
//			System.out.println(path2.toString());
			
			
			// Check if no path was found to either of the nodes
			if (foundPath1 != true  || foundPath2 != true) {
				return -1;
			}
			
			
			// The LCA of the two nodes will be the first index in both path arrays that are different
			int index = 0;
			while (index < path1.size() && index < path2.size() ) {
				if (path1.get(index) != path2.get(index)) {
					break;
				}
				
				index++;
			}
			
			
			return path1.get(index-1);
		}
		
		return -1;
	}
	
	
	
	public static void main(String[] args) {
		BT bt = new BT(12);
		bt.put(13);
		
		System.out.println(bt.root.key);
	}
}
