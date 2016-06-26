package com.example.tvadpricing.service;

import com.example.tvadpricing.service.ChannelPricing;

import java.util.Date;

/**
 * Service interface to name and price a tv channel.
 */

public interface ChannelPricingService {

    String getChannel();

    ChannelPricing getPricing(Date startInclusive, Date endExclusive, int durationSeconds, int nofOccurrences);
}
