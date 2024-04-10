import javax.swing.JCheckBox;

public class DualCheckBox extends JCheckBox {
  public DualCheckBox(String offLabel, String onLabel) {
    super(offLabel);
    this.addActionListener(
        event -> {
          if (this.isSelected()) {
            this.setText(onLabel);
          } else {
            this.setText(offLabel);
          }
        }
      );

  }
}