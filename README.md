# Androd Popular Movies App
- Popular Movies app for Udacity Android Developer Nanodegree
## Description
- The app fetches movies from <a href="https://www.themoviedb.org" target="_blank">The Movie DB</a> showing to the user a scrolling grid of movie posters. 
- It allows the user to sort the order by most popular, top rated and favourite movies. 
- Tapping on one of the posters will open a details screen with additional information such as original title, movie poster image thumbnail, synopsis, user rating, release date, trailers and reviews. 
- Always on the detail screen, the user can mark a movie as a favourite by tapping the heart icon and it will be stored in a local database. 
- The app handles data persistence using a content provider and a sqlite database.
## Screenshot
<img src="https://github.com/simoneconigliaro/android_movies/blob/master/Screenshot_1563301244.png" width="300"/> <img src="https://github.com/simoneconigliaro/android_movies/blob/master/Screenshot_1563301376.png" width="300"/> <img src="https://github.com/simoneconigliaro/android_movies/blob/master/Screenshot_1563301425.png" width="300"/>
## Getting Started
App uses The Movie Database API. You have to enter your API key in order to run the app. You can create your own one very easy! https://www.themoviedb.org/account/signup?language=en-EN. When you get it, just set it here: "popular-movies-app/gradle.properties"

