<template>
  <h1>Register</h1>


</template>

<script>
export default {
  name: 'Register',
  data () {
    return {
      title: '',
    }
  },
  mounted() {
    fetch("/GuitarShop/public/register")
        .then((response) => response.text())
        .then((data) => {
          this.title = data;
        })

  }
}
</script>