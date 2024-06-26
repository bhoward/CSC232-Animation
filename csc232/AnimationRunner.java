////////////////////////////////////////////////////////////////////////////////
// File:            AnimationRunner.java
// Course:          CSC 232, Spring 2024
// Authors:         Brian Howard
//
// Acknowledgments: None
//
// Online sources:  None
////////////////////////////////////////////////////////////////////////////////

package csc232;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

/**
 * A simple user interface to go around an <code>AnimationComponent</code>.
 * 
 * @author bhoward
 */
public class AnimationRunner
{
   private JFrame frame;

   /**
    * Construct a UI to display the given <code>Animation</code>.
    * 
    * @param animation
    */
   public AnimationRunner(Animation animation)
   {
      final AnimationComponent view = new AnimationComponent(animation);
      view.setPreferredSize(new Dimension(350, 350));

      frame = new JFrame("Animation");
      frame.setLayout(new BorderLayout());
      frame.add(view, BorderLayout.CENTER);

      Box buttons = Box.createHorizontalBox();

      JButton startButton = new JButton("Start");
      startButton.addActionListener(event -> view.start());
      buttons.add(startButton);

      JButton stopResumeButton = new JButton("Resume");
      stopResumeButton.addActionListener(event -> {
         if (view.isRunning()) {
            view.stop();
         }
         else {
            view.resume();
         }
      });
      stopResumeButton.setEnabled(false); // initially disabled
      buttons.add(stopResumeButton);

      view.addMouseListener(
        new ClickListener(stopResumeButton.getActionListeners()[0]));
     
      JButton slowerButton = new JButton("Slower");
      slowerButton.addActionListener(
               event -> view.setDuration(view.getDuration() * 6 / 5));
      buttons.add(slowerButton);

      JButton fasterButton = new JButton("Faster");
      fasterButton.addActionListener(
               event -> view.setDuration(view.getDuration() * 5 / 6));
      buttons.add(fasterButton);

      JCheckBox loopButton = new DualCheckBox("Once", "Loop");
      loopButton.addActionListener(
               event -> view.setLoop(loopButton.isSelected()));
      buttons.add(loopButton);

      frame.add(buttons, BorderLayout.SOUTH);

      view.addRunStateListener(event -> {
         if (view.isRunning()) {
            startButton.setEnabled(false);
            stopResumeButton.setText("Stop");
            stopResumeButton.setEnabled(true);
         }
         else {
            startButton.setEnabled(true);
            stopResumeButton.setText("Resume");
            stopResumeButton.setEnabled(view.isPaused());
         }
      });

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
   }

   /**
    * Start the UI running.
    */
   public void start()
   {
      frame.setVisible(true);
   }
}
