
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import domain.Actor;
import domain.Application;
import domain.Company;
import domain.Position;
import domain.Problem;

@Transactional
@Service
public class PositionService {

	@Autowired
	private PositionRepository	positionRepository;

	@Autowired
	private ProblemService		problemService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CurriculaService	curriculaService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	public Position create(final Actor actor) {
		Actor principal;
		Position result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "COMPANY"), "not.allowed");

		result = new Position();
		result.setCompany((Company) actor);
		final Collection<Problem> problems = new ArrayList<>();
		result.setProblems(problems);
		result.setIsDraft(true);
		return result;
	}

	public Collection<Position> findAll() {
		Collection<Position> result;
		result = this.positionRepository.findAll();

		return result;
	}

	public Collection<Position> findAllFinal() {
		Collection<Position> result;
		result = this.positionRepository.findAllFinal();

		return result;
	}

	public Position findOne(final int positionId) {
		Position result;

		result = this.positionRepository.findOne(positionId);
		Assert.notNull(result);
		return result;
	}

	public Position save(final Position position) {
		Actor principal;
		principal = this.actorService.findByPrincipal();
		Position result = this.create(principal);

		Assert.isTrue(this.actorService.checkAuthority(principal, "COMPANY"), "not.allowed");
		Assert.isTrue(position.getCompany().equals(principal), "not.allowed");
		if (position.getId() == 0) {
			result = position;
			result.setTicker(this.generateTicker(position));
		} else {
			result = this.findOne(position.getId());
			Assert.isTrue(result.getIsDraft());
			if (position.getIsCancelled() == true) {
				result.setIsCancelled(true);
				result.setIsDraft(true);
			} else {
				Assert.isTrue(result.getCompany().equals(principal), "not.allowed");
				result.setIsDraft(position.getIsDraft());
				result.setDeadline(position.getDeadline());
				result.setDescription(position.getDescription());
				result.setProblems(position.getProblems());
				result.setProfileRequired(position.getProfileRequired());
				result.setSalary(position.getSalary());
				result.setSkillsRequired(position.getSkillsRequired());
				result.setTechnologiesRequired(position.getTechnologiesRequired());
				result.setTitle(position.getTitle());
			}
		}
		Assert.notNull(result);
		result = this.positionRepository.save(result);

		return result;
	}

	public void delete(final Position position) {
		Actor principal;

		Assert.notNull(position);
		Assert.isTrue(position.getId() != 0, "wrong.id");
		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "COMPANY"), "not.allowed");
		final Position orig = this.findOne(position.getId());
		Assert.isTrue(position.getCompany().getId() == principal.getId(), "not.allowed");
		Assert.isTrue(orig.getId() == position.getId());
		final Collection<Application> applies = this.applicationService.findByPosition(position);
		for (final Application a : applies) {
			this.applicationService.delete(a);
			this.curriculaService.delete(a.getCopyCurricula().getId());
		}

		this.positionRepository.delete(position.getId());

	}
	public void cancel(final Position position) {
		Actor principal;

		Assert.notNull(position);
		Assert.isTrue(position.getId() != 0, "wrong.id");
		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "COMPANY"), "not.allowed");
		final Position orig = this.findOne(position.getId());
		Assert.isTrue(position.getCompany().getId() == principal.getId(), "not.allowed");
		Assert.isTrue(orig.getId() == position.getId());
		orig.setIsCancelled(true);
		this.positionRepository.save(orig);
	}

	public Collection<Position> findByOwner(final Actor actor) {
		Assert.notNull(actor);
		return this.positionRepository.findByOwner(actor.getId());

	}

	// Other business methods -------------------------------

	public Position reconstruct(final Position position, final BindingResult binding) {
		final Actor principal = this.actorService.findByPrincipal();
		final Position result = this.create(principal);

		if (position.getId() == 0)
			result.setIsCancelled(false);
		else {
			final Position orig = this.findOne(position.getId());
			Assert.notNull(orig);
			Assert.isTrue(orig.getCompany().getId() == principal.getId());
			result.setIsCancelled(orig.getIsCancelled());
			result.setId(orig.getId());
		}
		result.setCompany((Company) principal);
		result.setDeadline(position.getDeadline());
		result.setDescription(position.getDescription());
		result.setIsDraft(position.getIsDraft());
		result.setProfileRequired(position.getProfileRequired());
		result.setProblems(position.getProblems());
		result.setSalary(position.getSalary());
		result.setSkillsRequired(position.getSkillsRequired());
		result.setTechnologiesRequired(position.getTechnologiesRequired());
		result.setTitle(position.getTitle());
		this.validator.validate(result, binding);
		this.checkProblems(position, binding);
		return result;
	}
	public void checkProblems(final Position position, final BindingResult binding) {
		final Collection<Problem> newProblems = position.getProblems();
		final Actor actor = this.actorService.findByPrincipal();
		final Collection<Problem> orig = this.problemService.findByOwner(actor);

		if (orig != null && !orig.isEmpty() && (position.getProblems()) != null && !(position.getProblems()).isEmpty())
			try {
				Assert.isTrue(orig.containsAll(newProblems), "problems.error");
			} catch (final Throwable oops) {
				binding.rejectValue("problems", "problems.error.notAllow");
			}
	}

	public String generateTicker(final Position position) {
		String res = "";
		String name = position.getTitle() + "XXXX";
		name = name.substring(0, 4);
		res = name + "-";
		boolean b = true;
		while (b) {
			final Date d = new Date();
			final Random rand = new Random();
			rand.setSeed(d.getTime());
			final int i = (1000 + rand.nextInt(9000));
			final String result = res + i;
			if (this.positionRepository.findByTicker(result) != null) {
				res = result;
				b = false;
			}
		}
		return res;
	}

	public void flush() {
		this.positionRepository.flush();
	}

}
