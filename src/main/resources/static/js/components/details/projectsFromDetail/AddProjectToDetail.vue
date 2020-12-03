<template>
  <div>
    <v-btn @click="submit" outline small flat round color="indigo" class="my-3">submit</v-btn>
  <v-layout align-start justify-center row fill-height>
    <div  v-for="(project, index) in projectsWhichNotIncludeInDetail">
      <add-project-to-detail-card :index="index" :projectWhichChose="projectWhichChose" :project="project" :projectsWhichNotIncludeInDetail="projectsWhichNotIncludeInDetail"/>
    </div>
  </v-layout>
  </div>
</template>

<script>
import {getIndex} from "util/collections";
import AddProjectToDetailCard from 'components/details/projectsFromDetail/AddProjectToDetailCard.vue'

export default {
  props: ['projects'],
  components: {AddProjectToDetailCard},
  data: function () {
    return {
      projectsWhichNotIncludeInDetail: [],
      projectWhichChose:{
        projects:[],
        quantity:[]
      }
    }
  },
  methods:{
    submit:function (){

    }
  },
  created: function () {
    this.$resource('/projects').get().then(result =>
        result.json().then(projectsFromDb => {
              this.projects.forEach(project => {
                projectsFromDb.splice(getIndex(projectsFromDb, project.id), 1)
              })
              this.projectsWhichNotIncludeInDetail = projectsFromDb
              this.projectsWhichNotIncludeInDetail.sort((a, b) => -(a.id - b.id));
            }
        )
    )
  }
}
</script>

<style>

</style>