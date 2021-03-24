////////////////////////////////////////////////////////////////////////////////
// File:            Image.java
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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * An <code>Image</code> represents anything that can be drawn in a rectangular
 * region. The x and y coordinates of the region go from (0., 0.) in the
 * upper-left to (1., 1.) in the lower-right.
 * 
 * @author bhoward
 */
public interface Image
{
   /**
    * Draws the image on the provided graphics context.
    * 
    * @param graphics
    *           The <code>Graphics2D</code> context object with which to do the
    *           drawing
    */
   void render(Graphics2D graphics);

   /**
    * Creates a new <code>Image</code> by composing this image on top of
    * another.
    * 
    * @param that
    *           The <code>Image</code> to draw under this one
    * @return a new composite image
    */
   default Image over(Image that)
   {
      return new CompositeImage(that, this);
   }

   /**
    * Creates a new <code>Image</code> by rotating this image clockwise around
    * the origin.
    * 
    * @param theta
    *           The rotation angle in radians
    * @return a new rotated image
    */
   default Image rotate(double theta)
   {
      return new TransformImage(this, AffineTransform.getRotateInstance(theta));
   }

   /**
    * Creates a new <code>Image</code> by scaling this image in the x and y
    * directions.
    * 
    * @param scalex
    *           The scale factor in the x direction
    * @param scaley
    *           The scale factor in the y direction
    * @return a new scaled image
    */
   default Image scale(double scalex, double scaley)
   {
      return new TransformImage(this,
               AffineTransform.getScaleInstance(scalex, scaley));
   }

   /**
    * Creates a new <code>Image</code> by translating this image. The origin of
    * this image will be shifted to the given point (transx, transy).
    * 
    * @param transx
    *           The distance to translate in the x direction
    * @param transy
    *           The distance to translate in the y direction
    * @return a new translated image
    */
   default Image translate(double transx, double transy)
   {
      return new TransformImage(this,
               AffineTransform.getTranslateInstance(transx, transy));
   }

   /**
    * Creates a new <code>Image</code> consisting of a circle with diameter 1,
    * centered at (0.5, 0.5).
    * 
    * @return a new circle image
    */
   static Image circle()
   {
      return new ShapeImage(new Ellipse2D.Double(0, 0, 1, 1));
   }

   /**
    * Creates a new <code>Image</code> consisting of a square with side 1 (so it
    * will completely fill the viewing region unless it is transformed).
    * 
    * @return a new square image
    */
   static Image square()
   {
      return new ShapeImage(new Rectangle2D.Double(0, 0, 1, 1));
   }
}
