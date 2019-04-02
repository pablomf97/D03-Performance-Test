package converters;

import org.springframework.core.convert.converter.Converter;


import domain.Company;

public class CompanyToStringConverter implements Converter<Company, String> {
	@Override
	public String convert(final Company actor) {
		String result;

		if (actor == null)
			result = null;
		else
			result = String.valueOf(actor.getId());
		return result;
	}
}
