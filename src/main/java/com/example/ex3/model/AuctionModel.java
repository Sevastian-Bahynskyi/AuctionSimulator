package com.example.ex3.model;

import java.beans.PropertyChangeListener;

public interface AuctionModel
{
    void placeBid(int bid, String bidder);
    String getItem();
    int getCurrentBid();
    AuctionItem getCurrentAuctionItem();
    String getCustomerBidder();
    int getRemainingTimeInSeconds();
    String getTimeLeft();
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
}
