import React from 'react';
import axios from "axios";

const URL = 'http://localhost:8080/api/movies/';
//в заголовок каждого запроса добавляется токен
axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem("token");

class MoviesPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            movies: [],
            userMovies: [],
            chosenMovies: new Set(),
            chosenButtons: [],
            statusMsg: ''
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        this.getMovies();
        this.getUserMovies();
    }

    getMovies() {
        axios
            .get(URL + 'all')
            .then(response => {
                this.setState({
                    movies: response.data,
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

    getUserMovies() {
        axios
            .get(URL + 'user-movies/all', {
                params: {
                    username: localStorage.getItem("username")
                }
            })
            .then(response => {
                this.setState({
                    userMovies: response.data
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

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    updateMoviesList() {
        const userMovies = Array.from(this.state.chosenMovies);
        axios
            .post(URL + 'user-movies/update', userMovies)
            .then(response => {
                console.log(response.data);
                this.setState({
                    statusMsg: "Фильмы успешно добавлены, проверьте вкладку 'Мои фильмы'"
                })
                this.props.history.push("/user-movies");
            })
            .catch(error => {
                console.log(error);
                this.setState({
                    statusMsg: error.response.reason
                })
            });
    }

    //метод обработки кнопки подтверждения формы
    handleSubmit(event) {
        this.updateMoviesList();
        event.preventDefault();
    }

    addMovie(event, i) {
        event.preventDefault();
        const userMovie = {}
        userMovie.movie = this.state.movies[i];
        userMovie.username = localStorage.getItem("username");
        const chosenMovies = this.state.chosenMovies;
        chosenMovies.add(userMovie);

        const chosenButtons = this.state.chosenButtons
        chosenButtons[i] = true

        this.setState({
            chosenMovies: chosenMovies,
            chosenButtons: chosenButtons
        });
    }

    formTable(movies, userMovies, chosenButtons) {
        let table = [];
        for (let i = 0; i < movies.length; i++) {
            let rows = [];
            rows.push(<th>{movies[i].name}</th>);
            rows.push(<th>{this.toDDMMYY(movies[i].releaseDate)}</th>);
            rows.push(<th>{movies[i].genres.map((genre) => {
                return `${genre.name}; `;
            })}</th>);
            rows.push(<th>{movies[i].synopsis}</th>);
            const isDisabled = userMovies.some(userMovie => userMovie.movie.id === movies[i].id)
            rows.push(<th>
                <button disabled={isDisabled || chosenButtons[i]} hidden={isDisabled || chosenButtons[i]}
                        onClick={(event) => {
                            this.addMovie(event, i)
                        }}><img hidden={isDisabled || chosenButtons[i]} className="addicon"
                                src={process.env.PUBLIC_URL + '/addicon.png'}
                                alt="Добавить"/></button>
            </th>)
            table.push(<tr>{rows}</tr>);
        }
        return table;
    }

    toDDMMYY(date) {
        const d = new Date(date)
        return `${d.getDay()}.${d.getMonth()}.${d.getFullYear()}`
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit} className="form" id="movies-form">
                <h1>Все фильмы</h1>
                <table id="movies-table">
                    <tbody>
                    <tr>
                        <th>Название</th>
                        <th>Дата выхода</th>
                        <th>Жанры</th>
                        <th>Синопсис</th>
                        <th></th>
                    </tr>
                    {this.formTable(this.state.movies, this.state.userMovies, this.state.chosenButtons)}
                    </tbody>
                </table>
                <input type="submit" value="Добавить фильмы"/>
                <div>{this.state.statusMsg}</div>
            </form>
        );
    }
}

export default MoviesPage;