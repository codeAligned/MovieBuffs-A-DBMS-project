package com.moviebuff.objects;

import java.util.List;

public class Movie {
	
	String title;
	long tmdbid;
	long imdbid;
	int movieid;
	List<String> genre;
	String genres;
	public String getGenres() {
		return genres;
	}
	public void setGenres(String genres) {
		this.genres = genres;
	}
	double avgRating;
	
	public double getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getTmdbid() {
		return tmdbid;
	}
	public void setTmdbid(long tmdbid) {
		this.tmdbid = tmdbid;
	}
	public long getImdbid() {
		return imdbid;
	}
	public void setImdbid(long imdbid) {
		this.imdbid = imdbid;
	}
	public int getMovieid() {
		return movieid;
	}
	public void setMovieid(int movieid) {
		this.movieid = movieid;
	}
	public List<String> getGenre() {
		return genre;
	}
	public void setGenre(List<String> genre) {
		this.genre = genre;
	}
	@Override
	public String toString() {
		return "<td>" + title + "</td><td>" + tmdbid + "</td><td>" + imdbid + "</td><td>" + movieid
				+ "</td><td>" + genre + "</td><td>" + avgRating + "</td>";
	}
	
	

}
