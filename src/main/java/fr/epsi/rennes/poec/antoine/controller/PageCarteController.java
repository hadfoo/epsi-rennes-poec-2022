package fr.epsi.rennes.poec.antoine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.epsi.rennes.poec.antoine.domain.Galette;
import fr.epsi.rennes.poec.antoine.domain.Response;
import fr.epsi.rennes.poec.antoine.service.GaletteService;

@RestController
public class PageCarteController {
	
	@Autowired
	private GaletteService galetteService;
	
	@GetMapping("/public/carte")
	public Response<List<Galette>> getAllGalettes(){
		/*
		 * Retourne toutes les galettes de la carte
		 */
		List<Galette> galettes = galetteService.getAllGalettes();
		Response<List<Galette>> response = new Response<>();
		response.setData(galettes);
		return response;
	}

}
