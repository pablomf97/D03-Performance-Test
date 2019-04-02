package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;


import repositories.MiscellaneousDataRepository;

import domain.MiscellaneousData;

public class StringToMiscellaneousDataConverter implements Converter<String, MiscellaneousData> {

	@Autowired

	MiscellaneousDataRepository actorRepository;

	@Override
	public MiscellaneousData convert(final String text) {
		MiscellaneousData result;

		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.actorRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}