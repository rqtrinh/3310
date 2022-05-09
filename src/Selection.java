import java.util.Arrays;
import java.util.Random;

public class Selection {
    public static void main(String[] args) throws Exception 
    {


        //Creating Objets so we can test methods
        Random random = new Random();
        Selection selection = new Selection();

        //These are variables we can change for each time we test
        int n = 1000;
        int randomTrials = 100;
        int sameTrial = 10;

        //Initialize k
        int k = 0;
        //Variable will contain kth element after algorithm is complete
        int result;

        //Start and finish times for our timer
        long start, finish;
        //Total sum of randomly generated runtimes
        long totalTimeSelectKth1 = 0;
        long totalTimeSelectKth2 = 0;
        long totalTimeSelectKth3 = 0;
        long totalTimeSelectKth4 = 0;

        //Loop for random trials
        for(int randomGenerate = 0; randomGenerate < randomTrials; randomGenerate++)
        {
            //Create our test array
            int[] test = new int[n];
            //Populate it with random variables
            for(int j = 0; j < n; j++)
            {
                //testing from range 200 to -200
                test[j] = random.nextInt(401)-200;
            }

            //Make a reference because some of our algorithms will alter our array
            int[] reference = Arrays.copyOf(test, test.length);

            //Total sum of randomly generated runtimes
            long timeSelectKth1 = 0;
            long timeSelectKth2 = 0;
            long timeSelectKth3 = 0;
            long timeSelectKth4 = 0;

            for(int kValues = 0; kValues < 5; kValues++)
            {
                switch(kValues) {
                    case 0: k = 1;
                    case 1: k = n/4;
                    case 2: k = n/2;
                    case 3: k = (3*n)/4;
                    case 4: k = n;
                }

                //Testing the same array for each k value
                for(int same = 0; same < sameTrial; same++)
                {
                    test = Arrays.copyOf(reference, reference.length);
                    start = System.nanoTime();
                    result = selection.selectKth1(test, 0, test.length, k);
                    finish = System.nanoTime();
                    timeSelectKth1 += (finish-start);

        
                    test = Arrays.copyOf(reference, reference.length);
                    start = System.nanoTime();
                    result = selection.selectKth2(test, test.length, k);
                    finish = System.nanoTime();
                    timeSelectKth2 += (finish-start);
        
                    test = Arrays.copyOf(reference, reference.length);
                    start = System.nanoTime();
                    result = selection.selectKth3(test, 0, test.length, k);
                    finish = System.nanoTime();
                    timeSelectKth3 += (finish-start);

        
                    test = Arrays.copyOf(reference, reference.length);
                    start = System.nanoTime();
                    result = selection.selectKth4(test, test.length, k);
                    finish = System.nanoTime();
                    timeSelectKth4 += (finish-start);
                }
            }

            //Find the average time of trials that have the same input 
            //The 5 represents the 5 different k values we tested 
            //For each of the 5 values we tested it sameTrial times 
            //Must multiply sameTrial*5 and divide it by the total to get avg
            totalTimeSelectKth1 += (timeSelectKth1/(sameTrial*5));
            totalTimeSelectKth2 += (timeSelectKth2/(sameTrial*5));
            totalTimeSelectKth3 += (timeSelectKth3/(sameTrial*5));
            totalTimeSelectKth4 += (timeSelectKth4/(sameTrial*5));

        }
        //Find the average runtime of all random arrays for each algorithm
        totalTimeSelectKth1 = (totalTimeSelectKth1/randomTrials);
        totalTimeSelectKth2 = (totalTimeSelectKth2/randomTrials);
        totalTimeSelectKth3 = (totalTimeSelectKth3/randomTrials);
        totalTimeSelectKth4 = (totalTimeSelectKth4/randomTrials);

        //Print the data on terminal for each algorithm 
        System.out.println("For Array Size: n = " + n + "\n" +
                           "Select-kth 1: " + totalTimeSelectKth1 + " Nanoseconds\n"+
                           "Select-kth 2: " + totalTimeSelectKth2 + " Nanoseconds\n"+
                           "Select-kth 3: " + totalTimeSelectKth3 + " Nanoseconds\n"+
                           "Select-kth 4: " + totalTimeSelectKth4 + " Nanoseconds\n");
    }

    
    //Utilizes mergeSort, sort entire array, return kth element
    //@parameters: A = input array, low = starting index of array, 
    //high = number of elements, k = kth element we want to find (not an index)
    public int selectKth1(int A[], int low, int high, int k)
    {
        //Call mergeSort to sort entire array
        mergeSort(A, low, high-1);
        //Return the kth element
        return A[k-1];
    }

