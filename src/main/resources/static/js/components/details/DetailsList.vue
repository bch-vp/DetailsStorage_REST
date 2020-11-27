<template>
  <div>
    <detail-form :details="details"/>
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
      quantity: 0
    }
  }
}
</script>

<style>

</style>