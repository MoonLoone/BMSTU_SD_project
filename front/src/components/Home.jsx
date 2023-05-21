import React from 'react';
import logo from '../static/img/hello_img.jpeg';
const Home = props => {

    return (
        <div className="mt-5 me-auto">
            <h1>Welcome to MoonMosem</h1>
            <img src={logo} width="80%" height="25%" />
        </div>
    );

}

export default Home;
