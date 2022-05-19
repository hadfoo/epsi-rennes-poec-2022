var app = new Vue({
	el: '#app',
	data() {
		return {
			pizza: {}
		}
	},
	mounted() {
		let url = new URL(window.location);
		let pizzaId = url.searchParams.get('pizzaId');
		
		axios.get('/public/pizza?pizzaId='+pizzaId)
		.then(response => {
			this.pizza = response.data.data;
		});
	},
	methods: {
	}
});