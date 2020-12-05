<template>
  <v-container>
    <v-layout row>
      <v-flex xs8 md4 sm6 offset-xs2 offset-md4 offset-sm3>
        <v-text-field
            v-model="projectName"
            :error-messages="projectNameErrors"
            :counter="25"
            label="Project name (required)"
            required
            @input="$v.projectName.$touch()"
            @blur="$v.projectName.$touch()"
        ></v-text-field>
        <v-text-field
            v-model="type"
            :error-messages="typeErrors"
            :counter="25"
            label="Type"
            required
            @input="$v.type.$touch()"
            @blur="$v.type.$touch()"
        ></v-text-field>
        <!--          <v-text-field-->
        <!--              v-model="quantity"-->
        <!--              :error-messages="quantityErrors"-->
        <!--              :counter="10"-->
        <!--              label="Quantity (required)"-->
        <!--              required-->
        <!--              @input="$v.quantity.$touch()"-->
        <!--              @blur="$v.quantity.$touch()"-->
        <!--          ></v-text-field>-->
        <v-text-field
            v-model="storage"
            :error-messages="storageErrors"
            label="Storage (required)"
            required
            @input="$v.storage.$touch()"
            @blur="$v.storage.$touch()"
        ></v-text-field>
        <div class="text-xs-center">
          <v-btn @click="submit" small flat round color="indigo">submit</v-btn>
          <v-btn @click="clear" outline small fab color="indigo">
            <v-icon>autorenew</v-icon>
          </v-btn>
        </div>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import {validationMixin} from 'vuelidate'
import {required, maxLength, email, numeric} from 'vuelidate/lib/validators'

export default {
  mixins: [validationMixin],
  props: ['projects'],
  validations: {
    projectName: {required, maxLength: maxLength(25)},
    type: {maxLength: maxLength(25)},
    quantity: {required, maxLength: maxLength(10), numeric},
    storage: {required},
  },

  data: () => ({
    projectName: '',
    type: '',
    quantity: '',
    storage: ''
  }),

  computed: {
    projectNameErrors() {
      const errors = []
      if (!this.$v.projectName.$dirty) return errors
      !this.$v.projectName.maxLength && errors.push('Project name must be at most 25 characters long')
      !this.$v.projectName.required && errors.push('Project name is required.')
      return errors
    },
    typeErrors() {
      const errors = []
      if (!this.$v.type.$dirty) return errors
      !this.$v.type.maxLength && errors.push('Type must be at most 25 characters long')
      return errors
    },
    // quantityErrors() {
    //   const errors = []
    //   if (!this.$v.quantity.$dirty) return errors
    //   !this.$v.quantity.maxLength && errors.push('Quantity must be at most 10 characters long')
    //   !this.$v.quantity.required && errors.push('Quantity is required.')
    //   if (!this.$v.quantity.numeric) {
    //     errors.push('Quantity not correct')
    //   }
    //   return errors
    // },
    storageErrors() {
      const errors = []
      if (!this.$v.storage.$dirty) return errors
      !this.$v.storage.required && errors.push('Storage is required.')
      return errors
    },
  },

  methods: {
    submit() {
      this.$v.$touch()
      if (!this.$v.$invalid) {
        var project = {
          projectsName: this.projectName,
          type: this.type,
          quantity: 1,
          storage: this.storage
        }
        this.$resource('/projects').save({}, project).then(result =>
            result.json().then(data => {
              this.projects.push(data);
              this.projects.sort((a, b) => -(a.id - b.id))
              this.clear()
            })
        )
      }
    },
    clear() {
      this.$v.$reset()
      this.projectName = ''
      this.type = ''
      // this.quantity = ''
      this.storage = ''
    }
  }
}
</script>

<style>

</style>