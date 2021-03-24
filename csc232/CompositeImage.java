////////////////////////////////////////////////////////////////////////////////
// File:            CompositeImage.java
// Course:          CSC 232 A, Spring 2021
// Authors:         Brian Howard
//
// Acknowledgments: None
//
// Online sources:  None
////////////////////////////////////////////////////////////////////////////////

package csc232;

import java.awt.Graphics2D;

/**
 * An implementation of the <code>Image</code> interface that overlays a
 * sequence of images.
 * 
 * @author bhoward
 */
public class CompositeImage implements Image
{
   /**
    * Constructs a <code>CompositeImage</code> containing the given images, with
    * the bottom-most first.
    * 
    * @param images
    */
   public CompositeImage(Image... images)
   {
      this.images = images;
   }

   @Override
   public void render(Graphics2D g2)
   {
      for (Image image : images) {
         image.render(g2);
      }
   }

   private Image[] images;
}
