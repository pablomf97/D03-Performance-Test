package converters;

import org.springframework.core.convert.converter.Converter;


import domain.PositionData;

public class PositionDataToStringConverter implements Converter<PositionData, String> {
	@Override
	public String convert(final PositionData actor) {
		String result;

		if (actor == null)
			result = null;
		else
			result = String.valueOf(actor.getId());
		return result;
	}
}
