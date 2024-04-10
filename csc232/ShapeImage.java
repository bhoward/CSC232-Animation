////////////////////////////////////////////////////////////////////////////////
// File:            ShapeImage.java
// Course:          CSC 232, Spring 2024
// Authors:         Brian Howard
//
// Acknowledgments: None
//
// Online sources:  None
////////////////////////////////////////////////////////////////////////////////

package csc232;

import java.awt.Graphics2D;
import java.awt.Shape;

/**
 * An implementation of the <code>Image</code> interface that will draw a given
 * <code>Shape</code>.
 * 
 * @author bhoward
 * @see java.awt.Shape
 */
public class ShapeImage implements Image
{
   /**
    * Constructs a <code>ShapeImage</code> that will display the given shape.
    * 
    * @param shape
    */
   public ShapeImage(Shape shape)
   {
      this.shape = shape;
   }

   @Override
   public void render(Graphics2D g2)
   {
      g2.fill(shape);
      g2.draw(shape);
   }

   private Shape shape;
}
