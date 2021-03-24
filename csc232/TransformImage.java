////////////////////////////////////////////////////////////////////////////////
// File:            TransformImage.java
// Course:          CSC 232 A, Spring 2021
// Authors:         Brian Howard
//
// Acknowledgments: None
//
// Online sources:  None
////////////////////////////////////////////////////////////////////////////////

package csc232;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * An implementation of the <code>Image</code> interface that will display a
 * given image after applying the specified <code>AffineTransform</code>.
 * 
 * @author bhoward
 * @see java.awt.geom.AffineTransform
 */
public class TransformImage implements Image
{
   /**
    * Constructs a <code>TransformImage</code> for the given image and
    * transform.
    * 
    * @param image
    * @param transform
    */
   public TransformImage(Image image, AffineTransform transform)
   {
      this.image = image;
      this.transform = transform;
   }

   @Override
   public void render(Graphics2D g2)
   {
      AffineTransform save = g2.getTransform();
      g2.transform(transform);
      image.render(g2);
      g2.setTransform(save);
   }

   private Image image;
   private AffineTransform transform;
}
