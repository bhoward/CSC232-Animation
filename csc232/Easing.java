////////////////////////////////////////////////////////////////////////////////
// File:            Easing.java
// Course:          CSC 232 A, Spring 2021
// Authors:         Brian Howard
//
// Acknowledgments: None
//
// Online sources:  https://easings.net/
////////////////////////////////////////////////////////////////////////////////

package csc232;

/**
 * @author bhoward
 *
 */
public class Easing
{
   private static final double C1 = 1.70158;
   private static final double C2 = C1 * 1.525;

   private static double square(double x)
   {
      return x * x;
   }

   private static double cube(double x)
   {
      return x * x * x;
   }

   public static double linear(double x)
   {
      return x;
   }

   public static double quadIn(double x)
   {
      return square(x);
   }

   public static double quadOut(double x)
   {
      return 1 - square(1 - x);
   }

   public static double quadInOut(double x)
   {
      return (x < 0.5) ? 2 * square(x) : 1 - 2 * square(1 - x);
   }

   public static double cubicIn(double x)
   {
      return cube(x);
   }

   public static double cubicOut(double x)
   {
      return 1 - cube(1 - x);
   }

   public static double cubicInOut(double x)
   {
      return (x < 0.5) ? 4 * cube(x) : 1 - 4 * cube(1 - x);
   }

   public static double sineIn(double x)
   {
      return 1 - Math.cos(x * Math.PI / 2);
   }

   public static double sineOut(double x)
   {
      return Math.sin(x * Math.PI / 2);
   }

   public static double sineInOut(double x)
   {
      return (1 - Math.cos(Math.PI * x)) / 2;
   }

   public static double backIn(double x)
   {
      return (C1 + 1) * cube(x) - C1 * square(x);
   }

   public static double backOut(double x)
   {
      return 1 - (C1 + 1) * cube(1 - x) + C1 * square(1 - x);
   }

   public static double backInOut(double x)
   {
      return (x < 0.5) ? 4 * (C2 + 1) * cube(x) - 2 * C2 * square(x)
               : 1 - 4 * (C2 + 1) * cube(1 - x) + 2 * C2 * square(1 - x);
   }
}
