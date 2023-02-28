package com.example.ex3;

import com.example.ex3.model.AuctionItem;
import com.example.ex3.model.Bid;

import java.util.ArrayList;

public class Data
{
    public static ArrayList<AuctionItem> auction()
    {
        ArrayList<AuctionItem> auction = new ArrayList<>();
        auction.add(new AuctionItem("Old house with ghosts", 20, 30000));
        auction.add(new AuctionItem("Luxury Watches", 30, 2000));
        auction.add(new AuctionItem("Vintage Car", 30, 90000));
        auction.add(new AuctionItem("Old piano", 20, 1000));
        auction.add(new AuctionItem("Rare Books", 40, 3000));
        return auction;
    }
}
