package converters;

import org.springframework.core.convert.converter.Converter;

import domain.PersonalData;

public class PersonalDataToStringConverter implements Converter<PersonalData, String>  {
	@Override
	public String convert(final PersonalData actor) {
		String result;

		if (actor == null)
			result = null;
		else
			result = String.valueOf(actor.getId());
		return result;
	}
}
