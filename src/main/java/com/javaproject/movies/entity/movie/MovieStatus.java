package com.javaproject.movies.entity.movie;

public enum MovieStatus {
    WATCHED, PLANNED, DROPPED;

    public static MovieStatus fromText(String text) {
        MovieStatus status = null;
        switch (text) {
            case "Просмотрено":
                status = WATCHED;
                break;
            case "Запланировано":
                status = PLANNED;
                break;
            case "Прекратил просмотр":
                status = DROPPED;
                break;
        }
        return status;
    }
}
