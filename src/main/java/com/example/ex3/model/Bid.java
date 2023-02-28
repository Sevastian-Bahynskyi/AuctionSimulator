package com.example.ex3.model;

public class Bid
{
    private int currentBid;
    private String bidder;


    public Bid(int currentBid, String bidder)
    {
        this.currentBid = currentBid;
        this.bidder = bidder;
    }

    public int getCurrentBid()
    {
        return currentBid;
    }

    public String getBidderName()
    {
        return bidder;
    }
}
