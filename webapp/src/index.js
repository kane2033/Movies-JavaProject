import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import * as serviceWorker from './other/serviceWorker';
import ProfilePage from './pages/ProfilePage';
import LoginForm from "./pages/auth/LoginForm";
import RegisterForm from "./pages/auth/RegisterForm"
import HomePage from "./pages/HomePage";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import UserMoviesPage from "./pages/UserMoviesPage";
import MoviesPage from "./pages/MoviesPage";


ReactDOM.render(
    <BrowserRouter>
        <Switch>
            <Route exact path="/" component={HomePage}/>
            <Route path="/login" component={LoginForm}/>
            <Route path="/register" component={RegisterForm}/>
            <Route path="/profile" component={ProfilePage}/>
            <Route path="/user-movies" component={UserMoviesPage}/>
            <Route path="/movies" component={MoviesPage}/>
        </Switch>
    </BrowserRouter>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
