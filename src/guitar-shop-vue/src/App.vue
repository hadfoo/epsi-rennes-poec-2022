<template>
  <GuitarShopHome v-if="this.show_home"/>

  <button v-on:click=" this.show_home = !this.show_home"> Login </button>
<ProductList v-if="!this.show_home"/>



</template>

<script>
import GuitarShopHome from './components/GuitarShopHome'
import ProductList from "./components/ProductList";

export default {
  name: 'App',
  components: {
    ProductList,
    GuitarShopHome
  },
  data () {
    return {
      show_home: true
    }
  },
  method: {
    logtoorder () {
      this.show_home = !this.show_home
    }
  }
}
</script>


