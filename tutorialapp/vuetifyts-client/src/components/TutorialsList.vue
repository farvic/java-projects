<script>

</script>


<template>
  <v-row align="center" class="list px-3 mx-auto">
    <v-col cols="12" md="8">
      <v-text-field v-model="title" label="Search by Title"></v-text-field>
    </v-col>

    <v-col cols="12" md="4">
      <v-btn small @click="searchTitle">
        Search
      </v-btn>
    </v-col>

    <v-col cols="12" sm="12">
      <v-card class="mx-auto" tile>
        <v-card-title>Tutorials</v-card-title>

        <v-data-table
          :headers="headers"
          :items="tutorials"
          disable-pagination
          :hide-default-footer="true"
        >
          <template v-slot:[`item.actions`]="{ item }">
            <v-icon small class="mr-2" @click="editTutorial(item.id)">mdi-pencil</v-icon>
            <v-icon small @click="deleteTutorial(item.id)">mdi-delete</v-icon>
          </template>
        </v-data-table>

        <v-card-actions v-if="tutorials.length > 0">
          <v-btn small color="error" @click="removeAllTutorials">
            Remove All
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import TutorialDataService from "@/services/TutorialDataService";
import type Tutorial from "@/types/Tutorial";
import type ResponseData from "@/types/ResponseData";

import { VDataTable } from 'vuetify/labs/VDataTable';




export default defineComponent({
  components: {
    VDataTable
  },
  name: "tutorials-list",
  data() {
    return {
      tutorials: [] as Tutorial[],
      headers: [
          { text: "Title", align: "start", sortable: false, value: "title" },
          { text: "Description", value: "description", sortable: false },
          { text: "Status", value: "status", sortable: false },
          { text: "Actions", value: "actions", sortable: false },
        ],
      title: "",
    };
  },
  methods: {
    retrieveTutorials() {
      TutorialDataService.getAll()
        .then((response: ResponseData) => {
          this.tutorials = response.data;
          console.log(response.data);
        })
        .catch((e: Error) => {
          console.log(e);
        });
    },

    refreshList() {
      this.retrieveTutorials();
    },

    deleteTutorial(id : number) {
        TutorialDataService.delete(id)
          .then(() => {
            this.refreshList();
          })
          .catch((e) => {
            console.log(e);
          });
    },

    editTutorial(id: number) {
      this.$router.push({ name: "tutorial-details", params: { id: id } });
    },


    removeAllTutorials() {
      TutorialDataService.deleteAll()
        .then((response: ResponseData) => {
          console.log(response.data);
          this.refreshList();
        })
        .catch((e: Error) => {
          console.log(e);
        });
    },

    getDisplayTutorial(tutorial: Tutorial) {
        return {
          id: tutorial.id,
          title: tutorial.title.length > 30 ? tutorial.title.substr(0, 30) + "..." : tutorial.title,
          description: tutorial.description.length > 30 ? tutorial.description.substr(0, 30) + "..." : tutorial.description,
          status: tutorial.published ? "Published" : "Pending",
        };
      },

    searchTitle() {
      TutorialDataService.findByTitle(this.title)
        .then((response: ResponseData) => {
          this.tutorials = response.data.map(this.getDisplayTutorial);
          console.log(response.data);
        })
        .catch((e: Error) => {
          console.log(e);
        });
    },
  },
  mounted() {
    this.retrieveTutorials();
  },
});
</script>

<style>
.list {
  text-align: left;
  max-width: 750px;
  margin: auto;
}
</style>
