////////////////////////////////////////////////////////////////////////////////
// File:            TextImage.java
// Course:          CSC 232, Spring 2024
// Authors:         Brian Howard
//
// Acknowledgments: None
//
// Online sources:  None
////////////////////////////////////////////////////////////////////////////////

package csc232;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.font.TransformAttribute;
import java.awt.geom.AffineTransform;
import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author bhoward
 *
 */
public class TextImage implements Image
{
   private static final int DEFAULT_SIZE = 24;

   private AttributedString text;

   /**
    * @param contents
    */
   public TextImage(String contents)
   {
      this.text = processAttributes(contents);;
   }

   @Override
   public void render(Graphics2D graphics)
   {
      // Based on code from
      // https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/font/LineBreakMeasurer.html
      float width = 400f;
      float height = 400f;

      float y = 0f;

      AttributedCharacterIterator paragraph = text.getIterator();

      FontRenderContext frc = graphics.getFontRenderContext();

      LineBreakMeasurer measurer = new LineBreakMeasurer(paragraph, frc);
      measurer.setPosition(paragraph.getBeginIndex());

      List<Line> lines = new ArrayList<>();
      while (measurer.getPosition() < paragraph.getEndIndex()) {
         TextLayout layout = measurer.nextLayout(0.99f * width);

         // Compute x and height from top of current layout's baseline
         float x = (width - layout.getAdvance()) / 2; // centered
         y += (layout.getAscent());

         lines.add(new Line(layout, x, y));
         y += layout.getDescent() + layout.getLeading();
      }

      // Use height to center vertically and draw each line in a 400x400 box
      AffineTransform save = graphics.getTransform();
      graphics.scale(1 / width, 1 / height);
      float top = (height - y) / 2;
      for (Line line : lines) {
         line.layout.draw(graphics, line.x, line.y + top);
      }
      graphics.setTransform(save);
   }

   private static AttributedString processAttributes(String contents)
   {
      StringBuilder builder = new StringBuilder();
      Stack<Tag> tags = new Stack<>();
      List<AttributeItem> items = new ArrayList<>();

      int left = 0;
      int right = contents.indexOf('<', left);
      while (right != -1) {
         builder.append(expandEntities(contents.substring(left, right)));
         left = right;
         right = contents.indexOf('>', left);
         if (right == -1) {
            // No closing >, so treat as text
            builder.append(expandEntities(contents.substring(left, contents.length())));
            left = contents.length();
         }
         else {
            String tagName = contents.substring(left + 1, right);
            if (tagName.startsWith("/")) {
               tagName = tagName.substring(1);
               while (!tags.isEmpty()) {
                  Tag tag = tags.pop();
                  items.add(new AttributeItem(tag.tagName, tag.position,
                           builder.length()));
                  if (tagName.equals(tag.tagName))
                     break;
               }
            }
            else {
               tags.push(new Tag(tagName, builder.length()));
            }
            left = right + 1;
            right = contents.indexOf('<', left);
         }
      }
      builder.append(expandEntities(contents.substring(left, contents.length())));
      while (!tags.isEmpty()) {
         Tag tag = tags.pop();
         items.add(new AttributeItem(tag.tagName, tag.position,
                  builder.length()));
      }

      AttributedString text = new AttributedString(builder.toString());
      
      // text.addAttribute(TextAttribute.FAMILY, Font.SANS_SERIF);
      text.addAttribute(TextAttribute.FAMILY, Font.SERIF);
      text.addAttribute(TextAttribute.SIZE, DEFAULT_SIZE);
      text.addAttribute(TextAttribute.KERNING, TextAttribute.KERNING_ON);
      text.addAttribute(TextAttribute.LIGATURES, TextAttribute.LIGATURES_ON);

      for (AttributeItem item : items) {
         text.addAttribute(item.attribute, item.value, item.begin, item.end);
      }
      
      return text;
   }

   private static Object expandEntities(String s)
   {
      s = s.replaceAll("&lt;", "<");
      s = s.replaceAll("&gt;", ">");
      s = s.replaceAll("&amp;", "&");
      return s;
   }

   private static class Tag
   {
      public Tag(String tagName, int position)
      {
         this.tagName = tagName;
         this.position = position;
      }

      public String tagName;
      public int position;
   }

   private static class AttributeItem
   {
      public AttributeItem(String tagName, int begin, int end)
      {
         switch (tagName) {
         case "b":
            this.attribute = TextAttribute.WEIGHT;
            this.value = TextAttribute.WEIGHT_BOLD;
            break;
         case "i":
            this.attribute = TextAttribute.POSTURE;
            this.value = TextAttribute.POSTURE_OBLIQUE;
            break;
         case "sup":
            this.attribute = TextAttribute.SUPERSCRIPT;
            this.value = TextAttribute.SUPERSCRIPT_SUPER;
            break;
         case "sub":
            this.attribute = TextAttribute.SUPERSCRIPT;
            this.value = TextAttribute.SUPERSCRIPT_SUB;
            break;
         case "big":
            this.attribute = TextAttribute.SIZE;
            this.value = DEFAULT_SIZE * 1.2;
            break;
         case "small":
            this.attribute = TextAttribute.SIZE;
            this.value = DEFAULT_SIZE * 0.8;
            break;
         // TODO other tags?
         default:
            this.attribute = TextAttribute.TRANSFORM;
            this.value = TransformAttribute.IDENTITY;
         }
         this.begin = begin;
         this.end = end;
      }

      public Attribute attribute;
      public Object value;
      public int begin;
      public int end;
   }

   private static class Line
   {
      public Line(TextLayout layout, float x, float y)
      {
         this.layout = layout;
         this.x = x;
         this.y = y;
      }

      public TextLayout layout;
      public float x;
      public float y;
   }
}
