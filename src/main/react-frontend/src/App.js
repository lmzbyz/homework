import './App.css';
import React from 'react';
import InputForm from "./input";


class App extends React.Component {
    constructor() {
        super();
    }

    render() {
        return (

            <div className="App">
                <h2>Korona Verileri</h2>
                <InputForm/>
            </div>
        );
    }
}

export default App;