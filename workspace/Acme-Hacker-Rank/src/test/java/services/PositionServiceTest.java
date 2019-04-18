
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import utilities.AbstractTest;
import domain.Actor;
import domain.Position;
import domain.Problem;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PositionServiceTest extends AbstractTest {

	@Autowired
	private ActorService	actorService;

	@Autowired
	private Validator		validator;

	@Autowired
	private PositionService	positionService;

	@Autowired
	private ProblemService	problemService;


	@Test
	public void driver() {
		final Collection<Problem> problems = new ArrayList<>();
		final Collection<Problem> problems2 = new ArrayList<>();

		problems2.add(this.problemService.findOne(this.getEntityId("problem2c1")));
		problems2.add(this.problemService.findOne(this.getEntityId("problem1c3")));

		final Object testingData[][] = {
			{
				"Test 1", "Test 1", "22/06/2019 00:00", "Test", "Test", 100.00, "pru-7890", "test", "company1", problems, true, false, null
			},// Positivo:crear
				// normal

			{
				"", "Test 1", "22/06/2019 00:00", "Test", "Test", 100.00, "pru-7892", "test", "company1", problems, true, false, IllegalArgumentException.class
			},// Negativo->RN:Title
				// no puedes ser
				// notBlank
			{
				null, "Test 1", "22/06/2019 00:00", "Test", "Test", 100.00, "pru-7893", "test", "company1", problems, true, false, NullPointerException.class
			},// Negativo->RN:Title
				// no puedes ser
				// notNull
			{
				"Test 1", "Test 1", "22/06-2019 00:00", "Test", "Test", 100.00, "pru-7894", "test", "company1", problems, true, false, IllegalArgumentException.class
			},// Negativo->RN:fecha
				// no
				// tiene
				// el
				// formato
				// adecuado
			{
				"Test 1", null, "22/06/2019 00:00", "Test", "Test", null, "pru-7895", null, "company1", problems, true, false, NullPointerException.class
			},// Negativo->RN:nombre,
				// datos nulos
			{
				"Test 1", "Test 1", "22/06/2019 00:00", "Test", "Test", 100.00, "pru-7896", "test", "company100", problems, true, false, NullPointerException.class
			},// Negativo->Intento de
				// registro logueado
				// como un actor no
				// existente en la
				// DB
			{

				"Test 1", "Test 1", "22/06/2019 00:00", "Test", "Test", -100.00, "pru-7897", "test", "company1", problems, true, false, IllegalArgumentException.class
			},// Negativo->salario negativo
			{

				"Test 1", "Test 1", "22/06/2019 00:00", "Test", "Test", -100.00, "pru-7898", "test", "company1", problems, false, false, IllegalArgumentException.class
			},// Negativo->final con menos de 2 problemas

			{

				"Test 1", "Test 1", "22/06/2019 00:00", "Test", "Test", -100.00, "pru-7898", "test", "company2", problems2, false, false, IllegalArgumentException.class
			},// Negativo->guardar con problemas que no le pertenecen
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (String) testingData[i][1], (Date) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Double) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (Collection<Problem>) testingData[i][9], (Boolean) testingData[i][10], (Boolean) testingData[i][11], (Class<?>) testingData[i][12]);
	}
	private void template(final String title, final String description, final Date deadline, final String profileRequired, final String technologiesRequired, final Double salary, final String ticker, final String skillsRequired, final String company,
		final Collection<Problem> problems, final Boolean isDraft, final Boolean isCancelled, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(company);

			this.saveProblem(title, description, deadline, profileRequired, technologiesRequired, salary, ticker, skillsRequired, company, problems, isDraft, isCancelled);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
	private void saveProblem(final String title, final String description, final Date deadline, final String profileRequired, final String technologiesRequired, final Double salary, final String ticker, final String skillsRequired, final String company,
		final Collection<Problem> problems, final Boolean isDraft, final Boolean isCancelled) {
		Actor principal;
		principal = this.actorService.findByPrincipal();
		final Position result = this.positionService.create(principal);
		result.setDeadline(deadline);
		result.setDescription(description);
		result.setIsDraft(isDraft);
		result.setIsCancelled(isCancelled);
		result.setProfileRequired(profileRequired);
		result.setProblems(problems);
		result.setSalary(salary);
		result.setTicker(ticker);
		result.setSkillsRequired(skillsRequired);
		result.setTechnologiesRequired(technologiesRequired);
		result.setTitle(title);
		final BindingResult binding = null;

		result.setTitle(title);

		this.validator.validate(result, binding);
		this.positionService.save(result);
		this.positionService.flush();

	}

	@Test
	public void driver2() {
		final Object testingData[][] = {
			{
				"position3c1", "company1", null
			},// Positivo:borrar
				// normal
			{
				"position1c2", "company1", IllegalArgumentException.class
			},// Negativo:borrar
				// no pertenece
			{
				"position1c1", "company1", IllegalArgumentException.class
			},// Negativo:borrar
				// usado

		};

		for (int i = 0; i < testingData.length; i++) {
			System.out.println(i);
			this.template2((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	private void template2(final String id, final String company, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(company);
			final Integer idEntity = this.getEntityId(id);

			this.deletePosition(idEntity);
			this.unauthenticate();
		} catch (final Throwable oops) {
			oops.printStackTrace();
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void deletePosition(final Integer idEntity) {
		final Position p = this.positionService.findOne(idEntity);

		this.positionService.delete(p);
		this.positionService.flush();

	}

	@Test
	public void driver3() {
		final Object testingData[][] = {

			{
				"position3c1", "company1", "title test", null
			},// Positivo:editar
				// normal
			{
				"position3c1", "company2", "title test", IllegalArgumentException.class
			},// Negativo:editar
				// no pertenece
			{
				"position1c1", "company1", "title test", IllegalArgumentException.class
			},// Negativo:editar
				// no borrador
		};

		for (int i = 0; i < testingData.length; i++)
			this.template3((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][1], (Class<?>) testingData[i][3]);
	}

	private void template3(final String id, final String company, final String title, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(company);
			final Integer idEntity = this.getEntityId(id);

			this.editProblem(idEntity, title);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void editProblem(final Integer idEntity, final String title) {
		final Position p = this.positionService.findOne(idEntity);
		p.setTitle(title);

		this.positionService.save(p);
		this.positionService.flush();

	}

}
