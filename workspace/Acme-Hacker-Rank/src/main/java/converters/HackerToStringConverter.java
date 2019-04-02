package converters;

import org.springframework.core.convert.converter.Converter;


import domain.Hacker;

public class HackerToStringConverter implements Converter<Hacker, String> {
	@Override
	public String convert(final Hacker actor) {
		String result;

		if (actor == null)
			result = null;
		else
			result = String.valueOf(actor.getId());
		return result;
	}
}
