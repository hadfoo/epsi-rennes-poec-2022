var app = new Vue({
    el: '#app',
    data: function () {
        return {
            pizzas: [],
            panier: [],
            pizza_panier: [],
            somme: 0,

        }
    },
    mounted() {
        let panierId = localStorage.getItem('panier.id');
        axios.get('/public/pizza_panier?panierId=' + panierId)
            .then(response => {
                this.pizza_panier = response.data.data;
            });
        axios.get('/public/panier?panierId=' + panierId)
            .then(response => {
                this.panier = response.data.data;
            });
        axios.get('/public/pizzas')
            .then(response => {
                this.pizzas = response.data.data;
            });
        axios.get('/public/prix_panier?panierId=' + parseInt(panierId))
            .then(response => {
                this.somme = response.data.data;
            });
    },
    methods: {
        delPizza(pizza) {
            let panierId = localStorage.getItem('panier.id');
            let pizzaId = pizza.id
            axios.post('/public/panier/pizza_del?panierId=' + panierId +
                '&pizzaId=' + pizzaId)
                .then(response => {
                    if (response.data.success) {
                        this.pizza_panier = response.data.data;
                        axios.get('/public/panier?panierId=' + panierId)
                            .then(response => {
                                this.panier = response.data.data;
                            })
                        axios.get('/public/pizza_panier?panierId=' + panierId)
                            .then(response => {
                                this.pizza_panier = response.data.data;
                            })
                        axios.get('/public/prix_panier?panierId=' + parseInt(panierId))
                            .then(response => {
                                this.somme = response.data.data;
                            });

                    }
                })
        },
    },
});