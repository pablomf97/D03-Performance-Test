package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Finder;
import domain.Position;

import repositories.FinderRepository;

@Transactional
@Service
public class FinderService {
	
	// Managed repository ------------------------------
		@Autowired
		private FinderRepository finderRepository;

		// Supporting services -----------------------
		@Autowired
		private ActorService actorService;
		
		
		@Autowired
		private SystemConfigurationService systemConfigurationService;
		
		
		// Constructors
		public FinderService() {
			super();
		}
		
		public Finder create(){
			Finder result;
			
			result=new Finder();
			result.setResults(new ArrayList<Position>());
			 return result;
		}
		
		// /FINDONE
		public Finder findOne(final int finderId) {
			Finder result;

			result = this.finderRepository.findOne(finderId);
			

			return result;
		}
		
		// FINDALL
		public Collection<Finder> findAll() {
			Collection<Finder> result;
			result = this.finderRepository.findAll();
			

			return result;

		}
		public Finder save(Finder finder){
			Finder result = null;
			Date currentMoment;
			Actor principal;
			
			currentMoment=new Date(System.currentTimeMillis()-1);
			
			if(finder.getId()!=0){
				try{
					principal=this.actorService.findByPrincipal();
					Assert.isTrue(this.actorService.checkAuthority(principal, "HACKER"),
							"not.allowed");
					
				}catch(Throwable oops){
					return null;
				}
			}
			return result;
			
			
			
		}
		
}