    //Split into 2 eqaul parts using mergeSort, then merge two sorted
    //sub-lists into one list
    //@parameters: A = input array, low = starting index of array, 
    //high = last index of array
    public void mergeSort(int A[], int low, int high)
    {
        //make sure it is a valid index
        if(low<high)
        {
            //find the midpoint
            int mid = low + (high-low)/2;
            //split lower half
            mergeSort(A, low, mid);
            //split upper half
            mergeSort(A, mid+1, high);
            //merge the sub-lists into one sorted list
            merge(A, low, mid, high);

        }
    }

    //Merge two sub-lists into sorted list
    //@parameters: A = input array, low = first index of array
    //mid = middle index of array, high = last index of the array
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
            //move A[j] through A[high] to temp[k] through temp[high]
            while(j <= high)
            {
                temp[k] = A[j];
                k++;
                j++;
            }
        }
        else 
        {
            //move A[i] through A[mid] to temp[k] through temp[high]
            while(i <= mid)
            {
                temp[k] = A[i];
                k++;
                i++;
            }       
        }

        //move the elements in the actual array
        for(int p = low; p <= high; p++)
        {
            A[p] = temp[p];
        }
    }

    //Select kth element from an array utilizing Parition from Quicksort
    //it is iterative
    //@parameters: A is array, n is # of elements array,
    //k = kth element we want to find (not an index)
    public int selectKth2(int[] A, int n, int k)
    {
        //Change these values to be consistent with array indexing
        int m = 0;
        int j = n-1;
        k = k-1;

        //Initialize pivot position
        int pivotPosition;
        while(true)
        {
            pivotPosition = partition(A, m, j);
            //Successfully found kth position
            if(k == pivotPosition)
            {
                //return that element
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
            }
        }

    }

    //Select kth element from an array utilizing Parition from Quicksort
    //it is recursive
    //@parameters: A is array, p is the first index
    //q = # of elements
    //k = kth element we want to find (not an index)
    public int selectKth3(int[] A , int p, int q, int k)
    {
        quickSortRecursiveKth(A, p, q-1, k);
        //return the kth element
        return A[k-1];
    }

    //This is method is like quicksort recursive and utlizes partition
    //However it will stop when it finds the kth element 
    //@parameter: A = input array, p = first index
    //q = last index, k = kth element
    public void quickSortRecursiveKth(int[] A, int p, int q, int k)
    {
        //Initialize pivot position
        int pivotPosition;
        //make sure the array indexes are valid
        if(p<q)
        {
            //Partition the array 
            pivotPosition = partition(A, p, q);
            //Successfully found element it will be in kth spot
            if((k-1) == pivotPosition)
            {
                return;
            }
            //Search lower half
            else if((k-1) < pivotPosition)
            {
                quickSortRecursiveKth(A, p, pivotPosition-1, k);
            }
            //Search upper half
            else
            {
                quickSortRecursiveKth(A, pivotPosition+1, q, k);
            }
        }
    }

    //Partition method takes first number moves all lesser elements in front of it
    //in the array and all greater numbers after it. It returns the 
    //pivotPosition index where this number is located
    //@paremeters: A = input array, low = first index of array
    //high = last index of array
    public int partition(int[] A, int low, int high)
    {
        //Compare values to the value in the first index of the array
        int v = A[low];
        int j = low;
        //We will use this later to swap elements in the array
        int temp;
        //Initalize pivotPosition
        int pivotPosition;

        //Loop through the array starting at low+1
        for(int i = (low+1); i<=high; i++)
        {
            //Compare pivotValue to next value
            //Swap lower values starting at low+1 index
            //and in consecutive indexes after this
            if(A[i] < v)
            {
                j++;
                temp = A[i];
                A[i] = A[j];
                A[j] = temp;
                
            }
        }
        //Swap pivotValue which is at index 0 with
        //value at pivotPosition which is less than pivotValue
        pivotPosition = j;
        temp = A[low];
        A[low] = A[pivotPosition];
        A[pivotPosition] = temp;
        //Everything left of pivotValue is less than it
        //Everything to the right is greater than it
        //Return the index that pivotValue is at
        return pivotPosition;
    }

    //Select kth element from an array by finding MM and also using a partition method
    //This method is recursive and will return the kth element, utlizes special version
    //of partition method
    //@parameter: A = input array, n = length of array, k = kth element 
    public int selectKth4(int[] A, int n, int k)
    {
        //Each subset will have 5 elements
        int r = 5;

        //If there are not enough elements to fill the subsets
        //Sort and return kth element
        if(n <= r)
        {
            Arrays.sort(A);
            return A[k-1];
        }

        //Find out how many subsets there will be 
        int subsets;
        if(n%r == 0)
        {
            subsets = n/r;
        }
        else 
        {
            subsets = (n/r) + 1;
        }

        //Get an array of medians
        int[] mediansArray = medianfMedians(A, subsets, r);
        //Recursively call until we get MM
        int v = selectKth4(mediansArray, subsets, subsets/2);

        //Use this to get the pivotPosition of V and to do one partition
        int pivotPosition = partition2(A, 0, A.length-1, v);

        //Successfully found kth position
        if((k-1) == pivotPosition)
        {
            return v;
        }
        //Search lower half from pivot position
        else if((k-1) < pivotPosition)
        {
            int[] S = Arrays.copyOfRange(A, 0, pivotPosition);
            return selectKth4(S, pivotPosition, k);
        }
        //Search Upper half from pivot position
        else 
        {
            int[] R = Arrays.copyOfRange(A, pivotPosition+1, n);
            return selectKth4(R, (n-pivotPosition)-1, (k-pivotPosition)-1);
        }
    }

    //Helper method for select Kth 4, it will return an array that 
    //has the median of each subset of r elements
    //@parameter: A = input array, totalMedians = total amount of medians
    //r = the size of the subset
    public int[] medianfMedians(int[] A, int totalMedians, int r)
    {
        //Initialize the array
        int[] mediansArray = new int[totalMedians];

        //The start and finish of subsets according to the entire array
        int start = 0;
        int finish = r;

        //Loop through mediansArray
        for(int i = 0; i < totalMedians; i++)
        {
            //If we can get 5 elements
            if(finish <= A.length)
            {
                //Get the subarray of 5 elements 
                //sort and add the median to mediansArray
                int[] subArray = Arrays.copyOfRange(A, start, finish);
                Arrays.sort(subArray);
                mediansArray[i] = subArray[subArray.length/2];
            }
            //The subset has less than 5 elements
            else 
            {
                //Get the subarray of remaining elements 
                //sort and add the median to mediansArray
                int[] subArray = Arrays.copyOfRange(A, start, A.length);
                Arrays.sort(subArray);
                mediansArray[i] = subArray[subArray.length/2];
            }

            //increase the index to next subarray
            start += r;
            finish += r;
        }
        //Return the array with the medians of all subsets
        return mediansArray;
    }

    //This is a modified partition method specifically designed for 
    //select kth 4 which uses partiton and MM
    //@parameter: A = input array, low = first index of array
    //high = last index of array, pivotValue = value which we will pivot around
    public int partition2(int[] A, int low, int high, int pivotValue)
    {
        //We will use this to move two values in an array
        int temp;
        //Find our pivot value and move it to the first index of the array
        for(int k = 0; k < A.length; k++)
        {
            if(pivotValue == A[k])
            {
                temp = A[low];
                A[low] = A[k];
                A[k] = temp;
                break;
            }
        }

        //Compare values to the value in the first index of the array
        int v = A[low];
        int j = low;
        int pivotPosition;

        //Loop through the array
        for(int i = (low+1); i<=high; i++)
        {
            //Moving values lower starting at low+1 index
            //in consecutive indexes
            if(A[i] < v)
            {
                j++;
                temp = A[i];
                A[i] = A[j];
                A[j] = temp;
                
            }
        }
        //Swap pivotValue which is at index 0 with
        //value at pivotPosition which is less than pivotValue
        pivotPosition = j;
        temp = A[low];
        A[low] = A[pivotPosition];
        A[pivotPosition] = temp;
        //Everything left of pivotValue is less than it
        //Everything to the right is greater than it
        //Return the position that it is at
        return pivotPosition;
    }
}
