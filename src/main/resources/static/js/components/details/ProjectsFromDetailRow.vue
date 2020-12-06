<template>
  <div>
    <v-btn v-on:click="showProjects.show = false" outline flat round>
      <v-icon>clear</v-icon>
      Close
    </v-btn>
    <detail-card :showProjects="showProjects" class="my-4"/>
    <b v-if="!showAddProjectsToDetail.show">Projects from detail:<span v-if="!projects.length" style="color: red"> empty</span></b>
    <v-btn @click="showAddProjectsToDetail.show = true" v-if="!showAddProjectsToDetail.show" small outline flat round color="indigo">
      <v-icon>add</v-icon>Add projects to detail
    </v-btn>
    <projects-from-detail v-if="!showAddProjectsToDetail.show" :projects="projects"
                          :detail="showProjects.detail"></projects-from-detail>
    <add-projects-to-detail v-if="showAddProjectsToDetail.show" :showAddProjectsToDetail="showAddProjectsToDetail" :projects="projects" :detail="showProjects.detail"/>
  </div>
</template>

<script>
import DetailCard from 'components/details/projectsFromDetail/DetailCard.vue'
import ProjectsFromDetail from 'components/details/projectsFromDetail/ProjectsFromDetail.vue'
import AddProjectsToDetail from 'components/details/projectsFromDetail/AddProjectToDetail.vue'

export default {
  components: {DetailCard, ProjectsFromDetail, AddProjectsToDetail},
  props: ['showProjects'],
  data: function () {
    return {
      showAddProjectsToDetail: {
        show: false,
      },
      projects: []
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