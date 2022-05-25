var app = new Vue({
	el: '#app',
	data() {
		return {
			pizzas : [],
			panier: []
		}
	},
	mounted() {
		this.loadPanier();
		
	},
	methods: {
		loadPanier(){
			let panierId = localStorage.getItem('panier.id')
			axios.get('/public/panier?panierId='+ panierId)
			.then(response => {
				this.panier = response.data.data;
				if (!this.panier) {
					this.panier = {pizzas: []};
				}
			})
		},
		
		
		supprimerPizza(pizza){
			axios.post('/public/panier/pizzasup?pizzaId='+pizza.id+'&panierId=' + localStorage.getItem('panier.id'))
			.then(response => {
				if (response.data.success){
					this.loadPanier();
				}
			})
		}
		
	}
});