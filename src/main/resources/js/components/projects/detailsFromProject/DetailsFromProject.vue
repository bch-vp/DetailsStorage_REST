<template>
  <div>
    <v-layout align-start justify-center row fill-height>
      <div v-for="detail in details">
        <v-card class="card ">
          <v-card-text primary-title>

            <b style="color: cornflowerblue"> Quantity of details, which used:
              <span style="color: darkblue"> {{ detail.quantityInUsed }}</span>
            </b>
            <br><br>

            <b>Id:</b>
            {{ detail.id }} <br>

            <b>Detail name:</b>
            {{ detail.detailName }} <br>

            <b>Type:</b>
            {{ detail.type }} <br>

            <b>Production:</b>
            {{ detail.production }} <br>

            <b>Quantity of all:</b>
            <span style="color: forestgreen"> {{ detail.quantityOfAll }} </span>
            <br>

            <b>Quantity of available:</b>
            <span style="color: red"> {{ detail.quantityOfAvailable }} </span> <br>

            <b>Price:</b>
            {{ detail.price }} $<br>

            <b>Storage:</b>
            {{ detail.storage }}


          </v-card-text>
          <v-card-actions>
            <details-from-project-delete :project="project" :detail="detail" :details="details"/>
          </v-card-actions>
        </v-card>
      </div>
    </v-layout>
  </div>
</template>

<script>
import DetailsFromProjectDelete from 'components/projects/DetailsFromProjectDelete.vue'

export default {
  components: {DetailsFromProjectDelete},
  props: ['project', 'details'],
}
</script>

<style>

</style>