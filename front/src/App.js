import './App.css';
import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import {createBrowserHistory} from "history";

import NavigationBar from "./components/NavigationBar";
import Home from "./components/Home";

function App() {
    return (
        <div className="App">
            <BrowserRouter>
                <NavigationBar />
                <div className="container-fluid">
                    <Routes>
                        <Route path="home" element={<Home />}/>
                    </Routes>
                </div>
            </BrowserRouter>
        </div>
    );
}

export default  App;