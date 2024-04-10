////////////////////////////////////////////////////////////////////////////////
// File:            Image.java
// Course:          CSC 232, Spring 2024
// Authors:         Brian Howard
//
// Acknowledgments: None
//
// Online sources:  None
////////////////////////////////////////////////////////////////////////////////

package csc232;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
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
    * Creates a new <code>Image</code> by applying the provided coordinate
    * transform. All other transformations should route through this, so that it
    * can be overridden for efficiency.
    * 
    * @param transform
    *           The desired affine transformation
    * @return a new transformed image
    */
   default Image transform(AffineTransform transform)
   {
      return new TransformImage(this, transform);
   }

   /**
    * Creates a new <code>Image</code> by rotating this image clockwise around
    * the center, (0.5, 0.5).
    * 
    * @param theta
    *           The rotation angle in radians
    * @return a new rotated image
    */
   default Image rotateRadians(double theta)
   {
      return rotateRadians(theta, 0.5, 0.5);
   }

   /**
    * Creates a new <code>Image</code> by rotating this image clockwise the
    * given fraction of a revolution (0.0 to 1.0) around the point (x, y).
    * 
    * @param revs
    *           The rotation angle in revolutions
    * @return a new rotated image
    */
   default Image rotate(double revs, double x, double y)
   {
      return rotateRadians(2 * Math.PI * revs, x, y);
   }

   /**
    * Creates a new <code>Image</code> by rotating this image clockwise around
    * the point (x, y).
    * 
    * @param theta
    *           The rotation angle in radians
    * @return a new rotated image
    */
   default Image rotateRadians(double theta, double x, double y)
   {
      return transform(AffineTransform.getRotateInstance(theta, x, y));
   }

   /**
    * Creates a new <code>Image</code> by rotating this image clockwise the
    * given fraction of a revolution (0.0 to 1.0) around the center, (0.5, 0.5).
    * 
    * @param revs
    *           The rotation angle in revolutions
    * @return a new rotated image
    */
   default Image rotate(double revs)
   {
      return rotateRadians(2 * Math.PI * revs);
   }

   /**
    * Creates a new <code>Image</code> by scaling this image in the x and y
    * directions relative to the origin, (0.0, 0.0).
    * 
    * @param scalex
    *           The scale factor in the x direction
    * @param scaley
    *           The scale factor in the y direction
    * @return a new scaled image
    */
   default Image scale(double scalex, double scaley)
   {
      // Avoid NoninvertibleTransformException
      if (scalex == 0) {
         scalex = Double.MIN_NORMAL;
      }
      if (scaley == 0) {
         scaley = Double.MIN_NORMAL;
      }
      return transform(AffineTransform.getScaleInstance(scalex, scaley));
   }

   /**
    * Creates a new <code>Image</code> by scaling this image in the x and y
    * directions relative to the origin, (0.0, 0.0).
    * 
    * @param scale
    *           The scale factor used for both directions
    * @return a new scaled image
    */
   default Image scale(double scale)
   {
      return scale(scale, scale);
   }

   /**
    * Creates a new <code>Image</code> by scaling this image in the x and y
    * directions relative to the given point.
    * 
    * @param scalex
    *           The scale factor in the x direction
    * @param scaley
    *           The scale factor in the y direction
    * @param cx
    *           The x coordinate of the center of scaling
    * @param cy
    *           The y coordinate of the center of scaling
    * @return a new scaled image
    */
   default Image scale(double scalex, double scaley, double cx, double cy)
   {
      return scale(scalex, scaley).translate(cx - cx * scalex,
               cy - cy * scaley);
   }

   /**
    * Creates a new <code>Image</code> by scaling this image in the x and y
    * directions relative to the given point.
    * 
    * @param scale
    *           The scale factor used for both directions
    * @param cx
    *           The x coordinate of the center of scaling
    * @param cy
    *           The y coordinate of the center of scaling
    * @return a new scaled image
    */
   default Image scale(double scale, double cx, double cy)
   {
      return scale(scale, scale, cx, cy);
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
      return transform(AffineTransform.getTranslateInstance(transx, transy));
   }

   /**
    * Creates a new <code>Image</code> from this one, where the default fill
    * color is given by the specified red/green/blue components. Each color
    * component is a number between 0.0 and 1.0.
    * 
    * @param red
    * @param green
    * @param blue
    * @return a new colored image
    */
   default Image rgb(double red, double green, double blue)
   {
      return new PaintImage(this,
               new Color((float) red, (float) green, (float) blue));
   }

   /**
    * Creates a new <code>Image</code> from this one, where the default fill
    * color is given by the specified hue/saturation/brightness components. Each
    * color component is a number between 0.0 and 1.0.
    * 
    * @param hue
    * @param saturation
    * @param brightness
    * @return a new colored image
    */
   default Image hsb(double hue, double saturation, double brightness)
   {
      return new PaintImage(this, Color.getHSBColor((float) hue,
               (float) saturation, (float) brightness));
   }

   /**
    * Creates a new empty <code>Image</code>.
    * 
    * @return an image with no content
    */
   static Image empty()
   {
      return new ShapeImage(new Path2D.Double());
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
    * Creates a new <code>Image</code> consisting of an ellipse with center at
    * (x, y) and the given width and height.
    * 
    * @param x
    * @param y
    * @param width
    * @param height
    * 
    * @return a new rectangle image
    */
   static Image ellipse(double x, double y, double width, double height)
   {
      return circle().scale(width, height)
                     .translate(x - width / 2, y - height / 2);
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

   /**
    * Creates a new <code>Image</code> consisting of a rectangle with upper-left
    * corner at (x, y) and the given width and height.
    * 
    * @param x
    * @param y
    * @param width
    * @param height
    * 
    * @return a new rectangle image
    */
   static Image rectangle(double x, double y, double width, double height)
   {
      return square().scale(width, height)
                     .translate(x, y);
   }

   /**
    * Creates a new <code>Image</code> consisting of a block of text centered in
    * a column 1 unit wide, broken across as many lines as needed. The text may
    * include HTML-style tags such as &lt;i&gt; (italic) and &lt;b&gt; (bold).
    * 
    * @param contents
    * @return a new text image
    */
   static Image text(String contents)
   {
      return new TextImage(contents);
   }
}
