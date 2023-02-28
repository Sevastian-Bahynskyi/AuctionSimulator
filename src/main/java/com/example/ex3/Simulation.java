package com.example.ex3;

import com.example.ex3.model.AuctionModelManager;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final AuctionModelManager auctionModelManager;
    private final ArrayList<BotBidder> botBidders;

    public Simulation(AuctionModelManager auctionModelManager, int numBidders) {
        this.auctionModelManager = auctionModelManager;
        botBidders = new ArrayList<>();

        // Create and start each bot bidder thread
        for (int i = 1; i <= numBidders; i++) {
            String name = "BotBidder " + i;
            BotBidder botBidder = new BotBidder(name, 1000000);
            botBidder.setAuctionModelManager(auctionModelManager);
            botBidders.add(botBidder);
            Thread botBidderThread = new Thread(botBidder);
            botBidderThread.start();
        }
    }
}
