import java.util.Comparator;

public class BoardComparator implements Comparator<SearchNode> {


    @Override
    public int compare(SearchNode a, SearchNode b) {
        int pa = a.get_f_val();
        int pb = b.get_f_val();
        if (pa > pb)   return 1;
        else if (pa < pb)   return -1;
        else  return 0;
    }
}
