import MainPage from './Pages/MainPage';

import thunk from 'redux-thunk';
import { Provider } from 'react-redux';
import { applyMiddleware, createStore } from 'redux';

import CombinedReducers from './Stores/Reducers/CombinedReducers';

const App = () : JSX.Element =>  {

  const globalStore = createStore(CombinedReducers, applyMiddleware(thunk));


  return (
    <Provider store={globalStore}>
        <div className="App">
              <MainPage></MainPage>
        </div>
    </Provider>
  );
}

export default App;
