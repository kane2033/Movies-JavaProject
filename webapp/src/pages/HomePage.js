import React from "react";
import {Link} from "react-router-dom";

class HomePage extends React.Component {

    isAuthenticated() {
        return localStorage.getItem("token") !== null;
    }

    // отображаем контент
    // только для авторизованных юзеров
    render() {
        if (this.isAuthenticated()) {
            return (
                <nav id="nav">
                    <Link to="/profile">Мой профиль</Link>
                    <Link to="/user-movies">Мои фильмы</Link>
                    <Link to="/movies">Все фильмы</Link>
                </nav>
            )
        } else {
            return (
                <nav id="nav">
                    <Link to="/login">Войти</Link>
                    <Link to="/register">Зарегистрироваться</Link>
                </nav>
            )
        }
    }
}

export default HomePage;