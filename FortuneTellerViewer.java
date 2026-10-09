import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Main class for the Fortune Teller application. It only creates an instance
 * of FortuneTellerFrame and sets the frame's stats: size (3/4 of the screen),
 * centered position, close behavior and visibility.
 *
 * @author Your Name
 * @version 1.0
 */
public class FortuneTellerViewer
{
    /**
     * Program entry point.
     *
     * @param args command line arguments, unused
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            FortuneTellerFrame frame = new FortuneTellerFrame();

            Toolkit kit = Toolkit.getDefaultToolkit();
            Dimension screen = kit.getScreenSize();
            int width = screen.width * 3 / 4;
            int height = screen.height * 3 / 4;

            frame.setSize(width, height);
            frame.setLocation((screen.width - width) / 2, (screen.height - height) / 2);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
