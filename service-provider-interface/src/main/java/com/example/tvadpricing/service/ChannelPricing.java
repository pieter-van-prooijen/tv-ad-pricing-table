package com.example.tvadpricing.service;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Value object representing the pricing on a tv channel
 *
 */

public class ChannelPricing {

    private String channel;

    private Date startInclusive;
    private Date endExclusive;
    private int durationSeconds;
    private int nofOccurrences;

    private int priceEuros;

    public ChannelPricing(String channel, Date startInclusive, Date endExclusive, int durationSeconds, int nofOccurrences, int
            priceEuros) {
        this.channel = channel;
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
        this.durationSeconds = durationSeconds;
        this.nofOccurrences = nofOccurrences;
        this.priceEuros = priceEuros;
    }

    public String getChannel() {
        return channel;
    }

    @JsonFormat(pattern = Formats.PERIOD)
    public Date getStartInclusive() {
        return startInclusive;
    }

    @JsonFormat(pattern = Formats.PERIOD)
    public Date getEndExclusive() {
        return endExclusive;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public int getNofOccurrences() {
        return nofOccurrences;
    }

    public int getPriceEuros() {
        return priceEuros;
    }
}
