var app = new Vue({
    el: '#app',
    data() {
        return {
            pizzas: [],
            panier: {},
            somme: 0.00,
        }
    },
    mounted() {
        axios.get('/public/pizzas')
            .then(response => {
                this.pizzas = response.data.data;
            });
        let panierId = localStorage.getItem('panier.id');
        axios.get('/public/panier?panierId=' + panierId)
            .then(response => {
                this.panier = response.data.data;
            })
        axios.get('/public/prix_panier?panierId=' + parseInt(panierId))
            .then(response => {
                this.somme = response.data.data;
            });
    },
    methods: {
        ajouterPizza(pizza) {
            pizza.prix = undefined;
            let panierId = localStorage.getItem('panier.id');
            if (!panierId) {
                panierId = -1;
            }
            axios.post('/public/panier/pizza' +
                '?panierid=' + panierId +
                '&pizzaid=' + pizza.id)
                .then(response => {
                    if (response.data.success) {
                        localStorage.setItem('panier.id', response.data.data);
                        axios.get('/public/pizzas')
                            .then(response => {
                                this.pizzas = response.data.data;
                            });
                        axios.get('/public/panier?panierId=' + response.data.data)
                            .then(response => {
                                this.panier = response.data.data;
                            })
                        axios.get('/public/prix_panier?panierId=' + parseInt(panierId))
                            .then(response => {
                                this.somme = response.data.data;
                            });
                    }
                })
        }
    }
});
