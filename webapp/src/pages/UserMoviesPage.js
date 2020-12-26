import React from 'react';
import axios from "axios";

const URL = 'http://localhost:8080/api/movies/user-movies/';
//в заголовок каждого запроса добавляется токен
axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem("token");

class UserMoviesPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userMovies: [],
            removedMovies: [],
            chosenButtons: [],
            statusMsg: '',
            deleteDisabled: true,
            saveDisabled: true,
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        this.getUserMovies();
    }

    getUserMovies() {
        axios
            .get(URL + 'all', {
                params: {
                    username: localStorage.getItem("username")
                }
            })
            .then(response => {
                this.setState({
                    userMovies: response.data,
                    chosenButtons: new Array(response.data.length).fill(false)
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

    updateMovies() {
        axios
            .post(URL + 'update', this.state.userMovies)
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

    removeMovie(event, i) {
        event.preventDefault();
        const removedMovies = this.state.removedMovies;
        removedMovies.push(this.state.userMovies[i]);
        const chosenButtons = this.state.chosenButtons;
        chosenButtons[i] = true;

        this.setState({
            removedMovies: removedMovies,
            chosenButtons: chosenButtons,
            deleteDisabled: false
        });
    }

    postDeleteMovies(event) {
        event.preventDefault();
        if (this.state.removedMovies.length > 0) {
            axios
                .post(URL + 'delete/multiple', this.state.removedMovies)
                .then(response => {
                    console.log(response.data);
                    this.setState({
                        statusMsg: "Выбранные фильмы успешно удалены"
                    });
                    window.location.reload(true); //перезагрузка страницы
                })
                .catch(error => {
                    console.log(error);
                    this.setState({
                        statusMsg: error.response.reason
                    })
                });
        }
    }

    handleChange(event, i) {
        const userMovies = this.state.userMovies;
        const {name, value} = event.target;
        if (userMovies[i].hasOwnProperty(name)) {
            userMovies[i][name] = value
            this.setState({
                userMovies: userMovies,
                saveDisabled: false
            })
        }
    }

    //метод обработки кнопки подтверждения формы
    handleSubmit(event) {
        this.updateMovies();
        event.preventDefault();
    }

    formTable(userMovies, chosenButtons) {
        let table = [];
        for (let i = 0; i < userMovies.length; i++) {
            if (userMovies[i] !== undefined) {
                let rows = [];
                rows.push(<th>{userMovies[i].movie.name}</th>);
                rows.push(<th><select name={"movieStatus"} value={this.state.userMovies[i].movieStatus}
                                      onChange={(event) => this.handleChange(event, i)}>
                    <option key={0} value={null}></option>
                    <option key={1} value={"WATCHED"}>Просмотрено</option>
                    <option key={2} value={"PLANNED"}>Запланировано</option>
                    <option key={3} value={"DROPPED"}>Прекратил просмотр</option>
                </select></th>)
                rows.push(<th><select name={"rating"} value={this.state.userMovies[i].rating}
                                      onChange={(event) => this.handleChange(event, i)}>
                    {this.formOneToTenSelect()}</select></th>)
                rows.push(<th>
                    <button disabled={chosenButtons[i]} hidden={chosenButtons[i]} onClick={(event) => {
                        this.removeMovie(event, i)
                    }}><img hidden={chosenButtons[i]} className="removeicon"
                            src={process.env.PUBLIC_URL + '/removeicon.png'}
                            alt="Удалить"/></button>
                </th>)
                table.push(<tr key={userMovies[i].id}>{rows}</tr>);
            }
        }
        return table;
    }

    formOneToTenSelect() {
        let rows = [];
        rows.push(<option key={0} value={null}></option>)
        for (let i = 1; i <= 10; i++) {
            rows.push(<option key={i} value={i}>{i}</option>)
        }
        return rows;
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit} className="form" id="user-movies-form">
                <h1>Мои фильмы</h1>
                <table id="user-movies-table">
                    <tbody>
                    <tr>
                        <th>Название фильма</th>
                        <th>Статус</th>
                        <th>Оценка</th>
                        <th></th>
                    </tr>
                    {this.formTable(this.state.userMovies, this.state.chosenButtons)}
                    </tbody>
                </table>
                <button disabled={this.state.deleteDisabled} hidden={this.state.deleteDisabled} onClick={(event) => {
                    this.postDeleteMovies(event)
                }}>Удалить фильмы
                </button>
                <input disabled={this.state.saveDisabled} hidden={this.state.saveDisabled} type="submit"
                       value="Сохранить изменения"/>
                <div>{this.state.statusMsg}</div>
            </form>
        );
    }
}

export default UserMoviesPage;