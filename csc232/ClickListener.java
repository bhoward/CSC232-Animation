package csc232;

import java.awt.event.*;

public class ClickListener extends MouseAdapter implements MouseListener {
  private ActionListener al;
  
  public ClickListener(ActionListener al) {
    this.al = al;
  }
  
  public void mouseClicked(MouseEvent e) {
    al.actionPerformed(new ActionEvent(e.getSource(), e.getID(), ""));
  }
}