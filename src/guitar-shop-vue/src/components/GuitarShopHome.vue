<template>
  <h1>Home</h1>
  <h3> {{ title }} </h3>

</template>

<script>
export default {
  name: 'GuitarShopHome',
  data () {
    return {
      title: '',
    }
  },
  mounted() {
    fetch("/GuitarShop/public")
      .then((response) => response.text())
      .then((data) => {
        this.title = data;
      })

  }
}
</script>