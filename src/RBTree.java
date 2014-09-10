import java.util.ArrayList;


public class RBTree {

	class Node{
		int k;
		Node left;
		Node right;
		public Node(int k) {
			this.k=k;left=right=null;
		}
		public String toString() {
			return ""+k;
		}
	}
	
	public RBTree() {}
	
	public Node insert(Node r,int k){
		if(r==null) return new Node(k);
		if(k<r.k){
			r.left=insert(r.left, k);
			return rr(r);
		}else{
			r.right=insert(r.right, k);
			return rl(r);
		}
	}
	
	public Node rr(Node r){
		Node nl=r.left;
		r.left=nl.right;
		nl.right=r;
		return nl;
	}
	
	public Node rl(Node r){
		Node nr=r.right;
		r.right=nr.left;
		nr.left=r;
		return nr;
	}
	
	public void visit(Node r){
		if(r==null) return;
		System.out.print(r.k+" ");
		visit(r.left);
		visit(r.right);
	}
	
	public void visitInOrder(Node r){
		if(r==null) return;
		visitInOrder(r.left);
		System.out.print(r.k+" ");
		visitInOrder(r.right);
	}
	
	public void visitLevelOrder(Node r){
		ArrayList<Node> list = new ArrayList<Node>();
		ArrayList<Node> nextlist = new ArrayList<Node>();
		ArrayList<Node> tmplist = null;
		list.ensureCapacity(100);
		nextlist.ensureCapacity(100);
		list.add(r);
		while(!list.isEmpty()){
			Node x = list.remove(0);
			if(x!=null) System.out.print(x.k+" ");
			else System.out.print("* ");
			if(x!=null){
				nextlist.add(x.left);
				nextlist.add(x.right);
			}
			if(list.isEmpty()){
				System.out.println("");
				tmplist=nextlist; nextlist=list; list=tmplist;
			}
		}
	}
	
	public void run(){
		ArrayList<Node> actualList = new ArrayList<Node>();
		Node root=null;
		for(int i=0;i<1000;i++){
			int k = (int)(Math.random()*1000);
			root=insert(root, k);
			actualList.add(new Node(k));
		}
		System.out.println("actual : "+actualList);
		visit(root);System.out.println("[Rlr]");
		visitInOrder(root);System.out.println("[lRr]");
		System.out.println("LevelOrder");
		visitLevelOrder(root);
	}
	
	public static void main(String[] args) {
		RBTree t = new RBTree();
		t.run();
	}
}
