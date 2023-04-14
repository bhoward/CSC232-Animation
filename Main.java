////////////////////////////////////////////////////////////////////////////////
// File:            Main.java
// Course:          CSC 232 A, Spring 2021
// Authors:         Brian Howard
//
// Acknowledgments: None
//
// Online sources:  None
////////////////////////////////////////////////////////////////////////////////

import csc232.Animation;
import csc232.AnimationRunner;
import csc232.Image;

public class Main
{
   public static void main(String[] args)
   {
      // Construct a simple animation: a large circle over an upper-left corner
      // square, followed by a rectangle that grows from the left to fill the
      // window, followed by a circle that grows from the center.
      // WARNING: there are magic numbers here, and I don't care.
      Animation part1 = Animation.fixed(Image.circle()
                                             .over(Image.rectangle(0, 0, 0.5,
                                                      0.5)));
      Animation part2 = new Animation(time -> Image.rectangle(0, 0, time, 1));
      Animation part3 = new Animation(
               time -> Image.ellipse(0.5, 0.5, time, time));
      Animation animation = part1.before(part2)
                                 .before(part3);

      AnimationRunner runner = new AnimationRunner(animation);
      runner.start();
   }
}
