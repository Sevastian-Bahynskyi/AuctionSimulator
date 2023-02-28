package com.example.ex3.viewmodel;

import com.example.ex3.model.AuctionModel;
import com.example.ex3.model.AuctionModelManager;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.ParseException;

public class AuctionViewModel implements PropertyChangeListener
{
    private AuctionModel model;
    private StringProperty itemProperty;
    private StringProperty bidderProperty;
    private StringProperty timerProperty;
    private StringProperty currentBidProperty;
    private StringProperty errorProperty;
    private StringProperty bidInputProperty;
    private PropertyChangeSupport support;

    public AuctionViewModel(AuctionModel model)
    {
        this.model = model;
        this.bidderProperty = new SimpleStringProperty();
        this.itemProperty = new SimpleStringProperty();
        this.timerProperty = new SimpleStringProperty();
        this.currentBidProperty = new SimpleStringProperty();
        this.errorProperty = new SimpleStringProperty("");
        this.bidInputProperty = new SimpleStringProperty();
        support = new PropertyChangeSupport(this);
        model.addPropertyChangeListener("timer", this);
        model.addPropertyChangeListener("end", this);
        model.addPropertyChangeListener("bid", this);
        model.addPropertyChangeListener("Bid was not placed", this);
    }

    public void bindItemProperty(StringProperty property)
    {
        itemProperty.bindBidirectional(property);
    }

    public void bindBidderProperty(StringProperty property)
    {
        bidderProperty.bindBidirectional(property);
    }

    public void bindBidInputProperty(StringProperty property)
    {
        bidInputProperty.bindBidirectional(property);
    }

    public void bindTimerProperty(StringProperty property)
    {
        timerProperty.bindBidirectional(property);
    }

    public void bindCurrentBidProperty(StringProperty property)
    {
        currentBidProperty.bindBidirectional(property);
    }

    public void bindErrorProperty(StringProperty property)
    {
        errorProperty.bindBidirectional(property);
    }

    public void reset()
    {
        itemProperty.set(model.getCurrentAuctionItem().getItem());
        currentBidProperty.set(String.valueOf(model.getCurrentBid()));
        bidderProperty.set(String.valueOf(model.getCustomerBidder()));
        refreshTimer();
        support.firePropertyChange("isRed", true, false);
    }

    public void placeBid()
    {
        try
        {
            model.placeBid(Integer.parseInt(bidInputProperty.get()), "You");
            refreshBid();
        }catch (Exception e)
        {
            errorProperty.set(e.getMessage());
        }

    }

    private void refreshBid()
    {
        currentBidProperty.set(String.valueOf(model.getCurrentBid()));
        bidderProperty.set(String.valueOf(model.getCustomerBidder()));
    }

    private void refreshTimer()
    {
        int t = model.getRemainingTimeInSeconds();
        if(t <= 10)
        {
            if(t % 2 == 0)
                support.firePropertyChange("isRed", false, true);
            else
                support.firePropertyChange("isRed", true, false);
        }
        timerProperty.set(model.getTimeLeft());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        switch (evt.getPropertyName())
        {
            case "timer" -> refreshTimer();
            case "end" -> reset();
            case "bid" -> refreshBid();
            default -> errorProperty.set(evt.getPropertyName());
        }
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(propertyName, listener);
    }
}
