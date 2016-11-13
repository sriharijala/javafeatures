package com.library.java.util;

import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/*

Java 8 time packages has lot of new functionality that is helpful in day to day date functions

1) Provides javax.time.ZoneId to deal with timezones.

2) Provides LocalDate and LocalTime classes

3) All classes of new Date and Time API of Java 8 are Immutable and thread-safe, as opposed to existing Date and Calendar API, where key classes e.g. java.util.Date or SimpleDateFormat are not thread-safe.

4) The key thing to new Date and Time API is that it define principle date-time concepts e.g. instants, durations, dates, times, timezones and periods. They are also based on the ISO Calendar system.

3) Every Java developer should at least know following five classes from new Date and Time API :
Instant - It represents a time-stamp e.g. 2014-01-14T02:20:13.592Z and can be obtained from java.time.Clock class as shown below :
Instant current = Clock.system(ZoneId.of("Asia/Tokyo")).instant();
LocalDate - represents a date without a time e.g. 2014-01-14. It can be used to store birthday, anniversary, date of joining etc.
LocalTime - represents time without a date
LocalDateTime - is used to combine date and time, but still without any offset or time-zone
ZonedDateTime - a complete date-time with time-zone and resolved offset from UTC/Greenwich


4) The main package is java.time, which contains classes to represent dates, times, instants, and durations. Two sub-packages are java.time.format for obvious reasons and java.time.temporal for a lower level access to fields.

5) A time zone is a region of the earth where the same standard time is used. Each time zone is described by an identifier and usually has the format region/city (Asia/Tokyo) and an offset from Greenwich/UTC time. For example, the offset for Tokyo is +09:00.

6) The OffsetDateTime class, in effect, combines the LocalDateTime class with the ZoneOffset class. It is used to represent a full date (year, month, day) and time (hour, minute, second, nanosecond) with an offset from Greenwich/UTC time (+/-hours:minutes, such as +06:00 or -08:00).

7) The DateTimeFormatter class is used to format and parse dates in Java. Unlike SimpleDateFormat, this is both immutable and thread-safe and it can (and should) be assigned to a static constant where appropriate. DateFormatter class provides numerous predefined formatters and also allows you to define your own.

Following the convention, it also has parse() to convert String to Date in Java and throws DateTimeParseException, if any problem occurs during conversion. Similarly, DateFormatter has a format() method to format dates in Java, and it throws DateTimeException error in case of a problem.

8) Just to add, there is subtle difference between date formats "MMM d yyyy" and  "MMM dd yyyy", as former will recognize both "Jan 2, 2014" and "Jan 14, 2014", but later will throw exception when you pass "Jan 2, 2014", because it always expects two characters for day of month. To fix this, you must always pass single digit days as two digits by padding zero at beginning e.g. "Jan 2, 2014" should be passed as "Jan 02 2014".



*/
public class Java8DateDemo {

	public static void main(String...strings) {
		
		//get current date
		LocalDate today = LocalDate.now(); 
		
		System.out.println("Today's Local date : " + today);
		
		//getting current year, month, day
		int year = today.getYear(); 
		int month = today.getMonthValue(); 
		int day = today.getDayOfMonth(); 
		System.out.printf("Year : %d Month : %d day : %d \t %n", year, month, day);

		//get particular date
		LocalDate dateOfBirth = LocalDate.of(2011, 8, 18); 
		System.out.println("Your Date of birth is : " + dateOfBirth);

		//comparing dates
		LocalDate date1 = LocalDate.of(2016, 8, 18); 
		if(date1.equals(today)){ 
			System.out.printf("Today %s and date1 %s are same date %n", today, date1); 
		}

		//check recurring dates
		MonthDay birthday = MonthDay.of(dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());
		MonthDay currentMonthDay = MonthDay.from(today);
		if(currentMonthDay.equals(birthday)){ 
			System.out.println("Many Many happy returns of the day !!"); 
		} else { 
			System.out.println("Sorry, today is not your birthday");
		}

		//local time
		LocalTime time = LocalTime.now(); 
		System.out.println("local time now : " + time);

		//add hours
		time = LocalTime.now(); 
		LocalTime newTime = time.plusHours(2); // adding two hours 
		System.out.println("Time after 2 hours : " + newTime); 
		
		//day after one week
		LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS); 
		System.out.println("Today is : " + today); 
		System.out.println("Date after 1 week : " + nextWeek);

