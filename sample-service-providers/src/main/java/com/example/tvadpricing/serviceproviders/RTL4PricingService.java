package com.example.tvadpricing.serviceproviders;

import com.example.tvadpricing.service.ChannelPricing;
import com.example.tvadpricing.service.ChannelPricingService;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import java.util.Date;

/**
 *  Implement the pricing for RTL4 as a sample implementation
 */
public class RTL4PricingService implements ChannelPricingService {
    @Override
    public String getChannel() {
        return "RTL4";
    }

    @Override
    public ChannelPricing getPricing(Date startInclusive, Date endExclusive, int durationSeconds, int nofOccurrences) {

        DateTime dtStartInclusive = new DateTime(startInclusive);
        DateTime dtEndExclusive = new DateTime(endExclusive);
        Interval interval = new Interval(dtStartInclusive, dtEndExclusive);

        Period period = new Period(interval);
        int priceEuros = period.getDays() * 1500;
        return new ChannelPricing(getChannel(), startInclusive, endExclusive, durationSeconds, nofOccurrences,
                priceEuros);
    }
}
