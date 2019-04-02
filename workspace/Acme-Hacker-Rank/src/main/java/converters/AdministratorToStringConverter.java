package converters;


import org.springframework.core.convert.converter.Converter;


import domain.Administrator;

public class AdministratorToStringConverter implements Converter<Administrator, String> {

	
	@Override
	public String convert(final Administrator actor) {
		String result;

		if (actor == null)
			result = null;
		else
			result = String.valueOf(actor.getId());
		return result;
	}
}
