package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;


import repositories.CompanyRepository;

import domain.Company;

public class StringToCompanyConverter implements Converter<String, Company> {

	@Autowired

	CompanyRepository	actorRepository;

	@Override
	public Company convert(final String text) {
		Company result;

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