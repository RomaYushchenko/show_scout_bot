package com.ua.yushchenko.enums;

/**
 * Enum that represents the status of a TV show.
 * It indicates whether the show is still "Running" or has "Ended".
 * <p>
 * Each status is associated with a descriptive string.
 *
 * @author ivanshalaev
 * @version 0.1
 */
public enum ShowStatus {
    RUNNING("Running"),
    ENDED("Ended");

    private final String showStatus;

    ShowStatus(String showStatus) {
        this.showStatus = showStatus;
    }

    public String getShowStatus() {
        return showStatus;
    }
}
