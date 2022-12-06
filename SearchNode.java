import java.util.Arrays;

class SearchNode {             // A search node consists of the board, number of moves to reach
    int moves;                 // this step and pointed to the previous search node
    Board board;
    SearchNode prev;
    int f_val;

    public SearchNode(Board initial) {
        board = initial;
    }
    public SearchNode(Board board,int move,SearchNode prev){
        this.board=board;
        this.moves=move;
        this.prev=prev;
    }
    void set_f_val(int val){
        f_val=val;
    }
    int get_f_val(){
        return f_val;
    }

    @Override
    public int hashCode(){
        int hash;
        hash = Arrays.deepHashCode(this.board.matrix);
        return hash;
    }
    @Override
    public boolean equals(Object obj)
    {

        SearchNode node = (SearchNode) obj;
        int len =this.board.length;
        for (int i = 0;i<len; i++)
        {
            for(int j=0;j<len;j++)
            {
                if(this.board.matrix[i][j]!=node.board.matrix[i][j])
                {
                    return false;
                }
            }
        }
        // System.out.println("Matching");
        return true;
    }



}