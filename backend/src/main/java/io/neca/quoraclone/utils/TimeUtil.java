package io.neca.quoraclone.utils;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class TimeUtil {

    public String toDuration(Instant created) {
        return new PrettyTime().format(Date.from(created));
    }

}
