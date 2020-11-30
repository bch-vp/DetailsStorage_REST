<template>
  <v-card class="card deep-orange lighten-3">
    <v-card-text primary-title>
      <b>Id:</b>
      {{ detail.id }} <br>

        <v-text-field v-model="detailName"
                      :counter="25"
                      label="Detail name"
        ></v-text-field>


        <v-text-field v-model="type"
                      :counter="25"
                      label="Type"
        ></v-text-field>

        <v-text-field v-model="production"
                      :counter="25"
                      label="Production"
        ></v-text-field>

      <b>Quantity of all:</b>
      <span style="color: forestgreen"> {{ detail.quantityOfAll }}</span> <br>

      <b>Quantity of available:</b>
      <span style="color: red"> {{ detail.quantityOfAvailable }} </span> <br>

        <v-text-field v-model="price"
                      :counter="10"
                      label="Price $"
        ></v-text-field>


        <v-text-field v-model="storage"
                      :counter="10"
                      label="Storage"
        ></v-text-field>
    </v-card-text>
    <v-card-actions>
        <v-btn outline small fab color="indigo">
          <v-icon>done</v-icon>
        </v-btn>
        <v-btn outline small fab color="indigo" v-on:click="editObject.edit = !editObject.edit">
          <v-icon>clear</v-icon>
        </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  props:["detail", 'editObject'],
  data: function () {
    return {
      detailName: this.detail.detailName,
      type: this.detail.type,
      production: this.detail.production,
      price: this.detail.price,
      storage: this.detail.storage,
    }
  },
  methods: {
    saveEditedDetail: function () {

    }
  }
}
</script>

<style>

</style>