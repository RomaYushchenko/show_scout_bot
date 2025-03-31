package com.ua.yushchenko.common.kafka.events.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Class that provide abstract model of Kafka event entity
 *
 * @author romanyushchenko
 * @version 0.1
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "eventType",
        visible = true
)
@Getter
@ToString
@EqualsAndHashCode
public abstract class AbstractKafkaEvent {

    private final EventAction eventAction;

    protected AbstractKafkaEvent(@JsonProperty("eventAction") final EventAction eventAction) {
        this.eventAction = eventAction;
    }

    @JsonIgnore
    public abstract String getMessageKey();
}
