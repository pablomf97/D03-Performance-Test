package converters;

import org.springframework.core.convert.converter.Converter;


import domain.Curricula;

public class CurriculaToStringConverter implements Converter<Curricula, String> {
	@Override
	public String convert(final Curricula actor) {
		String result;

		if (actor == null)
			result = null;
		else
			result = String.valueOf(actor.getId());
		return result;
	}
}
