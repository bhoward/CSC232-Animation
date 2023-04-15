////////////////////////////////////////////////////////////////////////////////
// File:            AnimationComponent.java
// Course:          CSC 232 A, Spring 2021
// Authors:         Brian Howard
//
// Acknowledgments: None
//
// Online sources:  None
////////////////////////////////////////////////////////////////////////////////

package csc232;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * A Swing GUI component that can display an <code>Animation</code>.
 * 
 * @author bhoward
 */
public class AnimationComponent extends JComponent
{
   /**
    * Constructs an <code>AnimationComponent</code> to display a given
    * <code>Animation</code>.
    * 
    * @param animation
    */
   public AnimationComponent(Animation animation)
   {
      this.animation = animation;
      this.duration = DEFAULT_DURATION;
      this.loop = false;
      this.progress = 0.0;
      this.lastEvent = 0;
      this.timer = new Timer(DELAY, this::handleTimer);
      this.runStateListeners = new ArrayList<>();
      this.progressListeners = new ArrayList<>();
   }

   /**
    * @return the duration (in milliseconds) over which one cycle of the
    *         animation will be played
    */
   public int getDuration()
   {
      return duration;
   }

   /**
    * Sets the duration over which one cycle of the animation will be played. If
    * a duration is chosen shorter than the delay for one frame, then the
    * duration will be set to the time for a single frame.
    * 
    * @param duration
    *           the length of one cycle in milliseconds
    */
   public void setDuration(int duration)
   {
      if (duration >= DELAY) {
         this.duration = duration;
      }
      else {
         this.duration = DELAY;
      }
   }

   /**
    * @return true if the animation will loop back to the beginning
    */
   public boolean isLoop()
   {
      return loop;
   }

   /**
    * Sets whether the animation should cycle back to the beginning after
    * reaching the end.
    * 
    * @param loop
    *           <code>true</code> if the animation should cycle indefinitely
    */
   public void setLoop(boolean loop)
   {
      this.loop = loop;
   }

   /**
    * @return the progress through the animation cycle as a number from 0.0 to
    *         1.0
    */
   public double getProgress()
   {
      return progress;
   }

   /**
    * Set the fractional progress through the animation cycle.
    * 
    * @param progress
    *           a number between 0.0 (beginning) and 1.0 (end)
    */
   public void setProgress(double progress)
   {
      this.progress = progress;
      repaint();
   }

   /**
    * Start the animation at the beginning (time 0.0 on the timeline).
    */
   public void start()
   {
      progress = 0.0;
      lastEvent = System.currentTimeMillis();
      timer.start();
      notifyRunStateListeners();
   }

   /**
    * Start the animation at the position where it was last stopped.
    */
   public void resume()
   {
      lastEvent = System.currentTimeMillis();
      timer.start();
      notifyRunStateListeners();
   }

   /**
    * Stop playing the animation.
    */
   public void stop()
   {
      timer.stop();
      notifyRunStateListeners();
   }

   /**
    * @return <code>true</code> if the animation is currently playing
    */
   public boolean isRunning()
   {
      return timer.isRunning();
   }

   /**
    * @return <code>true</code> if the animation was stopped before reaching the
    *         end
    */
   public boolean isPaused()
   {
      return !timer.isRunning() && (progress < 1.0 || loop);
   }

   /**
    * Adds an <code>ActionListener</code> that will be notified when the
    * animation is either started or stopped.
    * 
    * @param listener
    */
   public void addRunStateListener(ActionListener listener)
   {
      runStateListeners.add(listener);
   }

   private void notifyRunStateListeners()
   {
      ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "");
      for (ActionListener listener : runStateListeners) {
         listener.actionPerformed(e);
      }
   }

   /**
    * Adds an <code>ActionListener</code> that will be notified of the progress
    * of the animation.
    * 
    * @param listener
    */
   public void addProgressListener(ActionListener listener)
   {
      progressListeners.add(listener);
   }

   private void notifyProgressListeners()
   {
      ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "");
      for (ActionListener listener : progressListeners) {
         listener.actionPerformed(e);
      }
   }

   private void handleTimer(ActionEvent event)
   {
      long now = System.currentTimeMillis();
      long elapsed = now - lastEvent;

      lastEvent = now;
      progress += (double) elapsed / duration;
      if (progress > 1.0) {
         if (loop) {
            progress %= 1.0;
         }
         else {
            progress = 1.0;
            timer.stop();
            notifyRunStateListeners();
         }
      }
      notifyProgressListeners();
      repaint();
   }

   @Override
   protected void paintComponent(Graphics g)
   {
      super.paintComponent(g);

      Graphics2D g2 = (Graphics2D) g;
      g2.setPaint(Color.BLUE);
      g2.setStroke(new BasicStroke(0.01f));
      g2.scale(this.getWidth(), this.getHeight());
      Image frame = animation.getFrame(progress);
      frame.render(g2);
   }

   private Animation animation;
   private int duration;
   private boolean loop;
   private double progress;
   private long lastEvent;
   private Timer timer;
   private List<ActionListener> runStateListeners;
   private List<ActionListener> progressListeners;

   private static final int DELAY = 20; // 50 frames per second goal
   private static final int DEFAULT_DURATION = 10000; // 10 seconds
}
