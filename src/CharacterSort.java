import java.util.Vector;


public class CharacterSort {
    public static void sort(Vector<Character> a) {
        sort(a, 0, a.size() - 1);
    }
    private static void sort(Vector<Character> a, int lo, int hi) { 
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }
    
    private static int partition(Vector<Character> a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Character v = a.get(lo);
        while(true) { 

            // find item on lo to swap
            while (less(a.get(++i), v))
                if (i == hi) break;

            // find item on hi to swap
            while (less(v, a.get(--j)))
                if (j == lo) break;      // redundant since a[lo] acts as sentinel

            // check if pointers cross
            if (i >= j) break;

            swap(a, i, j);
        }

        // put v = a[j] into position
        swap(a, lo, j);

        // with a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }
    
    public static  void swap(Vector<Character> a, int i, int j){
    	a.insertElementAt(a.get(i), j+1);
    	a.remove(i);
    	
    
    }
    
    

   /***********************************************************************
    *  Rearranges the elements in a so that a[k] is the kth smallest element,
    *  and a[0] through a[k-1] are less than or equal to a[k], and
    *  a[k+1] through a[n-1] are greater than or equal to a[k].
    ***********************************************************************/
    public static Character select(Vector<Character> a, int k) {
        if (k < 0 || k >= a.size()) {
            throw new RuntimeException("Selected element out of bounds");
        }
        int lo = 0, hi = a.size() - 1;
        while (hi > lo) {
            int i = partition(a, lo, hi);
            if      (i > k) hi = i - 1;
            else if (i < k) lo = i + 1;
            else return a.get(i);
        }
        return a.get(lo);
    }



   /***********************************************************************
    *  Helper sorting functions
    ***********************************************************************/
    
    // is v < w ?
    private static boolean less(Character v, Character w) {
        return v.getDistanceFromOrigin() < w.getDistanceFromOrigin();
        
    }
        

   /***********************************************************************
    *  Check if array is sorted - useful for debugging
    ***********************************************************************/


}
