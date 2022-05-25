<template>


      <tr v-for="product in products" v-bind:key="product.name + product.model" >
        <td>
          {{ product.brand }}
        </td>
        <td>
          {{ product.model }}
        </td>
        <td>
          {{ product.price }} €
        </td>
        <td> <button v-on:click="purchase"> buy </button></td>
      </tr>


</template>

<script>

 export default {
  name: 'ProductInList',
  // props: ['current_login'],
  components: {
  },
  data() {
    return {
      products: '',
    }
  },
  mounted() {

  },
  methods: {
    debug() {
      console.log(this.products)
    },
    purchase() {
      console.log("value")
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