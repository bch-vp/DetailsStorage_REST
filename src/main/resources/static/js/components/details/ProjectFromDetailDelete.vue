<template>
  <v-btn outline small fab color="indigo" @click="deleteProjectFromDetail">
    <v-icon>delete_forever</v-icon>
  </v-btn>
</template>

<script>
export default {
  props: ['detail', 'project','projects'],
  methods:{
    deleteProjectFromDetail: function () {
      this.$resource('details/' + this.detail.id + '/projects/' + this.project.id).remove().then(result => {
        this.detail.quantityOfAvailable+=this.project.quantityInUsed
        this.projects.splice(this.projects.indexOf(this.project), 1)
      }, ex => {
        alert(ex.status);
      })
    }
  }
}
</script>

<style>

</style>