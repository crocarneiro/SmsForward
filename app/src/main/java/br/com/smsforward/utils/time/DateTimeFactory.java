package br.com.smsforward.utils.time;

import java.time.LocalDateTime;
import br.com.smsforward.Application;

public class DateTimeFactory {
    public static LocalDateTime now() {
        return LocalDateTime.now(Application.ZONE_ID);
    }
}
