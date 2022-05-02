public class Selection {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        int[] random = {4, 1, 1, 7, 8, 3, 2};
        Selection selection = new Selection();
        int k = selection.algorithm1(random, 0, random.length-1, 4);
        System.out.println(k);
        int[] same = {4, 1, 1, 7, 8, 3, 2};
        k = selection.quickSort(same, same.length, 4);
        System.out.println(k);
        
        for (int element: random) {
            System.out.print(element + " ");
        }

    }
    public int algorithm1(int A[], int low, int high, int k)
    {
        mergeSort(A, low, high);
        return A[k-1];
    }
    public void mergeSort(int A[], int low, int high)
    {
        if(low<high)
        {
            int mid = low + (high-low)/2;
            mergeSort(A, low, mid);
            mergeSort(A, mid+1, high);
            merge(A, low, mid, high);

        }
    }

    public void merge(int[] A, int low, int mid, int high)
    {
        int i = low;
        int j = mid+1;
        int k = low;
        int[] temp = new int[A.length];

        while(i<=mid && j<=high)
        {
            if(A[i]<A[j])
            {
                temp[k] = A[i];
                i++;
            }
            else
            {
                temp[k] = A[j];
                j++;
            }
            k++;
        }

        if (i>mid)
        {
            while(j <= high)
            {
                temp[k] = A[j];
                k++;
                j++;
            }
        }
        else 
        {
            while(i <= mid)
            {
                temp[k] = A[i];
                k++;
                i++;
            }       
        }

        for(int p = low; p <= high; p++)
        {
            A[p] = temp[p];
        }
    }

    public int quickSort(int[] A, int n, int k)//A is array, n is # of array, k is key
    {
        int m = 0;
        int j = n-1;
        k = k-1;

        int pivotPosition = 0;
        while(true)
        {
            pivotPosition = partition(A, m, j, pivotPosition);
            //Successfully found kth position
            if(k == pivotPosition)
            {
                return A[k];
            }
            //Search the lower half before pivot position
            else if(k < pivotPosition)
            {
                j = pivotPosition-1;
            }
            //Search the upper half greater than pivot value
            else 
            {
                m = pivotPosition+1;
                //k = k-pivotPosition;
            }
        }

    }

    public int partition(int[] A, int low, int high, int pivotPosition)
    {
        int v = A[low];
        int j = low;
        int temp;

        for(int i = (low+1); i<=high; i++)
        {
            if(A[i] < v)
            {
                j++;
                temp = A[i];
                A[i] = A[j];
                A[j] = temp;
                
            }
        }
        pivotPosition = j;
        temp = A[low];
        A[low] = A[pivotPosition];
        A[pivotPosition] = temp;
        return pivotPosition;
    }
}
