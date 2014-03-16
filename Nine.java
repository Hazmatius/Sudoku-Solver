import java.util.*;

public class Nine
{
    private Cell[] cells =  new Cell[9];
    
    public Nine(ArrayList<Cell> someCells)
    {
        cells = someCells.toArray(new Cell[9]);
    }
    
    /**
     * -1 = wrong, 0 = incomplete, 1 = solved
     */
    public int getTruthState()
    {
        int[] nums = new int[9];
        for(int i=0; i<9; i++)
        {
            int val = cells[i].getValue();
            if(val!=0)
            {
                nums[val-1]++;
            }
        }
        boolean solved = true;
        for(int i=0; i<9; i++)
        {
            solved = solved && nums[i]==1;
        }
        if(solved)
        {
            return 1;
        }
        else
        {
            boolean wrong = false;
            for(int i=0; i<9; i++)
            {
                wrong = wrong || nums[i]>1;
            }
            if(wrong)
            {
                return -1;
            }
            else
            {
                wrong = false;
                for(int i=0; i<9; i++)
                {
                    wrong = wrong || (cells[i].getValue()==N._0 && cells[i].getPosNumbers().size()==0);
                }
                if(wrong)
                {
                    return -1;
                }
                else
                {
                    return 0;
                }
            }
        }
    }
    
    /**
     * Returns a list of numbers that have been assigned in this nine.
     */
    public ArrayList<Integer> getTakenNumbers()
    {
        ArrayList<Integer> taken = new ArrayList<Integer>();
        for(int i=0; i<cells.length; i++)
        {
            taken.add(cells[i].getValue());
        }
        return taken;
    }
    
    public ArrayList<Integer> getAllPosNumbers()
    {
        ArrayList<Integer> allPosNumbers = new ArrayList<Integer>();
        for(int i=0; i<9; i++)
        {
            allPosNumbers.addAll(cells[i].getPosNumbers());
        }
        return allPosNumbers;
    }
    
    /**
     * Removes the given numbers from the possible numbers from each cell of the given cells.
     */
    public void removePosNumbers(ArrayList<Integer> nums, ArrayList<Cell> cels)
    {
        for(int i=0; i<cels.size(); i++)
        {
            for(int j=0; j<nums.size(); j++)
            {
                cels.get(i).removePosNumber(nums.get(j));
            }
        }
    }
    
    /**
     * Removes the given numbers from the possible numbers of all the cells in this nine.
     */
    public void simpleRemovePosNumbers(ArrayList<Integer> nums)
    {
        ArrayList<Cell> cels = new ArrayList<Cell>(Arrays.asList(cells));
        removePosNumbers(nums, cels);
    }
    
    /**
     * A type of elimination that pairs away possibile numbers based on what values have already been filled.
     */
    public void simpleElimination()
    {
        simpleRemovePosNumbers(getTakenNumbers());
    }
    
    /**
     * Returns a list of numbers that only appear once as possibilities in this nine.
     */
    public ArrayList<Integer> getSingleInstances()
    {
        ArrayList<Integer> allPosNumbers = getAllPosNumbers();
        int[] posNums = new int[9];
        for(int i=0; i<allPosNumbers.size(); i++)
        {
            posNums[allPosNumbers.get(i).intValue()-1]++;
        }
        ArrayList<Integer> singleInstances = new ArrayList<Integer>();
        for(int i=0; i<9; i++)
        {
            if(posNums[i]==1)
            {
                singleInstances.add(N.nums.get(i+1));
            }
        }
        return singleInstances;
    }
    
    /**
     * A type of elimination that looks for single instances of a number in a nine.
     */
    public void onlyOneElimination()
    {
        simpleElimination();
        ArrayList<Integer> singleInstances = getSingleInstances();
        for(int i=0; i<9; i++)
        {
            for(int j=0; j<singleInstances.size(); j++)
            {
                if(cells[i].getPosNumbers().indexOf(singleInstances.get(j))!=-1)
                {
                    cells[i].setValue(singleInstances.get(j));
                }
            }
        }
    }
    
    /**
     * Does this posNum item fit into the given list of other posNums?
     */
    private boolean doesPosNumListMatch(ArrayList<Integer> posNums, ArrayList<ArrayList<Integer>> posNumsList)
    {
        boolean b = posNumsList.get(0).equals(posNums);
        if(b)
        {
            posNumsList.add(posNums);
        }
        return b;
    }
    
    /**
     * Does this posNum item need a new buckit of identical posNums?
     */
    private boolean doesPosNumHaveList(ArrayList<Integer> posNums, ArrayList<ArrayList<ArrayList<Integer>>> posNumsListArray)
    {
        boolean dPNHL = false;
        for(int i=0; i<posNumsListArray.size(); i++)
        {
            dPNHL = dPNHL || doesPosNumListMatch(posNums, posNumsListArray.get(i));
        }
        if(!dPNHL)
        {
            ArrayList<ArrayList<Integer>> newBucket = new ArrayList<ArrayList<Integer>>();
            newBucket.add(posNums);
            posNumsListArray.add(newBucket);
        }
        return dPNHL;
    }
    
    /**
     * Returns a list of buckets, each filled with a number of the same lists of possible numbers.
     */
    private ArrayList<ArrayList<ArrayList<Integer>>> getListOfPosNumBuckets()
    {
        ArrayList<ArrayList<ArrayList<Integer>>> posNumBuckets = new ArrayList<ArrayList<ArrayList<Integer>>>();
        for(int i=0; i<9; i++)
        {
            doesPosNumHaveList(cells[i].getPosNumbers(), posNumBuckets);
        }
        return posNumBuckets;
    }
    
    /**
     * Returns a list of all N Pairs of N Possible Numbers.
     */
    private ArrayList<ArrayList<Integer>> getUniqueNPairs()
    {
        ArrayList<ArrayList<ArrayList<Integer>>> bucketList = getListOfPosNumBuckets();
        int[] posNumSize = new int[bucketList.size()];
        int[] posNumListSize = new int[bucketList.size()];
        for(int i=0; i<bucketList.size(); i++)
        {
            posNumSize[i] = bucketList.get(i).get(0).size();
            posNumListSize[i] = bucketList.get(i).size();
        }
        ArrayList<ArrayList<Integer>> uniqueNPairs = new ArrayList<ArrayList<Integer>>();
        for(int i=0; i<bucketList.size(); i++)
        {
            if(posNumSize[i]==posNumListSize[i])
            {
                uniqueNPairs.add(bucketList.get(i).get(0));
            }
        }
        return uniqueNPairs;
    }
    
    /**
     * A type of elimination that possibilies based on there being n pairs of n possible numbers in a nine.
     */
    public void nPairsElimination()
    {
        ArrayList<ArrayList<Integer>> uniqueNPairs = getUniqueNPairs();
        for(int i=0; i<9; i++)
        {
            for(int j=0; j<uniqueNPairs.size(); j++)
            {
                if(!cells[i].getPosNumbers().equals(uniqueNPairs.get(j)))
                {
                    cells[i].removePosNumbers(uniqueNPairs.get(j));
                }
            }
        }
    }
}