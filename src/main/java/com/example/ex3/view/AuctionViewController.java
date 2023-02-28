package com.example.ex3.view;

import com.example.ex3.viewmodel.AuctionViewModel;
import com.example.ex3.view.ViewHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.UnaryOperator;

public class AuctionViewController implements PropertyChangeListener
{

    private AuctionViewModel viewModel;
    private ViewHandler viewHandler;
    private Region root;
    @FXML
    private TextField bidInputField;
    @FXML
    private Label bidderField;
    @FXML
    private Label currentBidField;
    @FXML
    private Label errorField;
    @FXML
    private Label itemField;
    @FXML
    private Label timerField;
    @FXML
    void onPlaceBid() {
        viewModel.placeBid();
    }


    public void init(ViewHandler viewHandler, AuctionViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        viewModel.bindBidderProperty(bidderField.textProperty());
        viewModel.bindErrorProperty(errorField.textProperty());
        viewModel.bindItemProperty(itemField.textProperty());
        viewModel.bindCurrentBidProperty(currentBidField.textProperty());
        viewModel.bindTimerProperty(timerField.textProperty());
        viewModel.bindBidInputProperty(bidInputField.textProperty());
        viewModel.addPropertyChangeListener("isRed", this);
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Accept only digits
                return change;
            }
            return null; // Reject any other change
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        bidInputField.setTextFormatter(textFormatter);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if(evt.getPropertyName().equals("isRed"))
        {
            if((Boolean) evt.getNewValue())
                timerField.setStyle("-fx-text-fill: red");
            else
                timerField.setStyle("-fx-text-fill: black");
        }
    }

    public void reset()
    {
        viewModel.reset();
    }
    public Region getRoot()
    {
        return root;
    }


}
