package converters;

import org.springframework.core.convert.converter.Converter;


import domain.EducationData;

public class EducationDataToStringConverter implements Converter<EducationData, String> {
	@Override
	public String convert(final EducationData actor) {
		String result;

		if (actor == null)
			result = null;
		else
			result = String.valueOf(actor.getId());
		return result;
	}
}
