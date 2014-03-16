import java.util.*;
import java.awt.*;

public class Puzzle
{
    public static int drawSize = 100;
    private Cell[][] grid = new Cell[9][9];
    private ArrayList<Nine> nines = new ArrayList<Nine>();
    
    private Cell selectedCell;
    
    private Color incomplete = Color.YELLOW;
    private Color solved = Color.GREEN;
    private Color wrong = Color.RED;
    
    public static Color stateColor;
    
    public Puzzle currentPuzzle = this;
    
    public void printPuzzle()
    {
        for(int i=0; i<9; i++)
        {
            for(int j=0; j<9; j++)
            {
                if(grid[i][j].getValue()==N._0)
                {
                    System.out.print(" .");
                }
                else
                {
                    System.out.print(" " + grid[i][j].getValue().intValue());
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public void resetPosNumbers()
    {
        for(int i=0; i<9; i++)
        {
            for(int j=0; j<9; j++)
            {
                if(grid[i][j].getValue()==N._0)
                {
                    grid[i][j].setValue(N._0);
                }
            }
        }
    }
    
    public Puzzle()
    {
        for(int i=0; i<9; i++)
        {
            for(int j=0; j<9; j++)
            {
                grid[i][j] = new Cell(i, j);
            }
        }
        
        for(int i=0; i<9; i++)
        {
            nines.add(makeRow(i));
            nines.add(makeCol(i));
        }
        for(int i=0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                nines.add(makeBox(i, j));
            }
        }
    }
    
    public Puzzle(Cell[][] cells)
    {
        grid = cells;
        for(int i=0; i<9; i++)
        {
            nines.add(makeRow(i));
            nines.add(makeCol(i));
        }
        for(int i=0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                nines.add(makeBox(i, j));
            }
        }
    }
    
    public Puzzle clone()
    {
        Cell[][] cells = new Cell[9][9];
        for(int i=0; i<9; i++)
        {
            for(int j=0; j<9; j++)
            {
                cells[i][j] = grid[i][j].clone();
            }
        }
        return new Puzzle(cells);
    }
    
    public void setTo(Puzzle p)
    {
        if(p!=null)
        {
            grid = p.getGrid();
            nines = p.getNines();
        }
    }
    
    private Nine makeRow(int row)
    {
        ArrayList<Cell> rowNine = new ArrayList<Cell>();
        for(int i=0; i<9; i++)
        {
            rowNine.add(grid[row][i]);
        }
        return new Nine(rowNine);
    }
    
    private Nine makeCol(int col)
    {
        ArrayList<Cell> colNine = new ArrayList<Cell>();
        for(int i=0; i<9; i++)
        {
            colNine.add(grid[i][col]);
        }
        return new Nine(colNine);
    }
    
    private Nine makeBox(int boxRow, int boxCol)
    {
        ArrayList<Cell> boxNine = new ArrayList<Cell>();
        for(int i=3*boxRow; i<3*boxRow+3; i++)
        {
            for(int j=3*boxCol; j<3*boxCol+3; j++)
            {
                boxNine.add(grid[i][j]);
            }
        }
        return new Nine(boxNine);
    }
    
    public int getTruthState()
    {
        int[] truthStates = new int[nines.size()];
        for(int i=0; i<nines.size(); i++)
        {
            truthStates[i] = nines.get(i).getTruthState();
        }
        int wrong = 0;
        int incomplete = 0;
        int solved = 0;
        for(int i=0; i<nines.size(); i++)
        {
            if(truthStates[i]==-1){wrong++;}
            if(truthStates[i]==0){incomplete++;}
            if(truthStates[i]==1){solved++;}
        }
        if(wrong>0)
        {
            return -1;
        }
        else if(solved==nines.size())
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    
    public Cell[][] getGrid()
    {
        return grid;
    }
    
    public ArrayList<Nine> getNines()
    {
        return nines;
    }
    
    public boolean equals(Puzzle p)
    {
        boolean equals = true;
        for(int i=0; i<9; i++)
        {
            for(int j=0; j<9; j++)
            {
                equals = equals && grid[i][j].equals(p.getGrid()[i][j]);
            }
        }
        return equals;
    }
    
    public void setColor()
    {
        int truthState = getTruthState();
        if(truthState==-1)
        {
            stateColor = Color.RED;
        }
        if(truthState==0)
        {
            stateColor = Color.YELLOW;
        }
        if(truthState==1)
        {
            stateColor = Color.GREEN;
        }
    }
    
    /**
     * 
     */
    public void setAndCheckAllCells()
    {
        for(int i=0; i<9; i++)
        {
            for(int j=0; j<9; j++)
            {
                grid[i][j].checkAndSet();
            }
        }
    }
    
    public void simpleElimination()
    {
        for(int i=0; i<nines.size(); i++)
        {
            nines.get(i).simpleElimination();
        }
        setAndCheckAllCells();
    }
    
    public void onlyOneElimination()
    {
        for(int i=0; i<nines.size(); i++)
        {
            nines.get(i).onlyOneElimination();
        }
    }
    
    public void nPairsElimination()
    {
        for(int i=0; i<nines.size(); i++)
        {
            nines.get(i).nPairsElimination();
        }
    }
    
    public void logicSolve()
    {
        onlyOneElimination();
        nPairsElimination();
        simpleElimination();
    }
    
    public void iterativeLogicSolve()
    {
        Puzzle oldP = this.clone();
        logicSolve();
        while(!this.equals(oldP))
        {
            oldP = this.clone();
            logicSolve();
        }
    }
    
    public void solve()
    {
        Puzzle solution = findSolution();
        this.setTo(solution);
    }
    
    private Cell getChoice()
    {
        Cell choice = grid[0][0];
        for(int i=0; i<9; i++)
        {
            for(int j=0; j<9; j++)
            {
                int oldSize = choice.getPosNumbers().size();
                int newSize = grid[i][j].getPosNumbers().size();
                if( (oldSize==0 && newSize!=0) || (oldSize>2 && newSize<oldSize) )
                {
                    choice = grid[i][j];
                }
            }
        }
        return choice;
    }
    
    public Puzzle findSolution()
    {
        iterativeLogicSolve();
        printPuzzle();
        if(getTruthState()==1)
        {
            return this;
        }
        else if(getTruthState()==-1)
        {
            return null;
        }
        else
        {
            Cell c = this.getChoice();
            ArrayList<Integer> guesses = c.getPosNumbers();
            Puzzle solution = null;
            for(int i=0; i<guesses.size(); i++)
            {
                Puzzle guess = this.clone();
                guess.getCell(c.getRow(), c.getCol()).setValue(guesses.get(i));
                Puzzle terminal = guess.findSolution();
                if(terminal!=null)
                {
                    solution = terminal;
                    i = guesses.size();
                }
            }
            return solution;
        }
    }
    
    public Cell getCell(int r, int c)
    {
        return grid[r][c];
    }
    
    public void draw(Graphics2D g2)
    {
        logicSolve();
        setColor();
        
        for(int i=0; i<9; i++)
        {
            for(int j=0; j<9; j++)
            {
                grid[i][j].draw(g2);
            }
        }
        for(int i=0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                Rectangle r = new Rectangle(i*drawSize*3, j*drawSize*3, drawSize*3, drawSize*3);
                g2.setColor(Puzzle.stateColor);
                g2.setStroke(new BasicStroke(7));
                g2.draw(r);
                g2.setStroke(new BasicStroke(3));
            }
        }
    }
    
    public void drawPuzzle(Graphics2D g2)
    {
        currentPuzzle.draw(g2);
    }
    
    public void click(int x, int y)
    {
        if(x<9*drawSize && y<9*drawSize)
        {
            Cell clickedCell = grid[y/drawSize][x/drawSize];
            if(clickedCell==selectedCell)
            {
                selectedCell.deselect();
                selectedCell = null;
            }
            else
            {
                if(selectedCell!=null)
                {
                    selectedCell.deselect();
                }
                selectedCell = clickedCell;
                selectedCell.select();
            }
        }
    }
    
    public void setValueOfSelectedCell(Integer i)
    {
        if(selectedCell!=null)
        {
            selectedCell.setValue(i);
        }
    }
}