package com.example.ex3;

import com.example.ex3.model.Bidder;
import javafx.application.Platform;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

public class BotBidder extends Bidder implements Runnable
{
    private final Object lock = new Object();
    private String lastBidItem;

    public BotBidder(String name, int maxBid)
    {
        super(name, maxBid);
        lastBidItem = "";
    }

    @Override
    public void run() {
        while (true) {
            Random random = new Random();

            String currentItem = getAuctionModelManager().getItem();
            if (getAuctionModelManager().getCustomerBidder().equals(getName()) && lastBidItem.equals(currentItem)) {
                continue;
            }

            synchronized (lock) {
                int currentBid = getCurrentBid();
                int maxBid = super.getMaxBid();
                int possibility = 0;

                int bid = 0;
                if (currentBid < maxBid) {
                    int minIncrease, maxIncrease;
                    if (currentBid <= maxBid * 0.2) {
                        minIncrease = 30;
                        maxIncrease = 200;
                        possibility = 90;
                    } else if (currentBid <= maxBid * 0.4) {
                        minIncrease = 20;
                        maxIncrease = 100;
                        possibility = 80;
                    } else if (currentBid <= maxBid * 0.6) {
                        minIncrease = 10;
                        maxIncrease = 60;
                        possibility = 50;
                    } else if (currentBid <= maxBid * 0.8) {
                        minIncrease = 5;
                        maxIncrease = 40;
                        possibility = 20;
                    } else {
                        minIncrease = 1;
                        maxIncrease = 15;
                        possibility = 10;
                    }

                    if (possibilityOfPlacingBid(possibility)) {
                        bid = generateBid(minIncrease, maxIncrease);
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }

                if (bid > 0) {
                    int finalBid = bid;
                    lastBidItem = getAuctionModelManager().getItem();
                    Platform.runLater(() -> {
                        System.out.println(getName() + " has a bid: " + finalBid);
                        placeBid(finalBid);
                        synchronized (lock) {
                            lock.notifyAll();
                        }
                    });

                    synchronized (lock)
                    {
                        try
                        {
                            lock.wait();
                        } catch (InterruptedException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

    // TODO -> fix the problem, the first item was sold, only 1 bot (who bought the item) becomes active and places the bids,
    //  others don't, even though they still have money
    //  also make a pause after each bid, because when GUI starts the bid is already almost 1_000_000


//    @Override
//    public void run()
//    {
//        while (true)
//        {
//            Random random = new Random();
//            int bid;
//            int currentBid = getCurrentBid();
//            // if current bid less than 20 percent of max bid -> generate bid that is 20% - 300% (generates randomly) greater than the current bid
//            if (currentBid <= super.getMaxBid() * 0.2)
//            {
//                bid = generateBid(30, 200);
//            } else if (currentBid > super.getMaxBid() * 0.2 // if current bid less than 40% but greater than 20% max bid
//                    && currentBid <= super.getMaxBid() * 0.4) // generate bid that is 20% - 200% (generates randomly) greater than the current bid
//            {
//                if (!possibilityOfPlacingBid(90))
//                    bid = generateBid(20, 100);
//                else continue;
//            } else if (currentBid > super.getMaxBid() * 0.4 // if current bid less than 60% but greater than 40% max bid
//                    && currentBid <= super.getMaxBid() * 0.6) // generate bid that is 20% - 100% (generates randomly) greater than the current bid
//            {
//                if (possibilityOfPlacingBid(60))
//                    bid = generateBid(10, 60);
//                else continue;
//            } else if (currentBid > super.getMaxBid() * 0.6 // if current bid less than 80% but greater than 60% max bid
//                    && currentBid <= super.getMaxBid() * 0.8) // generate bid that is 10% - 50% (generates randomly) greater than the current bid
//            {
//                if (possibilityOfPlacingBid(40))
//                    bid = generateBid(5, 40);
//                else continue;
//            } else if (currentBid > super.getMaxBid() * 0.8 // if current bid less than 80% but greater than 60% max bid
//                    && currentBid < super.getMaxBid()) // generate bid that is 10% - 50% (generates randomly) greater than the current bid
//            {
//                if (possibilityOfPlacingBid(20))
//                    bid = generateBid(1, 15);
//                else continue;
//            } else {
//                bid = 0;
//            }
//
//
////            if (bid == 0)
////                continue;
//
//            Platform.runLater(() -> {
//                System.out.println(getName() + " has a bid: " + bid);
//                placeBid(bid);
//            });
//        }
//    }

    private int generateBid(int minIncrease, int maxIncrease)
    {
        Random random = new Random();
        int t = random.nextInt(maxIncrease - minIncrease) + minIncrease;
        int res = 0;
        while (res > getMaxBid() || res == 0)
            res = (int) (((double) t / 100) * getCurrentBid() + getCurrentBid());
        return res;
    }


    private int getCurrentBid()
    {
        return super.getAuctionModelManager().getCurrentBid();
    }

    private boolean possibilityOfPlacingBid(int chance)
    {
        Random random = new Random();
        int gen = random.nextInt(100) + 1;
        return gen <= chance;
    }

}
