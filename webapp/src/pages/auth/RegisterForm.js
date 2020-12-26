import React from 'react';
import '../../other/App.css';
import axios from "axios";

const URL = 'http://localhost:8080/api/auth/';

class RegisterForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            email: '',
            username: '',
            password: '',
            lastName: '',
            firstName: '',
            secondName: '',
            birthday: '',
            statusMsg: ''
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    //POST запрос на регистрацию
    register() {
        axios
            .post(URL + 'register', {
                email: this.state.email,
                username: this.state.username,
                password: this.state.password,
                lastName: this.state.lastName,
                firstName: this.state.firstName,
                secondName: this.state.secondName,
                birthday: this.state.birthday
            })
            .then(response => {
                console.log(response.data);
                this.props.history.push("/login");
            })
            .catch(error => {
                this.setState({
                    statusMsg: error.message
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
        this.register();
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit} className="form" id="register-form">
                <h1>Регистрация</h1>
                <label>E-mail<span className="required">*</span></label>
                <input name="email" type="email" value={this.state.email} onChange={this.handleChange} size="40"
                       required/>
                <label>Логин<span className="required">*</span></label>
                <input name="username" type="text" value={this.state.username} onChange={this.handleChange} size="40"
                       required/>
                <label>Пароль<span className="required">*</span></label>
                <input name="password" type="password" value={this.state.password} onChange={this.handleChange}
                       size="40"
                       required/>
                <label>Фамилия</label>
                <input name="lastName" type="text" value={this.state.lastName} onChange={this.handleChange} size="40"/>
                <label>Имя</label>
                <input name="firstName" type="text" value={this.state.firstName} onChange={this.handleChange}
                       size="40"/>
                <label>Отчество</label>
                <input name="secondName" type="text" value={this.state.secondName} onChange={this.handleChange}
                       size="40"/>
                <label>День рождения</label>
                <input name="birthday" type="date" value={this.state.birthday} onChange={this.handleChange}/>
                <label>Согласие на обработку персональных данных</label>
                <input name="consent" className="checkbox" type="checkbox" value={this.state.consent}
                       onChange={this.handleChange} required/>
                <input type="submit" value="Зарегистрироваться"/>
                <div>{this.state.statusMsg}</div>
            </form>
        );
    }
}

export default RegisterForm;