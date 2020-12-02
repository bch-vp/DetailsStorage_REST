<template>
  <div>
    <v-btn v-on:click="showProjects.show = false" flat round>
      <v-icon>clear</v-icon>
      Close
    </v-btn>
    <detail-card :showProjects="showProjects" class="my-4"/>
    <b>Projects:</b>
      <span v-if="!projects.length" style="color: red"> empty</span>
      <span v-else>
       <v-btn @click="showAddProjectsToDetail = !showAddProjectsToDetail" flat round color="indigo">
          <div v-if="!showAddProjectsToDetail">
        <v-icon>add</v-icon>
        Add project to detail
      </div>
      <div v-if="showAddProjectsToDetail">
        <v-icon>clear</v-icon>
       Close
      </div>
    </v-btn>
      </span>
    <projects-from-detail v-if="!showAddProjectsToDetail" :projects="projects" :detail="showProjects.detail"></projects-from-detail>
    <add-projects-from-detail v-if="showAddProjectsToDetail" :projects="projects"/>
  </div>
</template>

<script>
import DetailCard from 'components/details/projectsFromDetail/DetailCard.vue'
import ProjectsFromDetail from 'components/details/projectsFromDetail/ProjectsFromDetail.vue'
import AddProjectsFromDetail from  'components/details/projectsFromDetail/AddProjectToDetail.vue'

export default {
  components: {DetailCard, ProjectsFromDetail, AddProjectsFromDetail},
  props: ['showProjects'],
  data: function () {
    return {
      showAddProjectsToDetail: false,
      projects:[]
    }
  },
  created: function () {
    this.$resource('details/' + this.showProjects.detail.id + '/projects').get().then(result =>
        result.json().then(rows =>
            rows.forEach(row => {
              this.projects.push(row);
              this.projects.sort((a, b) => -(a.id - b.id));
            })
        )
    )
  },
}
</script>

<style>

</style>