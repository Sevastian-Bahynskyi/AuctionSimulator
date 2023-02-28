package com.example.ex3.model;


import javafx.application.Platform;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.regex.Pattern;

public class AuctionItem
{
    private Bid bid;
    private Timer timer;
    private String item;
    private int seconds;
    private ArrayList<Bid> bidsHistory;
    private PropertyChangeSupport support;


    public AuctionItem(String item, int seconds, int startBid)
    {
        this.item = item;
        timer = new Timer();
        this.seconds = seconds;
        bidsHistory = new ArrayList<>();
        bid = new Bid(startBid, "None");
        support = new PropertyChangeSupport(this);
    }

    public void placeBid(Bid bid)
    {
        this.bid = bid;
        bidsHistory.add(bid);
    }

    public void startTimer()
    {
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                if(seconds > 0)
                {
                    Platform.runLater(() -> support.firePropertyChange("timer", null, getTimeLeft()));
                    seconds--;
                }
            }
        }, 0, 1000);
    }

    public void cancelTimer()
    {
        this.timer.cancel();
    }

    public int getSeconds()
    {
        return seconds;
    }

    public String getItem()
    {
        return item;
    }

    public String getTimeLeft()
    {
        String h = "00:";
        String m = "00:";
        String s;

        int tempSeconds = seconds;
        if(getSeconds() >= 3600)
        {
            int hours = seconds / 3600;
            h = (hours > 9? String.valueOf(hours) : "0" + hours) + ":";
            tempSeconds -= hours * 3600;
        }

        if(getSeconds() >= 60)
        {
            int minutes = seconds / 60;
            m = (minutes > 9? String.valueOf(minutes) : "0" + minutes) + ":";
            tempSeconds -= minutes * 60;
        }

        s = (tempSeconds > 9? String.valueOf(tempSeconds) : "0" + tempSeconds);
        return h + m + s;
    }

    public Bid getBid()
    {
        return bid;
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
