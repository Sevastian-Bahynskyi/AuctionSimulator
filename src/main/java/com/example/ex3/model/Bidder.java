package com.example.ex3.model;

public class Bidder
{
    private String name;
    private int maxBid;
    private AuctionModelManager auctionModelManager;


    public Bidder(String name, int maxBid)
    {
        this.name = name;
        this.maxBid = maxBid;
    }

    public String getName()
    {
        return name;
    }

    protected int getMaxBid()
    {
        return maxBid;
    }

    public void setMaxBid(int maxBid)
    {
        this.maxBid = maxBid;
    }

    protected AuctionModelManager getAuctionModelManager()
    {
        return auctionModelManager;
    }

    public void placeBid(int bid)
    {
        auctionModelManager.placeBid(bid, name);
    }

    public void setAuctionModelManager(AuctionModelManager auctionModelManager)
    {
        this.auctionModelManager = auctionModelManager;
    }
}
