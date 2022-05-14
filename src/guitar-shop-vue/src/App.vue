<template>
  <GuitarShopHome/>

<ProductList/>



</template>

<script>
import GuitarShopHome from './components/GuitarShopHome'
import ProductList from "./components/ProductList";

export default {
  name: 'App',
  components: {
    ProductList,
    GuitarShopHome
  }
}
</script>


