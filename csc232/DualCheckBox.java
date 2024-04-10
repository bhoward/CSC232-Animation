////////////////////////////////////////////////////////////////////////////////
// File:            DualCheckBox.java
// Course:          CSC 232, Spring 2024
// Authors:         Brian Howard
//
// Acknowledgments: None
//
// Online sources:  None
////////////////////////////////////////////////////////////////////////////////

package csc232;

import javax.swing.JCheckBox;

/**
 * A <code>DualCheckBox</code> is a modified <code>JCheckBox</code> that will
 * display one of two labels, depending on whether the checkbox is on (selected)
 * or off.
 * 
 * @author bhoward
 */
public class DualCheckBox extends JCheckBox
{
   /**
    * Construct a <code>DualCheckBox</code> with the given pair of labels.
    * 
    * @param offLabel
    * @param onLabel
    */
   public DualCheckBox(String offLabel, String onLabel)
   {
      super(offLabel);
      this.addActionListener(event -> {
         if (this.isSelected()) {
            this.setText(onLabel);
         }
         else {
            this.setText(offLabel);
         }
      });

   }
}