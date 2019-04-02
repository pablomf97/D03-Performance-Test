package converters;

import org.springframework.core.convert.converter.Converter;


import domain.Problem;

public class ProblemToStringConverter implements Converter<Problem, String> {
	@Override
	public String convert(final Problem actor) {
		String result;

		if (actor == null)
			result = null;
		else
			result = String.valueOf(actor.getId());
		return result;
	}
}
