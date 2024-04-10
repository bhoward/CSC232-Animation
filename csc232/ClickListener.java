////////////////////////////////////////////////////////////////////////////////
// File:            ClickListener.java
// Course:          CSC 232, Spring 2024
// Authors:         Brian Howard
//
// Acknowledgments: None
//
// Online sources:  None
////////////////////////////////////////////////////////////////////////////////

package csc232;

import java.awt.event.*;

/**
 * A <code>ClickListener</code> is an implementation of the
 * <code>MouseListener</code> interface that allows a mouse click to trigger an
 * action, just like for a <code>JButton</code>.
 * 
 * @author bhoward
 */
public class ClickListener extends MouseAdapter implements MouseListener
{
   private ActionListener al;

   /**
    * Construct a <code>ClickListener</code> with the given
    * <code>ActionListener</code>.
    * 
    * @param al
    */
   public ClickListener(ActionListener al)
   {
      this.al = al;
   }

   @Override
   public void mouseClicked(MouseEvent e)
   {
      al.actionPerformed(new ActionEvent(e.getSource(), e.getID(), ""));
   }
}