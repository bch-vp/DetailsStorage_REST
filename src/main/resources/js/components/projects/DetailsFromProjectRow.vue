<template>
  <div>
    <v-btn v-on:click="showDetails.show = false" outline flat round>
      <v-icon>clear</v-icon>
      Close
    </v-btn>
    <project-card :showDetails="showDetails" class="my-4"/>
    <b v-if="!showAddDetailsToProject.show">Details from project:<span v-if="!details.length" style="color: red"> empty</span></b>
    <v-btn @click="showAddDetailsToProject.show = true" v-if="!showAddDetailsToProject.show" small outline flat round color="indigo">
      <v-icon>add</v-icon>Add details to project
    </v-btn>
    <details-from-project v-if="!showAddDetailsToProject.show" :details="details"
                          :project="showDetails.project"></details-from-project>
    <add-details-to-project v-if="showAddDetailsToProject.show" :showAddDetailsToProject="showAddDetailsToProject" :details="details" :project="showDetails.project"/>
  </div>
</template>

<script>
import ProjectCard from 'components/projects/detailsFromProject/ProjectCard.vue'
import DetailsFromProject from 'components/projects/detailsFromProject/DetailsFromProject.vue'
import AddDetailsToProject from 'components/projects/detailsFromProject/AddDetailToProject.vue'

export default {
  components: {ProjectCard, DetailsFromProject, AddDetailsToProject},
  props: ['showDetails'],
  data: function () {
    return {
      showAddDetailsToProject: {
        show: false,
      },
      details: []
    }
  },
  created: function () {
    this.$resource('projects/' + this.showDetails.project.id + '/details').get().then(result =>
        result.json().then(rows =>
            rows.forEach(row => {
              this.details.push(row);
              this.details.sort((a, b) => -(a.id - b.id));
            })
        )
    )
  },
}
</script>

<style>

</style>