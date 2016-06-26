package com.example.tvadpricing;

import com.example.tvadpricing.service.ChannelPricing;
import com.example.tvadpricing.service.ChannelPricingService;
import com.example.tvadpricing.serviceproviders.RTL4PricingService;
import junit.framework.TestCase;
import org.joda.time.DateTime;
import org.joda.time.Period;

public class ChannelPricingServiceTest extends TestCase {

    public void testRTL4() {
        ChannelPricingService service = new RTL4PricingService();

        assertEquals("RTL4", service.getChannel());

        DateTime dtStart = new DateTime();
        DateTime dtEnd = dtStart.plus(Period.days(2));
        ChannelPricing pricing = service.getPricing(dtStart.toDate(), dtEnd.toDate(), -1, -1);

        assertEquals(2 * 1500, pricing.getPriceEuros());
    }
}