		//date before and after one year
		LocalDate previousYear = today.minus(1, ChronoUnit.YEARS); 
		System.out.println("Date before 1 year : " + previousYear); 
		
		LocalDate nextYear = today.plus(1, ChronoUnit.YEARS); 
		System.out.println("Date after 1 year : " + nextYear);

		//using clock
		// Returns the current time based on your system clock and set to UTC.
		Clock clock = Clock.systemUTC(); 
		System.out.println("Clock : " + clock); // Returns time based on system clock zone 
		Clock defaultClock = Clock.systemDefaultZone(); 
		System.out.println("Clock : " + defaultClock);

		//check date is before or after certain date
		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
		if(tomorrow.isAfter(today)){ 
			System.out.println("Tomorrow comes after today"); 
		} 
		
		LocalDate yesterday = today.minus(1, ChronoUnit.DAYS); 
		if(yesterday.isBefore(today)){ 
			System.out.println("Yesterday is day before today"); 
		}

		//dealing with time zones
		// Date and time with timezone in Java 8 
		ZoneId america = ZoneId.of("America/New_York"); 
		LocalDateTime localtDateAndTime = LocalDateTime.now(); 
		
		ZonedDateTime dateAndTimeInNewYork = ZonedDateTime.of(localtDateAndTime, america ); 
		System.out.println("Current date and time in a particular timezone : " + dateAndTimeInNewYork); 

		//fixed date format
		YearMonth currentYearMonth = YearMonth.now(); 
		System.out.printf("Days in month year %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth()); 
		YearMonth creditCardExpiry = YearMonth.of(2018, Month.FEBRUARY); 
		System.out.printf("Your credit card expires on %s %n", creditCardExpiry); 
		
		//check for leap year
		if(today.isLeapYear()){ 
			System.out.println("This year is Leap year"); 
		} else { 
			System.out.println("2014 is not a Leap year");
		}

		
		//how many months and days between two dates
		today = LocalDate.now(); 
		LocalDate java8Release = LocalDate.of(2014, Month.MARCH, 14); 
		Period periodToNextJavaRelease = Period.between(today, java8Release); 
		System.out.println(today);
		System.out.printf("Year %s Months %s between today and Java 8 release %n", periodToNextJavaRelease.getYears(),periodToNextJavaRelease.getMonths()  ); 
			
		//date and time with timezoe offset
		LocalDateTime datetime = LocalDateTime.of(2014, Month.JANUARY, 14, 19, 30); 
		ZoneOffset offset = ZoneOffset.of("+05:30"); 
		OffsetDateTime date = OffsetDateTime.of(datetime, offset); 
		System.out.println("Date and Time with timezone offset in Java : " + date);

		//get current timestamp
		Instant timestamp = Instant.now(); 
		System.out.println("What is value of this instant " + timestamp); 
		
		//parse date in java8
		String dayAfterTommorrow = "20140116";
		LocalDate formatted = LocalDate.parse(dayAfterTommorrow, DateTimeFormatter.BASIC_ISO_DATE);
		System.out.printf("Date generated from String %s is %s %n", dayAfterTommorrow, formatted);

		//custom date parsing
		String goodFriday = "Apr 18 2014"; 
		try { 
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
			LocalDate holiday = LocalDate.parse(goodFriday, formatter); 
			System.out.printf("Successfully parsed String %s, date is %s%n", goodFriday, holiday); 
		} catch (DateTimeParseException ex) { 
			System.out.printf("%s is not parsable!%n", goodFriday); ex.printStackTrace();
		}
		
		//convert date to custom formatted string
		LocalDateTime arrivalDate = LocalDateTime.now(); 
		try { 
			DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a"); 
			String landing = arrivalDate.format(format); 
			System.out.printf("Arriving at : %s %n", landing); 
		} catch (DateTimeException ex) { 
			System.out.printf("%s can't be formatted!%n", arrivalDate); 
			ex.printStackTrace(); 
		}

		

		

		
		
		
	
	}
}
