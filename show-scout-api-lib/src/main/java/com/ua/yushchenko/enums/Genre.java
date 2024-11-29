package com.ua.yushchenko.enums;

/**
 * Enum that represents the type or genre of a TV show.
 * Each type is associated with a descriptive string, making it easy
 * to categorize and identify the genre of a show.
 * <p>
 * Supported genres include:
 * <ul>
 *   <li>Horror</li>
 *   <li>Drama</li>
 *   <li>Science Fiction</li>
 *   <li>Adventure</li>
 *   <li>Supernatural</li>
 *   <li>Romance</li>
 *   <li>Comedy</li>
 *   <li>Crime</li>
 *   <li>Other</li>
 * </ul>
 * <p>
 * This list can be extended to accommodate more genres.
 *
 * @author ivanshalaev
 * @version 0.1
 */
public enum Genre {
    HORROR("Horror"),
    DRAMA("Drama"),
    SCIENCE_FICTION("Science Fiction"),
    ADVENTURE("Adventure"),
    SUPERNATURAL("Supernatural"),
    ROMANCE("Romance"),
    COMEDY("Comedy"),
    CRIME("Crime"),
    OTHER("Other");

    private final String genre;

    Genre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }
}