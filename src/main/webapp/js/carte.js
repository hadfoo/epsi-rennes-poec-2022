var app = new Vue({
	el: '#app',
	data() {
		return {
			galettes : []
		}
	},
	mounted() {
		axios.get('/public/carte')
		.then(response =>{
			this.galettes = response.data.data;
		});
	}
});