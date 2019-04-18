package services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import repositories.FinderRepository;

import domain.Finder;

@Transactional
@Service
public class FinderService {

	private FinderRepository finderRepository;

	public Finder save(Finder finder) {
		return this.finderRepository.save(finder);
	}

	public void saveAndFlush(Finder finder) {
		this.finderRepository.saveAndFlush(finder);
	}

}
