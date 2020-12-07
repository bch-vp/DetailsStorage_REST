<template>
  <div>
    <div class="my-3">
      <v-btn @click="submit" outline small flat round color="indigo" class="my-3">submit</v-btn>
      <v-btn @click="showAddProjectsToDetail.show = false" v-if="showAddProjectsToDetail.show" outline small flat round
             color="indigo">
        <v-icon>close</v-icon>
        Close
      </v-btn>
    </div>
    <div class=" font-weight-light subheading ">
      Quantity of details, which available:
      <span v-if="quantityOfAvailable.quantity>=0" class="headline font-weight-regular" style="color: forestgreen">
        {{ quantityOfAvailable.quantity }}
      </span>
      <span v-if="quantityOfAvailable.quantity<0" class="headline font-weight-regular" style="color: red">
        {{ quantityOfAvailable.quantity }} cannot be negative
      </span>
    </div>
    <v-layout align-start justify-center row fill-height>
      <div v-for="(project, index) in projectsWhichNotIncludeInDetail">
        <add-project-to-detail-card :detail="detail" :quantityOfAvailable="quantityOfAvailable" :index="index"
                                    :projectWhichChose="projectWhichChose" :project="project"
                                    :projectsWhichNotIncludeInDetail="projectsWhichNotIncludeInDetail"/>
      </div>
    </v-layout>
  </div>
</template>

<script>
import {getIndex} from "util/collections";
import AddProjectToDetailCard from 'components/details/projectsFromDetail/AddProjectToDetailCard.vue'

export default {
  props: ['projects', 'detail', 'showAddProjectsToDetail'],
  components: {AddProjectToDetailCard},
  data: function () {
    return {
      projectsWhichNotIncludeInDetail: [],
      projectWhichChose: {
        projectsId: [],
        quantity: []
      },
      quantityOfAvailable: {
        quantity: this.detail.quantityOfAvailable
      },
    }
  },
  watch: {
    projectWhichChose: {
      deep: true,
      handler: function () {
        let i = 0
        let quantityOfAvailable = 0;
        this.projectWhichChose.quantity.forEach(quantity => {
          if (quantity !== 0) {
            this.projectWhichChose.projectsId[i] = this.projectsWhichNotIncludeInDetail[i]
            quantityOfAvailable += this.projectWhichChose.quantity[i]
          } else {
            this.projectWhichChose.projectsId[i] = null
          }
          i++
        })
        this.quantityOfAvailable.quantity = this.detail.quantityOfAvailable - quantityOfAvailable
      }
    }
  },
  methods: {
    submit: function () {
      if (this.quantityOfAvailable.quantity >= 0) {
        let i = 0
        this.projectWhichChose.projectsId.forEach(projectId => {
          if (projectId !== null) {
            let quantityObject = {
              quantity: String(this.projectWhichChose.quantity[i])
            }
            this.$resource('details/' + this.detail.id + '/projects/' + projectId.id).save({}, quantityObject).then(result => {
              let indexx = getIndex(this.projectsWhichNotIncludeInDetail, projectId.id)
              this.detail.quantityOfAvailable -= parseInt(quantityObject.quantity)
              this.projectsWhichNotIncludeInDetail[indexx].quantityInUsed = quantityObject.quantity
              this.projects.push(this.projectsWhichNotIncludeInDetail[indexx])
              // this.projects[indexx].quantityInUsed = this.projectWhichChose.quantity[i]
              this.projects.sort((a, b) => -(a.id - b.id));
              this.projectsWhichNotIncludeInDetail.splice(indexx, 1)
              this.showAddProjectsToDetail.show = false
            }, ex => {
              alert(ex.status);
            })
          }
          i++
        })
      }
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
              let i = 0;
              this.projectsWhichNotIncludeInDetail.forEach(project => {
                if (typeof this.projectWhichChose.projectsId[i] !== null) {
                  this.projectWhichChose.quantity[i] = 0
                  this.projectWhichChose.projectsId[i] = null
                }
                i++
              })
            }
        )
    )
  }
}
</script>

<style>

</style>