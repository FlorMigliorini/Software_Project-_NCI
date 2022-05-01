package florence.migliorini.crossingborder;

import static org.junit.Assert.assertEquals;

import android.util.Log;

import org.junit.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTest {
    @Test
    public void addition_isCorrect() {
        Log.d("TestDate",LocalTime.of(25,10).format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
