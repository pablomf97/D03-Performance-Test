package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;


import repositories.EducationDataRepository;

import domain.EducationData;

public class StringToEducationDataConverter implements Converter<String, EducationData> {

	@Autowired

	EducationDataRepository	actorRepository;

	@Override
	public EducationData convert(final String text) {
		EducationData result;

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