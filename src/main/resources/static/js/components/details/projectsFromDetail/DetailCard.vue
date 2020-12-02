<template>
  <div>
    <b>Detail:</b>
    <v-layout align-start justify-space-between row fill-height>
      <v-card class="card deep-orange lighten-3">
        <v-card-text primary-title>

          <b>Id:</b>
          {{ showProjects.detail.id }} <br>

          <b>Detail name:</b>
          {{ showProjects.detail.detailName }} <br>

          <b>Type:</b>
          {{ showProjects.detail.type }} <br>

          <b>Production:</b>
          {{ showProjects.detail.production }} <br>

          <b>Quantity of all:</b>
          <span style="color: forestgreen"> {{ showProjects.detail.quantityOfAll }}</span> <br>

          <b>Quantity of available:</b>
          <span style="color: red"> {{ showProjects.detail.quantityOfAvailable }} </span> <br>

          <b>Price:</b>
          {{ showProjects.detail.price }} $<br>

          <b>Storage:</b>
          {{ showProjects.detail.storage }}

        </v-card-text>
      </v-card>
    </v-layout>
  </div>
</template>

<script>
export default {
  props: ['showProjects']
}
</script>

<style>

</style>