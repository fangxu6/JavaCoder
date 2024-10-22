package tel2mapping;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import tel2mapping.dat.LotDat;
import tel2mapping.dat.TxtOut;
import tel2mapping.dat.WaferDat;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * className: FileProcessorApp
 * package: tel2mapping
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2024/10/22 19:10
 */
public class FileProcessorApp extends Application {

    private TextField directoryPathField;
    private TextArea statusArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Data File Processor");

        directoryPathField = new TextField();
        directoryPathField.setPromptText("Enter directory path");

        Button browseButton = new Button("Browse");
        browseButton.setOnAction(e -> openDirectoryChooser(primaryStage));

        Button processButton = new Button("Process Files");
        processButton.setOnAction(e -> processFiles());

        statusArea = new TextArea();
        statusArea.setEditable(false);

        VBox layout = new VBox(10, directoryPathField, browseButton, processButton, statusArea);
        Scene scene = new Scene(layout, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openDirectoryChooser(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            directoryPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    private void processFiles() {
        String directoryPath = directoryPathField.getText();
        if (directoryPath == null || directoryPath.isEmpty()) {
            statusArea.appendText("Please enter a valid directory path.\n");
            return;
        }

        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            statusArea.appendText("The specified path is not a directory.\n");
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
                                statusArea.appendText("Processed: " + waferFile.getName() + "\n");
                            }
                        }
                    }
                } catch (IOException e) {
                    statusArea.appendText("Error processing file: " + lotFile.getName() + "\n");
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
}