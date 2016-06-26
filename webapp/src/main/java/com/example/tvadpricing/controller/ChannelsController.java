package com.example.tvadpricing.controller;

import com.example.tvadpricing.security.SecurityConfig;
import com.example.tvadpricing.service.ChannelPricing;
import com.example.tvadpricing.service.ChannelPricingService;
import com.example.tvadpricing.service.Formats;
import com.example.tvadpricing.service.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handle requests to the /channels resource
 */

@RestController
public class ChannelsController {

    private static final Logger LOG = LoggerFactory.getLogger(ChannelsController.class);

    @Autowired
    private ServiceLocator serviceLocator;

    /**
     * Get the pricing for all channels
     *
     * @param startInclusive
     * @param endExclusive
     * @param nofOccurrences
     * @param durationSeconds
     * @param response
     * @return the channel pricing or null
     * @throws IOException
     */
    @Secured({ SecurityConfig.USER_ROLE, SecurityConfig.ADMIN_ROLE })
    @RequestMapping(method = RequestMethod.GET, path = "/channels")
    public List<ChannelPricing> getPricing(@RequestParam("startInclusive") @DateTimeFormat(pattern = Formats.PERIOD)
                                             Date startInclusive,
                                     @RequestParam("endExclusive") @DateTimeFormat(pattern = Formats.PERIOD)
                                             Date endExclusive,
                                     @RequestParam("nofOccurrences") int nofOccurrences,
                                     @RequestParam("durationSeconds") int durationSeconds,
                                     HttpServletResponse response) throws IOException {

        List<ChannelPricingService> services = serviceLocator.getServices();

        if (startInclusive.getTime() > endExclusive.getTime()) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "startInclusive must be before endExclusive");
            return Collections.emptyList();
        }
        return services.stream().map(s -> s.getPricing(startInclusive, endExclusive, durationSeconds, nofOccurrences))
                .collect(Collectors.toList());
    }

}
