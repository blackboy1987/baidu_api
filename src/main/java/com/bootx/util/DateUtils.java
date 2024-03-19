package com.bootx.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 采用jdk8的日期类进行操作时间
 */
public class DateUtils {

    /**
     * 获取到当天的0点0分0秒
     *
     * @return
     */
    public static Date getBeginToday() {
        LocalDate localDate = LocalDate.now();
        return convertLocalDateToDate(localDate);
    }

    /**
     * 当天的24点
     * @return
     */
    public static Date getEndToday() {
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime= convertLocalDateToLocalDateTime(localDate);
        return convertLocalDateTimeToDate(localDateTime.plusDays(1));
    }

    /**
     * 获取指定时间的0点0分0秒
     *
     * @param date
     *      指定时间
     * @return
     *  指定时间的0点0分0秒
     */
    public static Date getBeginDay(Date date){
      LocalDate localDate = convertDateToLocalDate(date);
      return convertLocalDateToDate(localDate);
    }
    /**
     * 获取指定时间的24点
     *
     * @param date
     *      指定时间
     * @return
     *  指定时间的0点0分0秒
     */
    public static Date getEndDay(Date date){
        LocalDate localDate = convertDateToLocalDate(date);
        return convertLocalDateTimeToDate(convertLocalDateToLocalDateTime(localDate).plusDays(1));
    }






