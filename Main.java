////////////////////////////////////////////////////////////////////////////////
// File:            Main.java
// Course:          CSC 232 A, Spring 2021
// Authors:         Brian Howard
//
// Acknowledgments: None
//
// Online sources:  None
////////////////////////////////////////////////////////////////////////////////

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

import csc232.Animation;
import csc232.AnimationComponent;
import csc232.ClickListener;
import csc232.Image;

public class Main
{
  public static void main(String[] args)
  {
    JFrame frame = new JFrame("Animation");

    // Construct a simple animation: a large circle, followed by a
    // rectangle that grows from the left to fill the window, followed
    // by a circle that grows from the center.
    // WARNING: there are magic numbers here, and I don't care.
    Animation part1 = Animation.fixed(Image.circle()
                                           .over(Image.square()
                                                      .scale(0.5, 0.5)));
    Animation part2 = new Animation(time -> Image.square()
                                                 .scale(time, 1));
    Animation part3 = new Animation(time -> Image.circle()
                                                 .scale(time, time)
                                                 .translate(0.5 - time / 2,
                                                       0.5 - time / 2));
    Animation animation = part1.before(part2)
                               .before(part3);

    final AnimationComponent view = new AnimationComponent(animation);
    view.setPreferredSize(new Dimension(350, 350));

    // Build a simple user interface around the AnimationComponent
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

    // JCheckBox demo = new DualCheckBox("No", "Yes");
    // buttons.add(demo);

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
    frame.setVisible(true);
  }
}
