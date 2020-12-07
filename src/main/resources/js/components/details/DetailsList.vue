<template>
  <div>
    <div v-if="showProjects.show">
      <projects-from-detail-row :showProjects="showProjects"/>
    </div>

    <div v-if="!showProjects.show">
      <v-btn v-on:click="showForm = !showForm" v-if="!showForm" outline flat round>
        <v-icon>add</v-icon>
        Add detail
      </v-btn>
      <v-btn v-on:click="showForm = !showForm" v-if="showForm" outline flat round>
        <v-icon>close</v-icon>
        Close
      </v-btn>
      <detail-form v-if="showForm" :details="details"/>
      <v-layout align-start justify-center row fill-height>
        <detail-row v-for="detail in details" :detail="detail" :details="details" :showProjects="showProjects"
                    :key="detail.id"/>
      </v-layout>
    </div>
  </div>
</template>

<script>
import DetailRow from "components/details/DetailRow.vue"
import DetailForm from 'components/details/DetailForm.vue'
import ProjectsFromDetailRow from 'components/details/ProjectsFromDetailRow.vue'

export default {
  components: {DetailForm, DetailRow, ProjectsFromDetailRow},
  data: function () {
    return {
      details:[],
      showProjects: {
        show: false,
        detail: null
      },
      showForm: false
    }
  },
  created: function () {
    this.$resource('/details').get().then(result =>
        result.json().then(details => {
          details.forEach(detail => {
            this.details.push(detail)
            this.details.sort((a, b) => -(a.id - b.id))
          })
          this.details.sort((a, b) => -(a.id - b.id))
        })
    )
  }
}
</script>

<style>

</style>