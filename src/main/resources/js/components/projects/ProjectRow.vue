<template>
  <div>
    <project-card v-if="!editObject.edit" :showDetails="showDetails" :project="project" :editObject="editObject" :projects="projects" />
    <project-card-edit v-if="editObject.edit" :projects="projects" :project="project" :editObject="editObject"/>
  </div>
</template>

<script>
import ProjectCard from 'components/projects/ProjectCard.vue'
import ProjectCardEdit from 'components/projects/ProjectCardEdit.vue'

export default {
  components: {ProjectCardEdit, ProjectCard},
  props: ['project', 'projects', 'showDetails'],
  data: function () {
    return {
      editObject: {
        edit: false
      }
    };
  }
}
</script>

<style>
.card {
  margin-top: 30px;
  margin-right: 30px;
  margin-left: 30px;
  margin-bottom: 30px;
}
</style>