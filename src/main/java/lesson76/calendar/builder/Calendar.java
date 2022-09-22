//package lesson76.calendar.builder;
//
//
//import java.io.Serializable;
//import java.util.Locale;
//import java.util.TimeZone;
//
//import static java.util.Calendar.FIELD_COUNT;
//
//public abstract class Calendar implements Serializable, Cloneable, Comparable<Calendar> {
//    //...
//    public static class Builder {
//        private static final int NFIELDS = FIELD_COUNT + 1;
//        private static final int WEEK_YEAR = FIELD_COUNT;
//        private long instant;
//        private int[] fields;
//        private int nextStamp;
//        private int maxFieldIndex;
//        private String type;
//        private TimeZone zone;
//        private boolean lenient = true;
//        private Locale locale;
//        private int firstDayOfWeek, minimalDaysInFirstWeek;
//
//        public Builder() {}
//
//        public Builder setInstant(long instant) {
//            if (fields != null) {
//                throw new IllegalStateException();
//            }
//            this.instant = instant;
//            nextStamp = COMPUTED;
//            return this;
//        }
//        //...省略n多set()方法
//
//        public Calendar build() {
//            if (locale == null) {
//                locale = Locale.getDefault();
//            }
//            if (zone == null) {
//                zone = TimeZone.getDefault();
//            }
//            Calendar cal;
//            if (type == null) {
//                type = locale.getUnicodeLocaleType("ca");
//            }
//            if (type == null) {
//                if (locale.getCountry() == "TH" && locale.getLanguage() == "th") {
//                    type = "buddhist";
//                } else {
//                    type = "gregory";
//                }
//            }
//            switch (type) {
//                case "gregory":
//                    cal = new GregorianCalendar(zone, locale, true);
//                    break;
//                case "iso8601":
//                    GregorianCalendar gcal = new GregorianCalendar(zone, locale, true);
//                    // make gcal a proleptic Gregorian
//                    gcal.setGregorianChange(new Date(Long.MIN_VALUE));
//                    // and week definition to be compatible with ISO 8601
//                    setWeekDefinition(MONDAY, 4);
//                    cal = gcal;
//                    break;
//                case "buddhist":
//                    cal = new BuddhistCalendar(zone, locale);
//                    cal.clear();
//                    break;
//                case "japanese":
//                    cal = new JapaneseImperialCalendar(zone, locale, true);
//                    break;
//                default:
//                    throw new IllegalArgumentException("unknown calendar type: " + type);
//            }
//            cal.setLenient(lenient);
//            if (firstDayOfWeek != 0) {
//                cal.setFirstDayOfWeek(firstDayOfWeek);
//                cal.setMinimalDaysInFirstWeek(minimalDaysInFirstWeek);
//            }
//            if (isInstantSet()) {
//                cal.setTimeInMillis(instant);
//                cal.complete();
//                return cal;
//            }
//
//            if (fields != null) {
//                boolean weekDate = isSet(WEEK_YEAR) && fields[WEEK_YEAR] > fields[YEAR];
//                if (weekDate && !cal.isWeekDateSupported()) {
//                    throw new IllegalArgumentException("week date is unsupported by " + type);
//                }
//                for (int stamp = MINIMUM_USER_STAMP; stamp < nextStamp; stamp++) {
//                    for (int index = 0; index <= maxFieldIndex; index++) {
//                        if (fields[index] == stamp) {
//                            cal.set(index, fields[NFIELDS + index]);
//                            break;
//                        }
//                    }
//                }
//
//                if (weekDate) {
//                    int weekOfYear = isSet(WEEK_OF_YEAR) ? fields[NFIELDS + WEEK_OF_YEAR] : 1;
//                    int dayOfWeek = isSet(DAY_OF_WEEK) ? fields[NFIELDS + DAY_OF_WEEK] : cal.getFirstDayOfWeek();
//                    cal.setWeekDate(fields[NFIELDS + WEEK_YEAR], weekOfYear, dayOfWeek);
//                }
//                cal.complete();
//            }
//            return cal;
//        }
//    }
//}
