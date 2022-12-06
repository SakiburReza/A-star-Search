/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;


public class Main {


	static SearchNode a_star_search(Board board, int method){
		System.out.println("Begins A*");
		SearchNode goal;
		PriorityQueue<SearchNode> openList=new PriorityQueue<>(new BoardComparator());
		Set<SearchNode> closeList=new HashSet<>();

		SearchNode startNode=new SearchNode(board,0,null);
		openList.add(startNode);

		while(!openList.isEmpty()){
			SearchNode current=openList.poll();
			closeList.add(current);
			if(current.board.isGoal()) {
				goal=current;
				System.out.println("Goal found\n");
				System.out.println("Explored Node : "+(closeList.size()+openList.size()));
				System.out.println("Expanded Node : "+closeList.size());
				return goal;
			}
			List<Board> successors=current.board.successors();
			for(Board child:successors){
				SearchNode Node=new SearchNode(child,(current.moves+1),current);
				if( !closeList.contains(Node)){
					int h=Node.board.heuristicVal(method);
					Node.set_f_val(Node.moves+h);
					openList.add(Node);

				}

			}
		}
		return null;

	}

	public static void main(String[] args) {
          while(true){
              Scanner scan=new Scanner(System.in);
          
                System.out.println("Enter value of n :");
                int n=scan.nextInt();
                int length=(int)Math.sqrt(n+1);
                int[][]data=new int[length][length];
                System.out.println("Enter matrix :");
                for(int row = 0; row<length; row++){
                    for(int col = 0 ;col<length; col++){
                        data[row][col] = scan.nextInt();
                    }
                }
		System.out.println("Enter Method\n");
		System.out.println("1.Hamming  2. Manhatton  3.Linear Conflict");
                int method=scan.nextInt();
		
		Board board=new Board(data);
		PrintResult pr = new PrintResult();
		int man=board.manhattan();
		int ham=board.hamming();
                int lc=board.linear_conflict();
		if(board.isSolvable()){
			System.out.println("Solvable");
			SearchNode result=a_star_search(board,method);
			if(result!=null){
				System.out.println("No of moves needed: "+result.moves);
				System.out.println("Solution: ");
				pr.printSolution(result);
			}
			else System.out.println("No Solution Found...");
                }	
		else System.out.println("Not Solvable");
          }
	}
	

	
	
}


