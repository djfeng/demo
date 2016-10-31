package com.bocweb.core.util.date;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarUtil {
	/**
	 * 获取虚拟的第一天
	 */
	static public Date getVirtualStartOfFirstDay() {
		Calendar calendar = new GregorianCalendar(1900, 11, 30);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	};

	/**
	 * 获取今天的结束
	 */
	static public Date getEndOfToday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	};

	/**
	 * 获取一天的结束
	 * 
	 * @param date
	 * @return 23:59:59.999
	 */
	static public Date getEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	};

	/**
	 * 获取一天的开始
	 * 
	 * @param date
	 * @return 00:00:00.000
	 */
	static public Date getBeginOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	};

	/**
	 * @param year
	 * @param week
	 *            注意 week 从 1 开始
	 */
	static public Date getMondayOfWeekWithMonDayIsFirstDay(int year, int week) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return calendar.getTime();
	}

	/**
	 * @param year
	 * @param week
	 *            注意 week 从 1 开始
	 */
	static public Date getSundayOfWeekWithMonDayIsFirstDay(int year, int week) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return calendar.getTime();
	}

	static public boolean isWeekend(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		return (day == Calendar.SATURDAY || day == Calendar.SUNDAY);
	}

	/**
	 * @param date
	 */
	static public Date getMondayOfWeekWithMonDayIsFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return calendar.getTime();
	}

	/**
	 * @param date
	 */
	static public Date getSundayOfWeekWithMonDayIsFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return calendar.getTime();
	}


	static public int getWeekOfYearWithMondayIsFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	};

	static public int getYearWithMondayIsFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		return calendar.get(Calendar.YEAR);
	};

	/**
	 * @param year
	 * @param month
	 *            注意 month 从 1 开始
	 * @return 第一天 00:00:00.000
	 */
	static public Date getFirstDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	};

	
	static public Date getMonDayOfFirstWeekInMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 0);
		return calendar.getTime();
	};

	static public Date getSunDayOfLastWeekInMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, -1);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	};

	/**
	 * @param year
	 * @param month
	 *            注意 month 从 1 开始
	 * @return 最后一天 23:59:59.999
	 */
	static public Date getLastDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	};

	/**
	 * 获取一天的开始
	 * 
	 * @param date
	 * @return 00:00:00.000
	 */
	static public Date getMiddleOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 11);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	};

	static public int getDayOfYear(Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	static public int getYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * @param date
	 * @return 周一是 1
	 */
	static public int getDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (i == 0) {
			return 7;
		} else {
			return i;
		}
	};

	/**
	 * @param date
	 */
	static public int getDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	};

	/**
	 * @param year
	 * @param month
	 *            注意 month 从 1 开始
	 */
	static public int getDayCountOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	};

	
	static public int getDayCountOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	};

	static public List<Date> getNormalDaysWithMonDayIsFirstDay(int year,
			int week) {
		List<Date> days = new ArrayList<Date>();
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		for (int d = 2; d < 7; d++) {
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.DAY_OF_WEEK, d);
			days.add(calendar.getTime());
		}
		return days;
	}

	/**
	 * @param year
	 * @param month
	 *            注意 month 从 1 开始
	 */
	static public Date getDay(int year, int month, int Day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, Day);
		return calendar.getTime();
	};

	static public List<Date> getWeekendDaysWithMonDayIsFirstDay(int year,
			int week) {
		List<Date> days = new ArrayList<Date>();
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_WEEK, 7);
		days.add(calendar.getTime());
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		days.add(calendar.getTime());
		return days;
	}
	
	static public List<DateExt> getWeekDaysWithMonDayIsFirstDay(Date date) {
		List<DateExt> days = new ArrayList<DateExt>(7);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		int i = calendar.get(Calendar.DAY_OF_WEEK);
		int d = calendar.get(Calendar.DAY_OF_YEAR);
		if (i == 1) {// SunDay
			for (int ii = 6; ii > -1; ii--) {
				calendar.set(Calendar.DAY_OF_YEAR, d - ii);
				DateExt de=new DateExt();
				de.setDate(calendar.getTime());
				days.add(de);
			}
		} else {
			int monday = d - (i - 2);
			for (int ii = 0; ii < 7; ii++) {
				calendar.set(Calendar.DAY_OF_YEAR, monday + ii);
				DateExt de=new DateExt();
				de.setDate(calendar.getTime());
				days.add(de);
			}
		}
		return days;
	}

	static public List<DateExt> getFullMonthDaysWithMonDayIsFirstDay(int year,
			int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		//要制定日期
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// 本月天数
		int monthCount = calendar.getActualMaximum(Calendar.DATE);
		// 本月1号是周几
		int thisMonthFirstDay = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// 下月1号是周几
		int nextMonthFirstDay = calendar.get(Calendar.DAY_OF_WEEK);
		int addF = 0;
		int addL = 0;
		if (thisMonthFirstDay == Calendar.SUNDAY) { // 第一天是SunDay
			addF = 6;
		} else {
			addF = thisMonthFirstDay - 2;
		}
		if (nextMonthFirstDay == Calendar.MONDAY) { // 第一天是SunDay
			addL = 0;
		} else if (nextMonthFirstDay == Calendar.SUNDAY) { // 第一天是SunDay
			addL = 1;
		} else {
			addL = 9 - nextMonthFirstDay;
		}
		int alldays = addF + addL + monthCount;
		List<DateExt> days = new ArrayList<DateExt>(alldays);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int d = calendar.get(Calendar.DAY_OF_YEAR);
		int allInMonth=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int ii = 0; ii < alldays; ii++) {
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month - 1);
			calendar.set(Calendar.DAY_OF_YEAR, d - addF + ii);
			DateExt de=new DateExt();
			de.setDate(calendar.getTime());
			if ((- addF + ii<0)||(- addF + ii>allInMonth-1)){
				de.setThisMonth(false);
			}
			days.add(de);
		}
		return days;
	}

	/**
	 * 获取某年的所有 星期六 星期日
	 * @param year
	 */
	public static List<DateExt> getFullWeekDayByYears(int year){
		List<DateExt> list = new ArrayList<DateExt>();
		Calendar calendar = new GregorianCalendar(year, 0, 1);
        int i = 1;
        DateExt dateExt;
        while (calendar.get(Calendar.YEAR) < year + 1) {
            calendar.set(Calendar.WEEK_OF_YEAR, i++);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            if (calendar.get(Calendar.YEAR) == year) {
            	dateExt = new DateExt();
            	dateExt.setDate(calendar.getTime());
            	list.add(dateExt);
            }
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            if (calendar.get(Calendar.YEAR) == year) {
            	dateExt = new DateExt();
            	dateExt.setDate(calendar.getTime());
            	list.add(dateExt);
            }
        }
		return list;
	}

	static public Date getTime(int hour, int min, int sec) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, sec);
		return calendar.getTime();
	}

	static public Date getDateTime(Date date, Date time) {
		Calendar calendarD = Calendar.getInstance();
		calendarD.setTime(date);
		Calendar calendarT = Calendar.getInstance();
		calendarT.setTime(time);
		calendarD
				.set(Calendar.HOUR_OF_DAY, calendarT.get(Calendar.HOUR_OF_DAY));
		calendarD.set(Calendar.MINUTE, calendarT.get(Calendar.MINUTE));
		calendarD.set(Calendar.SECOND, calendarT.get(Calendar.SECOND));

		return calendarD.getTime();
	}

	static public int getDaysBetweenDates(Date date1, Date date2) {
		long d = date2.getTime() - date1.getTime();
		return Long.valueOf(d / (24 * 60 * 60 * 1000)).intValue();
	}

	static public Date getDayAfterDate(Date date, int days) {
		Calendar calendarD = Calendar.getInstance();
		calendarD.setTime(date);
		calendarD.set(Calendar.DATE, calendarD.get(Calendar.DATE) + days);
		return calendarD.getTime();

	}

	static public Boolean isDayBetweenDates(Date date, Date sd, Date md) {
		return date.before(md) && date.after(sd);
	}

//	public static void main(String[] args) {
//	
//	List<DateExt> dateExts = getFullMonthDaysWithMonDayIsFirstDay(2011,11);
//	
//	for (DateExt dateExt : dateExts) {
//	  if(dateExt.getThisMonth()){
//		  System.out.println(dateExt.getDate() + "," + dateExt.getYear() + "," + dateExt.getMonth() + " ," + dateExt.getDay() + " ," + dateExt.getWeek()%7);
//	  }	
//	}
//}

}
