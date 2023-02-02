import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import { VDataTable } from 'vuetify/labs/VDataTable'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

// import * as settings from 'vuetify/settings'
// import * as styles from 'vuetify/styles'

export default createVuetify({
    components : { components, VDataTable },
    directives,
    theme: {defaultTheme: 'light'},
    defaults: {
        VDataTable: {
          fixedHeader: true,
          noDataText: 'Results not found',
        },
    },
})

// export default createVuetify()

// plugins/vuetify.js
// import 'vuetify/styles'
// import { createVuetify } from 'vuetify'

// export default createVuetify({theme: {theme: 'light'}})