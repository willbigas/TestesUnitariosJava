package utils;

import java.time.*;

public class DateUtils {

	public static boolean verificaDiaSemana(LocalDate date, DayOfWeek diaDaSemana) {
		return date.getDayOfWeek().equals(diaDaSemana);
	}

	public static Clock fixarData(LocalDate localDate) {
		return Clock.fixed(Instant.from(localDate.atStartOfDay(ZoneId.systemDefault())), ZoneOffset.systemDefault());
	}

	public static boolean isDomingo(LocalDate dataEntrega) {
		return DateUtils.verificaDiaSemana(dataEntrega, DayOfWeek.SUNDAY);
	}

	public static boolean isSabado(LocalDate dataEntrega) {
		return DateUtils.verificaDiaSemana(dataEntrega, DayOfWeek.SATURDAY);
	}

}


