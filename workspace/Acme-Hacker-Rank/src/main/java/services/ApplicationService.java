package services;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ApplicationRepository;
import domain.Actor;
import domain.Application;
import domain.Hacker;
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
			Assert.isTrue(application.getHacker().equals((Hacker) principal));
			
			if (this.actorService.checkAuthority(principal, "MEMBER")) {
				
				if(application.getId() == 0) {
					Collection<Problem> problems;
					Problem toSolve;
		
					Assert.isTrue(application.getStatus() == "PENDING");
					
					problems = this.problemService.findProblemsByPositionId(application.getPosition().getId());
					
					toSolve = this.selectProblem(problems);
					
					application.setProblem(toSolve);					
					
				} else {
					
					Assert.notNull(application.getExplanation());
					Assert.notNull(application.getLinkCode());
					Assert.notNull(application.getCopyCurricula());
					Assert.isTrue(application.getStatus() == "PENDING");
					Assert.isTrue(application.getApplicationMoment().before(application.getSubmitMoment()));
					
					application.setStatus("SUBMITTED");
					application.setSubmitMoment(new Date(System.currentTimeMillis() - 1));
					
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

				Assert.isTrue(application.getId() == 0);
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
		
		private Problem selectProblem (Collection<Problem> problems) {
			Problem result;
			final SecureRandom rnd = new SecureRandom();
			Problem[] arrayProblems = (Problem[]) problems.toArray();
			
			Integer a = (rnd.nextInt() % 10);
			while(a > problems.size()) {
				a = (rnd.nextInt() % 10);
			}
			
			result = arrayProblems[a];			
			
			return result;
		}

		public void flush() {
			this.applicationRepository.flush();
			
		}

		
}