    /**
     * 距离当前时间index天之后的时间
     * @param index
     *      相隔的天数。index=0：当天，index>0 之后的index天。index<0:之前的index天
     * @return
     *      距离当前时间index天之后的时间
     */
    public static Date getNextDay(Integer index) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return convertLocalDateTimeToDate(localDateTime.plusDays(index));
    }

    /**
     * 距离当前时间index天之后的时间
     * @param index
     *      相隔的天数。index=0：当天，index>0 之后的index天。index<0:之前的index天
     * @return
     *      距离当前时间index天之后的时间
     */
    public static Date getNextHour(Integer index) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return convertLocalDateTimeToDate(localDateTime.plusHours(index));
    }


    /**
     * 距离当前时间index天之后的时间
     * @param index
     *      相隔的天数。index=0：当天，index>0 之后的index天。index<0:之前的index天
     * @return
     *      距离当前时间index天之后的时间
     */
    public static Date getNextMinute(Integer index) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return convertLocalDateTimeToDate(localDateTime.plusMinutes(index));
    }


    /**
     * 距离当前时间index天之后的时间
     * @param index
     *      相隔的天数。index=0：当天，index>0 之后的index天。index<0:之前的index天
     * @return
     *      距离当前时间index天之后的时间
     */
    public static Date getNextSecond(long index) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return convertLocalDateTimeToDate(localDateTime.plusSeconds(index));
    }


    /**
     * 距离指定时间index天之后的时间
     * @param index
     *      相隔的天数。index=0：当天，index>0 之后的index天。index<0:之前的index天
     * @return
     *      距离指定时间index天之后的时间
     */
    public static Date getNextDay(Date date,Integer index) {
        LocalDateTime localDateTime = convertDateToLocalDateTime(date);
        return convertLocalDateTimeToDate(localDateTime.plusDays(index));
    }

    public static Date getNextHours(Date date,Integer index) {
        LocalDateTime localDateTime = convertDateToLocalDateTime(date);
        return convertLocalDateTimeToDate(localDateTime.plusHours(index));
    }


    /**
     * 距离当前时间index天之后的0点0分0秒
     * @param index
     *      相隔的天数。index=0：当天，index>0 之后的index天。index<0:之前的index天
     * @return
     *      距离当前时间index天之后的0点0分0秒
     */
    public static Date getBeginNextDay(Integer index) {
        LocalDate localDate = LocalDate.now();
        return convertLocalDateToDate(localDate.plusDays(index));
    }

    /**
     * 距离当前时间index天之后的24点
     * @param index
     *      相隔的天数。index=0：当天，index>0 之后的index天。index<0:之前的index天
     * @return
     *      距离当前时间index天之后的24点
     */
    public static Date getEndNextDay(Integer index) {
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusDays(index+1);
        return convertLocalDateTimeToDate(convertLocalDateToLocalDateTime(localDate));
    }


    //计算2个时间的插值（秒）
    public static Long getIntervalSecond(Date startDate, Date endDate) {
        Duration duration = Duration.between(convertDateToLocalDateTime(endDate),convertDateToLocalDateTime(startDate));
        return duration.getSeconds();
    }

    //计算2个时间的插值（分）
    public static Long getIntervalMin(Date startDate, Date endDate) {
        Duration duration = Duration.between(convertDateToLocalDateTime(endDate),convertDateToLocalDateTime(startDate));
        return duration.toMinutes();
    }

    //计算2个时间的插值（时）
    public static Long getIntervalHour(Date startDate, Date endDate) {
        Duration duration = Duration.between(convertDateToLocalDateTime(endDate),convertDateToLocalDateTime(startDate));
        return duration.toHours();
    }

    //计算2个时间的插值（天）
    public static Long getIntervalDay(Date startDate, Date endDate) {
        Duration duration = Duration.between(convertDateToLocalDateTime(endDate),convertDateToLocalDateTime(startDate));
        return duration.toDays();
    }

    //根据时间段获取所有天数(以0时0分0秒开始)
    public static List<Date> findDates(Date beginDate, Date endDate,Integer hours,Integer minutes,Integer seconds) {
        List<Date> list = new ArrayList<>();
        LocalDateTime localDateTime = convertDateToLocalDateTime(beginDate);
        if(hours==null||hours<0){
            hours = localDateTime.getHour();
        }
        if(minutes==null||minutes<0){
            minutes = localDateTime.getMinute();
        }
        if(seconds==null||seconds<0){
            seconds = localDateTime.getSecond();
        }

        LocalDateTime localDateTime1 = convertDateToLocalDateTime(endDate);
        while (localDateTime1.isAfter(localDateTime)){
            LocalDate localDate = convertLocalDateTimeToLocalDate(localDateTime1);
            LocalDateTime localDateTime2 = convertLocalDateToLocalDateTime(localDate).plusHours(hours).plusMinutes(minutes).plusSeconds(seconds);
            list.add(convertLocalDateTimeToDate(localDateTime2));
            localDateTime1 = localDateTime1.plusDays(-1);
        }

        return list;
    }

    /**
     * 将时间按照指定的格式转换成字符串
     *
     * @param date
     *      时间
     * @param pattern
     *     格式
     * @return
     *      字符串
     */
    public static String formatDateToString(Date date, String pattern) {
        LocalDateTime localDateTime = convertDateToLocalDateTime(date);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * 将时间格式的字符串按照指定的格式转换成时间类型
     *
     * @param dateStr
     *      时间格式的字符串
     * @param pattern
     *     格式
     * @return
     *      Date对象
     */
    public static Date formatStringToDate(String dateStr, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return convertLocalDateTimeToDate(LocalDateTime.parse(dateStr,dateTimeFormatter));
    }

    /**
     * 获取指定时间所在月份的第一天的0点0分0秒
     *
     * @param date
     *      时间
     * @return
     *  指定时间所在月份的第一天的0点0分0秒
     */
    public static Date getBeginMonth(Date date){
        LocalDate localDate = convertDateToLocalDate(date);
        localDate = LocalDate.of(localDate.getYear(),localDate.getMonthValue(),1);
        return convertLocalDateToDate(localDate);
    }

    /**
     * 获取指定时间所在月份的最后一天的0点0分0秒
     *
     * @param date
     *      时间
     * @return
     *  指定时间所在月份的最后一天的0点0分0秒
     */
    public static Date getEndMonth(Date date){
        LocalDate localDate = convertDateToLocalDate(date);
        localDate = LocalDate.of(localDate.getYear(),localDate.getMonthValue(),localDate.getMonth().maxLength());
        return convertLocalDateToDate(localDate);
    }


/*LocalDateTime和Date相互转换*******************************************************************************************************************************************************************/
    /**
     * 将LocalDateTime转成Date
     *
     * @param localDateTime
     *      LocalDateTime对象
     * @return
     *      Date类型的时间
     */
    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime){
        if(localDateTime==null){
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zoneId).toInstant();
        return Date.from(instant);
    }

    /**
     *  将Date转成LocalDateTime
     *
     * @param date
     *      Date 对象
     * @return
     *      LocalDateTime对象
     */
    public static LocalDateTime convertDateToLocalDateTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant,zoneId);

    }

/*LocalDate和Date相互转换*******************************************************************************************************************************************************************/
    /**
     * 将LocalDate转成Date
     *
     * @param localDate
     *      LocalDateTime对象
     * @return
     *      Date类型的时间
     */
    public static Date convertLocalDateToDate(LocalDate localDate){
        if(localDate==null){
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay(zoneId).toInstant();
        return Date.from(instant);
    }

    /**
     *  将Date转成LocalDate
     *
     * @param date
     *      Date 对象
     * @return
     *      LocalDate对象
     */
    public static LocalDate convertDateToLocalDate(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant,zoneId).toLocalDate();

    }

/*LocalTime和Date相互转换*******************************************************************************************************************************************************************/
    /**
     * 将LocalTime转成Date
     *
     * @param localTime
     *      LocalDate对象
     * @return
     *      Date类型的时间
     */
    public static Date convertLocalTimeToDate(LocalTime localTime){
        if(localTime==null){
            return null;
        }
        LocalDate localDate = LocalDate.now();
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = LocalDateTime.of(localDate,localTime).atZone(zoneId).toInstant();
        return Date.from(instant);
    }

    /**
     *  将Date转成LocalTime
     *
     * @param date
     *      Date 对象
     * @return
     *      LocalTime对象
     */
    public static LocalTime convertDateToLocalTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant,zoneId).toLocalTime();

    }




