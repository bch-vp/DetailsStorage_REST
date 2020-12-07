<template>
  <v-app>
    <v-toolbar dark app>
      <v-toolbar-title v-if="showDetails" style="color: coral;">DetailsStorage</v-toolbar-title>
      <v-toolbar-title v-if="showProjects" style="color: greenyellow;">DetailsStorage</v-toolbar-title>
        <v-btn @click="showDetails=true; showProjects=false" flat style="margin-left: 30px" round>
          <span v-if="showDetails" class="font-weight-black subheading">Details</span>
          <span v-else>Details</span>
        </v-btn>
        <v-btn @click="showDetails=false; showProjects=true" flat round>
          <span v-if="showProjects" class="font-weight-black subheading">Projects</span>
          <span v-else>Projects</span>
        </v-btn>
      <v-spacer></v-spacer>
      <v-toolbar-items class="hidden-sm-and-down">
        <v-btn icon href="#/">
          <v-icon>exit_to_app</v-icon>
        </v-btn>
      </v-toolbar-items>
    </v-toolbar>

    <v-content>
      <v-container>
        <details-list v-if="showDetails" :details="details"/>
        <projects-list v-if="showProjects"/>
      </v-container>
    </v-content>

  </v-app>
</template>

<script>

import DetailsList from "components/details/DetailsList.vue";
import ProjectsList from "components/projects/ProjectsList.vue";

export default {
  components: {DetailsList, ProjectsList},
  comments: {
    DetailsList
  },
  data: function () {
    return {
      details: [],
      showDetails: true,
      showProjects: false
    }
  }
}
</script>


<style>
</style>