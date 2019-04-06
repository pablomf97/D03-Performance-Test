package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SystemConfigurationRepository;

import domain.SystemConfiguration;

@Transactional
@Service
public class SystemConfigurationService {

	/* Working repository */

	@Autowired
	private SystemConfigurationRepository systemConfigurationRepository;

	/* Simple CRUD methods */

	/* Other methods */

	/* Find system configuration */
	public SystemConfiguration findMySystemConfiguration() {
		final SystemConfiguration result;

		result = this.systemConfigurationRepository.findSystemConf();

		return result;
	}
}
