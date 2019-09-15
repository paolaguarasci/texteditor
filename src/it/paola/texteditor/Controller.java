package it.paola.texteditor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;


public class Controller {

  @FXML
  MenuItem newMenuItem;
  @FXML
  MenuItem saveMenuItem;
  @FXML
  MenuItem openMenuItem;
  @FXML
  MenuItem closeMenuItem;
  
  @FXML
  MenuItem aboutMenuItem;
  
  @FXML
  TabPane tabPane;
  private Stage stage;
  
  private HashMap<Tab, File> tab2file;
  
  public void init(Stage stage) {
    this.stage = stage;
    tab2file = new HashMap<Tab, File>();
  }
  public  void handleNew(){
    tabPane.setVisible(true);
    tabPane.getTabs().add(makeNewTab());
  }
  
  private  Tab makeNewTab() {
    tabPane.setVisible(true);
    Tab nuova = new Tab("Senza Nome ");
    TextArea areaDiTesto = new TextArea();
    nuova.setContent(areaDiTesto);
    nuova.setClosable(true);
    areaDiTesto.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable,
                          String oldValue, String newValue) {
        nuova.setText(nuova.getText() + "*");

      }
    });
    return nuova;
  }
  
  public  void handleClose(){
   stage.close();
  }
  
  public  void handleSave() {
    int index = tabPane.getSelectionModel().getSelectedIndex();
    Tab selectTab = tabPane.getTabs().get(index);
    TextArea selectTextArea = ((TextArea) selectTab.getContent());
    System.out.println("Tab selezionata: " + index);
    String testo = selectTextArea.getText();
    File file = tab2file.get(selectTab);
    
    System.out.println(file);
    
    if (file == null) {
      file = viewFileChooser(true);
      selectTab.setText(file.getName());
      tab2file.remove(selectTab);
      tab2file.put(selectTab, file);
    }
    System.out.println("Tab aperte: " + tab2file.size());
    save(testo, file);
  }
  
  private  void save(String content, File file){
    try {
      FileWriter fileWriter;
      fileWriter = new FileWriter(file);
      fileWriter.write(content);
      fileWriter.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
  
  public  void handleOpen(){
    Tab selectTab = makeNewTab();
    TextArea testo = ((TextArea) selectTab.getContent());
    File file = viewFileChooser(false);
    tab2file.put(selectTab, file);
    selectTab.setText(file.getName());
    testo.setText(open(file));
    tabPane.getTabs().add(selectTab);
    
  }
  
  private  String open(File fileDaAprire){
    StringBuilder str = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader(fileDaAprire))) {
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
        str.append(line);
        str.append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return str.toString();
  }
  private  File viewFileChooser(boolean save){
    FileChooser fc = new FileChooser();
    File tmp;
    
    if(save) {
      tmp = fc.showSaveDialog(null);
    } else {
      tmp = fc.showOpenDialog(null);
    }
    
    return tmp;
  }
  public  void handleAbout() {
    Alert about = new Alert(Alert.AlertType.INFORMATION);
    about.setHeaderText(null);
    about.setTitle("About");
    about.setContentText("Programma sviluppato per il corso di IGPE");
    about.show();
  }
  
  public  void handleChange() {
  
  }
  
}
