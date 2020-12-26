import React from 'react';
import '../../other/App.css';
import axios from "axios";

const URL = 'http://localhost:8080/api/auth/';

class LoginForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            errorMsg: ''
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    //POST запрос на вход
    login() {
        axios
            .post(URL + 'login', {
                username: this.state.username,
                password: this.state.password
            })
            .then(response => {
                localStorage.setItem("username", response.data.username);
                localStorage.setItem("token", response.data.token);
                console.log(response.data);
                this.props.history.push("/");
            })
            .catch(error => {
                this.setState({
                    errorMsg: error.message
                })
                console.log(error);
            });
    }

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    //метод обработки кнопки подтверждения формы
    handleSubmit(event) {
        this.login();
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit} className="form" id="login-form">
                <h1>Вход в личный кабинет:</h1>
                <label>Логин:</label>
                <input name="username" type="text" value={this.state.username} onChange={this.handleChange} size="40"
                       required/> <br/>
                <label>Пароль:</label>
                <input name="password" type="password" value={this.state.password} onChange={this.handleChange}
                       size="40" required/> <br/>
                <input type="submit" value="Войти"/>
                <div>{this.state.errorMsg}</div>
            </form>
        );
    }
}

export default LoginForm;