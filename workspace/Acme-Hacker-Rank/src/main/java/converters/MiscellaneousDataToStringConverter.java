package converters;

import org.springframework.core.convert.converter.Converter;


import domain.MiscellaneousData;

public class MiscellaneousDataToStringConverter implements Converter<MiscellaneousData, String> {
	@Override
	public String convert(final MiscellaneousData actor) {
		String result;

		if (actor == null)
			result = null;
		else
			result = String.valueOf(actor.getId());
		return result;
	}
}
