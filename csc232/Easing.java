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

import java.util.function.Function;

/**
 * @author bhoward
 */
public interface Easing
{
   default double in(double x)
   {
      return 1 - out(1 - x);
   }

   default double out(double x)
   {
      return 1 - in(1 - x);
   }

   default double inOut(double x)
   {
      return (x < 0.5) ? in(2 * x) / 2 : 1 - in(2 - 2 * x) / 2;
   }

   static Easing fromIn(Function<Double, Double> f)
   {
      return new Easing()
      {
         @Override
         public double in(double x)
         {
            return f.apply(x);
         }
      };
   }

   static Easing fromOut(Function<Double, Double> f)
   {
      return new Easing()
      {
         @Override
         public double out(double x)
         {
            return f.apply(x);
         }
      };
   }

   static final Easing LINEAR = fromIn(x -> x);
   
   static final Easing QUAD = fromIn(x -> x * x);
   
   static final Easing CUBIC = fromIn(x -> x * x * x);
   
   static final Easing SINE = fromOut(x -> Math.sin(x));
}
// public class Easing
// {
// private static final double C1 = 1.70158;
// private static final double C2 = C1 * 1.525;
//
// public static double backIn(double x)
// {
// return (C1 + 1) * cube(x) - C1 * square(x);
// }
//
// public static double backOut(double x)
// {
// return 1 - (C1 + 1) * cube(1 - x) + C1 * square(1 - x);
// }
//
// public static double backInOut(double x)
// {
// return (x < 0.5) ? 4 * (C2 + 1) * cube(x) - 2 * C2 * square(x)
// : 1 - 4 * (C2 + 1) * cube(1 - x) + 2 * C2 * square(1 - x);
// }
// }
