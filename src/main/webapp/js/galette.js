var app = new Vue({
	el: '#app',
	data() {
		return {
			galette : {},
			ingredients: [],
			newGalette : {}
		}
	},
	mounted() {
		let url = new URL(window.location);
		let galetteId = url.searchParams.get('galetteId');
		
		axios.get('/public/galette?galetteId='+galetteId)
		.then(response => {
			this.galette = response.data.data;
			this.newGalette = Object.assign({},this.galette);
		});
		
		this.loadIngredients();
	},
	updated(){
		this.$nextTick(() => {
			ingredientsGaletteLibelles = [];
			for (ingredient of this.galette.ingredients){
				ingredientsGaletteLibelles.push(ingredient.libelle);
			}
			for (ingredient of this.ingredients){
				if (ingredientsGaletteLibelles.includes(ingredient.libelle)){
					var checkbox = document.getElementById(ingredient.libelle);
					checkbox.checked = true;
				}
			}
		})
	},
	methods: {
		
		loadIngredients(){
			axios.get('/public/galettes/ingredients')
			.then(response => {
				this.ingredients = response.data.data;
			});
		},
		
		updateGalette(){
			this.newGalette.ingredients = [];
			checkboxIngredient = document.getElementsByName("ingredientList");
			for (element of checkboxIngredient){
				if (element.checked){
					for (ingredient of this.ingredients){
						if (ingredient.libelle === element.id){
							this.newGalette.ingredients.push(ingredient);
						}
					}
				}
			}
			
			textLibelle = document.getElementById("textLibelle");
			if (textLibelle.value){
				this.newGalette.libelle = textLibelle.value;
			}
			else {
				this.newGalette.libelle = this.galette.libelle;
			}
			
			axios.post('/public/galette/update', this.newGalette)
			.then(response => {
				if (response.data.success) {
					this.galette = response.data.data;
					this.newGalette = {};
					document.getElementById("confirmer_label").textContent = "Modification confirmée";
				}
			});
		}
	}
});
