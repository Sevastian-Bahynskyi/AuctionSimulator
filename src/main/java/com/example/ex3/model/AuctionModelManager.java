package com.example.ex3.model;

import com.example.ex3.BotBidder;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class AuctionModelManager implements AuctionModel, PropertyChangeListener
{

    public static final String BLUE = "\033[0;34m";  // todo delete
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";
    public static final String RESET = "\033[0m";  // Text Reset

    private PropertyChangeSupport support;
    private AuctionItem auctionItem;
    private ArrayList<AuctionItem> auctionItems;
    private ArrayList<Bidder> bidders;
    private int currentAuctionItemIndex;


    public AuctionModelManager(ArrayList<AuctionItem> auctionItems, ArrayList<Bidder> bidders)
    {
        this.support = new PropertyChangeSupport(this);
        this.bidders = bidders;
        for (Bidder b:bidders)
        {
            b.setAuctionModelManager(this);
        }
        this.auctionItems = auctionItems;
        currentAuctionItemIndex = -1;
        auctionItem = auctionItems.get(0);
        nextAuctionItem();
        auctionItem.addPropertyChangeListener("timer", this);
    }


    @Override
    public void placeBid(int bid, String bidder)
    {
        if (bid > auctionItem.getBid().getCurrentBid() && !bidder.equals(getCustomerBidder()))
        {
            Bid bid_ = new Bid(bid, bidder);
            auctionItem.placeBid(bid_);
            for (Bidder b:bidders)
            {
                if(b.getName().equals(bidder))
                {
                    System.out.println(b.getName() + " has " + b.getMaxBid());
                    break;
                }
            }
            System.out.println(YELLOW + bidder + " made a bid: " + bid + RESET);
            System.out.println(BLUE + "-------------------------------------------------------" + RESET);
            support.firePropertyChange("bid", null, bid_);
        } else
        {
            support.firePropertyChange("Bid was not placed", null, "");
            System.out.println(RED + "Bid of " + bidder + " was not placed." + RESET);
        }
    }

    public void addAuctionItem(AuctionItem item)
    {
        this.auctionItems.add(item);
    }

    @Override
    public String getItem()
    {
        return auctionItem.getItem();
    }

    @Override
    public int getCurrentBid()
    {
        return auctionItem.getBid().getCurrentBid();
    }

    @Override
    public AuctionItem getCurrentAuctionItem()
    {
        return auctionItem;
    }

    @Override
    public String getCustomerBidder()
    {
        return auctionItem.getBid().getBidderName();
    }

    @Override
    public int getRemainingTimeInSeconds()
    {
        return auctionItem.getSeconds();
    }

    @Override
    public String getTimeLeft()
    {
        return auctionItem.getTimeLeft();
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(propertyName, listener);
    }

    public void nextAuctionItem()
    {
        if(currentAuctionItemIndex + 1 < auctionItems.size())
        {
            currentAuctionItemIndex++;
            for (Bidder b:bidders)
            {
                if (b.getName().equals(auctionItem.getBid().getBidderName()))
                {
                    b.setMaxBid(b.getMaxBid() - auctionItem.getBid().getCurrentBid());
                    System.out.println("The item was sold to " + b.getName() + " he left with " + b.getMaxBid());
                    break;
                }
            }
            AuctionItem oldAuctionItem = auctionItem;
            auctionItem = auctionItems.get(currentAuctionItemIndex);
            oldAuctionItem.removePropertyChangeListener("timer", this);
            auctionItem.addPropertyChangeListener("timer", this);
            auctionItem.startTimer();
            support.firePropertyChange("end", oldAuctionItem, auctionItem);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        support.firePropertyChange("timer", null, getTimeLeft());
        if(getRemainingTimeInSeconds() == 0)
        {
            auctionItem.cancelTimer();
            nextAuctionItem();
        }
    }
}
