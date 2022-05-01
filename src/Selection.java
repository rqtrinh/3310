public class Selection {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        int[] random = {5, 3, 1, 2, 4, 9, 3};
        Selection selection = new Selection();
        selection.mergeSort(0, random.length, random);
    }
    public void mergeSort(int low, int high, int[] input)
    {
        if(low<high)
        {
            int mid = low + (high-low)/2;
            mergeSort(low, mid, input);
            mergeSort(mid+1, high, input);
            merge(low, mid, high, input);

        }
    }

    public void merge(int low, int mid, int high, int[] input)
    {
        int i = low;
        int j = mid;
        int k = high;
        int[] temp = new int[input.length];

        while(i<=mid && j<=high)
        {
            if(input[i]<input[j])
            {
                temp[k] = input[i];
                i++;
            }
            else
            {
                temp[k] = input[j];
                j++;
            }
            k++;
        }

        if (i>mid)
        {
            while(j < high)
            {
                temp[k] = input[j];
                j++;
                k++;
            }
        }
        else 
        {
            while(i < mid)
            {
                temp[k] = input[i];
                i++;
                k++;
            }       
        }

        for(int p = low; low < high+1; low++)
        {
            input[p] = temp[p];
        }
    }
}
