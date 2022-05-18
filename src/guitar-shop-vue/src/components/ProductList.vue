<template>
  <div class="container" style="float: left">

    <div>

      <ul id="menu">
        <li><a href="#">Filter</a>
          <ul>
            <li v-for="cat in category" v-bind:key="cat.id"><button v-on:click="cat_filter(cat)"> {{ cat }} </button></li>
            <li ><a href="#" v-on:click="reset_filter"> all </a></li>

          </ul>
        </li>

      </ul>

    </div>

    <button v-on:click="debug" style="position: fixed; bottom: 10%">debug</button>
    <table id="allproducttable">
      <tr id="allproducttablehead">
        <th>
          Brand
        </th>
        <th>
          Model
        </th>
        <th>
          Price
        </th>

      </tr>
      <!--      <Product for="product in products"/>-->

      <tr v-for="product in products" v-bind:key="product.id" v-bind:id="product.brand + product.model">
        <td>
          {{ product.brand }}
        </td>
        <td>
          {{ product.model }}
        </td>
        <td>
          {{ product.price }} €
        </td>
        <td>
          <button v-on:click="purchase(product)"> buy</button>
        </td>
      </tr>
    </table>
  </div>

  <div id="cart" style="padding-left: 40%; border: 1px solid black">
    <h1>Cart : {{ sum }}€ </h1>

    <table id="cart table" v-show="purchased_product.length != 0" style="border: 1px solid black">
      <tr id="carttablehead">
        <th>
          Brand
        </th>
        <th>
          Model
        </th>
        <th>
          Price
        </th>

      </tr>
      <!--      <Product for="product in products"/>-->

      <tr v-for="product in purchased_product" v-bind:key="product.id" v-bind:id="product.brand + product.model">
        <td>
          {{ product.brand }}
        </td>
        <td>
          {{ product.model }}
        </td>
        <td>
          {{ product.price }} €
        </td>
        <td>
          <button v-on:click="deletefromcart(product)"> delete</button>
        </td>
      </tr>
    </table>

    <button v-show="purchased_product.length != 0" v-on:click="order">Order</button>


  </div>

  <!--  <CartVue/>-->
</template>

<script>

// import CartVue from "@/components/CartVue";
// import Product from "@/components/Product";
export default {
  name: 'ProductList',
  // props: ['current_login'],
  components: {
    // CartVue,
    // Product
  },
  data() {
    return {
      products: [],
      purchased_product: [],
      sum: 0,
      category: [],
      cat_filter_name: ''
      //current_login: current_login

    }
  },
  mounted() {
    // if (this.products == '') {
    fetch("/GuitarShop/product-list")
        .then((response) => response.json())
        .then((data) => {
          this.products = data;
        });

    fetch("/GuitarShop/category")
        .then((response) => response.json())
        .then((data) => {
          this.category = data;
          console.log(this.category)
        });
  },
  methods: {
    debug(obj) {
      console.log(obj)
    },
    purchase(obj) {
      this.purchased_product.push(JSON.parse(JSON.stringify(obj)))
      this.sum += obj.price
    },
    deletefromcart(obj) {
      this.purchased_product.splice(this.purchased_product.indexOf(obj), 1);
      this.sum -= obj.price
    },
    order() {
      console.log(JSON.stringify(this.purchased_product))
    },
    cat_filter(obj) {
      // fetch("/GuitarShop/product-list/cat=guitar")
      //     .then((response) => response.json())
      //     .then((data) => {
      //       this.products = data;
      //     });
      console.log(obj)
      this.products = []
    },
    reset_filter(){
      fetch("/GuitarShop/product-list")
          .then((response) => response.json())
          .then((data) => {
            this.products = data;
          });
    }


  }
}
</script>

<style>
#allproducttable {
  background-color: #4B8BBE;
}

td {
  background-color: #FFE873;
  border: solid #306998;
  border-width: 3px;
}


/* partie positionnement et déco */
#menu a {
  display: block;
  color: #fff;
  text-decoration: none;
}

#menu > li,
#menu > li li {
  position: relative;
  display: inline-block;
  width: 110px;
  padding: 6px 15px;
  background-color: #777;
  background-image: linear-gradient(#aaa, #888 50%, #777 50%, #999);
}

#menu > li li {
  background: transparent none;
}

#menu > li li a {
  color: #444;
}

#menu > li li:hover {
  background: #eee;
}

#menu > li:first-child {
  border-right: 1px solid #777;
  border-radius: 8px 0 0 8px;
}

#menu > li + li {
  border-left: 1px solid #aaa;
  border-right: 1px solid #777;
}

#menu > li:last-child {
  border-right: 0;
  border-left: 1px solid #aaa;
  border-radius: 0 8px 8px 0;
}

#menu > li:hover {
  background-color: #999;
  background-image: linear-gradient(#ccc, #aaa 50%, #999 50%, #bbb);
}

/* (presque) fin de la partie positionnement/déco */
/* dans cette déclaration, on fixe le max-height */
#menu ul {
  position: absolute;
  top: 2em;
  left: 0;
  max-height: 0em;
  margin: 0;
  padding: 0;
  background-color: #ddd;
  background-image: linear-gradient(#fff, #ddd);
  overflow: hidden;
  transition: 1s max-height 0.3s;
  border-radius: 0 0 8px 8px;
}

/* ici on change la valeur de max-height au :hover */
#menu > li:hover ul {
  /* à adapter, le minimum est le meilleur mais voyez large 😉 */
  max-height: 13em;
}
</style>