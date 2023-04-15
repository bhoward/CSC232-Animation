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
      // Construct a simple animation to show off various capabilities of the
      // framework. WARNING: there are magic numbers here, and I don't care.
      Animation part1 = Animation.fixed(Image.circle()
                                             .over(Image.rectangle(0, 0, 0.5,
                                                      0.5)));
      Animation part2 = Animation.of(time -> Image.rectangle(0, 0, time, 1)
                                                  .rgb(0, 1, 0));
      Animation part3 = Animation.of(time -> Image.ellipse(0.5, 0.5, time, time)
                                                  .over(Image.square()
                                                             .rotate(time)
                                                             .hsb(time, 0.5,
                                                                      1)));
      Image text = Image.text(
               "Hello World. This is <i>some text</i> that <small><i>I <b>hope</small> will flow across multiple lines. "
                  + "Let's see if it does. "
                  + "The first thing I will do after that is check out the <b>kerning</b> and <b><i>ligatures</i></b>. "
                  + "Here is some <big>math</big>: "
                  + "<b>c</b><sub>2</sub><i>x</i><sup>2</sup>+<b>c</b><sub>1</sub><i>x</i>+<b>c</b><sub>0</sub> &lt; 0");
      Animation part4 = Animation.of(2, time -> text.rotate(-time));
      Animation part5 = Animation.of(1, time -> text.scale(1 - time));

      Animation background = Animation.of(
               time -> Image.rectangle(0, 0.2, 0.9, 0.6)
                            .hsb(0.8, 1, 1)
                            .rotate(time, 0.9, 0.8));

      Animation animation = part1.before(part2)
                                 .before(part3)
                                 .before(part4)
                                 .before(part5)
                                 .over(background);

      AnimationRunner runner = new AnimationRunner(animation);
      runner.start();
   }
}
