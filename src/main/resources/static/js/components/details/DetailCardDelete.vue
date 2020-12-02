<template>
  <v-btn outline small fab color="indigo" @click="deleteDetail">
    <v-icon>delete_forever</v-icon>
  </v-btn>
</template>

<script>
export default {
  props: ['detail', 'details'],
  methods:{
    deleteDetail: function () {
      this.$resource('/details/' + this.detail.id).remove().then(result => {
        this.details.splice(this.details.indexOf(this.detail), 1)
      }, ex => {
        alert(ex.status);
      })
    }
  }
}
</script>

<style>
</style>