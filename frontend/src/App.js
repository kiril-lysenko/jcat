import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import React, {Component} from "react";
import ListOfCats from "./components/ListOfCats";
import CatColorsInfo from "./components/CatColorsInfo";
import CatsStatisticInfo from "./components/CatsStatisticInfo";
import CreateCat from "./components/CreateCat";

class App extends Component {

    render() {
        return (
            <Router>
                <Routes>
                    <Route path='/' exact={true} element={<ListOfCats/>}/>
                    <Route path='/cats' exact={true} element={<ListOfCats/>}/>
                    <Route path='/cats-statistic/cat-colors' exact={true} element={<CatColorsInfo/>}/>
                    <Route path='/cats-statistic/tail-and-whiskers-length' exact={true} element={<CatsStatisticInfo/>}/>
                    <Route path='/cat' exact={true} element={<CreateCat/>}/>
                </Routes>
            </Router>
        )
    }
}

export default App;
