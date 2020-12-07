<template>
  <v-btn outline small fab color="deep-purple" @click="deleteProject">
    <v-icon>delete_forever</v-icon>
  </v-btn>
</template>

<script>
export default {
  props: ['project', 'projects'],
  methods:{
    deleteProject: function () {
      this.$resource('/projects/' + this.project.id).remove().then(result => {
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