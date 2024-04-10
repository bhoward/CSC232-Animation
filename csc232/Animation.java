////////////////////////////////////////////////////////////////////////////////
// File:            Animation.java
// Course:          CSC 232, Spring 2024
// Authors:         Brian Howard
//
// Acknowledgments: None
//
// Online sources:  None
////////////////////////////////////////////////////////////////////////////////

package csc232;

import java.util.function.Function;

/**
 * An <code>Animation</code> is essentially a function from time to
 * <code>Image</code>. The time parameter is assumed to run from 0.0 to 1.0. An
 * animation also has a <em>weight</em>, which affects what fraction of the
 * combined time of two animations joined with <code>before</code> will be spent
 * on this one (the fraction is computed as this weight divided by the sum of
 * the two weights).
 * 
 * @author bhoward
 */
public class Animation
{
   /**
    * Constructs an <code>Animation</code> with the given weight and timeline
    * function.
    * 
    * @param weight
    * @param timeline
    */
   public Animation(double weight, Function<Double, Image> timeline)
   {
      this.weight = weight;
      this.timeline = timeline;
   }

   /**
    * Constructs an <code>Animation</code> with weight 1.0 and the given
    * timeline function.
    * 
    * @param timeline
    */
   public Animation(Function<Double, Image> timeline)
   {
      this(1.0, timeline);
   }

   /**
    * @return the weight associated with this animation, to be used when
    *         combining two animations with <code>before</code>
    */
   public double getWeight()
   {
      return weight;
   }

   /**
    * Set the (positive) weight associated with this animation. The weight will
    * be used when combining two animations with <code>before</code>, to
    * determine what fraction of the time will be spent on each.
    * 
    * @param weight
    */
   public void setWeight(double weight)
   {
      if (weight > 0) {
         this.weight = weight;
      }
   }

   /**
    * Compute the <code>Image</code> for the frame to be displayed at the time
    * given by the progress fraction (between 0.0 and 1.0).
    * 
    * @param progress
    * @return the image to display at the specified instant
    */
   public Image getFrame(double progress)
   {
      return timeline.apply(progress);
   }

   /**
    * Creates a new <code>Animation</code> that consists of <code>this</code>
    * followed by <code>that</code>, where the relative weights of the two
    * animations will determine what fraction of the running time to spend on
    * the first before changing over to the second.
    * 
    * @param that
    * @return the new combined animation
    */
   public Animation before(Animation that)
   {
      final double changeover = weight / (weight + that.weight);
      return new Animation(weight + that.weight, time -> {
         if (time < changeover) {
            return timeline.apply(time / changeover);
         }
         else {
            return that.getFrame((time - changeover) / (1 - changeover));
         }
      });
   }

   /**
    * Creates an <code>Animation</code> with the given weight that displays a
    * fixed image for its entire duration.
    * 
    * @param weight
    * @param image
    * @return the new "fixed image" animation
    */
   public static Animation fixed(double weight, Image image)
   {
      return new Animation(weight, time -> image);
   }

   /**
    * Creates an <code>Animation</code> with weight 1.0 that displays a fixed
    * image for its entire duration.
    * 
    * @param weight
    * @param image
    * @return the new "fixed image" animation
    */
   public static Animation fixed(Image image)
   {
      return new Animation(1.0, time -> image);
   }

   private double weight;
   private Function<Double, Image> timeline;
}
