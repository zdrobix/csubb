package com.example.demo;

import com.example.demo.domain.Nota;
import com.example.demo.domain.Student;
import com.example.demo.domain.Tema;
import com.example.demo.service.Service;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.function.Predicate;

public class HelloController {
    private Service service;

    @FXML
    public TextField temaStudentTextField;
    @FXML
    public TextField notaStudentTextField;
    @FXML
    public TextField nameStudentTextField;

    @FXML
    private TableView tableStudents;
    @FXML
    private TableColumn<Nota, String> columnStudent;
    @FXML
    private TableColumn<Nota, Double> columnNota;
    @FXML
    private TableColumn<Nota, String> columnTema;
    @FXML
    private ComboBox comboboxTema;

    private ObservableList<Nota> modelNote = FXCollections.observableArrayList();

    public void setService(Service service) {
        this.service = service;
        this.initModel(this.service.getNote());
        this.initialize();
    }

    private void initialize() {
        columnStudent.setCellValueFactory(data -> new SimpleObjectProperty<>(
                data.getValue().getStudent().getName()
        ));
        columnNota.setCellValueFactory(data -> new SimpleObjectProperty<>(
                data.getValue().getNota()
        ));
        columnTema.setCellValueFactory(data -> new SimpleObjectProperty<>(
                data.getValue().getTema().getDesc()
        ));

        tableStudents.setItems(modelNote);

        temaStudentTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.handleFilter();
        });
        notaStudentTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.handleFilter();
        });
        nameStudentTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.handleFilter();
        });
    }

    private void initModel(List<Nota> lista) {
        this.modelNote.setAll(
            lista
        );
        this.comboboxTema.setItems(
                this.service.getTemeUnice()
        );
    }

    public void handleFilter() {
        Predicate<Nota> p1 = new Predicate<Nota>() {
            @Override
            public boolean test(Nota nota) {
                return Double.parseDouble(notaStudentTextField.getText().trim()) >= nota.getNota();
            }
        };
        Predicate<Nota> p2 = new Predicate<Nota>() {
            @Override
            public boolean test(Nota nota) {
                return nota.getStudent().getName().toLowerCase().startsWith(notaStudentTextField.getText().trim().toLowerCase());
            }
        };
        Predicate<Nota> p3 = new Predicate<Nota>() {
            @Override
            public boolean test(Nota nota) {
                return nota.getTema().getId() == notaStudentTextField.getText();
            }
        };

    }
}