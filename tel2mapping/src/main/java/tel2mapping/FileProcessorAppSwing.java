package tel2mapping;

import tel2mapping.dat.LotDat;
import tel2mapping.dat.TxtOut;
import tel2mapping.dat.WaferDat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * className: FileProcessorAppGUI
 * package: tel2mapping
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2024/10/22 20:18
 */
public class FileProcessorAppSwing extends JFrame {

    private JTextField directoryPathField;
    private JTextArea statusArea;

    public FileProcessorAppSwing() {
        setTitle("Data File Processor");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        directoryPathField = new JTextField();
        directoryPathField.setMaximumSize(new Dimension(Integer.MAX_VALUE, directoryPathField.getPreferredSize().height));
        directoryPathField.setToolTipText("Enter directory path");

        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDirectoryChooser();
            }
        });

        JButton processButton = new JButton("Process Files");
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processFiles();
            }
        });

        statusArea = new JTextArea();
        statusArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(statusArea);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(directoryPathField);
        panel.add(browseButton);
        panel.add(processButton);
        panel.add(scrollPane);

        add(panel);
    }

    private void openDirectoryChooser() {
        JFileChooser directoryChooser = new JFileChooser();
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = directoryChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = directoryChooser.getSelectedFile();
            directoryPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    private void processFiles() {
        String directoryPath = directoryPathField.getText();
        if (directoryPath == null || directoryPath.isEmpty()) {
            statusArea.append("Please enter a valid directory path.\n");
            return;
        }

        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            statusArea.append("The specified path is not a directory.\n");
            return;
        }

        File[] lotFiles = directory.listFiles((dir, name) ->
                name.toLowerCase().startsWith("lot") && name.toLowerCase().endsWith(".dat"));

        if (lotFiles != null) {
            for (File lotFile : lotFiles) {
                try {
                    LotDat lotDat = processLotFile(lotFile);

                    String lotFileName = lotFile.getName();
                    Pattern pattern = Pattern.compile("lot(\\d+)\\.dat", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(lotFileName);
                    if (matcher.find()) {
                        String number = matcher.group(1);

                        File[] waferFiles = directory.listFiles((dir, name) ->
                                name.toLowerCase().matches("wafer.*" + number + "\\.dat"));
                        if (waferFiles != null) {
                            for (File waferFile : waferFiles) {
                                WaferDat waferDat = processWaferFile(waferFile);
                                TxtOut txtOut = new TxtOut();
                                txtOut.printTxt(lotDat, waferDat, waferFile);
                                statusArea.append("Processed: " + waferFile.getName() + "\n");
                            }
                        }
                    }
                } catch (IOException e) {
                    statusArea.append("Error processing file: " + lotFile.getName() + "\n");
                    e.printStackTrace();
                }
            }
        }
    }

    private LotDat processLotFile(File file) throws IOException {
        LotDat lotDat = new LotDat();
        return lotDat.read(file.getAbsolutePath());
    }

    private WaferDat processWaferFile(File file) throws IOException {
        WaferDat waferDat = new WaferDat();
        return waferDat.read(file.getAbsolutePath());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FileProcessorAppSwing().setVisible(true);
            }
        });
    }
}