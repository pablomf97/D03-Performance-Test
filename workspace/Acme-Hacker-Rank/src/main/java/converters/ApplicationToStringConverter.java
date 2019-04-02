package converters;

import org.springframework.core.convert.converter.Converter;


import domain.Application;

public class ApplicationToStringConverter implements Converter<Application, String> {
	@Override
	public String convert(final Application actor) {
		String result;

		if (actor == null)
			result = null;
		else
			result = String.valueOf(actor.getId());
		return result;
	}
}
