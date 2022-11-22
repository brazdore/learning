import { BrowserRouter, Route, Switch } from 'react-router-dom';

import Home from './pages/Home';
import Navbar from './components/Navbar';
import Catalog from './pages/Catalog';

const Routes = () => (
  <BrowserRouter>
    <Navbar />
    <Switch>
      <Route path={'/'} exact>
        <Home />
      </Route>
      <Route path={'/catalog'}>
        <Catalog />
      </Route>
    </Switch>
  </BrowserRouter>
);

export default Routes;