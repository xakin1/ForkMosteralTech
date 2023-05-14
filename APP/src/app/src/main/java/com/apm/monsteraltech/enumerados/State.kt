package com.apm.monsteraltech.enumerados
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

enum class State (val stateString: String) {
    NEW("NEW"),
    SECOND_HAND("SECOND_HAND"),
    SEMI_NEW("SEMI_NEW"),
    UNKNOWN("UNKNOWN");

}