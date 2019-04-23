
package services;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ApplicationRepository;
import domain.Actor;
import domain.Application;
import domain.Company;
import domain.Hacker;
import domain.Position;
import domain.Problem;

@Transactional
@Service
public class ApplicationService {


	// Managed repository ------------------------------------

		@Autowired
		private ApplicationRepository	applicationRepository;

		// Supporting services -----------------------------------

		@Autowired
		private ActorService	actorService;
		
		@Autowired
		private ProblemService 	problemService;
		
//		@Autowired
//		private CurriculaService 	curriculaService;
		
		@Autowired
		private Validator	validator;

		// Simple CRUD methods -----------------------------------

		public Application create() {
			Actor principal;
			Application result;

			principal = this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal, "HACKER"), "not.allowed");
			
			result = new Application();

			result.setApplicationMoment(new Date(System.currentTimeMillis() - 1));
			result.setStatus("PENDING");
			result.setHacker((Hacker) principal);
			
			return result;
		}

		public Collection<Application> findAll() {
			Collection<Application> result;
			result = this.applicationRepository.findAll();

			return result;
		}

		public Application findOne(final int applicationId) {
			Application result;
			result = this.applicationRepository.findOne(applicationId);

			return result;
		}

		public Application save(final Application application) {
			Actor principal;
			Application result;

			principal = this.actorService.findByPrincipal();
			Assert.notNull(principal, "not.allowed");
			Assert.notNull(application);

			Assert.notNull(application.getPosition());
			Assert.notNull(application.getHacker());
			Assert.notNull(application.getApplicationMoment());
			
			if (this.actorService.checkAuthority(principal, "HACKER")) {
				
				Assert.isTrue(application.getHacker().equals((Hacker) principal));
				
				if(application.getId() == 0) {
		
					Assert.isTrue(application.getStatus() == "PENDING");
					
				} else {
					
					Assert.notNull(application.getExplanation());
					Assert.notNull(application.getLinkCode());
					Assert.notNull(application.getCopyCurricula());
					Assert.isTrue(application.getStatus() == "SUBMITTED");
					
					application.setSubmitMoment(new Date(System.currentTimeMillis() - 1));
					
					Assert.isTrue(application.getApplicationMoment().before(application.getSubmitMoment()));
					
//					Curricula copy = this.curriculaService.create();
//					copy.setPersonalData(application.getCopyCurricula().getPersonalData());
//					copy.setEducationData(application.getCopyCurricula().getEducationData());
//					copy.setMiscellaneousData(application.getCopyCurricula().getMiscellaneousData());
//					copy.setPositionData(application.getCopyCurricula().getPositionData());
//					copy.setHacker(application.getCopyCurricula().getHacker());
//					copy.setIsCopy(true);
//					res = this.curriculaService.save(copy);
//					
//					application.setCopyCurricula(res);
				}

			} else if (this.actorService.checkAuthority(principal, "COMPANY")) {
				
				Assert.isTrue(application.getPosition().getCompany().equals((Company) principal));

				Assert.isTrue(application.getId() != 0);
				Assert.notNull(application.getExplanation());
				Assert.notNull(application.getLinkCode());
				Assert.notNull(application.getCopyCurricula());
				
			}

			result = this.applicationRepository.save(application);
			Assert.notNull(result);

			return result;
		}

		public void delete(final Application application) {
			Actor principal;

			Assert.notNull(application);
			Assert.isTrue(application.getId() != 0, "wrong.id");

			principal = this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal, "HACKER"), "not.allowed");

			Assert.isTrue(application.getHacker().equals(principal), "not.allowed");

			this.applicationRepository.delete(application.getId());

		}

		// Other business methods -------------------------------

		public Application reconstruct(final Application application, final BindingResult binding) {
			Application result;

			if (application.getId() == 0) {
				result = new Application();
				
				result.setPosition(application.getPosition());
				
			} else {				
				result = this.findOne(application.getId());

				Assert.isTrue(!application.getExplanation().isEmpty(), "explanation.needed");
				Assert.isTrue(!application.getLinkCode().isEmpty(), "link.needed");
				Assert.notNull(application.getCopyCurricula());
				
				result.setExplanation(application.getExplanation());
				result.setLinkCode(application.getLinkCode());
				result.setCopyCurricula(application.getCopyCurricula());
				result.setStatus("SUBMITTED");
			}
			
			this.validator.validate(result, binding);

			return result;
		}
		
		public Collection<Application> findApplicationsByHackerId(int hackerId) {
			Collection<Application> applications;
			
			applications = this.applicationRepository.findApplicationsByHackerId(hackerId);
			
			return applications;
		}
		
		public Collection<Application> findApplicationsByCompanyId(int companyId) {
			Collection<Application> applications;
			
			applications = this.applicationRepository.findApplicationsByCompanyId(companyId);
			
			return applications;
		}
		
		public Problem selectProblem (Collection<Problem> problems) {
			Problem result;
			final SecureRandom rnd = new SecureRandom();
			List<Problem> listProblems = new ArrayList<>(problems);
			
			Integer a = (rnd.nextInt() % 10);
			while(a < 0 || a > problems.size()) {
				a = (rnd.nextInt() % 10);
			}
			
			result = listProblems.get(a);			
			
			return result;
		}

		public void flush() {
			this.applicationRepository.flush();
			
		}

		public Collection<Application> findByProblem(final Problem problem) {
			Assert.notNull(problem);
			final Collection<Application> res = this.applicationRepository.findByProblem(problem.getId());
			return res;
		}

		public Collection<Application> findByPosition(final Position position) {
			Assert.notNull(position);
			final Collection<Application> res = this.applicationRepository.findByPosition(position.getId());
			return res;
		}
}
