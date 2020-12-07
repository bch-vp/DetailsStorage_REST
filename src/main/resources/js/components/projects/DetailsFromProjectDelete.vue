<template>
  <v-btn outline small fab color="indigo" @click="deleteDetailFromProject">
    <v-icon>delete_forever</v-icon>
  </v-btn>
</template>

<script>
export default {
  props: ['project', 'detail','details'],
  methods:{
    deleteDetailFromProject: function () {
      this.$resource('projects/' + this.project.id + '/details/' + this.detail.id).remove().then(result => {
        this.detail.quantityOfAvailable += parseInt(this.detail.quantityInUsed)
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