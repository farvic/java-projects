import { createVuetify } from "vuetify"
import * as directives from 'vuetify/directives'
import * as components from 'vuetify/components';
import 'vuetify/styles';
// import '@mdi/font/css/materialdesignicons.css'





export default createVuetify({
    components: components,
    directives,
    ssr: true,
    theme: {defaultTheme: 'light'},
    defaults: {
        VDataTable: {
          fixedHeader: true,
          noDataText: 'Results not found',
        },
    },
});

// export { components, directives };

