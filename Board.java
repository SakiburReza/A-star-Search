/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.LinkedList;
import java.util.List;


public class Board 
{
	int [][]matrix;
	int length;
	int zeroRow;
	//int fval;
	//int moves;
	 public Board(int[][] tiles) {
		 // create a board from an n-by-n array of tiles,
		 int size=tiles[0].length;
		 length=size;
		 //moves=0;
		 matrix=new int[size][size];
		 for(int i=0;i<size;i++){
			 for(int j=0;j<size;j++){
				 matrix[i][j]=tiles[i][j];
			 }
		 }
	 }
     
public int tileAt(int row, int col)    // tile at (row, col) or 0 if blank
{
	if (!(row < 0 || row >= length)&& !(col < 0 || col >= length)){
		return matrix[row][col];
	}

return -1000;
}

public List<Board> successors()     // all neighboring boards
	{
		int ival=-1,jval=-1;
		boolean found=false;
		int[][] array=this.matrix;
		LinkedList<Board> q =new LinkedList<>();
		for(int i=0;i<array.length;i++){
			for(int j=0;j<array.length;j++){
				if(array[i][j]==0) {
					ival=i;
					jval=j;
					found=true;
					break;
				}
			}
			if(found) break;
		}
		int result[][]=left_move(array,ival,jval);
		if(result!=null){
			Board successor=new Board(result);
			q.add(successor);
		}
		result=right_move(array,ival,jval);
		if(result!=null){
			Board successor=new Board(result);
			q.add(successor);
		}

		result=up_move(array,ival,jval);
		if(result!=null){
			Board successor=new Board(result);
			q.add(successor);
		}

		result=down_move(array,ival,jval);
		if(result!=null){
			Board successor=new Board(result);
			q.add(successor);
		}

		return q;
	}
	public boolean isSolvable()
	{
		int inversions = 0;

		for (int i = 0; i < length * length; i++) {
			int currentRow = i /length;
			int currentCol = i % length;

			if (tileAt(currentRow, currentCol) == 0) {
				this.zeroRow = currentRow;
				//this.zeroCol = currentCol;
			}

			for (int j = i; j <length*length; j++) {
				int row = j / length;
				int col = j % length;


				if (tileAt(row, col) != 0 && tileAt(row, col) < tileAt(currentRow, currentCol)) {
					inversions++;
				}
			}
		}

		if (matrix.length % 2 != 0 && inversions % 2 != 0) return false;
		if (matrix.length % 2 == 0 && (inversions + this.zeroRow) % 2 == 0) return false;
		return true;
	}

	int heuristicVal(int choice){
		switch (choice) {
			case 1:
				return this.hamming();
			case 2:
				return this.manhattan();
			case 3:
				return this.linear_conflict();
			default:
				return 0;
		}

	}

public int hamming()//# of blocks in the wrong position
{

        int hamm_distance = 0;
        for (int i = 0; i <length; i++) {
            for (int j = 0; j <length; j++) {
                if (tileAt(i,j) != 0 && tileAt(i, j) != (i*length+ j + 1)) hamm_distance++; 
            }
        }
        return hamm_distance;
    
}

public int manhattan()
{
	int manhattan = 0;

    int expectedRow,expectedCol;
    for (int row = 0; row <length; row++) {
        for (int col = 0; col <length; col++) {
            if (tileAt(row, col) != 0 && tileAt(row, col) != (row*length + col + 1)) {
                expectedRow = (tileAt(row, col) - 1) / length;
                expectedCol = (tileAt(row, col) - 1) % length;
                manhattan += Math.abs(expectedRow - row) + Math.abs(expectedCol - col);
            }
        }
    }
    return manhattan;
}

public int linear_conflict()
{
    int man_dist=manhattan();
    int conflict=0;
    //for row line
    for (int row = 0; row < length; row++)
    {
	//int max = -1;
	for (int column = 0;  column <length; column++)
        {
	 int value =tileAt(row, column);
         
		if (value != 0 && ((value-1)/length == row))
                {
                    for(int k=column+1;k<length;k++){
                        int value2 =tileAt(row,k);
                        if(value2 != 0 && ((value2-1)/length == row)){
                            if (value>value2) conflict++;
                        }
                    }
		    
		}
	}
    }

    /*for (int column = 0; column <length; column++){
	for (int row = 0;  row < length; row++){
	    int value =tileAt(row,column);
                if (value!=0 && ((value %length)==(column+1)))
                {
		    for(int k=row+1;k<length;k++){
                        int value2 =tileAt(k,column);
                        if(value2 != 0 && (value2%length == (column+1))){
                            if (value>value2) conflict++;
                        }
                    }
		}				
	}
			
    }*/
    return man_dist+2*conflict;
}
  

public boolean isGoal()
{
	if (tileAt(length-1, length-1) != 0) return false;  

    for (int i = 0; i < length; i++)
    {
        for (int j = 0; j < length; j++) 
        {
            if (tileAt(i, j) != 0 && tileAt(i, j) != (i*length + j + 1)) return false;
        }
    }

    return true;
}




	int[][] right_move(int b[][],int i,int j){
		int len=b[0].length;
		if(j==len-1)return null;
		int arr[][]=new int[len][len];
		array_copy(arr,b,len);
		int temp=arr[i][j];
		arr[i][j]=arr[i][j+1];
		arr[i][j+1]=temp;
		return arr;
	}
	void array_copy(int arr1[][],int arr2[][],int n){
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				arr1[i][j]=arr2[i][j];
			}
		}
	}

int[][] up_move(int b[][],int i,int j){
	
	if(i==0) return null;
	int len=b[0].length;
	int arr[][]=new int[len][len];
	array_copy(arr,b,len);
	int temp=arr[i][j];
	arr[i][j]=arr[i-1][j];
	arr[i-1][j]=temp;
	return arr;
	
	
}
int[][] down_move(int b[][],int i,int j){
	int len=b[0].length;	
	if(i==len-1)return null;
	int arr[][]=new int[len][len];
	array_copy(arr,b,len);
	int temp=arr[i][j];
	arr[i][j]=arr[i+1][j];
	arr[i+1][j]=temp;
	return arr;
}
int[][] left_move(int b[][],int i,int j){
	int len=b[0].length;
	if(j==0)return null;
	int arr[][]=new int[len][len];
	array_copy(arr,b,len);
	int temp=arr[i][j];
	arr[i][j]=arr[i][j-1];
	arr[i][j-1]=temp;
	return arr;
}


}
