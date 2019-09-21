package org.pureacc.betcentral.application.stub;

import org.pureacc.betcentral.domain.service.Time;

import java.util.Date;

public class TestTime implements Time {
    private static final Date NOW = new Date();

    @Override
    public Date now() {
        return NOW;
    }
}
