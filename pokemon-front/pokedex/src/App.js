import './App.css';
import Home from './components/Home';
import PokedexHeader from './components/PokedexHeader';

function App() {
  return (
    <>
      <header className="Header">
        <PokedexHeader />
      </header>
      <div className="App">
        <Home />
      </div>
    </>
  );
}

export default App;
