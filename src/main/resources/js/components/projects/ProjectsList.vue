<template>
  <div>
    <div v-if=" showDetails.show">
      <details-from-project-row :showDetails="showDetails"/>
    </div>

    <div v-if="!showDetails.show">
      <v-btn v-on:click="showForm = !showForm" v-if="!showForm" outline flat round>
        <v-icon>add</v-icon>
        Add Project
      </v-btn>
      <v-btn v-on:click="showForm = !showForm" v-if="showForm" outline flat round>
        <v-icon>close</v-icon>
        Close
      </v-btn>
      <project-form v-if="showForm" :projects="projects"/>
      <v-layout align-start justify-center row fill-height>
        <project-row v-for="project in projects" :project="project" :projects="projects" :showDetails="showDetails"
                    :key="project.id"/>
      </v-layout>
    </div>
  </div>
</template>

<script>
import ProjectRow from "components/projects/ProjectRow.vue"
import ProjectForm from 'components/projects/ProjectForm.vue'
import  DetailsFromProjectRow from 'components/projects/DetailsFromProjectRow.vue'

export default {
  components: {ProjectForm, ProjectRow, DetailsFromProjectRow},
  data: function () {
    return {
      projects:[],
      showDetails: {
        show: false,
        project: null
      },
      showForm: false
    }
  },
  created: function () {
    this.$resource('/projects').get().then(result =>
        result.json().then(projects => {
          projects.forEach(projetc => {
            this.projects.push(projetc)
            this.projects.sort((a, b) => -(a.id - b.id))
          })
          this.projects.sort((a, b) => -(a.id - b.id))
        })
    )
  }
}
</script>

<style>

</style>