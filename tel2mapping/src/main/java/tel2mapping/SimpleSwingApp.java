package tel2mapping;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * className: SimpleSwingApp
 * package: tel2mapping
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2024/10/22 20:15
 */
public class SimpleSwingApp extends JFrame {

    private JTextField inputField;
    private JTextArea outputArea;

    public SimpleSwingApp() {
        setTitle("Simple Swing UI");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inputField = new JTextField();
        inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputField.getPreferredSize().height));
        inputField.setToolTipText("Enter some text");

        JButton actionButton = new JButton("Click Me");
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performAction();
            }
        });

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(inputField);
        panel.add(actionButton);
        panel.add(scrollPane);

        add(panel);
    }

    private void performAction() {
        String inputText = inputField.getText();
        outputArea.append("You entered: " + inputText + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimpleSwingApp().setVisible(true);
            }
        });
    }
}