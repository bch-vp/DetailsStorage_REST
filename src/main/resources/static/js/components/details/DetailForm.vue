<template>
  <v-layout align-start justify-center row >
    <form>

          <v-text-field
              v-model="Id"
              :error-messages="nameErrors"
              :counter="10"
              label="Id"
              required
              @input="$v.name.$touch()"
              @blur="$v.name.$touch()"
          ></v-text-field>
          <v-text-field
              v-model="name"
              :error-messages="nameErrors"
              :counter="10"
              label="Detail name"
              required
              @input="$v.name.$touch()"
              @blur="$v.name.$touch()"
          ></v-text-field>
          <v-text-field
              v-model="name"
              :error-messages="nameErrors"
              :counter="10"
              label="Type"
              required
              @input="$v.name.$touch()"
              @blur="$v.name.$touch()"
          ></v-text-field>
          <v-text-field
              v-model="name"
              :error-messages="nameErrors"
              :counter="10"
              label="Production"
              required
              @input="$v.name.$touch()"
              @blur="$v.name.$touch()"
          ></v-text-field>
          <v-text-field
              v-model="name"
              :error-messages="nameErrors"
              :counter="10"
              label="Quantity of all"
              required
              @input="$v.name.$touch()"
              @blur="$v.name.$touch()"
          ></v-text-field>
          <v-text-field
              v-model="name"
              :error-messages="nameErrors"
              :counter="10"
              label="Quantity of available"
              required
              @input="$v.name.$touch()"
              @blur="$v.name.$touch()"
          ></v-text-field>
          <v-text-field
              v-model="name"
              :error-messages="nameErrors"
              :counter="10"
              label="Price"
              required
              @input="$v.name.$touch()"
              @blur="$v.name.$touch()"
          ></v-text-field>
          <v-text-field
              v-model="email"
              :error-messages="emailErrors"
              label="Storage"
              required
              @input="$v.email.$touch()"
              @blur="$v.email.$touch()"
          ></v-text-field>

        <v-btn @click="submit" small flat round color="indigo">submit</v-btn>
        <v-btn @click="clear" outline small fab color="indigo">
          <v-icon>autorenew</v-icon>
        </v-btn>

    </form>

  </v-layout>
</template>

<script>
import {validationMixin} from 'vuelidate'
import {required, maxLength, email} from 'vuelidate/lib/validators'

export default {
  mixins: [validationMixin],

  validations: {
    name: {required, maxLength: maxLength(10)},
    email: {required, email},
    select: {required},
    checkbox: {
      checked(val) {
        return val
      }
    }
  },

  data: () => ({
    name: '',
    email: '',
    select: null,
    items: [
      'Item 1',
      'Item 2',
      'Item 3',
      'Item 4'
    ],
    checkbox: false
  }),

  computed: {
    checkboxErrors() {
      const errors = []
      if (!this.$v.checkbox.$dirty) return errors
      !this.$v.checkbox.checked && errors.push('You must agree to continue!')
      return errors
    },
    selectErrors() {
      const errors = []
      if (!this.$v.select.$dirty) return errors
      !this.$v.select.required && errors.push('Item is required')
      return errors
    },
    nameErrors() {
      const errors = []
      if (!this.$v.name.$dirty) return errors
      !this.$v.name.maxLength && errors.push('Name must be at most 10 characters long')
      !this.$v.name.required && errors.push('Name is required.')
      return errors
    },
    emailErrors() {
      const errors = []
      if (!this.$v.email.$dirty) return errors
      !this.$v.email.email && errors.push('Must be valid e-mail')
      !this.$v.email.required && errors.push('E-mail is required')
      return errors
    }
  },

  methods: {
    submit() {
      this.$v.$touch()
    },
    clear() {
      this.$v.$reset()
      this.name = ''
      this.email = ''
      this.select = null
      this.checkbox = false
    }
  }
}

</script>

<style>

</style>