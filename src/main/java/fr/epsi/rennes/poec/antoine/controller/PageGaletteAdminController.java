package fr.epsi.rennes.poec.antoine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.epsi.rennes.poec.antoine.domain.Galette;
import fr.epsi.rennes.poec.antoine.domain.Ingredient;
import fr.epsi.rennes.poec.antoine.domain.Response;
import fr.epsi.rennes.poec.antoine.service.GaletteService;
import fr.epsi.rennes.poec.antoine.service.IngredientService;

@RestController
public class PageGaletteAdminController {
	
	@Autowired
	private GaletteService galetteService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@GetMapping("/public/galettes")
	public Response<List<Galette>> getAllGalettes(){
		/*
		 * Retourne toutes les galettes disponibles sur la carte
		 */
		List<Galette> galettes = galetteService.getAllGalettes();
		Response<List<Galette>> response = new Response<>();
		response.setData(galettes);
		return response;
	}
	
	@GetMapping("/public/galettes/ingredients")
	public Response<List<Ingredient>> getAllIngredients(){
		/*
		 * Retourne tous les ingrédients qui composent les galettes de la carte
		 */
		List<Ingredient> ingredients = ingredientService.getAllIngredients();
		Response<List<Ingredient>> response = new Response<>();
		response.setData(ingredients);
		return response;
	}

	@PostMapping("/public/galettes/delete")
	public Response<Void> deleteGalette(@RequestBody Galette galette){
		/*
		 * Supprime la galette en paramètre de la carte.
		 */
		this.galetteService.deleteGalette(galette);
		return new Response<>();
	}
	
	@PostMapping("/public/galettes/create")
	public Response<Void> createGalette(@RequestBody Galette galette){
		/*
		 * Ajoute la galette en paramètre à la carte.
		 */
		this.galetteService.createGalette(galette);
		return new Response<>();
	}
	
	@GetMapping("/public/galette")
	public Response<Galette> getGalette(@RequestParam int galetteId) {
		/*
		 * Retourne la galette de la carte identifiée par l'id en paramètre
		 */
		Galette galette = this.galetteService.getGaletteById(galetteId);
		Response<Galette> response = new Response<>();
		response.setData(galette);
		return response;
	}
	
	@PostMapping("/public/galette/update")
	public Response<Galette> updateGalette(@RequestBody Galette updatedGalette){
		/*
		 * Met à jour les attributs de la galette (désormais égaux à ceux de updatedGalette),
		 * identifiée par l'id de updatedGalette.
		 */
		Galette galette = this.galetteService.updateGalette(updatedGalette);
		Response<Galette> response = new Response<>();
		response.setData(galette);
		return response;
	}
}