/*LocalDateTime和LocalDate相互转换*******************************************************************************************************************************************************************/

    /**
     * LocalDateTime转LocalDate
     *
     * @param localDateTime
     *      LocalDateTime对象
     * @return
     *      LocalDate对象
     */
    public static LocalDate convertLocalDateTimeToLocalDate(LocalDateTime localDateTime){
        if(localDateTime==null){
            return null;
        }
        return localDateTime.toLocalDate();

    }

    /**
     * LocaleDate转LocalDateTime
     *
     * @param localDate
     *      LocalDate对象
     * @return
     *      LocalDateTime对象
     */
    public static LocalDateTime convertLocalDateToLocalDateTime(LocalDate localDate){
        if(localDate==null){
            return null;
        }
        return LocalDateTime.of(localDate,LocalTime.of(0,0,0,0));

    }

/*LocalDateTime和LocalTime相互转换*******************************************************************************************************************************************************************/

    /**
     * LocalDateTime转LocalTime
     *
     * @param localDateTime
     *      LcalDateTime对象
     * @returno
     *      LocaTime对象
     */
    public static LocalTime convertLocalDateTimeToLocalTime(LocalDateTime localDateTime){
        if(localDateTime==null){
            return null;
        }
        return localDateTime.toLocalTime();
    }

    /**
     * LocalTime转LocalDateTime
     *
     * @param localTime
     *      LocalTime对象
     * @return
     *      LocalDateTime对象
     */
    public static LocalDateTime convertLocalTimeToLocalDateTime(LocalTime localTime){
        if(localTime==null){
            return null;
        }
        LocalDate localDate = LocalDate.now();
        return LocalDateTime.of(localDate,localTime);
    }

    /**
     * 获取指定日期所在周的周一
     * @param date
     *      日期
     * @return
     *      周一
     */
    public static Date getMonday(Date date,Integer index){
        if(date==null){
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if(c.get(Calendar.DAY_OF_WEEK)==1){
            c.add(Calendar.DAY_OF_MONTH,-1);
        }
        c.add(Calendar.DAY_OF_MONTH,c.getFirstDayOfWeek()-c.get(Calendar.DAY_OF_WEEK)+1);
        return DateUtils.getNextDay(c.getTime(),index*7);
    }

    /**
     * 获取指定日期所在周的周日
     * @param date
     *      日期
     * @return
     *      周日
     */
    public static Date getSunday(Date date,Integer index){
        if(date==null){
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if(c.get(Calendar.DAY_OF_WEEK)==1){
            return date;
        }
        c.add(Calendar.DAY_OF_MONTH,7-c.get(Calendar.DAY_OF_WEEK)+1);
        return DateUtils.getNextDay(c.getTime(),index*7);
    }

    public static String getDuration(Integer seconds) {
        int days = seconds/(60*60*24);
        int hour = seconds/(60*60);
        if(days>0){
            return days+"天前";
        }
        return hour+"小时前";

    }

    public static String formatDateInfo(Date date) {
        if(date==null){
            return "";
        }
        String info = "";
        Date now = new Date();
        long nowTime = now.getTime();
        long time = date.getTime();
        long duration = nowTime - time;
        long yearSeconds = 86400*1000*365L;
        long monthSeconds = 86400*1000*30L;
        long daySeconds = 86400*1000;
        long hourSeconds = 3600*1000;
        long minuteSeconds = 60*1000;
        long seconds = 60*1000;


        long years = Math.floorDiv(duration,yearSeconds);
        long months = Math.floorDiv(duration-years*yearSeconds,monthSeconds);
        long days = Math.floorDiv(duration-years*yearSeconds-months*monthSeconds,daySeconds);
        long hours = Math.floorDiv(duration-years*yearSeconds-months*monthSeconds-days*daySeconds,hourSeconds);
        long minute = Math.floorDiv(duration-years*yearSeconds-months*monthSeconds-days*daySeconds-hourSeconds*hours,minuteSeconds);
        long seconds1 = Math.floorDiv(duration-years*yearSeconds-months*monthSeconds-days*daySeconds-hourSeconds*hours-minute*minuteSeconds,seconds);
        if(years>0){
            return years+"年前";
        }
        if(months>0){
            return months+"月前";
        }
        if(days>0){
            return days+"天前";
        }
        if(hours>0){
            return hours+"小时前";
        }
        if(minute>0){
            return minute+"分钟前";
        }
        if(seconds1>0){
            return seconds1+"秒钟前";
        }

        return info;
    }
}
