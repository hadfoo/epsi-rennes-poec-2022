<template>
  <div class="container">
    <button v-on:click="debug">debug</button>
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

      <tr v-for="product in products" v-bind:key="product.id">
        <td>
          {{ product.brand }}
        </td>
        <td>
          {{ product.brand }}
        </td>
        <td>
          {{ product.price }} €
        </td>
      </tr>
    </table>
  </div>
</template>

<script>

export default {
  name: 'ProductList',
  // props: ['current_login'],
  data() {
    return {
      products: '',
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
    //}

  },
  methods: {
    debug() {
      console.log("this.products")
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

</style>