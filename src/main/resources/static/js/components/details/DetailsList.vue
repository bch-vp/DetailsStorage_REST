<template>
  <div>
    <v-btn v-on:click="show = !show" flat round>
      <div v-if="!show">
        <v-icon>add</v-icon>Add detail
      </div>
      <div v-if="show">
        <v-icon>clear</v-icon>Close
      </div>
    </v-btn>
    <transition name="slide-fade">
      <detail-form v-if="show" :details="details"/>
    </transition>

    <v-layout align-start justify-center row fill-height>
      <detail-row v-for="detail in details" :detail="detail" :details="details" :key="detail.id"/>
    </v-layout>
  </div>
</template>

<script>
import DetailRow from "components/details/DetailRow.vue"
import DetailForm from 'components/details/DetailForm.vue'

export default {
  components: {DetailForm, DetailRow},
  props: ["details"],
  comments: {
    DetailRow,
    DetailForm
  },
  data: function () {
    return {
      quantity: 0,
      show: false
    }
  }
}
</script>

<style>
</style>