var app = new Vue({
	el: '#app',
	data() {
		return {
			panier: {}
		}
	},
	mounted() {
		this.loadPanier();
	},
	methods: {
		loadPanier() {
			let panierId = localStorage.getItem('panier.id');
			axios.get('/public/panier?panierId=' + panierId)
			.then(response => {
				this.panier = response.data.data;
			})
		},
		supprimerPizza(pizza) {
			let panierId = localStorage.getItem('panier.id');
			if (panierId){
				panierId -1;
			}
			axios.post('/public/pizza/remove'+ '?pizzaId=' + pizza.id + '&panierId=' + panierId)
			.then(response => {
				if (response.data.success) {
					this.loadPanier();
				}
			})
		}
	}
});