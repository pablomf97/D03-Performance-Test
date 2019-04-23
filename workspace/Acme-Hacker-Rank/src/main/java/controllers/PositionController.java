
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.PositionService;
import services.ProblemService;
import domain.Actor;
import domain.Position;
import domain.Problem;

@Controller
@RequestMapping("/position")
public class PositionController extends AbstractController {

	@Autowired
	private ActorService	actorService;

	@Autowired
	private PositionService	positionService;

	@Autowired
	private ProblemService	problemService;


	public PositionController() {
		super();
	}

	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAll(final Integer id) {
		ModelAndView result;
		try {
			Collection<Position> positions = new ArrayList<>();
			if (id != null) {
				final Actor actor = this.actorService.findOne(id);
				positions = this.positionService.findByOwner(actor);
			} else
				positions = this.positionService.findAllFinal();
			result = new ModelAndView("position/list");
			result.addObject("requestURI", "/position/list.do");
			result.addObject("positions", positions);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "position.commit.error");
		}
		return result;
	}
	
	@RequestMapping(value = "/hacker/listAll", method = RequestMethod.GET)
	public ModelAndView listAllHacker() {
		ModelAndView result;
		try {
			Collection<Position> applied, positions = new ArrayList<>();
			Actor principal = this.actorService.findByPrincipal();
			applied = this.positionService.findAllAppliedPositionsByHackerId(principal.getId());
			positions = this.positionService.findAllToApply();
			positions.removeAll(applied);
			
			result = new ModelAndView("position/list");
			result.addObject("requestURI", "/position/list.do");
			result.addObject("positions", positions);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listLoged() {
		ModelAndView result;
		try {
			result = new ModelAndView("position/list");
			final Actor actor = this.actorService.findByPrincipal();
			final Collection<Position> positions = this.positionService.findByOwner(actor);
			result.addObject("requestURI", "/position/list.do");
			result.addObject("positions", positions);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "position.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deletePosition(@RequestParam final int Id) {
		ModelAndView result;
		try {
			final Position position = this.positionService.findOne(Id);
			if (position.getIsDraft() == false)
				this.positionService.cancel(position);
			else
				this.positionService.delete(position);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageCode", "position.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView savePositionFinal(Position position, final BindingResult binding) {
		ModelAndView result;

		try {

			position = this.positionService.reconstruct(position, binding);
			if (binding.hasErrors()) {
				result = new ModelAndView("position/edit");
				result.addObject("position", position);
				final Actor actor = this.actorService.findByPrincipal();
				final Collection<Problem> problems = this.problemService.findByOwner(actor);
				result.addObject("problems", problems);
			} else
				try {
					position.setIsDraft(false);
					this.positionService.save(position);
					result = new ModelAndView("redirect:list.do");
				} catch (final Throwable opps) {
					opps.printStackTrace();
					result = new ModelAndView("position/edit");
					final Actor actor = this.actorService.findByPrincipal();
					final Collection<Problem> problems = this.problemService.findByOwner(actor);
					result.addObject("problems", problems);
					result.addObject("position", position);
					result.addObject("messageCode", "position.commit.error");
				}
		} catch (final Throwable opps) {
			//TODO: pantalla de error
			opps.printStackTrace();
			result = new ModelAndView("redirect:misc/error");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView savePosition(Position position, final BindingResult binding) {
		ModelAndView result;
		try {

			position = this.positionService.reconstruct(position, binding);
			if (binding.hasErrors()) {
				result = new ModelAndView("position/edit");
				result.addObject("position", position);
				final Actor actor = this.actorService.findByPrincipal();
				final Collection<Problem> problems = this.problemService.findByOwner(actor);
				result.addObject("problems", problems);
			} else
				try {
					this.positionService.save(position);
					result = new ModelAndView("redirect:list.do");
				} catch (final Throwable opps) {
					opps.printStackTrace();
					result = new ModelAndView("position/edit");
					final Actor actor = this.actorService.findByPrincipal();
					final Collection<Problem> problems = this.problemService.findByOwner(actor);
					result.addObject("problems", problems);
					result.addObject("position", position);
					result.addObject("messageCode", "position.commit.error");
				}
		} catch (final Throwable opps) {
			//TODO: pantalla de error
			opps.printStackTrace();
			result = new ModelAndView("redirect:misc/error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editPosition(@RequestParam final int Id) {
		ModelAndView result;
		Position position;
		try {
			position = this.positionService.findOne(Id);
			result = new ModelAndView("position/edit");
			result.addObject(position);
			final Actor actor = this.actorService.findByPrincipal();
			final Collection<Problem> problems = this.problemService.findByOwner(actor);
			result.addObject("problems", problems);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageCode", "position.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView displayPosition(@RequestParam final int Id) {
		ModelAndView result;
		Position position;
		try {
			position = this.positionService.findOne(Id);
			result = new ModelAndView("position/display");
			result.addObject(position);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageCode", "position.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final Actor actor = this.actorService.findByPrincipal();
			result = new ModelAndView("position/edit");
			final Position position = this.positionService.create(actor);
			result.addObject("position", position);
			final Collection<Problem> problems = this.problemService.findByOwner(actor);
			result.addObject("problems", problems);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageCode", "position.commit.error");
		}
		return result;

	}
}
