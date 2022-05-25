console.log("aaaaaaaaaaaaaaaaaaaaa")
var app = new Vue({
	el: '#app',
	data() {
		return {
			pizzas : [],
			panier: {}
		}
	},
	mounted() {
		axios.get('/public/pizzas')
		.then(response =>{
			console.log(response.data)
			this.pizzas = response.data.data;
		});
		
	},
	methods: {
		ajouterPizza(pizza){
			let panierId = localStorage.getItem('panier.id');
			if (!panierId){
				panierId = -1;
			}
			axios.post('/public/panier/pizza?panierId='+panierId+'&pizzaId=' + pizza.id)
			.then(response => {
				if (response.data.success){
					localStorage.setItem('panier.id', response.data.data);
					axios.get('/public/panier?panierId=' + response.data.data)
					.then(response => {
						this.panier = response.data.data;
					})
				}
			})
		}
		
	}
});