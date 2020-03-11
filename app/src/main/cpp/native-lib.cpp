#include <jni.h>
#include <string>
using namespace std;

//
//  MovieController.hpp
//  Highrise
//
//  Created by Jimmy Xu on 12/19/18.
//  Copyright © 2019 Highrise. All rights reserved.
//

#ifndef MovieController_hpp
#define MovieController_hpp

#include <vector>
#include <map>
#include <android/log.h>

namespace movies {
    class Actor {
    public:
        string name;
        int age;

        //optional challenge 1: Load image from URL
        string imageUrl;
    };

    class Movie {
    public:
        string name;
        int lastUpdated;

    };

    class MovieDetail {
    public:
        string name;
        float score;
        vector<Actor> actors;
        string description;
    };

    class MovieController {
    private:
        vector<Movie*> _movies;
        map<string, MovieDetail*> _details;

    public:
        MovieController() {
            //populate data
            for (int i = 0; i < 10; i++) {
                auto movie = new Movie();
                movie->name = "Top Gun " + to_string(i);
                movie->lastUpdated = i * 10000;
                _movies.push_back(movie);

                auto movieDetail = new MovieDetail();
                movieDetail->name = movie->name;
                movieDetail->score = rand() % 10;
                movieDetail->description = "As students at the United States Navys elite fighter weapons school compete to be best in the class, one daring young pilot learns a few things from a civilian instructor that are not taught in the classroom.";

                auto tomCruise = Actor();
                tomCruise.name = "Tom Cruise";
                tomCruise.age = 50;

                auto valKilmer = Actor();
                valKilmer.name = "Val Kilmer";
                valKilmer.age = 46;
                valKilmer.imageUrl = "https://m.media-amazon.com/images/M/MV5BMTk3ODIzMDA5Ml5BMl5BanBnXkFtZTcwNDY0NTU4Ng@@._V1_UY317_CR4,0,214,317_AL_.jpg";

                movieDetail->actors.push_back(tomCruise);
                movieDetail->actors.push_back(valKilmer);

                if (i % 2 == 0) {
                    auto timRobbins = Actor();
                    timRobbins.name = "Tim Robbins";
                    timRobbins.age = 55;
                    timRobbins.imageUrl = "https://m.media-amazon.com/images/M/MV5BMTI1OTYxNzAxOF5BMl5BanBnXkFtZTYwNTE5ODI4._V1_UY317_CR16,0,214,317_AL_.jpg";

                    movieDetail->actors.push_back(timRobbins);
                } else {
                    auto jenniferConnelly = Actor();
                    jenniferConnelly.name = "Jennifer Connelly";
                    jenniferConnelly.age = 39;
                    jenniferConnelly.imageUrl = "https://m.media-amazon.com/images/M/MV5BOTczNTgzODYyMF5BMl5BanBnXkFtZTcwNjk4ODk4Mw@@._V1_UY317_CR12,0,214,317_AL_.jpg";

                    movieDetail->actors.push_back(jenniferConnelly);
                }

                _details[movie->name] = movieDetail;
            }
        }

        //Returns list of movies
        vector<Movie*> getMovies() {
            return _movies;
        }

        //Returns details about a specific movie
        MovieDetail* getMovieDetail(string name) {
            for (auto item:_details) {
                if (item.second->name == name) {
                    return item.second;
                }
            }
            return nullptr;
        }

        string getJsonString(Actor actor) {
            string actorJson = "{";
            actorJson.append("\'name\':\'" + actor.name + "\'");
            actorJson.append(",");
            actorJson.append("\'age\':\'" + to_string(actor.age) + "\'");
            actorJson.append(",");
            actorJson.append("\'imageUrl\':\'" + actor.imageUrl + "\'");
            actorJson.append("}");
            return actorJson;
        }

        string getJsonString(vector<Actor> actors) {
            string actorsJson = "[";
            for (auto actor:actors) {
                actorsJson.append(getJsonString(actor));
                actorsJson.append(",");
            }
            actorsJson.append("]");
            return actorsJson;
        }
    };

    extern "C" JNIEXPORT jstring JNICALL
    Java_com_david_movieapp_data_MovieRepository_getMovies(
            JNIEnv *env,
            jobject /* this */) {
        auto controller = new MovieController();
        auto movies = controller->getMovies();
        string movieJson = "{";
        movieJson.append("\'movies\':");
        movieJson.append("[");
        for (int i = 0; i < movies.size(); i++) {
            auto movie = movies[i];
            movieJson.append("{");
            movieJson.append("\'name\':\'" + movie->name + "\'");
            movieJson.append(",");
            movieJson.append("\'lastUpdated\':\'" + to_string(movie->lastUpdated) + "\'");
            movieJson.append("}");
            movieJson.append(",");
        }
        movieJson.append("]}");
        char tab2[1024];
        strcpy(tab2, movieJson.c_str());
        return env->NewStringUTF(tab2);
    }

    extern "C" JNIEXPORT jstring JNICALL
    Java_com_david_movieapp_data_MovieRepository_getMovieDetail(
            JNIEnv *env,
            jobject /* this */,
            jstring movieName) {
        auto controller = new MovieController();
        const char *cstr = env->GetStringUTFChars(movieName, NULL);
        std::string str = std::string(cstr);
        auto movieDetail = controller->getMovieDetail(str);
        string movieDetailJson = "{";
        movieDetailJson.append("\'movieDetail\':");
        movieDetailJson.append("{");
        movieDetailJson.append("\'name\':\'" + movieDetail->name + "\'");
        movieDetailJson.append(",");
        movieDetailJson.append("\'score\':\'" + to_string(movieDetail->score) + "\'");
        movieDetailJson.append(",");
        auto actors = controller->getJsonString(movieDetail->actors);
        movieDetailJson.append("\'actors\':" + actors);
        movieDetailJson.append(",");
        movieDetailJson.append("\'description\':\'" + movieDetail->description + "\'");
        movieDetailJson.append("}");
        movieDetailJson.append("}");
        char tab2[1024];
        strcpy(tab2, movieDetailJson.c_str());
        return env->NewStringUTF(tab2);
    }
}

#endif /* MovieController_hpp */