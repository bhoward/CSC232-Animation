////////////////////////////////////////////////////////////////////////////////
// File:            ColorImage.java
// Course:          CSC 232, Spring 2024
// Authors:         Brian Howard
//
// Acknowledgments: None
//
// Online sources:  None
////////////////////////////////////////////////////////////////////////////////

package csc232;

import java.awt.Graphics2D;
import java.awt.Paint;

/**
 * @author bhoward
 */
public class PaintImage implements Image
{
   /**
    * @param image
    * @param paint
    */
   public PaintImage(Image image, Paint paint)
   {
      this.image = image;
      this.paint = paint;
   }

   @Override
   public void render(Graphics2D graphics)
   {
      Paint save = graphics.getPaint();
      graphics.setPaint(paint);
      image.render(graphics);
      graphics.setPaint(save);
   }

   private Image image;
   private Paint paint;
}
