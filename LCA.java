import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;



class DAG{
	public Map<String, Integer> node_map;
	public ArrayList<HashSet<String>> parents;
	public ArrayList<HashSet<String>> children;
	public ArrayList<Integer> depth;
	public int currentIndex = -1;
	
	public DAG() {
		this.node_map = new HashMap<String, Integer>();
		this.parents = new ArrayList<HashSet<String>>();
		this.children = new ArrayList<HashSet<String>>();
		this.depth = new ArrayList<Integer>();
	}
	
	public boolean insertInto(String node, String parent, String child) {
		
		// First check to see if the node has already been inserted into the hashmap
		if (!this.node_map.containsKey(node)) {
			
			// Allocate the index that will be used for contents of this node
			this.currentIndex++;
			
			
			// Then add the node to the node map
			this.node_map.put(node, this.currentIndex);
			
			
			// Create a hashset for storing parents and add it to the correct index in the parents array
			HashSet<String> pars = new HashSet<String>();
			if (parent != null) {
				pars.add(parent);
				this.depth.add(this.currentIndex, 1 + this.depth.get(this.node_map.get(parent)));
			}
			else {
				this.depth.add(this.currentIndex, 0);;
			}
			this.parents.add(this.currentIndex, pars);
			
			
			// Do similar for the children array
			HashSet<String> childs = new HashSet<String>();
			if (child != null) { childs.add(child);}
			this.children.add(this.currentIndex, childs);
			
			
			return true;
		}
		
		return false;
	}
	
	
	
	public void addEdge(String parent, String child) {
		
		// See if we are able to insert the parent node into our data structures
		if (!insertInto(parent, null, child)) {
			// Parent exists so add child to its children set
			int index = this.node_map.get(parent);
			this.children.get(index).add(child);
		}
		
		
		// See if we are able to insert the child into our data structures
		if (!insertInto(child, parent, null)) {
			// The child exists so add the parent to its parents set
			int index = this.node_map.get(child);
			this.parents.get(index).add(parent);
		}
		
	}
	
	
	
	// Recursively get all the predecessors of a given node
	public void recursiveSearch(String node, HashSet<String> nodeSet) {
		nodeSet.add(node);
		// Loop through all the parents of this node and add them to the node set
		for (String parentNode: this.parents.get(this.node_map.get(node))) {
			nodeSet.add(parentNode);
			recursiveSearch(parentNode, nodeSet);
		}
	}
	
	
	
	// Find the LCA of two nodes contained in the DAG graph
	public String findLCA(String firstNode, String secondNode) {
		HashSet<String> firstPath = new HashSet<String>();
		HashSet<String> secondPath = new HashSet<String>();
		
		// Check to make sure first target and second target nodes are in the graph
		if (!this.node_map.containsKey(firstNode) || !this.node_map.containsKey(secondNode)) {
			return null;
		}
		else {
			
			// Get all the nodes that are predecessors of each target node
			recursiveSearch(firstNode, firstPath);
			recursiveSearch(secondNode, secondPath);
			
			
			// Only keep the path nodes which are common to both target nodes
			firstPath.retainAll(secondPath);
			
			
			// Finally loop through all the remaining nodes and return the common node with the largest depth
			int largest = -1;
			String returnNode = null;
			for (String node: firstPath) {
				int depth = this.depth.get(this.node_map.get(node));
				if (depth > largest) {
					largest = depth;
					returnNode = node;
				}
			}
			
			return returnNode;
		}
	}
	
	
	
	public void displayTable() {
		System.out.println("Nodes: "+this.node_map.keySet());
		System.out.println("Parents: "+this.parents.toString());
		System.out.println("Children: "+this.children.toString());
		System.out.println("Depth: "+this.depth.toString());
	}
	
}



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

}
