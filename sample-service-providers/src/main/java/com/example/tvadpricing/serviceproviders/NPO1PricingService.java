package com.example.tvadpricing.serviceproviders;

import com.example.tvadpricing.service.ChannelPricing;
import com.example.tvadpricing.service.ChannelPricingService;

import java.util.Date;

/**
 *  Implement the pricing for NPO1 as a sample implementation
 */
public class NPO1PricingService implements ChannelPricingService {
    @Override
    public String getChannel() {
        return "NPO1";
    }

    @Override
    public ChannelPricing getPricing(Date startInclusive, Date endExclusive, int durationSeconds, int nofOccurrences) {

        int priceEuros = nofOccurrences * 1800;
        return new ChannelPricing(getChannel(), startInclusive, endExclusive, durationSeconds, nofOccurrences,
                priceEuros);
    }
}
