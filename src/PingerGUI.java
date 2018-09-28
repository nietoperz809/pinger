import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PingerGUI extends JDialog
{
    private JPanel contentPane;
    private JLabel lab1;
    private JCheckBox _check = new JCheckBox ("Play Sound");
    private Pinger _pinger;

    public PingerGUI ()
    {
        $$$setupUI$$$();
        setContentPane(contentPane);
        setModal(true);
        setTitle("Internet Monitor");

        lab1.setOpaque(true);

        _pinger = new Pinger("yahoo.com", (yesno, timespan) ->
        {
            if (yesno == InetState.OPEN)
            {
                lab1.setText("<html>Inet available<br>since " + timespan + " seconds.</html>");
                lab1.setBackground(Color.GREEN);
            }
            else if (yesno == InetState.CLOSED)
            {
                lab1.setText("<html>No Inet<br>since " + timespan + " seconds.</html>");
                lab1.setBackground(Color.RED);
            }
        });

        // Sound on
        _check.setSelected(true);
        _pinger.setToneEnabled(true);
        
        _check.addActionListener(e ->
        {
            Utils.playWaveFromResource("cpckey.wav");
            _pinger.setToneEnabled(_check.isSelected());
        });
    }

    public static void main (String[] args)
    {
        PingerGUI dialog = new PingerGUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents ()
    {
        // TODO: place custom component creation code here
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$ ()
    {
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(10, 10));
        contentPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-4473925)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, 24, contentPane.getFont())));
        lab1 = new JLabel();
        Font lab1Font = this.$$$getFont$$$(null, Font.BOLD, 20, lab1.getFont());
        if (lab1Font != null)
        {
            lab1.setFont(lab1Font);
        }
        lab1.setHorizontalAlignment(0);
        lab1.setHorizontalTextPosition(0);
        lab1.setMaximumSize(new Dimension(200, 200));
        lab1.setMinimumSize(new Dimension(200, 200));
        lab1.setPreferredSize(new Dimension(200, 200));
        lab1.setRequestFocusEnabled(true);
        lab1.setText("Please wait ...");

        contentPane.add (_check, BorderLayout.NORTH);
        contentPane.add(lab1, BorderLayout.CENTER);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$ (String fontName, int style, int size, Font currentFont)
    {
        if (currentFont == null)
        {
            return null;
        }
        String resultName;
        if (fontName == null)
        {
            resultName = currentFont.getName();
        }
        else
        {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1'))
            {
                resultName = fontName;
            }
            else
            {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$ ()
    {
        return contentPane;
    }
}
