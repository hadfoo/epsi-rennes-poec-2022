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
            axios.post('/public/pizza/remove', pizza.id)
                .then(response => {
                    if (response.data.success) {
                        this.loadPanier();
                    }
                })
        }
    }
});