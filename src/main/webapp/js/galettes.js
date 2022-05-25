var app = new Vue({
	el: '#app',
	data() {
		return {
			galettes : [],
			ingredients : [],
			isCreerGalette : false,
			newGalette : {},
			newIngredient : {}
		}
	},
	mounted() {
		this.loadGalettes();
		this.loadIngredients()
	},
	methods: {
		loadGalettes(){
			axios.get('/public/galettes')
			.then(response =>{
				this.galettes = response.data.data;
			});
		},
		
		loadIngredients(){
			axios.get('/public/galettes/ingredients')
			.then(response => {
				this.ingredients = response.data.data;
			});
		},
		
		supprimerGalette(galette){
			axios.post('/public/galettes/delete', galette)
			.then(response => {
				if (response.data.success) {
					this.loadGalettes();
				}
			});
		},
		
		addIngredient() {
			if (!this.newGalette.ingredients) {
				this.newGalette.ingredients = [];
			}
			this.newGalette.ingredients.push(this.newIngredient);
			this.newIngredient = {};
		},
		
		createGalette() {
			axios.post('/public/galettes/create', this.newGalette)
			.then(response => {
				if (response.data.success) {
					this.newGalette = {};
					this.newIngredient = {};
					this.isCreerGalette = false;
					
					axios.get('/public/galettes')
					.then(response => {
						this.galettes = response.data.data;
					});
				}
			});
		}
	}
});


/*var app = new Vue({
	el: '#app',
	data() {
		return {
			pizzas: [],
			ingredients: [],
			create: false,
			newPizza: {},
			newIngredient: {}
		}
	},
	mounted() {
		axios.get('/admin/pizzas')
		.then(response => {
			this.pizzas = response.data.data;
		});
		axios.get('/admin/ingredients')
		.then(response => {
			this.ingredients = response.data.data;
		});
	},
	methods: {
		addIngredient() {
			if (!this.newPizza.ingredients) {
				this.newPizza.ingredients = [];
			}
			this.newPizza.ingredients.push(this.newIngredient);
			this.newIngredient = {};
		},
		createPizza() {
			axios.post('/admin/pizza/create', this.newPizza)
			.then(response => {
				if (response.data.success) {
					this.newPizza = {};
					this.newIngredient = {};
					this.create = false;
					
					axios.get('/admin/pizzas')
					.then(response => {
						this.pizzas = response.data.data;
					});
				}
			})
		}
	}
});*/