package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class DateTimeUtil {

    private static Locale localeBr = new Locale("pt", "BR");
    private String defaultDateFormatOut = "dd/MM/yyyy";
    private String defaultDateFormatIn = "dd/MM/yy";
    private String defaultTimeFormat = "HH:mm:ss";
    private static DateTimeUtil instance; // Cria uma instancia unica para toda a aplicacao

    public DateTimeUtil() {
    }

    public static void adjustTimeZone() {
        TimeZone.setDefault(getDefaultTimeZone());
    }

    public static TimeZone getDefaultTimeZone() {
        if (!(TimeZone.getDefault() instanceof InternalTimeZone)) {
            if (TimeZone.getDefault().inDaylightTime(new Date())) {
                return new InternalTimeZone(-2 * 60 * 60 * 1000, "GMT-2:00");
            }
            return new InternalTimeZone(-3 * 60 * 60 * 1000, "GMT-3:00");
        }
        return TimeZone.getDefault();
    }

    private static class InternalTimeZone extends SimpleTimeZone {

        private static final long serialVersionUID = 1L;

        public InternalTimeZone(int rawOffset, String ID) {
            super(rawOffset, ID);
        }
    }

    public Calendar getGregorianCalendar() {
        return new GregorianCalendar(getDefaultTimeZone(), localeBr);
    }

    public Date parse(String dateFormat, String data) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, localeBr);
        sdf.setTimeZone(getDefaultTimeZone());
        try {
            return sdf.parse(data);
        } catch (ParseException e) {
            return null;
        }
    }

    public String parse(String dateFormat, Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, localeBr);
        return sdf.format(data);
    }

    public String getMonth(Date date) {
        if (date == null) {
            return null;
        }
        return parse("MM", date);
    }

    public String getDay(Date date) {
        if (date == null) {
            return null;
        }
        return parse("dd", date);
    }

    public String getYear(Date date) {
        if (date == null) {
            return null;
        }
        return parse("yyyy", date);
    }

    public String parseTime(Date time) {
        return parse(defaultTimeFormat, time);
    }

    public Date parseTime(String time) {
        return parse(defaultTimeFormat, time);
    }

    public String parseDate(Date data) {
        if (data == null) {
            return null;
        }
        return parse(defaultDateFormatOut, data);
    }

    public Date parseDateException(String data) {
        return parse(defaultDateFormatIn, data);
    }

    public Date parseDate(String data) {
        return parse(defaultDateFormatIn, data);
    }

    public String parseDateToStrSQL(Date data) {
        String dataStr = parse("yyyy-MM-dd", data);
        return "'" + dataStr + "'";
    }

    public String parseTimeToStrSQL(Date data) {
        return "'" + parse("hh:mm:ss", data) + "'";
    }

    public Date addDay(Date date, int dia) {
        Calendar calendario = getGregorianCalendar();
        calendario.setTime(date);
        calendario.add(Calendar.DAY_OF_MONTH, dia);
        return calendario.getTime();
    }

    public long numberOfDays(Date dateStart, Date dateEnd) {
        return (dateEnd.getTime() - dateStart.getTime()) / (24 * 60 * 60 * 1000);
    }

    public Date getCurrentDateTime() {
        Calendar calendar = getGregorianCalendar();
        return calendar.getTime();
    }

    public static DateTimeUtil getInstance() {
        if (instance == null) {
            instance = new DateTimeUtil();
        }

        return instance;
    }

    public long subtractDate(Date dateStart, Date dateEnd) {
        return (dateEnd.getTime() - dateStart.getTime()) / (24 * 60 * 60 * 1000);
    }

    /*
     * GETTERS AND SETTERS
     */
    public String getDefaultDateFormatIn() {
        return this.defaultDateFormatIn;
    }

    public void setDefaultDateFormatIn(String dateFormat) {
        this.defaultDateFormatIn = dateFormat;
    }

    public String getDefaultDateFormatOut() {
        return this.defaultDateFormatOut;
    }

    public void setDefaultDateFormatOut(String dateFormat) {
        this.defaultDateFormatOut = dateFormat;
    }

    public Locale getLocaleBr() {
        return localeBr;
    }

    public String getDefaultTimeFormat() {
        return defaultTimeFormat;
    }

    public void setDefaultTimeFormat(String defaultTimeFormat) {
        this.defaultTimeFormat = defaultTimeFormat;
    }
}