import java.util.ArrayList;

public class PrintResult {
    PrintResult(){}

    public void printSolution(SearchNode node){

        ArrayList<SearchNode> path=new ArrayList<>();
        SearchNode start=node;
        while(start!=null){
            path.add(start);
            start=start.prev;
        }
        int depth=path.size()-1;
        for(int i=depth;i>=0;i--){
            start=path.get(i);
            System.out.println("move# "+start.moves);
            print(start.board.matrix);

        }

        System.out.println("Depth : "+depth);
    }
    public static void print(int arr[][]){
        for (int[] arr1 : arr) {
            for (int j = 0; j<arr.length; j++) {
                if(arr1[j]==0) System.out.print("*"+" ");
                else System.out.print((arr1[j]) + " ");
            }
            System.out.println("");
        }
        System.out.println("\n");
    }
}
