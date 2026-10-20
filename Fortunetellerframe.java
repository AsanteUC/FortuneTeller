import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.Random;

/**
 * The main window of the Fortune Teller application.
 * <p>
 * Layout (BorderLayout):
 * NORTH  - title JLabel with a fortune teller ImageIcon
 * CENTER - JTextArea inside a JScrollPane showing every fortune read so far
 * SOUTH  - "Read My Fortune!" and "Quit" buttons
 * <p>
 * Each click on "Read My Fortune!" appends a randomly chosen fortune to the
 * text area. Fortunes may repeat over time, but never twice in a row.
 * @version 1.0
 */
public class FortuneTellerFrame extends JFrame
{
    /** The pool of humorous fortunes to choose from. */
    private static final String[] FORTUNES = {
            "You will find a parking spot right in front of the door. Then realize you're not going in.",
            "A great opportunity is coming your way. It will arrive in a mislabeled package.",
            "Your code will compile on the first try. Nobody will believe you.",
            "You will soon be asked to join a meeting that could have been an email.",
            "Someone is thinking about you. They want their charger back.",
            "The leftovers you are saving will be eaten by someone else tonight.",
            "You will win an argument today, but only with your reflection.",
            "Good news: your Wi-Fi will work perfectly. Right after you restart everything.",
            "A mysterious stranger will offer you advice. Do not take it. He is a pigeon.",
            "You will discover a lost twenty-dollar bill in an old coat. It will be Monopoly money.",
            "Your next big idea will come to you in the shower and be gone by the time you find a towel.",
            "Long-term success is in your future, but first you must find your keys.",
            "You will be very productive today, right after one more video.",
            "An unexpected gift awaits you: the last slice of pizza, cold, at 2 a.m.",
            "Beware the group project. You are the group.",
            "Your future is bright. Please wear sunglasses and also sunscreen."
    };

    private final Random rng = new Random();
    private int lastIndex = -1;   // index of the fortune shown last, -1 means none yet

    private JTextArea fortuneArea;

    // Separate fonts for the title, the buttons and the fortune display
    private final Font titleFont = new Font("Serif", Font.BOLD | Font.ITALIC, 48);
    private final Font buttonFont = new Font("SansSerif", Font.BOLD, 22);
    private final Font fortuneFont = new Font("Serif", Font.PLAIN, 24);

    /**
     * Builds the window and lays out all of the components.
     */
    public FortuneTellerFrame()
    {
        super("Fortune Teller");
        setLayout(new BorderLayout());

        add(createTopPanel(), BorderLayout.NORTH);
        add(createMiddlePanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    /**
     * Creates the top panel: a JLabel holding the title text and the image,
     * with the text placed below the image.
     *
     * @return the finished top panel
     */
    private JPanel createTopPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(45, 27, 85));

        ImageIcon icon = loadIcon("fortune.png", 220);

        JLabel titleLabel = new JLabel("Fortune Teller", icon, SwingConstants.CENTER);
        // Put the text below the image, centered under it
        titleLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        titleLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(new Color(255, 215, 120));
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        panel.add(titleLabel, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates the middle panel: a read-only, word-wrapped JTextArea inside a
     * JScrollPane.
     *
     * @return the finished middle panel
     */
    private JPanel createMiddlePanel()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 15, 10, 15));

        fortuneArea = new JTextArea();
        fortuneArea.setFont(fortuneFont);
        fortuneArea.setEditable(false);
        fortuneArea.setLineWrap(true);
        fortuneArea.setWrapStyleWord(true);
        fortuneArea.setMargin(new java.awt.Insets(8, 8, 8, 8));

        JScrollPane scrollPane = new JScrollPane(fortuneArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates the bottom panel with the "Read My Fortune!" and "Quit" buttons.
     * Both buttons use Java 8 lambda expressions as their ActionListeners.
     *
     * @return the finished bottom panel
     */
    private JPanel createBottomPanel()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));

        JButton readButton = new JButton("Read My Fortune!");
        readButton.setFont(buttonFont);
        readButton.addActionListener(e -> showNextFortune());

        JButton quitButton = new JButton("Quit");
        quitButton.setFont(buttonFont);
        quitButton.addActionListener(e -> System.exit(0));

        panel.add(readButton);
        panel.add(quitButton);
        return panel;
    }

    /**
     * Picks a random fortune that differs from the previous one and appends
     * it to the text area, then scrolls so the newest fortune is visible.
     */
    private void showNextFortune()
    {
        lastIndex = nextIndex(lastIndex, FORTUNES.length, rng);
        fortuneArea.append(FORTUNES[lastIndex] + "\n");
        fortuneArea.setCaretPosition(fortuneArea.getDocument().getLength());
    }

    /**
     * Chooses a random index in the range 0 to size - 1 that is never equal
     * to the previous index (so the same item can't appear twice in a row).
     *
     * @param previous the index chosen last time, or -1 if there was none
     * @param size     how many items there are to choose from (must be 2 or more
     *                 for the no-repeat rule to be satisfiable)
     * @param rng      the random number generator to use
     * @return a random index different from previous
     */
    static int nextIndex(int previous, int size, Random rng)
    {
        int index;
        do
        {
            index = rng.nextInt(size);
        } while (index == previous && size > 1);
        return index;
    } 

    /**
     * Loads an image file and scales it to the given height, keeping its
     * proportions. If the file can't be found, an empty icon is returned so
     * the application still runs (just without the picture).
     *
     * @param fileName the image file, relative to the project folder
     * @param height   the height in pixels to scale to
     * @return the scaled ImageIcon, or an empty ImageIcon if the file is missing
     */
    private static ImageIcon loadIcon(String fileName, int height)
    {
        ImageIcon icon = new ImageIcon(fileName);
        if (icon.getIconWidth() <= 0)
        {
            System.out.println("Could not find image file: " + fileName);
            return new ImageIcon();
        }
        Image scaled = icon.getImage().getScaledInstance(-1, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
