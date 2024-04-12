////////////////////////////////////////////////////////////////////////////////
// File:            Main.java
// Course:          CSC 232, Spring 2024
// Authors:         Brian Howard
//
// Acknowledgments: None
//
// Online sources:  None
////////////////////////////////////////////////////////////////////////////////

import java.util.function.Function;

import csc232.Animation;
import csc232.AnimationRunner;
import csc232.Easing;
import csc232.Image;

public class Main
{
   public static void main(String[] args)
   {
      // Construct a simple animation to show off various capabilities of the
      // framework. WARNING: there are magic numbers here, and I don't care.
      Animation part1 = Animation.fixed(Image.ellipse(0, 0, 0.5, 0.5)
                                             .rgb(1, 0, 0)
                                             .over(Image.rectangle(0, 0, 0.5, 0.5)));
      Animation part2 = Animation.of(time -> Image.rectangle(0, 0, time, 1)
                                                  .rgb(0, 1, 0));
      Animation part3 = Animation.of(
               time -> Image.ellipseAt(0.5, 0.5, time, time)
                            .over(Image.square()
                                       .rotate(time)
                                       .hsb(time, 0.5, 1)));
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

      // AnimationRunner runner = new AnimationRunner(animation);

      Animation easingDemo = Animation.of(helperIn(Easing.LINEAR, "Linear"))
                                      .before(Animation.of(helperIn(Easing.QUAD,
                                               "Quad In")))
                                      .before(Animation.of(helperIn(Easing.SINE,
                                               "Sine In")))
                                      .before(Animation.of(helperIn(
                                               Easing.CUBIC, "Cubic In")))
                                      .before(Animation.of(helperIn(Easing.BACK,
                                               "Back In")))
                                      .before(Animation.of(helperOut(
                                               Easing.QUAD, "Quad Out")))
                                      .before(Animation.of(helperOut(
                                               Easing.SINE, "Sine Out")))
                                      .before(Animation.of(helperOut(
                                               Easing.CUBIC, "Cubic Out")))
                                      .before(Animation.of(helperOut(
                                               Easing.BACK, "Back Out")))
                                      .before(Animation.of(helperInOut(
                                               Easing.QUAD, "Quad InOut")))
                                      .before(Animation.of(helperInOut(
                                               Easing.SINE, "Sine InOut")))
                                      .before(Animation.of(helperInOut(
                                               Easing.CUBIC, "Cubic InOut")))
                                      .before(Animation.of(helperInOut(
                                               Easing.BACK, "Back InOut")));

      AnimationRunner runner = new AnimationRunner(
               animation.before(easingDemo));

      runner.start();
   }

   private static Function<Double, Image> helperIn(Easing easing, String name)
   {
      return time -> Image.ellipseAt(time, easing.in(time), 0.1, 0.1)
                          .over(Image.ellipseAt(1, easing.in(time), 0.1, 0.1)
                                     .rgb(1, 0, 0))
                          .over(Image.text(name))
                          .scale(0.9, 0.5, 0.5);
   }

   private static Function<Double, Image> helperOut(Easing easing, String name)
   {
      return time -> Image.ellipseAt(time, easing.out(time), 0.1, 0.1)
                          .over(Image.ellipseAt(1, easing.out(time), 0.1, 0.1)
                                     .rgb(1, 0, 0))
                          .over(Image.text(name))
                          .scale(0.9, 0.5, 0.5);
   }

   private static Function<Double, Image> helperInOut(Easing easing,
            String name)
   {
      return time -> Image.ellipseAt(time, easing.inOut(time), 0.1, 0.1)
                          .over(Image.ellipseAt(1, easing.inOut(time), 0.1, 0.1)
                                     .rgb(1, 0, 0))
                          .over(Image.text(name))
                          .scale(0.9, 0.5, 0.5);
   }
}
