import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Sudoku_Solver
{
    final static JFrame frame = new JFrame();
    
    public static void main(String[] args)
    {
        frame.setBackground(Color.BLACK);
        frame.setSize(906, 928);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Sudoku Solver");
        final Puzzle puzzle = new Puzzle();
        N.makeList();
        
        class Comp extends JComponent
        {
            public void paintComponent(Graphics g)
            {
                Graphics2D g2 = (Graphics2D)g;
                puzzle.draw(g2);
            }
        }
        
        class Physics implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.repaint();
                //puzzle.logicSolve();
            }
        }
        
        class Mouse extends MouseAdapter
        {
            public void mouseClicked(MouseEvent e)
            {
                
            }
            
            public void mouseReleased(MouseEvent e)
            {
                
            }
            
            public void mousePressed(MouseEvent e)
            {
                puzzle.click(e.getX()-1, e.getY()-24);
            }
            
            
        }
        
        class MouseDrag extends MouseMotionAdapter
        {
            public void mouseDragged(MouseEvent e)
            {
                
            }
        }
        
        class Keyboard extends KeyAdapter
        {
            public void keyPressed(KeyEvent k)
            {
                if(k.getKeyCode()==KeyEvent.VK_0)
                {
                    puzzle.setValueOfSelectedCell(N.nums.get(0));
                    puzzle.resetPosNumbers();
                }
                if(k.getKeyCode()==KeyEvent.VK_1)
                {
                    puzzle.setValueOfSelectedCell(N.nums.get(1));
                }
                if(k.getKeyCode()==KeyEvent.VK_2)
                {
                    puzzle.setValueOfSelectedCell(N.nums.get(2));
                }
                if(k.getKeyCode()==KeyEvent.VK_3)
                {
                    puzzle.setValueOfSelectedCell(N.nums.get(3));
                }
                if(k.getKeyCode()==KeyEvent.VK_4)
                {
                    puzzle.setValueOfSelectedCell(N.nums.get(4));
                }
                if(k.getKeyCode()==KeyEvent.VK_5)
                {
                    puzzle.setValueOfSelectedCell(N.nums.get(5));
                }
                if(k.getKeyCode()==KeyEvent.VK_6)
                {
                    puzzle.setValueOfSelectedCell(N.nums.get(6));
                }
                if(k.getKeyCode()==KeyEvent.VK_7)
                {
                    puzzle.setValueOfSelectedCell(N.nums.get(7));
                }
                if(k.getKeyCode()==KeyEvent.VK_8)
                {
                    puzzle.setValueOfSelectedCell(N.nums.get(8));
                }
                if(k.getKeyCode()==KeyEvent.VK_9)
                {
                    puzzle.setValueOfSelectedCell(N.nums.get(9));
                }
                if(k.getKeyCode()==KeyEvent.VK_S)
                {
                    puzzle.solve();
                }
            }
            
            public void keyReleased(KeyEvent k)
            {
                
            }
            
            public void keyTyped(KeyEvent k)
            {
                
            }
        }
        
        Comp comp = new Comp();
        frame.add(comp);
        
        MouseAdapter clicker = new Mouse();
        frame.addMouseListener(clicker);
        
        KeyAdapter keyer = new Keyboard();
        frame.addKeyListener(keyer);
        
        MouseMotionListener dragger = new MouseDrag();
        frame.addMouseMotionListener(dragger);
        
        Physics time = new Physics();
        Timer t = new Timer(0, time);
        t.start();
        
        frame.repaint();
        frame.setVisible(true);
    }
}