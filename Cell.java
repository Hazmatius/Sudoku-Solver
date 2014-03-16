import java.util.*;
import java.awt.*;

public class Cell
{
    private int row;
    private int col;
    
    private boolean selected = false;
    
    private Integer value =  N._0;
    private ArrayList<Integer> posNumbers = new ArrayList<Integer>();
    
    public Cell(int row, int col)
    {
        this.row = row;
        this.col = col;
        posNumbers.add(N._1);
        posNumbers.add(N._2);
        posNumbers.add(N._3);
        posNumbers.add(N._4);
        posNumbers.add(N._5);
        posNumbers.add(N._6);
        posNumbers.add(N._7);
        posNumbers.add(N._8);
        posNumbers.add(N._9);
    }
    
    public int getRow()
    {
        return row;
    }
    
    public int getCol()
    {
        return col;
    }
    
    public Cell(int row, int col, Integer value, ArrayList<Integer> posNumbers)
    {
        this.row = row;
        this.col = col;
        this.value = value;
        this.posNumbers.addAll(posNumbers);
    }
    
    public Cell clone()
    {
        return new Cell(row, col, value, posNumbers);
    }
    
    public boolean equals(Cell c)
    {
        return value.equals(c.getValue()) && posNumbers.equals(c.getPosNumbers());
    }
    
    public void select()
    {
        selected = true;
    }
    
    public void deselect()
    {
        selected = false;
    }
    
    public Integer getValue()
    {
        return value;
    }
    
    public void setValue(Integer i)
    {
        value = i;
        posNumbers.clear();
        if(i==N._0)
        {
            posNumbers.add(N._1);
            posNumbers.add(N._2);
            posNumbers.add(N._3);
            posNumbers.add(N._4);
            posNumbers.add(N._5);
            posNumbers.add(N._6);
            posNumbers.add(N._7);
            posNumbers.add(N._8);
            posNumbers.add(N._9);
        }
    }
    
    /**
     * Checks to see if there is only one possible value in this cell. If there is, then it sets the value of this cell to that number.
     */
    public void checkAndSet()
    {
        if(posNumbers.size()==1)
        {
            value = posNumbers.get(0).intValue();
            posNumbers.clear();
        }
    }
    
    /**
     * Eliminates a possible number from this cell.
     */
    public void removePosNumber(Integer i)
    {
        if(posNumbers.indexOf(i)!=-1)
        {
            posNumbers.remove(i);
        }
    }
    
    /**
     * Eliminates a set of possible numbers from this cell.
     */
    public void removePosNumbers(ArrayList<Integer> is)
    {
        for(int i=0; i<is.size(); i++)
        {
            removePosNumber(is.get(i));
        }
    }
    
    /**
     * Returns the list of possible numbers in this cell.
     */
    public ArrayList<Integer> getPosNumbers()
    {
        return posNumbers;
    }
    
    public String posNumsToString()
    {
        String s = "";
        for(int i=0; i<posNumbers.size(); i++)
        {
            s+= (""+posNumbers.get(i).intValue()+" ");
        }
        return s;
    }
    
    public void draw(Graphics2D g2)
    {
        Rectangle r = new Rectangle(col*Puzzle.drawSize, row*Puzzle.drawSize, Puzzle.drawSize, Puzzle.drawSize);
        g2.setStroke(new BasicStroke(3));
        if(!selected)
        {   g2.setColor(Color.BLACK);   }
        else
        {   g2.setColor(Puzzle.stateColor);   }
        
        g2.fill(r);
        g2.setColor(Puzzle.stateColor);
        g2.draw(r);
        
        if(!selected)
        {   g2.setColor(Puzzle.stateColor);   }
        else
        {   g2.setColor(Color.BLACK);   }
        if(value>0)
        {
            //Digit III
            Font font = new Font("Helvetica", 0, 100);
            g2.setFont(font);
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(""+value, col*Puzzle.drawSize+25, row*Puzzle.drawSize+85);
        }
        if(value==0)
        {
            Font font = new Font("Helvetica", 0, 10);
            g2.setFont(font);
            FontMetrics fm = g2.getFontMetrics();
            String vals = posNumsToString();
            g2.drawString(vals, col*Puzzle.drawSize+9, row*Puzzle.drawSize+15);
        }
    }
}