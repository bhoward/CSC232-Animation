////////////////////////////////////////////////////////////////////////////////
// File:            Easing.java
// Course:          CSC 232, Spring 2024
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
   
   static final Easing SINE = fromOut(x -> Math.sin(x * Math.PI / 2));
   
   static final Easing BACK = fromIn(x -> 2.70158 * x * x * x - 1.70158 * x * x);
}

