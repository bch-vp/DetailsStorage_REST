<template>
  <div>
  <v-layout align-start justify-center row fill-height>
    <div v-for="project in projects">
      <v-card class="card ">
        <v-card-text primary-title>

          <b style="color: cornflowerblue"> Quantity of details, which used:
            <span style="color: darkblue"> {{ project.quantityInUsed }}</span>
          </b>
          <br><br>

          <b>Id:</b>
          {{ project.id }} <br>

          <b>Project name:</b>
          {{ project.projectName }} <br>

          <b>Type:</b>
          {{ project.type }} <br>

          <b>Quantity:</b>
          {{ project.quantity }} <br>

          <b>Storage:</b>
          {{ project.storage }} <br>

        </v-card-text>
        <v-card-actions>
          <projects-in-detail-delete :detail="detail" :project="project" :projects="projects"/>
        </v-card-actions>
      </v-card>
    </div>
  </v-layout>
  </div>
</template>

<script>
import ProjectsInDetailDelete from 'components/details/ProjectFromDetailDelete.vue'

export default {
  components:{ProjectsInDetailDelete},
  props:['detail','projects'],
}
</script>

<style>

</style>