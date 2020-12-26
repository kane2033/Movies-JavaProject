import React from 'react';
import '../other/App.css';
import axios from "axios";

const URL = 'http://localhost:8080/api/profile/';
//в заголовок каждого запроса добавляется токен
axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem("token");

class ProfilePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            firstName: '',
            secondName: '',
            lastName: '',
            email: '',
            birthday: '',
            consent: false,
            statusMsg: ''
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    //заполнение информации о пользователе
    //при заргрузки страницы
    componentDidMount() {
        axios
            .get(URL + '', {
                params: {
                    username: localStorage.getItem("username")
                }
            })
            .then(response => {
                this.setState({
                    firstName: response.data.firstName,
                    secondName: response.data.secondName,
                    lastName: response.data.lastName,
                    email: response.data.email,
                    birthday: new Date(response.data.birthday).toISOString().slice(0, 10),
                });
                console.log(response);
            })
            .catch(error => {
                console.log(error);
                let message = error.response.status === 400 ? "Вы не авторизованы." : error.response.reason
                this.setState({
                    statusMsg: message
                })
            });
    }

    updateUser() {
        axios
            .post(URL + 'update', {
                username: localStorage.getItem("username"),
                firstName: this.state.firstName,
                secondName: this.state.secondName,
                lastName: this.state.lastName,
                email: this.state.email,
                birthday: this.state.birthday,
            })
            .then(response => {
                console.log(response.data);
                this.setState({
                    statusMsg: "Изменения успешно применены"
                })
            })
            .catch(error => {
                console.log(error);
                this.setState({
                    statusMsg: error.response.reason
                })
            });
    }

    handleSubmit(event) {
        this.updateUser();
        event.preventDefault();
    }

    logout(event) {
        event.preventDefault();
        localStorage.clear();
        this.props.history.push("/");
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit} className="form" id="profile-form">
                <h1>Личный кабинет</h1>
                <label>Фамилия:</label>
                <input name="lastName" type="text" value={this.state.lastName} onChange={this.handleChange} size="40"
                       required/>
                <label>Имя:</label>
                <input name="firstName" type="text" value={this.state.firstName} onChange={this.handleChange} size="40"
                       required/>
                <label>Отчество:</label>
                <input name="secondName" type="text" value={this.state.secondName} onChange={this.handleChange}
                       size="40"/>
                <label>E-mail:</label>
                <input name="email" type="email" value={this.state.email} onChange={this.handleChange} size="40"
                       required/>
                <label>День рождения:</label>
                <input name="birthday" type="date" value={this.state.birthday} onChange={this.handleChange} required/>
                <label>Согласие на обработку персональных данных:</label>
                <input name="consent" className="checkbox" type="checkbox" value={this.state.consent}
                       onChange={this.handleChange} required/>
                <input type="submit" value="Сохранить изменения"/>
                <div>{this.state.statusMsg}</div>
                <button onClick={(event) => {
                    this.logout(event)
                }}>Выйти из учетной записи
                </button>
            </form>
        );
    }
}

export default ProfilePage;