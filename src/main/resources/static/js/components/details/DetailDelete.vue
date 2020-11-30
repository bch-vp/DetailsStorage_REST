<template>
  <v-btn outline small fab color="indigo" @click="del">
    <v-icon>delete</v-icon>
  </v-btn>
</template>

<script>
export default {
  props: ['detail', 'details'],
  methods:{
    del: function () {
      this.$resource('/details{/id}').remove({id: this.detail.id}).then(result => {
        this.details.splice(this.details.indexOf(this.detail), 1)
      }, ex => {
        alert(ex.status);
      })
    },
    save:{

    }
  }
}
</script>

<style>

</style>